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

import static com.synflow.core.IProperties.ACTIVE_HIGH;
import static com.synflow.core.IProperties.ACTIVE_LOW;
import static com.synflow.core.IProperties.DEFAULT_CLOCK;
import static com.synflow.core.IProperties.PROP_ACTIVE;
import static com.synflow.core.IProperties.PROP_CLOCKS;
import static com.synflow.core.IProperties.PROP_NAME;
import static com.synflow.core.IProperties.PROP_RESETS;
import static com.synflow.core.IProperties.PROP_TEST;
import static com.synflow.core.IProperties.RESET_ASYNCHRONOUS;
import static com.synflow.core.IProperties.RESET_SYNCHRONOUS;
import static com.synflow.cx.CxConstants.PROP_TYPE;
import static com.synflow.cx.CxConstants.TYPE_COMBINATIONAL;
import static com.synflow.models.ir.util.IrUtil.array;
import static com.synflow.models.ir.util.IrUtil.obj;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Instantiable;
import com.synflow.cx.cx.Variable;

/**
 * This class defines a properties checker for an entity.
 *
 * @author Matthieu Wipliez
 *
 */
public class EntityPropertiesChecker extends PropertiesChecker {

	public EntityPropertiesChecker(IJsonErrorHandler handler) {
		super(handler);
	}

	private void checkClocksDeclared(JsonObject properties) {
		applyShortcut(properties, ABBR_CLOCK);

		// if there are no clocks, or if they are not valid, use default clock
		JsonElement clocks = properties.get(PROP_CLOCKS);
		if (clocks == null || !checkClockArray(clocks)) {
			properties.add(PROP_CLOCKS, array(DEFAULT_CLOCK));
		}
	}

	/**
	 * Checks the given properties of the <code>instantiable</code>.
	 *
	 * @param instantiable
	 *            a Cx instantiable
	 * @param properties
	 *            JSON properties
	 */
	public void checkProperties(Instantiable instantiable, JsonObject properties) {
		checkTest(instantiable, properties.get(PROP_TEST));

		JsonElement type = properties.get(PROP_TYPE);
		if (type != null) {
			if (type.isJsonPrimitive()) {
				JsonPrimitive entityType = properties.getAsJsonPrimitive(PROP_TYPE);
				if (TYPE_COMBINATIONAL.equals(entityType)) {
					// set an empty list of clocks and resets
					properties.add(PROP_CLOCKS, array());
					properties.add(PROP_RESETS, array());
					return;
				} else {
					handler.addError(entityType,
							"the only valid value of type is \"combinational\", ignored.");
				}
			} else {
				handler.addError(type, "type must be a string");
			}
		}

		checkClocksDeclared(properties);
		checkResetDeclared(properties);
	}

	/**
	 * Checks that the "resets" property is properly declared.
	 *
	 * @param properties
	 *            properties
	 */
	private void checkResetDeclared(JsonObject properties) {
		applyShortcut(properties, ABBR_RESET);

		// if there are no resets, or if they are not valid, use default clock
		JsonElement resets = properties.get(PROP_RESETS);
		if (resets == null || !resets.isJsonArray()) {
			resets = array(obj());
			properties.add(PROP_RESETS, resets);
		}

		for (JsonElement reset : resets.getAsJsonArray()) {
			if (reset.isJsonObject()) {
				JsonObject resetObj = reset.getAsJsonObject();

				JsonPrimitive type = resetObj.getAsJsonPrimitive(PROP_TYPE);
				if (type == null
						|| !RESET_ASYNCHRONOUS.equals(type) && !RESET_SYNCHRONOUS.equals(type)) {
					// default is asynchronous reset
					resetObj.add(PROP_TYPE, RESET_ASYNCHRONOUS);
				}

				JsonPrimitive active = resetObj.getAsJsonPrimitive(PROP_ACTIVE);
				if (active == null || !ACTIVE_HIGH.equals(active) && !ACTIVE_LOW.equals(active)) {
					// default is active low reset
					resetObj.add(PROP_ACTIVE, ACTIVE_LOW);
				}

				if (!resetObj.has(PROP_NAME)) {
					// compute default name
					if (ACTIVE_LOW.equals(resetObj.getAsJsonPrimitive(PROP_ACTIVE))) {
						resetObj.addProperty(PROP_NAME, "reset_n");
					} else {
						resetObj.addProperty(PROP_NAME, "reset");
					}
				}
			}
		}
	}

	/**
	 * Checks the "test" property. We use an instantiable because the entity is not translated when
	 * we do this check (in the skeleton maker).
	 *
	 * @param instantiable
	 *            instantiable
	 * @param test
	 *            test element
	 */
	private void checkTest(Instantiable instantiable, JsonElement test) {
		if (test == null) {
			return;
		}

		if (!test.isJsonObject()) {
			handler.addError(test, "test must be an object");
		}

		JsonObject objTest = test.getAsJsonObject();
		Set<String> ports = new HashSet<>();
		for (Variable port : CxUtil.getPorts(instantiable.getPortDecls())) {
			String name = port.getName();
			ports.add(name);

			if (!objTest.has(name)) {
				handler.addError(objTest, "missing test values for port \"" + name + "\"");
				continue;
			}

			JsonElement values = objTest.get(name);
			if (!values.isJsonArray()) {
				handler.addError(objTest, "test values for port \"" + name + "\" must be an array");
				continue;
			}
		}

		for (Entry<String, JsonElement> entry : objTest.entrySet()) {
			String name = entry.getKey();
			if (!ports.contains(name)) {
				handler.addError(objTest, "unknown port \"" + name + "\"");
			}
		}
	}

}
