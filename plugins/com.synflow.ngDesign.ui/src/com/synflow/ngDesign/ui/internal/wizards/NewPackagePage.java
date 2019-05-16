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

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ide.undo.CreateFolderOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;

import com.synflow.core.SynflowCore;
import com.synflow.core.layout.ITreeElement;
import com.synflow.core.layout.ProjectLayout;
import com.synflow.core.layout.SourceFolder;
import com.synflow.models.ir.util.IrUtil;

/**
 * This class defines a page for creating a new package.
 *
 * @author Matthieu Wipliez
 *
 */
public class NewPackagePage extends WizardPage implements ModifyListener {

	private static Pattern id = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");

	private static final int SIZING_TEXT_FIELD_WIDTH = 250;

	private IProject project;

	private Text resourceNameField;

	private IStructuredSelection selection;

	public NewPackagePage(IStructuredSelection selection) {
		super("NewPackage");
		this.selection = selection;

		setTitle("New package");
		setDescription("Create a new package (for example: com.acme.anvil)");
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		Label label = new Label(composite, SWT.WRAP);
		label.setText("Package:");
		label.setFont(this.getFont());

		// resource name entry field
		resourceNameField = new Text(composite, SWT.BORDER);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		resourceNameField.setLayoutData(data);

		initializePage();

		// add modify listener *after* page is initialized
		// so page is not complete, but no error message is shown initially
		resourceNameField.addModifyListener(this);

		// Show description on opening
		setControl(composite);
	}

	public IFolder createNewFolder() {
		final IFolder newFolderHandle = getFolder();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				CreateFolderOperation op = new CreateFolderOperation(newFolderHandle, null,
						"New Package");
				try {
					// see bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=219901
					// directly execute the operation so that the undo state is
					// not preserved. Making this undoable can result in accidental
					// folder (and file) deletions.
					op.execute(monitor, WorkspaceUndoUtil.getUIInfoAdapter(getShell()));
				} catch (final ExecutionException e) {
					getContainer().getShell().getDisplay().syncExec(new Runnable() {
						public void run() {
							if (e.getCause() instanceof CoreException) {
								ErrorDialog.openError(getContainer().getShell(),
										"Could not create package", null,
										((CoreException) e.getCause()).getStatus());
							} else {
								SynflowCore.log(e.getCause());
								MessageDialog.openError(getContainer().getShell(),
										"Could not create package",
										NLS.bind("Internal error: {0}", e.getCause().getMessage()));
							}
						}
					});
				}
			}
		};

		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			// ExecutionExceptions are handled above, but unexpected runtime
			// exceptions and errors may still occur.
			SynflowCore.log(e.getTargetException());
			MessageDialog
					.open(MessageDialog.ERROR, getContainer().getShell(),
							"Could not create package",
							NLS.bind("Internal error: {0}", e.getTargetException().getMessage()),
							SWT.SHEET);
			return null;
		}

		return newFolderHandle;
	}

	private IFolder getFolder() {
		SourceFolder srcFolder = ProjectLayout.getSourceFolder(project);
		String text = resourceNameField.getText();
		IPath path = new Path(IrUtil.getFile(text));
		return srcFolder.getResource().getFolder(path);
	}

	/**
	 * Initializes this page's controls.
	 */
	protected void initializePage() {
		boolean valid = false;
		Iterator<?> it = selection.iterator();
		if (it.hasNext()) {
			Object next = it.next();
			if (next instanceof ITreeElement) {
				ITreeElement element = (ITreeElement) next;
				IResource resource = element.getResource();
				project = resource.getProject();

				if (element.isPackage()) {
					resourceNameField.setText(element.getName() + ".");
				} else {
					SourceFolder folder = (SourceFolder) element;
					if (folder.getPackages().length == 0) {
						// if no packages exist, suggest project name as initial package
						resourceNameField.setText(project.getName().toLowerCase());
						valid = true;
					}
				}
			}
		}

		// move cursor at the end
		resourceNameField.setSelection(resourceNameField.getCharCount());

		setPageComplete(valid);
	}

	@Override
	public void modifyText(ModifyEvent e) {
		setPageComplete(validatePage());
	}

	/**
	 * Returns whether this page's controls currently all contain valid values.
	 *
	 * @return <code>true</code> if all controls are valid, and <code>false</code> if at least one
	 *         is invalid
	 */
	protected boolean validatePage() {
		String text = resourceNameField.getText();
		if (text.isEmpty()) {
			setErrorMessage("Enter a package name.");
			return false;
		}

		if (text.startsWith(".") || text.endsWith(".")) {
			setErrorMessage("Invalid package name. A package name cannot start or end with a dot.");
			return false;
		}

		String[] segments = text.split("\\.");
		for (String segment : segments) {
			if (segment.isEmpty()) {
				setErrorMessage("Invalid package name. A package name must not contain two consecutive dots.");
				return false;
			}

			if (!id.matcher(segment).matches()) {
				setErrorMessage("Invalid package name. '" + segment
						+ "' is not a valid identifier. Must match \"" + id.toString() + "\"");
				return false;
			}
		}

		IFolder folder = getFolder();
		if (folder.exists()) {
			setErrorMessage("Package already exists.");
			return false;
		}

		setMessage(null);
		setErrorMessage(null);

		return true;
	}

}
