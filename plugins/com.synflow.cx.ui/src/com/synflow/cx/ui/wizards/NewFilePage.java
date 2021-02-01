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

import static com.synflow.core.ICoreConstants.FILE_EXT_CX;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
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
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;

import com.synflow.core.SynflowCore;
import com.synflow.core.layout.ITreeElement;
import com.synflow.core.layout.Package;

/**
 * This class provides a page to create a new text file.
 *
 * @author Matthieu Wipliez
 */
public class NewFilePage extends WizardPage implements ModifyListener {

	private static Pattern id = Pattern.compile("[A-Z][a-zA-Z0-9_]*");

	private static final int SIZING_TEXT_FIELD_WIDTH = 250;

	private static String getTypeUpper(String type) {
		return type.substring(0, 1).toUpperCase() + type.substring(1);
	}

	private Package containingPackage;

	private Label labelPackage;

	private Text resourceNameField;

	private IStructuredSelection selection;

	private final String type;

	public NewFilePage(String type, IStructuredSelection selection) {
		super("New" + getTypeUpper(type));
		this.selection = selection;

		setTitle("New Cx " + type);
		setDescription("Creates a new Cx " + type + ".");
		this.type = type;
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		Label label1 = new Label(composite, SWT.NONE);
		label1.setText("Package:");
		label1.setFont(this.getFont());

		labelPackage = new Label(composite, SWT.NONE);
		labelPackage.setFont(this.getFont());

		Label label2 = new Label(composite, SWT.WRAP);
		label2.setText(getTypeUpper(type) + " name:");
		label2.setFont(this.getFont());

		// resource name entry field
		resourceNameField = new Text(composite, SWT.BORDER);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		resourceNameField.setLayoutData(data);
		resourceNameField.setFont(this.getFont());

		initializePage();

		// add modify listener *after* page is initialized
		// so page is not complete, but no error message is shown initially
		resourceNameField.addModifyListener(this);

		// Show description on opening
		setControl(composite);
	}

	/**
	 * Creates a new file resource in the selected container and with the selected name. Creates any
	 * missing resource containers along the path; does nothing if the container resources already
	 * exist.
	 *
	 * @param initialContents
	 *            initial contents of the file
	 *
	 * @return the created file resource, or <code>null</code> if the file was not created
	 */
	public IFile createNewFile(final InputStream initialContents) {
		final IFile newFileHandle = getFile();

		IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {
				CreateFileOperation op = new CreateFileOperation(newFileHandle, null,
						initialContents, getTitle());
				try {
					// see bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=219901
					// directly execute the operation so that the undo state is
					// not preserved. Making this undoable resulted in too many
					// accidental file deletions.
					op.execute(monitor, WorkspaceUndoUtil.getUIInfoAdapter(getShell()));
				} catch (final ExecutionException e) {
					getContainer().getShell().getDisplay().syncExec(new Runnable() {
						@Override
						public void run() {
							if (e.getCause() instanceof CoreException) {
								ErrorDialog.openError(getContainer().getShell(),
										"Could not create " + type, null,
										((CoreException) e.getCause()).getStatus());
							} else {
								SynflowCore.log(e.getCause());
								MessageDialog.openError(getContainer().getShell(),
										"Could not create " + type,
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
			MessageDialog.open(MessageDialog.ERROR, getContainer().getShell(), "Could not create "
					+ type, NLS.bind("Internal error: {0}", e.getTargetException().getMessage()),
					SWT.SHEET);
			return null;
		}

		return newFileHandle;
	}

	protected final String getEntityName() {
		return resourceNameField.getText();
	}

	private IFile getFile() {
		String fileName = resourceNameField.getText() + "." + FILE_EXT_CX;
		IFolder folder = (IFolder) containingPackage.getResource();
		return folder.getFile(fileName);
	}

	protected final String getPackage() {
		return containingPackage.getName();
	}

	protected CharSequence getStringContents(String author, int year) {
		return null;
	}

	/**
	 * Initializes this page's controls.
	 */
	protected void initializePage() {
		Iterator<?> it = selection.iterator();
		if (it.hasNext()) {
			Object next = it.next();
			if (next instanceof ITreeElement) {
				ITreeElement element = (ITreeElement) next;
				if (element.isPackage()) {
					containingPackage = (Package) element;
					labelPackage.setText(containingPackage.getName());
				}
			}
		}

		// cannot complete until a name is entered
		setPageComplete(false);
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
			setErrorMessage("Enter a " + type + " name.");
			return false;
		}

		// check first letter is uppercase
		char first = text.charAt(0);
		if (!Character.isUpperCase(first)) {
			setErrorMessage("A " + type + " name must begin with a uppercase letter");
			return false;
		}

		if (!id.matcher(text).matches()) {
			setErrorMessage("Invalid " + type + " name: '" + text
					+ "' is not a valid identifier. Must match \"" + id.toString() + "\".");
			return false;
		}

		IFile file = getFile();
		if (file.exists()) {
			setErrorMessage("An entity with the same name already exists.");
			return false;
		}

		setErrorMessage(null);

		return true;
	}

}
