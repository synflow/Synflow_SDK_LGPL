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
package com.synflow.ngDesign.ui.internal.wizards;

import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import com.synflow.core.SynflowCore;

/**
 * This class defines a new project wizard.
 *
 * @author Matthieu Wipliez
 *
 */
public class NewProjectWizard extends Wizard implements IExecutableExtension, INewWizard {

	public static final String WIZARD_ID = "com.synflow.ngDesign.ui.wizards.newProject";

	private IConfigurationElement fConfigElement;

	private NewProjectPage mainPage;

	@Override
	public void addPages() {
		mainPage = new NewProjectPage("new project");
		mainPage.setDescription("Creates a new project");
		mainPage.setTitle("New Project");
		addPage(mainPage);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	@Override
	public boolean performFinish() {
		// get a project descriptor
		IProject project = mainPage.getProjectHandle();

		// location of project
		URI uri = mainPage.useDefaults() ? null : mainPage.getLocationURI();

		try {
			new ProjectCreator().createProject(project, uri, mainPage.getGenerator());
		} catch (CoreException e) {
			SynflowCore.log(e);
		}

		BasicNewProjectResourceWizard.updatePerspective(fConfigElement);

		return true;
	}

	/**
	 * Stores the configuration element for the wizard. The config element will be used in
	 * <code>performFinish</code> to set the result perspective.
	 */
	public void setInitializationData(IConfigurationElement cfig, String propertyName, Object data) {
		fConfigElement = cfig;
	}

}
