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

/**
 * This class defines an integer type.
 *
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * @model extends="net.sf.orcc.ir.Type"
 *
 */
public interface TypeInt extends Type {

	/**
	 * Returns the value of the '<em><b>Size</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Size</em>' attribute.
	 * @see #setSize(int)
	 * @see com.synflow.models.ir.IrPackage#getTypeInt_Size()
	 * @model
	 * @generated
	 */
	int getSize();

	/**
	 * Returns the value of the '<em><b>Signed</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Signed</em>' attribute.
	 * @see #setSigned(boolean)
	 * @see com.synflow.models.ir.IrPackage#getTypeInt_Signed()
	 * @model
	 * @generated
	 */
	boolean isSigned();

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.TypeInt#isSigned <em>Signed</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Signed</em>' attribute.
	 * @see #isSigned()
	 * @generated
	 */
	void setSigned(boolean value);

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.TypeInt#getSize <em>Size</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Size</em>' attribute.
	 * @see #getSize()
	 * @generated
	 */
	void setSize(int value);

}
