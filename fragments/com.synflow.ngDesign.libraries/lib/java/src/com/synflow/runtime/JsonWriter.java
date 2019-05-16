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

 package com.synflow.runtime;

import java.util.ArrayList;
import java.util.List;

public class JsonWriter {

	private List<String> tokens;

	public JsonWriter() {
		tokens = new ArrayList<>();
	}

	public void flush() {
		System.out.println('[' + String.join(",", tokens) + ']');
		tokens.clear();
	}

	public void writeValue(Port port) {
		if (port.hasFlag(Port.VALID) && !port.valid) {
			tokens.add("null");
			return;
		}

		if (port.getSize() <= 32) {
			tokens.add(String.valueOf(port.intValue));
		} else if (port.getSize() <= 64) {
			tokens.add(String.valueOf(port.longValue));
		} else {
			tokens.add(String.valueOf(port.value));
		}
	}

}
