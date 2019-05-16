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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Expr Cast</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.ir.ExprUnary#getExpr <em>Expr</em>}</li>
 * <li>{@link com.synflow.models.ir.ExprUnary#getOp <em>Op</em>}</li>
 * </ul>
 *
 * @see com.synflow.models.ir.IrPackage#getExprUnary()
 * @model
 * @generated
 */
public interface ExprUnary extends Expression {

	/**
	 * Returns the operand of this unary expression as an expression.
	 *
	 * @return the operand of this unary expression
	 * @model containment="true"
	 */
	Expression getExpr();

	/**
	 * Returns the operator of this unary expression.
	 *
	 * @return the operator of this unary expression
	 * @model
	 */
	OpUnary getOp();

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.ExprUnary#getExpr <em>Expr</em>}'
	 * containment reference. <!-- begin-user-doc --><!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Expr</em>' containment reference.
	 * @see #getExpr()
	 * @generated
	 */
	void setExpr(Expression value);

	/**
	 * Sets the value of the '{@link com.synflow.models.ir.ExprUnary#getOp <em>Op</em>}' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Op</em>' attribute.
	 * @see com.synflow.models.ir.OpUnary
	 * @see #getOp()
	 * @generated
	 */
	void setOp(OpUnary value);

}
