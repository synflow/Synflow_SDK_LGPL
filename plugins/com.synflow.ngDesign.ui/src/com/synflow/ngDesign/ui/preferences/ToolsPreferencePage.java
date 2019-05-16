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
package com.synflow.ngDesign.ui.preferences;

import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_DIAMOND_BIN;
import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_DIAMOND_PATH;
import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_MODELSIM_BIN;
import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_MODELSIM_PATH;
import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_QUARTUS_BIN;
import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_QUARTUS_PATH;
import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_VIVADO_BIN;
import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_VIVADO_PATH;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.synflow.core.util.CoreUtil;

/**
 * This class represents a preference page for external tools.
 *
 * @author Matthieu Wipliez
 */
public class ToolsPreferencePage extends AbstractToolsPreferencePage
		implements IWorkbenchPreferencePage {

	private Text diamond;

	private String diamondBin;

	private Label diamondVersion;

	private Text modelsim;

	private String modelsimBin;

	private Label modelsimVersion;

	private Text quartus;

	private String quartusBin;

	private Label quartusVersion;

	private Text vivado;

	private String vivadoBin;

	private Label vivadoVersion;

	@Override
	protected void createTools(Composite composite) {
		createTool(composite, "&Modelsim (Mentor Graphics)",
				"Path of the folder in which Modelsim is installed (contains the \"modelsim.ini\" file).",
				pair -> {
					modelsim = pair.getKey();
					modelsim.addModifyListener(e -> updateModelsim());
					modelsimVersion = pair.getValue();
				});

		createTool(composite, "&Diamond (Lattice)",
				"Path of the folder in which Lattice Diamond is installed (contains the \"bin\" folder).",
				pair -> {
					diamond = pair.getKey();
					diamond.addModifyListener(e -> updateDiamond());
					diamondVersion = pair.getValue();
				});

		createTool(composite, "&Quartus (Quartus)",
				"Path of the folder in which Quartus Prime is installed (contains the \"nios2eds\" and \"quartus\" folders).",
				pair -> {
					quartus = pair.getKey();
					quartus.addModifyListener(e -> updateQuartus());
					quartusVersion = pair.getValue();
				});

		createTool(composite, "&Vivado (Xilinx)",
				"Path of the folder in which Xilinx Vivado is installed (contains the \"bin\" folder).",
				pair -> {
					vivado = pair.getKey();
					vivado.addModifyListener(e -> updateVivado());
					vivadoVersion = pair.getValue();
				});
	}

	/**
	 * Returns the path of a folder described the given text component, or null if not valid.
	 *
	 * @param text
	 * @return
	 */
	private Path getPath(Text text, String... subfolders) {
		String trimmed = text.getText().trim();
		if (trimmed.isEmpty()) {
			// ignore empty field
			setErrorMessage(null);
			return null;
		}

		Path path = Paths.get(trimmed, subfolders);
		if (Files.isDirectory(path)) {
			return path;
		} else {
			setErrorMessage("The following path does not point to an existing directory: " + path);
			return null;
		}
	}

	@Override
	protected void initializeFromStore() {
		IPreferenceStore store = getPreferenceStore();
		modelsim.setText(store.getString(PREF_MODELSIM_PATH));
		diamond.setText(store.getString(PREF_DIAMOND_PATH));
		vivado.setText(store.getString(PREF_QUARTUS_PATH));
		vivado.setText(store.getString(PREF_VIVADO_PATH));
	}

	@Override
	protected void performDefaults() {
		IPreferenceStore store = getPreferenceStore();
		modelsim.setText(store.getDefaultString(PREF_MODELSIM_PATH));
		diamond.setText(store.getDefaultString(PREF_DIAMOND_PATH));
		vivado.setText(store.getDefaultString(PREF_QUARTUS_PATH));
		vivado.setText(store.getDefaultString(PREF_VIVADO_PATH));
	}

	@Override
	public boolean performOk() {
		IPreferenceStore store = getPreferenceStore();
		store.setValue(PREF_MODELSIM_PATH, modelsim.getText().trim());
		store.setValue(PREF_MODELSIM_BIN, modelsimBin);

		store.setValue(PREF_DIAMOND_PATH, diamond.getText().trim());
		store.setValue(PREF_DIAMOND_BIN, diamondBin);

		store.setValue(PREF_QUARTUS_PATH, quartus.getText().trim());
		store.setValue(PREF_QUARTUS_BIN, quartusBin);

		store.setValue(PREF_VIVADO_PATH, vivado.getText().trim());
		store.setValue(PREF_VIVADO_BIN, vivadoBin);

		return true;
	}

	/**
	 * Updates Diamond binary and version from text field.
	 */
	private void updateDiamond() {
		diamondBin = "";
		diamondVersion.setText("");

		Path path = getPath(diamond, "bin");
		if (path == null) {
			return;
		}

		try {
			Path executable = findExecutable(path, "pnmainc", ".exe");
			File temp = File.createTempFile("dummy", ".tcl");

			diamondVersion.setText("...");
			String result = launchProcess(executable.toString(), temp.toString());
			temp.delete();

			Pattern pattern = Pattern.compile("---\\s+Lattice\\s+Diamond\\s+Version\\s+(.*)");
			Matcher matcher = pattern.matcher(result);
			if (matcher.find()) {
				diamondBin = executable.getParent().toString();
				diamondVersion.setText(matcher.group(1));

				setErrorMessage(null);
			} else {
				setErrorMessage("Could not determine Diamond version");
			}
		} catch (IOException | NoSuchElementException e) {
			setErrorMessage("Could not determine Diamond version: " + e.getMessage());
		}
	}

	/**
	 * Updates Modelsim binary and version from text field.
	 */
	private void updateModelsim() {
		modelsimBin = "";
		modelsimVersion.setText("");

		Path path = getPath(modelsim);
		if (path == null) {
			return;
		}

		try {
			Path executable = findExecutable(path, "vsim", ".exe");
			String result = launchProcess(executable.toString(), "-version");
			modelsimBin = executable.getParent().toString();
			modelsimVersion.setText(result.trim());

			setErrorMessage(null);
		} catch (IOException | NoSuchElementException e) {
			setErrorMessage("Could not determine Modelsim version: " + e.getMessage());
		}
	}

	/**
	 * Updates Quartus binary and version from text field.
	 */
	private void updateQuartus() {
		quartusBin = "";
		quartusVersion.setText("");

		Path path = getPath(quartus, "nios2eds");
		if (path == null) {
			return;
		}

		try {
			Path executable = path.resolve(CoreUtil.getExecutable("Nios II Command Shell", ".bat"));
			String result = launchProcess(executable.toString(), "quartus_sh -v");
			String[] lines = result.split("\r?\n");

			quartusBin = executable.getParent().toString();
			quartusVersion.setText(lines[0].trim());

			setErrorMessage(null);
		} catch (IOException | NoSuchElementException e) {
			setErrorMessage("Could not determine Quartus version: " + e.getMessage());
		}
	}

	/**
	 * Updates Vivado binary and version from text field.
	 */
	private void updateVivado() {
		vivadoBin = "";
		vivadoVersion.setText("");

		Path path = getPath(vivado, "bin");
		if (path == null) {
			return;
		}

		try {
			Path executable = path.resolve(CoreUtil.getExecutable("vivado", ".bat"));
			String result = launchProcess(executable.toString(), "-version");
			String[] lines = result.split("\r?\n");

			vivadoBin = executable.getParent().toString();
			vivadoVersion.setText(lines[0].trim());

			setErrorMessage(null);
		} catch (IOException | NoSuchElementException e) {
			setErrorMessage("Could not determine Vivado version: " + e.getMessage());
		}
	}

}
