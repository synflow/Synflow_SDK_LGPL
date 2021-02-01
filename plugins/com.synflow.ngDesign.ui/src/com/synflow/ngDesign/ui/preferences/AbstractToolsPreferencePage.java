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
package com.synflow.ngDesign.ui.preferences;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.xtext.xbase.lib.Pair;

import com.synflow.core.util.CoreUtil;
import com.synflow.ngDesign.NgDesign;

/**
 * This class represents a preference page for external tools.
 *
 * @author Matthieu Wipliez
 */
public abstract class AbstractToolsPreferencePage extends PreferencePage
		implements IWorkbenchPreferencePage {

	public static final String ID = "com.synflow.ngDesign.ui.toolsPage";

	/**
	 * Creates the composite which will contain all the preference controls for this page.
	 *
	 * @param parent
	 *            the parent composite
	 * @return the composite for this page
	 */
	private Composite createComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		composite.setLayoutData(
				new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		return composite;
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = createComposite(parent);

		createTools(composite);

		initializeFromStore();

		return composite;
	}

	private Button createPushButton(Composite parent, String label, Image image) {
		Button button = new Button(parent, SWT.PUSH);
		button.setFont(parent.getFont());
		if (image != null) {
			button.setImage(image);
		}
		if (label != null) {
			button.setText(label);
		}
		GridData gd = new GridData();
		button.setLayoutData(gd);
		gd.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		gd.horizontalAlignment = GridData.FILL;
		return button;
	}

	/**
	 * Creates the group for a tool with the given name, description, and consumer lambda.
	 *
	 * @param parent
	 * @param name
	 * @param description
	 * @param consumer
	 *            a lambda operation that takes a pair (text, label) as argument
	 */
	protected final void createTool(Composite parent, String name, String description,
			Consumer<Pair<Text, Label>> consumer) {
		final Group group = new Group(parent, SWT.NONE);
		group.setFont(getFont());
		group.setText(name);
		group.setLayout(new GridLayout(3, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		Label label = new Label(group, SWT.NONE);
		label.setText(description);
		GridData gd = new GridData();
		gd.horizontalSpan = 3;
		label.setLayoutData(gd);

		label = new Label(group, SWT.NONE);
		label.setText("Path:");

		Text path = new Text(group, SWT.BORDER | SWT.SINGLE);
		path.setFont(getFont());
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		path.setLayoutData(data);

		Button browse = createPushButton(group, "Browse...", null);
		browse.addSelectionListener(new SelectionAdapter() {
			private String lastPath;

			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				dialog.setFilterPath(lastPath);
				lastPath = dialog.open();
				path.setText(lastPath);
			}

		});

		label = new Label(group, SWT.NONE);
		label.setText("Version:");

		Label version = new Label(group, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		version.setLayoutData(gd);

		consumer.accept(Pair.of(path, version));
	}

	protected abstract void createTools(Composite composite);

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return new ScopedPreferenceStore(InstanceScope.INSTANCE, NgDesign.PLUGIN_ID);
	}

	/**
	 * Iterates over the children of the given path to find the given executable file.
	 *
	 * @param path
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	protected final Path findExecutable(Path path, String fileName, String fileExt)
			throws IOException, NoSuchElementException {
		// if cannot find the executable in the expected location
		final String exeName = CoreUtil.getExecutable(fileName, fileExt);
		return Files.list(path).map(candidate -> candidate.resolve(exeName))
				.filter(Files::isExecutable).findFirst().get();
	}

	@Override
	public void init(IWorkbench workbench) {
		// nothing to do
	}

	protected abstract void initializeFromStore();

	protected final String launchProcess(String... command) throws IOException {
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();
		byte[] buf = new byte[1024];
		int nb = process.getInputStream().read(buf);
		try {
			// wait fearlessly without timeout
			process.waitFor();
		} catch (InterruptedException e) {
		}
		return new String(buf, 0, nb);
	}

	@Override
	public void setErrorMessage(String newMessage) {
		super.setErrorMessage(newMessage);
		setValid(newMessage == null);
	}

}
