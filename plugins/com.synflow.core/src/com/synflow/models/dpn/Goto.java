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

import com.synflow.models.ir.Instruction;

/**
 * <!-- begin-user-doc -->This class defines a state of a Finite State Machine.<!-- end-user-doc -->
 *
 * @author Matthieu Wipliez
 * @model
 */
public interface Goto extends Instruction {

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference. <!-- begin-user-doc --><!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(State)
	 * @see com.synflow.models.dpn.DpnPackage#getGoto_Target()
	 * @model
	 * @generated
	 */
	State getTarget();

	/**
	 * Sets the value of the '{@link com.synflow.models.dpn.Goto#getTarget <em>Target</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(State value);

}
