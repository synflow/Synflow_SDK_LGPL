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
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.TYPE_SYNTHESIS;
import static org.eclipse.debug.ui.IDebugUIConstants.ID_RUN_LAUNCH_GROUP;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.synflow.ngDesign.ui.internal.launching.AbstractLaunchShortcut;

/**
 * This class defines a launch shortcut for running a Cx entity as hardware on FPGA.
 *
 * @author Matthieu Wipliez
 *
 */
public class HardwareLaunchShortcut extends AbstractLaunchShortcut {

	@Override
	protected String getLaunchConfigurationName(String name) {
		return name + " - Synthesis";
	}

	@Override
	protected String getType() {
		return TYPE_SYNTHESIS;
	}

	@Override
	protected void launch(ILaunchConfiguration config, String mode) throws CoreException {
		String configFile = config.getAttribute(SYNTHESIS_CONFIGURATION, "");
		if (configFile.isEmpty()) {
			IStructuredSelection selection = new StructuredSelection(config);
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			DebugUITools.openLaunchConfigurationDialogOnGroup(shell, selection,
					ID_RUN_LAUNCH_GROUP);
		} else {
			DebugUITools.launch(config, mode);
		}
	}

	@Override
	protected void setDefaults(ILaunchConfigurationWorkingCopy wc) {
	}

}
