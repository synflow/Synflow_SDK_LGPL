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
package com.synflow.ngDesign.preferences;

import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_MODELSIM_BIN;
import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_MODELSIM_PATH;
import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_VIVADO_BIN;
import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_VIVADO_PATH;
import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_QUARTUS_BIN;
import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_QUARTUS_PATH;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import com.synflow.core.util.CoreUtil;
import com.synflow.ngDesign.NgDesign;

/**
 * This class defines the preference initializer for the com.synflow.ngDesign
 * plug-in.
 *
 * @author Matthieu Wipliez
 *
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		// initialize modelsim path from PATH environment variable
		final String vsim = CoreUtil.getExecutable("vsim");
		String Modelsim = System.getenv("Path");
		if (Modelsim != null) {
		Optional<Path> path = Arrays.stream(System.getenv("Path").split(File.pathSeparator))
				.map(candidate -> Paths.get(candidate, vsim)).filter(Files::isExecutable).findFirst();
		if (path.isPresent()) {
			Path bin = path.get().getParent();
			IEclipsePreferences prefs = DefaultScope.INSTANCE.getNode(NgDesign.PLUGIN_ID);
			prefs.put(PREF_MODELSIM_BIN, bin.toString());
			prefs.put(PREF_MODELSIM_PATH, bin.getParent().toString());
		}
		}

		// initialize Vivado path from XILINX_VIVADO environment variable
		String vivado = System.getenv("XILINX_VIVADO");
		if (vivado != null) {
			Path bin = Paths.get(vivado.trim(), "bin");
			if (Files.isDirectory(bin)) {
				IEclipsePreferences prefs = DefaultScope.INSTANCE.getNode(NgDesign.PLUGIN_ID);
				prefs.put(PREF_VIVADO_BIN, bin.toString());
				prefs.put(PREF_VIVADO_PATH, bin.getParent().toString());
			}
		}

		// initialize Altera path from SOPC_KIT_NIOS2 environment variable
		String altera = System.getenv("SOPC_KIT_NIOS2");
		if (altera != null) {
			Path bin = Paths.get(altera.trim());
			if (Files.isDirectory(bin)) {
				IEclipsePreferences prefs = DefaultScope.INSTANCE.getNode(NgDesign.PLUGIN_ID);
				prefs.put(PREF_QUARTUS_BIN, bin.toString());
				prefs.put(PREF_QUARTUS_PATH, bin.getParent().toString());
			}
		}
	}

}
