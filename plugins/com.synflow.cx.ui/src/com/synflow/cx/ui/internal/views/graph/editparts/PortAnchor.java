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
package com.synflow.cx.ui.internal.views.graph.editparts;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.synflow.models.dpn.Direction;

/**
 * This class defines a port anchor.
 *
 * @author Matthieu Wipliez
 *
 */
public class PortAnchor extends AbstractConnectionAnchor {

	private Direction direction;

	public PortAnchor(IFigure owner, Direction direction) {
		super(owner);
		this.direction = direction;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PortAnchor) {
			PortAnchor other = (PortAnchor) obj;
			return other.direction == this.direction && other.getOwner() == this.getOwner();
		}
		return false;
	}

	@Override
	public Point getLocation(Point reference) {
		Rectangle bounds = getOwner().getBounds();
		Point location = bounds.getLocation();
		getOwner().translateToAbsolute(location);

		if (direction == Direction.INPUT) {
			location.translate(0, bounds.height / 2);
		} else {
			location.translate(bounds.width, bounds.height / 2);
		}
		return location;
	}

	@Override
	public Point getReferencePoint() {
		Point ref = getOwner().getBounds().getCenter();
		getOwner().translateToAbsolute(ref);
		return ref;
	}

	@Override
	public int hashCode() {
		return direction.hashCode() ^ getOwner().hashCode();
	}

}
