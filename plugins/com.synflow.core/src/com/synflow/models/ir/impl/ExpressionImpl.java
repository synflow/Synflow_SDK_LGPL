/*
 * Copyright (c) 2013-2014, Synflow SAS
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package com.synflow.models.ir.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.synflow.models.ir.Expression;
import com.synflow.models.ir.IrPackage;
import com.synflow.models.ir.Type;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

/**
 * This class is an abstract implementation of {@link Expression}.
 * 
 * @author Matthieu Wipliez
 * @generated
 * 
 */
public abstract class ExpressionImpl extends EObjectImpl implements Expression {

	/**
	 * The cached value of the '{@link #getComputedType() <em>Computed Type</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getComputedType()
	 * @generated
	 * @ordered
	 */
	protected Type computedType;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Type getComputedType() {
		return computedType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetComputedType(Type newComputedType, NotificationChain msgs) {
		Type oldComputedType = computedType;
		computedType = newComputedType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IrPackage.EXPRESSION__COMPUTED_TYPE, oldComputedType, newComputedType);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setComputedType(Type newComputedType) {
		if (newComputedType != computedType) {
			NotificationChain msgs = null;
			if (computedType != null)
				msgs = ((InternalEObject) computedType).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.EXPRESSION__COMPUTED_TYPE, null, msgs);
			if (newComputedType != null)
				msgs = ((InternalEObject) newComputedType).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.EXPRESSION__COMPUTED_TYPE, null, msgs);
			msgs = basicSetComputedType(newComputedType, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.EXPRESSION__COMPUTED_TYPE, newComputedType, newComputedType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
			NotificationChain msgs) {
		switch (featureID) {
		case IrPackage.EXPRESSION__COMPUTED_TYPE:
			return basicSetComputedType(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IrPackage.EXPRESSION__COMPUTED_TYPE:
			return getComputedType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IrPackage.EXPRESSION__COMPUTED_TYPE:
			setComputedType((Type) newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.EXPRESSION__COMPUTED_TYPE:
			setComputedType((Type) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case IrPackage.EXPRESSION__COMPUTED_TYPE:
			return computedType != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public boolean isExprBinary() {
		return false;
	}

	@Override
	public boolean isExprBool() {
		return false;
	}

	@Override
	public boolean isExprFloat() {
		return false;
	}

	@Override
	public boolean isExprInt() {
		return false;
	}

	@Override
	public boolean isExprList() {
		return false;
	}

	@Override
	public boolean isExprResize() {
		return false;
	}

	@Override
	public boolean isExprString() {
		return false;
	}

	@Override
	public boolean isExprTypeConv() {
		return false;
	}

	@Override
	public boolean isExprUnary() {
		return false;
	}

	@Override
	public boolean isExprVar() {
		return false;
	}

}
