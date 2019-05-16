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
package com.synflow.cx.ui.internal.views;

import static com.synflow.cx.ui.internal.views.ViewsConstants.SELECTION_COLOR;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.swt.graphics.Color;

/**
 * This class defines a common selection edit policy for FSM and Graph views.
 *
 * @author Matthieu Wipliez
 *
 */
public class CommonSelectionEditPolicy extends NonResizableEditPolicy {

	public CommonSelectionEditPolicy() {
		setDragAllowed(false);
	}

	@Override
	protected void hideSelection() {
		IFigure figure = ((GraphicalEditPart) getHost()).getFigure();
		setColor(figure, ColorConstants.black);
	}

	private void setColor(IFigure figure, Color color) {
		figure.setForegroundColor(color);
		for (Object child : figure.getChildren()) {
			setColor((IFigure) child, color);
		}
	}

	@Override
	protected void showSelection() {
		IFigure figure = ((GraphicalEditPart) getHost()).getFigure();
		setColor(figure, SELECTION_COLOR);
	}

}
