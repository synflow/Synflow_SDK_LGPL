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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import com.synflow.core.SynflowCore;
import com.synflow.core.layout.ITreeElement;

/**
 * This class defines a label decorator that doesn't do much at the moment.
 *
 * @author Matthieu Wipliez
 *
 */
public class ProblemsLabelDecorator implements ILabelDecorator, ILightweightLabelDecorator {

	private ListenerList<ILabelProviderListener> fListeners;

	@Override
	public void addListener(ILabelProviderListener listener) {
		if (fListeners == null) {
			fListeners = new ListenerList<ILabelProviderListener>();
		}
		fListeners.add(listener);
	}

	@Override
	public void decorate(Object element, IDecoration decoration) {
	}

	@Override
	public Image decorateImage(Image image, Object obj) {
		if (image == null) {
			return null;
		}

		int severity = findSeverity(obj);
		if (severity > 0) {
			Rectangle bounds = image.getBounds();
			Point size = new Point(bounds.width, bounds.height);
			return new CxImageDescriptor(image, severity, size).createImage();
		}
		return image;
	}

	@Override
	public String decorateText(String text, Object element) {
		return text;
	}

	@Override
	public void dispose() {
	}

	private int findSeverity(Object obj) {
		int severity = -1;

		IResource resource;
		if (obj instanceof IResource) {
			resource = (IResource) obj;
		} else if (obj instanceof ITreeElement) {
			ITreeElement element = (ITreeElement) obj;
			resource = element.getResource();
		} else {
			return severity;
		}

		if (!resource.isAccessible()) {
			return severity;
		}

		try {
			severity = resource.findMaxProblemSeverity(IMarker.PROBLEM, true,
					IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			SynflowCore.log(e);
		}
		return severity;
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		if (fListeners != null) {
			fListeners.remove(listener);
		}
	}

}
