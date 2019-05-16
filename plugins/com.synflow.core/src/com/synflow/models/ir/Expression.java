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

import org.eclipse.emf.ecore.EObject;

/**
 * This interface defines an expression.
 *
 * @author Matthieu Wipliez
 * @model abstract="true"
 *
 */
public interface Expression extends EObject {

	/**
	 * Returns the value of the '<em><b>Computed Type</b></em>' containment reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Computed Type</em>' containment reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Computed Type</em>' containment reference.
	 * @see #setComputedType(Type)
	 * @see com.synflow.models.ir.IrPackage#getExprBinary_ComputedType()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	Type getComputedType();

	/**
	 * Returns true if the expression is a binary expression.
	 *
	 * @return true if the expression is a binary expression
	 */
	boolean isExprBinary();

	/**
	 * Returns true if the expression is a boolean expression.
	 *
	 * @return true if the expression is a boolean expression
	 */
	boolean isExprBool();

	/**
	 * Returns true if the expression is a float expression.
	 *
	 * @return true if the expression is a float expression
	 */
	boolean isExprFloat();

	/**
	 * Returns true if the expression is an integer expression.
	 *
	 * @return true if the expression is an integer expression
	 */
	boolean isExprInt();

	/**
	 * Returns true if the expression is a list expression.
	 *
	 * @return true if the expression is a list expression
	 */
	boolean isExprList();

	boolean isExprResize();

	/**
	 * Returns true if the expression is a string expression.
	 *
	 * @return true if the expression is a string expression
	 */
	boolean isExprString();

	boolean isExprTypeConv();

	/**
	 * Returns true if the expression is a unary expression.
	 *
	 * @return true if the expression is a unary expression
	 */
	boolean isExprUnary();

	/**
	 * Returns true if the expression is a variable expression.
	 *
	 * @return true if the expression is a variable expression
	 */
	boolean isExprVar();

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.Expression#getComputedType
	 * <em>Computed Type</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Computed Type</em>' containment reference.
	 * @see #getComputedType()
	 * @generated
	 */
	void setComputedType(Type value);

}
