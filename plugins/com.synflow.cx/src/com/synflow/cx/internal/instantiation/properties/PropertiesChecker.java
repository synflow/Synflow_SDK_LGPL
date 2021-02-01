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
package com.synflow.cx.internal.instantiation.properties;

import static com.synflow.models.ir.util.IrUtil.array;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * This class defines an abstract properties checker that is subclassed by concrete implementations
 * to check entities and instances respectively.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class PropertiesChecker {

	/**
	 * {clock: "name"} accepted as synonym for {clocks: ["name"]}
	 */
	protected static final String ABBR_CLOCK = "clock";

	/**
	 * {reset: {...}} accepted as synonym for {resets: [{...}]}
	 */
	protected static final String ABBR_RESET = "reset";

	protected final IJsonErrorHandler handler;

	protected PropertiesChecker(IJsonErrorHandler handler) {
		this.handler = handler;
	}

	/**
	 * If the given properties define {prop: ...}, and no "props" property, transform to {props:
	 * [...]}.
	 *
	 * @param properties
	 * @return the single value, or <code>null</code>
	 */
	protected final JsonElement applyShortcut(JsonObject properties, String single) {
		JsonElement value = properties.remove(single);
		if (value == null) {
			// no single value, return
			return null;
		}

		String plural = single + "s";
		if (properties.has(plural)) {
			// both "prop" and "props" exist, show error and ignore "prop"
			handler.addError(value,
					"\"" + single + "\" and \"" + plural + "\" are mutually exclusive");
			return null;
		}

		if (value.isJsonNull()) {
			// {prop: null} becomes {props: []}
			properties.add(plural, array());
		} else {
			// prop is valid, {prop: ...} becomes {props: [...]}
			properties.add(plural, array(value));
		}

		return value;
	}

	/**
	 * Check the "clocks" array.
	 *
	 * @param clocks
	 *            an array of clock names
	 * @return <code>true</code> if it is valid
	 */
	protected boolean checkClockArray(JsonElement clocks) {
		boolean isValid;
		if (clocks.isJsonArray()) {
			isValid = true;
			JsonArray clocksArray = clocks.getAsJsonArray();
			for (JsonElement clock : clocksArray) {
				if (!clock.isJsonPrimitive() || !clock.getAsJsonPrimitive().isString()) {
					isValid = false;
					break;
				}
			}
		} else {
			isValid = false;
		}

		if (!isValid) {
			handler.addError(clocks, "\"clocks\" must be an array of clock names");
		}
		return isValid;
	}

}
