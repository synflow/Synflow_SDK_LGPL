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

import org.eclipse.emf.common.util.EList;

/**
 * This interface defines a instruction that Stores data to memory from an expression. The target
 * can be a global (scalar or array), or a local array.
 *
 * @author Matthieu Wipliez
 * @model extends="net.sf.orcc.ir.Instruction"
 */
public interface InstStore extends Instruction {

	/**
	 * Returns the (possibly empty) list of indexes of this store.
	 *
	 * @return the (possibly empty) list of indexes of this store
	 * @model containment="true"
	 */
	EList<Expression> getIndexes();

	/**
	 * Returns the target of this store.
	 *
	 * @return the target of this store
	 * @model containment="true"
	 */
	Def getTarget();

	/**
	 * Returns the value of this store.
	 *
	 * @return the value of this store
	 * @model containment="true"
	 */
	Expression getValue();

	/**
	 * Sets the target of this store.
	 *
	 * @param target
	 *            a local variable
	 */
	void setTarget(Def target);

	/**
	 * Sets the value of this store.
	 *
	 * @param value
	 *            an expression
	 */
	void setValue(Expression value);

}
