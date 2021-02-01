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
package com.synflow.cx.ui.internal.views.fsm.layout;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;

import com.synflow.models.dpn.State;

public class Box {

	private final List<Box> children;

	private int height;

	private Box parent;

	private int startX;

	private int startY;

	private final List<State> vertices;

	private int width;

	public int x;

	public int y;

	public Box() {
		this(null);
	}

	public Box(Box parent) {
		this(parent, parent.x, parent.y);
	}

	public Box(Box parent, int x, int y) {
		children = new ArrayList<>(4);
		if (parent != null) {
			parent.add(this);
			this.parent = parent;
		}

		this.x = startX = x;
		this.y = startY = y;
		vertices = new ArrayList<>(4);
	}

	public Box(int x, int y) {
		this(null, x, y);
	}

	private void add(Box child) {
		children.add(child);
	}

	public void add(State state) {
		vertices.add(state);
	}

	public int getBottom() {
		return startY + height;
	}

	public List<Box> getChildren() {
		return children;
	}

	public Point getLocation() {
		return new Point(x, y);
	}

	public Box getParent() {
		return parent;
	}

	public int getStartX() {
		return startX;
	}

	public List<State> getVertices() {
		return vertices;
	}

	public int getWidth() {
		return width;
	}

	/**
	 * Updates the size of this box based on the current value of {@link #x} and
	 * {@link #y}
	 */
	public void updateSize() {
		int maxY = y;

		int minX = startX;
		int maxX = x;
		for (Box child : children) {
			if (child.startX < minX) {
				minX = child.startX;
			}
			if (child.x > maxX) {
				maxX = child.x;
			}
		}

		for (State state : vertices) {
			Point location = state.get("location");
			if (location.x < minX) {
				minX = location.x;
			}
			if (location.x > maxX) {
				maxX = location.x;
			}

			if (location.y > maxY) {
				maxY = location.y;
			}
		}

		startX = minX;
		width = maxX - minX;
		y = maxY;
		height = y - startY;
	}

}
