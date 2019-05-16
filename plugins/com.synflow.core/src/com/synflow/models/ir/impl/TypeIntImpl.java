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
 
package com.synflow.models.ir.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import com.synflow.models.ir.IrPackage;
import com.synflow.models.ir.TypeInt;
import com.synflow.models.ir.util.TypePrinter;
import java.lang.Integer;

/**
 * This class defines an integer type.
 *
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 *
 */
public class TypeIntImpl extends TypeImpl implements TypeInt {

	/**
	 * The default value of the '{@link #isSigned() <em>Signed</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #isSigned()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SIGNED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSigned() <em>Signed</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #isSigned()
	 * @generated
	 * @ordered
	 */
	protected boolean signed = SIGNED_EDEFAULT;

	/**
	 * The default value of the '{@link #getSize() <em>Size</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected static final int SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSize() <em>Size</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected int size = SIZE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected TypeIntImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IrPackage.TYPE_INT__SIGNED:
			return isSigned();
		case IrPackage.TYPE_INT__SIZE:
			return getSize();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case IrPackage.TYPE_INT__SIGNED:
			return signed != SIGNED_EDEFAULT;
		case IrPackage.TYPE_INT__SIZE:
			return size != SIZE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TypeInt) {
			return size == ((TypeInt) obj).getSize();
		} else {
			return false;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IrPackage.TYPE_INT__SIGNED:
			setSigned((Boolean) newValue);
			return;
		case IrPackage.TYPE_INT__SIZE:
			setSize((Integer) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.TYPE_INT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.TYPE_INT__SIGNED:
			setSigned(SIGNED_EDEFAULT);
			return;
		case IrPackage.TYPE_INT__SIZE:
			setSize(SIZE_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * Returns the size of this integer type.
	 *
	 * @return the size of this integer type
	 * @generated
	 */
	public int getSize() {
		return size;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void setSize(int newSize) {
		int oldSize = size;
		size = newSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.TYPE_INT__SIZE, oldSize,
					size));
	}

	@Override
	public boolean isInt() {
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean isSigned() {
		return signed;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void setSigned(boolean newSigned) {
		boolean oldSigned = signed;
		signed = newSigned;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.TYPE_INT__SIGNED,
					oldSigned, signed));
	}

	@Override
	public String toString() {
		return new TypePrinter().toString(this);
	}

}
