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
package com.synflow.ngDesign.intro;

import java.util.Properties;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.clone.GitCloneWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroAction;

/**
 * This class defines a check out intro action.
 *
 * @author Matthieu Wipliez
 *
 */
@SuppressWarnings("restriction")
public class CheckoutIntroAction implements IIntroAction {

	private void executeUpdateCommand(Shell shell, String url) {
		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.CLONE_WIZARD_IMPORT_PROJECTS, true);

		GitCloneWizard wizard = new GitCloneWizard(url);
		wizard.setShowProjectImport(true);

		WizardDialog dlg = new WizardDialog(shell, wizard);
		dlg.setHelpAvailable(true);
		dlg.open();
	}

	@Override
	public void run(final IIntroSite site, Properties params) {
		final String url = params.getProperty("url");
		final Shell shell = site.getWorkbenchWindow().getShell();

		Runnable r = new Runnable() {
			public void run() {
				executeUpdateCommand(shell, url);
			}
		};

		shell.getDisplay().asyncExec(r);
	}

}
