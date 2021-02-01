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

import static org.eclipse.xtext.EcoreUtil2.getContainerOfType;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synflow.cx.cx.CxPackage.Literals;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Instantiable;
import com.synflow.cx.cx.Obj;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.cx.internal.ErrorMarker;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Instance;

/**
 * This class defines a properties support class that checks and updates properties to a normal
 * form.
 *
 * @author Matthieu Wipliez
 *
 */
public class PropertiesService implements IJsonErrorHandler {

	/**
	 * the marker to use when no specific marker information is available
	 */
	private ErrorMarker defaultMarker;

	private List<ErrorMarker> errors;

	private final IInstantiator instantiator;

	private JsonMaker maker;

	public PropertiesService(IInstantiator instantiator) {
		this.instantiator = instantiator;
	}

	@Override
	public void addError(JsonElement element, String message) {
		ErrorMarker marker = maker.getMapping(element);
		if (marker == null) {
			marker = new ErrorMarker(message, defaultMarker);
		} else {
			marker = new ErrorMarker(message, marker);
		}

		errors.add(marker);
	}

	private JsonObject translateJson(Entity entity, EObject source, EStructuralFeature feature) {
		defaultMarker = new ErrorMarker(null, source, feature);
		errors = getContainerOfType(source, Instantiable.class).getErrors();
		maker = new JsonMaker(instantiator, entity);

		Obj obj = (Obj) source.eGet(feature);
		if (obj == null) {
			return new JsonObject();
		} else {
			return maker.toJson(obj);
		}
	}

	/**
	 * Translate properties of <code>inst</code> and set them to the given instance.
	 *
	 * @param inst
	 *            Cx instance
	 * @param instance
	 *            IR instance
	 */
	public void translateProperties(Inst inst, Instance instance) {
		Entity entity = instance.getEntity();
		JsonObject properties;
		if (inst.getTask() == null) {
			properties = translateJson(entity, inst, Literals.INST__ARGUMENTS);
		} else {
			// inner task, use the task's properties for the instance
			properties = translateJson(entity, inst.getTask(), Literals.CX_ENTITY__PROPERTIES);
		}

		instance.setProperties(properties);

		new InstancePropertiesChecker(this).checkProperties(instance);
	}

	/**
	 * Translate properties of <code>instantiable</code> and set them to the given IR entity.
	 *
	 * @param instantiable
	 *            Cx instantiable (task or network)
	 * @param entity
	 *            IR entity
	 */
	public void translateProperties(Instantiable instantiable, Entity entity) {
		JsonObject properties;
		properties = translateJson(entity, instantiable, Literals.CX_ENTITY__PROPERTIES);
		entity.setProperties(properties);

		new EntityPropertiesChecker(this).checkProperties(instantiable, properties);
	}

}
