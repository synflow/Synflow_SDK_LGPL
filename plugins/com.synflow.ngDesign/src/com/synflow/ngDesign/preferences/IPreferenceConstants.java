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
package com.synflow.ngDesign.preferences;

/**
 * This interface defines global preference constants.
 *
 * @author Matthieu Wipliez
 *
 */
public interface IPreferenceConstants {

	/**
	 * path to folder that contains the "pnmainc" executable
	 */
	String PREF_DIAMOND_BIN = "diamond.bin";

	/**
	 * path to Diamond (contains "bin" folder)
	 */
	String PREF_DIAMOND_PATH = "diamond.path";

	/**
	 * license key
	 */
	String PREF_LICENSE_KEY = "key";

	/**
	 * login for license
	 */
	String PREF_LICENSE_LOGIN = "login";

	/**
	 * path of folder that contains the "modelsim" and "vsim" executables
	 */
	String PREF_MODELSIM_BIN = "modelsim.bin";

	/**
	 * path to Modelsim folder (contains "modelsim.ini" file)
	 */
	String PREF_MODELSIM_PATH = "modelsim.path";

	/**
	 * path to folder that contains the "vivado" script
	 */
	String PREF_VIVADO_BIN = "vivado.bin";

	/**
	 * path to Vivado (contains "bin" folder)
	 */
	String PREF_VIVADO_PATH = "vivado.path";

	/**
	 * path to folder that contains the "Nios II Command Shell" script
	 */
	String PREF_QUARTUS_BIN = "quartus.nios2eds";

	/**
	 * path to Quartus Prime (contains "nios2eds" and "quartus" folders)
	 */
	String PREF_QUARTUS_PATH = "quartus.path";
}
