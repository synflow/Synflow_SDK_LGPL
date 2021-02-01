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

import java.util.IdentityHashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.synflow.cx.cx.Array;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.Element;
import com.synflow.cx.cx.Null;
import com.synflow.cx.cx.Obj;
import com.synflow.cx.cx.Pair;
import com.synflow.cx.cx.Primitive;
import com.synflow.cx.cx.util.CxSwitch;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.cx.internal.ErrorMarker;
import com.synflow.models.dpn.Entity;
import com.synflow.models.ir.util.ValueUtil;

/**
 * This class transforms our Javascript-like syntax to pure JSON.
 *
 * @author Matthieu Wipliez
 *
 */
public class JsonMaker extends CxSwitch<JsonElement> {

	private Entity entity;

	private IInstantiator instantiator;

	private Map<JsonElement, ErrorMarker> mapping;

	public JsonMaker(IInstantiator instantiator, Entity entity) {
		mapping = new IdentityHashMap<>();
		this.instantiator = instantiator;
		this.entity = entity;
	}

	@Override
	public JsonArray caseArray(Array array) {
		JsonArray jsonArray = new JsonArray();
		mapping.put(jsonArray, new ErrorMarker(array));
		for (Element element : array.getElements()) {
			jsonArray.add(doSwitch(element));
		}
		return jsonArray;
	}

	@Override
	public JsonPrimitive caseCxExpression(CxExpression expression) {
		Object value = instantiator.evaluate(entity, expression);
		JsonPrimitive primitive;
		if (ValueUtil.isBool(value)) {
			primitive = new JsonPrimitive((Boolean) value);
		} else if (ValueUtil.isFloat(value) || ValueUtil.isInt(value)) {
			primitive = new JsonPrimitive((Number) value);
		} else if (ValueUtil.isString(value)) {
			primitive = new JsonPrimitive((String) value);
		} else {
			return null;
		}

		mapping.put(primitive, new ErrorMarker(expression));
		return primitive;
	}

	@Override
	public JsonNull caseNull(Null null_) {
		return JsonNull.INSTANCE;
	}

	@Override
	public JsonObject caseObj(Obj obj) {
		JsonObject jsonObj = new JsonObject();
		mapping.put(jsonObj, new ErrorMarker(obj));
		for (Pair pair : obj.getMembers()) {
			Element value = pair.getValue();
			if (value != null) {
				String key = pair.getKey();
				jsonObj.add(key, doSwitch(value));
			}
		}
		return jsonObj;
	}

	@Override
	public JsonElement casePrimitive(Primitive primitive) {
		return doSwitch(primitive.getValue());
	}

	/**
	 * Returns the error marker that corresponds to the given JSON element.
	 *
	 * @param element
	 *            a JSON element
	 * @return an error marker
	 */
	public ErrorMarker getMapping(JsonElement element) {
		return mapping.get(element);
	}

	/**
	 * Transforms the given object to JSON.
	 *
	 * @param obj
	 *            an object
	 * @return a JSON object
	 */
	public JsonObject toJson(Obj obj) {
		return (JsonObject) doSwitch(obj);
	}

}
