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
package com.synflow.cx.internal.instantiation.properties;

import static com.synflow.core.IProperties.PROP_CLOCKS;
import static com.synflow.core.IProperties.PROP_NAME;
import static com.synflow.core.IProperties.PROP_RESETS;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.function.Function;

import com.google.common.collect.Iterables;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Instance;

/**
 * This class defines a properties checker for an instance.
 *
 * @author Matthieu Wipliez
 *
 */
public class InstancePropertiesChecker extends PropertiesChecker {

	/**
	 * the NO_CLOCK string is not a valid identifier, only used internally
	 */
	private static final String NO_CLOCK = "<no clock>";

	public InstancePropertiesChecker(IJsonErrorHandler handler) {
		super(handler);
	}

	/**
	 * This method returns an object that associates the given key array with the given value array.
	 *
	 * @param keys
	 *            array of keys
	 * @param values
	 *            array of values
	 * @return an object associating keys with values
	 */
	private JsonObject associate(JsonArray keys, JsonArray values,
			Function<JsonElement, String> keyName, Function<JsonElement, JsonElement> valueName) {
		JsonObject object = new JsonObject();
		if (keys.size() == 0) {
			// when the entity declares no keys (no clocks => combinational)
			// we associate no values
			return object;
		}

		Iterator<JsonElement> it = values.iterator();
		for (JsonElement key : keys) {
			String keyString = keyName.apply(key);
			if (it.hasNext()) {
				object.add(keyString, valueName.apply(it.next()));
			} else {
				if (values.size() == 1) {
					// support repetition of one value
					object.add(keyString, valueName.apply(values.get(0)));
				} else {
					// not enough values given
					// this is verified and caught (by validateClocks for clocks)
					// with a !it.hasNext() test.
					break;
				}
			}
		}

		while (it.hasNext()) {
			// too many values given
			object.add(keyName.apply(null), valueName.apply(it.next()));
		}

		return object;
	}

	/**
	 * Checks properties of <code>instance</code>.
	 *
	 * @param instance
	 *            IR instance
	 */
	public void checkProperties(Instance instance) {
		Specializer specializer = new Specializer(handler);
		specializer.visitArguments(instance);

		updateClocksInstantiated(instance);
		updateResets(instance);
	}

	/**
	 * Checks and transforms the clocks in the properties of the given instance. Clocks accepts an
	 * array and an object. If clocks is an array, this method transforms it to an object and checks
	 * it.
	 *
	 * @param instance
	 */
	private void updateClocksInstantiated(Instance instance) {
		DPN dpn = instance.getDPN();
		JsonArray parentClocks = dpn.getProperties().getAsJsonArray(PROP_CLOCKS);

		Entity entity = instance.getEntity();
		JsonArray entityClocks = entity.getProperties().getAsJsonArray(PROP_CLOCKS);

		// use {clock: "name"} as a shortcut for {clocks: ["name"]}
		JsonObject properties = instance.getProperties();
		applyShortcut(properties, ABBR_CLOCK);

		JsonElement clocks = properties.get(PROP_CLOCKS);
		if (clocks == null || clocks.isJsonArray()) {
			JsonArray referenceClocks;
			if (clocks == null) {
				referenceClocks = parentClocks;
			} else if (checkClockArray(clocks)) {
				referenceClocks = clocks.getAsJsonArray();
			} else {
				// do not associate clocks
				return;
			}

			clocks = associate(entityClocks, referenceClocks,
					elt -> elt == null ? NO_CLOCK : elt.getAsString(), elt -> elt);
			properties.add(PROP_CLOCKS, clocks);
		} else if (!clocks.isJsonObject()) {
			// do not check clocks
			handler.addError(clocks, "clocks must be an array or an object");
			return;

		}

		validateClocks(clocks.getAsJsonObject(), parentClocks, entityClocks);
	}

	private void updateResets(Instance instance) {
		DPN dpn = instance.getDPN();
		JsonArray parentResets = dpn.getProperties().getAsJsonArray(PROP_RESETS);

		JsonArray entityResets = instance.getEntity().getProperties().getAsJsonArray(PROP_RESETS);

		// use {reset: {"x": "y"}} as a shortcut for {resets: [{"x": "y"}]}
		JsonObject properties = instance.getProperties();
		applyShortcut(properties, ABBR_RESET);

		JsonElement resets = properties.get(PROP_RESETS);
		if (resets == null || resets.isJsonArray()) {
			JsonArray referenceResets = resets == null ? parentResets : resets.getAsJsonArray();
			resets = associate(entityResets, referenceResets,
					elt -> elt == null ? "no reset"
							: elt.getAsJsonObject().get(PROP_NAME).getAsString(),
					elt -> elt.getAsJsonObject().get(PROP_NAME));
		} else if (!resets.isJsonObject()) {
			handler.addError(resets, "resets must be an array or an object");
			return;
		}

		properties.add(PROP_RESETS, resets);
	}

	/**
	 * Validates the given clocks object.
	 *
	 * @param clocks
	 *            a clocks object
	 * @param parentClocks
	 *            a list of parent clocks
	 * @param entityClocks
	 *            a list of entity clocks
	 */
	private void validateClocks(JsonObject clocks, JsonArray parentClocks, JsonArray entityClocks) {
		int size = entityClocks.size();
		int got = 0;

		Iterator<JsonElement> it = entityClocks.iterator();
		for (Entry<String, JsonElement> pair : clocks.entrySet()) {
			String clockName = pair.getKey();
			if (NO_CLOCK.equals(clockName)) {
				// no more clocks after this one => mismatch in number of clocks
				got = clocks.entrySet().size();
				break;
			}

			if (!it.hasNext()) {
				// no more entity clocks => mismatch in number of clocks
				break;
			}

			// check we use a valid entity clock name
			String expected = it.next().getAsString();
			if (!clockName.equals(expected)) {
				handler.addError(clocks, "given clock name \"" + clockName
						+ "\" does not match entity's clock \"" + expected + "\"");
			}

			// check value
			JsonElement value = pair.getValue();
			if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
				got++;
				if (!Iterables.contains(parentClocks, value)) {
					handler.addError(value, "given clock name \"" + value.getAsString()
							+ "\" does not appear in parent's clocks " + parentClocks);
				}
			} else {
				handler.addError(value, "invalid clock value: " + value.toString());
			}
		}

		if (got < size) {
			String msg = "not enough clocks given, expected " + size + " clocks, got " + got;
			handler.addError(clocks, msg);
		} else if (got > size) {
			String msg = "too many clocks given, expected " + size + " clocks, got " + got;
			handler.addError(clocks, msg);
		}
	}

}
