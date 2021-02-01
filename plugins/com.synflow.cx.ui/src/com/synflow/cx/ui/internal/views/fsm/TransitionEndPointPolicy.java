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
package com.synflow.cx.ui.internal.views.fsm;

import static com.synflow.cx.ui.internal.views.ViewsConstants.SELECTION_COLOR;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Shape;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

/**
 * This class defines a connection endpoint edit policy that shows selected connections in a nice
 * color.
 *
 * @author Matthieu Wipliez
 */
public class TransitionEndPointPolicy extends ConnectionEndpointEditPolicy {

	@Override
	protected void addSelectionHandles() {
		Shape connection = (Shape) getConnection();
		connection.setLineWidth(3);
		connection.setForegroundColor(SELECTION_COLOR);
	}

	@Override
	protected void removeSelectionHandles() {
		Shape connection = (Shape) getConnection();
		connection.setLineWidth(1);
		connection.setForegroundColor(ColorConstants.black);
	}

}
