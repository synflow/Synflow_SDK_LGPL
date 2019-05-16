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

import java.lang.Integer;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Var</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.ir.TypeArray#getDimensions <em>Dimensions</em>}</li>
 * <li>{@link com.synflow.models.ir.TypeArray#getElementType <em>Element Type</em>}</li>
 * </ul>
 *
 * @see com.synflow.models.ir.IrPackage#getTypeArray()
 * @model
 * @generated
 */
public interface TypeArray extends Type {

	/**
	 * Returns the value of the '<em><b>Dimensions</b></em>' attribute list. The list contents are
	 * of type {@link java.lang.Integer}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Dimensions</em>' attribute list.
	 * @see com.synflow.models.ir.IrPackage#getTypeArray_Dimensions()
	 * @model unique="false"
	 * @generated
	 */
	EList<Integer> getDimensions();

	/**
	 * Returns the value of the '<em><b>Element Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Element Type</em>' containment reference.
	 * @see #setElementType(Type)
	 * @see com.synflow.models.ir.IrPackage#getTypeArray_ElementType()
	 * @model containment="true"
	 * @generated
	 */
	Type getElementType();

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.TypeArray#getElementType
	 * <em>Element Type</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Element Type</em>' containment reference.
	 * @see #getElementType()
	 * @generated
	 */
	void setElementType(Type value);

}
