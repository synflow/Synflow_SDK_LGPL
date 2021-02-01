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
package com.synflow.ngDesign.internal;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.synflow.core.IFileWriter;
import com.synflow.core.SynflowCore;

/**
 * This class defines an implementation of a IFileWriter based on the native Java file classes. The
 * name of a file must be relative to the output directory.
 *
 * @author Matthieu Wipliez
 *
 */
public class NativeFileWriter implements IFileWriter {

	private static final boolean RUNNING_ON_WINDOWS = java.io.File.separatorChar == '\\';

	private String outputFolder;

	@Override
	public boolean exists(String fileName) {
		return Files.exists(getPath(fileName));
	}

	@Override
	public String getAbsolutePath(String fileName) {
		String path = getPath(fileName).toAbsolutePath().toString();
		if (RUNNING_ON_WINDOWS) {
			path = path.replace('\\', '/');
		}
		return path;
	}

	private Path getPath(String fileName) {
		return Paths.get(outputFolder, fileName);
	}

	@Override
	public void remove(String fileName) {
		if (exists(fileName)) {
			try {
				Files.delete(getPath(fileName));
			} catch (IOException e) {
				SynflowCore.log(e);
			}
		}
	}

	@Override
	public void setOutputFolder(String folder) {
		outputFolder = folder;
	}

	@Override
	public void write(String fileName, CharSequence sequence) {
		if (sequence == null) {
			return;
		}

		Path path = getPath(fileName);
		try {
			Files.createDirectories(path.getParent());
			Files.write(path, sequence.toString().getBytes());
		} catch (IOException e) {
			SynflowCore.log(e);
		}
	}

	@Override
	public void write(String fileName, InputStream source) {
		Path path = getPath(fileName);
		try {
			Files.createDirectories(path.getParent());
			Files.copy(source, path, REPLACE_EXISTING);
		} catch (IOException e) {
			SynflowCore.log(e);
		}
	}

}
