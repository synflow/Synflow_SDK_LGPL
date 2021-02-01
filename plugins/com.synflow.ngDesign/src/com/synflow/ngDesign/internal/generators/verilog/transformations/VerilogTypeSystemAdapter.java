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
package com.synflow.ngDesign.internal.generators.verilog.transformations;

import com.synflow.models.ir.ExprBinary;
import com.synflow.models.ir.ExprUnary;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.OpUnary;
import com.synflow.models.ir.Type;
import com.synflow.ngDesign.transformations.HdlTypeSystemAdapter;

/**
 * This class defines an expression switch that modifies the IR to support Cx's type system in
 * Verilog.
 *
 * @author Matthieu Wipliez
 *
 */
public class VerilogTypeSystemAdapter extends HdlTypeSystemAdapter {

	@Override
	public Expression caseExprUnary(ExprUnary exprUn) {
		Expression expr = exprUn.getExpr();
		if (exprUn.getOp() == OpUnary.MINUS) {
			ExprBinary exprBin = ir.createExprBinary(ir.createExprInt(0), OpBinary.MINUS, expr);
			return transform(getTarget(), exprBin);
		}

		return super.caseExprUnary(exprUn);
	}

	@Override
	protected Type getOperandType(OpBinary op, Type typeExpr, Type typeOper, int n) {
		switch (op) {
		case SHIFT_LEFT:
			// we implement the left shift as a bit concatenation
			// so we must not cast operands to the size of the result
			// instead we just cast them to their respective type
			return typeOper;
		default:
			return typeExpr;
		}
	}

}
