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
package com.synflow.cx.internal.instantiation;

import static com.synflow.cx.CxConstants.TYPE_READS;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;

import com.google.common.collect.Multimap;
import com.synflow.cx.cx.Connect;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Instance;
import com.synflow.models.dpn.Port;

/**
 * This class holds information about a connect statement.
 *
 * @author Matthieu Wipliez
 *
 */
public class ConnectionInfo implements Iterable<Port> {

	private final Instance instance;

	private final String name;

	private final Collection<Port> ports;

	public ConnectionInfo(IInstantiator instantiator, Multimap<EObject, Port> portMap, DPN dpn,
			Connect connect) {
		if (connect.isThis()) {
			instance = null;
		} else {
			instance = instantiator.getMapping(dpn, connect.getInstance());
		}

		name = instance == null ? "this" : instance.getName();
		ports = getPorts(portMap, dpn, connect.getType());
	}

	public Instance getInstance() {
		return instance;
	}

	public String getName() {
		return name;
	}

	public int getNumPorts() {
		return ports.size();
	}

	private Collection<Port> getPorts(Multimap<EObject, Port> portMap, DPN dpn, String type) {
		if (instance == null) {
			if (TYPE_READS.equals(type)) {
				return portMap.get(dpn);
			} else { // TYPE_WRITES
				return dpn.getInputs();
			}
		} else {
			Entity entity = instance.getEntity();
			if (TYPE_READS.equals(type)) {
				return portMap.get(instance);
			} else { // TYPE_WRITES
				return entity.getOutputs();
			}
		}
	}

	@Override
	public Iterator<Port> iterator() {
		return ports.iterator();
	}

	@Override
	public String toString() {
		return name;
	}

}
