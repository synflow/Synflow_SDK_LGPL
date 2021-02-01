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
 
package com.synflow.models.dpn;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> This class defines an actor. An actor has parameters, input and output
 * ports, state variables, procedures, actions and an FSM.<!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.dpn.Actor#getActions <em>Actions</em>}</li>
 * <li>{@link com.synflow.models.dpn.Actor#getBufferedInputs <em>Buffered Inputs</em>}</li>
 * <li>{@link com.synflow.models.dpn.Actor#getFsm <em>Fsm</em>}</li>
 * </ul>
 *
 * @see com.synflow.models.dpn.DpnPackage#getActor()
 * @model
 * @generated
 */
public interface Actor extends Entity {

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' containment reference list. The list
	 * contents are of type {@link com.synflow.models.dpn.Action}. <!-- begin-user-doc --><!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Actions</em>' containment reference list.
	 * @see com.synflow.models.dpn.DpnPackage#getActor_Actions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Action> getActions();

	/**
	 * Returns the value of the '<em><b>Buffered Inputs</b></em>' reference list. The list contents
	 * are of type {@link com.synflow.models.dpn.Port}. <!-- begin-user-doc -->Returns the list of
	 * buffered input ready ports.<!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Buffered Inputs</em>' reference list.
	 * @see com.synflow.models.dpn.DpnPackage#getActor_BufferedInputs()
	 * @model
	 * @generated
	 */
	EList<Port> getBufferedInputs();

	/**
	 * Returns the value of the '<em><b>Fsm</b></em>' containment reference. <!-- begin-user-doc
	 * --><!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Fsm</em>' containment reference.
	 * @see #setFsm(FSM)
	 * @see com.synflow.models.dpn.DpnPackage#getActor_Fsm()
	 * @model containment="true"
	 * @generated
	 */
	FSM getFsm();

	/**
	 * Returns true if this actor has an FSM.
	 *
	 * @return true if this actor has an FSM
	 */
	boolean hasFsm();

	/**
	 * Sets the value of the '{@link com.synflow.models.dpn.Actor#getFsm <em>Fsm</em>}' containment
	 * reference. <!-- begin-user-doc --><!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Fsm</em>' containment reference.
	 * @see #getFsm()
	 * @generated
	 */
	void setFsm(FSM value);

}
