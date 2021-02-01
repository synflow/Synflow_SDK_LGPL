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
package com.synflow.ngDesign.ui.internal.debug;

import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.LIBRARIES;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.LIBRARIES_DEFAULT;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.PRINT_CYCLES;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.PRINT_CYCLES_DEFAULT;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.SOURCES;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.SOURCES_DEFAULT;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.WORKING_DIRECTORY;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.getDefaultWorkingDirectory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.synflow.ngDesign.ui.internal.NgDesignUi;

/**
 * This class defines the main tab for simulation configuration type.
 *
 * @author Matthieu Wipliez
 */
public class ConfigurationTab extends AbstractLaunchConfigurationTab {

	/**
	 * A listener which handles widget change events for the controls in this tab.
	 */
	private class WidgetListener extends SelectionAdapter implements ModifyListener {

		/**
		 * Handles a click on the "Browse..." button for the working directory.
		 */
		private void handleBrowseWorkingDirectory() {
			String defaultWorkingDir = (String) workingDirectory.getData();

			DirectoryDialog dialog = new DirectoryDialog(getShell());

			String workingDir = workingDirectory.getText();
			if (workingDir.isEmpty()) {
				workingDir = defaultWorkingDir;
			}
			dialog.setFilterPath(workingDir);

			String path = dialog.open();
			if (path != null) {
				if (defaultWorkingDir.equals(path)) {
					// default path => reset the text field to blank
					workingDirectory.setText("");
				} else {
					workingDirectory.setText(path);
				}
			}
		}

		@Override
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			if (e.widget == browseWorkingDir) {
				handleBrowseWorkingDirectory();
			} else {
				updateLaunchConfigurationDialog();
			}
		}

	}

	private static String[] SOURCE_FILES_EXT = { "*.c", "*.cpp; *.cxx; *.cc", "*.*" };

	private static String[] SOURCE_FILES_NAMES = { "C source files (" + SOURCE_FILES_EXT[0] + ")",
			"C++ source files (" + SOURCE_FILES_EXT[1] + ")",
			"All files (" + SOURCE_FILES_EXT[2] + ")" };

	private static String[] UNIX_LIB_EXT = { "*.so", "*.a", "*.*" };

	private static String[] UNIX_LIB_NAMES = { "Dynamic library files (" + UNIX_LIB_EXT[0] + ")",
			"Static library files (" + UNIX_LIB_EXT[1] + ")", "All files (" + UNIX_LIB_EXT[2] + ")" };

	private static String[] WINDOWS_LIB_EXT = { "*.lib", "*.*" };

	private static String[] WINDOWS_LIB_NAMES = { "Library files (" + WINDOWS_LIB_EXT[0] + ")",
			"All files (" + WINDOWS_LIB_EXT[1] + ")" };

	private Button browseWorkingDir;

	private WidgetListener fListener = new WidgetListener();

	private StringList includes;

	private String lastPath;

	private StringList libraries;

	private Button printCycles;

	private StringList sources;

	private Text workingDirectory;

	@Override
	public boolean canSave() {
		return isValid(null);
	}

	private void createCompilationGroup(Composite composite) {
		final Group group = new Group(composite, SWT.NONE);
		group.setFont(getFont());
		group.setText("&Compile options:");
		group.setLayout(new GridLayout(3, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		includes = new StringList(group, ignored -> updateLaunchConfigurationDialog());
		includes.setLabel("Include directories:");

		// add "add" button
		Button addButton = createPushButton(group, "Add include directory...");
		includes.onClickAdd(addButton, e -> {
			DirectoryDialog dialog = new DirectoryDialog(getShell());
			dialog.setFilterPath(getLastPath(includes));
			lastPath = dialog.open();
			return lastPath;
		});

		libraries = new StringList(group, ignored -> updateLaunchConfigurationDialog());
		libraries.setLabel("Libraries:");

		Composite addLibraries = new Composite(group, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		addLibraries.setLayout(layout);
		addLibraries.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));

		Button addLibraryName = createPushButton(addLibraries, "Add library name...");
		libraries.onClickAdd(addLibraryName, e -> {
			InputDialog dialog = new InputDialog(getShell(), "Add library", "Enter library name",
					null, (text) -> text.trim().isEmpty() ? "Please enter a valid library name"
							: null);
			dialog.open();
			return dialog.getValue();
		});
		Button addLibraryFile = createPushButton(addLibraries, "Add library file...");
		libraries.onClickAdd(addLibraryFile, e -> {
			FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
			dialog.setFilterPath(getLastPath(libraries));
			boolean isWindows = Platform.OS_WIN32.equals(Platform.getOS());
			dialog.setFilterExtensions(isWindows ? WINDOWS_LIB_EXT : UNIX_LIB_EXT);
			dialog.setFilterNames(isWindows ? WINDOWS_LIB_NAMES : UNIX_LIB_NAMES);
			String fileName = dialog.open();
			lastPath = dialog.getFilterPath();
			return fileName;
		});

		sources = new StringList(group, ignored -> updateLaunchConfigurationDialog());
		sources.setLabel("Source files:");
		Button addSourceFile = createPushButton(group, "Add source file...");
		sources.onClickAdd(addSourceFile, e -> {
			FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
			dialog.setFilterPath(getLastPath(sources));
			dialog.setFilterExtensions(SOURCE_FILES_EXT);
			dialog.setFilterNames(SOURCE_FILES_NAMES);
			String fileName = dialog.open();
			lastPath = dialog.getFilterPath();
			return fileName;
		});
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		setControl(composite);

		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 0;
		composite.setLayout(layout);

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(data);

		createCompilationGroup(composite);
		createRuntimeGroup(composite);
	}

	private Button createPushButton(Composite parent, String label) {
		Button button = super.createPushButton(parent, label, null);
		((GridData) button.getLayoutData()).verticalAlignment = SWT.TOP;
		return button;
	}

	private void createRuntimeGroup(Composite composite) {
		final Group group = new Group(composite, SWT.NONE);
		group.setFont(getFont());
		group.setText("&Runtime options:");
		group.setLayout(new GridLayout(3, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		printCycles = new Button(group, SWT.CHECK);
		printCycles.setFont(getFont());
		data = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		printCycles.setLayoutData(data);
		printCycles.setText("Print the current cycle number");
		printCycles.addSelectionListener(fListener);

		Label labelWorkingDir = new Label(group, SWT.NONE);
		labelWorkingDir.setText("Working directory:");

		workingDirectory = new Text(group, SWT.BORDER | SWT.SINGLE);
		workingDirectory.setFont(getFont());
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		workingDirectory.setLayoutData(data);
		workingDirectory.addModifyListener(fListener);

		browseWorkingDir = createPushButton(group, "Browse...");
		browseWorkingDir.addSelectionListener(fListener);
	}

	private Font getFont() {
		return getControl().getFont();
	}

	@Override
	public Image getImage() {
		return NgDesignUi.getImageDescriptor("icons/settings.gif").createImage();
	}

	private String getLastPath(StringList list) {
		if (lastPath == null) {
			List<String> strings = list.getList();
			ListIterator<String> it = strings.listIterator(strings.size());
			if (it.hasPrevious()) {
				return it.previous();
			}
		}
		return lastPath;
	}

	/**
	 * Returns the list backing the given StringList, or <code>null</code> if said list is empty.
	 * Needed to avoid having to Apply when it is not necessary.
	 *
	 * @param stringList
	 *            a StringList
	 * @return a list of strings, or <code>null</code>
	 */
	private List<String> getListOrNull(StringList stringList) {
		List<String> list = stringList.getList();
		return list.isEmpty() ? null : list;
	}

	@Override
	public String getName() {
		return "Configuration";
	}

	/**
	 * Returns the string from the given text, or <code>null</code> if said string is empty. Needed
	 * to avoid having to Apply when it is not necessary.
	 *
	 * @param text
	 *            a Text
	 * @return a string, or <code>null</code>
	 */
	private String getStringOrNull(Text text) {
		String workingDir = text.getText().trim();
		return workingDir.isEmpty() ? null : workingDir;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration config) {
		try {
			libraries.setInput(config.getAttribute(LIBRARIES, LIBRARIES_DEFAULT));
		} catch (CoreException e) {
			setErrorMessage(e.getStatus().getMessage());
		}

		try {
			sources.setInput(config.getAttribute(SOURCES, SOURCES_DEFAULT));
		} catch (CoreException e) {
			setErrorMessage(e.getStatus().getMessage());
		}

		// runtime
		try {
			printCycles.setSelection(config.getAttribute(PRINT_CYCLES, PRINT_CYCLES_DEFAULT));
		} catch (CoreException e) {
			setErrorMessage(e.getStatus().getMessage());
		}

		try {
			String workingDir = getDefaultWorkingDirectory(config);
			workingDirectory.setData(workingDir);
			workingDirectory.setMessage("if left blank, defaults to " + workingDir);
			if (config.hasAttribute(WORKING_DIRECTORY)) {
				workingDirectory.setText(config.getAttribute(WORKING_DIRECTORY, ""));
			}
		} catch (CoreException e) {
			setErrorMessage(e.getStatus().getMessage());
		}
	}

	@Override
	public boolean isValid(ILaunchConfiguration config) {
		setErrorMessage(null);
		setMessage(null);

		String workingDir = workingDirectory.getText().trim();
		if (workingDir.length() > 0) {
			Path dir = Paths.get(workingDir);
			if (!Files.exists(dir)) {
				setErrorMessage("The given working directory does not exist: " + workingDir);
				return false;
			}
		}

		return true;
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy copy) {
		// compile time
		copy.setAttribute(LIBRARIES, getListOrNull(libraries));
		copy.setAttribute(SOURCES, getListOrNull(sources));

		// runtime
		copy.setAttribute(PRINT_CYCLES, printCycles.getSelection());
		copy.setAttribute(WORKING_DIRECTORY, getStringOrNull(workingDirectory));
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// compile time
		configuration.setAttribute(LIBRARIES, LIBRARIES_DEFAULT);
		configuration.setAttribute(SOURCES, SOURCES_DEFAULT);

		// runtime
		configuration.setAttribute(PRINT_CYCLES, PRINT_CYCLES_DEFAULT);
		configuration.setAttribute(WORKING_DIRECTORY, getDefaultWorkingDirectory(configuration));
	}

}
