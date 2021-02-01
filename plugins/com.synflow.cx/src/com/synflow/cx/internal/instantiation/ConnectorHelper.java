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

import com.google.inject.Inject;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Named;
import com.synflow.cx.cx.VarRef;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Endpoint;
import com.synflow.models.dpn.Instance;
import com.synflow.models.dpn.Port;

/**
 * This class defines an helper class that creates Connections.
 *
 * @author Matthieu Wipliez
 *
 */
public class ConnectorHelper {

	@Inject
	private IInstantiator instantiator;

	/**
	 * Returns the endpoint associated with the port associated with the given reference.
	 *
	 * @param ref
	 *            reference to a port in another instance or in the containing network
	 * @return an endpoint
	 */
	public Endpoint getEndpoint(DPN dpn, VarRef ref) {
		Variable cxPort = ref.getVariable();
		Instance otherInst = getInstance(dpn, ref);
		if (otherInst == null) {
			Port otherPort = instantiator.getMapping(dpn, cxPort);
			if (otherPort == null) {
				// may happen if link refers to a non-existent port
				return null;
			}

			return new Endpoint(dpn, otherPort);
		} else {
			if (otherInst.getEntity() == null) {
				return null;
			}

			Port otherPort = instantiator.getMapping(otherInst.getEntity(), cxPort);
			if (otherPort == null) {
				// may happen if link refers to a non-existent port
				return null;
			}

			return new Endpoint(otherInst, otherPort);
		}
	}

	/**
	 * If the given port reference refers to a port in an instance, returns that instance.
	 * Otherwise, if the reference is that of a simple port (no instance), returns null.
	 *
	 * @param dpn
	 *            dpn
	 * @param ref
	 *            a port reference
	 * @return an instance
	 */
	public Instance getInstance(DPN dpn, VarRef ref) {
		Named named = ref.getObjects().get(0);
		if (named instanceof Inst) {
			Inst inst = (Inst) named;
			return instantiator.getMapping(dpn, inst);
		}

		return null;
	}

}
