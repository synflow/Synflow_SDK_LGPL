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
package com.synflow.cx.ui.labeling;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

import com.synflow.cx.ui.internal.CxActivator;

/**
 * This class defines a composite image descriptor that adds an error or a warning overlay. Images
 * and addBottomLeftImage method borrowed from JDT are Copyright (c) 2000, 2013 IBM Corporation and
 * others.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxImageDescriptor extends CompositeImageDescriptor {

	private static final String CX_UI_PLUGIN_ID = "com.synflow.cx.ui";

	private static final ImageDescriptor ERROR = CxActivator.imageDescriptorFromPlugin(
			CX_UI_PLUGIN_ID, "icons/error_co.gif");

	private static final ImageDescriptor WARNING = CxActivator.imageDescriptorFromPlugin(
			CX_UI_PLUGIN_ID, "icons/warning_co.gif");

	private Image baseImage;

	private int severity;

	private Point size;

	public CxImageDescriptor(Image image, int severity, Point size) {
		this.baseImage = image;
		this.size = size;
		this.severity = severity;
	}

	private void addBottomLeftImage(ImageDescriptor desc) {
		ImageData data = desc.getImageData();
		int y = size.y - data.height;
		if (data.width < getSize().x && y >= 0) {
			drawImage(data, 0, y);
		}
	}

	@Override
	protected void drawCompositeImage(int width, int height) {
		drawImage(baseImage.getImageData(), 0, 0);

		if (severity == IMarker.SEVERITY_ERROR) {
			addBottomLeftImage(ERROR);
		} else if (severity == IMarker.SEVERITY_WARNING) {
			addBottomLeftImage(WARNING);
		}
	}

	@Override
	protected Point getSize() {
		return size;
	}

}
