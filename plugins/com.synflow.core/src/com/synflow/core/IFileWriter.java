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

import java.io.InputStream;

/**
 * This interface defines a file writer that is independent of the underlying platform.
 *
 * @author Matthieu Wipliez
 *
 */
public interface IFileWriter {

	/**
	 * Returns <code>true</code> if a file with the given name exists.
	 *
	 * @param fileName
	 *            name of destination file
	 */
	boolean exists(String fileName);

	/**
	 * Returns the absolute path to the file with the given name. The path uses the same separator
	 * "/" on all platforms.
	 *
	 * @param fileName
	 *            name of destination file
	 * @return absolute path to the file with the given name
	 */
	String getAbsolutePath(String fileName);

	/**
	 * Removes the file with the given name.
	 *
	 * @param fileName
	 *            name of destination file
	 */
	void remove(String fileName);

	/**
	 * Sets the output folder to which file name are relative.
	 *
	 * @param folder
	 *            an output folder
	 */
	void setOutputFolder(String folder);

	/**
	 * Writes the given contents to the file with the given name.
	 *
	 * @param fileName
	 *            name of destination file
	 * @param sequence
	 *            a sequence of characters
	 */
	void write(String fileName, CharSequence sequence);

	/**
	 * Writes the given contents to the file with the given name.
	 *
	 * @param fileName
	 *            name of destination file
	 * @param source
	 *            an input stream
	 */
	void write(String fileName, InputStream source);

}
