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
package com.synflow.cx.internal.validation;

import static com.synflow.cx.CxConstants.PROP_AVAILABLE;
import static com.synflow.cx.CxConstants.PROP_READ;
import static com.synflow.cx.CxConstants.PROP_READY;
import static com.synflow.cx.validation.IssueCodes.ERR_LOCAL_NOT_INITIALIZED;
import static com.synflow.cx.validation.IssueCodes.ERR_MULTIPLE_READS;
import static com.synflow.cx.validation.IssueCodes.ERR_NO_SIDE_EFFECTS;
import static com.synflow.models.util.SwitchUtil.check;
import static org.eclipse.xtext.validation.CheckType.NORMAL;

import java.util.List;

import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.inject.Inject;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Branch;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.CxPackage.Literals;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.StatementAssign;
import com.synflow.cx.cx.StatementLoop;
import com.synflow.cx.cx.StatementVariable;
import com.synflow.cx.cx.StatementWrite;
import com.synflow.cx.cx.Task;
import com.synflow.cx.cx.VarRef;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.cx.internal.services.BoolCxSwitch;
import com.synflow.models.dpn.Direction;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.InterfaceType;
import com.synflow.models.dpn.Port;
import com.synflow.models.util.Executable;

/**
 * This class defines a validator for Cx expressions.
 *
 * @author Matthieu Wipliez
 *
 */
public class ExpressionValidator extends AbstractDeclarativeValidator {

	/**
	 * This class defines a visitor that returns true when a function called is not constant.
	 *
	 * @author Matthieu Wipliez
	 *
	 */
	private static class ConstantCallSwitch extends BoolCxSwitch {

		@Override
		public Boolean caseExpressionVariable(ExpressionVariable expr) {
			Variable variable = expr.getSource().getVariable();
			if (CxUtil.isFunctionNotConstant(variable)) {
				return true;
			}

			return super.caseExpressionVariable(expr);
		}

	}

	@Inject
	private IInstantiator instantiator;

	@Check
	public void checkCondition(Branch stmt) {
		checkFunctionCalls(stmt.getCondition());
	}

	@Check
	public void checkCondition(StatementLoop stmt) {
		checkFunctionCalls(stmt.getCondition());
	}

	private void checkFunctionCalls(CxExpression condition) {
		if (check(new ConstantCallSwitch(), condition)) {
			error("Scheduling: this expression cannot call functions with side effects", condition,
					null, ERR_NO_SIDE_EFFECTS);
		}
	}

	@Check
	public void checkLocalVariableUse(ExpressionVariable expr) {
		Variable variable = expr.getSource().getVariable();
		if (!CxUtil.isLocal(variable)) {
			return;
		}

		boolean isArray = !variable.getDimensions().isEmpty();
		if (!isArray && !variable.isInitialized()) {
			error("The local variable '" + variable.getName() + "' may not have been initialized",
					expr, Literals.EXPRESSION_VARIABLE__SOURCE, ERR_LOCAL_NOT_INITIALIZED);
		}
	}

	@Check(NORMAL)
	public void checkMultipleReads(final CxExpression expr) {
		// Checks that there are at most one read per port in the expression. Otherwise indicate an
		// error.
		Task task = EcoreUtil2.getContainerOfType(expr, Task.class);
		if (task == null) {
			return;
		}

		instantiator.forEachMapping(task, new Executable<Entity>() {
			@Override
			public void exec(Entity entity) {
				Multiset<Port> portsRead = LinkedHashMultiset.create();
				Multiset<Port> portsAvailable = LinkedHashMultiset.create();
				computePortSets(entity, portsAvailable, portsRead, expr);

				boolean hasMultipleReads = false;
				for (Entry<Port> entry : portsRead.entrySet()) {
					hasMultipleReads |= entry.getCount() > 1;
				}

				if (hasMultipleReads) {
					error("Port error: cannot have more than one read per port in expression", expr,
							null, ERR_MULTIPLE_READS);
				}
			}
		});
	}

	@Check(NORMAL)
	public void checkPort(final ExpressionVariable expr) {
		Variable variable = expr.getSource().getVariable();
		if (CxUtil.isPort(variable)) {
			Task task = EcoreUtil2.getContainerOfType(expr, Task.class);
			if (task == null) {
				return;
			}

			// checks that the given reference to a port variable has the proper semantics.
			instantiator.forEachMapping(task, new Executable<Entity>() {
				@Override
				public void exec(Entity entity) {
					Port port = instantiator.getPort(entity, expr.getSource());
					if (port != null) {
						checkPortExpression(expr, port);
					}
				}
			});
		}
	}

	/**
	 * Checks the given expression that refers to an input port.
	 *
	 * @param expr
	 *            an expression variable
	 */
	private void checkPortExpression(ExpressionVariable expr, Port port) {
		String prop = expr.getPropertyName();
		InterfaceType iface = port.getInterface();
		if (PROP_AVAILABLE.equals(prop)) {
			if (port.getDirection() == Direction.OUTPUT || !iface.isSync()) {
				error("Port error: '" + PROP_AVAILABLE
						+ "' can only be used on 'sync' and 'ready' input ports", expr, null);
			}
		} else if (PROP_READ.equals(prop)) {
			if (port.getDirection() == Direction.OUTPUT) {
				error("Port error: '" + PROP_READ + "' can only be used on input ports", expr,
						null);
			}
		} else if (PROP_READY.equals(prop)) {
			if (port.getDirection() == Direction.INPUT || !iface.isSyncReady()) {
				error("Port error: '" + PROP_READY + "' can only be used on 'ready' output ports",
						expr, null);
			}
		} else if (port.getDirection() == Direction.OUTPUT) {
			error("Port error: an output port can only be accessed with write", expr, null);
		}

		if (!expr.getIndexes().isEmpty()) {
			error("Port error: an input port cannot be used with indexes", expr, null);
		}

		if (!expr.getParameters().isEmpty()) {
			error("Port error: the '" + prop + "' function does not accept arguments", expr, null);
		}
	}

	@Check(NORMAL)
	public void checkWrite(StatementWrite stmt) {
		Variable variable = stmt.getPort().getVariable();
		if (CxUtil.isPort(variable)) {
			Task task = EcoreUtil2.getContainerOfType(stmt, Task.class);

			// checks that the given reference to a port variable has the proper semantics.
			instantiator.forEachMapping(task, new Executable<Entity>() {
				@Override
				public void exec(Entity entity) {
					Port port = instantiator.getPort(entity, stmt.getPort());
					if (port != null && port.getDirection() != Direction.OUTPUT) {
						error("Port error: only output ports can only be accessed with write", stmt,
								null);
					}
				}
			});
		} else {
			error("Port error: only ports can be written.", stmt, null);
		}
	}

	/**
	 * Computes the two port sets: one containing ports that are available, the other one containing
	 * ports that are read.
	 *
	 * @param available
	 *            a set in which ports available are put
	 * @param read
	 *            a set in which ports read are put
	 * @param condition
	 *            the condition to visit
	 */
	public void computePortSets(Entity entity, Multiset<Port> available, Multiset<Port> read,
			CxExpression condition) {
		List<ExpressionVariable> exprs;
		if (condition == null) {
			return;
		}

		exprs = EcoreUtil2.eAllOfType(condition, ExpressionVariable.class);
		for (ExpressionVariable expr : exprs) {
			VarRef ref = expr.getSource();
			Variable variable = ref.getVariable();
			if (CxUtil.isPort(variable)) {
				Port port = instantiator.getPort(entity, ref);
				String prop = expr.getPropertyName();
				if (PROP_AVAILABLE.equals(prop)) {
					available.add(port);
				} else if (PROP_READ.equals(prop)) {
					read.add(port);
				}
			}
		}
	}

	@Override
	public void register(EValidatorRegistrar registrar) {
		// do nothing: packages are already registered by CxJavaValidator
	}

	@Check
	public void setInitialized(StatementAssign stmt) {
		Variable variable = stmt.getTarget().getSource().getVariable();

		// set variable as defined if the assignment has a value
		// MUST NOT USE variable.setDefined EVER BECAUSE IT WILL CLEAR THE INSTANTIATOR'S CACHE
		// don't use setInitialized(value != null)
		// because once a value has been defined, it must not be un-defined
		if (CxUtil.isLocal(variable) && stmt.getValue() != null) {
			setInitialized(variable);
		}
	}

	@Check
	public void setInitialized(StatementVariable stmt) {
		// set the 'defined' flag for each variable that has a value
		for (Variable variable : stmt.getVariables()) {
			if (variable.getValue() != null) {
				// MUST NOT USE variable.setDefined EVER
				setInitialized(variable);
			}
		}
	}

	/**
	 * Sets the 'initialized' field of variable to <code>true</code> without notifying adapters.
	 * Necessary so that resources stay cached.
	 *
	 * @param variable
	 *            a variable
	 */
	private void setInitialized(Variable variable) {
		boolean deliver = variable.eDeliver();
		variable.eSetDeliver(false);
		variable.setInitialized(true);
		variable.eSetDeliver(deliver);
	}

}
