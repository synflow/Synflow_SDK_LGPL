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

import org.eclipse.draw2d.AnchorListener;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

/**
 * This class defines the figure for a loop transition.
 *
 * @author Matthieu Wipliez
 *
 */
public class TransitionLoopFigure extends Shape implements AnchorListener,
		Connection {

	private PolygonDecoration decoration;

	private Point start;

	private ConnectionAnchor startAnchor, endAnchor;

	public TransitionLoopFigure() {
		decoration = new PolygonDecoration();
		start = new Point(0, 0);

		setAntialias(SWT.ON);
		setBackgroundColor(ColorConstants.black);
	}

	@Override
	public void anchorMoved(ConnectionAnchor anchor) {
		// anchorMoved is called with final location of anchor
		if (anchor == getSourceAnchor()) {
			start = getSourceAnchor().getReferencePoint();
			anchor.getOwner().translateToRelative(start);
			bounds = null;
		}
	}

	@Override
	protected void fillShape(Graphics g) {
		Point location = getStart();
		g.setBackgroundColor(getForegroundColor());
		decoration.setLocation(new Point(location.x + 15, location.y));
		decoration
				.setReferencePoint(new Point(location.x + 30, location.y + 5));
		decoration.paintFigure(g);
	}

	@Override
	public Rectangle getBounds() {
		if (bounds == null) {
			Point location = getStart();
			int size = 30 + 2 * getLineWidth();
			bounds = new Rectangle(location.x, location.y, size, size);
		}
		return bounds;
	}

	@Override
	public ConnectionRouter getConnectionRouter() {
		return null;
	}

	@Override
	public PointList getPoints() {
		return null;
	}

	@Override
	public Object getRoutingConstraint() {
		return null;
	}

	@Override
	public ConnectionAnchor getSourceAnchor() {
		return startAnchor;
	}

	private Point getStart() {
		return start;
	}

	@Override
	public ConnectionAnchor getTargetAnchor() {
		return endAnchor;
	}

	private void hookSourceAnchor() {
		if (getSourceAnchor() != null) {
			getSourceAnchor().addAnchorListener(this);
		}
	}

	private void hookTargetAnchor() {
		if (getTargetAnchor() != null) {
			getTargetAnchor().addAnchorListener(this);
		}
	}

	@Override
	protected void outlineShape(Graphics g) {
		Point location = getStart();

		// g.drawArc(location.x - 30, location.y, 30, 30, 90, 270);

		g.drawArc(location.x, location.y, 30, 30, 180, 270);

		// g.drawArc(location.x, location.y - 30, 30, 30, 270, 270);
		// g.drawArc(location.x - 30, location.y - 30, 30, 30, 0, 270);
	}

	@Override
	public void setConnectionRouter(ConnectionRouter router) {
		// ignore set connection router
	}

	@Override
	public void setPoints(PointList list) {
		// ignore set points
	}

	@Override
	public void setRoutingConstraint(Object cons) {
		// ignore routing constraint
	}

	@Override
	public void setSourceAnchor(ConnectionAnchor anchor) {
		if (anchor == startAnchor) {
			return;
		}
		unhookSourceAnchor();

		startAnchor = anchor;
		if (getParent() != null) {
			hookSourceAnchor();
		}
	}

	@Override
	public void setTargetAnchor(ConnectionAnchor anchor) {
		if (anchor == endAnchor) {
			return;
		}
		unhookTargetAnchor();

		endAnchor = anchor;
		if (getParent() != null) {
			hookTargetAnchor();
		}
	}

	private void unhookSourceAnchor() {
		if (getSourceAnchor() != null) {
			getSourceAnchor().removeAnchorListener(this);
		}
	}

	private void unhookTargetAnchor() {
		if (getTargetAnchor() != null) {
			getTargetAnchor().removeAnchorListener(this);
		}
	}

}
