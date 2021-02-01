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
package com.synflow.ngDesign;

import static com.synflow.core.ICoreConstants.FOLDER_SIM;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.ILaunchConfiguration;

/**
 * This class defines constants for launch configuration.
 *
 * @author Matthieu Wipliez
 *
 */
public interface ICxLaunchConfigurationConstants {
	/**
	 * Enable or Disable PR depending on the version (with or withing bRANE)
	 */
	boolean ENABLE_PARTIAL_RECONFIGURATION = false;

	/**
	 * name of the entity to export/simulate
	 */
	String ENTITY = NgDesign.PLUGIN_ID + ".entity";

	/**
	 * list of additional JAR files
	 */
	String LIBRARIES = NgDesign.PLUGIN_ID + ".compile.libraries";

	/**
	 * default: no libraries
	 */
	List<String> LIBRARIES_DEFAULT = Collections.emptyList();

	/**
	 * (simulation) print cycles
	 */
	String PRINT_CYCLES = NgDesign.PLUGIN_ID + ".runtime.printCycles";

	/**
	 * default: do not print cycle number
	 */
	boolean PRINT_CYCLES_DEFAULT = false;

	/**
	 * name of the project in which the entity is defined
	 */
	String PROJECT = NgDesign.PLUGIN_ID + ".project";

	String SIMULATION_MODELSIM = NgDesign.PLUGIN_ID + ".simulation.modelsim";

	boolean SIMULATION_MODELSIM_DEFAULT = false;

	/**
	 * list of source files to compile
	 */
	String SOURCES = NgDesign.PLUGIN_ID + ".compile.sources";

	/**
	 * default: no additional source files
	 */
	List<String> SOURCES_DEFAULT = Collections.emptyList();

	/**
	 * name of XML file describing configuration to use for synthesis
	 */
	String SYNTHESIS_CONFIGURATION = NgDesign.PLUGIN_ID + ".synthesis.configuration";

	/**
	 * name of attribute that governs whether we should generate a project or not
	 */
	String SYNTHESIS_GENERATE_PROJECT = NgDesign.PLUGIN_ID + ".synthesis.generateProject";

	/**
	 * name of attribute that sets if we perform a Partial Reconfig synthesis or not
	 */
	String SYNTHESIS_PARTIAL_RECONFIGURATION = NgDesign.PLUGIN_ID + ".synthesis.partialReconfiguration";

	/**
	 * name of the entity number 2 when we perform a Partial Reconfig synthesis
	 */
	String ENTITYPR1 = NgDesign.PLUGIN_ID + ".entityPR1";

	/**
	 * name of the entity number 3 when we perform a Partial Reconfig synthesis
	 */
	String ENTITYPR2 = NgDesign.PLUGIN_ID + ".entityPR2";

	/**
	 * type for "simulation" launch configuration
	 */
	String TYPE_SIMULATION = NgDesign.PLUGIN_ID + ".launchConfigurationSimulation";

	/**
	 * type for synthesis launch configuration (Run As > Hardware on FPGA)
	 */
	String TYPE_SYNTHESIS = NgDesign.PLUGIN_ID + ".launchConfigurationSynthesis";

	/**
	 * (simulation) working directory
	 */
	String WORKING_DIRECTORY = NgDesign.PLUGIN_ID + ".runtime.workingDirectory";

	public static String getDefaultWorkingDirectory(ILaunchConfiguration configuration) {
		try {
			String projectName = configuration.getAttribute(PROJECT, "");
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IStatus status = workspace.validateName(projectName, IResource.PROJECT);
			if (status.isOK()) {
				IProject project = workspace.getRoot().getProject(projectName);
				IPath location = project.getFolder(FOLDER_SIM).getLocation();
				if (location != null) {
					return location.toOSString();
				}
			}
			// status not ok, or null location: fall-through
		} catch (CoreException e) {
			// ignore and fall-through
		}

		return "";
	}

}
