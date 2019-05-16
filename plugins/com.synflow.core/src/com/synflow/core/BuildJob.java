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
 
package com.synflow.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;

/**
 * This class defines a build job.
 *
 * @author Matthieu Wipliez
 *
 */
public class BuildJob extends Job {

	private final IProject project;

	public BuildJob(IProject project) {
		super("Build " + project.getName());
		this.project = project;
	}

	@Override
	public boolean belongsTo(Object family) {
		return ResourcesPlugin.FAMILY_MANUAL_BUILD == family;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			return Status.CANCEL_STATUS;
		}

		try {
			SubMonitor subMonitor = SubMonitor.convert(monitor, "Building " + project.getName(), 2);
			project.build(IncrementalProjectBuilder.CLEAN_BUILD, subMonitor);
		} catch (CoreException e) {
			return e.getStatus();
		} finally {
			monitor.done();
		}
		return Status.OK_STATUS;
	}

}
