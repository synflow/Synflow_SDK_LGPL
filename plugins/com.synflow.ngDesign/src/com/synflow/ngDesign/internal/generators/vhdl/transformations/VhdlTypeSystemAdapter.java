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
package com.synflow.ngDesign.internal.generators.vhdl.transformations;

import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.Type;
import com.synflow.ngDesign.transformations.HdlTypeSystemAdapter;

/**
 * This class implements the VHDL type system adapter.
 *
 * @author Matthieu Wipliez
 *
 */
public class VhdlTypeSystemAdapter extends HdlTypeSystemAdapter {

	@Override
	protected Type getOperandType(OpBinary op, Type typeExpr, Type typeOper, int n) {
		switch (op) {
		case TIMES:
			// in VHDL a * b is typed as size(a) + size(b)
			// so we must not cast operands to the size of the result
			// instead we just transform operands using their own type as target
			return typeOper;
		default:
			return typeExpr;
		}
	}

}
