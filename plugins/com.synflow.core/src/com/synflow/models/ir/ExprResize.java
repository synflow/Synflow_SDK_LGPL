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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Expr Resize</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.ir.ExprResize#getExpr <em>Expr</em>}</li>
 * <li>{@link com.synflow.models.ir.ExprResize#getTargetSize <em>Target Size</em>}</li>
 * </ul>
 *
 * @see com.synflow.models.ir.IrPackage#getExprResize()
 * @model
 * @generated
 */
public interface ExprResize extends Expression {
	/**
	 * Returns the value of the '<em><b>Expr</b></em>' containment reference. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Expr</em>' containment reference isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Expr</em>' containment reference.
	 * @see #setExpr(Expression)
	 * @see com.synflow.models.ir.IrPackage#getExprResize_Expr()
	 * @model containment="true"
	 * @generated
	 */
	Expression getExpr();

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.ExprResize#getExpr <em>Expr</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Expr</em>' containment reference.
	 * @see #getExpr()
	 * @generated
	 */
	void setExpr(Expression value);

	/**
	 * Returns the value of the '<em><b>Target Size</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Size</em>' attribute isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Target Size</em>' attribute.
	 * @see #setTargetSize(int)
	 * @see com.synflow.models.ir.IrPackage#getExprResize_TargetSize()
	 * @model
	 * @generated
	 */
	int getTargetSize();

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.ExprResize#getTargetSize
	 * <em>Target Size</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Target Size</em>' attribute.
	 * @see #getTargetSize()
	 * @generated
	 */
	void setTargetSize(int value);

} // ExprResize
