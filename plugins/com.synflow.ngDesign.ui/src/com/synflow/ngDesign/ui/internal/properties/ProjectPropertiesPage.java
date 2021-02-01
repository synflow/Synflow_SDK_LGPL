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
package com.synflow.ngDesign.ui.internal.properties;

import static com.synflow.core.ICoreConstants.PROP_GENERATOR;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.dialogs.PropertyPage;
import org.osgi.service.prefs.BackingStoreException;

import com.synflow.core.BuildJob;
import com.synflow.core.SynflowCore;

/**
 * This class defines a property page for a Synflow project.
 *
 * @author Matthieu Wipliez
 *
 */
public class ProjectPropertiesPage extends PropertyPage implements IPreferenceChangeListener {

	private IEclipsePreferences prefs;

	public ProjectPropertiesPage() {
		noDefaultAndApplyButton();
	}

	private boolean checkIsValid() {
		setErrorMessage(null);
		return true;
	}

	@Override
	protected Control createContents(Composite parent) {
		IProject project = (IProject) getElement().getAdapter(IProject.class);
		prefs = SynflowCore.getProjectPreferences(project);
		prefs.addPreferenceChangeListener(this);

		Group group = new Group(parent, NONE);
		group.setText("Hardware code generation");

		// create a button for each code generator
		for (final String generator : SynflowCore.getGenerators()) {
			Button button = new Button(group, SWT.RADIO);
			button.setSelection(generator.equals(prefs.get(PROP_GENERATOR, "")));
			button.setText(generator);
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (((Button) e.widget).getSelection()) {
						prefs.put(PROP_GENERATOR, generator);
					}
				}
			});
		}

		GridLayout layout = new GridLayout(1, false);
		group.setLayout(layout);

		GridData data = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		group.setLayoutData(data);

		return group;
	}

	@Override
	public void dispose() {
		// remove preference listener
		prefs.removePreferenceChangeListener(this);

		super.dispose();
	}

	@Override
	public boolean performOk() {
		IProject project = (IProject) getElement().getAdapter(IProject.class);

		// writes preferences
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			SynflowCore.log(e);
		}

		// schedule a build job
		Job buildJob = new BuildJob(project);
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		buildJob.setRule(workspace.getRuleFactory().buildRule());
		buildJob.setUser(true);
		buildJob.schedule();

		return true;
	}

	@Override
	public void preferenceChange(PreferenceChangeEvent event) {
		setValid(checkIsValid());
	}

}
