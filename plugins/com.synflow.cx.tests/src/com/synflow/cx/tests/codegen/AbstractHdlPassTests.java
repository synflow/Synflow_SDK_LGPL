/*******************************************************************************
 * Copyright (c) 2012-2015 Synflow SAS.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthieu Wipliez
 *    Nicolas Siret
 *******************************************************************************/
package com.synflow.cx.tests.codegen;

import static com.synflow.core.ICoreConstants.FOLDER_SIM;
import static com.synflow.core.ICoreConstants.FOLDER_TESTBENCH;
import static com.synflow.core.ICoreConstants.SUFFIX_GEN;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.testing.InjectWith;
import org.junit.Assert;

import com.google.gson.JsonObject;
import com.synflow.core.util.CoreUtil;
import com.synflow.cx.CxInjectorProvider;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Instance;
import com.synflow.models.ir.util.IrUtil;

/**
 * This class defines Cx tests that are expected to succeed.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
@InjectWith(CxInjectorProvider.class)
public abstract class AbstractHdlPassTests extends AbstractCodegenPassTests {

	/**
	 * Returns the paths of all entities (including dependencies) referenced by the given entity,
	 * and the entity itself.
	 * 
	 * @param entity
	 *            an entity
	 * @return a list of paths
	 */
	protected final List<String> collectPaths(Entity entity) {
		List<String> paths = new ArrayList<>();
		collectPaths(paths, entity);
		return paths;
	}

	private void collectPaths(List<String> paths, Entity entity) {
		if (entity instanceof DPN) {
			DPN network = (DPN) entity;
			for (Instance instance : network.getInstances()) {
				collectPaths(paths, instance.getEntity());
			}
		}

		Iterable<String> names = CoreUtil.getFileList(entity);
		boolean external = CoreUtil.isExternal(entity);
		for (String name : names) {
			String path = computePath(name, external);
			paths.add(path);
		}
	}

	/**
	 * Compiles the given file names.
	 * 
	 * @param files
	 *            a list of file names
	 * @throws Exception
	 */
	protected final void compile(List<String> files) throws Exception {
		File sim = new File(outputPath, FOLDER_SIM);

		// compiles files
		for (String path : files) {
			int compil = runCompileCommand(sim, path);
			Assert.assertEquals("expected code generation to be correct for " + path, 0, compil);
		}
	}

	/**
	 * Compiles the testbench of the given entity (unless the entity has an 'implementation'
	 * property)
	 * 
	 * @param entity
	 *            an entity
	 */
	protected void compileTb(Entity entity) throws Exception {
		JsonObject implementation = CoreUtil.getImplementation(entity);
		if (implementation != null) {
			return;
		}

		File sim = new File(outputPath, FOLDER_SIM);
		File tb = new File(outputPath, FOLDER_TESTBENCH);

		String name = entity.getName();
		final String fileExt = getCodeGenerator().getFileExtension();
		String file = new File(tb, IrUtil.getFile(name) + ".tb." + fileExt).getPath();
		int compil = runCompileCommand(sim, file);
		Assert.assertEquals("expected code generation to be correct for " + name + "testbench", 0,
				compil);
	}

	/**
	 * Computes a path based on the given entity name and external flag.
	 * 
	 * @param name
	 * @param external
	 * @return an absolute path
	 */
	private String computePath(String name, boolean external) {
		if (external) {
			return Paths.get(name).toAbsolutePath().toString();
		} else {
			final String genName = getCodeGenerator().getName().toLowerCase();
			File target = new File(outputPath, genName + SUFFIX_GEN);
			target.mkdirs();

			final String fileExt = getCodeGenerator().getFileExtension();
			return target.toPath().resolve(IrUtil.getFile(name) + "." + fileExt).toString();
		}
	}

	protected abstract String getLibraryPath();

	/**
	 * Runs the compile command for a file with the given name, in the given sim folder.
	 * 
	 * @param sim
	 * @param name
	 * @return exit code of the command
	 * @throws Exception
	 */
	protected abstract int runCompileCommand(File sim, String name) throws Exception;

	/**
	 * Runs vsim in the given folder with the given file name.
	 * 
	 * @param directory
	 * @param name
	 * @return
	 * @throws Exception
	 */
	private int runVsim(File directory, String name) throws Exception {
		Process process = executeCommand(directory, "vsim", "-novopt", "work." + name);

		// Process process = executeCommand(directory, "vsim", "-c", "-novopt",
		// "-lib", "work", name);

		try {
			OutputStream os = process.getOutputStream();
			os.write("run 10 us\nquit -sim\nquit\n".getBytes());
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// read stdout and set status to -1 if "Error" is encountered
		// copy output to System.out
		int status = 0;
		InputStream source = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(source));
		try {
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				if (line.contains("Error") || line.contains("Fatal") || line.contains("Failure")
						|| line.contains("Assertion failed")) {
					status = -1;
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		process.waitFor();
		return status;
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		// set output folder
		getCodeGenerator().setOutputFolder(outputPath);

		File sim = new File(outputPath, FOLDER_SIM);
		if (sim.exists()) {
			return;
		}

		// create work library
		sim.mkdirs();
		executeCommand(sim, "vlib", "work").waitFor();

		// compile libraries
		String path = getLibraryPath();
		String pathLib = new File(path).getCanonicalPath();
		String fileExt = getCodeGenerator().getFileExtension();
		for (String lib : getCodeGenerator().getLibraries()) {
			String file = IrUtil.getFile(lib);
			int simul = runCompileCommand(sim, pathLib + "/" + file + "." + fileExt);
			Assert.assertEquals("expected code generation to be correct for libraries", 0, simul);
		}
	}

	/**
	 * Simulates the given entity (unless the entity is built-in).
	 * 
	 * @param entity
	 *            an entity
	 * @throws Exception
	 */
	protected void simulate(Entity entity) throws Exception {
		if (CoreUtil.isBuiltin(entity)) {
			return;
		}

		String name = entity.getSimpleName();
		File sim = new File(outputPath, FOLDER_SIM);
		int simu = runVsim(sim, name + "_tb");
		Assert.assertEquals("expected simulation to be correct for " + name, 0, simu);
	}

}
