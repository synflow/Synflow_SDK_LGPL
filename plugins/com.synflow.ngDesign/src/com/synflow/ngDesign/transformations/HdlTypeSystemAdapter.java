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

import static java.math.BigInteger.ONE;

import java.math.BigInteger;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.synflow.models.ir.ExprInt;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.util.TypeUtil;

/**
 * This class extends the TypeSystemAdapter for HDLs.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class HdlTypeSystemAdapter extends TypeSystemAdapter {

	@Override
	public Expression caseExprInt(ExprInt expr) {
		// set the size of integer literals
		int targetSize = TypeUtil.getSize(getTarget());

		BigInteger value = expr.getValue();
		int size = TypeUtil.getSize(value);
		if (targetSize < size) {
			BigInteger mask = ONE.shiftLeft(targetSize).subtract(ONE);
			expr.setValue(getUnsigned(value, size).and(mask));
		} else {
			expr.setValue(getUnsigned(value, targetSize));
		}

		expr.setComputedType(EcoreUtil.copy(getTarget()));

		return expr;
	}

	private BigInteger getUnsigned(BigInteger value, int size) {
		if (value.signum() < 0) {
			return ONE.shiftLeft(size).add(value);
		} else {
			return value;
		}
	}

}
