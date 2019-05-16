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
 
package com.synflow.core;

import com.synflow.models.dpn.Entity;

public interface IPathResolver {

	/**
	 * Returns the path to the given entity. Equivalent to
	 * <code>computePath(IrUtil.getFile(entity.getName()))</code>
	 *
	 * @param entity
	 *            an entity
	 * @return a path
	 */
	String computePath(Entity entity);

	/**
	 * Returns a path composed of this generator's name (possibly with a '-gen' suffix, depending on
	 * generators), the given file name and this generator's file extension.
	 *
	 * @param fileName
	 *            file name
	 * @return a path
	 */
	String computePath(String fileName);

	/**
	 * Returns <code>'testbench/' + file + '.' + fileExtension</code> where file is computed based
	 * on the given entity's name.
	 *
	 * @param entity
	 *            an entity
	 * @return a path
	 */
	String computePathTb(Entity entity);

	/**
	 * Returns the absolute path to the given entity.
	 *
	 * @param entity
	 *            an entity
	 * @return a path
	 */
	String getFullPath(Entity entity);

}
