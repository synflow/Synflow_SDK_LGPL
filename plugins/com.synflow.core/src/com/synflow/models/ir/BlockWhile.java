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
 * <!-- begin-user-doc -->This class defines a While block. A while block is a block with a value
 * used in its condition.<!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.ir.BlockWhile#getCondition <em>Condition</em>}</li>
 * <li>{@link com.synflow.models.ir.BlockWhile#getJoinBlock <em>Join Block</em>}</li>
 * <li>{@link com.synflow.models.ir.BlockWhile#getLineNumber <em>Line Number</em>}</li>
 * <li>{@link com.synflow.models.ir.BlockWhile#getBlocks <em>Blocks</em>}</li>
 * </ul>
 *
 * @see com.synflow.models.ir.IrPackage#getBlockWhile()
 * @model
 * @generated
 */
public interface BlockWhile extends Block {

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference. <!--
	 * begin-user-doc --><!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(Expression)
	 * @see com.synflow.models.ir.IrPackage#getBlockWhile_Condition()
	 * @model containment="true"
	 * @generated
	 */
	Expression getCondition();

	/**
	 * Returns the value of the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc
	 * -->Returns the line number on which this "while" starts.
	 *
	 * @return the line number on which this "while" starts<!-- end-user-doc -->
	 * @return the value of the '<em>Line Number</em>' attribute.
	 * @see #setLineNumber(int)
	 * @see com.synflow.models.ir.IrPackage#getBlockWhile_LineNumber()
	 * @model
	 * @generated
	 */
	int getLineNumber();

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.BlockWhile#getCondition
	 * <em>Condition</em>}' containment reference. <!-- begin-user-doc --><!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(Expression value);

	/**
	 * Returns the value of the '<em><b>Join Block</b></em>' containment reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Block</em>' containment reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Join Block</em>' containment reference.
	 * @see #setJoinBlock(BlockBasic)
	 * @see com.synflow.models.ir.IrPackage#getBlockWhile_JoinBlock()
	 * @model containment="true"
	 * @generated
	 */
	BlockBasic getJoinBlock();

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.BlockWhile#getJoinBlock
	 * <em>Join Block</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Join Block</em>' containment reference.
	 * @see #getJoinBlock()
	 * @generated
	 */
	void setJoinBlock(BlockBasic value);

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.BlockWhile#getLineNumber
	 * <em>Line Number</em>}' attribute. <!-- begin-user-doc -->Sets the line number on which this
	 * "while" starts. <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Line Number</em>' attribute.
	 * @see #getLineNumber()
	 * @generated
	 */
	void setLineNumber(int value);

	/**
	 * Returns the value of the '<em><b>Blocks</b></em>' containment reference list. The list
	 * contents are of type {@link com.synflow.models.ir.Block}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Blocks</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Blocks</em>' containment reference list.
	 * @see com.synflow.models.ir.IrPackage#getBlockWhile_Blocks()
	 * @model containment="true"
	 * @generated
	 */
	EList<Block> getBlocks();

}
