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
package com.synflow.ngDesign.internal.generators;

import static com.synflow.core.ICoreConstants.FOLDER_TESTBENCH;
import static com.synflow.core.ICoreConstants.SUFFIX_GEN;
import static com.synflow.core.IProperties.IMPL_BUILTIN;
import static com.synflow.core.IProperties.PROP_TYPE;
import static com.synflow.models.ir.util.IrUtil.getFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.synflow.core.ICodeGenerator;
import com.synflow.core.IFileWriter;
import com.synflow.core.IPathResolver;
import com.synflow.core.SynflowCore;
import com.synflow.core.util.CoreUtil;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Instance;
import com.synflow.models.dpn.Unit;
import com.synflow.models.ir.util.IrUtil;

/**
 * This class defines an abstract generator that uses Xtend for templates.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class AbstractGenerator implements ICodeGenerator, IPathResolver {

	@Inject
	protected IFileWriter writer;

	/**
	 * Returns the path to the given file name in the library.
	 *
	 * @param fileName
	 *            a file name (without extension)
	 * @return <code>"/src/" + fileName</code>
	 */
	protected String computeLibPath(String fileName) {
		final String gen = getName().toLowerCase();
		return "/lib/" + gen + "/src/" + fileName + '.' + getFileExtension();
	}

	@Override
	public String computePath(Entity entity) {
		return computePath(getFile(entity.getName()), getFileExtension());
	}

	@Override
	public final String computePath(String name) {
		return computePath(name, getFileExtension());
	}

	/**
	 * Returns a path composed of this generator's name (possibly with a '-gen' suffix, depending on
	 * generators), the given file name and file extension.
	 *
	 * @param fileName
	 *            name of a file
	 * @param fileExt
	 *            file extension
	 * @return a path
	 */
	protected String computePath(String fileName, String fileExt) {
		String name = getName().toLowerCase();
		return name + SUFFIX_GEN + '/' + fileName + '.' + fileExt;
	}

	@Override
	public String computePathTb(Entity entity) {
		String name = getFile(entity.getName()) + ".tb";
		return FOLDER_TESTBENCH + '/' + name + '.' + getFileExtension();
	}

	/**
	 * Copy the built-in entity and its dependencies to the target folder.
	 *
	 * @param entity
	 *            an entity
	 */
	private void copyBuiltinEntity(Entity entity) {
		for (String name : CoreUtil.getFileList(entity)) {
			copyBuiltinFile(name, false);
		}
	}

	/**
	 * Copy the file that matches the given qualified name to the target folder.
	 *
	 * @param name
	 *            qualified name of an entity
	 */
	protected void copyBuiltinFile(String name, boolean force) {
		final String fileName = IrUtil.getFile(name);
		String path = computePath(fileName);
		if (force || !writer.exists(path)) {
			final String pathName = computeLibPath(fileName);
			try (InputStream is = SynflowCore.openStream(pathName)) {
				writer.write(path, is);
			} catch (IOException e) {
				SynflowCore.log(e);
			}
		}
	}

	@Override
	public void copyLibraries() {
		for (String library : getLibraries()) {
			copyBuiltinFile(library, true);
		}
	}

	protected final DPN createTestDpn(Entity entity) {
		DPN dpn = new TestGenerator().createDpn(entity);
		for (Instance instance : dpn.getInstances()) {
			if (instance.getEntity() != entity) {
				transform(instance.getEntity());
			}
		}
		transform(dpn);
		return dpn;
	}

	/**
	 * Prints the given entity.
	 *
	 * @param entity
	 *            an entity
	 */
	protected abstract void doPrint(Entity entity);

	/**
	 * Prints the testbench for the given entity.
	 *
	 * @param entity
	 *            an entity
	 */
	protected abstract void doPrintTestbench(Entity entity);

	@Override
	public void fullBuild(Entity entity) {
		copyLibraries();
		for (Entity ent : DpnFactory.eINSTANCE.collectEntities(entity)) {
			transform(ent);
			print(ent);
		}

		Set<Unit> units = new LinkedHashSet<>();
		ResourceSet set = entity.eResource().getResourceSet();
		for (Resource resource : set.getResources()) {
			Entity ent = (Entity) resource.getContents().get(0);
			if (ent instanceof Unit) {
				units.add((Unit) ent);
			}
		}

		for (Unit unit : units) {
			transform(unit);
			print(unit);
		}

		printTestbench(entity);
	}

	@Override
	public final IFileWriter getFileWriter() {
		return writer;
	}

	@Override
	public String getFullPath(Entity entity) {
		return writer.getAbsolutePath(computePath(entity));
	}

	@Override
	public Iterable<String> getLibraries() {
		// by default a generator has no libraries
		return ImmutableList.of();
	}

	@Override
	public void print(Entity entity) {
		JsonObject implementation = CoreUtil.getImplementation(entity);
		if (implementation == null) {
			// default case: print entity
			if (entity instanceof DPN) {
				DPN dpn = (DPN) entity;
				for (Instance instance : dpn.getInstances()) {
					Entity ent = instance.getEntity();
					if (ent == null || ent.eIsProxy()) {
						// do not print this network if not all entities are resolved
						return;
					}
				}
			}
			doPrint(entity);
		} else {
			JsonElement type = implementation.get(PROP_TYPE);
			if (IMPL_BUILTIN.equals(type)) {
				// if type is 'builtin' copy entity and dependencies to target folder
				copyBuiltinEntity(entity);
			}
		}
	}

	@Override
	public void printTestbench(Entity entity) {
		if (entity instanceof Unit) {
			return;
		}

		JsonObject implementation = CoreUtil.getImplementation(entity);
		if (implementation == null) {
			// only print testbench for entities without 'implementation' property
			doPrintTestbench(entity);
		}
	}

	@Override
	public void remove(String name) {
		writer.remove(computePath(IrUtil.getFile(name)));
	}

	@Override
	public final void setOutputFolder(String folder) {
		writer.setOutputFolder(folder);
	}

}
