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

/**
 * This interface defines an Assign instruction. The target is a local variable, and the value an
 * expression.
 *
 * @author Matthieu Wipliez
 * @model extends="net.sf.orcc.ir.Instruction"
 */
public interface InstAssign extends Instruction {

	/**
	 * Returns the target of this assignment.
	 *
	 * @return the target of this assignment
	 * @model containment="true"
	 */
	Def getTarget();

	/**
	 * Returns the value of this assignment.
	 *
	 * @return the value of this assignment
	 * @model containment="true"
	 */
	Expression getValue();

	/**
	 * Sets the target of this assignment.
	 *
	 * @param target
	 *            a local variable
	 */
	void setTarget(Def target);

	/**
	 * Sets the value of this assignment. Uses are updated to point to this assignment.
	 *
	 * @param value
	 *            an expression
	 */
	void setValue(Expression value);

}
