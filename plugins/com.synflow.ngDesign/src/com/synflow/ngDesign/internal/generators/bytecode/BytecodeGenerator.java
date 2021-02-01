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
package com.synflow.ngDesign.internal.generators.bytecode;

import static com.synflow.core.ICoreConstants.FOLDER_CLASSES;
import static com.synflow.core.ICoreConstants.FOLDER_SIM;
import static com.synflow.ngDesign.NgDesignModule.JAVA;

import java.io.ByteArrayInputStream;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.synflow.core.util.CoreUtil;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Instance;
import com.synflow.models.ir.util.IrUtil;
import com.synflow.ngDesign.internal.generators.AbstractGenerator;

/**
 * This class defines a bytecode generator from IR.
 *
 * @author Matthieu Wipliez
 *
 */
public class BytecodeGenerator extends AbstractGenerator {

	private static final Set<String> specialized = ImmutableSet.of("std.lib.SynchronizerMux", "std.mem.DualPortRAM",
			"std.mem.PseudoDualPortRAM", "std.mem.SinglePortRAM");

	@Override
	public String computeLibPath(String fileName) {
		final String gen = getName().toLowerCase();
		return "/lib/" + gen + "/bin/" + fileName + '.' + getFileExtension();
	}

	@Override
	protected String computePath(String fileName, String fileExt) {
		return FOLDER_SIM + '/' + FOLDER_CLASSES + '/' + fileName + '.' + fileExt;
	}

	@Override
	protected void copyBuiltinFile(String name, boolean force) {
		super.copyBuiltinFile(name, force);

		if (specialized.contains(name)) {
			String prefix = name + "$" + IrUtil.getSimpleName(name);
			copyBuiltinFile(prefix + "BigInteger", true);
			copyBuiltinFile(prefix + "Int", true);
			copyBuiltinFile(prefix + "Long", true);
		}
	}

	@Override
	protected void doPrint(Entity entity) {
		try {
			byte[] code = EntityCompiler.compile(entity);
			writer.write(computePath(entity), new ByteArrayInputStream(code));
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPrintTestbench(Entity entity) {
		if (CoreUtil.needsWrapper(entity)) {
			DPN dpn = createTestDpn(entity);

			// print generated entities
			for (Instance instance : dpn.getInstances()) {
				Entity ent = instance.getEntity();
				if (ent != entity) {
					print(ent);
				}
			}

			// print network
			dpn.setName(dpn.getName() + "_test");
			print(dpn);
		}
	}

	@Override
	public String getFileExtension() {
		return "class";
	}

	@Override
	public Iterable<String> getLibraries() {
		return ImmutableList.of("com.synflow.runtime.JsonReader", "com.synflow.runtime.JsonWriter",
				"com.synflow.runtime.Port", "com.synflow.runtime.VcdWriter", "std.fifo.SynchronousFIFO");
	}

	@Override
	public String getName() {
		return JAVA;
	}

	@Override
	public void transform(Entity entity) {
		new BytecodeTransformer().doSwitch(entity);
	}

}
