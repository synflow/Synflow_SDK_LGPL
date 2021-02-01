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
 
package com.synflow.core.layout;

import static com.synflow.core.ICoreConstants.FILE_EXT_CX;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.google.common.base.Joiner;
import com.synflow.core.SynflowCore;

/**
 * This class defines a package in the project tree.
 *
 * @author Matthieu Wipliez
 *
 */
public class Package extends AbstractTreeElement {

	private String name;

	public Package(IResource resource) {
		super(resource);
	}

	public Object[] getFiles() {
		IFolder folder = (IFolder) getResource();
		List<IFile> files = new ArrayList<>();
		try {
			for (IResource member : folder.members()) {
				if (member.getType() == IResource.FILE) {
					files.add((IFile) member);
				}
			}
		} catch (CoreException e) {
			SynflowCore.log(e);
		}
		return files.toArray();
	}

	@Override
	public String getName() {
		if (name == null) {
			IPath path = getResource().getFullPath();
			String[] segments = path.removeFirstSegments(2).segments();
			name = Joiner.on('.').join(segments);
		}
		return name;
	}

	public SourceFolder getSourceFolder() {
		return ProjectLayout.getSourceFolder(getResource().getProject());
	}

	public boolean isEmpty() {
		for (Object obj : getFiles()) {
			IFile file = (IFile) obj;
			if (FILE_EXT_CX.equals(file.getFileExtension())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isPackage() {
		return true;
	}

	@Override
	public String toString() {
		return "package " + getName();
	}

}
