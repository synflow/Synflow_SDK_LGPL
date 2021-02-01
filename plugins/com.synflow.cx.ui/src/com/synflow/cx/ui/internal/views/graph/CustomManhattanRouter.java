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

import static org.eclipse.draw2d.geometry.Geometry.linesIntersect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * This class defines a custom manhattan router that avoids obstacles.
 *
 * @author Matthieu Wipliez
 *
 */
public class CustomManhattanRouter extends AbstractRouter {

	private IFigure container;

	private Map<IFigure, Rectangle> figuresToBounds;

	private PointList points;

	private Set<Integer> uses_x;

	private Set<Integer> uses_y;

	public CustomManhattanRouter(IFigure container) {
		this.container = container;
	}

	private void drawStraight(Point start, Point end) {
		int middle = getMiddle(start.x, end.x);
		points.addPoint(new Point(start));
		points.addPoint(new Point(middle, start.y));
		points.addPoint(new Point(middle, end.y));
		points.addPoint(new Point(end));
	}

	private int getMiddle(int min, int max) {
		int middle = (min + max) / 2;
		if (middle == min || middle == max) {
			return middle;
		}

		while (uses_x.contains(middle) || uses_x.contains(middle - 1)
				|| uses_x.contains(middle + 1)) {
			if (middle < max) {
				middle++;
			} else {
				middle = min;
				break;
			}
		}

		uses_x.add(middle);
		return middle;
	}

	private int getUnused_x(int x, int inc) {
		x += inc;
		while (uses_x.contains(x) || uses_x.contains(x - 1) || uses_x.contains(x + 1)) {
			x += inc;
		}

		uses_x.add(x);
		return x;
	}

	private int getUnused_y(int y, int inc) {
		y += inc;
		while (uses_y.contains(y) || uses_y.contains(y - 1) || uses_y.contains(y + 1)) {
			y += inc;
		}

		uses_y.add(y);
		return y;
	}

	private boolean intersects(Rectangle bounds, Point start, Point end) {
		int left = bounds.x, top = bounds.y, right = bounds.x + bounds.width,
				bottom = bounds.y + bounds.height;
		return linesIntersect(start.x, start.y + 1, end.x, end.y - 1, left, top, right, top)
				|| linesIntersect(start.x + 1, start.y, end.x - 1, end.y, left, top, left, bottom)
				|| linesIntersect(start.x, start.y + 1, end.x, end.y - 1, left, bottom, right,
						bottom)
				|| linesIntersect(start.x + 1, start.y, end.x - 1, end.y, right, bottom, right,
						top);
	}

	@Override
	public void invalidate(Connection connection) {
		uses_x = null;
		uses_y = null;
	}

	@Override
	public void route(Connection connection) {
		if (figuresToBounds == null) {
			updateMap();
		}

		if (uses_x == null) {
			uses_x = new TreeSet<>();
			uses_y = new TreeSet<>();
		}

		routeConnection(connection);
	}

	private void routeConnection(Connection connection) {
		this.points = new PointList();

		Point start = getStartPoint(connection);
		Point end = getEndPoint(connection);

		connection.translateToRelative(start);
		connection.translateToRelative(end);

		walk(start, end);

		connection.setPoints(points);
	}

	private void updateMap() {
		figuresToBounds = new HashMap<>();
		for (Object child : container.getChildren()) {
			IFigure figure = (IFigure) child;
			Rectangle bounds = figure.getBounds().getCopy();
			figuresToBounds.put(figure, bounds);
		}
	}

	private void walk(Point start, Point end) {
		List<Rectangle> list = new ArrayList<>();
		for (Rectangle candidate : figuresToBounds.values()) {
			if (intersects(candidate, start, end)) {
				list.add(candidate);
			}
		}

		if (list.isEmpty()) {
			drawStraight(start, end);
			return;
		}

		list.sort((b1, b2) -> {
			if (b1.x < b2.x) {
				return -1;
			} else if (b1.x > b2.x) {
				return 1;
			} else {
				return 0;
			}
		} );

		points.addPoint(new Point(start));

		Rectangle bounds = list.get(start.x < end.x ? 0 : list.size() - 1);
		int height;
		if (start.y < bounds.y + bounds.height / 2) {
			height = getUnused_y(bounds.y, -1);
		} else {
			height = getUnused_y(bounds.y + bounds.height, +1);
		}

		if (start.x < end.x) {
			int width;
			if (start.x < bounds.x) {
				width = getUnused_x(bounds.x, -1);
			} else {
				width = getUnused_x(start.x, +1);
			}

			points.addPoint(new Point(width, start.y));
			points.addPoint(new Point(width, height));
			start = new Point(getUnused_x(bounds.x + bounds.width, +1), height);
		} else {
			points.addPoint(new Point(start.x, height));
			start = new Point(getUnused_x(bounds.x, -1), height);
		}

		points.addPoint(start);
		walk(start, end);
	}

}
