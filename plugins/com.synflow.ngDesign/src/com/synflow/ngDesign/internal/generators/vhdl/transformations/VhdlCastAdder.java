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
package com.synflow.ngDesign.internal.generators.vhdl.transformations;

import static com.synflow.models.ir.ExprTypeConv.SIGNED;
import static com.synflow.models.ir.ExprTypeConv.UNSIGNED;
import static com.synflow.models.ir.IrFactory.eINSTANCE;

import org.eclipse.emf.ecore.EObject;

import com.synflow.models.dpn.Port;
import com.synflow.models.ir.BlockIf;
import com.synflow.models.ir.BlockWhile;
import com.synflow.models.ir.ExprBinary;
import com.synflow.models.ir.ExprResize;
import com.synflow.models.ir.ExprTypeConv;
import com.synflow.models.ir.ExprUnary;
import com.synflow.models.ir.ExprVar;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstCall;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeInt;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.transform.AbstractExpressionTransformer;
import com.synflow.models.ir.util.TypeUtil;

/**
 * This class adds 'signed' or 'unsigned' around accesses from/to ports, and calls to
 * to_boolean/to_std_logic helper functions where necessary.
 *
 * @author Matthieu Wipliez
 *
 */
public class VhdlCastAdder extends AbstractExpressionTransformer {

	@Override
	public Expression caseExprBinary(ExprBinary expr) {
		Expression e1 = expr.getE1();
		Expression e2 = expr.getE2();
		if (e1.isExprInt() && e2.isExprInt()) {
			expr.setE1(eINSTANCE.convert(UNSIGNED, e1));
		}

		return caseExpression(super.caseExprBinary(expr));
	}

	@Override
	public Expression caseExpression(Expression expr) {
		if (!getTarget().isBool()) {
			return expr;
		}

		EObject cter = expr.eContainer();
		boolean booleanExpected = cter instanceof BlockIf || cter instanceof BlockWhile
				|| (cter instanceof InstCall && ((InstCall) cter).isAssert());
		if (expr instanceof ExprBinary) {
			ExprBinary exprBin = (ExprBinary) expr;
			if (exprBin.getOp().isComparison()) {
				if (booleanExpected) {
					return expr;
				}

				return eINSTANCE.convert("to_std_logic", expr);
			}
		}

		if (booleanExpected) {
			return eINSTANCE.convert("to_boolean", expr);
		}

		return expr;
	}

	@Override
	public Expression caseExprResize(ExprResize resize) {
		Type type = TypeUtil.getType(resize.getExpr());
		if (resize.getTargetSize() < TypeUtil.getSize(type)) {
			if (type.isInt() && ((TypeInt) type).isSigned()) {
				ExprTypeConv typeConv = eINSTANCE.convert(UNSIGNED, resize.getExpr());
				resize.setExpr(typeConv);
				return eINSTANCE.convert(SIGNED, super.caseExprResize(resize));
			}
		}
		return super.caseExprResize(resize);
	}

	@Override
	public Expression caseExprTypeConv(ExprTypeConv typeConv) {
		if (typeConv.getExpr().isExprInt()) {
			if (!typeConv.getTypeName().endsWith("'")) {
				typeConv.setTypeName(typeConv.getTypeName() + "'");
			}
		}

		return caseExpression(super.caseExprTypeConv(typeConv));
	}

	@Override
	public Expression caseExprUnary(ExprUnary exprUn) {
		Expression expr = exprUn.getExpr();
		if (expr.isExprInt()) {
			exprUn.setExpr(eINSTANCE.convert(UNSIGNED, expr));
		}

		return caseExpression(super.caseExprUnary(exprUn));
	}

	@Override
	public Expression caseExprVar(ExprVar expr) {
		Var variable = expr.getUse().getVariable();
		Type type = variable.getType();
		if (type.isInt()) {
			TypeInt typeInt = (TypeInt) type;
			if (variable instanceof Port) {
				String typeName = typeInt.isSigned() ? SIGNED : UNSIGNED;
				return IrFactory.eINSTANCE.convert(typeName, expr);
			}
		}
		return caseExpression(expr);
	}

}
