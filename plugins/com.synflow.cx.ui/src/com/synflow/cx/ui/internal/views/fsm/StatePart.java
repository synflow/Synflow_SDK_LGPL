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
package com.synflow.cx.ui.internal.views.fsm;

import static org.eclipse.gef.EditPolicy.LAYOUT_ROLE;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.synflow.models.dpn.State;
import com.synflow.models.graph.Edge;

/**
 * This class defines the edit part for a state.
 *
 * @author Matthieu Wipliez
 *
 */
public class StatePart extends AbstractGraphicalEditPart {

	@Override
	protected void createEditPolicies() {
		installEditPolicy(LAYOUT_ROLE, new LayoutEditPolicy());
	}

	@Override
	protected IFigure createFigure() {
		return new StateFigure();
	}

	@Override
	protected List<Edge> getModelSourceConnections() {
		State state = (State) getModel();
		return state.getOutgoing();
	}

	@Override
	protected List<Edge> getModelTargetConnections() {
		State state = (State) getModel();
		return state.getIncoming();
	}

	@Override
	protected void refreshVisuals() {
		StateFigure figure = (StateFigure) getFigure();
		State state = (State) getModel();
		Point location = state.get("location");

		figure.setLabel(state.getName());

		figure.setLayout(new Rectangle(location.x, location.y, -1, -1));
	}

}
