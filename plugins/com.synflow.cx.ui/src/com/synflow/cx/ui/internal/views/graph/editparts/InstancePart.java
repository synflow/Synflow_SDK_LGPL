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
package com.synflow.cx.ui.internal.views.graph.editparts;

import static com.synflow.core.IProperties.PROP_CLOCKS;
import static org.eclipse.gef.EditPolicy.LAYOUT_ROLE;

import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.google.common.base.Function;
import com.synflow.cx.ui.internal.views.graph.LayoutEditPolicy;
import com.synflow.cx.ui.internal.views.graph.figures.InstanceFigure;
import com.synflow.models.dpn.Connection;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Direction;
import com.synflow.models.dpn.Endpoint;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Instance;
import com.synflow.models.dpn.Port;
import com.synflow.models.graph.Edge;

/**
 * This class defines the edit part for an instance.
 *
 * @author Matthieu Wipliez
 *
 */
public class InstancePart extends AbstractGraphicalEditPart implements NodeEditPart {

	private Map<Port, IFigure> ports;

	@Override
	protected void createEditPolicies() {
		installEditPolicy(LAYOUT_ROLE, new LayoutEditPolicy());
	}

	@Override
	protected IFigure createFigure() {
		final Instance instance = getInstance();

		Entity entity = instance.getEntity();
		boolean isDPN = entity instanceof DPN;
		boolean combinational = false;
		if (!entity.eIsProxy()) {
			combinational = entity.getProperties().getAsJsonArray(PROP_CLOCKS).size() == 0;
		}
		InstanceFigure figure = new InstanceFigure(isDPN, combinational, instance.getName());

		final DPN dpn = instance.getDPN();
		ports = figure.setPorts(new Function<Port, Boolean>() {
			@Override
			public Boolean apply(Port port) {
				if (port.getDirection() == Direction.INPUT) {
					Endpoint incoming = dpn.getIncoming(new Endpoint(instance, port));
					return incoming != null;
				} else {
					List<Endpoint> endpoints = dpn.getOutgoing(new Endpoint(instance, port));
					return !endpoints.isEmpty();
				}
			}
		}, entity.getInputs(), entity.getOutputs());
		return figure;
	}

	private ConnectionAnchor getAnchor(Port port) {
		if (port.eContainer() == null) {
			return null;
		}

		IFigure fig = ports.get(port);
		return new PortAnchor(fig, port.getDirection());
	}

	private Instance getInstance() {
		return (Instance) getModel();
	}

	@Override
	protected List<Edge> getModelSourceConnections() {
		return getInstance().getOutgoing();
	}

	@Override
	protected List<Edge> getModelTargetConnections() {
		return getInstance().getIncoming();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connPart) {
		Connection connection = (Connection) connPart.getModel();
		return getAnchor(connection.getSourcePort());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connPart) {
		Connection connection = (Connection) connPart.getModel();
		return getAnchor(connection.getTargetPort());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return null;
	}

	@Override
	public void performRequest(Request request) {
		Command command = getCommand(request);
		if (command != null && command.canExecute()) {
			command.execute();
		}
	}

}
