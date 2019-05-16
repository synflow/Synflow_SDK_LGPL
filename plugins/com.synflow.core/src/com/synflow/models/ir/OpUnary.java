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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.Enumerator;

/**
 * This class defines the unary operators of the IR.
 *
 * @author Matthieu Wipliez
 * @model
 */
public enum OpUnary implements Enumerator {

	/**
	 * a binary not <code>~</code>
	 *
	 * @model
	 */
	BITNOT(0, "BITNOT", "BITNOT"),

	/**
	 * a logical not <code>!</code>
	 *
	 * @model
	 */
	LOGIC_NOT(1, "LOGIC_NOT", "LOGIC_NOT"),

	/**
	 * unary minus <code>-</code>
	 *
	 * @model
	 */
	MINUS(2, "MINUS", "MINUS");

	/**
	 * The '<em><b>BITNOT</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BITNOT</b></em>' literal object isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @see #BITNOT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BITNOT_VALUE = 0;

	/**
	 * The '<em><b>LOGIC NOT</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LOGIC NOT</b></em>' literal object isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @see #LOGIC_NOT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LOGIC_NOT_VALUE = 1;

	/**
	 * The '<em><b>MINUS</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>MINUS</b></em>' literal object isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @see #MINUS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MINUS_VALUE = 2;

	private static final String[] NAMES_ARRAY = new String[] { "~", "!", "-" };

	private static final Map<String, OpUnary> operators = new HashMap<String, OpUnary>();

	/**
	 * An array of all the '<em><b>Op Unary</b></em>' enumerators. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	private static final OpUnary[] VALUES_ARRAY = new OpUnary[] { BITNOT, LOGIC_NOT, MINUS, };

	/**
	 * A public read-only list of all the '<em><b>Op Unary</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final List<OpUnary> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	static {
		for (OpUnary op : OpUnary.values()) {
			operators.put(op.getText(), op);
		}
	}

	/**
	 * Returns the '<em><b>Op Unary</b></em>' literal with the specified integer value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static OpUnary get(int value) {
		switch (value) {
		case BITNOT_VALUE:
			return BITNOT;
		case LOGIC_NOT_VALUE:
			return LOGIC_NOT;
		case MINUS_VALUE:
			return MINUS;
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Op Unary</b></em>' literal with the specified literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static OpUnary get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OpUnary result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Op Unary</b></em>' literal with the specified name. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static OpUnary getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OpUnary result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the unary operator that has the given name.
	 *
	 * @param name
	 *            an operator name
	 * @return a unary operator
	 * @throws OrccRuntimeException
	 *             if there is no operator with the given name
	 */
	public static OpUnary getOperator(String name) {
		OpUnary op = operators.get(name);
		if (op == null) {
			throw new IllegalArgumentException("unknown operator \"" + name + "\"");
		} else {
			return op;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private final String literal;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private final int value;

	/**
	 * Only this class can construct instances. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private OpUnary(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the textual representation of this operator.
	 *
	 * @return the textual representation of this operator
	 */
	public String getText() {
		return NAMES_ARRAY[value];
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

}
