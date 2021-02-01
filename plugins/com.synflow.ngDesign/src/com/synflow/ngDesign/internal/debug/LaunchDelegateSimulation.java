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
package com.synflow.ngDesign.internal.debug;

import static com.synflow.core.ICoreConstants.FOLDER_CLASSES;
import static com.synflow.core.ICoreConstants.FOLDER_SIM;
import static com.synflow.models.util.EcoreHelper.getEObject;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.ENTITY;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.LIBRARIES;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.LIBRARIES_DEFAULT;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.PROJECT;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.SIMULATION_MODELSIM;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.SIMULATION_MODELSIM_DEFAULT;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.SOURCES;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.SOURCES_DEFAULT;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.WORKING_DIRECTORY;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.getDefaultWorkingDirectory;
import static com.synflow.ngDesign.NgDesignModule.JAVA;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.debug.core.model.RuntimeProcess;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.synflow.core.ICodeGenerator;
import com.synflow.core.IExporter;
import com.synflow.core.SynflowCore;
import com.synflow.core.util.CoreUtil;
import com.synflow.models.dpn.Entity;
import com.synflow.ngDesign.NgDesign;

/**
 * This class defines a delegate for simulation.
 *
 * @author Matthieu Wipliez
 *
 */
public class LaunchDelegateSimulation extends LaunchConfigurationDelegate {

	private class Launcher implements IDebugEventSetListener {

		@Override
		public void handleDebugEvents(DebugEvent[] events) {
			for (DebugEvent event : events) {
				if (event.getKind() == DebugEvent.TERMINATE) {
					Object source = event.getSource();
					if (source instanceof RuntimeProcess) {
						RuntimeProcess process = (RuntimeProcess) source;
						if ("java".equals(process.getLabel())) {
							try {
								process.getExitValue();
							} catch (DebugException e) {
								// never thrown because we're sure that process is already
								// terminated
							}

							// stops listening
							DebugPlugin.getDefault().removeDebugEventListener(this);
						}
					}
				}
			}
		}
	}

	@Inject
	@Named(JAVA)
	private ICodeGenerator generator;

	/**
	 * Returns a list of source files for the given launch configuration. If no source files were
	 * specified, use the {{entity}}.tb.c testbench, otherwise append the {{entity}}.c top to the
	 * list.
	 *
	 * @param config
	 *            launch configuration
	 * @param project
	 *            project
	 * @param entity
	 *            entity
	 * @return a list of source files
	 * @throws CoreException
	 */
	private List<String> getSources(ILaunchConfiguration config, IProject project, Entity entity)
			throws CoreException {
		List<String> sources = new ArrayList<>(config.getAttribute(SOURCES, SOURCES_DEFAULT));

		// add source file (either .tb or normal)
		IFile sourceFile;
		if (sources.isEmpty()) {
			sourceFile = project.getFile(generator.computePathTb(entity));
		} else {
			sourceFile = project.getFile(generator.computePath(entity));
		}

		sources.add(sourceFile.getLocation().toString());
		return sources;
	}

	@Override
	public void launch(ILaunchConfiguration configuration, final String mode, ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		String projectName = configuration.getAttribute(PROJECT, "");
		generator.setOutputFolder(projectName);

		IProject project = root.getProject(projectName);
		String className = configuration.getAttribute(ENTITY, "");
		IFile file = CoreUtil.getIrFile(project, className);
		Entity entity = getEObject(file, Entity.class);
		if (entity == null) {
			return;
		}

		if (configuration.getAttribute(SIMULATION_MODELSIM, SIMULATION_MODELSIM_DEFAULT)) {
			runModelsimExporter(launch, project, entity);
		} else {
			runBytecode(launch, project, entity);
		}
	}

	/**
	 * Compiles the code for the given launch, project, entity, using the given compiler.
	 *
	 * @param launch
	 * @param project
	 * @param entity
	 * @param compiler
	 * @throws CoreException
	 */
	private void runBytecode(ILaunch launch, IProject project, Entity entity) throws CoreException {
		generator.printTestbench(entity);

		DebugPlugin manager = DebugPlugin.getDefault();
		manager.addDebugEventListener(new Launcher());

		ILaunchConfiguration config = launch.getLaunchConfiguration();
		config.getAttribute(LIBRARIES, LIBRARIES_DEFAULT);
		getSources(config, project, entity);

		IFolder classes = project.getFolder(new Path(FOLDER_SIM + '/' + FOLDER_CLASSES));
		String classpath = classes.getLocation().toString();
		String name = entity.getName();
		if (CoreUtil.needsWrapper(entity)) {
			name += "_test";
		}
		ProcessBuilder pb = new ProcessBuilder("java", "-cp", classpath, name);

		String defaultWorkingDir = getDefaultWorkingDirectory(config);
		String workingDir = config.getAttribute(WORKING_DIRECTORY, defaultWorkingDir);
		pb.directory(new File(workingDir));
		try {
			new RuntimeProcess(launch, pb.start(), "java", null);
		} catch (IOException e) {
			SynflowCore.log(e);
		}
	}

	/**
	 * Runs Modelsim exporter and then vsim to execute the script.
	 *
	 * @param launch
	 * @param project
	 * @param entity
	 * @throws CoreException
	 */
	private void runModelsimExporter(ILaunch launch, IProject project, Entity entity)
			throws CoreException {

		// print testbench (either Verilog or VHDL)
		ICodeGenerator generator = SynflowCore.getGenerator(project);
		if (generator != null) {
			generator.fullBuild(entity);
		}

		IExporter exporter = SynflowCore.getDefault().getInstance(IExporter.class, "Modelsim");
		ProcessBuilder pb = exporter.buildProject(entity, null);
		try {
			Process process = pb.start();
			new RuntimeProcess(launch, process, "vsim", null);
		} catch (IOException e) {
			throw new CoreException(
					new Status(IStatus.ERROR, NgDesign.PLUGIN_ID, e.getMessage(), e));
		}
	}

}
