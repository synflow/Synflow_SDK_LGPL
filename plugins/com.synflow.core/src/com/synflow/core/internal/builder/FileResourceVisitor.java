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
 
package com.synflow.core.internal.builder;

import static com.synflow.core.ICoreConstants.FILE_EXT_CX;
import static com.synflow.core.ICoreConstants.FILE_EXT_IR;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;

/**
 * This class defines a file resource visitor.
 *
 * @author Matthieu Wipliez
 *
 */
public class FileResourceVisitor implements IResourceVisitor, IResourceDeltaVisitor {

	private final List<IFile> derived;

	private final List<IFile> removed;

	private final List<IFile> sources;

	public FileResourceVisitor() {
		derived = new ArrayList<>();
		removed = new ArrayList<>();
		sources = new ArrayList<>();
	}

	public List<IFile> getDerived() {
		return derived;
	}

	public List<IFile> getRemoved() {
		return removed;
	}

	public List<IFile> getSources() {
		return sources;
	}

	@Override
	public boolean visit(IResource resource) {
		visitResource(resource);
		return true;
	}

	@Override
	public boolean visit(IResourceDelta delta) {
		int kind = delta.getKind();
		if (kind == IResourceDelta.ADDED || kind == IResourceDelta.CHANGED) {
			visitResource(delta.getResource());
		} else if (kind == IResourceDelta.REMOVED) {
			IResource resource = delta.getResource();
			if (resource.getType() == IResource.FILE) {
				IFile file = (IFile) resource;
				String fileExt = resource.getFileExtension();

				if (fileExt != null) {
					switch (fileExt) {
					case FILE_EXT_IR:
						removed.add(file);
					}
				}
			}
		}

		return true;
	}

	/**
	 * Visits a resource and adds the given resource to the appropriate list (if any).
	 *
	 * @param resource
	 *            the resource being added/changed or visited
	 */
	private void visitResource(IResource resource) {
		if (resource.getType() == IResource.FILE) {
			IFile file = (IFile) resource;
			String fileExt = resource.getFileExtension();

			if (fileExt != null) {
				switch (fileExt) {
				case FILE_EXT_CX:
					sources.add(file);
					break;
				case FILE_EXT_IR:
					derived.add(file);
					break;
				}
			}
		}
	}

}
