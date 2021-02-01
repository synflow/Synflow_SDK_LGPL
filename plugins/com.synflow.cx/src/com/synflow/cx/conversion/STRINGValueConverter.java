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

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

/**
 * This class defines a value converter for strings.
 *
 * @author Matthieu Wipliez
 */
public class STRINGValueConverter extends AbstractLexerBasedConverter<String> {

	@Override
	protected String toEscapedString(String value) {
		StringBuilder builder = new StringBuilder("\"");
		for (int i = 0; i < value.length(); i++) {
			CHARValueConverter.convertChar(builder, value.codePointAt(i));
		}
		builder.append('"');
		return builder.toString();
	}

	@Override
	public String toValue(String string, INode node) {
		if (Strings.isEmpty(string)) {
			throw new ValueConverterException("Couldn't convert empty string literal.", node, null);
		}

		StringLexer lexer = new StringLexer(string);
		StringBuilder builder = new StringBuilder();
		while (lexer.hasMoreChars()) {
			int c = lexer.getNextChar();
			if (Character.isValidCodePoint(c)) {
				builder.appendCodePoint(c);
			} else {
				// ignore for now
			}
		}
		return builder.toString();
	}

}
