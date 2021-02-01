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
package com.synflow.cx.internal.validation;

import static com.synflow.core.IProperties.DEFAULT_CLOCK;
import static com.synflow.core.IProperties.PROP_CLOCKS;
import static com.synflow.core.IProperties.PROP_DOMAINS;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.Connection;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Endpoint;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Instance;
import com.synflow.models.dpn.Port;
import com.synflow.models.dpn.util.DpnSwitch;

/**
 * This class defines a clock domain computer.
 *
 * @author Matthieu Wipliez
 *
 */
public class ClockDomainComputer extends DpnSwitch<JsonObject> {

	private static final String PORT_MAP = "__port_map";

	/**
	 * Associates candidates to the given ports in the given domains.
	 *
	 * @param domains
	 *            target clock domains
	 * @param port
	 *            a port
	 * @param candidates
	 *            set of candidates
	 */
	private void addCandidates(JsonObject domains, Port port, Set<String> candidates) {
		JsonArray array = new JsonArray();
		for (String candidate : candidates) {
			if (candidate != null) {
				// skips null clocks
				array.add(new JsonPrimitive(candidate));
			}
		}

		int size = array.size();
		if (size == 1) {
			domains.add(port.getName(), array.get(0));
		} else if (size > 1) {
			domains.add(port.getName(), array);
		}
	}

	@Override
	public JsonObject caseActor(Actor actor) {
		JsonObject properties = actor.getProperties();
		JsonObject domains = properties.getAsJsonObject(PROP_DOMAINS);
		if (properties.has(PORT_MAP)) {
			// if the flag is here, domains are available
			return domains;
		}

		if (domains == null) {
			// no domain, creates one
			domains = createPortMap(actor);
		} else {
			// domains exists, but not in port map format, convert them
			domains = convertToPortMap(domains);
		}

		// adds/updates domains to properties, and adds the "port map domains" flag
		properties.add(PROP_DOMAINS, domains);
		properties.add(PORT_MAP, null);

		return domains;
	}

	@Override
	public JsonObject caseDPN(DPN dpn) {
		JsonObject properties = dpn.getProperties();
		JsonObject domains = properties.getAsJsonObject(PROP_DOMAINS);
		if (domains != null) {
			return domains;
		}

		// creates domains and adds them to properties so next time they will just be reused
		domains = new JsonObject();
		properties.add(PROP_DOMAINS, domains);

		// compute domains for input ports
		for (Port port : dpn.getInputs()) {
			Set<String> candidates = new HashSet<>();
			for (Connection connection : dpn.getOutgoing(port)) {
				for (Endpoint endpoint : getClocked(dpn, connection.getTargetEndpoint())) {
					candidates.add(getClockDomain(endpoint));
				}
			}

			addCandidates(domains, port, candidates);
		}

		// compute domains for output ports
		for (Port port : dpn.getOutputs()) {
			Endpoint incoming = dpn.getIncoming(port);
			if (incoming != null) {
				Set<String> candidates = new HashSet<>();
				for (Endpoint endpoint : getClocked(dpn, incoming)) {
					candidates.add(getClockDomain(endpoint));
				}
				addCandidates(domains, port, candidates);
			}
		}

		return domains;
	}

	@Override
	public JsonObject caseInstance(Instance instance) {
		Entity entity = instance.getEntity();
		JsonObject domains = doSwitch(entity);

		// translate entity clocks to instance clocks
		JsonObject instDomains = new JsonObject();
		JsonElement properties = instance.getProperties().get(PROP_CLOCKS);
		if (!properties.isJsonObject()) {
			return instDomains;
		}

		JsonObject instClocks = properties.getAsJsonObject();
		for (Entry<String, JsonElement> domain : domains.entrySet()) {
			String port = domain.getKey();
			JsonElement value = domain.getValue();
			if (value.isJsonPrimitive()) {
				String clock = value.getAsString();
				JsonElement instClock = instClocks.get(clock);
				if (instClock != null) {
					instDomains.add(port, instClock);
				}
			}
		}

		return instDomains;
	}

	/**
	 * Returns a port-map version of the given clock domain definitions.
	 *
	 * @param domains
	 *            a clock to port array object
	 * @return a port map
	 */
	private JsonObject convertToPortMap(JsonObject domains) {
		// create a port map domain
		JsonObject portMap = new JsonObject();
		for (Entry<String, JsonElement> pair : domains.entrySet()) {
			String clock = pair.getKey();
			JsonArray ports = pair.getValue().getAsJsonArray();
			for (JsonElement port : ports) {
				portMap.addProperty(port.getAsString(), clock);
			}
		}

		return portMap;
	}

	/**
	 * Creates the simple port map for the given task.
	 *
	 * @param actor
	 *            an actor
	 * @return a new port map domains
	 */
	private JsonObject createPortMap(Actor actor) {
		JsonObject domains = new JsonObject();

		// single-clock and combinational tasks
		JsonArray clocks = actor.getProperties().getAsJsonArray(PROP_CLOCKS);
		String clock;
		if (clocks.size() == 0) {
			clock = DEFAULT_CLOCK.getAsString();
		} else {
			clock = clocks.get(0).getAsString();
		}

		Iterable<Port> ports = Iterables.concat(actor.getInputs(), actor.getOutputs());
		for (Port port : ports) {
			domains.addProperty(port.getName(), clock);
		}

		return domains;
	}

	/**
	 * Returns the clock domain of the given endpoint. The endpoint must be of an instance.
	 *
	 * @param endpoint
	 *            an endpoint
	 * @return a port-clock domains
	 */
	public String getClockDomain(Endpoint endpoint) {
		if (!endpoint.hasInstance()) {
			throw new IllegalArgumentException("expected endpoint of an instance");
		}

		JsonObject domains = doSwitch(endpoint.getInstance());
		JsonElement value = domains.get(endpoint.getPort().getName());
		if (value == null) {
			return null;
		}
		return value.getAsString();
	}

	/**
	 * If the given endpoint is clocked, returns it. Otherwise, iterate over the endpoint's incoming
	 * and outgoing edges until it finds a clocked instance.
	 *
	 * @param dpn
	 *            dpn
	 * @param endpoint
	 *            an endpoint
	 * @return an iterable over clocked endpoints
	 */
	private Iterable<Endpoint> getClocked(DPN dpn, Endpoint endpoint) {
		Instance instance = endpoint.getInstance();
		if (!isCombinational(instance)) {
			return ImmutableSet.of(endpoint);
		}

		Set<Endpoint> endpoints = new HashSet<>();
		Set<Instance> visited = new HashSet<>();
		visitEndpoint(visited, endpoints, dpn, endpoint);
		return endpoints;
	}

	/**
	 * Returns true if the entity referenced by this instance is combinational (has no clocks).
	 *
	 * @param instance
	 *            an instance
	 * @return a boolean
	 */
	public boolean isCombinational(Instance instance) {
		JsonObject properties = instance.getEntity().getProperties();
		JsonElement clocks = properties.get(PROP_CLOCKS);
		return clocks.isJsonArray() && clocks.getAsJsonArray().size() == 0;
	}

	/**
	 * Visits the given endpoint. If the given endpoint's instance is clocked, adds it to the
	 * endpoints set and returns. Otherwise, recursively visit the endpoint's incoming and outgoing
	 * edges.
	 *
	 * @param visited
	 *            the set of instances already visited
	 * @param endpoints
	 *            the set of endpoints found so far
	 * @param dpn
	 *            dpn
	 * @param endpoint
	 *            an endpoint
	 */
	private void visitEndpoint(Set<Instance> visited, Set<Endpoint> endpoints, DPN dpn,
			Endpoint endpoint) {
		Instance instance = endpoint.getInstance();
		if (visited.contains(instance)) {
			return;
		}

		// remember that we visited this instance
		visited.add(instance);

		// if this instance is clocked, adds the endpoint and returns
		if (!isCombinational(instance)) {
			endpoints.add(endpoint);
			return;
		}

		// visit incoming
		for (Connection connection : dpn.getIncoming(instance)) {
			Endpoint target = connection.getSourceEndpoint();
			if (target.hasInstance()) {
				visitEndpoint(visited, endpoints, dpn, target);
			}
		}

		// visit outgoing
		for (Connection connection : dpn.getOutgoing(instance)) {
			Endpoint target = connection.getTargetEndpoint();
			if (target.hasInstance()) {
				visitEndpoint(visited, endpoints, dpn, target);
			}
		}
	}

}
