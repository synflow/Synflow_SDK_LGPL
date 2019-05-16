/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.dpn.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.DpnPackage;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;
import com.synflow.models.graph.impl.EdgeImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Transition</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.dpn.impl.TransitionImpl#getBody <em>Body</em>}</li>
 * <li>{@link com.synflow.models.dpn.impl.TransitionImpl#getLines <em>Lines</em>}</li>
 * <li>{@link com.synflow.models.dpn.impl.TransitionImpl#getScheduler <em>Scheduler</em>}</li>
 * <li>{@link com.synflow.models.dpn.impl.TransitionImpl#getAction <em>Action</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TransitionImpl extends EdgeImpl implements Transition {

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> body;

	/**
	 * The cached value of the '{@link #getLines() <em>Lines</em>}' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLines()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> lines;

	private Map<String, Object> properties;

	/**
	 * The cached value of the '{@link #getScheduler() <em>Scheduler</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getScheduler()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> scheduler;

	/**
	 * The cached value of the '{@link #getAction() <em>Action</em>}' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getAction()
	 * @generated
	 * @ordered
	 */
	protected Action action;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Action basicGetAction() {
		return action;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DpnPackage.TRANSITION__BODY:
			return getBody();
		case DpnPackage.TRANSITION__LINES:
			return getLines();
		case DpnPackage.TRANSITION__SCHEDULER:
			return getScheduler();
		case DpnPackage.TRANSITION__ACTION:
			if (resolve)
				return getAction();
			return basicGetAction();
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
		case DpnPackage.TRANSITION__BODY:
			return body != null && !body.isEmpty();
		case DpnPackage.TRANSITION__LINES:
			return lines != null && !lines.isEmpty();
		case DpnPackage.TRANSITION__SCHEDULER:
			return scheduler != null && !scheduler.isEmpty();
		case DpnPackage.TRANSITION__ACTION:
			return action != null;
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
		case DpnPackage.TRANSITION__BODY:
			getBody().clear();
			getBody().addAll((Collection<? extends EObject>) newValue);
			return;
		case DpnPackage.TRANSITION__LINES:
			getLines().clear();
			getLines().addAll((Collection<? extends Integer>) newValue);
			return;
		case DpnPackage.TRANSITION__SCHEDULER:
			getScheduler().clear();
			getScheduler().addAll((Collection<? extends EObject>) newValue);
			return;
		case DpnPackage.TRANSITION__ACTION:
			setAction((Action) newValue);
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
		return DpnPackage.Literals.TRANSITION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DpnPackage.TRANSITION__BODY:
			getBody().clear();
			return;
		case DpnPackage.TRANSITION__LINES:
			getLines().clear();
			return;
		case DpnPackage.TRANSITION__SCHEDULER:
			getScheduler().clear();
			return;
		case DpnPackage.TRANSITION__ACTION:
			setAction((Action) null);
			return;
		}
		super.eUnset(featureID);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		if (properties == null) {
			properties = new HashMap<>();
		}
		return (T) properties.get(key);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Action getAction() {
		if (action != null && action.eIsProxy()) {
			InternalEObject oldAction = (InternalEObject) action;
			action = (Action) eResolveProxy(oldAction);
			if (action != oldAction) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							DpnPackage.TRANSITION__ACTION, oldAction, action));
			}
		}
		return action;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<EObject> getBody() {
		if (body == null) {
			body = new EObjectResolvingEList<EObject>(EObject.class, this,
					DpnPackage.TRANSITION__BODY);
		}
		return body;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Integer> getLines() {
		if (lines == null) {
			lines = new EDataTypeUniqueEList<Integer>(Integer.class, this,
					DpnPackage.TRANSITION__LINES);
		}
		return lines;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<EObject> getScheduler() {
		if (scheduler == null) {
			scheduler = new EObjectResolvingEList<EObject>(EObject.class, this,
					DpnPackage.TRANSITION__SCHEDULER);
		}
		return scheduler;
	}

	@Override
	public State getSource() {
		return (State) super.getSource();
	}

	@Override
	public State getTarget() {
		return (State) super.getTarget();
	}

	@Override
	public <T> void put(String key, T value) {
		if (properties == null) {
			properties = new HashMap<>();
		}
		properties.put(key, value);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setAction(Action newAction) {
		Action oldAction = action;
		action = newAction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DpnPackage.TRANSITION__ACTION,
					oldAction, action));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (getSource() != null) {
			builder.append(getSource());
		}
		builder.append(" (");
		if (getAction() != null) {
			builder.append("action");
		}
		builder.append(") --> ");
		if (getTarget() != null) {
			builder.append(getTarget());
		}
		return builder.toString();
	}

}
