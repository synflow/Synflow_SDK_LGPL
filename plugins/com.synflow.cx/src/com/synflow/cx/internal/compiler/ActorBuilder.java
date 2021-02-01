/*
 * This file is part of the ngDesign SDK.
 *
 * Copyright (c) 2019 - 2021 Synflow SAS.
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

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.synflow.cx.cx.VarRef;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.Port;
import com.synflow.models.dpn.Transition;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.Var;

/**
 * This class defines a dataflow builder that inherits from the IR builder.
 *
 * @author Matthieu Wipliez
 *
 */
public class ActorBuilder extends IrBuilder {

	private boolean ignoreWrites;

	protected Integer line;

	private Transition transition;

	public ActorBuilder(IInstantiator instantiator, Actor actor) {
		super(instantiator, actor);
	}

	/**
	 * Creates the body of the action associated with the given transition info. This method simply
	 * uses the transformer to visit the objects associated with the info's body.
	 *
	 * @param info
	 *            transition info
	 */
	private void createBody() {
		// use the 'body' procedure
		setProcedure(transition.getAction().getBody());
		for (EObject eObject : transition.getBody()) {
			transformer.doSwitch(eObject);
		}
	}

	/**
	 * Creates the scheduler of the action associated with the given transition info.
	 *
	 * @param info
	 *            transition info
	 */
	private void createScheduler() {
		// use the 'scheduler' procedure
		setProcedure(transition.getAction().getScheduler());

		// translate statements and condition
		ignoreWrites = true;
		Expression expr = null;
		List<EObject> eObjects = transition.getScheduler();
		for (EObject eObject : eObjects) {
			// translate object
			EObject irObject = transformer.doSwitch(eObject);
			if (irObject instanceof Expression) {
				expr = translateCondition(expr, (Expression) irObject);
			}
		}
		ignoreWrites = false;

		// adds a return if the expression is not null
		if (expr == null) {
			expr = ir.createExprBool(true);
		}
		add(ir.createInstReturn(expr));
	}

	final Actor getActor() {
		return (Actor) entity;
	}

	/**
	 * Returns the IR port that corresponds to the given variable reference.
	 *
	 * @param ref
	 *            a variable reference
	 * @return an IR port
	 */
	public Port getPort(VarRef ref) {
		return instantiator.getPort(entity, ref);
	}

	/**
	 * Returns true if writes should be ignored, which is the case when translating the scheduler.
	 *
	 * @return
	 */
	public boolean ignoreWrites() {
		return ignoreWrites;
	}

	private Expression translateCondition(Expression expr, Expression condition) {
		// assign to new 'cond' variable
		Var condVar = createLocal(0, ir.createTypeBool(), "cond");
		add(ir.createInstAssign(condVar, condition));
		Expression cond = ir.createExprVar(condVar);

		if (expr == null) {
			return cond;
		} else {
			return ir.createExprBinary(expr, OpBinary.LOGIC_AND, cond);
		}
	}

	final void updateLineInfo(int lineNumber) {
		if (line == null) {
			transition.getLines().add(lineNumber);
		} else {
			transition.getLines().add(line);
		}
	}

	/**
	 * Visits the given transition and creates the IR of the action associated with it.
	 *
	 * @param transition
	 *            a transition
	 */
	public void visitTransition(Transition transition) {
		this.transition = transition;

		createScheduler();
		createBody();

		transition.getBody().clear();
		transition.getScheduler().clear();
	}

}
