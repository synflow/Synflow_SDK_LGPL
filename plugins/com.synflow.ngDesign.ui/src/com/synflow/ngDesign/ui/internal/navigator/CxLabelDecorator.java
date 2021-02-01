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
package com.synflow.ngDesign.ui.internal.navigator;

import static com.synflow.core.ICoreConstants.FILE_EXT_CX;
import static com.synflow.models.util.EcoreHelper.getEObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.google.common.collect.Iterables;
import com.synflow.cx.cx.Module;
import com.synflow.cx.cx.Network;
import com.synflow.cx.ui.internal.CxActivator;

/**
 * This class defines a label decorator.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxLabelDecorator implements ILightweightLabelDecorator {

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof IFile) {
			IFile file = (IFile) element;
			if (FILE_EXT_CX.equals(file.getFileExtension())) {
				Module module = getEObject(file, Module.class);
				if (module != null) {
					Iterable<Network> networks = Iterables.filter(module.getEntities(),
							Network.class);
					if (!Iterables.isEmpty(networks)) {
						ImageDescriptor descriptor = CxActivator.imageDescriptorFromPlugin(
								"com.synflow.cx.ui", "icons/overlay_network.png");
						decoration.addOverlay(descriptor);
					}
				}
			}
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

}
