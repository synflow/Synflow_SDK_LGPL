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

import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> This class defines a basic block. A basic block only contains
 * instructions.<!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.ir.BlockBasic#getInstructions <em>Instructions</em>}</li>
 * </ul>
 *
 * @see com.synflow.models.ir.IrPackage#getBlockBasic()
 * @model
 * @generated
 */
public interface BlockBasic extends Block {

	/**
	 * Appends the specified instruction to the end of this block.
	 *
	 * @param instruction
	 *            an instruction
	 */
	void add(Instruction instruction);

	/**
	 * Appends the specified instruction to this block at the specified index.
	 *
	 * @param index
	 *            the index
	 * @param instruction
	 *            an instruction
	 */
	void add(int index, Instruction instruction);

	/**
	 * Returns the value of the '<em><b>Instructions</b></em>' containment reference list. The list
	 * contents are of type {@link com.synflow.models.ir.Instruction}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Instructions</em>' containment reference list.
	 * @see com.synflow.models.ir.IrPackage#getBlockBasic_Instructions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Instruction> getInstructions();

	/**
	 * Returns the index of the given instruction in the list of instructions of this block.
	 *
	 * @param instruction
	 *            an instruction
	 * @return the index of the given instruction in the list of instructions of this block
	 */
	int indexOf(Instruction instruction);

	Iterator<Instruction> iterator();

	/**
	 * Returns a list iterator over the elements in this list (in proper sequence) that is
	 * positioned after the last instruction.
	 *
	 * @return a list iterator over the elements in this list (in proper sequence)
	 */
	ListIterator<Instruction> lastListIterator();

	/**
	 * Returns a list iterator over the elements in this list (in proper sequence).
	 *
	 * @return a list iterator over the elements in this list (in proper sequence)
	 */
	ListIterator<Instruction> listIterator();

	/**
	 * Returns a list iterator over the elements in this list already positioned at index (in proper
	 * sequence).
	 *
	 * @return a list iterator over the elements in this list already positioned at index (in proper
	 *         sequence)
	 */
	ListIterator<Instruction> listIterator(int index);

}
