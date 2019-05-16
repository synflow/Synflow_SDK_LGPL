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

import static com.synflow.cx.CxConstants.NAME_LOOP;
import static com.synflow.cx.CxConstants.NAME_LOOP_DEPRECATED;
import static com.synflow.cx.CxConstants.NAME_SETUP;
import static com.synflow.cx.CxConstants.NAME_SETUP_DEPRECATED;
import static com.synflow.cx.validation.IssueCodes.ERR_DUPLICATE_DECLARATIONS;
import static com.synflow.cx.validation.IssueCodes.ERR_ENTRY_FUNCTION_BAD_TYPE;
import static com.synflow.cx.validation.IssueCodes.ERR_EXPECTED_CONST;
import static com.synflow.cx.validation.IssueCodes.ERR_ILLEGAL_FENCE;
import static com.synflow.cx.validation.IssueCodes.ERR_SIDE_EFFECTS_FUNCTION;
import static com.synflow.cx.validation.IssueCodes.ERR_TYPE_ONE_BIT;
import static com.synflow.cx.validation.IssueCodes.ERR_VAR_DECL;
import static com.synflow.cx.validation.IssueCodes.SHOULD_REPLACE_NAME;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.synflow.core.layout.ITreeElement;
import com.synflow.core.layout.ProjectLayout;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Block;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.CxPackage.Literals;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.Instantiable;
import com.synflow.cx.cx.Module;
import com.synflow.cx.cx.PortDef;
import com.synflow.cx.cx.SinglePortDecl;
import com.synflow.cx.cx.Statement;
import com.synflow.cx.cx.StatementAssign;
import com.synflow.cx.cx.StatementFence;
import com.synflow.cx.cx.StatementIdle;
import com.synflow.cx.cx.StatementIf;
import com.synflow.cx.cx.StatementLoop;
import com.synflow.cx.cx.StatementVariable;
import com.synflow.cx.cx.Task;
import com.synflow.cx.cx.TypeDecl;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.internal.services.BoolCxSwitch;

/**
 * This class defines a structural validator.
 *
 * @author Matthieu Wipliez
 *
 */
public class StructuralValidator extends AbstractDeclarativeValidator {

	/**
	 * This class defines a visitor that checks if a value has side-effects, which is the case if it
	 * references any variable that is not constant (this includes functions and ports).
	 *
	 * @author Matthieu Wipliez
	 *
	 */
	private static class ValueVisitor extends BoolCxSwitch {

		@Override
		public Boolean caseExpressionVariable(ExpressionVariable expr) {
			Variable variable = expr.getSource().getVariable();
			if (!CxUtil.isConstant(variable)) {
				// any reference to a port and non-constant function
				return true;
			}

			return super.caseExpressionVariable(expr);
		}

	}

	@Inject
	private IQualifiedNameProvider nameProvider;

	@Inject
	private IScopeProvider scopeProvider;

	// @Inject
	// private Typer typer;

	@Check
	public void checkArrayMultiDimPowerOfTwo(Variable variable) {
		if (CxUtil.isPort(variable)) {
			return;
		}

		// TODO we need an entity here, this check should be moved elsewhere
		// Type type = typer.getType(variable);
		// if (type == null) {
		// return;
		// }
		//
		// int dimensions = Typer.getNumDimensions(type);
		// if (dimensions >= 2) {
		// for (int dim : ((TypeArray) type).getDimensions()) {
		// if (!ValueUtil.isPowerOfTwo(dim)) {
		// error("Multi-dimensional arrays must have dimensions that are power-of-two",
		// variable, Literals.VARIABLE__DIMENSIONS,
		// ERR_ARRAY_MULTI_NON_POWER_OF_TWO);
		// }
		// }
		// }
	}

	@Check
	public void checkAssign(StatementAssign stmt) {
		Variable variable = stmt.getTarget().getSource().getVariable();
		if (CxUtil.isPort(variable) && stmt.getOp() != null) {
			error("Port error: a port cannot be assigned. Use the write function instead.", stmt,
					Literals.STATEMENT_ASSIGN__TARGET);
		}
	}

	@Check
	public void checkDuplicateDeclarations(Variable variable) {
		EObject context = variable.eContainer();
		if (variable.getName() == null || context instanceof ExpressionVariable) {
			// ignore variables with a null name and synthetic variables (created by the linker)
			// name is null when the variable declaration is incomplete
			return;
		}

		QualifiedName name;
		if (context instanceof StatementVariable) {
			// local variable
			name = QualifiedName.create(variable.getName());
		} else {
			// not a local variable
			context = EcoreUtil2.getContainerOfType(variable, Module.class);
			name = nameProvider.getFullyQualifiedName(variable);
		}

		IScope scope = scopeProvider.getScope(context, Literals.VAR_REF__OBJECTS);
		Iterable<IEObjectDescription> it = scope.getElements(name);
		int n = Iterables.size(it);

		if (n > 1) {
			error("Duplicate variable declaration '" + variable.getName() + "'", variable,
					Literals.NAMED__NAME, ERR_DUPLICATE_DECLARATIONS);
		}
	}

	@Check
	public void checkFence(StatementFence fence) {
		Block compound = (Block) fence.eContainer();
		List<Statement> stmts = compound.getStmts();
		int index = stmts.indexOf(fence);
		boolean illegal = false;
		if (index == 0 || index == stmts.size() - 1) {
			// first or last => illegal
			illegal = true;
		} else {
			Statement previous = stmts.get(index - 1);
			if (previous instanceof StatementFence) {
				// fence before a fence => illegal
				illegal = true;
			} else {
				Statement next = stmts.get(index + 1);
				if ((previous instanceof StatementIdle || next instanceof StatementIdle)
						|| (previous instanceof StatementIf)
						|| (previous instanceof StatementLoop || next instanceof StatementLoop)) {
					// fence before/after idle, if, loop => illegal
					illegal = true;
				}
			}
		}

		if (illegal) {
			error("Illegal fence: a fence must be placed between two statements.", fence, null,
					ERR_ILLEGAL_FENCE);
		}
	}

	@Check
	public void checkFunction(Variable variable) {
		if (CxUtil.isFunction(variable)) {
			// functions declared as constant must not have side effects
			if (CxUtil.isConstant(variable) && CxUtil.hasSideEffects(variable)) {
				error("Constant function '" + variable.getName() + "' cannot have side effects",
						variable, Literals.NAMED__NAME, ERR_SIDE_EFFECTS_FUNCTION);
			}

			// functions declared as constant must not have side effects
			if (!CxUtil.isConstant(variable) && !CxUtil.isVoid(variable)) {
				error("Function '" + variable.getName()
						+ "' returns a result and must be declared const", variable,
						Literals.NAMED__NAME, ERR_SIDE_EFFECTS_FUNCTION);
			}
		}
	}

	@Check
	public void checkPackage(Module module) {
		String packageName = module.getPackage();
		URI uri = module.eResource().getURI();
		if (uri.isPlatform()) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IResource resource = workspace.getRoot().findMember(uri.toPlatformString(true));
			ITreeElement element = ProjectLayout.getTreeElement(resource.getParent());
			if (element != null && element.isPackage()) {
				String expected = element.getName();
				if (!packageName.equals(expected)) {
					error("The declared package \"" + packageName
							+ "\" does not match the expected package \"" + expected + "\"",
							module, Literals.MODULE__PACKAGE, INSIGNIFICANT_INDEX,
							SHOULD_REPLACE_NAME, packageName, expected);
				}
			}
		}
	}

	@Check
	public void checkPortDecl(SinglePortDecl decl) {
		if (!decl.getPorts().isEmpty()) {
			PortDef def = decl.getPorts().get(0);
			if (def.getVar().getType() == null) {
				error("Port declaration: this port must have a type", def.getVar(),
						Literals.NAMED__NAME);
			}
		}
	}

	@Check
	public void checkStateVariable(Variable variable) {
		// this is only for global variables (not local, not functions)
		if (!CxUtil.isGlobal(variable) || CxUtil.isFunction(variable)) {
			return;
		}

		// check dimensions
		for (CxExpression dim : variable.getDimensions()) {
			boolean hasSideEffects = new ValueVisitor().doSwitch(dim);
			if (hasSideEffects) {
				error("This expression is not a compile-time constant", dim, null,
						ERR_EXPECTED_CONST);
			}
		}

		// set flag "module is actor"
		Instantiable entity = EcoreUtil2.getContainerOfType(variable, Instantiable.class);

		// check initial value
		if (!checkStateVarValue(entity != null, variable)) {
			return;
		}

		// check type of value is compatible with type of state variable
		// TODO do it differently so we don't have to compute the type of arrays
		// Value value = (Value) variable.getValue();
		// Type typeExpr = ValueUtil.getType(Evaluator.getValue(value));
		// new TypeChecker(getMessageAcceptor()).checkAssign(variable, variable, typeExpr);

		// in a header, a state variable is implicitly constant
	}

	private boolean checkStateVarValue(boolean isActor, Variable variable) {
		CxExpression value = variable.getValue();
		if (value == null) {
			if (!isActor) {
				// in a header, a state variable must have an initial value
				error("The variable " + variable.getName() + " must have "
						+ "an initial value because it is defined in a header", variable, null,
						ERR_VAR_DECL);
				return false;
			}

			// a variable declared as "const" must have an initial value
			if (CxUtil.isConstant(variable)) {
				error("The variable " + variable.getName() + " must have "
						+ "an initial value because it is declared constant", variable, null,
						ERR_VAR_DECL);
			}

			return false;
		}

		// check if value has side-effects
		boolean hasSideEffects = new ValueVisitor().doSwitch(value);
		if (hasSideEffects) {
			error("The initial value of the variable '" + variable.getName()
					+ "' is not a compile-time constant", value, null, ERR_EXPECTED_CONST);
			return false;
		}
		return true;
	}

	@Check
	public void checkTask(Task task) {
		Variable function = CxUtil.getFunction(task, NAME_LOOP);
		if (function == null) {
			function = CxUtil.getFunction(task, NAME_LOOP_DEPRECATED);
			if (function == null) {
				return;
			}
		}

		Variable loop = function;
		if (!CxUtil.isVoid(loop)) {
			String message = "The 'loop' function must have type void";
			error(message, loop, Literals.NAMED__NAME, ERR_ENTRY_FUNCTION_BAD_TYPE);
		}

		function = CxUtil.getFunction(task, NAME_SETUP);
		if (function == null) {
			function = CxUtil.getFunction(task, NAME_SETUP_DEPRECATED);
		}

		Variable setup = function;
		if (setup != null && !CxUtil.isVoid(setup)) {
			String message = "The 'setup' function must have type void";
			error(message, setup, Literals.NAMED__NAME, ERR_ENTRY_FUNCTION_BAD_TYPE);
		}
	}

	@Check
	public void checkTypeDecl(TypeDecl type) {
		String spec = type.getSpec();
		if ("i1".equals(spec) || "u1".equals(spec)) {
			error("Integer types must be at least two bits large, use bool to declare a single-bit variable",
					type, null, ERR_TYPE_ONE_BIT);
		}
	}

	@Override
	public void register(EValidatorRegistrar registrar) {
		// do nothing: packages are already registered by CxJavaValidator
	}

}
