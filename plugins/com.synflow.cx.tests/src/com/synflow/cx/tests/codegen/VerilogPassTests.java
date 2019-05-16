/*******************************************************************************
 * Copyright (c) 2012-2015 Synflow SAS.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthieu Wipliez - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.synflow.cx.tests.codegen;

import static com.synflow.ngDesign.NgDesignModule.VERILOG;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.xtext.testing.InjectWith;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.synflow.core.ICodeGenerator;
import com.synflow.core.util.StreamCopier;
import com.synflow.cx.tests.CustomCxInjectorProvider;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Unit;

/**
 * This class defines Cx tests that are expected to succeed.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
@InjectWith(CustomCxInjectorProvider.class)
public class VerilogPassTests extends AbstractHdlPassTests {

	@Inject
	@Named(VERILOG)
	private ICodeGenerator generator;

	@Test
	public void app_external() throws Exception {
		test("app", "External", entity -> true);
	}

	@Override
	protected void compileAndSimulate(Entity entity) throws Exception {
		if (!(entity instanceof Unit)) {
			List<String> paths = collectPaths(entity);

			lint(paths);
			compile(paths);
			compileTb(entity);
			simulate(entity);
		}
	}

	@Override
	protected ICodeGenerator getCodeGenerator() {
		return generator;
	}

	@Override
	protected String getLibraryPath() {
		String name = getCodeGenerator().getName().toLowerCase();
		return "../../fragments/com.synflow.ngDesign.libraries/lib/" + name + "/src";
	}

	private void lint(List<String> fileNames) {
		boolean useCygwin = System.getProperty("os.name").startsWith("Windows");
		final String cygwin = "E:\\cygwin64\\bin";
		String bash = useCygwin ? cygwin + "/bash" : "bash";
		String command = mkVerilatorCommand(fileNames);
		ProcessBuilder builder = new ProcessBuilder(bash, "-c", command);

		if (useCygwin) {
			Map<String, String> environment = builder.environment();
			for (Entry<String, String> entry : environment.entrySet()) {
				if ("PATH".equals(entry.getKey().toUpperCase())) {
					System.out.println("setting " + entry.getKey());
					entry.setValue(cygwin + File.pathSeparator + entry.getValue());
				}
			}
		}

		int exit;
		try {
			System.out.println("running " + command);
			Process process = builder.start();
			new StreamCopier(process.getErrorStream(), System.err).start();
			new StreamCopier(process.getInputStream(), System.out).start();

			exit = process.waitFor();
		} catch (IOException | InterruptedException e) {
			exit = -1;
		}

		System.out.println("linting returned " + exit);
		Assert.assertEquals("linting failed", 0, exit);
	}

	private String mkVerilatorCommand(List<String> fileNames) {
		List<String> command = new ArrayList<>();
		command.add("/usr/local/bin/verilator");
		command.add("--lint-only");

		// from list of file names to set of (unique) folders
		Set<String> paths = new LinkedHashSet<>();
		for (String fileName : fileNames) {
			Path path = Paths.get(fileName);
			paths.add(path.getParent().toString().replace('\\', '/'));
		}

		command.add("-I" + outputPath + "/verilog-gen");
		for (String path : paths) {
			command.add("-I" + path);
		}

		String last = fileNames.get(fileNames.size() - 1);
		command.add(Paths.get(last).getFileName().toString());
		return Joiner.on(' ').join(command);
	}

	@Override
	protected int runCompileCommand(File directory, String name) throws Exception {
		String incdir = "+incdir+" + outputPath + "/verilog-gen";
		Process process = executeCommand(directory, "vlog", "-reportprogress", "300", "-work",
				"work", "-quiet", incdir, name);
		new StreamCopier(process.getInputStream(), System.out).start();
		return process.waitFor();
	}

}
