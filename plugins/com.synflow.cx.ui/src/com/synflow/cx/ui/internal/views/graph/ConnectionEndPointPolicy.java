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
package com.synflow.cx.ui.internal.views.graph;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

import com.synflow.cx.ui.internal.views.ViewsConstants;

/**
 * This class defines a connection endpoint edit policy that shows selected connections in color.
 *
 * @author Matthieu Wipliez
 */
public class ConnectionEndPointPolicy extends ConnectionEndpointEditPolicy {

	@Override
	protected void addSelectionHandles() {
		Connection connection = getConnection();
		setActive(connection.getSourceAnchor(), true);
		setActive(connection.getTargetAnchor(), true);
		setActive(connection, true);
	}

	@Override
	protected void removeSelectionHandles() {
		Connection connection = getConnection();
		setActive(connection.getSourceAnchor(), false);
		setActive(connection.getTargetAnchor(), false);
		setActive(connection, false);
	}

	private void setActive(ConnectionAnchor anchor, boolean active) {
		if (anchor != null) {
			setActive(anchor.getOwner(), active);
		}
	}

	/**
	 * If active is true, set the figure's foreground color to red; otherwise set it to black.
	 *
	 * @param figure
	 *            figure whose color should be changed
	 * @param active
	 *            active or not flag
	 */
	private void setActive(IFigure figure, boolean active) {
		figure.setForegroundColor(active ? ViewsConstants.SELECTION_COLOR : ColorConstants.black);
	}

}
