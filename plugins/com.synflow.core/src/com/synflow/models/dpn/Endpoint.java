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
 
package com.synflow.models.dpn;

import java.util.Objects;

import com.synflow.models.graph.Vertex;

/**
 * This class defines an endpoint.
 *
 * @author Matthieu Wipliez
 *
 */
public class Endpoint {

	private final Instance instance;

	private final Port port;

	public Endpoint(DPN dpn, Port port) {
		Objects.requireNonNull(dpn, "dpn must not be null in Endpoint");
		Objects.requireNonNull(port, "port must not be null in Endpoint");

		if (port.eContainer() != dpn) {
			throw new IllegalArgumentException("port must be contained in dpn");
		}

		this.instance = null;
		this.port = port;
	}

	public Endpoint(Instance instance, Port port) {
		Objects.requireNonNull(instance, "instance must not be null in Endpoint");
		Objects.requireNonNull(port, "port must not be null in Endpoint");

		this.instance = instance;
		this.port = port;
	}

	@Override
	public boolean equals(Object anObject) {
		if (!(anObject instanceof Endpoint)) {
			return false;
		}

		Endpoint endpoint = (Endpoint) anObject;
		return Objects.equals(instance, endpoint.instance) && Objects.equals(port, endpoint.port);
	}

	public Instance getInstance() {
		return instance;
	}

	public Port getPort() {
		return port;
	}

	public Vertex getVertex() {
		if (instance == null) {
			DPN dpn = (DPN) port.eContainer();
			return dpn.getVertex();
		} else {
			return instance;
		}
	}

	@Override
	public int hashCode() {
		if (instance == null) {
			return port.hashCode();
		}
		return instance.hashCode() ^ port.hashCode();
	}

	/**
	 * Equivalent to <code>getInstance() != null</code>
	 *
	 * @return a boolean
	 */
	public boolean hasInstance() {
		return instance != null;
	}

	@Override
	public String toString() {
		if (hasInstance()) {
			return instance.getName() + "." + port.getName();
		} else {
			return port.getName();
		}
	}

}
