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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.Var;

/**
 * This class defines utility methods for VHDL generation.
 *
 * @author Matthieu Wipliez
 *
 */
public class VhdlGeneratorUtil {

	private static String computeName(Map<String, Var> inverseMap, String name) {
		int i = 0;
		String candidate;
		do {
			i++;
			candidate = name + "_unique_" + i;
		} while (inverseMap.containsKey(candidate));

		return candidate;
	}

	/**
	 * computes the local variable map that associates each local variable of each action's body
	 * with a name. The implementation uses two maps rather than a BiMap because BiMap does not
	 * support multiple values for a given key.
	 */
	public static Collection<Var> computeVarMap(Map<Var, String> varMap, List<Procedure> procs) {
		Map<String, Var> inverseMap = new LinkedHashMap<>();
		for (Procedure proc : procs) {
			for (Var local : proc.getLocals()) {
				Type type = local.getType();
				String name = local.getName();

				Var existing = inverseMap.get(name);
				if (existing == null || EcoreUtil.equals(type, existing.getType())) {
					varMap.put(local, name);
					inverseMap.put(name, local);
				} else {
					String typedName = computeName(inverseMap, name);
					varMap.put(local, typedName);
					inverseMap.put(typedName, local);
				}
			}
		}

		return inverseMap.values();
	}

}
