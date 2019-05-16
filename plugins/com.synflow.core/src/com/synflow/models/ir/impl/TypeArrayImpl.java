/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.ir.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import com.synflow.models.ir.IrPackage;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeArray;
import com.synflow.models.ir.util.TypePrinter;
import java.lang.Integer;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Var</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.ir.impl.TypeArrayImpl#getDimensions <em>Dimensions</em>}</li>
 * <li>{@link com.synflow.models.ir.impl.TypeArrayImpl#getElementType <em>Element Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeArrayImpl extends TypeImpl implements TypeArray {

	/**
	 * The cached value of the '{@link #getDimensions() <em>Dimensions</em>}' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDimensions()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> dimensions;

	/**
	 * The cached value of the '{@link #getElementType() <em>Element Type</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected Type elementType;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TypeArrayImpl() {
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
		case IrPackage.TYPE_ARRAY__DIMENSIONS:
			return getDimensions();
		case IrPackage.TYPE_ARRAY__ELEMENT_TYPE:
			return getElementType();
		}
		return super.eGet(featureID, resolve, coreType);
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
		case IrPackage.TYPE_ARRAY__ELEMENT_TYPE:
			return basicSetElementType(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case IrPackage.TYPE_ARRAY__DIMENSIONS:
			return dimensions != null && !dimensions.isEmpty();
		case IrPackage.TYPE_ARRAY__ELEMENT_TYPE:
			return elementType != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IrPackage.TYPE_ARRAY__DIMENSIONS:
			getDimensions().clear();
			getDimensions().addAll((Collection<? extends Integer>) newValue);
			return;
		case IrPackage.TYPE_ARRAY__ELEMENT_TYPE:
			setElementType((Type) newValue);
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
		return IrPackage.Literals.TYPE_ARRAY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.TYPE_ARRAY__DIMENSIONS:
			getDimensions().clear();
			return;
		case IrPackage.TYPE_ARRAY__ELEMENT_TYPE:
			setElementType((Type) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Integer> getDimensions() {
		if (dimensions == null) {
			dimensions = new EDataTypeEList<Integer>(Integer.class, this,
					IrPackage.TYPE_ARRAY__DIMENSIONS);
		}
		return dimensions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Type getElementType() {
		return elementType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetElementType(Type newElementType, NotificationChain msgs) {
		Type oldElementType = elementType;
		elementType = newElementType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IrPackage.TYPE_ARRAY__ELEMENT_TYPE, oldElementType, newElementType);
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
	public void setElementType(Type newElementType) {
		if (newElementType != elementType) {
			NotificationChain msgs = null;
			if (elementType != null)
				msgs = ((InternalEObject) elementType).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.TYPE_ARRAY__ELEMENT_TYPE, null, msgs);
			if (newElementType != null)
				msgs = ((InternalEObject) newElementType).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.TYPE_ARRAY__ELEMENT_TYPE, null, msgs);
			msgs = basicSetElementType(newElementType, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.TYPE_ARRAY__ELEMENT_TYPE, newElementType, newElementType));
	}

	@Override
	public boolean isArray() {
		return true;
	}

	@Override
	public String toString() {
		return new TypePrinter().toString(this);
	}

}
