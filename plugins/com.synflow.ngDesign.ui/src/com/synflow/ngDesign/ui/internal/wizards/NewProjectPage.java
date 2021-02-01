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
package com.synflow.ngDesign.ui.internal.wizards;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import com.synflow.core.SynflowCore;

/**
 * This class defines a new project wizard.
 *
 * @author Matthieu Wipliez
 *
 */
public class NewProjectPage extends WizardNewProjectCreationPage {

	private String generator;

	public NewProjectPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		List<String> generators = SynflowCore.getGenerators();
		if (generators.size() == 1) {
			generator = generators.get(0);
			return;
		}

		Composite composite = (Composite) getControl();
		Group group = new Group(composite, NONE);
		group.setText("Hardware code generation");

		// create a button for each code generator
		for (String generator : generators) {
			Button button = new Button(group, SWT.RADIO);
			button.setText(generator);
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Button button = (Button) e.widget;
					if (button.getSelection()) {
						NewProjectPage.this.generator = button.getText();
					}
				}
			});
		}

		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// select first button (if any)
		Control[] children = group.getChildren();
		if (children.length > 0) {
			Button first = (Button) children[0];
			first.setSelection(true);
			generator = first.getText();
		}
	}

	/**
	 * Returns the generator to use for this new project.
	 *
	 * @return a generator name
	 */
	public String getGenerator() {
		return generator;
	}

}
