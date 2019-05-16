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
 
package com.synflow.core.layout;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.PlatformObject;

/**
 * This class defines an abstract tree element, adaptable to IResource.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class AbstractTreeElement extends PlatformObject implements ITreeElement {

	private IResource resource;

	public AbstractTreeElement(IResource resource) {
		this.resource = resource;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AbstractTreeElement)) {
			return false;
		}
		AbstractTreeElement other = (AbstractTreeElement) obj;
		return resource.equals(other.resource);
	}

	@Override
	public IResource getResource() {
		return resource;
	}

	@Override
	public int hashCode() {
		return resource.hashCode();
	}

	@Override
	public boolean isPackage() {
		return false;
	}

	@Override
	public boolean isSourceFolder() {
		return false;
	}

}
