/*******************************************************************************
 * Copyright (c) 2014 Synflow SAS, 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 *    Matthieu Wipliez - modified
 *******************************************************************************/
package com.synflow.ngDesign.ui.internal.navigator.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.synflow.ngDesign.ui.internal.wizards.NewProjectWizard;

/**
 * This class defines a New Synflow Project action based on
 * {@link org.eclipse.ui.actions.NewProjectAction}
 * 
 * @author Matthieu Wipliez
 * 
 */
public class NewSynflowProjectAction extends Action {

	/**
	 * The wizard dialog width
	 */
	private static final int SIZING_WIZARD_WIDTH = 500;

	/**
	 * The wizard dialog height
	 */
	private static final int SIZING_WIZARD_HEIGHT = 500;

	/**
	 * The workbench window this action will run in
	 */
	private IWorkbenchWindow window;

	/**
	 * This default constructor allows the the action to be called from the welcome page.
	 */
	public NewSynflowProjectAction() {
		this(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	}

	/**
	 * Creates a new action for launching the new project selection wizard.
	 * 
	 * @param window
	 *            the workbench window to query the current selection and shell for opening the
	 *            wizard.
	 */
	public NewSynflowProjectAction(IWorkbenchWindow window) {
		super("Synflow Project...");
		if (window == null) {
			throw new IllegalArgumentException();
		}
		this.window = window;
		ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
		setDisabledImageDescriptor(images
				.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
		setToolTipText("New Synflow Project");
	}

	/*
	 * (non-Javadoc) Method declared on IAction.
	 */
	public void run() {
		// Create wizard selection wizard.
		IWorkbench workbench = PlatformUI.getWorkbench();
		NewProjectWizard wizard = new NewProjectWizard();
		ISelection selection = window.getSelectionService().getSelection();
		IStructuredSelection selectionToPass = StructuredSelection.EMPTY;
		if (selection instanceof IStructuredSelection) {
			selectionToPass = (IStructuredSelection) selection;
		}
		wizard.init(workbench, selectionToPass);

		wizard.setForcePreviousAndNextButtons(true);

		// Create wizard dialog.
		WizardDialog dialog = new WizardDialog(null, wizard);
		dialog.create();
		dialog.getShell().setSize(Math.max(SIZING_WIZARD_WIDTH, dialog.getShell().getSize().x),
				SIZING_WIZARD_HEIGHT);

		// Open wizard.
		dialog.open();
	}
}
