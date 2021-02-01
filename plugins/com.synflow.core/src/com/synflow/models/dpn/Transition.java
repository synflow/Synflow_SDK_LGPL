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
import org.eclipse.emf.ecore.EObject;

import com.synflow.models.graph.Edge;

/**
 * This class defines a transition of a Finite State Machine.
 *
 * @author Matthieu Wipliez
 * @model
 */
public interface Transition extends Edge {

	<T> T get(String name);

	/**
	 * Returns the value of the '<em><b>Action</b></em>' reference. <!-- begin-user-doc -->Returns
	 * the action that is associated with this transition, or <code>null</code> if there is no
	 * action associated with this transition. <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Action</em>' reference.
	 * @see #setAction(Action)
	 * @see com.synflow.models.dpn.DpnPackage#getTransition_Action()
	 * @model
	 * @generated
	 */
	Action getAction();

	/**
	 * Returns the value of the '<em><b>Body</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.emf.ecore.EObject}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' reference list isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Body</em>' reference list.
	 * @see com.synflow.models.dpn.DpnPackage#getTransition_Body()
	 * @model
	 * @generated
	 */
	EList<EObject> getBody();

	/**
	 * Returns the value of the '<em><b>Lines</b></em>' attribute list. The list contents are of
	 * type {@link java.lang.Integer}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lines</em>' attribute list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Lines</em>' attribute list.
	 * @see com.synflow.models.dpn.DpnPackage#getTransition_Lines()
	 * @model
	 * @generated
	 */
	EList<Integer> getLines();

	/**
	 * Returns the value of the '<em><b>Scheduler</b></em>' reference list. The list contents are of
	 * type {@link org.eclipse.emf.ecore.EObject}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scheduler</em>' reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Scheduler</em>' reference list.
	 * @see com.synflow.models.dpn.DpnPackage#getTransition_Scheduler()
	 * @model
	 * @generated
	 */
	EList<EObject> getScheduler();

	@Override
	State getSource();

	@Override
	State getTarget();

	<T> void put(String name, T value);

	/**
	 * Sets the value of the '{@link com.synflow.models.dpn.Transition#getAction <em>Action</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Action</em>' reference.
	 * @see #getAction()
	 * @generated
	 */
	void setAction(Action value);

}
