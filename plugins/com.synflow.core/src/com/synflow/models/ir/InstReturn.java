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
 * This interface defines a Return instruction.
 *
 * @author Matthieu Wipliez
 * @model extends="net.sf.orcc.ir.Instruction"
 */
public interface InstReturn extends Instruction {

	/**
	 * Returns the value returned by this return instruction (may be <code>null</code>).
	 *
	 * @return the value returned by this return instruction (may be <code>null</code>)
	 * @model containment="true"
	 */
	Expression getValue();

	/**
	 * Sets the value returned by this instruction.
	 *
	 * @param value
	 *            an expression
	 */
	void setValue(Expression value);

}
