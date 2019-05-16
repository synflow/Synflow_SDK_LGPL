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
 
package com.synflow.models.dpn;

import org.eclipse.emf.ecore.EObject;

import com.synflow.models.ir.Expression;
import com.synflow.models.ir.Var;

/**
 * This interface defines an argument that can be given to an instance.
 *
 * @author Matthieu Wipliez
 * @model
 */
public interface Argument extends EObject {

	/**
	 * Returns the value of this argument.
	 *
	 * @return the value of this argument
	 * @model containment="true"
	 */
	Expression getValue();

	/**
	 * Returns the variable to which this argument associates a value.
	 *
	 * @return the variable to which this argument associates a value
	 * @model
	 */
	Var getVariable();

	/**
	 * Sets the value of this attribute.
	 *
	 * @param newValue
	 *            a value
	 */
	void setValue(Expression newValue);

	/**
	 * Sets the variable to which this argument associates a value.
	 *
	 * @param newVariable
	 *            a variable
	 */
	void setVariable(Var newVariable);

}
