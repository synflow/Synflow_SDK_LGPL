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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Expr Ternary</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.ir.ExprTernary#getCond <em>Cond</em>}</li>
 * <li>{@link com.synflow.models.ir.ExprTernary#getE1 <em>E1</em>}</li>
 * <li>{@link com.synflow.models.ir.ExprTernary#getE2 <em>E2</em>}</li>
 * </ul>
 *
 * @see com.synflow.models.ir.IrPackage#getExprTernary()
 * @model
 * @generated
 */
public interface ExprTernary extends Expression {
	/**
	 * Returns the value of the '<em><b>Cond</b></em>' containment reference. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Cond</em>' containment reference isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Cond</em>' containment reference.
	 * @see #setCond(Expression)
	 * @see com.synflow.models.ir.IrPackage#getExprTernary_Cond()
	 * @model containment="true"
	 * @generated
	 */
	Expression getCond();

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.ExprTernary#getCond <em>Cond</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Cond</em>' containment reference.
	 * @see #getCond()
	 * @generated
	 */
	void setCond(Expression value);

	/**
	 * Returns the value of the '<em><b>E1</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>E1</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>E1</em>' containment reference.
	 * @see #setE1(Expression)
	 * @see com.synflow.models.ir.IrPackage#getExprTernary_E1()
	 * @model containment="true"
	 * @generated
	 */
	Expression getE1();

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.ExprTernary#getE1 <em>E1</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>E1</em>' containment reference.
	 * @see #getE1()
	 * @generated
	 */
	void setE1(Expression value);

	/**
	 * Returns the value of the '<em><b>E2</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>E2</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>E2</em>' containment reference.
	 * @see #setE2(Expression)
	 * @see com.synflow.models.ir.IrPackage#getExprTernary_E2()
	 * @model containment="true"
	 * @generated
	 */
	Expression getE2();

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.ExprTernary#getE2 <em>E2</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>E2</em>' containment reference.
	 * @see #getE2()
	 * @generated
	 */
	void setE2(Expression value);

} // ExprTernary
