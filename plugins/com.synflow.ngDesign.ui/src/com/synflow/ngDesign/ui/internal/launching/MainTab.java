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
package com.synflow.ngDesign.ui.internal.launching;

import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.ENTITY;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.PROJECT;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.synflow.core.SynflowCore;
import com.synflow.cx.cx.Instantiable;
import com.synflow.ngDesign.ui.internal.NgDesignUi;

/**
 * This class defines the main tab for simulation configuration type.
 *
 * @author Matthieu Wipliez
 */
public abstract class MainTab extends AbstractLaunchConfigurationTab {

	private static class ProjectLabelProvider extends LabelProvider {

		@Override
		public Image getImage(Object element) {
			ImageDescriptor desc;
			desc = NgDesignUi.getImageDescriptor("icons/sfprj.gif");
			return desc.createImage();
		}

		@Override
		public String getText(Object element) {
			return ((IProject) element).getName();
		}

	}

	/**
	 * A listener which handles widget change events for the controls in this tab.
	 */
	private class WidgetListener implements ModifyListener, SelectionListener {

		@Override
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// do nothing
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			if (source == fProjButton) {
				handleProjectButtonSelected();
			} else if (source == fSearchButton) {
				handleSearchButtonSelected();
			} else {
				updateLaunchConfigurationDialog();
			}
		}
	}

	private Text entity;

	protected final WidgetListener fListener = new WidgetListener();

	private Button fProjButton;

	private Button fSearchButton;

	private Text project;

	/**
	 * Chooses a Synflow project.
	 *
	 * @return
	 */
	private IProject chooseSynflowProject() {
		ILabelProvider labelProvider = new ProjectLabelProvider();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(getShell(),
				labelProvider);
		dialog.setTitle("Project selection");
		dialog.setMessage("Choose an existing project");

		dialog.setElements(SynflowCore.getProjects());

		IProject project = getProjectFromText();
		if (project != null) {
			dialog.setInitialSelections(new Object[] { project });
		}
		if (dialog.open() == Window.OK) {
			return (IProject) dialog.getFirstResult();
		}
		return null;
	}

	/**
	 * Creates additional widgets. To be extended by subclasses.
	 *
	 * @param parent
	 *            the parent composite
	 */
	protected void createAdditionalControls(Composite composite) {
		// do nothing
	}

	protected void createPRControls(Composite composite) {
		// do nothing
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		setControl(composite);

		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 0;
		composite.setLayout(layout);

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(data);

		createProjectControl(composite);
		createSpacer(composite);
		createEntityControl(composite);
		createSpacer(composite);
		createAdditionalControls(composite);
		createSpacer(composite);
		createPRControls(composite);
	}

	/**
	 * Creates the widgets for specifying a main type.
	 *
	 * @param parent
	 *            the parent composite
	 */
	private void createEntityControl(Composite composite) {
		final Group group = new Group(composite, SWT.NONE);
		group.setFont(getFont());
		group.setText("&Entity:");
		group.setLayout(new GridLayout(2, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		entity = new Text(group, SWT.BORDER | SWT.SINGLE);
		entity.setFont(getFont());
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		entity.setLayoutData(data);
		entity.addModifyListener(fListener);

		fSearchButton = createPushButton(group, "Search...", null);
		fSearchButton.addSelectionListener(fListener);
	}

	private void createProjectControl(Composite composite) {
		final Group group = new Group(composite, SWT.NONE);
		group.setFont(getFont());
		group.setText("&Project:");
		group.setLayout(new GridLayout(2, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		project = new Text(group, SWT.BORDER | SWT.SINGLE);
		project.setFont(getFont());
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		project.setLayoutData(data);
		project.addModifyListener(fListener);

		fProjButton = createPushButton(group, "Browse...", null);
		fProjButton.addSelectionListener(fListener);
	}

	private void createSpacer(Composite composite) {
		Label lbl = new Label(composite, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 1;
		lbl.setLayoutData(gd);
	}

	protected final Font getFont() {
		return getControl().getFont();
	}

	protected abstract String getIcon();

	@Override
	public final Image getImage() {
		return NgDesignUi.getImageDescriptor("icons/" + getIcon()).createImage();
	}

	@Override
	public String getName() {
		return "Main";
	}

	protected final IProject getProjectFromText() {
		String name = project.getText().trim();
		if (name.isEmpty()) {
			return null;
		}

		return ResourcesPlugin.getWorkspace().getRoot().getProject(name);
	}

	/**
	 * Show a dialog that lets the user select a project. This in turn provides context for the main
	 * type, allowing the user to key a main type name, or constraining the search for main types to
	 * the specified project.
	 */
	protected void handleProjectButtonSelected() {
		IProject candidate = chooseSynflowProject();
		if (candidate == null) {
			return;
		}

		project.setText(candidate.getName());
	}

	private void handleSearchButtonSelected() {
		// TODO search instantiables
		// IProject project = getProjectFromText();
		Instantiable[] instantiables = new Instantiable[0];

		EntitySelectionDialog dialog = new EntitySelectionDialog(null, instantiables,
				"Select entity");
		int result = dialog.open();
		if (result == Window.OK) {
			Instantiable instantiable = (Instantiable) dialog.getFirstResult();
			entity.setText(AbstractLaunchShortcut.getEntityName(instantiable));
		}
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		String name = "";
		try {
			name = configuration.getAttribute(PROJECT, "");
		} catch (CoreException e) {
			setErrorMessage(e.getStatus().getMessage());
		}
		project.setText(name);

		String className = "";
		try {
			className = configuration.getAttribute(ENTITY, "");
		} catch (CoreException e) {
			setErrorMessage(e.getStatus().getMessage());
		}
		entity.setText(className);
	}

	@Override
	public boolean isValid(ILaunchConfiguration config) {
		setErrorMessage(null);
		setMessage(null);

		String name = project.getText().trim();
		if (name.length() > 0) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IStatus status = workspace.validateName(name, IResource.PROJECT);
			if (status.isOK()) {
				IProject project = workspace.getRoot().getProject(name);
				if (!project.exists()) {
					setErrorMessage("Project \"" + name + "\" does not exist");
					return false;
				}
				if (!project.isOpen()) {
					setErrorMessage("Project \"" + name + "\" is closed");
					return false;
				}
			} else {
				setErrorMessage("Illegal project name: " + status.getMessage());
				return false;
			}
		} else {
			setErrorMessage("Project not specified");
			return false;
		}

		name = entity.getText().trim();
		if (name.length() == 0) {
			setErrorMessage("Entity not specified");
			return false;
		}
		return true;
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(PROJECT, project.getText().trim());
		configuration.setAttribute(ENTITY, entity.getText().trim());
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(PROJECT, "");
		configuration.setAttribute(ENTITY, "");
	}

}
