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
package com.synflow.ngDesign.ui.internal.wizards;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

/**
 * This class defines a wizard for creating a new package.
 *
 * @author Matthieu Wipliez
 *
 */
public class NewPackageWizard extends Wizard implements INewWizard {

	public static final String WIZARD_ID = "com.synflow.ngDesign.ui.wizards.newPackage";

	private IStructuredSelection selection;

	private IWorkbench workbench;

	@Override
	public void addPages() {
		NewPackagePage page = new NewPackagePage(selection);
		page.setWizard(this);
		addPage(page);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;

		setNeedsProgressMonitor(true);
		setWindowTitle("New package");
	}

	@Override
	public boolean isHelpAvailable() {
		return false;
	}

	@Override
	public boolean performFinish() {
		NewPackagePage page = (NewPackagePage) getPages()[0];
		IFolder folder = page.createNewFolder();
		if (folder == null) {
			return false;
		}

		BasicNewResourceWizard.selectAndReveal(folder, workbench.getActiveWorkbenchWindow());

		return true;
	}

}
