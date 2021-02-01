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
package com.synflow.cx.ui.internal.views.graph.figures;

import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.swt.SWT;

/**
 * This class defines the figure for a connection.
 *
 * @author Matthieu Wipliez
 *
 */
public class ConnectionFigure extends PolylineConnection {

	public ConnectionFigure() {
		// set anti-aliasing
		setAntialias(SWT.ON);
	}

}
