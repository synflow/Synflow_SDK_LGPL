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
 
package com.synflow.models.ir.transform;

import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.synflow.models.ir.Var;

/**
 * This class defines a name computer that generates unique names.
 *
 * @author Matthieu Wipliez
 *
 */
public class UniqueNameComputer {

	private Set<String> names;

	public UniqueNameComputer(Iterable<String> names) {
		this.names = Sets.newHashSet(names);
	}

	public UniqueNameComputer(List<Var> variables) {
		this(Iterables.transform(variables, new Function<Var, String>() {
			public String apply(Var variable) {
				return variable.getName();
			}
		}));
	}

	/**
	 * Returns a unique name based on the given name.
	 *
	 * @param name
	 *            name
	 * @return a unique name based on the given name
	 */
	public String getUniqueName(String name) {
		String uniqueName = name;
		int i = 1;
		while (names.contains(uniqueName)) {
			uniqueName = name + "_" + i;
			i++;
		}
		names.add(uniqueName);
		return uniqueName;
	}

}
