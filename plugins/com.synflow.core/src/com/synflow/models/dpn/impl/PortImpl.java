/**
 */
package com.synflow.models.dpn.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.synflow.models.dpn.Direction;
import com.synflow.models.dpn.DpnPackage;
import com.synflow.models.dpn.InterfaceType;
import com.synflow.models.dpn.Port;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.impl.VarImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Port</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.dpn.impl.PortImpl#getAdditionalInputs <em>Additional Inputs</em>}
 * </li>
 * <li>{@link com.synflow.models.dpn.impl.PortImpl#getAdditionalOutputs <em>Additional Outputs</em>}
 * </li>
 * <li>{@link com.synflow.models.dpn.impl.PortImpl#getInterface <em>Interface</em>}</li>
 * <li>{@link com.synflow.models.dpn.impl.PortImpl#isSynchronous <em>Synchronous</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PortImpl extends VarImpl implements Port {

	/**
	 * The cached value of the '{@link #getAdditionalInputs() <em>Additional Inputs</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAdditionalInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Var> additionalInputs;

	/**
	 * The cached value of the '{@link #getAdditionalOutputs() <em>Additional Outputs</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAdditionalOutputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Var> additionalOutputs;

	/**
	 * The default value of the '{@link #getInterface() <em>Interface</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInterface()
	 * @generated
	 * @ordered
	 */
	protected static final InterfaceType INTERFACE_EDEFAULT = InterfaceType.BARE;

	/**
	 * The cached value of the '{@link #getInterface() <em>Interface</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInterface()
	 * @generated
	 * @ordered
	 */
	protected InterfaceType interface_ = INTERFACE_EDEFAULT;

	/**
	 * The default value of the '{@link #isSynchronous() <em>Synchronous</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSynchronous()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SYNCHRONOUS_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isSynchronous() <em>Synchronous</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSynchronous()
	 * @generated
	 * @ordered
	 */
	protected boolean synchronous = SYNCHRONOUS_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected PortImpl() {
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
		case DpnPackage.PORT__ADDITIONAL_INPUTS:
			return getAdditionalInputs();
		case DpnPackage.PORT__ADDITIONAL_OUTPUTS:
			return getAdditionalOutputs();
		case DpnPackage.PORT__INTERFACE:
			return getInterface();
		case DpnPackage.PORT__SYNCHRONOUS:
			return isSynchronous();
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
		case DpnPackage.PORT__ADDITIONAL_INPUTS:
			return ((InternalEList<?>) getAdditionalInputs()).basicRemove(otherEnd, msgs);
		case DpnPackage.PORT__ADDITIONAL_OUTPUTS:
			return ((InternalEList<?>) getAdditionalOutputs()).basicRemove(otherEnd, msgs);
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
		case DpnPackage.PORT__ADDITIONAL_INPUTS:
			return additionalInputs != null && !additionalInputs.isEmpty();
		case DpnPackage.PORT__ADDITIONAL_OUTPUTS:
			return additionalOutputs != null && !additionalOutputs.isEmpty();
		case DpnPackage.PORT__INTERFACE:
			return interface_ != INTERFACE_EDEFAULT;
		case DpnPackage.PORT__SYNCHRONOUS:
			return synchronous != SYNCHRONOUS_EDEFAULT;
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
		case DpnPackage.PORT__ADDITIONAL_INPUTS:
			getAdditionalInputs().clear();
			getAdditionalInputs().addAll((Collection<? extends Var>) newValue);
			return;
		case DpnPackage.PORT__ADDITIONAL_OUTPUTS:
			getAdditionalOutputs().clear();
			getAdditionalOutputs().addAll((Collection<? extends Var>) newValue);
			return;
		case DpnPackage.PORT__INTERFACE:
			setInterface((InterfaceType) newValue);
			return;
		case DpnPackage.PORT__SYNCHRONOUS:
			setSynchronous((Boolean) newValue);
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
		return DpnPackage.Literals.PORT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DpnPackage.PORT__ADDITIONAL_INPUTS:
			getAdditionalInputs().clear();
			return;
		case DpnPackage.PORT__ADDITIONAL_OUTPUTS:
			getAdditionalOutputs().clear();
			return;
		case DpnPackage.PORT__INTERFACE:
			setInterface(INTERFACE_EDEFAULT);
			return;
		case DpnPackage.PORT__SYNCHRONOUS:
			setSynchronous(SYNCHRONOUS_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Var> getAdditionalInputs() {
		if (additionalInputs == null) {
			additionalInputs = new EObjectContainmentEList<Var>(Var.class, this,
					DpnPackage.PORT__ADDITIONAL_INPUTS);
		}
		return additionalInputs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Var> getAdditionalOutputs() {
		if (additionalOutputs == null) {
			additionalOutputs = new EObjectContainmentEList<Var>(Var.class, this,
					DpnPackage.PORT__ADDITIONAL_OUTPUTS);
		}
		return additionalOutputs;
	}

	@Override
	public Direction getDirection() {
		// we take advantage of the fact that a port can only be contained in an entity
		int featureID = EOPPOSITE_FEATURE_BASE - eContainerFeatureID;
		if (featureID == DpnPackage.ENTITY__INPUTS) {
			return Direction.INPUT;
		} else if (featureID == DpnPackage.ENTITY__OUTPUTS) {
			return Direction.OUTPUT;
		} else {
			throw new IllegalArgumentException("port not contained in an entity");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InterfaceType getInterface() {
		return interface_;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSynchronous() {
		return synchronous;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setInterface(InterfaceType newInterface) {
		InterfaceType oldInterface = interface_;
		interface_ = newInterface == null ? INTERFACE_EDEFAULT : newInterface;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DpnPackage.PORT__INTERFACE,
					oldInterface, interface_));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSynchronous(boolean newSynchronous) {
		boolean oldSynchronous = synchronous;
		synchronous = newSynchronous;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DpnPackage.PORT__SYNCHRONOUS,
					oldSynchronous, synchronous));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (interface: ");
		result.append(interface_);
		result.append(", synchronous: ");
		result.append(synchronous);
		result.append(')');
		return result.toString();
	}

} // PortImpl
