/*******************************************************************************
 * Copyright (c) 2012-2013 Synflow SAS.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthieu Wipliez - initial API and implementation and/or initial documentation
 *    Nicolas Siret
 *******************************************************************************/
package com.synflow.cx.tests.codegen;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import org.eclipse.xtext.testing.InjectWith;
import org.junit.BeforeClass;

import com.synflow.core.ICodeGenerator;
import com.synflow.core.util.StreamCopier;
import com.synflow.cx.CxInjectorProvider;
import com.synflow.cx.tests.AbstractPassTests;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.dpn.Entity;

/**
 * This class defines Cx tests that are expected to succeed.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
@InjectWith(CxInjectorProvider.class)
public abstract class AbstractCodegenPassTests extends AbstractPassTests {

	@BeforeClass
	public static void cleanOutput() {
		boolean cleanOutput = false;

		String tmpDir = System.getProperty("java.io.tmpdir");
		String path = Paths.get(tmpDir, OUTPUT_NAME).toString();
		try {
			if (cleanOutput) {
				Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc)
							throws IOException {
						Files.delete(dir);
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
							throws IOException {
						Files.delete(file);
						return FileVisitResult.CONTINUE;
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected final void checkEntity(Entity entity, boolean expected) throws Exception {
		if (entity instanceof Actor) {
			checkProperties((Actor) entity);
		}
		generateCode(entity);
		compileAndSimulate(entity);
	}

	/**
	 * Compiles and simulates the given object.
	 * 
	 * @param entity
	 *            an entity
	 * @throws Exception
	 */
	protected abstract void compileAndSimulate(Entity entity) throws Exception;

	/**
	 * Executes a command in the path given by the location, and returns its exit code.
	 * 
	 * @param directory
	 *            a directory
	 * @param command
	 *            a list of String
	 * @return the return code of the process
	 * @throws IOException
	 * @throws InterruptedException
	 */
	protected final Process executeCommand(File directory, List<String> command)
			throws IOException, InterruptedException {
		return executeCommand(directory, command.toArray(new String[0]));
	}

	/**
	 * Executes a command in the path given by the location, and returns its exit code.
	 * 
	 * @param directory
	 *            a directory
	 * @param command
	 *            a list of String
	 * @return the return code of the process
	 * @throws IOException
	 * @throws InterruptedException
	 */
	protected final Process executeCommand(File directory, String... command)
			throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder(command).directory(directory);
		Process process = pb.start();
		new StreamCopier(process.getErrorStream(), System.err).start();
		return process;
	}

	/**
	 * Generates code, compiles it, and simulates.
	 * 
	 * @param entity
	 *            an entity
	 * @throws Exception
	 */
	protected void generateCode(Entity entity) throws Exception {
		ICodeGenerator generator = getCodeGenerator();
		for (Entity anEntity : DpnFactory.eINSTANCE.collectEntities(entity)) {
			generator.transform(anEntity);
			generator.print(anEntity);
		}
		generator.printTestbench(entity);
	}

	/**
	 * Returns the code generator that this class should use
	 * 
	 * @return an instance of a ICodeGenerator
	 */
	protected abstract ICodeGenerator getCodeGenerator();

}
