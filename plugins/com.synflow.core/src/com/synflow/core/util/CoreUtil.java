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
 
package com.synflow.core.util;

import static com.synflow.core.ICoreConstants.FILE_EXT_IR;
import static com.synflow.core.ICoreConstants.FOLDER_IR;
import static com.synflow.core.IProperties.IMPL_BUILTIN;
import static com.synflow.core.IProperties.IMPL_EXTERNAL;
import static com.synflow.core.IProperties.PROP_DEPENDENCIES;
import static com.synflow.core.IProperties.PROP_FILE;
import static com.synflow.core.IProperties.PROP_IMPLEMENTATION;
import static com.synflow.core.IProperties.PROP_TYPE;

import java.nio.file.Paths;
import java.util.Arrays;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synflow.core.SynflowCore;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.Entity;
import com.synflow.models.ir.util.IrUtil;

/**
 * This class defines utility methods to find .ir files from qualified class names (as well as the
 * other way around).
 *
 * @author Matthieu Wipliez
 *
 */
public class CoreUtil {

	/**
	 * Ensures that the file at the location given by path has the same case as represented by the
	 * underlying file system. If that's not the case, deletes the file. This avoids any exception
	 * related to "case variants" and makes sure that case remains consistent between file system
	 * representation and Eclipse representation. Necessary for case-insensitive file systems like
	 * Windows.
	 *
	 * @param path
	 *            path to a file
	 */
	public static void ensureCaseConsistency(IPath path) {
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IFile file = root.getFile(path);
			IFileStore store = EFS.getStore(file.getLocationURI());
			String localName = store.fetchInfo().getName();
			if (!file.getName().equals(localName)) {
				// case difference: remove file with old case
				file.getParent().getFile(new Path(localName)).delete(true, null);
			}
		} catch (CoreException e) {
			SynflowCore.log(e);
		}
	}

	/**
	 * Returns the dependencies of the given entity.
	 *
	 * @param entity
	 *            an entity
	 * @return a JSON array, empty if the entity has no dependencies
	 */
	private static JsonArray getDependencies(JsonObject implementation) {
		if (implementation != null) {
			JsonElement dependencies = implementation.get(PROP_DEPENDENCIES);
			if (dependencies != null && dependencies.isJsonArray()) {
				return dependencies.getAsJsonArray();
			}
		}
		return new JsonArray();
	}

	/**
	 * Returns the name of the given filename with ".exe" appended if we're on Windows...
	 *
	 * @param fileName
	 *            name of an executable file
	 * @return
	 */
	public static String getExecutable(String fileName) {
		return getExecutable(fileName, ".exe");
	}

	/**
	 * Returns the name of the given filename with <code>fileExit</code> appended if we're on
	 * Windows...
	 *
	 * @param fileName
	 *            name of an executable file
	 * @param fileExt
	 *            a Windows executable file extension like .exe or .bat
	 * @return
	 */
	public static String getExecutable(String fileName, String fileExt) {
		if (Platform.isRunning()) {
			if (Platform.OS_WIN32.equals(Platform.getOS())) {
				return fileName + fileExt;
			}
		} else if (System.getProperty("os.name").startsWith("Windows")) {
			return fileName + fileExt;
		}
		return fileName;
	}

	/**
	 * Returns the file in the given project, folder, with the given class name and file extension.
	 * Does not check for existence of the given file.
	 *
	 * @param project
	 *            a project
	 * @param folder
	 *            folder in which to look for
	 * @param className
	 *            class name of the file this method will look for
	 * @param fileExt
	 *            file extension
	 * @return an {@link IFile}
	 */
	private static IFile getFile(IProject project, String folder, String className,
			String fileExt) {
		String name = IrUtil.getFile(className);
		String fileName = folder + "/" + name + "." + fileExt;
		return project.getFile(fileName);
	}

	/**
	 * Returns a list of file list for the given entity. If the entity has a built-in or external
	 * implementation, the list begins with dependencies.
	 *
	 * @param entity
	 * @return
	 */
	public static Iterable<String> getFileList(Entity entity) {
		JsonObject implementation = CoreUtil.getImplementation(entity);
		Iterable<String> deps = Iterables.transform(getDependencies(implementation),
				new Function<JsonElement, String>() {
					@Override
					public String apply(JsonElement dependency) {
						return dependency.getAsString();
					}
				});

		if (isExternal(entity)) {
			// implementation external: expecting a "file" property
			String file = implementation.get(PROP_FILE).getAsString();
			return Iterables.concat(deps, Arrays.asList(file));
		}

		String name = entity.getName();
		return Iterables.concat(deps, ImmutableSet.of(name));
	}

	/**
	 * Returns the value of the 'implementation' property of the given entity as a JSON object.
	 *
	 * @param entity
	 *            an entity
	 * @return a JSON object, or <code>null</code> if the entity has no implementation property
	 */
	public static JsonObject getImplementation(Entity entity) {
		JsonElement implementation = entity.getProperties().get(PROP_IMPLEMENTATION);
		if (implementation != null && implementation.isJsonObject()) {
			return implementation.getAsJsonObject();
		}
		return null;
	}

	/**
	 * Returns the .ir file in the given project with the given class name. Does not check for
	 * existence of the given file.
	 *
	 * @param project
	 *            a project
	 * @param className
	 *            class name of the .ir file this method will look for
	 * @return an {@link IFile}
	 */
	public static IFile getIrFile(IProject project, String className) {
		return getFile(project, FOLDER_IR, className, FILE_EXT_IR);
	}

	/**
	 * Returns a new path that is built from <code>path</code>, except that it is relative to
	 * <code>root</code>. The <code>root</code> path must be a subset of <code>path</code>.
	 *
	 * @param root
	 *            root path
	 * @param path
	 *            file path
	 * @return a new path
	 */
	public static IPath getRelative(IPath root, IPath path) {
		int count = path.matchingFirstSegments(root);
		return path.removeFirstSegments(count);
	}

	/**
	 * Returns the relative path from <code>source</code> to <code>target</code> .
	 *
	 * @param source
	 *            source path
	 * @param target
	 *            target path
	 * @return a path that represents the relative path from source to target
	 */
	public static IPath getRelative(IResource source, IResource target) {
		java.nio.file.Path from = Paths.get(source.getLocation().toString());
		java.nio.file.Path to = Paths.get(target.getLocation().toString());
		String path = from.relativize(to).toString();
		// make path portable so it can be used anywhere
		return new Path(path);
	}

	/**
	 * Returns <code>true</code> if the given entity has an implementation whose type is builtin.
	 *
	 * @param entity
	 *            an entity
	 * @return a boolean
	 */
	public static boolean isBuiltin(Entity entity) {
		JsonObject implementation = getImplementation(entity);
		return implementation != null && IMPL_BUILTIN.equals(implementation.get(PROP_TYPE));
	}

	/**
	 * Returns <code>true</code> if the given entity has an implementation whose type is external.
	 *
	 * @param entity
	 *            an entity
	 * @return a boolean
	 */
	public static boolean isExternal(Entity entity) {
		JsonObject implementation = getImplementation(entity);
		return implementation != null && IMPL_EXTERNAL.equals(implementation.get(PROP_TYPE));
	}

	/**
	 * Returns <code>true</code> if the given entity needs to be instantiated by a test DPN.
	 *
	 * @param entity
	 * @return
	 */
	public static boolean needsWrapper(Entity entity) {
		return entity instanceof Actor || !entity.getInputs().isEmpty()
				|| !entity.getOutputs().isEmpty();
	}

}
