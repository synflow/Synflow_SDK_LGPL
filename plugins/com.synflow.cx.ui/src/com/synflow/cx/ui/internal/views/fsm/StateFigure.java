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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

/**
 * This class defines the figure for a state.
 *
 * @author Matthieu Wipliez
 *
 */
public class StateFigure extends Figure {

	private final Label label;

	private XYLayout layout;

	public StateFigure() {
		layout = new XYLayout();
		setLayoutManager(layout);

		setForegroundColor(ColorConstants.black);

		Ellipse ellipse = new Ellipse();
		ellipse.setAntialias(SWT.ON);
		ellipse.setBounds(new Rectangle(0, 0, 30, 30));
		ellipse.setBackgroundColor(ColorConstants.white);
		ellipse.setForegroundColor(ColorConstants.black);
		add(ellipse);

		label = new Label();
		label.setForegroundColor(ColorConstants.black);

		ellipse.setLayoutManager(new XYLayout());
		ellipse.add(label);
		ellipse.setConstraint(label, new Rectangle(0, 0, 30, 30));

		setConstraint(ellipse, new Rectangle(0, 0, 30, 30));
	}

	public void setLabel(String label) {
		int index = label.lastIndexOf('_');
		String text = label.substring(index + 1);
		this.label.setText(text);
	}

	public void setLayout(Rectangle rect) {
		getParent().setConstraint(this, rect);
	}

}
