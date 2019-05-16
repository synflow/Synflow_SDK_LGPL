/**
 * <copyright>
 * </copyright>
 *
 * $Id$
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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.DpnPackage;
import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.Port;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Actor</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.dpn.impl.ActorImpl#getActions <em>Actions</em>}</li>
 * <li>{@link com.synflow.models.dpn.impl.ActorImpl#getBufferedInputs <em>Buffered Inputs</em>}</li>
 * <li>{@link com.synflow.models.dpn.impl.ActorImpl#getFsm <em>Fsm</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ActorImpl extends EntityImpl implements Actor {
	/**
	 * The cached value of the '{@link #getActions() <em>Actions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
	protected EList<Action> actions;

	/**
	 * The cached value of the '{@link #getBufferedInputs() <em>Buffered Inputs</em>}' reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBufferedInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> bufferedInputs;

	/**
	 * The cached value of the '{@link #getFsm() <em>Fsm</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFsm()
	 * @generated
	 * @ordered
	 */
	protected FSM fsm;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ActorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetFsm(FSM newFsm, NotificationChain msgs) {
		FSM oldFsm = fsm;
		fsm = newFsm;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					DpnPackage.ACTOR__FSM, oldFsm, newFsm);
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
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DpnPackage.ACTOR__ACTIONS:
			return getActions();
		case DpnPackage.ACTOR__BUFFERED_INPUTS:
			return getBufferedInputs();
		case DpnPackage.ACTOR__FSM:
			return getFsm();
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
		case DpnPackage.ACTOR__ACTIONS:
			return ((InternalEList<?>) getActions()).basicRemove(otherEnd, msgs);
		case DpnPackage.ACTOR__FSM:
			return basicSetFsm(null, msgs);
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
		case DpnPackage.ACTOR__ACTIONS:
			return actions != null && !actions.isEmpty();
		case DpnPackage.ACTOR__BUFFERED_INPUTS:
			return bufferedInputs != null && !bufferedInputs.isEmpty();
		case DpnPackage.ACTOR__FSM:
			return fsm != null;
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
		case DpnPackage.ACTOR__ACTIONS:
			getActions().clear();
			getActions().addAll((Collection<? extends Action>) newValue);
			return;
		case DpnPackage.ACTOR__BUFFERED_INPUTS:
			getBufferedInputs().clear();
			getBufferedInputs().addAll((Collection<? extends Port>) newValue);
			return;
		case DpnPackage.ACTOR__FSM:
			setFsm((FSM) newValue);
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
		return DpnPackage.Literals.ACTOR;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DpnPackage.ACTOR__ACTIONS:
			getActions().clear();
			return;
		case DpnPackage.ACTOR__BUFFERED_INPUTS:
			getBufferedInputs().clear();
			return;
		case DpnPackage.ACTOR__FSM:
			setFsm((FSM) null);
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
	public EList<Action> getActions() {
		if (actions == null) {
			actions = new EObjectContainmentEList<Action>(Action.class, this,
					DpnPackage.ACTOR__ACTIONS);
		}
		return actions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Port> getBufferedInputs() {
		if (bufferedInputs == null) {
			bufferedInputs = new EObjectResolvingEList<Port>(Port.class, this,
					DpnPackage.ACTOR__BUFFERED_INPUTS);
		}
		return bufferedInputs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public FSM getFsm() {
		return fsm;
	}

	@Override
	public boolean hasFsm() {
		return fsm != null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setFsm(FSM newFsm) {
		if (newFsm != fsm) {
			NotificationChain msgs = null;
			if (fsm != null)
				msgs = ((InternalEObject) fsm).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - DpnPackage.ACTOR__FSM, null, msgs);
			if (newFsm != null)
				msgs = ((InternalEObject) newFsm).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - DpnPackage.ACTOR__FSM, null, msgs);
			msgs = basicSetFsm(newFsm, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DpnPackage.ACTOR__FSM, newFsm,
					newFsm));
	}

	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();
		return "actor " + getName();
	}

}
