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
package com.synflow.cx.builder;

import static com.synflow.cx.ui.internal.CxActivator.COM_SYNFLOW_CX_CX;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.xtext.builder.impl.IToBeBuiltComputerContribution;
import org.eclipse.xtext.builder.impl.ToBeBuilt;

import com.google.inject.Injector;
import com.synflow.core.layout.ITreeElement;
import com.synflow.core.layout.ProjectLayout;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.cx.ui.internal.CxActivator;

/**
 * This class defines a contributing ToBeBuiltComputer. For now just clears instantiation data on
 * full builds and when projects are added/removed.
 *
 * @author Matthieu Wipliez
 *
 */
@SuppressWarnings("restriction")
public class CxToBeBuiltComputer implements IToBeBuiltComputerContribution {

	private IInstantiator instantiator;

	private void ensureInstantiatorInjected() {
		if (instantiator == null) {
			Injector injector = CxActivator.getInstance().getInjector(COM_SYNFLOW_CX_CX);
			instantiator = injector.getInstance(IInstantiator.class);
		}
	}

	@Override
	public boolean isPossiblyHandled(IStorage storage) {
		// everything in a package or source folder is possibly handled
		// Xtext takes care of filtering .cx files later
		return true;
	}

	@Override
	public boolean isRejected(IFolder folder) {
		// ignore any resource that is not a package nor a source folder
		ITreeElement element = ProjectLayout.getTreeElement(folder);
		return element == null;
	}

	@Override
	public void removeProject(ToBeBuilt toBeBuilt, IProject project, IProgressMonitor monitor) {
		// this is called when a clean build starts
		// we just clear the instantiator's data

		ensureInstantiatorInjected();
		instantiator.clearData();
	}

	@Override
	public boolean removeStorage(ToBeBuilt toBeBuilt, IStorage storage, IProgressMonitor monitor) {
		return false;
	}

	@Override
	public void updateProject(ToBeBuilt toBeBuilt, IProject project, IProgressMonitor monitor)
			throws CoreException {
		// this is called when a full build starts
		// (if caused by a clean, removeProject has already run)

		// this is where we should update the instantiator, except that at this point we have
		// no build data and no resource description data.
	}

	@Override
	public boolean updateStorage(ToBeBuilt toBeBuilt, IStorage storage, IProgressMonitor monitor) {
		return false;
	}

}
