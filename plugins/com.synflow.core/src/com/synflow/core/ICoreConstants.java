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
 
package com.synflow.core;

/**
 * This interface defines useful well-known constants.
 *
 * @author Matthieu Wipliez
 *
 */
public interface ICoreConstants {

	String FILE_EXT_CX = "cx";

	String FILE_EXT_IR = "ir";

	String FOLDER_CLASSES = "classes";

	/**
	 * name of the folder where IR files are generated
	 */
	String FOLDER_IR = ".ir";

	/**
	 * name of the folder where simulation files are generated
	 */
	String FOLDER_SIM = "sim";

	/**
	 * name of the "testbench" folder
	 */
	String FOLDER_TESTBENCH = "testbench";

	/**
	 * project property for current generator
	 */
	String PROP_GENERATOR = SynflowCore.PLUGIN_ID + ".generator";

	/**
	 * suffix of folders for generated files
	 */
	String SUFFIX_GEN = "-gen";

}
