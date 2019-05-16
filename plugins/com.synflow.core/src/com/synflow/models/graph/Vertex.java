/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.graph;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->This class defines a vertex. A vertex has incoming edges and outgoing
 * edges. It also has a list of attributes. The predecessor/successor information is actually
 * deduced from the incoming/outgoing edges.<!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.graph.Vertex#getNumber <em>Number</em>}</li>
 * <li>{@link com.synflow.models.graph.Vertex#getIncoming <em>Incoming</em>}</li>
 * <li>{@link com.synflow.models.graph.Vertex#getOutgoing <em>Outgoing</em>}</li>
 * <li>{@link com.synflow.models.graph.Vertex#getPredecessors <em>Predecessors</em>}</li>
 * <li>{@link com.synflow.models.graph.Vertex#getSuccessors <em>Successors</em>}</li>
 * </ul>
 *
 * @see com.synflow.models.graph.GraphPackage#getVertex()
 * @model
 * @generated
 */
public interface Vertex extends EObject {

	/**
	 * Returns the graph in which this vertex is contained, or <code>null</code> if it is not
	 * contained in a graph. This is equivalent to <code>(Graph) eContainer()</code>.
	 * 
	 * @return the graph that contains this vertex
	 */
	Graph getGraph();

	/**
	 * Returns the hierarchy of this vertex as a list of graphs.
	 * 
	 * @return the hierarchy of this vertex
	 */
	List<Graph> getHierarchy();

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list. The list contents are of
	 * type {@link com.synflow.models.graph.Edge}. It is bidirectional and its opposite is '
	 * {@link com.synflow.models.graph.Edge#getTarget <em>Target</em>}'. <!-- begin-user-doc --><!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see com.synflow.models.graph.GraphPackage#getVertex_Incoming()
	 * @see com.synflow.models.graph.Edge#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<Edge> getIncoming();

	/**
	 * Returns the value of the '<em><b>Number</b></em>' attribute. <!-- begin-user-doc -->Returns
	 * the number associated with this vertex. If the vertex has not been assigned a number, this
	 * returns 0. This field is filled by visit algorithms.<!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Number</em>' attribute.
	 * @see #setNumber(int)
	 * @see com.synflow.models.graph.GraphPackage#getVertex_Number()
	 * @model transient="true"
	 * @generated
	 */
	int getNumber();

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list. The list contents are of
	 * type {@link com.synflow.models.graph.Edge}. It is bidirectional and its opposite is '
	 * {@link com.synflow.models.graph.Edge#getSource <em>Source</em>}'. <!-- begin-user-doc --><!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see com.synflow.models.graph.GraphPackage#getVertex_Outgoing()
	 * @see com.synflow.models.graph.Edge#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<Edge> getOutgoing();

	/**
	 * Returns the value of the '<em><b>Predecessors</b></em>' reference list. The list contents are
	 * of type {@link com.synflow.models.graph.Vertex}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Predecessors</em>' reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Predecessors</em>' reference list.
	 * @see com.synflow.models.graph.GraphPackage#getVertex_Predecessors()
	 * @model resolveProxies="false" transient="true" changeable="false" derived="true"
	 * @generated
	 */
	EList<Vertex> getPredecessors();

	/**
	 * Returns the value of the '<em><b>Successors</b></em>' reference list. The list contents are
	 * of type {@link com.synflow.models.graph.Vertex}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Successors</em>' reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Successors</em>' reference list.
	 * @see com.synflow.models.graph.GraphPackage#getVertex_Successors()
	 * @model resolveProxies="false" transient="true" changeable="false" derived="true"
	 * @generated
	 */
	EList<Vertex> getSuccessors();

	/**
	 * Sets the value of the '{@link com.synflow.models.graph.Vertex#getNumber <em>Number</em>}'
	 * attribute. <!-- begin-user-doc --><!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Number</em>' attribute.
	 * @see #getNumber()
	 * @generated
	 */
	void setNumber(int value);

}
