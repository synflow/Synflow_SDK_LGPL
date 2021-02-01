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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.synflow.core.SynflowCore;

/**
 *
 * This class defines a source folder in the project tree.
 *
 * @author Matthieu Wipliez
 *
 */
public class SourceFolder extends AbstractTreeElement {

	public SourceFolder(IResource resource) {
		super(resource);
	}

	/**
	 * Fills the given packages list from children of the given folder.
	 *
	 * @param packages
	 *            a list of packages
	 * @param folder
	 *            a folder
	 */
	private void fillPackages(List<Package> packages, IFolder folder) {
		try {
			for (IResource member : folder.members()) {
				if (member.getType() == IResource.FOLDER) {
					packages.add(new Package(member));
					fillPackages(packages, (IFolder) member);
				}
			}
		} catch (CoreException e) {
			SynflowCore.log(e);
		}
	}

	@Override
	public String getName() {
		return getResource().getName();
	}

	/**
	 * Computes and returns an array with all packages contained in this source folder.
	 *
	 * @return an array of Packages
	 */
	public Object[] getPackages() {
		List<Package> packages = new ArrayList<>();
		fillPackages(packages, getResource());
		return packages.toArray();
	}

	/**
	 * Returns the project in which this source folder is contained.
	 *
	 * @return a project
	 */
	public IProject getProject() {
		return getResource().getProject();
	}

	@Override
	public IFolder getResource() {
		return (IFolder) super.getResource();
	}

	@Override
	public boolean isSourceFolder() {
		return true;
	}

	@Override
	public String toString() {
		return "source folder \"" + getName() + "\"";
	}

}
