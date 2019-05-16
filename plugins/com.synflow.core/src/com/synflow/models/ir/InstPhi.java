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

import org.eclipse.emf.common.util.EList;

/**
 * This interface defines an assignment of the result of a <code>phi</code> function to a target
 * local variable.
 *
 * @author Matthieu Wipliez
 * @model extends="net.sf.orcc.ir.Instruction"
 */
public interface InstPhi extends Instruction {

	/**
	 * Returns the "old" variable of this phi. Only used when translating to SSA form.
	 *
	 * @return the "old" variable of this phi
	 * @model
	 */
	Var getOldVariable();

	/**
	 * Returns the target of this call (may be <code>null</code>).
	 *
	 * @return the target of this phi assignment (may be <code>null</code>)
	 * @model containment="true"
	 */
	Def getTarget();

	/**
	 * Returns the values of this phi instruction.
	 *
	 * @return the values of this phi instruction
	 * @model containment="true"
	 */
	EList<Expression> getValues();

	/**
	 * Sets the "old" variable to be remembered when examining the "else" branch of an if. Only used
	 * when translating to SSA form.
	 *
	 * @param old
	 *            an "old" variable
	 */
	void setOldVariable(Var old);

	/**
	 * Sets the target of this phi assignment.
	 *
	 * @param target
	 *            a local variable
	 */
	void setTarget(Def target);

}
