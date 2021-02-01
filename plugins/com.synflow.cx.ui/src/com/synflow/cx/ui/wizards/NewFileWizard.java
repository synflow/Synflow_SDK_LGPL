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
package com.synflow.cx.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

/**
 * This class provides a wizard to create a new file.
 *
 * @author Matthieu Wipliez
 */
public abstract class NewFileWizard extends Wizard implements INewWizard {

	private NewFilePage newFilePage;

	protected IStructuredSelection selection;

	private IWorkbench workbench;

	@Override
	public void addPages() {
		newFilePage = new NewFilePage(getType(), selection);
		addPage(newFilePage);
	}

	/**
	 * Returns a stream containing the initial contents to be given to new file resource instances.
	 *
	 * @return initial contents to be given to new file resource instances
	 */
	private InputStream getInitialContents() {
		final String author = System.getProperty("user.name");
		final int year = Calendar.getInstance().get(Calendar.YEAR);
		String pack = newFilePage.getPackage();
		String entityName = newFilePage.getEntityName();

		CharSequence charSeq = getStringContents(author, year, pack, entityName);
		return new ByteArrayInputStream(charSeq.toString().getBytes());
	}

	abstract protected CharSequence getStringContents(String author, int year, String package_,
			String entityName);

	protected abstract String getType();

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		this.workbench = workbench;

		setNeedsProgressMonitor(true);
		setWindowTitle("New Cx " + getType());
	}

	@Override
	public boolean performFinish() {
		NewFilePage page = (NewFilePage) getPages()[0];
		IFile file = page.createNewFile(getInitialContents());
		if (file == null) {
			return false;
		}

		// Open editor on new file.
		IWorkbenchWindow dw = workbench.getActiveWorkbenchWindow();
		try {
			if (dw != null) {
				BasicNewResourceWizard.selectAndReveal(file, dw);
				IWorkbenchPage activePage = dw.getActivePage();
				if (activePage != null) {
					IDE.openEditor(activePage, file, true);
				}
			}
		} catch (PartInitException e) {
			MessageDialog.openError(dw.getShell(), "Problem opening editor", e.getMessage());
		}

		return true;
	}

}
