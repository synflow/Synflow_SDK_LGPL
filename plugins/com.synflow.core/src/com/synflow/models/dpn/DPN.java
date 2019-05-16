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

import org.eclipse.emf.common.util.EList;

import com.synflow.models.graph.Graph;
import com.synflow.models.graph.Vertex;

/**
 * <!-- begin-user-doc -->This class defines a hierarchical XDF network. It extends both entity and
 * graph.<!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.dpn.DPN#getGraph <em>Graph</em>}</li>
 * <li>{@link com.synflow.models.dpn.DPN#getInstances <em>Instances</em>}</li>
 * <li>{@link com.synflow.models.dpn.DPN#getVertex <em>Vertex</em>}</li>
 * </ul>
 *
 * @see com.synflow.models.dpn.DpnPackage#getDPN()
 * @model
 * @generated
 */
public interface DPN extends Entity {

	/**
	 * Adds the given instance to this network.
	 *
	 * @param instance
	 *            an instance
	 */
	void add(Instance instance);

	/**
	 * Returns the connection whose target is the given endpoint.
	 *
	 * @param target
	 *            target endpoint
	 * @return a connection
	 */
	Connection getConnection(Endpoint target);

	/**
	 * @model containment="true"
	 * @generated
	 */
	Graph getGraph();

	/**
	 * Returns the incoming endpoint of the given instance and input port.
	 *
	 * @param instance
	 *            an instance
	 * @param port
	 *            an input port of the instance
	 * @return an incoming endpoint (may be <code>null</code>)
	 */
	Endpoint getIncoming(Endpoint endpoint);

	/**
	 * Returns the incoming connection of the given instance.
	 *
	 * @param instance
	 *            an instance
	 * @return a list of connections (never <code>null</code>)
	 */
	List<Connection> getIncoming(Instance instance);

	/**
	 * Returns the incoming endpoint of the given output port.
	 *
	 * @param port
	 *            an output port
	 * @return an incoming endpoint (may be <code>null</code>)
	 */
	Endpoint getIncoming(Port port);

	/**
	 * Returns the instance with the given name.
	 *
	 * @param name
	 *            name of an instance
	 * @return an instance, or <code>null</code> if none found
	 */
	Instance getInstance(String name);

	/**
	 * Returns the value of the '<em><b>Instances</b></em>' reference list. The list contents are of
	 * type {@link com.synflow.models.dpn.Instance}. <!-- begin-user-doc --><!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Instances</em>' reference list.
	 * @see com.synflow.models.dpn.DpnPackage#getDPN_Instances()
	 * @model
	 * @generated
	 */
	EList<Instance> getInstances();

	/**
	 * Returns the number of incoming connections of the given input port of the given instance.
	 *
	 * @param instance
	 *            an instance of this dpn
	 * @param port
	 *            an input port of <code>instance</code>
	 * @return an int
	 */
	int getNumIncoming(Instance instance, Port port);

	/**
	 * Returns the number of incoming connections of the given output port.
	 *
	 * @param port
	 *            an output port of this dpn
	 * @return an int
	 */
	int getNumIncoming(Port port);

	/**
	 * Returns a list of endpoints outgoing from the given endpoint.
	 *
	 * @param endpoint
	 *            an endpoint
	 * @return a list of endpoints (never <code>null</code>)
	 */
	List<Endpoint> getOutgoing(Endpoint endpoint);

	/**
	 * Returns the outgoing connection of the given instance.
	 *
	 * @param instance
	 *            an instance
	 * @return a list of connections (never <code>null</code>)
	 */
	List<Connection> getOutgoing(Instance instance);

	/**
	 * Returns the list of endpoints outgoing of the given input port.
	 *
	 * @param port
	 *            an input port
	 * @return a list of connections (never <code>null</code>)
	 */
	List<Connection> getOutgoing(Port port);

	/**
	 * Returns the value of the '<em><b>Vertex</b></em>' reference. <!-- begin-user-doc -->Returns
	 * the vertex associated with this DPN itself.<!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Vertex</em>' reference.
	 * @see #setVertex(Vertex)
	 * @see com.synflow.models.dpn.DpnPackage#getDPN_Vertex()
	 * @model
	 * @generated
	 */
	Vertex getVertex();

	void init();

	/**
	 * Removes the given instance from this network.
	 *
	 * @param instance
	 *            an instance
	 */
	void remove(Instance instance);

	/**
	 * Sets the value of the '{@link com.synflow.models.dpn.DPN#getGraph <em>Graph</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Graph</em>' containment reference.
	 * @see #getGraph()
	 * @generated
	 */
	void setGraph(Graph value);

	/**
	 * Sets the value of the '{@link com.synflow.models.dpn.DPN#getVertex <em>Vertex</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Vertex</em>' reference.
	 * @see #getVertex()
	 * @generated
	 */
	void setVertex(Vertex value);

}
