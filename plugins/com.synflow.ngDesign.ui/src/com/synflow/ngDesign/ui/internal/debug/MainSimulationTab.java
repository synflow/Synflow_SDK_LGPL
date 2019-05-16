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
package com.synflow.ngDesign.ui.internal.debug;

import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.SIMULATION_MODELSIM;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.SIMULATION_MODELSIM_DEFAULT;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.synflow.ngDesign.ui.internal.launching.MainTab;

/**
 * This class defines the main tab for synthesis configuration type.
 *
 * @author Matthieu Wipliez
 *
 */
public class MainSimulationTab extends MainTab {

	private Button modelsim;

	@Override
	protected void createAdditionalControls(Composite composite) {
		final Group group = new Group(composite, SWT.NONE);
		group.setFont(getFont());
		group.setText("&Simulation");
		group.setLayout(new GridLayout(2, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		modelsim = new Button(group, SWT.CHECK);
		modelsim.setText("Use Modelsim");
		modelsim.addSelectionListener(fListener);
	}

	@Override
	protected String getIcon() {
		return "cx_app.gif";
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);

		boolean flag = false;
		try {
			flag = configuration.getAttribute(SIMULATION_MODELSIM, SIMULATION_MODELSIM_DEFAULT);
		} catch (CoreException e) {
			setErrorMessage(e.getStatus().getMessage());
		}
		modelsim.setSelection(flag);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		configuration.setAttribute(SIMULATION_MODELSIM, modelsim.getSelection());
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		configuration.setAttribute(SIMULATION_MODELSIM, SIMULATION_MODELSIM_DEFAULT);
	}

}
