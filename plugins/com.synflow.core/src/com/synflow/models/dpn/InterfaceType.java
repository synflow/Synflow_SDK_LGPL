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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '
 * <em><b>Interface Type</b></em>', and utility methods for working with them. <!-- end-user-doc -->
 *
 * @see com.synflow.models.dpn.DpnPackage#getInterfaceType()
 * @model
 * @generated
 */
public enum InterfaceType implements Enumerator {
	/**
	 * The '<em><b>BARE</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #BARE_VALUE
	 * @generated
	 * @ordered
	 */
	BARE(0, "BARE", "BARE"),

	/**
	 * The '<em><b>SYNC</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #SYNC_VALUE
	 * @generated
	 * @ordered
	 */
	SYNC(1, "SYNC", "SYNC"),

	/**
	 * The '<em><b>SYNC READY</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see #SYNC_READY_VALUE
	 * @generated
	 * @ordered
	 */
	SYNC_READY(2, "SYNC_READY", "SYNC_READY"),

	/**
	 * The '<em><b>SYNC ACK</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #SYNC_ACK_VALUE
	 * @generated
	 * @ordered
	 */
	SYNC_ACK(3, "SYNC_ACK", "SYNC_ACK");

	/**
	 * The '<em><b>BARE</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BARE</b></em>' literal object isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @see #BARE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BARE_VALUE = 0;

	/**
	 * The '<em><b>SYNC</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SYNC</b></em>' literal object isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @see #SYNC
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SYNC_VALUE = 1;

	/**
	 * The '<em><b>SYNC READY</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SYNC READY</b></em>' literal object isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @see #SYNC_READY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SYNC_READY_VALUE = 2;

	/**
	 * The '<em><b>SYNC ACK</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SYNC ACK</b></em>' literal object isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @see #SYNC_ACK
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SYNC_ACK_VALUE = 3;

	/**
	 * An array of all the '<em><b>Interface Type</b></em>' enumerators. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static final InterfaceType[] VALUES_ARRAY = new InterfaceType[] { BARE, SYNC,
			SYNC_READY, SYNC_ACK, };

	/**
	 * A public read-only list of all the '<em><b>Interface Type</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final List<InterfaceType> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Interface Type</b></em>' literal with the specified integer value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static InterfaceType get(int value) {
		switch (value) {
		case BARE_VALUE:
			return BARE;
		case SYNC_VALUE:
			return SYNC;
		case SYNC_READY_VALUE:
			return SYNC_READY;
		case SYNC_ACK_VALUE:
			return SYNC_ACK;
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Interface Type</b></em>' literal with the specified literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static InterfaceType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			InterfaceType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Interface Type</b></em>' literal with the specified name. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static InterfaceType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			InterfaceType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	private List<String> inputs;

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

	private List<String> signals;

	private List<String> outputs;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private final int value;

	/**
	 * Only this class can construct instances. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	private InterfaceType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
		this.inputs = createInputs();
		this.outputs = createOutputs();

		Builder<String> builder = ImmutableList.builder();
		builder.addAll(inputs);
		builder.addAll(outputs);
		signals = builder.build();
	}

	private List<String> createInputs() {
		switch (value) {
		case BARE_VALUE:
		case SYNC_VALUE:
			return ImmutableList.of();
		case SYNC_READY_VALUE: {
			return ImmutableList.of("ready");
		}
		case SYNC_ACK_VALUE: {
			return ImmutableList.of("ack");
		}
		}
		return null;
	}

	private List<String> createOutputs() {
		switch (value) {
		case BARE_VALUE:
			return ImmutableList.of();
		case SYNC_VALUE:
		case SYNC_READY_VALUE:
		case SYNC_ACK_VALUE: {
			return ImmutableList.of("valid");
		}
		}
		return null;
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
	 * Returns the input signals for this interface as a map whose key are signal names, and values
	 * are initial values.
	 *
	 * @param direction
	 * @return
	 */
	public List<String> getInputs(Direction direction) {
		return direction == Direction.OUTPUT ? inputs : outputs;
	}

	/**
	 * Returns the output signals for this interface as a map whose key are signal names, and values
	 * are initial values.
	 *
	 * @param direction
	 * @return
	 */
	public List<String> getOutputs(Direction direction) {
		return direction == Direction.OUTPUT ? outputs : inputs;
	}

	/**
	 * Returns all the signals for this interface as a map whose key are signal names, and values
	 * are initial values.
	 *
	 * @return
	 */
	public List<String> getSignals() {
		return signals;
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
	 * Returns true if this interface is or extends sync (including sync ack and sync ready).
	 *
	 * @return
	 */
	public boolean isSync() {
		return this == SYNC || isSyncAck() || isSyncReady();
	}

	public boolean isSyncAck() {
		return this == SYNC_ACK;
	}

	public boolean isSyncReady() {
		return this == SYNC_READY;
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

} // InterfaceType
