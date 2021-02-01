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
package com.synflow.cx.internal.validation;

import static com.synflow.cx.CxConstants.NAME_SIZEOF;
import static com.synflow.cx.validation.IssueCodes.ERR_CANNOT_ASSIGN_CONST;
import static com.synflow.cx.validation.IssueCodes.ERR_DIV_MOD_NOT_CONST_POW_Of_TWO;
import static com.synflow.cx.validation.IssueCodes.ERR_EXPECTED_CONST;
import static com.synflow.cx.validation.IssueCodes.ERR_TYPE_MISMATCH;
import static com.synflow.models.util.SwitchUtil.DONE;
import static com.synflow.models.util.SwitchUtil.visit;
import static java.math.BigInteger.ZERO;
import static org.eclipse.xtext.EcoreUtil2.getContainerOfType;
import static org.eclipse.xtext.validation.ValidationMessageAcceptor.INSIGNIFICANT_INDEX;

import java.math.BigInteger;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.synflow.cx.CxConstants;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Branch;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.CxPackage.Literals;
import com.synflow.cx.cx.ExpressionBinary;
import com.synflow.cx.cx.ExpressionCast;
import com.synflow.cx.cx.ExpressionIf;
import com.synflow.cx.cx.ExpressionUnary;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.StatementAssign;
import com.synflow.cx.cx.StatementIdle;
import com.synflow.cx.cx.StatementLoop;
import com.synflow.cx.cx.StatementPrint;
import com.synflow.cx.cx.StatementReturn;
import com.synflow.cx.cx.StatementVariable;
import com.synflow.cx.cx.StatementWrite;
import com.synflow.cx.cx.Task;
import com.synflow.cx.cx.VarDecl;
import com.synflow.cx.cx.VarRef;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.cx.internal.AstUtil;
import com.synflow.cx.internal.services.Typer;
import com.synflow.cx.services.CxPrinter;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Instance;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.OpUnary;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeArray;
import com.synflow.models.ir.TypeBool;
import com.synflow.models.ir.util.TypePrinter;
import com.synflow.models.ir.util.TypeUtil;
import com.synflow.models.ir.util.ValueUtil;
import com.synflow.models.util.Void;

/**
 * This class defines the type checker for Cx with package-access methods that are only accessed by
 * the Cx java validator class.
 *
 * @author Matthieu Wipliez
 *
 */
public class TypeChecker extends Checker {

	private Entity entity;

	private final IInstantiator instantiator;

	public TypeChecker(ValidationMessageAcceptor acceptor, IInstantiator instantiator,
			Entity entity) {
		super(acceptor);
		this.instantiator = instantiator;
		this.entity = entity;
	}

	@Override
	public Void caseBranch(Branch stmt) {
		Type typeExpr = instantiator.computeType(entity, stmt.getCondition());
		checkAssign(IrFactory.eINSTANCE.createTypeBool(), typeExpr, stmt,
				Literals.BRANCH__CONDITION, INSIGNIFICANT_INDEX);

		return visit(this, stmt.getCondition(), stmt.getBody());
	}

	@Override
	public Void caseExpressionBinary(ExpressionBinary expression) {
		OpBinary op = OpBinary.getOperator(expression.getOperator());
		CxExpression e1 = expression.getLeft();
		CxExpression e2 = expression.getRight();
		if (e1 == null || e2 == null) {
			return DONE;
		}

		// check sub expressions
		doSwitch(e1);
		doSwitch(e2);

		// get types
		Type t1 = instantiator.computeType(entity, e1);
		Type t2 = instantiator.computeType(entity, e2);
		if (t1 == null || t2 == null) {
			return DONE;
		}

		Type type = instantiator.computeType(entity, expression);
		if (type == null) {
			error("The operator " + op.getText() + " is undefined for the argument types "
					+ new TypePrinter().toString(t1) + " and " + new TypePrinter().toString(t2),
					expression, null, INSIGNIFICANT_INDEX);
		}

		if (op.isComparison() && t1 != null && t2 != null) {
			// Range<BigInteger> rangeT1 = getRange(e1, t1);
			// Range<BigInteger> rangeT2 = getRange(e2, t2);
			// if (op == OpBinary.LT) {
			// if (rangeT2.upperEndpoint().compareTo(rangeT1.lowerEndpoint()) <= 0) {
			// error("This condition is always false", expression, null, ERR_CMP_ALWAYS_FALSE);
			// return;
			// }
			//
			// if (rangeT2.lowerEndpoint().compareTo(rangeT1.upperEndpoint()) > 0) {
			// error("This condition is always true", expression, null, ERR_CMP_ALWAYS_TRUE);
			// return;
			// }
			// } else if (op == OpBinary.GT) {
			// if (rangeT2.upperEndpoint().compareTo(rangeT1.lowerEndpoint()) < 0) {
			// error("This condition is always true", expression, null, ERR_CMP_ALWAYS_TRUE);
			// return;
			// }
			//
			// if (rangeT2.lowerEndpoint().compareTo(rangeT1.upperEndpoint()) >= 0) {
			// error("This condition is always false", expression, null, ERR_CMP_ALWAYS_FALSE);
			// return;
			// }
			// }
		}

		return DONE;
	}

	@Override
	public Void caseExpressionCast(ExpressionCast cast) {
		CxExpression expression = cast.getExpression();
		Type source = instantiator.computeType(entity, expression);
		Type target = instantiator.computeType(entity, cast.getType());
		if (source != null && target != null && !TypeUtil.canCast(source, target)) {
			error("Type mismatch: cannot convert from " + new TypePrinter().toString(source)
					+ " to " + new TypePrinter().toString(target), cast, null, INSIGNIFICANT_INDEX,
					ERR_TYPE_MISMATCH);
		}

		return visit(this, expression);
	}

	@Override
	public Void caseExpressionIf(ExpressionIf expr) {
		Type typeExpr = instantiator.computeType(entity, expr.getCondition());
		if (!(typeExpr instanceof TypeBool)) {
			error("Type mismatch: cannot convert from " + new TypePrinter().toString(typeExpr)
					+ " to bool", expr, Literals.EXPRESSION_IF__CONDITION, ERR_TYPE_MISMATCH);
		}

		return DONE;
	}

	@Override
	public Void caseExpressionUnary(ExpressionUnary expression) {
		CxExpression subExpr = expression.getExpression();
		if (subExpr == null) {
			return DONE;
		}

		// check sub expressions
		doSwitch(subExpr);

		Type typeSubExpr = instantiator.computeType(entity, subExpr);
		if (typeSubExpr == null) {
			return DONE;
		}

		if (NAME_SIZEOF.equals(expression.getOperator())) {
			// no need to get type of sizeof expression
			return DONE;
		}

		Type type = instantiator.computeType(entity, expression);
		if (type == null) {
			OpUnary op = OpUnary.getOperator(expression.getOperator());
			error("The operator " + op.getText() + " is undefined for the argument type "
					+ new TypePrinter().toString(typeSubExpr), expression, null,
					INSIGNIFICANT_INDEX);
		}
		return DONE;
	}

	@Override
	public Void caseExpressionVariable(ExpressionVariable expression) {
		doSwitch(expression.getSource());

		Variable variable = expression.getSource().getVariable();
		if (CxUtil.isFunction(variable)) {
			checkParameters(variable, expression);
		} else {
			if (!expression.getParameters().isEmpty()) {
				error("Type mismatch: '" + variable.getName()
						+ "' is not a function and is given arguments", expression, null,
						ERR_TYPE_MISMATCH);
			}

			if (CxConstants.PROP_LENGTH.equals(expression.getPropertyName())) {
				Type type = instantiator.computeType(entity, expression);
				if (type == null) {
					Type typeSource = instantiator.computeType(entity, expression.getSource());
					error("The length property is undefined for the argument type "
							+ new TypePrinter().toString(typeSource), expression, null,
							INSIGNIFICANT_INDEX);
				}
			}
		}

		return DONE;
	}

	@Override
	public Void caseInst(Inst inst) {
		final Task task = inst.getTask();
		if (task != null) {
			Entity oldEntity = entity;
			Instance instance = instantiator.getMapping(entity, inst);
			entity = instance.getEntity();
			doSwitch(task);
			entity = oldEntity;
		}
		return DONE;
	}

	@Override
	public Void caseStatementAssign(StatementAssign stmt) {
		if (stmt.getValue() == null) {
			// increment or decrement, check the variable has been initialized before
			doSwitch(stmt.getTarget());
		} else {
			doSwitch(stmt.getValue());
		}

		ExpressionVariable target = stmt.getTarget();
		VarRef ref = target.getSource();
		Variable variable = ref.getVariable();

		// if this is an assignment, check target is constant
		if (CxUtil.isConstant(variable) && stmt.getOp() != null) {
			error("The constant '" + variable.getName() + "' cannot be assigned.", stmt,
					Literals.STATEMENT_ASSIGN__TARGET, ERR_CANNOT_ASSIGN_CONST);
			return DONE;
		}

		// compute type of variable
		Type targetType = instantiator.computeType(entity, ref);
		if (targetType == null) {
			return DONE;
		}

		// check array access
		checkArrayAccess(stmt, targetType, target.getIndexes());

		// compute type of target
		int dimensions = Typer.getNumDimensions(targetType);
		int indexes = target.getIndexes().size();
		if (indexes == dimensions + 1) {
			targetType = IrFactory.eINSTANCE.createTypeBool();
		} else if (indexes == dimensions) {
			if (targetType.isArray()) {
				targetType = ((TypeArray) targetType).getElementType();
			}
		} else {
			return null;
		}

		// check type
		CxExpression value = AstUtil.getAssignValue(stmt);
		checkAssignImplicitBool(targetType, instantiator.computeType(entity, value), stmt,
				Literals.STATEMENT_ASSIGN__VALUE, INSIGNIFICANT_INDEX);

		return DONE;
	}

	@Override
	public Void caseStatementLoop(StatementLoop stmt) {
		Type typeExpr = instantiator.computeType(entity, stmt.getCondition());
		checkAssign(IrFactory.eINSTANCE.createTypeBool(), typeExpr, stmt,
				Literals.STATEMENT_LOOP__CONDITION, INSIGNIFICANT_INDEX);

		return visit(this, stmt.getInit(), stmt.getCondition(), stmt.getBody(), stmt.getAfter());
	}

	@Override
	public Void caseStatementPrint(StatementPrint stmt) {
		for (CxExpression expr : stmt.getArgs()) {
			doSwitch(expr);

			Type type = instantiator.computeType(entity, expr);
			if (type != null && type.isVoid()) {
				error("Type mismatch: cannot print void", expr, null, ERR_TYPE_MISMATCH);
			}
		}

		return DONE;
	}

	@Override
	public Void caseStatementReturn(StatementReturn stmt) {
		Variable function = getContainerOfType(stmt, Variable.class);
		Type target = instantiator.computeType(entity, function);

		CxExpression value = stmt.getValue();
		if (value == null) {
			if (target != null && !target.isVoid()) {
				error("This method must return a result of type "
						+ new TypePrinter().toString(target), stmt, null, ERR_TYPE_MISMATCH);
			}
		} else {
			checkAssign(target, instantiator.computeType(entity, value), stmt,
					Literals.STATEMENT_RETURN__VALUE, INSIGNIFICANT_INDEX);
		}

		return DONE;
	}

	@Override
	public Void caseStatementVariable(StatementVariable stmt) {
		int index = 0;
		for (Variable variable : stmt.getVariables()) {
			Type typeVar = instantiator.computeType(entity, variable);
			EObject value = variable.getValue();
			if (value != null) {
				// type check of value
				doSwitch(value);

				// check assignment
				checkAssignImplicitBool(typeVar, instantiator.computeType(entity, value), stmt,
						Literals.STATEMENT_VARIABLE__VARIABLES, index);
			}
			index++;
		}

		return DONE;
	}

	@Override
	public Void caseStatementWrite(StatementWrite stmt) {
		Type typePort = instantiator.computeType(entity, stmt.getPort());
		Type typeExpr = instantiator.computeType(entity, stmt.getValue());
		checkAssignImplicitBool(typePort, typeExpr, stmt, Literals.STATEMENT_WRITE__VALUE,
				INSIGNIFICANT_INDEX);

		return visit(this, stmt.getValue());
	}

	@Override
	public Void caseTask(final Task task) {
		visit(TypeChecker.this, CxUtil.getFunctions(task));
		return DONE;
	}

	private void checkArrayAccess(EObject source, Type type, EList<CxExpression> indexes) {
		if (indexes.isEmpty()) {
			// no indexes, nothing to check
			return;
		}

		if (type == null) {
			// no valid type, nothing to check at this point
			return;
		}

		int actual = indexes.size();
		int dimensions = Typer.getNumDimensions(type);
		if (actual < dimensions) {
			// there are not enough indexes
			error("Type mismatch: cannot convert from array to scalar", source, null,
					ERR_TYPE_MISMATCH);
		} else if (actual > dimensions + 1) {
			// there are too many indexes
			error("Type mismatch: cannot convert from number to array", source, null,
					ERR_TYPE_MISMATCH);
		} else if (actual == dimensions + 1) {
			// bit selection
			checkBitSelect(type, indexes.get(actual - 1));
		}
	}

	public void checkBitSelect(ExpressionVariable expr) {
		Type type = instantiator.computeType(entity, expr.getSource());
		checkArrayAccess(expr, type, expr.getIndexes());
	}

	/**
	 * Checks bit selection on a variable with the given type and at the given index is valid.
	 *
	 * @param type
	 *            type of the variable
	 * @param index
	 *            index as an expression
	 */
	private void checkBitSelect(Type type, CxExpression index) {
		Object value = instantiator.evaluate(entity, index);
		if (!ValueUtil.isInt(value)) {
			error("Type mismatch: cannot convert value to constant integer in bit selection", index,
					null, ERR_TYPE_MISMATCH);
			return;
		}

		// Range<BigInteger> rangeActual = Ranges.singleton((BigInteger) value);
		// Range<BigInteger> rangeExpected = Ranges.closedOpen(BigInteger.ZERO,
		// BigInteger.valueOf(((TypeInt) type).getSize()));
		// if (rangeExpected.intersection(rangeActual).isEmpty()) {
		// error("Type mismatch: bit-selection index is outside of range", index, null,
		// ERR_TYPE_MISMATCH);
		// }
	}

	/**
	 * When the operator is /, %, &lt;&lt;, &gt;&gt;, and the expression is used outside of state
	 * variable initialization, checks that the right operand of the given binary expression is
	 * constant.
	 *
	 * @param expr
	 */
	public void checkExprBinaryHasConstantRightOperand(ExpressionBinary expr) {
		Variable var = getContainerOfType(expr, Variable.class);
		if (var != null && var.eContainer() instanceof VarDecl) {
			// use of /, %, <<, >> allowed in initialization of state variables
			return;
		}

		String op = expr.getOperator();
		if ("/".equals(op) || "%".equals(op)) {
			Object value = instantiator.evaluate(entity, expr.getRight());
			if (value == null || !ValueUtil.isInt(value)) {
				error("The right operand of operator " + op + " must be constant", expr, null,
						ERR_DIV_MOD_NOT_CONST_POW_Of_TWO);
				return;
			}

			int v = ((BigInteger) value).intValue();
			if (!ValueUtil.isPowerOfTwo(v)) {
				error("The right operand of operator " + op
						+ " must be a power of two and greater than zero", expr, null,
						ERR_DIV_MOD_NOT_CONST_POW_Of_TWO);
			}
		} else if ("<<".equals(op) || ">>".equals(op)) {
			Object value = instantiator.evaluate(entity, expr.getRight());
			if (value == null || !ValueUtil.isInt(value)) {
				error("The right operand of operator " + op + " must be constant", expr, null,
						ERR_EXPECTED_CONST);
			}
		}
	}

	@Check
	public void checkIdle(StatementIdle idle) {
		CxExpression numCycles = idle.getNumCycles();
		Object value = instantiator.evaluate(entity, numCycles);
		if (!ValueUtil.isInt(value)) {
			error("Illegal idle: the number of cycles must be a compile-time constant integer",
					numCycles, null, ERR_EXPECTED_CONST);
		} else if (!ValueUtil.isTrue(ValueUtil.gt(value, ZERO))) {
			error("Illegal idle: the number of cycles must be greater than zero", numCycles, null,
					ERR_EXPECTED_CONST);
		}
	}

	private void checkParameters(Variable function, ExpressionVariable call) {
		EList<CxExpression> arguments = call.getParameters();
		Iterable<Type> types = Iterables.transform(arguments, new Function<CxExpression, Type>() {
			@Override
			public Type apply(CxExpression expression) {
				return instantiator.computeType(entity, expression);
			}
		});

		EList<Variable> parameters = function.getParameters();
		if (parameters.size() == arguments.size()) {
			Iterator<Variable> itV = parameters.iterator();
			Iterator<Type> itT = types.iterator();
			boolean hasErrors = false;
			while (itV.hasNext() && itT.hasNext()) {
				Type target = instantiator.computeType(entity, itV.next());
				Type source = itT.next();
				if (target == null || source == null) {
					continue;
				} else if (!TypeUtil.canAssign(source, target)) {
					hasErrors = true;
					break;
				}
			}

			if (!hasErrors) {
				return;
			}
		}

		Iterable<String> typeStr = Iterables.transform(types, new Function<Type, String>() {
			@Override
			public String apply(Type type) {
				return new TypePrinter().toString(type);
			}
		});

		error("The method " + new CxPrinter().toString(function)
				+ " is not applicable for the arguments (" + Joiner.on(", ").join(typeStr) + ")",
				call, null, ERR_TYPE_MISMATCH);
	}

	// private Range<BigInteger> getRange(CxExpression expr, Type type) {
	// Object value = Evaluator.getValue(expr);
	// if (ValueUtil.isInt(value)) {
	// return Ranges.singleton((BigInteger) value);
	// } else {
	// return getRange(type);
	// }
	// }

	// private Range<BigInteger> getRange(Type type) {
	// if (type.isInt()) {
	// TypeInt typeInt = (TypeInt) type;
	// int size = typeInt.getSize();
	// if (typeInt.isSigned()) {
	// BigInteger lower = ONE.shiftLeft(size - 1).negate();
	// BigInteger upper = ONE.shiftLeft(size - 1).subtract(ONE);
	// return Ranges.closed(lower, upper);
	// } else {
	// BigInteger lower = ZERO;
	// BigInteger upper = ONE.shiftLeft(size).subtract(ONE);
	// return Ranges.closed(lower, upper);
	// }
	// }
	// return null;
	// }

}
