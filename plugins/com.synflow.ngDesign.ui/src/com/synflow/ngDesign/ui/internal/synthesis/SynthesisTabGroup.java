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

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.ENABLE_PARTIAL_RECONFIGURATION;

/**
 * This class defines the tab group for Synthesis configuration type.
 *
 * @author Matthieu Wipliez
 *
 */
public class SynthesisTabGroup extends AbstractLaunchConfigurationTabGroup {

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		if (ENABLE_PARTIAL_RECONFIGURATION) {
			ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] { new PRSynthesisTab() };
			setTabs(tabs);
		} else {
			ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] { new MainSynthesisTab() };
			setTabs(tabs);
		}
	}

}
