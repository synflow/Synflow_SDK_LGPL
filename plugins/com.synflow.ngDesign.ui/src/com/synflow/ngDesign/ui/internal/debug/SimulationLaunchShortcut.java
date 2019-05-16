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

import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.PRINT_CYCLES;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.PRINT_CYCLES_DEFAULT;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.TYPE_SIMULATION;

import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

import com.synflow.ngDesign.ui.internal.launching.AbstractLaunchShortcut;

/**
 * This class defines a launch shortcut for Cx Simulation.
 *
 * @author Matthieu Wipliez
 *
 */
public class SimulationLaunchShortcut extends AbstractLaunchShortcut {

	@Override
	protected String getLaunchConfigurationName(String name) {
		return name;
	}

	@Override
	protected String getType() {
		return TYPE_SIMULATION;
	}

	@Override
	protected void setDefaults(ILaunchConfigurationWorkingCopy wc) {
		// runtime option
		// must specify print cycles because it is a boolean option (no way of knowing when it is
		// set)
		wc.setAttribute(PRINT_CYCLES, PRINT_CYCLES_DEFAULT);
	}

}
