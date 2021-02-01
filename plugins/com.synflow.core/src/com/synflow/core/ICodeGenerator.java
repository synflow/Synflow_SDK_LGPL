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
 
package com.synflow.core;

import com.synflow.models.dpn.Entity;

/**
 * This interface defines a code generator. A code generator can be initialized, and defines a
 * doSwitch method that visits an object to generate code.
 *
 * @author Matthieu Wipliez
 *
 */
public interface ICodeGenerator extends IPathResolver {

	/**
	 * Copies libraries of the given generator to the generator's output folder.
	 */
	void copyLibraries();

	/**
	 * Performs a full code generation with the given top entity.
	 *
	 * @param entity
	 */
	void fullBuild(Entity entity);

	/**
	 * Returns the file extension of files this generator generates (e.g. "c").
	 *
	 * @return the file extension
	 */
	String getFileExtension();

	/**
	 * Returns the file writer used by this code generator.
	 *
	 * @return a file writer
	 */
	IFileWriter getFileWriter();

	/**
	 * Returns an iterable of support libraries required by this generator.
	 *
	 * @return an iterable of qualified names
	 */
	Iterable<String> getLibraries();

	/**
	 * Returns the name of this generator.
	 *
	 * @return the name of this generator
	 */
	String getName();

	/**
	 * Prints code for the given object, unless it has an 'implementation' property.
	 *
	 * @param entity
	 *            entity
	 */
	void print(Entity entity);

	/**
	 * Prints a test bench for the given object.
	 *
	 * @param entity
	 *            actor or network
	 */
	void printTestbench(Entity entity);

	/**
	 * Removes the file corresponding to the given qualified name.
	 *
	 * @param name
	 *            qualified name
	 */
	void remove(String name);

	/**
	 * Sets the output folder that this generator will use.
	 *
	 * @param name
	 *            folder name
	 */
	void setOutputFolder(String name);

	/**
	 * Transforms the given entity in place.
	 *
	 * @param entity
	 *            an entity to transform
	 */
	void transform(Entity entity);

}
