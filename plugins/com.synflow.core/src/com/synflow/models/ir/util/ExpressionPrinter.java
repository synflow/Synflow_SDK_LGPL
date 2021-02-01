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
 
package com.synflow.models.ir.util;

import static com.synflow.models.util.SwitchUtil.DONE;

import java.util.Iterator;

import com.synflow.models.ir.ExprBinary;
import com.synflow.models.ir.ExprBool;
import com.synflow.models.ir.ExprFloat;
import com.synflow.models.ir.ExprInt;
import com.synflow.models.ir.ExprList;
import com.synflow.models.ir.ExprResize;
import com.synflow.models.ir.ExprString;
import com.synflow.models.ir.ExprTypeConv;
import com.synflow.models.ir.ExprUnary;
import com.synflow.models.ir.ExprVar;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.util.Void;

/**
 * This class defines the default expression printer.
 *
 * @author Matthieu Wipliez
 *
 */
public class ExpressionPrinter extends IrSwitch<Void> {

	protected int branch;

	private StringBuilder builder;

	protected int precedence;

	private int radix;

	/**
	 * Creates a new expression printer.
	 */
	public ExpressionPrinter() {
		this(new StringBuilder());
	}

	/**
	 * Creates a new expression printer.
	 */
	public ExpressionPrinter(StringBuilder builder) {
		branch = 0; // left
		precedence = Integer.MAX_VALUE;
		radix = 10;
		this.builder = builder;
	}

	@Override
	public Void caseExprBinary(ExprBinary expr) {
		OpBinary op = expr.getOp();
		boolean needsParen = op.needsParentheses(precedence, branch);
		if (needsParen) {
			builder.append('(');
		}

		doSwitch(expr.getE1(), op.getPrecedence(), 0);
		builder.append(' ');
		builder.append(op.getText());
		builder.append(' ');
		doSwitch(expr.getE2(), op.getPrecedence(), 1);

		if (needsParen) {
			builder.append(')');
		}

		return DONE;
	}

	@Override
	public Void caseExprBool(ExprBool expr) {
		builder.append(expr.isValue());
		return DONE;
	}

	@Override
	public Void caseExprFloat(ExprFloat expr) {
		builder.append(expr.getValue());
		return DONE;
	}

	@Override
	public Void caseExprInt(ExprInt expr) {
		if (radix == 16) {
			builder.append("0x");
		}
		builder.append(expr.getValue().toString(radix));
		return DONE;
	}

	@Override
	public Void caseExprList(ExprList expr) {
		builder.append('{');

		Iterator<Expression> it = expr.getValue().iterator();
		if (it.hasNext()) {
			builder.append(doSwitch(it.next()));
			while (it.hasNext()) {
				builder.append(", ");
				doSwitch(it.next(), Integer.MAX_VALUE, 0);
			}
		}

		builder.append('}');
		return DONE;
	}

	@Override
	public Void caseExprResize(ExprResize expr) {
		builder.append("resize(");
		doSwitch(expr.getExpr());
		builder.append(", ");
		builder.append(expr.getTargetSize());
		builder.append(')');
		return DONE;
	}

	@Override
	public Void caseExprString(ExprString expr) {
		// note the difference with the caseExprString method from the
		// expression evaluator: quotes around the string
		builder.append('"');
		builder.append(expr.getValue());
		builder.append('"');
		return DONE;
	}

	@Override
	public Void caseExprTypeConv(ExprTypeConv expr) {
		builder.append(expr.getTypeName());
		builder.append('(');
		doSwitch(expr.getExpr());
		builder.append(')');
		return DONE;
	}

	@Override
	public Void caseExprUnary(ExprUnary expr) {
		builder.append(expr.getOp().getText());
		doSwitch(expr.getExpr(), Integer.MIN_VALUE, branch);
		return DONE;
	}

	@Override
	public Void caseExprVar(ExprVar expr) {
		builder.append(expr.getUse().getVariable().getName());
		return DONE;
	}

	public void doSwitch(Expression expression, int newPrecedence, int newBranch) {
		int oldBranch = branch;
		int oldPrecedence = precedence;

		branch = newBranch;
		precedence = newPrecedence;
		doSwitch(expression);
		precedence = oldPrecedence;
		branch = oldBranch;
	}

	public String toString(Expression expr) {
		doSwitch(expr);
		return builder.toString();
	}

}
