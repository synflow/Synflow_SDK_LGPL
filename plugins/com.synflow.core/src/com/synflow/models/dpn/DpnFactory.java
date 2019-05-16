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

import java.util.List;

import org.eclipse.emf.ecore.EFactory;

import com.google.gson.JsonObject;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.Var;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each
 * non-abstract class of the model. <!-- end-user-doc -->
 *
 * @see com.synflow.models.dpn.DpnPackage
 * @generated
 */
public interface DpnFactory extends EFactory {

	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	DpnFactory eINSTANCE = com.synflow.models.dpn.impl.DpnFactoryImpl.init();

	/**
	 * Adds source's input/output patterns to target's input/output patterns. Target is the first
	 * argument, as in <code>target += source</code>
	 *
	 * @param target
	 * @param source
	 */
	void addPatterns(Action target, Action source);

	/**
	 * Collects all entities referenced by this <code>entity</code>, as well as the entity itself.
	 * Begins with all units.
	 *
	 * @param entity
	 * @return
	 */
	Iterable<Entity> collectEntities(Entity entity);

	/**
	 * Creates a copy of the given action.
	 *
	 * @param action
	 *            an action
	 * @return a new action with the same patterns
	 */
	Action copy(Action action);

	/**
	 * Creates a copy of the given JSON properties. Implemented with serialization+deserialization.
	 *
	 * @param properties
	 *            properties of an entity/instance in JSON format
	 * @return a fresh copy of the given properties
	 */
	JsonObject copy(JsonObject properties);

	/**
	 * Returns a new object of class '<em>Action</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return a new object of class '<em>Action</em>'.
	 * @generated
	 */
	Action createAction();

	/**
	 * Creates a new action with empty patterns.
	 */
	Action createActionEmpty();

	/**
	 * Creates a new action with empty patterns, and empty scheduler and body procedures that do
	 * nothing (nop).
	 */
	Action createActionNop();

	/**
	 * Returns a new object of class '<em>Actor</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Actor</em>'.
	 * @generated
	 */
	Actor createActor();

	/**
	 * Returns a new object of class '<em>Argument</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return a new object of class '<em>Argument</em>'.
	 * @generated
	 */
	Argument createArgument();

	Argument createArgument(Var variable, Expression value);

	/**
	 * Returns a new object of class '<em>Connection</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return a new object of class '<em>Connection</em>'.
	 * @generated
	 */
	Connection createConnection();

	/**
	 * Creates a new connection with the given endpoints.
	 *
	 * @param source
	 *            source endpoint
	 * @param target
	 *            target endpoint
	 * @return a new connection
	 */
	Connection createConnection(Endpoint source, Endpoint target);

	/**
	 * Returns a new object of class '<em>DPN</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>DPN</em>'.
	 * @generated
	 */
	DPN createDPN();

	/**
	 * Returns a new object of class '<em>Entity</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return a new object of class '<em>Entity</em>'.
	 * @generated
	 */
	Entity createEntity();

	/**
	 * Returns a new object of class '<em>FSM</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>FSM</em>'.
	 * @generated
	 */
	FSM createFSM();

	/**
	 * Returns a new object of class '<em>Goto</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Goto</em>'.
	 * @generated
	 */
	Goto createGoto();

	/**
	 * Returns a new object of class '<em>Instance</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return a new object of class '<em>Instance</em>'.
	 * @generated
	 */
	Instance createInstance();

	Instance createInstance(String name);

	Instance createInstance(String name, Entity entity);

	/**
	 * Returns a new object of class '<em>Pattern</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return a new object of class '<em>Pattern</em>'.
	 * @generated
	 */
	Pattern createPattern();

	/**
	 * Returns a new object of class '<em>Port</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Port</em>'.
	 * @generated
	 */
	Port createPort();

	/**
	 * Creates a new port. Type is copied if needed.
	 *
	 * @param type
	 * @param name
	 * @param iface
	 * @return
	 */
	Port createPort(Type type, String name, InterfaceType iface, List<Port> ports);

	/**
	 * Returns a new object of class '<em>State</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>State</em>'.
	 * @generated
	 */
	State createState();

	/**
	 * Creates a new state with the given name.
	 *
	 * @param name
	 *            name
	 * @return a state
	 */
	State createState(String name);

	/**
	 * Returns a new object of class '<em>Transition</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return a new object of class '<em>Transition</em>'.
	 * @generated
	 */
	Transition createTransition();

	/**
	 * Creates a new transition from <code>source</code> to <code>target</code>.
	 *
	 * @param source
	 *            source state
	 * @param target
	 *            target state
	 * @return a new transition
	 */
	Transition createTransition(State source, State target);

	/**
	 * Returns a new object of class '<em>Unit</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Unit</em>'.
	 * @generated
	 */
	Unit createUnit();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	DpnPackage getDpnPackage();

	/**
	 * Returns the list of ports that are ready.
	 *
	 * @param ports
	 * @return
	 */
	Iterable<Port> getReadyPorts(List<Port> ports);

} // DpnFactory
