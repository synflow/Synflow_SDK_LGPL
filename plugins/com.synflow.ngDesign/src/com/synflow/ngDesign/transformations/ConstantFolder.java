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
package com.synflow.ngDesign.transformations;

import com.synflow.models.ir.ExprBinary;
import com.synflow.models.ir.ExprBool;
import com.synflow.models.ir.ExprResize;
import com.synflow.models.ir.ExprUnary;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.OpUnary;
import com.synflow.models.ir.util.IrSwitch;

/**
 * This class defines a constant folder.
 *
 * @author Matthieu Wipliez
 *
 */
public class ConstantFolder extends IrSwitch<Expression> {

	public static boolean isFalse(Expression expr) {
		return expr.isExprBool() && !((ExprBool) expr).isValue();
	}

	public static boolean isTrue(Expression expr) {
		return expr.isExprBool() && ((ExprBool) expr).isValue();
	}

	@Override
	public Expression caseExprBinary(ExprBinary expr) {
		Expression e1 = doSwitch(expr.getE1());
		Expression e2 = doSwitch(expr.getE2());

		OpBinary op = expr.getOp();
		if (op == OpBinary.LOGIC_AND) {
			if (isFalse(e1) || isFalse(e2)) {
				return IrFactory.eINSTANCE.createExprBool(false);
			} else if (isTrue(e1)) {
				return e2;
			} else if (isTrue(e2)) {
				return e1;
			}
		}

		expr.setE1(e1);
		expr.setE2(e2);

		return expr;
	}

	@Override
	public Expression caseExpression(Expression expr) {
		return expr;
	}

	@Override
	public Expression caseExprResize(ExprResize expr) {
		Expression subExpr = doSwitch(expr.getExpr());
		expr.setExpr(subExpr);

		return expr;
	}

	@Override
	public Expression caseExprUnary(ExprUnary expr) {
		Expression subExpr = doSwitch(expr.getExpr());

		OpUnary op = expr.getOp();
		if (op == OpUnary.LOGIC_NOT) {
			if (isTrue(subExpr)) {
				return IrFactory.eINSTANCE.createExprBool(false);
			} else if (isFalse(subExpr)) {
				return IrFactory.eINSTANCE.createExprBool(true);
			}
		}

		expr.setExpr(subExpr);

		return expr;
	}

}
