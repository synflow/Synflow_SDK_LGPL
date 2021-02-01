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
 * This interface defines a Call instruction, which possibly stores the result to a local variable.
 *
 * @author Matthieu Wipliez
 * @model extends="net.sf.orcc.ir.Instruction"
 */
public interface InstCall extends Instruction {

	/**
	 * Returns the arguments of this call instruction.
	 *
	 * @return the arguments of this call instruction
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getArguments();

	/**
	 * Returns the procedure referenced by this call instruction.
	 *
	 * @return the procedure referenced by this call instruction
	 * @model
	 */
	Procedure getProcedure();

	/**
	 * Returns the target of this call (may be <code>null</code>).
	 *
	 * @return the target of this call (may be <code>null</code>)
	 * @model containment="true"
	 */
	Def getTarget();

	/**
	 * Returns <code>true</code> if this call has a result.
	 *
	 * @return <code>true</code> if this call has a result
	 */
	boolean hasResult();

	/**
	 * Returns the value of the '<em><b>Assert</b></em>' attribute. <!-- begin-user-doc --><!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Assert</em>' attribute.
	 * @see #setAssert(boolean)
	 * @see com.synflow.models.ir.IrPackage#getInstCall_Assert()
	 * @model
	 * @generated
	 */
	boolean isAssert();

	/**
	 * Returns the value of the '<em><b>Print</b></em>' attribute. <!-- begin-user-doc --><!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Print</em>' attribute.
	 * @see #setPrint(boolean)
	 * @see com.synflow.models.ir.IrPackage#getInstCall_Print()
	 * @model
	 * @generated
	 */
	boolean isPrint();

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.InstCall#isAssert <em>Assert</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Assert</em>' attribute.
	 * @see #isAssert()
	 * @generated
	 */
	void setAssert(boolean value);

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.InstCall#isPrint <em>Print</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Print</em>' attribute.
	 * @see #isPrint()
	 * @generated
	 */
	void setPrint(boolean value);

	/**
	 * Sets the procedure referenced by this call instruction.
	 *
	 * @param procedure
	 *            a procedure
	 */
	void setProcedure(Procedure procedure);

	/**
	 * Sets the target of this call instruction.
	 *
	 * @param target
	 *            a local variable (may be <code>null</code>)
	 */
	void setTarget(Def target);

}
