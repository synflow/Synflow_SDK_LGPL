/*******************************************************************************
 * Copyright (c) 2000, 2008, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.synflow.ngDesign.ui.internal.navigator.actions;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.part.ResourceTransfer;

/**
 * Standard action for copying the currently selected resources to the clipboard.
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * 
 * @since 2.0
 */
public class CopyAction extends SelectionListenerAction {

	/**
	 * The id of this action.
	 */
	public static final String ID = PlatformUI.PLUGIN_ID + ".CopyAction"; //$NON-NLS-1$

	/**
	 * System clipboard
	 */
	private Clipboard clipboard;

	/**
	 * Associated paste action. May be <code>null</code>
	 */
	private PasteAction pasteAction;

	/**
	 * The shell in which to show any dialogs.
	 */
	private Shell shell;

	/**
	 * Creates a new action.
	 *
	 * @param shell
	 *            the shell for any dialogs
	 * @param clipboard
	 *            a platform clipboard
	 */
	public CopyAction(Shell shell, Clipboard clipboard) {
		super("Copy");
		Assert.isNotNull(shell);
		Assert.isNotNull(clipboard);
		this.shell = shell;
		this.clipboard = clipboard;
		setToolTipText("Copy selected resource(s)");
		setId(CopyAction.ID);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, "CopyHelpId"); //$NON-NLS-1$
	}

	/**
	 * Creates a new action.
	 *
	 * @param shell
	 *            the shell for any dialogs
	 * @param clipboard
	 *            a platform clipboard
	 * @param pasteAction
	 *            a paste action
	 * 
	 * @since 2.0
	 */
	public CopyAction(Shell shell, Clipboard clipboard, PasteAction pasteAction) {
		this(shell, clipboard);
		this.pasteAction = pasteAction;
	}

	/**
	 * The <code>CopyAction</code> implementation of this method defined on <code>IAction</code>
	 * copies the selected resources to the clipboard.
	 */
	@Override
	public void run() {
		List<? extends IResource> selectedResources = getSelectedResources();

		// cannot copy packages/source folder
		SelectionUtil.discardTreeElements(selectedResources, getStructuredSelection());

		IResource[] resources = selectedResources.toArray(new IResource[selectedResources.size()]);

		// Get the file names and a string representation
		final int length = resources.length;
		int actualLength = 0;
		String[] fileNames = new String[length];
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			IPath location = resources[i].getLocation();
			// location may be null. See bug 29491.
			if (location != null) {
				fileNames[actualLength++] = location.toOSString();
			}
			if (i > 0) {
				buf.append("\n"); //$NON-NLS-1$
			}
			buf.append(resources[i].getName());
		}
		// was one or more of the locations null?
		if (actualLength < length) {
			String[] tempFileNames = fileNames;
			fileNames = new String[actualLength];
			for (int i = 0; i < actualLength; i++) {
				fileNames[i] = tempFileNames[i];
			}
		}

		if (resources.length > 0 && fileNames.length > 0) {
			setClipboard(resources, fileNames, buf.toString());
		} else {
			clipboard.clearContents();
		}

		// update the enablement of the paste action
		// workaround since the clipboard does not suppot callbacks
		if (pasteAction != null && pasteAction.getStructuredSelection() != null) {
			pasteAction.selectionChanged(pasteAction.getStructuredSelection());
		}
	}

	/**
	 * Set the clipboard contents. Prompt to retry if clipboard is busy.
	 * 
	 * @param resources
	 *            the resources to copy to the clipboard
	 * @param fileNames
	 *            file names of the resources to copy to the clipboard
	 * @param names
	 *            string representation of all names
	 */
	private void setClipboard(IResource[] resources, String[] fileNames, String names) {
		try {
			// set the clipboard contents
			if (fileNames.length > 0) {
				clipboard.setContents(new Object[] { resources, fileNames, names },
						new Transfer[] { ResourceTransfer.getInstance(), FileTransfer.getInstance(),
								TextTransfer.getInstance() });
			} else {
				clipboard.setContents(new Object[] { resources, names }, new Transfer[] {
						ResourceTransfer.getInstance(), TextTransfer.getInstance() });
			}
		} catch (SWTError e) {
			if (e.code != DND.ERROR_CANNOT_SET_CLIPBOARD) {
				throw e;
			}
			if (MessageDialog.openQuestion(shell, "Problem with copy title", // TODO //$NON-NLS-1$
																				// ResourceNavigatorMessages.CopyToClipboardProblemDialog_title,
					"Problem with copy.")) { //$NON-NLS-1$
				setClipboard(resources, fileNames, names);
			}
		}
	}

	/**
	 * The <code>CopyAction</code> implementation of this <code>SelectionListenerAction</code>
	 * method enables this action if one or more resources of compatible types are selected.
	 */
	@Override
	protected boolean updateSelection(IStructuredSelection selection) {
		if (!super.updateSelection(selection)) {
			return false;
		}

		if (getSelectedNonResources().size() > 0) {
			return false;
		}

		List<? extends IResource> selectedResources = getSelectedResources();
		if (selectedResources.size() == 0) {
			return false;
		}

		boolean projSelected = selectionIsOfType(IResource.PROJECT);
		boolean fileFoldersSelected = selectionIsOfType(IResource.FILE | IResource.FOLDER);
		if (!projSelected && !fileFoldersSelected) {
			return false;
		}

		// selection must be homogeneous
		if (projSelected && fileFoldersSelected) {
			return false;
		}

		// must have a common parent
		IContainer firstParent = selectedResources.get(0).getParent();
		if (firstParent == null) {
			return false;
		}

		for (IResource currentResource : selectedResources) {
			if (!currentResource.getParent().equals(firstParent)) {
				return false;
			}
			// resource location must exist
			if (currentResource.getLocationURI() == null) {
				return false;
			}
		}

		return true;
	}

}
