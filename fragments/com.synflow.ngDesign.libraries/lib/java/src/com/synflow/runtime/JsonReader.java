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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class JsonReader {

	private BufferedReader in;

	private String[] tokens;

	private int index;

	private boolean done;

	public JsonReader() {
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	public void next() {
		if (done) {
			return;
		}

		String line;
		try {
			line = in.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (line.isEmpty()) {
			done = true;
			return;
		}

		int start = line.indexOf('[') + 1;
		int end = line.indexOf(']');
		tokens = line.substring(start, end).split(",");
		index = 0;
	}

	public void readValue(Port port) {
		if (done) {
			return;
		}

		BigInteger value = new BigInteger(tokens[index].trim());
		if (port.getSize() <= 32) {
			port.intValue = value.intValue();
		} else if (port.getSize() <= 64) {
			port.longValue = value.longValue();
		} else {
			port.value = value;
		}
		index++;

		if (port.hasFlag(Port.VALID)) {
			port.valid = true;
		}
	}

}
