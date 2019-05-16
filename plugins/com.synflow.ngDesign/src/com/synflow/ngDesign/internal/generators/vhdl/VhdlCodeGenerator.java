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
package com.synflow.ngDesign.internal.generators.vhdl;

import java.util.Set;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.synflow.core.util.CoreUtil;
import com.synflow.models.dpn.Entity;
import com.synflow.ngDesign.NgDesignModule;
import com.synflow.ngDesign.internal.generators.AbstractGenerator;
import com.synflow.ngDesign.internal.generators.Namer;

/**
 * This class implements a generator for VHDL.
 *
 * @author Matthieu Wipliez
 *
 */
public class VhdlCodeGenerator extends AbstractGenerator {

	/**
	 * In VHDL, a valid identifier must not begin or end with an underscore, and must not contain
	 * two following underscores. This regular expression matches any such illegal identifier.
	 */
	private static Pattern RE_ILLEGAL_ID = Pattern.compile("(^_.*)|(.*_$)|(.*__.*)");

	private static Set<String> RESERVED = ImmutableSet.of("abs", "access", "after", "alias", "all",
			"and", "architecture", "array", "assert", "attribute", "begin", "block", "body",
			"buffer", "bus", "case", "component", "configuration", "constant", "disconnect",
			"downto", "else", "elsif", "end", "entity", "exit", "file", "for", "function",
			"generate", "generic", "group", "guarded", "if", "impure", "in", "inertial", "inout",
			"is", "label", "library", "linkage", "literal", "loop", "map", "mod", "nand", "new",
			"next", "nor", "not", "null", "of", "on", "open", "or", "others", "out", "package",
			"port", "postponed", "procedure", "process", "pure", "range", "record", "register",
			"reject", "rem", "report", "resize", "return", "rol", "ror", "select", "severity",
			"signal", "shared", "sla", "sll", "sra", "srl", "subtype", "then", "to", "transport",
			"type", "unaffected", "units", "until", "use", "variable", "wait", "when", "while",
			"with", "xnor", "xor");

	private Namer namer;

	public VhdlCodeGenerator() {
		namer = new Namer(RESERVED, RE_ILLEGAL_ID, "\\", "\\");
	}

	@Override
	protected void doPrint(Entity entity) {
		CharSequence contents = new VhdlPrinter(namer).doSwitch(entity);
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

		CharSequence contents = new VhdlTestbenchPrinter(namer).printTestbench(entity);
		writer.write(path, contents);
	}

	@Override
	public String getFileExtension() {
		return "vhd";
	}

	@Override
	public Iterable<String> getLibraries() {
		return ImmutableList.of("com.synflow.lib.Helper_functions");
	}

	@Override
	public String getName() {
		return NgDesignModule.VHDL;
	}

	@Override
	public void transform(Entity entity) {
		new VhdlTransformer().doSwitch(entity);
	}

}
