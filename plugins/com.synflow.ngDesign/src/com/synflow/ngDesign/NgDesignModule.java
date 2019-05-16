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
package com.synflow.ngDesign;

import org.eclipse.core.runtime.Platform;
import org.eclipse.xtend2.lib.StringConcatenation;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.synflow.core.ICodeGenerator;
import com.synflow.core.IExporter;
import com.synflow.core.IFileWriter;
import com.synflow.ngDesign.internal.EclipseFileWriter;
import com.synflow.ngDesign.internal.NativeFileWriter;
import com.synflow.ngDesign.internal.exporters.DiamondExporter;
import com.synflow.ngDesign.internal.exporters.QuartusExporter;
import com.synflow.ngDesign.internal.exporters.VsimExporter;
import com.synflow.ngDesign.internal.generators.bytecode.BytecodeGenerator;
import com.synflow.ngDesign.internal.generators.verilog.VerilogCodeGenerator;
import com.synflow.ngDesign.internal.generators.vhdl.VhdlCodeGenerator;

/**
 * This class defines the module for ngDesign Pro.
 *
 * @author Matthieu Wipliez
 *
 */
public class NgDesignModule extends AbstractModule {

	public static final String JAVA = "Java";

	public static final String VERILOG = "Verilog";

	public static final String VHDL = "VHDL";

	public NgDesignModule() {
		// set line separator to \n
		String oldLineSeparator = System.setProperty("line.separator", "\n");

		// load StringConcatenation class, which uses the line.separator property to initialize
		// DEFAULT_LINE_DELIMITER
		// use a println so we're sure the class is actually loaded and delimiter is initialized
		System.out.println(StringConcatenation.DEFAULT_LINE_DELIMITER);

		// restore line separator
		System.setProperty("line.separator", oldLineSeparator);
	}

	@Override
	protected void configure() {
		// file writers
		if (Platform.isRunning()) {
			bind(IFileWriter.class).to(EclipseFileWriter.class);
		} else {
			bind(IFileWriter.class).to(NativeFileWriter.class);
		}

		// exporters
		bind(IExporter.class).annotatedWith(Names.named("Altera")).to(QuartusExporter.class);
		bind(IExporter.class).annotatedWith(Names.named("Lattice")).to(DiamondExporter.class);
		bind(IExporter.class).annotatedWith(Names.named("Modelsim")).to(VsimExporter.class);

		// Verilog code generator
		bind(ICodeGenerator.class).annotatedWith(Names.named(VERILOG))
				.to(VerilogCodeGenerator.class);

		// VHDL code generator
		bind(ICodeGenerator.class).annotatedWith(Names.named(VHDL)).to(VhdlCodeGenerator.class);

		// Bytecode generator
		bind(ICodeGenerator.class).annotatedWith(Names.named(JAVA)).to(BytecodeGenerator.class);
	}

}
