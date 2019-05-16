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
package com.synflow.ngDesign.internal.generators

import com.synflow.models.dpn.Entity
import com.synflow.models.dpn.Instance
import com.synflow.models.dpn.Port
import com.synflow.models.dpn.State
import com.synflow.models.ir.Procedure
import com.synflow.models.ir.Var
import java.util.Set
import java.util.regex.Pattern

/**
 * This class defines a Namer, that knows how to print ports and variables, and
 * escape them if necessary.
 *
 * @author Matthieu Wipliez
 */
class Namer {

	val String first

	val String last

	val Pattern pattern

	val Set<String> reserved

	/**
	 * Creates a new namer.
	 *
	 * @param reserved
	 *            set of reserved identifiers
	 * @param first
	 *            first character of the escape sequence
	 * @param last
	 *            first character of the escape sequence
	 */
	new(Set<String> reserved, String first, String last) {
		this.reserved = reserved
		this.pattern = null
		this.first = first
		this.last = last
	}

	/**
	 * Creates a new namer.
	 *
	 * @param reserved
	 *            set of reserved identifiers
	 * @param pattern
	 *            pattern that identifies reserved character sequences (may be
	 *            <code>null</code>)
	 * @param first
	 *            first character of the escape sequence
	 * @param last
	 *            first character of the escape sequence
	 */
	new(Set<String> reserved, Pattern pattern, String first, String last) {
		this.reserved = reserved
		this.pattern = pattern
		this.first = first
		this.last = last
	}

	protected new(Namer namer) {
		this.reserved = namer.reserved
		this.pattern = namer.pattern
		this.first = namer.first
		this.last = namer.last
	}

	def private String escape(String name) {
		'''«first»«name»«last»'''
	}

	def getName(Entity entity) {
		getSafeName(entity.simpleName)
	}

	def getName(Instance instance) {
		getSafeName(instance.name)
	}

	def getName(Port port) {
		getSafeName(port.name)
	}

	def getName(Procedure procedure) {
		getSafeName(procedure.name)
	}

	def getName(State state) {
		getSafeName(state.name)
	}

	def getName(Var variable) {
		getSafeName(variable.name)
	}

	/**
	 * If the given name needs to be escaped, escapes it.
	 */
	def final getSafeName(String name) {
		if (needsEscaping(name)) {
			name.escape
		} else {
			name
		}
	}

	/**
	 * Returns <code>true</code> if the given name needs escaping.
	 *
	 * @param name
	 *            name of a variable, parameter, port, procedure...
	 * @return <code>true</code> if the given name needs escaping
	 */
	def private needsEscaping(String name) {
		reserved.contains(name.toLowerCase()) || (pattern !== null && pattern.matcher(name).find())
	}

}
