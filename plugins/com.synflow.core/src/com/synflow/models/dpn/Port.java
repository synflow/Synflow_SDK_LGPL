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

import org.eclipse.emf.common.util.EList;
import com.synflow.models.ir.Var;

/**
 * <!-- begin-user-doc -->This class defines a port. A port has a location, a type, a name.<!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.dpn.Port#getAdditionalInputs <em>Additional Inputs</em>}</li>
 * <li>{@link com.synflow.models.dpn.Port#getAdditionalOutputs <em>Additional Outputs</em>}</li>
 * <li>{@link com.synflow.models.dpn.Port#getInterface <em>Interface</em>}</li>
 * <li>{@link com.synflow.models.dpn.Port#isSynchronous <em>Synchronous</em>}</li>
 * </ul>
 *
 * @see com.synflow.models.dpn.DpnPackage#getPort()
 * @model
 * @generated
 */
public interface Port extends Var {

	/**
	 * Returns the value of the '<em><b>Additional Inputs</b></em>' containment reference list. The
	 * list contents are of type {@link com.synflow.models.ir.Var}. <!-- begin-user-doc -->Returns
	 * additional input variables for +valid +ready ports.<!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Additional Inputs</em>' containment reference list.
	 * @see com.synflow.models.dpn.DpnPackage#getPort_AdditionalInputs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Var> getAdditionalInputs();

	/**
	 * Returns the value of the '<em><b>Additional Outputs</b></em>' containment reference list. The
	 * list contents are of type {@link com.synflow.models.ir.Var}. <!-- begin-user-doc -->Returns
	 * additional output variables for +valid +ready ports.<!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Additional Outputs</em>' containment reference list.
	 * @see com.synflow.models.dpn.DpnPackage#getPort_AdditionalOutputs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Var> getAdditionalOutputs();

	/**
	 * Returns the direction of this port. The port must be contained in an entity, or this method
	 * throws an exception.
	 *
	 * @return a direction
	 */
	Direction getDirection();

	/**
	 * Returns the value of the '<em><b>Interface</b></em>' attribute. The literals are from the
	 * enumeration {@link com.synflow.models.dpn.InterfaceType}. <!-- begin-user-doc --><!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Interface</em>' attribute.
	 * @see com.synflow.models.dpn.InterfaceType
	 * @see #setInterface(InterfaceType)
	 * @see com.synflow.models.dpn.DpnPackage#getPort_Interface()
	 * @model
	 * @generated
	 */
	InterfaceType getInterface();

	/**
	 * Returns the value of the '<em><b>Synchronous</b></em>' attribute. The default value is
	 * <code>"true"</code>. <!-- begin-user-doc --><!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Synchronous</em>' attribute.
	 * @see #setSynchronous(boolean)
	 * @see com.synflow.models.dpn.DpnPackage#getPort_Synchronous()
	 * @model default="true"
	 * @generated
	 */
	boolean isSynchronous();

	/**
	 * Sets the value of the '{@link com.synflow.models.dpn.Port#getInterface <em>Interface</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Interface</em>' attribute.
	 * @see com.synflow.models.dpn.InterfaceType
	 * @see #getInterface()
	 * @generated
	 */
	void setInterface(InterfaceType value);

	/**
	 * Sets the value of the '{@link com.synflow.models.dpn.Port#isSynchronous <em>Synchronous</em>}
	 * ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Synchronous</em>' attribute.
	 * @see #isSynchronous()
	 * @generated
	 */
	void setSynchronous(boolean value);

}
