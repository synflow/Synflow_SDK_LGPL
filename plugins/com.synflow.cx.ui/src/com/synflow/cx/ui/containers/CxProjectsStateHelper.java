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

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.ui.containers.WorkspaceProjectsStateHelper;

/**
 * This class defines a project state helper for Cx.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxProjectsStateHelper extends WorkspaceProjectsStateHelper {

	@Override
	public Collection<URI> initContainedURIs(String containerHandle) {
		return super.initContainedURIs(containerHandle);
	}

	@Override
	public String initHandle(URI uri) {
		return super.initHandle(uri);
	}

	@Override
	public List<String> initVisibleHandles(String handle) {
		return super.initVisibleHandles(handle);
	}

}
