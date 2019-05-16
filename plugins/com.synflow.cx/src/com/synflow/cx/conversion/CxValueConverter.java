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

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.impl.KeywordAlternativeConverter;
import org.eclipse.xtext.conversion.impl.QualifiedNameValueConverter;

import com.google.inject.Inject;

/**
 * Converts "true" and "false" to booleans, and hexadecimal to integer.
 */
public class CxValueConverter extends DefaultTerminalConverters {

	@Inject
	private BOOLValueConverter boolValueConverter;

	@Inject
	private CHARValueConverter charValueConverter;

	@Inject
	private BigDecimalValueConverter floatConverter;

	@Inject
	private QualifiedNameValueConverter fullyQualifiedNameConverter;

	@Inject
	private BigIntegerValueConverter integerConverter;

	@Inject
	private CxQualifiedNameValueConverter qualifiedNameValueConverter;

	@Inject
	private STRINGValueConverter stringValueConverter;

	@Inject
	private KeywordAlternativeConverter validIDConverter;

	@ValueConverter(rule = "BOOL")
	public IValueConverter<Boolean> getBoolConverter() {
		return boolValueConverter;
	}

	@ValueConverter(rule = "CHAR")
	public IValueConverter<BigInteger> getCharConverter() {
		return charValueConverter;
	}

	@ValueConverter(rule = "FLOAT")
	public IValueConverter<BigDecimal> getFloatConverter() {
		return floatConverter;
	}

	@ValueConverter(rule = "FullyQualifiedName")
	public IValueConverter<String> getFullyQualifiedNameConverter() {
		return fullyQualifiedNameConverter;
	}

	@ValueConverter(rule = "INTEGER")
	public IValueConverter<BigInteger> getIntegerConverter() {
		return integerConverter;
	}

	@ValueConverter(rule = "QualifiedName")
	public IValueConverter<String> getQualifiedNameValueConverter() {
		return qualifiedNameValueConverter;
	}

	@ValueConverter(rule = "ValidID")
	public IValueConverter<String> getValidIDConverter() {
		return validIDConverter;
	}

	@ValueConverter(rule = "STRING")
	public IValueConverter<String> STRING() {
		return stringValueConverter;
	}

}
