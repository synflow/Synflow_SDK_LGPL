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
package com.synflow.ngDesign.internal.synthesis;

import static com.synflow.models.util.EcoreHelper.getEObject;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.ENTITY;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.ENTITYPR1;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.ENTITYPR2;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.PROJECT;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.SYNTHESIS_CONFIGURATION;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.SYNTHESIS_GENERATE_PROJECT;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.SYNTHESIS_PARTIAL_RECONFIGURATION;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.debug.core.model.RuntimeProcess;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.synflow.core.ICodeGenerator;
import com.synflow.core.IExporter;
import com.synflow.core.SynflowCore;
import com.synflow.core.util.CoreUtil;
import com.synflow.models.dpn.Entity;
import com.synflow.models.util.dom.DomUtil;
import com.synflow.ngDesign.NgDesign;

/**
 * This class defines a delegate for synthesis.
 *
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 *
 */
public class LaunchDelegateSynthesis extends LaunchConfigurationDelegate {

	private void errorWithConfig(String message) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		try {
			root.deleteMarkers(IMarker.PROBLEM, false, IResource.DEPTH_ZERO);
			IMarker marker = root.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			marker.setAttribute(IMarker.MESSAGE, message);
		} catch (CoreException e) {
			SynflowCore.log(e);
		}
	}

	@Override
	public void launch(ILaunchConfiguration configuration, final String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {

		String projectName = configuration.getAttribute(PROJECT, "");

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		// Generic entity
		String className = configuration.getAttribute(ENTITY, "");
		IFile file = CoreUtil.getIrFile(project, className);
		Entity entity = getEObject(file, Entity.class);

		// print testbench (either Verilog or VHDL)
		ICodeGenerator generator = SynflowCore.getGenerator(project);
		if (generator != null) {
			generator.fullBuild(entity);
		}

		String configFile = configuration.getAttribute(SYNTHESIS_CONFIGURATION, "");
		try {
			Document doc = DomUtil.parseDocument(project.getFile(configFile).getContents());
			NodeList list = doc.getDocumentElement().getElementsByTagName("vendor");
			if (list.getLength() > 0) {
				String vendor = list.item(0).getTextContent();
				IExporter exporter = SynflowCore.getDefault().getInstance(IExporter.class, vendor);

				// the absence of this attribute means "generate project"
				// default is true so we always generate a project once
				if (configuration.getAttribute(SYNTHESIS_GENERATE_PROJECT, true)) {
					doc.getDocumentElement().setAttribute("GenerateProject", "true");
					ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
					wc.setAttribute(SYNTHESIS_GENERATE_PROJECT, false);
					wc.doSave();
				}

				ProcessBuilder pb = exporter.createProject(entity, doc);
				if (pb != null) {
					// runs synthesis if the exporter returned a process builder
					Process process = pb.start();
					RuntimeProcess run = new RuntimeProcess(launch, process, "Create Project", null);

					while (!run.isTerminated()) {
						// Wait for run to end before going on
					}
				}

				pb = exporter.buildProject(entity, doc);
				if (pb != null) {
					// runs synthesis if the exporter returned a process builder
					Process process = pb.start();
					RuntimeProcess run = new RuntimeProcess(launch, process, "Launch Synthesis", null);

					while (!run.isTerminated()) {
						// Wait for run to end before going on }
					}
				}

				// If running a synthesis with Partial Reconfiguration activated
				// runs a specific Out-Of-Context synthesis
				if (configuration.getAttribute(SYNTHESIS_PARTIAL_RECONFIGURATION, true)) {
					doc.getDocumentElement().setAttribute("PartialReconfiguration", "true");
					ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
					wc.setAttribute(SYNTHESIS_PARTIAL_RECONFIGURATION, false);
					wc.doSave();
					launchPRSynthesis(configuration, launch, project, vendor, file, entity, generator, doc, exporter);
				}
			}
		} catch (

		IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, NgDesign.PLUGIN_ID, e.getMessage(), e));
		}
	}

	private void launchPRSynthesis(ILaunchConfiguration configuration, ILaunch launch, IProject project,
			String className, IFile file, Entity entity, ICodeGenerator generator, Document doc, IExporter exporter)
			throws CoreException {

		/*
		 * Wait for first synthesis to complete then make the entity a black box
		 * for the PR and start another synthesis
		 */
		try {
			ProcessBuilder pb = exporter.setupPR(entity);
			if (pb != null) {
				Process process = pb.start();
				RuntimeProcess run = new RuntimeProcess(launch, process, "Set Up Partial Reconfiguration", null);

				while (!run.isTerminated()) {
					// Wait for run to end before going on
				}
			}

			/*
			 * Generate files for all variants
			 *
			 * TODO This should be generic, not hard coded
			 */

			// Entity 2 when performing Partial Reconfiguration
			className = configuration.getAttribute(ENTITYPR1, "");
			file = CoreUtil.getIrFile(project, className);
			Entity variant1 = getEObject(file, Entity.class);
			if (generator != null && variant1 != null) {
				generator.fullBuild(variant1);
			} else {
				errorWithConfig("Error in your configuration, please check your code generator (should be Verilog) "
						+ "and Entities for the Partial Reconfiguration");
				return;
			}

			// Entity 3 when performing Partial Reconfiguration
			className = configuration.getAttribute(ENTITYPR2, "");
			file = CoreUtil.getIrFile(project, className);
			Entity variant2 = getEObject(file, Entity.class);
			if (generator != null && variant2 != null) {
				generator.fullBuild(variant2);
			} else {
				errorWithConfig("Error in your configuration, please check your code generator (should be Verilog) "
						+ "and Entities for the Partial Reconfiguration");
				return;
			}

			/*
			 * Execute the Out-of-Context synthesis for the PR
			 */
			pb = exporter.exportPR(entity, variant1, variant2, doc);
			if (pb != null) {
				// runs synthesis if the exporter returned a process builder
				Process process = pb.start();
				RuntimeProcess run = new RuntimeProcess(launch, process, "Synthesis Partial Reconfiguration", null);
				while (!run.isTerminated()) {
					// Wait for run to end before going on
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
