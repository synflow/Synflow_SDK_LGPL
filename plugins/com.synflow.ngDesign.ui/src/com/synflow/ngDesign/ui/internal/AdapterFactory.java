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
package com.synflow.ngDesign.ui.internal;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.IContributorResourceAdapter;

import com.synflow.core.layout.ITreeElement;

/**
 * This class defines an adapter factory.
 *
 * @author Matthieu Wipliez
 *
 */
public class AdapterFactory implements IAdapterFactory, IContributorResourceAdapter {

	@Override
	public IResource getAdaptedResource(IAdaptable adaptable) {
		ITreeElement element = getTreeElement(adaptable);
		if (element != null) {
			return element.getResource();
		}
		return null;
	}

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		ITreeElement element = getTreeElement(adaptableObject);

		if (IResource.class.isAssignableFrom(adapterType)) {
			return adapterType.cast(element.getResource());
		} else if (IContributorResourceAdapter.class.equals(adapterType)) {
			return adapterType.cast(this);
		}

		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { IFolder.class, IResource.class, IContributorResourceAdapter.class };
	}

	private ITreeElement getTreeElement(Object object) {
		if (object instanceof ITreeElement) {
			return (ITreeElement) object;
		}
		return null;
	}

}
