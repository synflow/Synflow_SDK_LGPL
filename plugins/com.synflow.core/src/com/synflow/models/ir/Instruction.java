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
 * This class defines an instruction.
 *
 * @author Matthieu Wipliez
 * @model abstract="true"
 */
public interface Instruction extends EObject {

	/**
	 * Returns the block that contains this instruction.
	 *
	 * @return the block that contains this instruction
	 */
	BlockBasic getBlock();

	/**
	 * Returns the line number of this instruction.
	 *
	 * @return the line number of this instruction
	 * @model
	 */
	public int getLineNumber();

	/**
	 * Returns <code>true</code> if the instruction is an Assign.
	 *
	 * @return <code>true</code> if the instruction is an Assign
	 */
	boolean isInstAssign();

	/**
	 * Returns <code>true</code> if the instruction is a Call.
	 *
	 * @return <code>true</code> if the instruction is a Call
	 */
	boolean isInstCall();

	/**
	 * Returns <code>true</code> if the instruction is a Load.
	 *
	 * @return <code>true</code> if the instruction is a Load
	 */
	boolean isInstLoad();

	/**
	 * Returns <code>true</code> if the instruction is a Phi.
	 *
	 * @return <code>true</code> if the instruction is a Phi
	 */
	boolean isInstPhi();

	/**
	 * Returns <code>true</code> if the instruction is a Return.
	 *
	 * @return <code>true</code> if the instruction is a Return
	 */
	boolean isInstReturn();

	/**
	 * Return <code>true</code> if the instruction is a backend specific instruction
	 *
	 * @return <code>true</code> if the instruction is a backend specific instruction
	 */
	boolean isInstSpecific();

	/**
	 * Returns <code>true</code> if the instruction is a Store.
	 *
	 * @return <code>true</code> if the instruction is a Store
	 */
	boolean isInstStore();

	/**
	 * Sets the line number of this instruction.
	 *
	 * @param newLineNumber
	 *            the line number of this instruction
	 */
	void setLineNumber(int newLineNumber);

}
