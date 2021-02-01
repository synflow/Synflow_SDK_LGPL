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

/**
 * This class defines a string lexer.
 *
 * @author Matthieu Wipliez
 *
 */
public class StringLexer {

	private String buffer;

	private int index;

	public StringLexer(String buffer) {
		this.buffer = buffer;
		index = 1;
	}

	private int getEscapedNumber(int length, int radix) {
		int num = Integer.parseUnsignedInt(buffer.substring(index, index + length), radix);
		index += length;
		return num;
	}

	public int getNextChar() {
		char c = buffer.charAt(index);
		index++;
		if (c == '\\') {
			c = buffer.charAt(index);

			// first attempt octal sequence
			int length = 0;
			while (c >= '0' && c <= '7') {
				length++;
				if (hasMoreChars(index + length)) {
					c = buffer.charAt(index + length);
				} else {
					break;
				}
			}

			if (length > 0) {
				return getEscapedNumber(length, 8);
			}

			index++;
			switch (c) {
			case 'x':
				return getEscapedNumber(2, 16);
			case 'u':
				return getEscapedNumber(4, 16);
			case 'U':
				return getEscapedNumber(8, 16);

			case 'b':
				c = '\b';
				break;
			case 'f':
				c = '\f';
				break;
			case 'n':
				c = '\n';
				break;
			case 'r':
				c = '\r';
				break;
			case 't':
				c = '\t';
				break;
			}

			// default falls through for ', ", \, to end of method
		}

		return c;
	}

	public boolean hasMoreChars() {
		return hasMoreChars(index);
	}

	private boolean hasMoreChars(int index) {
		return index < buffer.length() - 1;
	}
}
