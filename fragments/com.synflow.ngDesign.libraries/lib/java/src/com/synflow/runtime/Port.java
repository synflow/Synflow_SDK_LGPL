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

package com.synflow.runtime;

import static java.math.BigInteger.ZERO;

import java.math.BigInteger;

/**
 * This class defines a port with flags and a value available as a float, an int, a long, a
 * BigInteger. A boolean port has size = 1 and uses an int value.
 *
 * @author Matthieu Wipliez
 *
 */
public class Port {

	public static final int BARE = 0;

	public static final int READY = 2;

	public static final int VALID = 1;

	public boolean ack;

	private final int flags;

	public float floatValue;

	public int intValue;

	public long longValue;

	public boolean ready;

	final String[] signals;

	private final int size;

	public boolean valid;

	public BigInteger value = ZERO;

	public Port(int flags, int size) {
		this.flags = flags;
		this.size = size;
		this.signals = new String[1 + Integer.bitCount(flags)];
	}

	/**
	 * Copy the other port's data to this port.
	 *
	 * @param other
	 *            another port, must have the same size
	 */
	public void copy(Port other) {
		if (size > 64) {
			value = other.value;
		} else if (size > 32) {
			longValue = other.longValue;
		} else {
			intValue = other.intValue;
		}
	}

	public int getSize() {
		return size;
	}

	String getVCDValue() {
		if (size > 64) {
			return "b" + value.toString(2);
		} else if (size > 32) {
			return "b" + Long.toBinaryString(longValue);
		} else if (size > 1) {
			return "b" + Integer.toBinaryString(intValue);
		} else {
			return intValue != 0 ? "1" : "0";
		}
	}

	/**
	 * Returns true if the port has the given flag.
	 *
	 * @param flag
	 * @return
	 */
	public boolean hasFlag(int flag) {
		return (flags & flag) != 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (size > 64) {
			builder.append(value);
		} else if (size > 32) {
			builder.append(longValue);
		} else {
			builder.append(intValue);
		}

		builder.append(" (ack: ");
		builder.append(ack);
		builder.append(", ready: ");
		builder.append(ready);
		builder.append(", valid: ");
		builder.append(valid);
		builder.append(")");

		return builder.toString();
	}

}
