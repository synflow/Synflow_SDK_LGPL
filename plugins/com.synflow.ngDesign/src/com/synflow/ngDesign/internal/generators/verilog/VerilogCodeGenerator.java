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
package com.synflow.ngDesign.internal.generators.verilog;

import static com.synflow.ngDesign.NgDesignModule.VERILOG;
import static com.synflow.ngDesign.internal.generators.GeneratorExtensions.getNumberOfHexadecimalDigits;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.synflow.core.util.CoreUtil;
import com.synflow.models.dpn.Entity;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeArray;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.util.IrUtil;
import com.synflow.models.ir.util.TypeUtil;
import com.synflow.ngDesign.internal.generators.AbstractGenerator;
import com.synflow.ngDesign.internal.generators.Namer;

/**
 * This class implements a generator for Verilog.
 *
 * @author Matthieu Wipliez
 *
 */
public class VerilogCodeGenerator extends AbstractGenerator {

	private static Set<String> RESERVED = ImmutableSet.of("always", "and", "assign", "automatic",
			"begin", "bit", "buf", "bufif0", "bufif1", "byte", "case", "casex", "casez", "cell",
			"cmos", "config", "deassign", "default", "defparam", "design", "disable", "edge",
			"else", "end", "endcase", "endconfig", "endfunction", "endgenerate", "endmodule",
			"endprimitive", "endspecify", "endtable", "endtask", "event", "for", "force", "forever",
			"fork", "function", "generate", "genvar", "highz0", "highz1", "if", "ifnone", "initial",
			"instance", "inout", "input", "integer", "join", "large", "liblist", "localparam",
			"macromodule", "medium", "module", "nand", "negedge", "nmos", "nor", "not",
			"noshowcancelled", "notif0", "notif1", "or", "output", "parameter", "pmos", "posedge",
			"primitive", "pull0", "pull1", "pulldown", "pullup", "pulsestyle_onevent",
			"pulsestyle_ondetect", "rcmos", "real", "realtime", "reg", "release", "repeat", "rnmos",
			"rpmos", "rtran", "rtranif0", "rtranif1", "scalared", "signed", "showcancelled",
			"small", "specify", "specparam", "strength", "strong0", "strong1", "supply0", "supply1",
			"table", "task", "time", "tran", "tranif0", "tranif1", "tri", "tri0", "tri1", "triand",
			"trior", "trireg", "type", "unsigned", "use", "vectored", "wait", "wand", "weak0",
			"weak1", "while", "wire", "wor", "xnor", "xor");

	private Namer namer;

	public VerilogCodeGenerator() {
		namer = new Namer(RESERVED, "\\", " ");
	}

	@Override
	protected void doPrint(Entity entity) {
		CharSequence contents = new VerilogPrinter(namer, this).doSwitch(entity);
		writer.write(computePath(entity), contents);
	}

	@Override
	protected void doPrintTestbench(Entity entity) {
		// compute path *before* changing the entity
		String path = computePathTb(entity);

		// generate test
		if (CoreUtil.needsWrapper(entity)) {
			entity = createTestDpn(entity);
		}

		// print testbench
		CharSequence contents = new VerilogTestbenchPrinter(namer, this).printTestbench(entity);
		writer.write(path, contents);
	}

	@Override
	public String getFileExtension() {
		return "v";
	}

	@Override
	public String getName() {
		return VERILOG;
	}

	/**
	 * Prints one .hex file per each variable that is an array. A .hex file contains all values of
	 * the array in hexadecimal notation.
	 *
	 * @param variables
	 *            a list of variables
	 */
	private void printHexFiles(Entity entity) {
		List<Var> variables = entity.getVariables();

		String file = IrUtil.getFile(entity.getName());
		for (Var var : variables) {
			Type type = var.getType();
			if (type.isArray()) {
				TypeArray typeArray = (TypeArray) type;
				int size = TypeUtil.getSize(typeArray.getElementType());

				CharSequence sequence;
				EObject value = var.getInitialValue();
				if (value == null) {
					int n = 1;
					for (Integer dim : typeArray.getDimensions()) {
						n *= dim;
					}

					int numDigits = getNumberOfHexadecimalDigits(size);
					String str = String.format("%0" + numDigits + "x\n", 0);
					sequence = Strings.repeat(str, n);
				} else {
					sequence = new VerilogHexPrinter(size).doSwitch(value);
				}

				String path = computePath(file + "_" + var.getName(), "hex");
				writer.write(path, sequence);
			}
		}
	}

	@Override
	public void transform(Entity entity) {
		printHexFiles(entity);

		VerilogTransformer transformer = new VerilogTransformer();
		transformer.doSwitch(entity);
	}

}
