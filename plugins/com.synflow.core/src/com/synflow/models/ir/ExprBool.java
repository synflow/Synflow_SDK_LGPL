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
 * <li>{@link com.synflow.models.ir.ExprBool#isValue <em>Value</em>}</li>
 * </ul>
 *
 * @see com.synflow.models.ir.IrPackage#getExprBool()
 * @model
 * @generated
 */
public interface ExprBool extends Expression {

	/**
	 * Returns the value of this boolean expression.
	 *
	 * @return the value of this boolean expression
	 * @model
	 */
	boolean isValue();

	/**
	 * Sets the value of this boolean expression.
	 *
	 * @param value
	 *            the value of this boolean expression
	 */
	void setValue(boolean value);

}
