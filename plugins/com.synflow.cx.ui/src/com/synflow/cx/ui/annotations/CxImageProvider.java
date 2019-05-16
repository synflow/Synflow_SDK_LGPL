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
package com.synflow.cx.ui.annotations;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.texteditor.IAnnotationImageProvider;
import org.eclipse.ui.texteditor.MarkerAnnotation;

import com.synflow.cx.ui.internal.views.ViewsConstants;

/**
 * This class defines an image provider for the cycle indicator marker.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxImageProvider implements IAnnotationImageProvider {

	public static final String TYPE_ANNOT = "com.synflow.cx.ui.cycleIndicator";

	private Map<Integer, Image> images = new HashMap<>();

	@Override
	public ImageDescriptor getImageDescriptor(String imageDescriptorId) {
		return null;
	}

	@Override
	public String getImageDescriptorId(Annotation annotation) {
		return null;
	}

	@Override
	public Image getManagedImage(Annotation annotation) {
		IMarker marker = ((MarkerAnnotation) annotation).getMarker();

		Device device = Display.getCurrent();
		int index = marker.getAttribute("index", 0);
		Image image = images.get(index);
		if (image == null) {
			image = new Image(device, 16, 16);
			images.put(index, image);

			Color color = new Color(device, 248, 248, 248);
			GC gc = new GC(image);
			gc.setBackground(color);
			gc.fillRectangle(0, 0, 16, 15);
			color.dispose();

			FontDescriptor fd = FontDescriptor.createFrom(gc.getFont()).setHeight(7);

			Font font = fd.createFont(device);
			gc.setFont(font);
			gc.setForeground(ViewsConstants.SELECTION_COLOR);
			gc.drawText(String.valueOf(index + 1), 2, 0);
			fd.destroyFont(font);

			gc.dispose();
		}

		return image;
	}

}
