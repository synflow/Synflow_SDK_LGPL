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

import static com.synflow.core.ICoreConstants.FOLDER_CLASSES;
import static com.synflow.core.ICoreConstants.FOLDER_IR;
import static com.synflow.core.ICoreConstants.FOLDER_SIM;
import static com.synflow.core.ICoreConstants.FOLDER_TESTBENCH;
import static com.synflow.models.util.EcoreHelper.getEObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.synflow.core.ICodeGenerator;
import com.synflow.core.SynflowCore;
import com.synflow.core.util.CoreUtil;
import com.synflow.models.dpn.Entity;
import com.synflow.models.util.EcoreHelper;

/**
 * This class defines the Synflow builder.
 *
 * @author Matthieu Wipliez
 *
 */
public class SynflowBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "com.synflow.core.builder";

	@Inject
	@Named("Java")
	private ICodeGenerator javaGenerator;

	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) {
		IProject project = getProject();

		// clean markers that might be set if code generators are not configured properly
		try {
			project.deleteMarkers(IMarker.PROBLEM, false, 0);
		} catch (CoreException e) {
			SynflowCore.log(e);
		}

		// visit project to collect resources
		FileResourceVisitor visitor = new FileResourceVisitor();
		collectResources(kind, visitor);

		// load all derived files (actors and networks)
		ResourceSet set = EcoreHelper.newResourceSet();
		List<Entity> entities = new ArrayList<>();
		List<IFile> derived = visitor.getDerived();
		for (IFile file : derived) {
			Entity entity = getEObject(set, file, Entity.class);
			if (entity != null) {
				entities.add(entity);
			}
		}

		// get generator for the project
		List<IFile> removed = visitor.getRemoved();
		javaGenerator.setOutputFolder(project.getName());
		generateCode(javaGenerator, kind, entities, removed, monitor);

		return null;
	}

	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		// clean up build state
		final IProject project = getProject();

		// delete files in .ir folder
		SubMonitor subMonitor = SubMonitor.convert(monitor, 6);
		deleteFiles(project, FOLDER_IR, subMonitor.newChild(1));

		deleteFiles(project, FOLDER_SIM + '/' + FOLDER_CLASSES, subMonitor.newChild(1));

		// clean up "-gen" folders
		deleteFiles(project, "verilog-gen", subMonitor.newChild(1));
		deleteFiles(project, "vhdl-gen", subMonitor.newChild(1));
		deleteFiles(project, FOLDER_TESTBENCH, subMonitor.newChild(1));
	}

	/**
	 * Visits the current project or delta with the given visitor to collect resources to build.
	 *
	 * @param kind
	 * @param visitor
	 */
	private void collectResources(int kind, FileResourceVisitor visitor) {
		IProject project = getProject();
		try {
			switch (kind) {
			case FULL_BUILD:
				project.accept(visitor);
				break;

			case AUTO_BUILD:
			case INCREMENTAL_BUILD:
				IResourceDelta delta = getDelta(getProject());
				if (delta != null) {
					delta.accept(visitor);
				}
				break;
			}
		} catch (CoreException e) {
			SynflowCore.log(e);
		}
	}

	/**
	 * Removes all the files in the folder with the given name in the given project.
	 *
	 * @param project
	 *            a project
	 * @param name
	 *            name of the folder to clean
	 */
	private void deleteFiles(IProject project, String name, IProgressMonitor monitor) {
		IFolder folder = project.getFolder(new Path(name));
		if (!folder.exists()) {
			return;
		}

		try {
			// first refresh so that everything can be removed by delete
			folder.refreshLocal(IResource.DEPTH_INFINITE, null);

			// find members and delete them
			IResource[] members = folder.members();
			SubMonitor subMonitor = SubMonitor.convert(monitor, members.length);
			for (IResource member : members) {
				member.delete(true, subMonitor);
			}
		} catch (CoreException e) {
			SynflowCore.log(e);
		}
	}

	/**
	 * Uses the given generator to generate code for each file in the given list.
	 *
	 * @param generator
	 *            a generator
	 * @param entities
	 *            a list of entities
	 * @param monitor
	 *            a monitor
	 */
	private void generateCode(ICodeGenerator generator, int kind, List<Entity> entities,
			List<IFile> removed, IProgressMonitor monitor) {
		// removes generated files correspond to removed .ir files
		for (IFile file : removed) {
			IProject project = file.getProject();
			IFolder irFolder = project.getFolder(FOLDER_IR);
			IPath path = CoreUtil.getRelative(irFolder, file);

			String name = path.removeFileExtension().toString().replace('/', '.');
			generator.remove(name);
		}

		final String taskName = "Generating " + generator.getName() + " ";
		final int size = entities.size();
		SubMonitor subMonitor = SubMonitor.convert(monitor, taskName, size);

		int i = 1;
		for (Entity entity : entities) {
			if (subMonitor.isCanceled()) {
				break;
			}

			String name = entity.getName();
			subMonitor.subTask(taskName + " for " + name + " (" + i + " of " + size + ")");
			subMonitor.newChild(1);
			try {
				generator.transform(entity);
				generator.print(entity);
			} catch (Exception e) {
				SynflowCore.log(e);
			}
			i++;
		}

		subMonitor.done();

		// for full builds (following a clean) we copy support files to the output folder
		if (kind == FULL_BUILD) {
			generator.copyLibraries();
		}
	}

}
