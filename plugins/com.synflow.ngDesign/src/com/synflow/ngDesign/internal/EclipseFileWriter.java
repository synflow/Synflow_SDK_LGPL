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
package com.synflow.ngDesign.internal;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.synflow.core.IFileWriter;
import com.synflow.core.SynflowCore;
import com.synflow.core.util.CoreUtil;

/**
 * This class defines an implementation of a IFileWriter based on the Eclipse IFile class. The name
 * of the file must be relative to a project.
 *
 * @author Matthieu Wipliez
 *
 */
public class EclipseFileWriter implements IFileWriter {

	private IProject project;

	/**
	 * Taken from org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2, (c) 2011 itemis AG
	 *
	 * @param container
	 * @throws CoreException
	 */
	private void ensureExists(IContainer container) throws CoreException {
		if (container.exists()) {
			return;
		} else if (container instanceof IFolder) {
			ensureExists(container.getParent());
			((IFolder) container).create(true, true, null);
		}
	}

	@Override
	public boolean exists(String fileName) {
		IFile file = project.getFile(fileName);
		return file.exists();
	}

	@Override
	public String getAbsolutePath(String fileName) {
		return project.getFile(fileName).getLocation().toString();
	}

	@Override
	public void remove(String fileName) {
		IFile file = project.getFile(fileName);
		try {
			file.delete(true, null);
		} catch (CoreException e) {
			SynflowCore.log(e);
		}
	}

	@Override
	public void setOutputFolder(String projectName) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		project = root.getProject(projectName);
	}

	@Override
	public void write(String fileName, CharSequence sequence) {
		if (sequence == null) {
			return;
		}

		String contents = sequence.toString();
		InputStream source = new ByteArrayInputStream(contents.getBytes());
		write(fileName, source);
	}

	@Override
	public void write(String fileName, InputStream source) {
		IFile file = project.getFile(fileName);
		try {
			CoreUtil.ensureCaseConsistency(file.getFullPath());

			if (file.exists()) {
				file.setContents(source, true, true, null);
			} else {
				ensureExists(file.getParent());
				file.create(source, true, null);
			}
		} catch (CoreException e) {
			SynflowCore.log(e);
		}
	}

}
