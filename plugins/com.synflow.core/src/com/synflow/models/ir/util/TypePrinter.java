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

import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeArray;
import com.synflow.models.ir.TypeBool;
import com.synflow.models.ir.TypeFloat;
import com.synflow.models.ir.TypeInt;
import com.synflow.models.ir.TypeString;
import com.synflow.models.ir.TypeVoid;
import com.synflow.models.util.Void;

/**
 * This class defines a type printer for Cx-like types.
 *
 * @author Matthieu Wipliez
 *
 */
public class TypePrinter extends IrSwitch<Void> {

	private StringBuilder builder;

	@Override
	public Void caseTypeArray(TypeArray type) {
		doSwitch(type.getElementType());
		for (int dim : type.getDimensions()) {
			builder.append('[');
			builder.append(dim);
			builder.append(']');
		}
		return DONE;
	}

	@Override
	public Void caseTypeBool(TypeBool type) {
		builder.append("bool");
		return DONE;
	}

	@Override
	public Void caseTypeFloat(TypeFloat type) {
		builder.append("float");
		return DONE;
	}

	@Override
	public Void caseTypeInt(TypeInt type) {
		if (type.isSigned()) {
			builder.append('i');
		} else {
			builder.append('u');
		}

		int size = type.getSize();
		builder.append(size);

		return DONE;
	}

	@Override
	public Void caseTypeString(TypeString type) {
		builder.append("String");
		return DONE;
	}

	@Override
	public Void caseTypeVoid(TypeVoid type) {
		builder.append("void");
		return DONE;
	}

	public String toString(Type type) {
		builder = new StringBuilder();
		doSwitch(type);
		return builder.toString();
	}

}
