/*******************************************************************************
 * Copyright (c) 2015 Synflow SAS.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthieu Wipliez - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.synflow.cx.tests.codegen;

import static com.synflow.core.ICoreConstants.FOLDER_CLASSES;
import static com.synflow.core.ICoreConstants.FOLDER_SIM;
import static com.synflow.ngDesign.NgDesignModule.JAVA;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.xtext.testing.InjectWith;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.synflow.core.ICodeGenerator;
import com.synflow.core.util.CoreUtil;
import com.synflow.core.util.StreamCopier;
import com.synflow.cx.tests.CustomCxInjectorProvider;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Unit;

/**
 * This class defines Cx test with bytecode.
 * 
 * @author Matthieu Wipliez
 * 
 */
@InjectWith(CustomCxInjectorProvider.class)
public class BytecodePassTests extends AbstractCodegenPassTests {

	private static boolean initialized;

	@Inject
	@Named(JAVA)
	private ICodeGenerator generator;

	@Test
	public void app_custom() throws Exception {
		testApp("CustomTestbench");
	}

	@Override
	protected void compileAndSimulate(Entity entity) throws Exception {
		if (entity instanceof Unit) {
			return;
		}

		Path workingDir = Paths.get(outputPath, FOLDER_SIM);

		String name = entity.getName();
		if (CoreUtil.needsWrapper(entity)) {
			name += "_test";
		}

		String classpath = workingDir.resolve(FOLDER_CLASSES).toString();
		Process process = executeCommand(workingDir.toFile(), "java", "-ea", "-cp", classpath, name, "-print-cycles");
		new StreamCopier(process.getInputStream(), System.out).start();
		assertEquals("expected run ok", 0, process.waitFor());
	}

	@Override
	protected ICodeGenerator getCodeGenerator() {
		return generator;
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		// set output folder
		generator.setOutputFolder(outputPath);

		if (initialized) {
			return;
		}

		generator.copyLibraries();
		initialized = true;
	}

}
