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

import static com.synflow.core.IProperties.IMPL_BUILTIN;
import static com.synflow.core.IProperties.IMPL_EXTERNAL;
import static com.synflow.core.IProperties.PROP_TYPE;
import static com.synflow.models.ir.IrFactory.eINSTANCE;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.synflow.core.util.CoreUtil;
import com.synflow.models.dpn.Argument;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Instance;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.Var;

/**
 * This class specializes an entity based on the properties of an instance.
 *
 * @author Matthieu Wipliez
 *
 */
public class Specializer {

	private IJsonErrorHandler errorHandler;

	public Specializer(IJsonErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	private void addArguments(Entity entity, Instance instance) {
		JsonObject properties = instance.getProperties();
		for (Var variable : entity.getVariables()) {
			if (!variable.isAssignable()) {
				String name = variable.getName();
				JsonElement json = properties.get(name);
				Expression value = json == null ? variable.getInitialValue() : transformJson(json);
				if (value == null) {
					errorHandler.addError(json == null ? properties : json,
							"Instantiation: invalid value for constant '" + name + "'");
				} else {
					Argument argument = DpnFactory.eINSTANCE.createArgument(variable, value);
					instance.getArguments().add(argument);
				}
			}
		}
	}

	/**
	 * Returns an IR expression from the given JSON element.
	 *
	 * @param json
	 *            a JSON element (should be a primitive)
	 * @return an expression, or <code>null</code>
	 */
	public Expression transformJson(JsonElement json) {
		if (json.isJsonPrimitive()) {
			JsonPrimitive primitive = json.getAsJsonPrimitive();
			if (primitive.isBoolean()) {
				return eINSTANCE.createExprBool(primitive.getAsBoolean());
			} else if (primitive.isNumber()) {
				return eINSTANCE.createExprInt(primitive.getAsBigInteger());
			} else if (primitive.isString()) {
				return eINSTANCE.createExprString(primitive.getAsString());
			}
		}
		return null;
	}

	public void visitArguments(Instance instance) {
		Entity entity = instance.getEntity();
		JsonObject impl = CoreUtil.getImplementation(entity);
		if (impl != null) {
			JsonElement type = impl.get(PROP_TYPE);
			if (IMPL_BUILTIN.equals(type) || IMPL_EXTERNAL.equals(type)) {
				addArguments(entity, instance);
			}
		}
	}

}
