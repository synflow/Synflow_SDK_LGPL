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
package com.synflow.cx.ui.internal.views.graph.editparts;

import static org.eclipse.gef.EditPolicy.LAYOUT_ROLE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.synflow.cx.ui.internal.views.graph.LayoutEditPolicy;
import com.synflow.cx.ui.internal.views.graph.figures.PortFigure;
import com.synflow.models.dpn.Connection;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Direction;
import com.synflow.models.dpn.Endpoint;
import com.synflow.models.dpn.Port;

/**
 * This class defines the edit part for a port.
 *
 * @author Matthieu Wipliez
 *
 */
public class PortPart extends AbstractGraphicalEditPart implements NodeEditPart {

	@Override
	protected void createEditPolicies() {
		installEditPolicy(LAYOUT_ROLE, new LayoutEditPolicy());
	}

	@Override
	protected IFigure createFigure() {
		PortFigure figure = new PortFigure();
		Port port = getModel();
		figure.setLabel(port.getName());
		return figure;
	}

	@Override
	public final Port getModel() {
		return (Port) super.getModel();
	}

	@Override
	protected List<Connection> getModelSourceConnections() {
		Port port = getModel();
		DPN dpn = (DPN) port.eContainer();
		return dpn.getOutgoing(port);
	}

	@Override
	protected List<Connection> getModelTargetConnections() {
		Port port = getModel();
		DPN dpn = (DPN) port.eContainer();
		Connection connection = dpn.getConnection(new Endpoint(dpn, port));
		if (connection == null) {
			return Collections.emptyList();
		}
		return Arrays.asList(connection);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return new PortAnchor(getFigure(), Direction.OUTPUT);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new PortAnchor(getFigure(), Direction.OUTPUT);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return new PortAnchor(getFigure(), Direction.INPUT);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new PortAnchor(getFigure(), Direction.INPUT);
	}

}
