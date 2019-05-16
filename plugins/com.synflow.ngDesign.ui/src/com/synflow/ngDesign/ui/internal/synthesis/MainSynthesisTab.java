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
package com.synflow.ngDesign.ui.internal.synthesis;

import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.SYNTHESIS_CONFIGURATION;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.SYNTHESIS_GENERATE_PROJECT;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.synflow.core.SynflowCore;
import com.synflow.models.util.dom.DomUtil;
import com.synflow.ngDesign.ui.internal.launching.MainTab;

/**
 * This class defines the main tab for synthesis configuration type.
 *
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 *
 */
public class MainSynthesisTab extends MainTab {

	private Combo board;

	private Text config;

	private Button fNewConfigFile;

	private Button fUseExistingConfiguration;

	private Button generateProject;

	private Label[] labels;

	private Combo model;

	private Combo vendor;

	@Override
	protected void createAdditionalControls(Composite composite) {
		final Group group = new Group(composite, SWT.NONE);
		group.setFont(getFont());
		group.setText("&Target configuration:");
		group.setLayout(new GridLayout(4, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		/**
		 * Configuration file
		 */
		Label label = new Label(group, SWT.NONE);
		label.setText("Configuration file:");

		config = new Text(group, SWT.SINGLE | SWT.READ_ONLY);
		config.setFont(getFont());
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		config.setLayoutData(data);

		fNewConfigFile = createPushButton(group, "Create new...", null);
		fNewConfigFile.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				createNewConfigurationFile();
			}
		});

		fUseExistingConfiguration = createPushButton(group, "Use existing...", null);
		fUseExistingConfiguration.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				useExistingConfigurationFile();
			}
		});

		/**
		 * Labels
		 */
		labels = new Label[3];

		labels[0] = new Label(group, SWT.NONE);
		labels[0].setText("Vendor:");

		vendor = new Combo(group, SWT.DROP_DOWN | SWT.READ_ONLY);
		vendor.setItems(new String[] { "Xilinx", "Altera", "Lattice", "Adapteva", "Other" });
		data = new GridData(SWT.FILL, SWT.TOP, true, false);
		data.horizontalSpan = 3;
		vendor.setLayoutData(data);
		vendor.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateXml("vendor", vendor);
			}
		});

		/*
		 * TODO board.setItems should be specific to a vendor. Now all boards
		 * are accessible.. not good.
		 */
		labels[1] = new Label(group, SWT.NONE);
		labels[1].setText("Board:");
		board = new Combo(group, SWT.DROP_DOWN | SWT.READ_ONLY);
		board.setItems(new String[] { "Zynq", "Kintex", "Artix", "Spartan", "Stratix", "Arria", "Cyclone", "iCE", "ECP", "MachXO", "Parallela", "Other" });
		data = new GridData(SWT.FILL, SWT.TOP, true, false);
		data.horizontalSpan = 3;
		board.setLayoutData(data);
		board.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateXml("board", board);
			}
		});

		labels[2] = new Label(group, SWT.NONE);
		labels[2].setText("Model:");
		model = new Combo(group, SWT.DROP_DOWN | SWT.READ_ONLY);
		model.setItems(new String[] { "Micro Server", "Desktop", "Other" });
		data = new GridData(SWT.FILL, SWT.TOP, true, false);
		data.horizontalSpan = 3;
		model.setLayoutData(data);
		model.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateXml("model", model);
			}
		});

		/**
		 * Generate (or not) the project
		 */
		generateProject = new Button(group, SWT.CHECK);
		generateProject.setText("Generate project");
		data = new GridData(SWT.FILL, SWT.TOP, true, false);
		data.horizontalSpan = 4;
		generateProject.setLayoutData(data);
		generateProject.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
	}

	private void createNewConfigurationFile() {
		IProject project = getProjectFromText();
		if (project == null) {
			return;
		}

		String name = "synthesis_" + Math.abs(new Random().nextInt()) + ".xml";
		InputDialog dialog = new InputDialog(getShell(), "Configuration file name",
				"Please enter the configuration file name", name, new IInputValidator() {
					@Override
					public String isValid(String newText) {
						if (!newText.endsWith(".xml")) {
							return "Please use a .xml file extension";
						}
						return null;
					}
				});

		dialog.open();
		name = dialog.getValue();
		if (name == null || name.isEmpty()) {
			return;
		}

		InputStream source = new ByteArrayInputStream("<configuration></configuration>".getBytes());
		try {
			IFile file = project.getFile(name);
			file.create(source, false, null);
			loadConfigurationFile(file);
		} catch (CoreException ex) {
			SynflowCore.log(ex);
		}
	}

	@Override
	protected String getIcon() {
		return "hardware.gif";
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);

		String configFile = "";
		try {
			configFile = configuration.getAttribute(SYNTHESIS_CONFIGURATION, "");
		} catch (CoreException e) {
			setErrorMessage(e.getStatus().getMessage());
		}

		config.setText(configFile);

		setEnabled(false);

		vendor.deselectAll();
		board.deselectAll();
		model.deselectAll();

		try {
			generateProject.setSelection(configuration.getAttribute(SYNTHESIS_GENERATE_PROJECT, true));
		} catch (CoreException e) {
			setErrorMessage(e.getStatus().getMessage());
		}

		String message;
		if (configFile.isEmpty()) {
			message = "No configuration file is set.";
		} else {
			IProject project = getProjectFromText();
			if (project == null) {
				return;
			}

			IFile file = project.getFile(configFile);
			if (file.exists()) {
				loadConfigurationFile(file);
				return;
			} else {
				message = "Configuration file \"" + configFile + "\" does not exist.";
			}
		}

		MessageDialog dialog = new MessageDialog(null, "Set configuration file", null,
				message + " Would you like to create a new configuration file or use an existing file?",
				MessageDialog.QUESTION, new String[] { "Create new", "Use existing" }, 0);
		int result = dialog.open();
		if (result == 0) {
			createNewConfigurationFile();
		} else if (result == 1) {
			useExistingConfigurationFile();
		}
	}

	@Override
	public boolean isValid(ILaunchConfiguration config) {
		boolean valid = super.isValid(config);
		if (valid) {
			try {
				String configFile = config.getAttribute(SYNTHESIS_CONFIGURATION, "");
				if (configFile.isEmpty()) {
					setErrorMessage("Please specify a configuration file");
					valid = false;
				} else {
					IProject project = getProjectFromText();
					if (!project.getFile(configFile).exists()) {
						setErrorMessage("Configuration file \"" + configFile + "\" does not exist");
						setMessage("Please use an existing configuration file or create a new one.");
						valid = false;
					}
				}
			} catch (CoreException e) {
				setErrorMessage(e.getMessage());
				valid = false;
			}
		}

		setEnabled(valid);

		return valid;
	}

	private void loadConfigurationFile(IFile file) {
		config.setText(file.getName());

		try {
			Document doc = DomUtil.parseDocument(file.getContents());
			Element configuration = doc.getDocumentElement();
			setIndex(configuration, "vendor", vendor);
			setIndex(configuration, "board", board);
			setIndex(configuration, "model", model);
		} catch (CoreException e) {
			SynflowCore.log(e);
		}

		updateLaunchConfigurationDialog();
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		configuration.setAttribute(SYNTHESIS_CONFIGURATION, config.getText());
		configuration.setAttribute(SYNTHESIS_GENERATE_PROJECT, generateProject.getSelection());
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		configuration.removeAttribute(SYNTHESIS_CONFIGURATION);
		configuration.removeAttribute(SYNTHESIS_GENERATE_PROJECT);
	}

	private void setEnabled(boolean valid) {
		for (Label label : labels) {
			label.setEnabled(valid);
		}

		vendor.setEnabled(valid);
		board.setEnabled(valid);
		model.setEnabled(valid);
	}

	private void setIndex(Element configuration, String name, Combo combo) {
		NodeList list = configuration.getElementsByTagName(name);
		if (list.getLength() > 0) {
			int index = combo.indexOf(list.item(0).getTextContent());

			if (index == -1) {
				index = combo.getItemCount() - 1;
			}

			combo.select(index);
		}
	}

	private void updateXml(String name, Combo combo) {
		IProject project = getProjectFromText();
		if (project == null) {
			return;
		}

		IFile file = project.getFile(config.getText());
		if (!file.exists()) {
			// do nothing if config file does not exist
			return;
		}

		try {
			Document doc = DomUtil.parseDocument(file.getContents());
			Element configuration = doc.getDocumentElement();
			NodeList list = configuration.getElementsByTagName(name);
			Element element;
			if (list.getLength() == 0) {
				element = doc.createElement(name);
				configuration.appendChild(element);
			} else {
				element = (Element) list.item(0);
			}

			element.setTextContent(combo.getText());

			String xml = DomUtil.writeToString(configuration);
			InputStream source = new ByteArrayInputStream(xml.getBytes());
			file.setContents(source, false, false, null);
		} catch (CoreException e) {
			SynflowCore.log(e);
		}

		// reload configuration file
		loadConfigurationFile(file);
	}

	private void useExistingConfigurationFile() {
		IProject project = getProjectFromText();
		if (project == null) {
			return;
		}

		ElementListSelectionDialog dialog = new ElementListSelectionDialog(getShell(), new WorkbenchLabelProvider());
		dialog.setTitle("Configuration file selection");
		dialog.setMessage("Choose an existing configuration file");

		try {
			Iterable<IResource> iterable = Iterables.filter(ImmutableList.copyOf(project.members()),
					res -> "xml".equals(res.getFileExtension()));
			dialog.setElements(Iterables.toArray(iterable, Object.class));
		} catch (CoreException e) {
			SynflowCore.log(e);
		}

		if (dialog.open() == Window.OK) {
			IFile file = (IFile) dialog.getResult()[0];
			loadConfigurationFile(file);
		}
	}
}
