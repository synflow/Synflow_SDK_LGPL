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
package com.synflow.cx.internal.instantiation;

import static com.synflow.cx.CxConstants.NAME_SIZEOF;
import static com.synflow.cx.CxConstants.PROP_LENGTH;
import static com.synflow.models.ir.util.TypeUtil.getSize;
import static com.synflow.models.ir.util.ValueUtil.getType;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;

import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.CxFactory;
import com.synflow.cx.cx.ExpressionBinary;
import com.synflow.cx.cx.ExpressionBoolean;
import com.synflow.cx.cx.ExpressionFloat;
import com.synflow.cx.cx.ExpressionIf;
import com.synflow.cx.cx.ExpressionInteger;
import com.synflow.cx.cx.ExpressionList;
import com.synflow.cx.cx.ExpressionString;
import com.synflow.cx.cx.ExpressionUnary;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.cx.util.CxSwitch;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.models.dpn.Entity;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.OpUnary;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.util.ValueUtil;

/**
 * This class defines an expression evaluator.
 *
 * @author Matthieu Wipliez
 *
 */
public class Evaluator extends CxSwitch<Object> {

	/**
	 * Returns the Cx value that matches the given runtime value. Value is expected to be one of
	 * Boolean, BigDecimal, BigInteger, String, or Array.
	 *
	 * @param value
	 *            a runtime value
	 * @return a Cx value (ValueExpr or ValueList)
	 */
	public static CxExpression getCxExpression(Object value) {
		if (ValueUtil.isBool(value)) {
			ExpressionBoolean expr = CxFactory.eINSTANCE.createExpressionBoolean();
			expr.setValue((Boolean) value);
			return expr;
		} else if (ValueUtil.isFloat(value)) {
			ExpressionFloat expr = CxFactory.eINSTANCE.createExpressionFloat();
			expr.setValue((BigDecimal) value);
			return expr;
		} else if (ValueUtil.isInt(value)) {
			ExpressionInteger expr = CxFactory.eINSTANCE.createExpressionInteger();
			expr.setValue((BigInteger) value);
			return expr;
		} else if (ValueUtil.isString(value)) {
			ExpressionString expr = CxFactory.eINSTANCE.createExpressionString();
			expr.setValue((String) value);
			return expr;
		} else if (ValueUtil.isList(value)) {
			ExpressionList list = CxFactory.eINSTANCE.createExpressionList();
			int length = Array.getLength(value);
			for (int i = 0; i < length; i++) {
				list.getValues().add(getCxExpression(Array.get(value, i)));
			}
			return list;
		} else {
			return null;
		}
	}

	private Entity entity;

	private IInstantiator instantiator;

	Evaluator(IInstantiator instantiator) {
		this.instantiator = instantiator;
	}

	@Override
	public Object caseExpressionBinary(ExpressionBinary expression) {
		OpBinary op = OpBinary.getOperator(expression.getOperator());
		Object val1 = doSwitch(expression.getLeft());
		Object val2 = doSwitch(expression.getRight());
		return ValueUtil.compute(val1, op, val2);
	}

	@Override
	public Object caseExpressionBoolean(ExpressionBoolean expression) {
		return expression.isValue();
	}

	@Override
	public Object caseExpressionFloat(ExpressionFloat expr) {
		return expr.getValue();
	}

	@Override
	public Object caseExpressionIf(ExpressionIf expression) {
		Object condition = doSwitch(expression.getCondition());

		if (ValueUtil.isBool(condition)) {
			if (ValueUtil.isTrue(condition)) {
				return doSwitch(expression.getThen());
			} else {
				return doSwitch(expression.getElse());
			}
		} else {
			return null;
		}
	}

	@Override
	public Object caseExpressionInteger(ExpressionInteger expression) {
		return expression.getValue();
	}

	@Override
	public Object caseExpressionList(ExpressionList valueList) {
		int size = valueList.getValues().size();
		Object[] objects = new Object[size];
		int i = 0;
		for (CxExpression value : valueList.getValues()) {
			objects[i] = doSwitch(value);
			i++;
		}
		return objects;
	}

	@Override
	public Object caseExpressionString(ExpressionString expression) {
		return expression.getValue();
	}

	@Override
	public Object caseExpressionUnary(ExpressionUnary expression) {
		CxExpression subExpr = expression.getExpression();
		if (NAME_SIZEOF.equals(expression.getOperator())) {
			if (subExpr instanceof ExpressionVariable) {
				ExpressionVariable expr = (ExpressionVariable) subExpr;
				Type typeSource = instantiator.computeType(entity, expr.getSource());
				if (typeSource != null && typeSource.isArray()) {
					Type type = instantiator.computeType(entity, subExpr);
					return BigInteger.valueOf(getSize(type));
				}
			}

			Object value = doSwitch(subExpr);
			if (value == null) {
				return null;
			}
			return BigInteger.valueOf(getSize(getType(value)));
		}

		OpUnary op = OpUnary.getOperator(expression.getOperator());
		Object value = doSwitch(subExpr);
		return ValueUtil.compute(op, value);
	}

	@Override
	public Object caseExpressionVariable(ExpressionVariable expression) {
		Variable variable = expression.getSource().getVariable();
		Object value;
		if (CxUtil.isConstant(variable)) {
			// only returns the value for constants
			// no cross-variable initializations
			if (CxUtil.isFunction(variable)) {
				// one day we should probably implement an evaluator for functions
				// until then we'll just return null
				return null;
			}

			if (CxUtil.isGlobal(variable)) {
				// global constant variables have already been mapped by the instantiator
				Var var = instantiator.getMapping(entity, variable);
				value = ValueUtil.getValue(var.getInitialValue());
			} else {
				value = doSwitch(variable.getValue());
			}
		} else {
			return null;
		}

		for (CxExpression index : expression.getIndexes()) {
			Object indexValue = doSwitch(index);
			if (!ValueUtil.isList(value)) {
				throw new IllegalArgumentException("trying to use a scalar as an array");
			}

			int ind = ValueUtil.getInt(indexValue);
			try {
				value = Array.get(value, ind);
			} catch (ArrayIndexOutOfBoundsException e) {
				return null;
			}
		}

		if (PROP_LENGTH.equals(expression.getPropertyName())) {
			return ValueUtil.isList(value) ? BigInteger.valueOf(Array.getLength(value)) : null;
		}

		return value;
	}

	@Override
	public Object doSwitch(EObject eObject) {
		if (eObject == null) {
			return null;
		}

		return super.doSwitch(eObject);
	}

	/**
	 * Returns the integer value associated with the given object using its URI. Returns -1 if the
	 * value is not an integer.
	 *
	 * @param eObject
	 *            an AST node
	 * @return the integer value associated with the given object
	 */
	int getIntValue(Entity entity, EObject eObject) {
		Object value = getValue(entity, eObject);
		if (value != null && ValueUtil.isInt(value)) {
			BigInteger intExpr = (BigInteger) value;
			if (intExpr.bitLength() < Integer.SIZE) {
				return intExpr.intValue();
			}
		}

		// evaluated ok, but not as an integer
		return -1;
	}

	/**
	 * Returns the value associated with the given object using its URI.
	 *
	 * @param eObject
	 *            an AST node
	 * @return the value associated with the given object
	 */
	Object getValue(Entity entity, EObject eObject) {
		Entity oldEntity = this.entity;
		this.entity = entity;
		try {
			return doSwitch(eObject);
		} catch (IllegalArgumentException e) {
			return null;
		} finally {
			this.entity = oldEntity;
		}
	}

}
