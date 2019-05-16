/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.dpn.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.google.common.base.Joiner;
import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.DpnPackage;
import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;
import com.synflow.models.graph.Edge;
import com.synflow.models.graph.impl.GraphImpl;
import com.synflow.models.ir.util.IrUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>FSM</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.dpn.impl.FSMImpl#getInitialState <em>Initial State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FSMImpl extends GraphImpl implements FSM {

	/**
	 * The cached value of the '{@link #getInitialState() <em>Initial State</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInitialState()
	 * @generated
	 * @ordered
	 */
	protected State initialState;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	protected FSMImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public State basicGetInitialState() {
		return initialState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DpnPackage.FSM__INITIAL_STATE:
			if (resolve)
				return getInitialState();
			return basicGetInitialState();
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
		case DpnPackage.FSM__INITIAL_STATE:
			return initialState != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case DpnPackage.FSM__INITIAL_STATE:
			setInitialState((State) newValue);
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
		return DpnPackage.Literals.FSM;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DpnPackage.FSM__INITIAL_STATE:
			setInitialState((State) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public State getInitialState() {
		if (initialState != null && initialState.eIsProxy()) {
			InternalEObject oldInitialState = (InternalEObject) initialState;
			initialState = (State) eResolveProxy(oldInitialState);
			if (initialState != oldInitialState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							DpnPackage.FSM__INITIAL_STATE, oldInitialState, initialState));
			}
		}
		return initialState;
	}

	@Override
	@SuppressWarnings("unchecked")
	public EList<State> getStates() {
		return (EList<State>) (EList<?>) getVertices();
	}

	@Override
	public List<Action> getTargetActions(State source) {
		List<Action> actions = new ArrayList<Action>();
		for (Edge edge : source.getOutgoing()) {
			Transition transition = (Transition) edge;
			actions.add(transition.getAction());
		}
		return actions;
	}

	@Override
	@SuppressWarnings("unchecked")
	public EList<Transition> getTransitions() {
		return (EList<Transition>) (EList<?>) getEdges();
	}

	@Override
	public void remove(Edge edge) {
		Action action = ((Transition) edge).getAction();
		if (action != null) {
			IrUtil.delete(action);
		}

		super.remove(edge);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setInitialState(State newInitialState) {
		State oldInitialState = initialState;
		initialState = newInitialState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DpnPackage.FSM__INITIAL_STATE,
					oldInitialState, initialState));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("states: [");
		Joiner.on(", ").appendTo(builder, getStates());
		builder.append("]\n");

		for (Transition transition : getTransitions()) {
			builder.append(transition.toString());
			builder.append('\n');
		}
		return builder.toString();
	}

}
