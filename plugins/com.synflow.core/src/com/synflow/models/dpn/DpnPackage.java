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

import com.synflow.models.graph.GraphPackage;
import com.synflow.models.ir.IrPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
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
 * @see com.synflow.models.dpn.DpnFactory
 * @model kind="package"
 * @generated
 */
public interface DpnPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "dpn";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.synflow.com/2013/Dpn";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "com.synflow.models.dpn";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	DpnPackage eINSTANCE = com.synflow.models.dpn.impl.DpnPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.impl.EntityImpl <em>Entity</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.impl.EntityImpl
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getEntity()
	 * @generated
	 */
	int ENTITY = 5;

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.impl.UnitImpl <em>Unit</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.impl.UnitImpl
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getUnit()
	 * @generated
	 */
	int UNIT = 12;

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.impl.InstanceImpl <em>Instance</em>
	 * }' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.impl.InstanceImpl
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getInstance()
	 * @generated
	 */
	int INSTANCE = 7;

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.impl.ActorImpl <em>Actor</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.impl.ActorImpl
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getActor()
	 * @generated
	 */
	int ACTOR = 1;

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.impl.ActionImpl <em>Action</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.impl.ActionImpl
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getAction()
	 * @generated
	 */
	int ACTION = 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION__BODY = 0;

	/**
	 * The feature id for the '<em><b>Combinational</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION__COMBINATIONAL = 1;

	/**
	 * The feature id for the '<em><b>Input Pattern</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION__INPUT_PATTERN = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION__NAME = 3;

	/**
	 * The feature id for the '<em><b>Output Pattern</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION__OUTPUT_PATTERN = 4;

	/**
	 * The feature id for the '<em><b>Peek Pattern</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION__PEEK_PATTERN = 5;

	/**
	 * The feature id for the '<em><b>Scheduler</b></em>' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION__SCHEDULER = 6;

	/**
	 * The number of structural features of the '<em>Action</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION_FEATURE_COUNT = 7;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENTITY__FILE_NAME = 0;

	/**
	 * The feature id for the '<em><b>Inputs</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENTITY__INPUTS = 1;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENTITY__LINE_NUMBER = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENTITY__NAME = 3;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENTITY__OUTPUTS = 4;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENTITY__PROPERTIES = 5;

	/**
	 * The feature id for the '<em><b>Procedures</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENTITY__PROCEDURES = 6;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENTITY__VARIABLES = 7;

	/**
	 * The number of structural features of the '<em>Entity</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENTITY_FEATURE_COUNT = 8;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTOR__FILE_NAME = ENTITY__FILE_NAME;

	/**
	 * The feature id for the '<em><b>Inputs</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTOR__INPUTS = ENTITY__INPUTS;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTOR__LINE_NUMBER = ENTITY__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTOR__NAME = ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTOR__OUTPUTS = ENTITY__OUTPUTS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTOR__PROPERTIES = ENTITY__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Procedures</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTOR__PROCEDURES = ENTITY__PROCEDURES;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTOR__VARIABLES = ENTITY__VARIABLES;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTOR__ACTIONS = ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Buffered Inputs</b></em>' reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTOR__BUFFERED_INPUTS = ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Fsm</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTOR__FSM = ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Actor</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTOR_FEATURE_COUNT = ENTITY_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.impl.FSMImpl <em>FSM</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.impl.FSMImpl
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getFSM()
	 * @generated
	 */
	int FSM = 6;

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.impl.PatternImpl <em>Pattern</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.impl.PatternImpl
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getPattern()
	 * @generated
	 */
	int PATTERN = 8;

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.impl.StateImpl <em>State</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.impl.StateImpl
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getState()
	 * @generated
	 */
	int STATE = 10;

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.impl.TransitionImpl
	 * <em>Transition</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.impl.TransitionImpl
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 11;

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.impl.ArgumentImpl <em>Argument</em>
	 * }' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.impl.ArgumentImpl
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getArgument()
	 * @generated
	 */
	int ARGUMENT = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ARGUMENT__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ARGUMENT__VARIABLE = 1;

	/**
	 * The number of structural features of the '<em>Argument</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.impl.PortImpl <em>Port</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.impl.PortImpl
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getPort()
	 * @generated
	 */
	int PORT = 9;

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.impl.ConnectionImpl
	 * <em>Connection</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.impl.ConnectionImpl
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getConnection()
	 * @generated
	 */
	int CONNECTION = 3;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION__LABEL = GraphPackage.EDGE__LABEL;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION__SOURCE = GraphPackage.EDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION__TARGET = GraphPackage.EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION__VALUE = GraphPackage.EDGE__VALUE;

	/**
	 * The feature id for the '<em><b>Source Port</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION__SOURCE_PORT = GraphPackage.EDGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target Port</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION__TARGET_PORT = GraphPackage.EDGE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Connection</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_FEATURE_COUNT = GraphPackage.EDGE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.impl.DPNImpl <em>DPN</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.impl.DPNImpl
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getDPN()
	 * @generated
	 */
	int DPN = 4;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DPN__FILE_NAME = ENTITY__FILE_NAME;

	/**
	 * The feature id for the '<em><b>Inputs</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DPN__INPUTS = ENTITY__INPUTS;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DPN__LINE_NUMBER = ENTITY__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DPN__NAME = ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DPN__OUTPUTS = ENTITY__OUTPUTS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DPN__PROPERTIES = ENTITY__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Procedures</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DPN__PROCEDURES = ENTITY__PROCEDURES;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DPN__VARIABLES = ENTITY__VARIABLES;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DPN__GRAPH = ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DPN__INSTANCES = ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Vertex</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DPN__VERTEX = ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>DPN</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DPN_FEATURE_COUNT = ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FSM__NUMBER = GraphPackage.GRAPH__NUMBER;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FSM__INCOMING = GraphPackage.GRAPH__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FSM__OUTGOING = GraphPackage.GRAPH__OUTGOING;

	/**
	 * The feature id for the '<em><b>Predecessors</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FSM__PREDECESSORS = GraphPackage.GRAPH__PREDECESSORS;

	/**
	 * The feature id for the '<em><b>Successors</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FSM__SUCCESSORS = GraphPackage.GRAPH__SUCCESSORS;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FSM__EDGES = GraphPackage.GRAPH__EDGES;

	/**
	 * The feature id for the '<em><b>Vertices</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FSM__VERTICES = GraphPackage.GRAPH__VERTICES;

	/**
	 * The feature id for the '<em><b>Initial State</b></em>' reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FSM__INITIAL_STATE = GraphPackage.GRAPH_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>FSM</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FSM_FEATURE_COUNT = GraphPackage.GRAPH_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INSTANCE__NUMBER = GraphPackage.VERTEX__NUMBER;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INSTANCE__INCOMING = GraphPackage.VERTEX__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INSTANCE__OUTGOING = GraphPackage.VERTEX__OUTGOING;

	/**
	 * The feature id for the '<em><b>Predecessors</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INSTANCE__PREDECESSORS = GraphPackage.VERTEX__PREDECESSORS;

	/**
	 * The feature id for the '<em><b>Successors</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INSTANCE__SUCCESSORS = GraphPackage.VERTEX__SUCCESSORS;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INSTANCE__ARGUMENTS = GraphPackage.VERTEX_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INSTANCE__ENTITY = GraphPackage.VERTEX_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INSTANCE__NAME = GraphPackage.VERTEX_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INSTANCE__PROPERTIES = GraphPackage.VERTEX_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Instance</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INSTANCE_FEATURE_COUNT = GraphPackage.VERTEX_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PATTERN__PORTS = 0;

	/**
	 * The number of structural features of the '<em>Pattern</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PATTERN_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Assignable</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__ASSIGNABLE = IrPackage.VAR__ASSIGNABLE;

	/**
	 * The feature id for the '<em><b>Defs</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__DEFS = IrPackage.VAR__DEFS;

	/**
	 * The feature id for the '<em><b>Global</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__GLOBAL = IrPackage.VAR__GLOBAL;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__INDEX = IrPackage.VAR__INDEX;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__INITIAL_VALUE = IrPackage.VAR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__LINE_NUMBER = IrPackage.VAR__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Local</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__LOCAL = IrPackage.VAR__LOCAL;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__NAME = IrPackage.VAR__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__TYPE = IrPackage.VAR__TYPE;

	/**
	 * The feature id for the '<em><b>Uses</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__USES = IrPackage.VAR__USES;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__VALUE = IrPackage.VAR__VALUE;

	/**
	 * The feature id for the '<em><b>Additional Inputs</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__ADDITIONAL_INPUTS = IrPackage.VAR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Additional Outputs</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__ADDITIONAL_OUTPUTS = IrPackage.VAR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__INTERFACE = IrPackage.VAR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Synchronous</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT__SYNCHRONOUS = IrPackage.VAR_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Port</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = IrPackage.VAR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__NUMBER = GraphPackage.VERTEX__NUMBER;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__INCOMING = GraphPackage.VERTEX__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__OUTGOING = GraphPackage.VERTEX__OUTGOING;

	/**
	 * The feature id for the '<em><b>Predecessors</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__PREDECESSORS = GraphPackage.VERTEX__PREDECESSORS;

	/**
	 * The feature id for the '<em><b>Successors</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__SUCCESSORS = GraphPackage.VERTEX__SUCCESSORS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE__NAME = GraphPackage.VERTEX_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>State</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = GraphPackage.VERTEX_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__LABEL = GraphPackage.EDGE__LABEL;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__SOURCE = GraphPackage.EDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TARGET = GraphPackage.EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__VALUE = GraphPackage.EDGE__VALUE;

	/**
	 * The feature id for the '<em><b>Body</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__BODY = GraphPackage.EDGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Lines</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__LINES = GraphPackage.EDGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Scheduler</b></em>' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__SCHEDULER = GraphPackage.EDGE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Action</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION__ACTION = GraphPackage.EDGE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Transition</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = GraphPackage.EDGE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UNIT__FILE_NAME = ENTITY__FILE_NAME;

	/**
	 * The feature id for the '<em><b>Inputs</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UNIT__INPUTS = ENTITY__INPUTS;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UNIT__LINE_NUMBER = ENTITY__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UNIT__NAME = ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UNIT__OUTPUTS = ENTITY__OUTPUTS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UNIT__PROPERTIES = ENTITY__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Procedures</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UNIT__PROCEDURES = ENTITY__PROCEDURES;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UNIT__VARIABLES = ENTITY__VARIABLES;

	/**
	 * The number of structural features of the '<em>Unit</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UNIT_FEATURE_COUNT = ENTITY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.impl.GotoImpl <em>Goto</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.impl.GotoImpl
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getGoto()
	 * @generated
	 */
	int GOTO = 13;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GOTO__LINE_NUMBER = IrPackage.INSTRUCTION__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GOTO__TARGET = IrPackage.INSTRUCTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Goto</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GOTO_FEATURE_COUNT = IrPackage.INSTRUCTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.synflow.models.dpn.InterfaceType
	 * <em>Interface Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see com.synflow.models.dpn.InterfaceType
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getInterfaceType()
	 * @generated
	 */
	int INTERFACE_TYPE = 14;

	/**
	 * The meta object id for the '<em>Json Object</em>' data type. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see com.google.gson.JsonObject
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getJsonObject()
	 * @generated
	 */
	int JSON_OBJECT = 15;

	/**
	 * The meta object id for the '<em>Json Array</em>' data type. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see com.google.gson.JsonArray
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getJsonArray()
	 * @generated
	 */
	int JSON_ARRAY = 16;

	/**
	 * The meta object id for the '<em>Bi Map</em>' data type. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see com.google.common.collect.BiMap
	 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getBiMap()
	 * @generated
	 */
	int BI_MAP = 17;

	/**
	 * Returns the meta object for class '{@link com.synflow.models.dpn.Unit <em>Unit</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Unit</em>'.
	 * @see com.synflow.models.dpn.Unit
	 * @generated
	 */
	EClass getUnit();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.dpn.Goto <em>Goto</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Goto</em>'.
	 * @see com.synflow.models.dpn.Goto
	 * @generated
	 */
	EClass getGoto();

	/**
	 * Returns the meta object for the reference '{@link com.synflow.models.dpn.Goto#getTarget
	 * <em>Target</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see com.synflow.models.dpn.Goto#getTarget()
	 * @see #getGoto()
	 * @generated
	 */
	EReference getGoto_Target();

	/**
	 * Returns the meta object for enum '{@link com.synflow.models.dpn.InterfaceType
	 * <em>Interface Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Interface Type</em>'.
	 * @see com.synflow.models.dpn.InterfaceType
	 * @generated
	 */
	EEnum getInterfaceType();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.dpn.Instance <em>Instance</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Instance</em>'.
	 * @see com.synflow.models.dpn.Instance
	 * @generated
	 */
	EClass getInstance();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.dpn.Instance#getArguments <em>Arguments</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Arguments</em>'.
	 * @see com.synflow.models.dpn.Instance#getArguments()
	 * @see #getInstance()
	 * @generated
	 */
	EReference getInstance_Arguments();

	/**
	 * Returns the meta object for the reference '{@link com.synflow.models.dpn.Instance#getEntity
	 * <em>Entity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Entity</em>'.
	 * @see com.synflow.models.dpn.Instance#getEntity()
	 * @see #getInstance()
	 * @generated
	 */
	EReference getInstance_Entity();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.dpn.Instance#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.synflow.models.dpn.Instance#getName()
	 * @see #getInstance()
	 * @generated
	 */
	EAttribute getInstance_Name();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.synflow.models.dpn.Instance#getProperties <em>Properties</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Properties</em>'.
	 * @see com.synflow.models.dpn.Instance#getProperties()
	 * @see #getInstance()
	 * @generated
	 */
	EAttribute getInstance_Properties();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.dpn.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Entity</em>'.
	 * @see com.synflow.models.dpn.Entity
	 * @generated
	 */
	EClass getEntity();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.dpn.Entity#getInputs <em>Inputs</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Inputs</em>'.
	 * @see com.synflow.models.dpn.Entity#getInputs()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_Inputs();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.dpn.Entity#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.synflow.models.dpn.Entity#getName()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_Name();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.dpn.Entity#getOutputs <em>Outputs</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Outputs</em>'.
	 * @see com.synflow.models.dpn.Entity#getOutputs()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_Outputs();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.dpn.Entity#getProperties
	 * <em>Properties</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Properties</em>'.
	 * @see com.synflow.models.dpn.Entity#getProperties()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_Properties();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.dpn.Entity#getFileName
	 * <em>File Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>File Name</em>'.
	 * @see com.synflow.models.dpn.Entity#getFileName()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_FileName();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.dpn.Entity#getVariables <em>Variables</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see com.synflow.models.dpn.Entity#getVariables()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_Variables();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.synflow.models.dpn.Entity#getLineNumber <em>Line Number</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see com.synflow.models.dpn.Entity#getLineNumber()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_LineNumber();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.dpn.Entity#getProcedures <em>Procedures</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Procedures</em>'.
	 * @see com.synflow.models.dpn.Entity#getProcedures()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_Procedures();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.dpn.Actor <em>Actor</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Actor</em>'.
	 * @see com.synflow.models.dpn.Actor
	 * @generated
	 */
	EClass getActor();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.dpn.Actor#getActions <em>Actions</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see com.synflow.models.dpn.Actor#getActions()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_Actions();

	/**
	 * Returns the meta object for the reference list '
	 * {@link com.synflow.models.dpn.Actor#getBufferedInputs <em>Buffered Inputs</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Buffered Inputs</em>'.
	 * @see com.synflow.models.dpn.Actor#getBufferedInputs()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_BufferedInputs();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.dpn.Actor#getFsm <em>Fsm</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Fsm</em>'.
	 * @see com.synflow.models.dpn.Actor#getFsm()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_Fsm();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.dpn.Action <em>Action</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Action</em>'.
	 * @see com.synflow.models.dpn.Action
	 * @generated
	 */
	EClass getAction();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.dpn.Action#getBody <em>Body</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see com.synflow.models.dpn.Action#getBody()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_Body();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.dpn.Action#getCombinational <em>Combinational</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Combinational</em>'.
	 * @see com.synflow.models.dpn.Action#getCombinational()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_Combinational();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.dpn.Action#getInputPattern <em>Input Pattern</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Input Pattern</em>'.
	 * @see com.synflow.models.dpn.Action#getInputPattern()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_InputPattern();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.dpn.Action#getOutputPattern <em>Output Pattern</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Output Pattern</em>'.
	 * @see com.synflow.models.dpn.Action#getOutputPattern()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_OutputPattern();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.dpn.Action#getPeekPattern <em>Peek Pattern</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Peek Pattern</em>'.
	 * @see com.synflow.models.dpn.Action#getPeekPattern()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_PeekPattern();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.dpn.Action#getScheduler <em>Scheduler</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Scheduler</em>'.
	 * @see com.synflow.models.dpn.Action#getScheduler()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_Scheduler();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.dpn.Action#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.synflow.models.dpn.Action#getName()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Name();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.dpn.FSM <em>FSM</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>FSM</em>'.
	 * @see com.synflow.models.dpn.FSM
	 * @generated
	 */
	EClass getFSM();

	/**
	 * Returns the meta object for the reference '{@link com.synflow.models.dpn.FSM#getInitialState
	 * <em>Initial State</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Initial State</em>'.
	 * @see com.synflow.models.dpn.FSM#getInitialState()
	 * @see #getFSM()
	 * @generated
	 */
	EReference getFSM_InitialState();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.dpn.Pattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Pattern</em>'.
	 * @see com.synflow.models.dpn.Pattern
	 * @generated
	 */
	EClass getPattern();

	/**
	 * Returns the meta object for the reference list '
	 * {@link com.synflow.models.dpn.Pattern#getPorts <em>Ports</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Ports</em>'.
	 * @see com.synflow.models.dpn.Pattern#getPorts()
	 * @see #getPattern()
	 * @generated
	 */
	EReference getPattern_Ports();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.dpn.State <em>State</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>State</em>'.
	 * @see com.synflow.models.dpn.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.dpn.State#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.synflow.models.dpn.State#getName()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_Name();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.dpn.Transition
	 * <em>Transition</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see com.synflow.models.dpn.Transition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the reference list '
	 * {@link com.synflow.models.dpn.Transition#getBody <em>Body</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Body</em>'.
	 * @see com.synflow.models.dpn.Transition#getBody()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Body();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link com.synflow.models.dpn.Transition#getLines <em>Lines</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute list '<em>Lines</em>'.
	 * @see com.synflow.models.dpn.Transition#getLines()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Lines();

	/**
	 * Returns the meta object for the reference list '
	 * {@link com.synflow.models.dpn.Transition#getScheduler <em>Scheduler</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Scheduler</em>'.
	 * @see com.synflow.models.dpn.Transition#getScheduler()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Scheduler();

	/**
	 * Returns the meta object for the reference '{@link com.synflow.models.dpn.Transition#getAction
	 * <em>Action</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Action</em>'.
	 * @see com.synflow.models.dpn.Transition#getAction()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Action();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.dpn.Argument <em>Argument</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Argument</em>'.
	 * @see com.synflow.models.dpn.Argument
	 * @generated
	 */
	EClass getArgument();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.dpn.Argument#getValue <em>Value</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see com.synflow.models.dpn.Argument#getValue()
	 * @see #getArgument()
	 * @generated
	 */
	EReference getArgument_Value();

	/**
	 * Returns the meta object for the reference '{@link com.synflow.models.dpn.Argument#getVariable
	 * <em>Variable</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see com.synflow.models.dpn.Argument#getVariable()
	 * @see #getArgument()
	 * @generated
	 */
	EReference getArgument_Variable();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.dpn.Port <em>Port</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Port</em>'.
	 * @see com.synflow.models.dpn.Port
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.dpn.Port#getInterface
	 * <em>Interface</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Interface</em>'.
	 * @see com.synflow.models.dpn.Port#getInterface()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Interface();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.dpn.Port#getAdditionalInputs <em>Additional Inputs</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Additional Inputs</em>'.
	 * @see com.synflow.models.dpn.Port#getAdditionalInputs()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_AdditionalInputs();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.dpn.Port#getAdditionalOutputs <em>Additional Outputs</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Additional Outputs</em>'.
	 * @see com.synflow.models.dpn.Port#getAdditionalOutputs()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_AdditionalOutputs();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.dpn.Port#isSynchronous
	 * <em>Synchronous</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Synchronous</em>'.
	 * @see com.synflow.models.dpn.Port#isSynchronous()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Synchronous();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.dpn.Connection
	 * <em>Connection</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Connection</em>'.
	 * @see com.synflow.models.dpn.Connection
	 * @generated
	 */
	EClass getConnection();

	/**
	 * Returns the meta object for the reference '
	 * {@link com.synflow.models.dpn.Connection#getSourcePort <em>Source Port</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Source Port</em>'.
	 * @see com.synflow.models.dpn.Connection#getSourcePort()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_SourcePort();

	/**
	 * Returns the meta object for the reference '
	 * {@link com.synflow.models.dpn.Connection#getTargetPort <em>Target Port</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Target Port</em>'.
	 * @see com.synflow.models.dpn.Connection#getTargetPort()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_TargetPort();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.dpn.DPN <em>DPN</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>DPN</em>'.
	 * @see com.synflow.models.dpn.DPN
	 * @generated
	 */
	EClass getDPN();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.dpn.DPN#getGraph <em>Graph</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Graph</em>'.
	 * @see com.synflow.models.dpn.DPN#getGraph()
	 * @see #getDPN()
	 * @generated
	 */
	EReference getDPN_Graph();

	/**
	 * Returns the meta object for the reference list '
	 * {@link com.synflow.models.dpn.DPN#getInstances <em>Instances</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Instances</em>'.
	 * @see com.synflow.models.dpn.DPN#getInstances()
	 * @see #getDPN()
	 * @generated
	 */
	EReference getDPN_Instances();

	/**
	 * Returns the meta object for the reference '{@link com.synflow.models.dpn.DPN#getVertex
	 * <em>Vertex</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Vertex</em>'.
	 * @see com.synflow.models.dpn.DPN#getVertex()
	 * @see #getDPN()
	 * @generated
	 */
	EReference getDPN_Vertex();

	/**
	 * Returns the meta object for data type '{@link com.google.gson.JsonObject <em>Json Object</em>
	 * }'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for data type '<em>Json Object</em>'.
	 * @see com.google.gson.JsonObject
	 * @model instanceClass="com.google.gson.JsonObject"
	 * @generated
	 */
	EDataType getJsonObject();

	/**
	 * Returns the meta object for data type '{@link com.google.gson.JsonArray <em>Json Array</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for data type '<em>Json Array</em>'.
	 * @see com.google.gson.JsonArray
	 * @model instanceClass="com.google.gson.JsonArray"
	 * @generated
	 */
	EDataType getJsonArray();

	/**
	 * Returns the meta object for data type '{@link com.google.common.collect.BiMap <em>Bi Map</em>
	 * }'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for data type '<em>Bi Map</em>'.
	 * @see com.google.common.collect.BiMap
	 * @model instanceClass="com.google.common.collect.BiMap" typeParameters="T T1"
	 * @generated
	 */
	EDataType getBiMap();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DpnFactory getDpnFactory();

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
		 * The meta object literal for the '{@link com.synflow.models.dpn.impl.UnitImpl
		 * <em>Unit</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.impl.UnitImpl
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getUnit()
		 * @generated
		 */
		EClass UNIT = eINSTANCE.getUnit();

		/**
		 * The meta object literal for the '{@link com.synflow.models.dpn.impl.GotoImpl
		 * <em>Goto</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.impl.GotoImpl
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getGoto()
		 * @generated
		 */
		EClass GOTO = eINSTANCE.getGoto();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference GOTO__TARGET = eINSTANCE.getGoto_Target();

		/**
		 * The meta object literal for the '{@link com.synflow.models.dpn.InterfaceType
		 * <em>Interface Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.InterfaceType
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getInterfaceType()
		 * @generated
		 */
		EEnum INTERFACE_TYPE = eINSTANCE.getInterfaceType();

		/**
		 * The meta object literal for the '{@link com.synflow.models.dpn.impl.InstanceImpl
		 * <em>Instance</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.impl.InstanceImpl
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getInstance()
		 * @generated
		 */
		EClass INSTANCE = eINSTANCE.getInstance();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference INSTANCE__ARGUMENTS = eINSTANCE.getInstance_Arguments();

		/**
		 * The meta object literal for the '<em><b>Entity</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference INSTANCE__ENTITY = eINSTANCE.getInstance_Entity();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute INSTANCE__NAME = eINSTANCE.getInstance_Name();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute INSTANCE__PROPERTIES = eINSTANCE.getInstance_Properties();

		/**
		 * The meta object literal for the '{@link com.synflow.models.dpn.impl.EntityImpl
		 * <em>Entity</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.impl.EntityImpl
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getEntity()
		 * @generated
		 */
		EClass ENTITY = eINSTANCE.getEntity();

		/**
		 * The meta object literal for the '<em><b>Inputs</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ENTITY__INPUTS = eINSTANCE.getEntity_Inputs();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ENTITY__NAME = eINSTANCE.getEntity_Name();

		/**
		 * The meta object literal for the '<em><b>Outputs</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ENTITY__OUTPUTS = eINSTANCE.getEntity_Outputs();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ENTITY__PROPERTIES = eINSTANCE.getEntity_Properties();

		/**
		 * The meta object literal for the '<em><b>File Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ENTITY__FILE_NAME = eINSTANCE.getEntity_FileName();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ENTITY__VARIABLES = eINSTANCE.getEntity_Variables();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ENTITY__LINE_NUMBER = eINSTANCE.getEntity_LineNumber();

		/**
		 * The meta object literal for the '<em><b>Procedures</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ENTITY__PROCEDURES = eINSTANCE.getEntity_Procedures();

		/**
		 * The meta object literal for the '{@link com.synflow.models.dpn.impl.ActorImpl
		 * <em>Actor</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.impl.ActorImpl
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getActor()
		 * @generated
		 */
		EClass ACTOR = eINSTANCE.getActor();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ACTOR__ACTIONS = eINSTANCE.getActor_Actions();

		/**
		 * The meta object literal for the '<em><b>Buffered Inputs</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ACTOR__BUFFERED_INPUTS = eINSTANCE.getActor_BufferedInputs();

		/**
		 * The meta object literal for the '<em><b>Fsm</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ACTOR__FSM = eINSTANCE.getActor_Fsm();

		/**
		 * The meta object literal for the '{@link com.synflow.models.dpn.impl.ActionImpl
		 * <em>Action</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.impl.ActionImpl
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getAction()
		 * @generated
		 */
		EClass ACTION = eINSTANCE.getAction();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ACTION__BODY = eINSTANCE.getAction_Body();

		/**
		 * The meta object literal for the '<em><b>Combinational</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ACTION__COMBINATIONAL = eINSTANCE.getAction_Combinational();

		/**
		 * The meta object literal for the '<em><b>Input Pattern</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ACTION__INPUT_PATTERN = eINSTANCE.getAction_InputPattern();

		/**
		 * The meta object literal for the '<em><b>Output Pattern</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ACTION__OUTPUT_PATTERN = eINSTANCE.getAction_OutputPattern();

		/**
		 * The meta object literal for the '<em><b>Peek Pattern</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ACTION__PEEK_PATTERN = eINSTANCE.getAction_PeekPattern();

		/**
		 * The meta object literal for the '<em><b>Scheduler</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ACTION__SCHEDULER = eINSTANCE.getAction_Scheduler();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ACTION__NAME = eINSTANCE.getAction_Name();

		/**
		 * The meta object literal for the '{@link com.synflow.models.dpn.impl.FSMImpl <em>FSM</em>}
		 * ' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.impl.FSMImpl
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getFSM()
		 * @generated
		 */
		EClass FSM = eINSTANCE.getFSM();

		/**
		 * The meta object literal for the '<em><b>Initial State</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference FSM__INITIAL_STATE = eINSTANCE.getFSM_InitialState();

		/**
		 * The meta object literal for the '{@link com.synflow.models.dpn.impl.PatternImpl
		 * <em>Pattern</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.impl.PatternImpl
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getPattern()
		 * @generated
		 */
		EClass PATTERN = eINSTANCE.getPattern();

		/**
		 * The meta object literal for the '<em><b>Ports</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PATTERN__PORTS = eINSTANCE.getPattern_Ports();

		/**
		 * The meta object literal for the '{@link com.synflow.models.dpn.impl.StateImpl
		 * <em>State</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.impl.StateImpl
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute STATE__NAME = eINSTANCE.getState_Name();

		/**
		 * The meta object literal for the '{@link com.synflow.models.dpn.impl.TransitionImpl
		 * <em>Transition</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.impl.TransitionImpl
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getTransition()
		 * @generated
		 */
		EClass TRANSITION = eINSTANCE.getTransition();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRANSITION__BODY = eINSTANCE.getTransition_Body();

		/**
		 * The meta object literal for the '<em><b>Lines</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TRANSITION__LINES = eINSTANCE.getTransition_Lines();

		/**
		 * The meta object literal for the '<em><b>Scheduler</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRANSITION__SCHEDULER = eINSTANCE.getTransition_Scheduler();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRANSITION__ACTION = eINSTANCE.getTransition_Action();

		/**
		 * The meta object literal for the '{@link com.synflow.models.dpn.impl.ArgumentImpl
		 * <em>Argument</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.impl.ArgumentImpl
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getArgument()
		 * @generated
		 */
		EClass ARGUMENT = eINSTANCE.getArgument();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ARGUMENT__VALUE = eINSTANCE.getArgument_Value();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ARGUMENT__VARIABLE = eINSTANCE.getArgument_Variable();

		/**
		 * The meta object literal for the '{@link com.synflow.models.dpn.impl.PortImpl
		 * <em>Port</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.impl.PortImpl
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getPort()
		 * @generated
		 */
		EClass PORT = eINSTANCE.getPort();

		/**
		 * The meta object literal for the '<em><b>Interface</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PORT__INTERFACE = eINSTANCE.getPort_Interface();

		/**
		 * The meta object literal for the '<em><b>Additional Inputs</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PORT__ADDITIONAL_INPUTS = eINSTANCE.getPort_AdditionalInputs();

		/**
		 * The meta object literal for the '<em><b>Additional Outputs</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PORT__ADDITIONAL_OUTPUTS = eINSTANCE.getPort_AdditionalOutputs();

		/**
		 * The meta object literal for the '<em><b>Synchronous</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PORT__SYNCHRONOUS = eINSTANCE.getPort_Synchronous();

		/**
		 * The meta object literal for the '{@link com.synflow.models.dpn.impl.ConnectionImpl
		 * <em>Connection</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.impl.ConnectionImpl
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getConnection()
		 * @generated
		 */
		EClass CONNECTION = eINSTANCE.getConnection();

		/**
		 * The meta object literal for the '<em><b>Source Port</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONNECTION__SOURCE_PORT = eINSTANCE.getConnection_SourcePort();

		/**
		 * The meta object literal for the '<em><b>Target Port</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONNECTION__TARGET_PORT = eINSTANCE.getConnection_TargetPort();

		/**
		 * The meta object literal for the '{@link com.synflow.models.dpn.impl.DPNImpl <em>DPN</em>}
		 * ' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see com.synflow.models.dpn.impl.DPNImpl
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getDPN()
		 * @generated
		 */
		EClass DPN = eINSTANCE.getDPN();

		/**
		 * The meta object literal for the '<em><b>Graph</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference DPN__GRAPH = eINSTANCE.getDPN_Graph();

		/**
		 * The meta object literal for the '<em><b>Instances</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference DPN__INSTANCES = eINSTANCE.getDPN_Instances();

		/**
		 * The meta object literal for the '<em><b>Vertex</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference DPN__VERTEX = eINSTANCE.getDPN_Vertex();

		/**
		 * The meta object literal for the '<em>Json Object</em>' data type. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see com.google.gson.JsonObject
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getJsonObject()
		 * @generated
		 */
		EDataType JSON_OBJECT = eINSTANCE.getJsonObject();

		/**
		 * The meta object literal for the '<em>Json Array</em>' data type. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see com.google.gson.JsonArray
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getJsonArray()
		 * @generated
		 */
		EDataType JSON_ARRAY = eINSTANCE.getJsonArray();

		/**
		 * The meta object literal for the '<em>Bi Map</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @see com.google.common.collect.BiMap
		 * @see com.synflow.models.dpn.impl.DpnPackageImpl#getBiMap()
		 * @generated
		 */
		EDataType BI_MAP = eINSTANCE.getBiMap();

	}

} // DpnPackage
