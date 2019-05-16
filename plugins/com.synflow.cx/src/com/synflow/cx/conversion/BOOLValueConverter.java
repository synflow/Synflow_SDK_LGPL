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

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

/**
 * This class defines a value converter for BOOL rule.
 *
 * @author Matthieu Wipliez
 */
public class BOOLValueConverter extends AbstractLexerBasedConverter<Boolean> {

	public BOOLValueConverter() {
		super();
	}

	@Override
	protected String toEscapedString(Boolean value) {
		return value.toString();
	}

	@Override
	public Boolean toValue(String string, INode node) {
		if (Strings.isEmpty(string)) {
			throw new ValueConverterException(
					"Couldn't convert empty string to boolean", node, null);
		}

		return ("true".equals(string));
	}

	@Override
	public String toString(Boolean value) {
		return value.toString();
	}

}
