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
package com.synflow.ngDesign.internal.generators.bytecode;

import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.Type;
import com.synflow.ngDesign.transformations.TypeSystemAdapter;

/**
 * This class defines an expression switch that modifies the IR to support Cx's type system in
 * bytecode.
 *
 * @author Matthieu Wipliez
 *
 */
public class BytecodeTypeSystemAdapter extends TypeSystemAdapter {

	@Override
	protected Type getOperandType(OpBinary op, Type typeExpr, Type typeOper, int n) {
		switch (op) {
		case SHIFT_LEFT:
		case SHIFT_RIGHT:
			// left operand must be cast to size of result
			// right operand must stay natural
			return n == 1 ? typeExpr : typeOper;
		default:
			return typeExpr;
		}
	}

}
