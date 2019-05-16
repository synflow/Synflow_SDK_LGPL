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
package com.synflow.ngDesign.transformations;

import static com.synflow.models.ir.util.TypeUtil.getLargest;
import static com.synflow.models.ir.util.TypeUtil.getType;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.synflow.models.ir.ExprBinary;
import com.synflow.models.ir.ExprResize;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.transform.AbstractExpressionTransformer;
import com.synflow.models.ir.util.TypeUtil;

/**
 * This class defines common functionality to adapt the IR bit-accurate type system to target
 * languages using resize and type conversions. This class must be extended to handle behavior that
 * is specific to the target language (C, VHDL, Verilog) with
 * {@link #getExpressionType(OpBinary, Type, Type, Type)} and
 * {@link #getOperandType(OpBinary, Type, Type, int)}.
 *
 * <p>
 * Why? Because the type of an IR expression implicitly grows or shrinks as needed, which is not
 * true of most languages. This class (and subclasses) adds explicit resizes and type conversions.
 * </p>
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class TypeSystemAdapter extends AbstractExpressionTransformer {

	protected static final IrFactory ir = IrFactory.eINSTANCE;

	@Override
	public Expression caseExprBinary(ExprBinary expr) {
		OpBinary op = expr.getOp();
		Type t1 = getType(expr.getE1());
		Type t2 = getType(expr.getE2());

		Type type = getExpressionType(op, getType(expr), t1, t2);
		Type t1Cast = getOperandType(op, type, t1, 1);
		Type t2Cast = getOperandType(op, type, t2, 2);

		expr.setE1(transform(t1Cast, expr.getE1()));
		expr.setE2(transform(t2Cast, expr.getE2()));

		// store type for later use
		if (op.isComparison()) {
			expr.setComputedType(ir.createTypeBool());
		} else {
			expr.setComputedType(type);
		}

		return expr;
	}

	@Override
	public Expression caseExprResize(ExprResize resize) {
		Expression expr = resize.getExpr();
		if (expr.isExprInt()) {
			// special case for integer expressions
			return transform(getTarget(), expr);
		} else {
			Type type = getType(resize);
			Expression result = transform(type, expr);
			if (result.isExprResize()) {
				resize = (ExprResize) result;
			} else {
				resize.setExpr(result);
			}

			return resize;
		}
	}

	protected Expression cast(Type target, Type source, Expression expr) {
		return ir.cast(target, source, expr, false);
	}

	protected Type getExpressionType(OpBinary op, Type typeExpr, Type t1, Type t2) {
		if (op.isArithmetic() || op == OpBinary.SHIFT_LEFT) {
			return typeExpr;
		}
		return getLargest(t1, t2);
	}

	/**
	 * Returns the type to which the operand should be cast.
	 *
	 * @param op
	 *            binary operator
	 * @param typeExpr
	 *            expression type
	 * @param typeOper
	 *            operand type
	 * @param n
	 *            1 for first operand, 2 for second operand
	 * @return a type
	 */
	protected abstract Type getOperandType(OpBinary op, Type typeExpr, Type typeOper, int n);

	@Override
	protected Expression transform(Type target, Expression expr) {
		Expression result = super.transform(target, expr);
		if (expr.isExprList()) {
			return result;
		}

		if (result.getComputedType() == null) {
			result.setComputedType(EcoreUtil.copy(TypeUtil.getType(result)));
		}

		return cast(target, getType(result), result);
	}

}
