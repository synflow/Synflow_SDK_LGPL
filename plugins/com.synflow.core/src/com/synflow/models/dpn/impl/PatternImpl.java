/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.dpn.impl;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import com.synflow.models.dpn.DpnPackage;
import com.synflow.models.dpn.Pattern;
import com.synflow.models.dpn.Port;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Pattern</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.dpn.impl.PatternImpl#getPorts <em>Ports</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PatternImpl extends EObjectImpl implements Pattern {

	/**
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> ports;

	/**
	 * @generated
	 */
	protected PatternImpl() {
		super();
	}

	@Override
	public void add(Pattern pattern) {
		getPorts().addAll(pattern.getPorts());
	}

	@Override
	public void add(Port port) {
		getPorts().add(port);
	}

	@Override
	public void clear() {
		getPorts().clear();
	}

	@Override
	public boolean contains(Port port) {
		return getPorts().contains(port);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DpnPackage.PATTERN__PORTS:
			return getPorts();
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
		case DpnPackage.PATTERN__PORTS:
			return ports != null && !ports.isEmpty();
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
		case DpnPackage.PATTERN__PORTS:
			getPorts().clear();
			getPorts().addAll((Collection<? extends Port>) newValue);
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
		return DpnPackage.Literals.PATTERN;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DpnPackage.PATTERN__PORTS:
			getPorts().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Port> getPorts() {
		if (ports == null) {
			ports = new EObjectResolvingEList<Port>(Port.class, this, DpnPackage.PATTERN__PORTS);
		}
		return ports;
	}

	@Override
	public boolean isEmpty() {
		return getPorts().isEmpty();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Iterator<Port> it = getPorts().iterator();
		builder.append('[');
		if (it.hasNext()) {
			Port port = it.next();
			builder.append(port.getName());
			while (it.hasNext()) {
				port = it.next();
				builder.append(", ");
				builder.append(port.getName());
			}
		}
		builder.append(']');

		return builder.toString();
	}

}
