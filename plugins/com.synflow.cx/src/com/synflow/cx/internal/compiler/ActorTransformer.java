/*
 * This file is part of the ngDesign SDK.
 *
 * Copyright (c) 2019 Synflow SAS.
 *
 * ngDesign is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ngDesign is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ngDesign.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
package com.synflow.cx.internal.compiler;

import static com.synflow.cx.CxConstants.PROP_AVAILABLE;
import static com.synflow.cx.CxConstants.PROP_READY;
import static com.synflow.cx.internal.TransformerUtil.getStartLine;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.Enter;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.Leave;
import com.synflow.cx.cx.StatementGoto;
import com.synflow.cx.cx.StatementIdle;
import com.synflow.cx.cx.StatementWrite;
import com.synflow.cx.cx.VarRef;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.Goto;
import com.synflow.models.dpn.Port;
import com.synflow.models.dpn.Transition;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstStore;
import com.synflow.models.ir.Var;

/**
 * This class transforms AST statements into IR instructions and/or nodes at the actor level.
 *
 * @author Matthieu Wipliez
 *
 */
public class ActorTransformer extends FunctionTransformer {

	private Deque<Integer> lines;

	/**
	 * Creates a new actor transformer with the given actor.
	 *
	 * @param actor
	 *            actor
	 */
	public ActorTransformer(IInstantiator instantiator, Actor actor) {
		super(new ActorBuilder(instantiator, actor));
		lines = new ArrayDeque<>();
	}

	@Override
	public EObject caseEnter(Enter enter) {
		Variable function = enter.getFunction().getVariable();
		int lineNumber = enter.getLineNumber();
		getBuilder().updateLineInfo(lineNumber);

		// all transitions will now correspond to this line
		if (getBuilder().line == null) {
			getBuilder().line = lineNumber;
		} else {
			lines.addFirst(getBuilder().line);
		}

		// transform arguments
		List<CxExpression> arguments = enter.getParameters();
		Iterator<CxExpression> it = arguments.iterator();
		for (Variable variable : function.getParameters()) {
			Var var = builder.transformLocal(variable);
			builder.getProcedure().getLocals().add(var);

			CxExpression value = it.next();
			builder.storeExpr(getBuilder().line, var, null, value);
		}

		// no need to include void function
		// this has been done by the cycle scheduler
		return null;
	}

	@Override
	public Expression caseExpressionVariable(ExpressionVariable expression) {
		VarRef ref = expression.getSource();
		Variable variable = ref.getVariable();
		if (!CxUtil.isPort(variable)) {
			return super.caseExpressionVariable(expression);
		}

		int lineNumber = getStartLine(expression);

		// translate expression to load from port
		Port port = getBuilder().getPort(ref);
		String prop = expression.getPropertyName();
		Var source, target;
		if (PROP_AVAILABLE.equals(prop) || PROP_READY.equals(prop)) {
			source = port.getAdditionalInputs().get(0);

			String targetName = "is_" + variable.getName() + "_" + prop;
			target = builder.createLocal(lineNumber, ir.createTypeBool(), targetName);
		} else {
			source = port;
			target = builder.createLocal(lineNumber, port.getType(), variable.getName());
		}

		builder.add(ir.createInstLoad(lineNumber, target, source));
		return ir.createExprVar(target);
	}

	@Override
	public EObject caseLeave(Leave leave) {
		// restore previous line behavior
		getBuilder().line = lines.pollFirst();
		return null;
	}

	@Override
	public EObject caseStatementGoto(StatementGoto stmt) {
		Goto gotoInstr = DpnFactory.eINSTANCE.createGoto();
		gotoInstr.setTarget(stmt.getTarget());
		builder.add(gotoInstr);

		return null;
	}

	@Override
	public EObject caseStatementIdle(StatementIdle idle) {
		hookBefore(idle);
		return null;
	}

	@Override
	public EObject caseStatementWrite(StatementWrite write) {
		// check if this write should be ignored (if it is translated as part of the scheduler)
		if (getBuilder().ignoreWrites()) {
			return null;
		}

		hookBefore(write);

		Port port = getBuilder().getPort(write.getPort());
		Expression expr = builder.transformExpr(write.getValue(), port.getType());
		InstStore store = ir.createInstStore(getStartLine(write), port, expr);
		builder.add(store);

		// for additional output signals
		for (Var signal : port.getAdditionalOutputs()) {
			store = ir.createInstStore(getStartLine(write), signal, ir.createExprBool(true));
			builder.add(store);
		}

		return null;
	}

	private ActorBuilder getBuilder() {
		return (ActorBuilder) builder;
	}

	@Override
	protected void hookBefore(EObject eObject) {
		int lineNumber = getStartLine(eObject);
		getBuilder().updateLineInfo(lineNumber);
	}

	public void visit() {
		ActorBuilder builder = getBuilder();
		FSM fsm = builder.getActor().getFsm();
		for (Transition transition : fsm.getTransitions()) {
			builder.visitTransition(transition);
		}
	}

}
