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
package com.synflow.cx.internal.instantiation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.Element;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Null;
import com.synflow.cx.cx.Obj;
import com.synflow.cx.cx.Pair;
import com.synflow.cx.cx.Primitive;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.models.dpn.Instance;
import com.synflow.models.node.Node;

/**
 * This class defines an instantiation context as the path and properties obtained throughout the
 * hierarchy.
 *
 * @author Matthieu Wipliez
 *
 */
public class InstantiationContext extends Node {

	private static final Set<String> reserved = Sets.newHashSet("clock", "clocks", "reset");

	private static boolean isReservedProperty(String key) {
		return reserved.contains(key);
	}

	private final Inst inst;

	private final Instance instance;

	private final Map<String, CxExpression> properties;

	/**
	 * Creates a new instantiation context using the given parent context and the given name.
	 *
	 * @param parent
	 *            parent context
	 * @param name
	 *            name of an instance
	 */
	public InstantiationContext(IInstantiator instantiator, InstantiationContext parent, Inst inst,
			Instance instance) {
		super(parent, inst.getName());
		this.inst = inst;
		this.instance = instance;

		// first add properties from parent context
		properties = new LinkedHashMap<>();
		if (parent != null) {
			properties.putAll(parent.properties);
		}

		// then add inst's properties (may override parent's)
		Obj obj = inst.getArguments();
		if (obj != null) {
			for (Pair pair : obj.getMembers()) {
				String key = pair.getKey();
				if (isReservedProperty(key)) {
					continue;
				}
				Element element = pair.getValue();

				// only support primitive values for now
				if (element instanceof Primitive) {
					Primitive primitive = (Primitive) element;
					EObject value = primitive.getValue();
					if (value instanceof CxExpression) {
						Object val = instantiator.evaluate(instance.getDPN(), value);
						properties.put(key, Evaluator.getCxExpression(val));
					} else if (value instanceof Null) {
						properties.put(key, null);
					}
				}
			}
		}
	}

	/**
	 * Creates a new instantiation context using the given root name.
	 *
	 * @param name
	 *            name of the entity at the root of the hierarchy
	 */
	public InstantiationContext(String name) {
		super(name);
		properties = new LinkedHashMap<>();
		inst = null;
		instance = null;
	}

	public Inst getInst() {
		return inst;
	}

	public Instance getInstance() {
		return instance;
	}

	/**
	 * Returns the full name as an underscore-separated list of names.
	 *
	 * @return a string
	 */
	public String getName() {
		List<String> path = new ArrayList<>();
		Node node = this;
		do {
			path.add((String) node.getContent());
			node = node.getParent();
		} while (node != null);

		return Joiner.on('_').join(Lists.reverse(path));
	}

	/**
	 * Returns an unmodifiable map of the properties of this instantiation context.
	 *
	 * @return a map
	 */
	public Map<String, CxExpression> getProperties() {
		return Collections.unmodifiableMap(properties);
	}

}
