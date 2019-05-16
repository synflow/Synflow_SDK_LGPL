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
 * This interface defines an instruction that Loads data from memory to a local variable. The source
 * can be a global (scalar or array), or a local array.
 *
 * @author Matthieu Wipliez
 * @model extends="net.sf.orcc.ir.Instruction"
 */
public interface InstLoad extends Instruction {

	/**
	 * Returns the (possibly empty) list of indexes of this load.
	 *
	 * @return the (possibly empty) list of indexes of this load
	 * @model containment="true"
	 */
	EList<Expression> getIndexes();

	/**
	 * Returns the variable loaded by this load instruction.
	 *
	 * @return the variable loaded by this load instruction
	 * @model containment="true"
	 */
	Use getSource();

	/**
	 * Returns the target of this load instruction.
	 *
	 * @return the target of this load instruction
	 * @model containment="true"
	 */
	Def getTarget();

	/**
	 * Sets the variable loaded by this load instruction.
	 *
	 * @param source
	 *            the variable loaded by this load instruction
	 */
	void setSource(Use source);

	/**
	 * Sets the target of this load.
	 *
	 * @param target
	 *            a local variable
	 */
	void setTarget(Def target);

}
