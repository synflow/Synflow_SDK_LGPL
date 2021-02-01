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
 
package com.synflow.models.ir;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Expr Cast</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.ir.ExprBinary#getE1 <em>E1</em>}</li>
 * <li>{@link com.synflow.models.ir.ExprBinary#getE2 <em>E2</em>}</li>
 * <li>{@link com.synflow.models.ir.ExprBinary#getOp <em>Op</em>}</li>
 * </ul>
 *
 * @see com.synflow.models.ir.IrPackage#getExprBinary()
 * @model
 * @generated
 */
public interface ExprBinary extends Expression {

	/**
	 * Returns the first operand of this binary expression as an expression.
	 *
	 * @return the first operand of this binary expression
	 * @model containment="true"
	 */
	Expression getE1();

	/**
	 * Returns the second operand of this binary expression as an expression.
	 *
	 * @return the second operand of this binary expression
	 * @model containment="true"
	 */
	Expression getE2();

	/**
	 * Returns the operator of this binary expression.
	 *
	 * @return the operator of this binary expression
	 * @model
	 */
	OpBinary getOp();

	/**
	 * Sets the first operand of this binary expression as an expression.
	 *
	 * @param e1
	 *            the first operand of this binary expression
	 */
	void setE1(Expression e1);

	/**
	 * Sets the second operand of this binary expression as an expression.
	 *
	 * @param e2
	 *            the second operand of this binary expression
	 */
	void setE2(Expression e2);

	/**
	 * Sets the operator of this binary expression.
	 *
	 * @param op
	 *            the operator of this binary expression
	 */
	void setOp(OpBinary op);

}
