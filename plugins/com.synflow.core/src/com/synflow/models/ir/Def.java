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
 
package com.synflow.models.ir;

import org.eclipse.emf.ecore.EObject;

/**
 * This interface defines a definition of a variable.
 *
 * @author Matthieu Wipliez
 * @model
 */
public interface Def extends EObject {

	/**
	 * Returns the var defined by this definition.
	 *
	 * @return the var defined by this definition
	 * @model type="Var" opposite="defs"
	 */
	Var getVariable();

	/**
	 * Sets the variable defined by this definition.
	 *
	 * @param variable
	 *            the variable defined by this definition
	 */
	void setVariable(Var variable);

}
