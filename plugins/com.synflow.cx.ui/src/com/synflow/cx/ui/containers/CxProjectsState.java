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
package com.synflow.cx.ui.containers;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.ui.containers.WorkspaceProjectsState;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.synflow.core.layout.ITreeElement;
import com.synflow.core.layout.ProjectLayout;

/**
 * This class defines a project state for Cx.
 *
 * @author Matthieu Wipliez
 *
 */
@Singleton
public class CxProjectsState extends WorkspaceProjectsState {

	private CxProjectsStateHelper cxHelper;

	@Inject
	public CxProjectsState(CxProjectsStateHelper helper) {
		this.cxHelper = helper;
	}

	@Override
	protected Collection<URI> doInitContainedURIs(String containerHandle) {
		return cxHelper.initContainedURIs(containerHandle);
	}

	@Override
	protected String doInitHandle(URI uri) {
		return cxHelper.initHandle(uri);
	}

	@Override
	protected List<String> doInitVisibleHandles(String handle) {
		return cxHelper.initVisibleHandles(handle);
	}

	@Override
	protected boolean isIgnoredResource(IResource resource) {
		if (resource.getType() == IResource.FOLDER) {
			// ignore any folder that is not in a package nor a source folder
			ITreeElement element = ProjectLayout.getTreeElement(resource);
			return element == null;
		}

		// accept other resources (workspace root, projects, files in source folder/packages)
		return false;
	}

}
