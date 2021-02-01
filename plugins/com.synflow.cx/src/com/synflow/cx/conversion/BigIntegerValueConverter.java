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
package com.synflow.cx.conversion;

import java.math.BigInteger;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

/**
 * This class defines a value converter for integer numbers.
 *
 * @author Matthieu Wipliez
 */
public class BigIntegerValueConverter extends AbstractLexerBasedConverter<BigInteger> {

	@Override
	protected void assertValidValue(BigInteger value) {
		super.assertValidValue(value);
		if (value.signum() == -1) {
			throw new ValueConverterException(
					getRuleName() + "-value may not be negative (value:" + value + ").", null,
					null);
		}
	}

	@Override
	protected String toEscapedString(BigInteger value) {
		return value.toString();
	}

	@Override
	public BigInteger toValue(String string, INode node) {
		if (Strings.isEmpty(string)) {
			throw new ValueConverterException("Couldn't convert empty string to int.", node, null);
		}

		try {
			// strip underscores if necessary
			string = string.replace("_", "");

			// compute radix
			int radix;
			int index = 0; // for octal and decimal numbers
			if (string.startsWith("0b")) {
				radix = 2;
				index = 2;
			} else if (string.startsWith("0x")) {
				radix = 16;
				index = 2;
			} else if (string.startsWith("0")) {
				radix = 8;
			} else {
				radix = 10;
			}

			return new BigInteger(string.substring(index), radix);
		} catch (NumberFormatException e) {
			throw new ValueConverterException(string + " is not a valid integer", node, e);
		}
	}

}
