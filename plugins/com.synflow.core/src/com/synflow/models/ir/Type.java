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
 
package com.synflow.models.ir;

import org.eclipse.emf.ecore.EObject;

/**
 * This interface defines a type.
 *
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * @model abstract="true"
 *
 */
public interface Type extends EObject {

	/**
	 * Returns true if this type is <tt>List</tt>.
	 *
	 * @return true if this type is <tt>List</tt>
	 */
	boolean isArray();

	/**
	 * Returns true if this type is <tt>bool</tt>.
	 *
	 * @return true if this type is <tt>bool</tt>
	 */
	boolean isBool();

	/**
	 * Returns true if this type is <tt>float</tt>.
	 *
	 * @return true if this type is <tt>float</tt>
	 */
	boolean isFloat();

	/**
	 * Returns true if this type is <tt>int</tt>.
	 *
	 * @return true if this type is <tt>int</tt>
	 */
	boolean isInt();

	/**
	 * Returns true if this type is <tt>String</tt>.
	 *
	 * @return true if this type is <tt>String</tt>
	 */
	boolean isString();

	/**
	 * Returns true if this type is <tt>void</tt>.
	 *
	 * @return true if this type is <tt>void</tt>
	 */
	boolean isVoid();

}
