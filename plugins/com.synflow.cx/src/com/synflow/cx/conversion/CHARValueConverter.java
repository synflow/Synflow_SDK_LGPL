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
package com.synflow.cx.conversion;

import java.math.BigInteger;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

/**
 * This class defines a value converter for characters.
 *
 * @author Matthieu Wipliez
 */
public class CHARValueConverter extends AbstractLexerBasedConverter<BigInteger> {

	private static void appendZeroes(StringBuilder builder, int actual, int expected) {
		int remaining = expected - actual;
		while (remaining > 0) {
			builder.append('0');
			remaining--;
		}
	}

	public static void convertChar(StringBuilder builder, int value) {
		switch (value) {
		case '\'':
			builder.append("\\'");
			break;
		case '\"':
			// not necessary for char, but this method may be used by strings too
			builder.append("\\\"");
			break;
		case '\\':
			builder.append("\\\\");
			break;
		case '\b':
			builder.append("\\b");
			break;
		case '\f':
			builder.append("\\f");
			break;
		case '\n':
			builder.append("\\n");
			break;
		case '\r':
			builder.append("\\r");
			break;
		case '\t':
			builder.append("\\t");
			break;
		default:
			if (value == 0) {
				builder.append("\\0");
			} else if (value > 31 && value < 127) {
				// printable ASCII
				builder.append((char) value);
			} else {
				String str = Integer.toUnsignedString(value, 16);
				int size = str.length();
				if (size <= 2) {
					builder.append("\\x");
					appendZeroes(builder, size, 2);
				} else if (size <= 4) {
					builder.append("\\u");
					appendZeroes(builder, size, 4);
				} else if (size <= 8) {
					builder.append("\\U");
					appendZeroes(builder, size, 8);
				}
				builder.append(str);
			}
		}
	}

	@Override
	protected void assertValidValue(BigInteger value) {
		super.assertValidValue(value);
		if (value.signum() == -1) {
			throw new ValueConverterException(getRuleName() + "-value may not be negative (value:"
					+ value + ").", null, null);
		}
	}

	@Override
	protected String toEscapedString(BigInteger value) {
		StringBuilder builder = new StringBuilder("'");
		int intValue = value.intValue();
		convertChar(builder, intValue);
		builder.append('\'');
		return builder.toString();
	}

	@Override
	public BigInteger toValue(String string, INode node) {
		if (Strings.isEmpty(string)) {
			throw new ValueConverterException("Couldn't convert empty char literal to int.", node,
					null);
		}

		StringLexer lexer = new StringLexer(string);
		int value = lexer.getNextChar();
		return BigInteger.valueOf(value);
	}

}
