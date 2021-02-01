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
 
package com.synflow.models.dpn;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines a pattern as a list of ports that are read/written.
 *
 * @author Matthieu Wipliez
 * @model
 */
public interface Pattern extends EObject {

	/**
	 * Adds the ports of the given pattern to this pattern.
	 *
	 * @param pattern
	 *            a pattern
	 */
	void add(Pattern pattern);

	void add(Port port);

	/**
	 * Clears this pattern.
	 */
	void clear();

	/**
	 * Returns <code>true</code> if this pattern contains the given port.
	 *
	 * @param port
	 *            a port
	 * @return a boolean
	 */
	boolean contains(Port port);

	/**
	 * Returns the ports of this pattern.
	 *
	 * @return the ports of this pattern
	 * @model
	 */
	EList<Port> getPorts();

	/**
	 * Returns <code>true</code> if this pattern is empty.
	 *
	 * @return <code>true</code> if this pattern is empty
	 */
	boolean isEmpty();

}
