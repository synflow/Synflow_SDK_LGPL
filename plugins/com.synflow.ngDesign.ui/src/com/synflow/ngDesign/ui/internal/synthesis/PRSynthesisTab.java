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
package com.synflow.ngDesign.ui.internal.synthesis;

import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.SYNTHESIS_PARTIAL_RECONFIGURATION;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.ENTITYPR1;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.ENTITYPR2;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import com.synflow.cx.cx.Instantiable;
import com.synflow.ngDesign.ui.internal.launching.AbstractLaunchShortcut;
import com.synflow.ngDesign.ui.internal.launching.EntitySelectionDialog;

/**
 * This class defines the main tab for synthesis configuration type.
 *
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 *
 */
public class PRSynthesisTab extends MainSynthesisTab {

	private Text entityPR1;

	private Text entityPR2;

	private Button partialReconfiguration;

	private Button fSearchButtonEntity1;

	private Button fSearchButtonEntity2;

	/**
	 * A listener which handles widget change events for the controls in this
	 * tab.
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
			if (source == fSearchButtonEntity1 || source == fSearchButtonEntity2) {
				handleSearchButtonSelected(entityPR1);
			} else if (source == fSearchButtonEntity2) {
				handleSearchButtonSelected(entityPR2);
			} else {
				updateLaunchConfigurationDialog();
			}
		}

	}

	private void handleSearchButtonSelected(Text entity) {
		// TODO search instantiables
		// IProject project = getProjectFromText();
		Instantiable[] instantiables = new Instantiable[0];

		EntitySelectionDialog dialog = new EntitySelectionDialog(null, instantiables, "Select entity");
		int result = dialog.open();
		if (result == Window.OK) {
			Instantiable instantiable = (Instantiable) dialog.getFirstResult();
			entity.setText(AbstractLaunchShortcut.getEntityName(instantiable));
		}
	}

	protected final WidgetListener fListener1 = new WidgetListener();

	protected final WidgetListener fListener2 = new WidgetListener();

	@Override
	protected void createPRControls(Composite composite) {
		final Group group = new Group(composite, SWT.NONE);
		group.setFont(getFont());
		group.setText("&Partial Reconfiguration:");
		group.setLayout(new GridLayout(2, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		entityPR1 = new Text(group, SWT.BORDER | SWT.SINGLE);
		entityPR1.setFont(getFont());
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		entityPR1.setLayoutData(data);
		entityPR1.addModifyListener(fListener1);

		fSearchButtonEntity1 = createPushButton(group, "Search...", null);
		fSearchButtonEntity1.addSelectionListener(fListener1);

		entityPR2 = new Text(group, SWT.BORDER | SWT.SINGLE);
		entityPR2.setFont(getFont());
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		entityPR2.setLayoutData(data);
		entityPR2.addModifyListener(fListener2);

		fSearchButtonEntity2 = createPushButton(group, "Search...", null);
		fSearchButtonEntity2.addSelectionListener(fListener2);

		partialReconfiguration = new Button(group, SWT.CHECK);
		partialReconfiguration.setText("Activate Partial Reconfiguration");
		data = new GridData(SWT.LEFT, SWT.TOP, true, false);
		partialReconfiguration.setLayoutData(data);
		partialReconfiguration.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);

		try {
			partialReconfiguration.setSelection(configuration.getAttribute(SYNTHESIS_PARTIAL_RECONFIGURATION, true));
		} catch (CoreException e) {
			setErrorMessage(e.getStatus().getMessage());
		}

		String className = "";
		try {
			className = configuration.getAttribute(ENTITYPR1, "");
		} catch (CoreException e) {
			setErrorMessage(e.getStatus().getMessage());
		}
		entityPR1.setText(className);

		try {
			className = configuration.getAttribute(ENTITYPR2, "");
		} catch (CoreException e) {
			setErrorMessage(e.getStatus().getMessage());
		}
		entityPR2.setText(className);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		configuration.setAttribute(SYNTHESIS_PARTIAL_RECONFIGURATION, partialReconfiguration.getSelection());
		configuration.setAttribute(ENTITYPR1, entityPR1.getText().trim());
		configuration.setAttribute(ENTITYPR2, entityPR2.getText().trim());
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		configuration.removeAttribute(SYNTHESIS_PARTIAL_RECONFIGURATION);
		configuration.setAttribute(ENTITYPR1, "");
		configuration.setAttribute(ENTITYPR2, "");
	}

}
