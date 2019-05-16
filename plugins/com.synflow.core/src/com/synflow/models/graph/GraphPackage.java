/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.graph;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta
 * objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see com.synflow.models.graph.GraphFactory
 * @model kind="package"
 * @generated
 */
public interface GraphPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "graph";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.synflow.com/2013/Graph";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "com.synflow.models.graph";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	GraphPackage eINSTANCE = com.synflow.models.graph.impl.GraphPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.synflow.models.graph.impl.GraphImpl <em>Graph</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.graph.impl.GraphImpl
	 * @see com.synflow.models.graph.impl.GraphPackageImpl#getGraph()
	 * @generated
	 */
	int GRAPH = 0;

	/**
	 * The meta object id for the '{@link com.synflow.models.graph.impl.VertexImpl <em>Vertex</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.graph.impl.VertexImpl
	 * @see com.synflow.models.graph.impl.GraphPackageImpl#getVertex()
	 * @generated
	 */
	int VERTEX = 1;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERTEX__NUMBER = 0;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERTEX__INCOMING = 1;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERTEX__OUTGOING = 2;

	/**
	 * The feature id for the '<em><b>Predecessors</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERTEX__PREDECESSORS = 3;

	/**
	 * The feature id for the '<em><b>Successors</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERTEX__SUCCESSORS = 4;

	/**
	 * The number of structural features of the '<em>Vertex</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERTEX_FEATURE_COUNT = 5;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GRAPH__NUMBER = VERTEX__NUMBER;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GRAPH__INCOMING = VERTEX__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GRAPH__OUTGOING = VERTEX__OUTGOING;

	/**
	 * The feature id for the '<em><b>Predecessors</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GRAPH__PREDECESSORS = VERTEX__PREDECESSORS;

	/**
	 * The feature id for the '<em><b>Successors</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GRAPH__SUCCESSORS = VERTEX__SUCCESSORS;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GRAPH__EDGES = VERTEX_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Vertices</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GRAPH__VERTICES = VERTEX_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Graph</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GRAPH_FEATURE_COUNT = VERTEX_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.synflow.models.graph.impl.EdgeImpl <em>Edge</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.graph.impl.EdgeImpl
	 * @see com.synflow.models.graph.impl.GraphPackageImpl#getEdge()
	 * @generated
	 */
	int EDGE = 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EDGE__LABEL = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EDGE__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EDGE__TARGET = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EDGE__VALUE = 3;

	/**
	 * The number of structural features of the '<em>Edge</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EDGE_FEATURE_COUNT = 4;

	/**
	 * Returns the meta object for class '{@link com.synflow.models.graph.Graph <em>Graph</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Graph</em>'.
	 * @see com.synflow.models.graph.Graph
	 * @generated
	 */
	EClass getGraph();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.graph.Graph#getVertices <em>Vertices</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Vertices</em>'.
	 * @see com.synflow.models.graph.Graph#getVertices()
	 * @see #getGraph()
	 * @generated
	 */
	EReference getGraph_Vertices();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.graph.Graph#getEdges <em>Edges</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Edges</em>'.
	 * @see com.synflow.models.graph.Graph#getEdges()
	 * @see #getGraph()
	 * @generated
	 */
	EReference getGraph_Edges();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.graph.Vertex <em>Vertex</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Vertex</em>'.
	 * @see com.synflow.models.graph.Vertex
	 * @generated
	 */
	EClass getVertex();

	/**
	 * Returns the meta object for the reference list '
	 * {@link com.synflow.models.graph.Vertex#getOutgoing <em>Outgoing</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Outgoing</em>'.
	 * @see com.synflow.models.graph.Vertex#getOutgoing()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Outgoing();

	/**
	 * Returns the meta object for the reference list '
	 * {@link com.synflow.models.graph.Vertex#getPredecessors <em>Predecessors</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Predecessors</em>'.
	 * @see com.synflow.models.graph.Vertex#getPredecessors()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Predecessors();

	/**
	 * Returns the meta object for the reference list '
	 * {@link com.synflow.models.graph.Vertex#getSuccessors <em>Successors</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Successors</em>'.
	 * @see com.synflow.models.graph.Vertex#getSuccessors()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Successors();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.graph.Vertex#getNumber
	 * <em>Number</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Number</em>'.
	 * @see com.synflow.models.graph.Vertex#getNumber()
	 * @see #getVertex()
	 * @generated
	 */
	EAttribute getVertex_Number();

	/**
	 * Returns the meta object for the reference list '
	 * {@link com.synflow.models.graph.Vertex#getIncoming <em>Incoming</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Incoming</em>'.
	 * @see com.synflow.models.graph.Vertex#getIncoming()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Incoming();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.graph.Edge <em>Edge</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Edge</em>'.
	 * @see com.synflow.models.graph.Edge
	 * @generated
	 */
	EClass getEdge();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.graph.Edge#getLabel
	 * <em>Label</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see com.synflow.models.graph.Edge#getLabel()
	 * @see #getEdge()
	 * @generated
	 */
	EAttribute getEdge_Label();

	/**
	 * Returns the meta object for the reference '{@link com.synflow.models.graph.Edge#getSource
	 * <em>Source</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see com.synflow.models.graph.Edge#getSource()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_Source();

	/**
	 * Returns the meta object for the reference '{@link com.synflow.models.graph.Edge#getTarget
	 * <em>Target</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see com.synflow.models.graph.Edge#getTarget()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_Target();

	/**
	 * Returns the meta object for the reference '{@link com.synflow.models.graph.Edge#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see com.synflow.models.graph.Edge#getValue()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_Value();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GraphFactory getGraphFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.synflow.models.graph.impl.GraphImpl
		 * <em>Graph</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.graph.impl.GraphImpl
		 * @see com.synflow.models.graph.impl.GraphPackageImpl#getGraph()
		 * @generated
		 */
		EClass GRAPH = eINSTANCE.getGraph();

		/**
		 * The meta object literal for the '<em><b>Vertices</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GRAPH__VERTICES = eINSTANCE.getGraph_Vertices();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GRAPH__EDGES = eINSTANCE.getGraph_Edges();

		/**
		 * The meta object literal for the '{@link com.synflow.models.graph.impl.VertexImpl
		 * <em>Vertex</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.graph.impl.VertexImpl
		 * @see com.synflow.models.graph.impl.GraphPackageImpl#getVertex()
		 * @generated
		 */
		EClass VERTEX = eINSTANCE.getVertex();

		/**
		 * The meta object literal for the '<em><b>Outgoing</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VERTEX__OUTGOING = eINSTANCE.getVertex_Outgoing();

		/**
		 * The meta object literal for the '<em><b>Predecessors</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VERTEX__PREDECESSORS = eINSTANCE.getVertex_Predecessors();

		/**
		 * The meta object literal for the '<em><b>Successors</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VERTEX__SUCCESSORS = eINSTANCE.getVertex_Successors();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VERTEX__NUMBER = eINSTANCE.getVertex_Number();

		/**
		 * The meta object literal for the '<em><b>Incoming</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VERTEX__INCOMING = eINSTANCE.getVertex_Incoming();

		/**
		 * The meta object literal for the '{@link com.synflow.models.graph.impl.EdgeImpl
		 * <em>Edge</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.graph.impl.EdgeImpl
		 * @see com.synflow.models.graph.impl.GraphPackageImpl#getEdge()
		 * @generated
		 */
		EClass EDGE = eINSTANCE.getEdge();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EDGE__LABEL = eINSTANCE.getEdge_Label();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EDGE__SOURCE = eINSTANCE.getEdge_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EDGE__TARGET = eINSTANCE.getEdge_Target();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EDGE__VALUE = eINSTANCE.getEdge_Value();

	}

} // GraphPackage
