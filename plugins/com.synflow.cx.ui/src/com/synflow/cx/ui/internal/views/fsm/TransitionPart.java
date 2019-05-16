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

import static org.eclipse.gef.EditPolicy.CONNECTION_ENDPOINTS_ROLE;
import static org.eclipse.gef.EditPolicy.LAYOUT_ROLE;

import java.util.List;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;

import com.synflow.models.dpn.Transition;

/**
 * This class defines the edit part for a transition.
 *
 * @author Matthieu Wipliez
 *
 */
public class TransitionPart extends AbstractConnectionEditPart {

	@Override
	protected void createEditPolicies() {
		installEditPolicy(LAYOUT_ROLE, new LayoutEditPolicy());
		installEditPolicy(CONNECTION_ENDPOINTS_ROLE,
				new TransitionEndPointPolicy());
	}

	@Override
	protected IFigure createFigure() {
		Transition transition = (Transition) getModel();
		if (transition.getSource() == transition.getTarget()) {
			return new TransitionLoopFigure();
		} else {
			TransitionFigure figure = new TransitionFigure();

			List<Bendpoint> bendpoints;
			bendpoints = transition.get("bendpoints");
			if (bendpoints != null) {
				ConnectionLayer connLayer = (ConnectionLayer) getLayer(CONNECTION_LAYER);
				ConnectionRouter router = connLayer.getConnectionRouter();
				router.setConstraint(figure, bendpoints);
				figure.setConnectionRouter(router);
			}

			return figure;
		}
	}

}
