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

import java.util.List;

import com.synflow.models.graph.Vertex;

/**
 * <!-- begin-user-doc -->This class defines a state of a Finite State Machine.<!-- end-user-doc -->
 *
 * @author Matthieu Wipliez
 * @model
 */
public interface State extends Vertex {

	<T> T get(String key);

	List<Action> getActions();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --><!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.synflow.models.dpn.DpnPackage#getState_Name()
	 * @model
	 * @generated
	 */
	String getName();

	<T> void put(String key, T value);

	/**
	 * Sets the value of the '{@link com.synflow.models.dpn.State#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

}
