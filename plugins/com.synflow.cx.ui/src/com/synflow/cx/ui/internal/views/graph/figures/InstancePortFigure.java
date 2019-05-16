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
package com.synflow.cx.ui.internal.views.graph.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

import com.synflow.models.dpn.Direction;
import com.synflow.models.dpn.Port;

/**
 * This class defines the figure for a port that is part of an instance figure.
 *
 * @author Matthieu Wipliez
 *
 */
public class InstancePortFigure extends Figure {

	private boolean isInput;

	private Label label;

	private Polyline line;

	private RectangleFigure rectangle;

	public InstancePortFigure(Port port, boolean isConnected) {
		setLayoutManager(new XYLayout());

		setForegroundColor(ColorConstants.black);

		label = new Label(port.getName());
		label.setBackgroundColor(ColorConstants.red);
		label.setLabelAlignment(PositionConstants.LEFT);
		add(label);

		if (isConnected) {
			line = new Polyline();
			if (!port.getType().isBool()) {
				line.setLineWidth(3);
			}
			add(line);

			rectangle = new RectangleFigure();
			rectangle.setFill(true);
			rectangle.setBackgroundColor(ColorConstants.black);
			add(rectangle);
		}

		isInput = port.getDirection() == Direction.INPUT;
		if (isInput) {
			setConstraint(label, new Rectangle(15, 0, -1, -1));
		} else {
			setConstraint(label, new Rectangle(0, 0, -1, -1));
		}
	}

	/**
	 * Computes the preferred size of this instance figure.
	 *
	 * @return the size
	 */
	private Dimension computePreferredSize() {
		Font font = getFont();
		FontData[] fontData = font.getFontData();
		for (FontData data : fontData) {
			data.setHeight(7);
		}

		Font smallFont = new Font(font.getDevice(), fontData);
		label.setFont(smallFont);

		return label.getPreferredSize();
	}

	public Label getLabel() {
		return label;
	}

	public Dimension getLabelPreferredSize() {
		return computePreferredSize();
	}

	@Override
	public Dimension getPreferredSize(int wHint, int hHint) {
		if (prefSize == null) {
			Dimension labelPrefSize = computePreferredSize();
			prefSize = new Dimension(labelPrefSize).expand(16, 0);
		}
		return prefSize;
	}

	@Override
	protected void layout() {
		super.layout();

		Rectangle bounds = getBounds();
		PointList points = new PointList();

		final int w = bounds.width;
		final int h = bounds.height;

		if (isInput) {
			points.addPoint(0, h / 2);
			points.addPoint(10, h / 2);
		} else {
			points.addPoint(w - 11, h / 2);
			points.addPoint(w, h / 2);
		}

		points.translate(getLocation());
		if (line != null) {
			line.setPoints(points);
		}
	}

	@Override
	public void validate() {
		if (isValid()) {
			return;
		}

		if (rectangle != null) {
			final int w = bounds.width, h = bounds.height;
			if (isInput) {
				setConstraint(rectangle, new Rectangle(0, h / 2 - 1, 3, 3));
			} else {
				setConstraint(rectangle, new Rectangle(w - 3, h / 2 - 1, 3, 3));
			}
		}

		super.validate();
	}

}
