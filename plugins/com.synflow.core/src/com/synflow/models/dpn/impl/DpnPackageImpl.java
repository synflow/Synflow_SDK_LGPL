/**
 */
package com.synflow.models.dpn.impl;

import com.google.common.collect.BiMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.Argument;
import com.synflow.models.dpn.Connection;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.dpn.DpnPackage;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Goto;
import com.synflow.models.dpn.Instance;
import com.synflow.models.dpn.InterfaceType;
import com.synflow.models.dpn.Pattern;
import com.synflow.models.dpn.Port;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;
import com.synflow.models.dpn.Unit;
import com.synflow.models.graph.GraphPackage;
import com.synflow.models.graph.impl.GraphPackageImpl;
import com.synflow.models.ir.IrPackage;
import com.synflow.models.ir.impl.IrPackageImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class DpnPackageImpl extends EPackageImpl implements DpnPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass unitEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass gotoEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum interfaceTypeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass instanceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass entityEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass actorEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass actionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass fsmEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass patternEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass stateEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass transitionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass argumentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass portEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass connectionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass dpnEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType jsonObjectEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType jsonArrayEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType biMapEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI
	 * value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init
	 * init()}, which also performs initialization of the package, or returns the registered
	 * package, if one already exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.synflow.models.dpn.DpnPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DpnPackageImpl() {
		super(eNS_URI, DpnFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others
	 * upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link DpnPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to
	 * obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DpnPackage init() {
		if (isInited)
			return (DpnPackage) EPackage.Registry.INSTANCE.getEPackage(DpnPackage.eNS_URI);

		// Obtain or create and register package
		DpnPackageImpl theDpnPackage = (DpnPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof DpnPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new DpnPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		GraphPackageImpl theGraphPackage = (GraphPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(GraphPackage.eNS_URI) instanceof GraphPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(GraphPackage.eNS_URI)
						: GraphPackage.eINSTANCE);
		IrPackageImpl theIrPackage = (IrPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(IrPackage.eNS_URI) instanceof IrPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(IrPackage.eNS_URI)
						: IrPackage.eINSTANCE);

		// Create package meta-data objects
		theDpnPackage.createPackageContents();
		theGraphPackage.createPackageContents();
		theIrPackage.createPackageContents();

		// Initialize created meta-data
		theDpnPackage.initializePackageContents();
		theGraphPackage.initializePackageContents();
		theIrPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDpnPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DpnPackage.eNS_URI, theDpnPackage);
		return theDpnPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getUnit() {
		return unitEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getGoto() {
		return gotoEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getGoto_Target() {
		return (EReference) gotoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getInterfaceType() {
		return interfaceTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getInstance() {
		return instanceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getInstance_Arguments() {
		return (EReference) instanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getInstance_Entity() {
		return (EReference) instanceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInstance_Name() {
		return (EAttribute) instanceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInstance_Properties() {
		return (EAttribute) instanceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getEntity() {
		return entityEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getEntity_Inputs() {
		return (EReference) entityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getEntity_Name() {
		return (EAttribute) entityEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getEntity_Outputs() {
		return (EReference) entityEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getEntity_Properties() {
		return (EAttribute) entityEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getEntity_FileName() {
		return (EAttribute) entityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getEntity_Variables() {
		return (EReference) entityEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getEntity_LineNumber() {
		return (EAttribute) entityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getEntity_Procedures() {
		return (EReference) entityEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getActor() {
		return actorEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getActor_Actions() {
		return (EReference) actorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getActor_BufferedInputs() {
		return (EReference) actorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getActor_Fsm() {
		return (EReference) actorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getAction() {
		return actionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getAction_Body() {
		return (EReference) actionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getAction_Combinational() {
		return (EReference) actionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getAction_InputPattern() {
		return (EReference) actionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getAction_OutputPattern() {
		return (EReference) actionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getAction_PeekPattern() {
		return (EReference) actionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getAction_Scheduler() {
		return (EReference) actionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getAction_Name() {
		return (EAttribute) actionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getFSM() {
		return fsmEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFSM_InitialState() {
		return (EReference) fsmEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getPattern() {
		return patternEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getPattern_Ports() {
		return (EReference) patternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getState() {
		return stateEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getState_Name() {
		return (EAttribute) stateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTransition() {
		return transitionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTransition_Body() {
		return (EReference) transitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTransition_Lines() {
		return (EAttribute) transitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTransition_Scheduler() {
		return (EReference) transitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTransition_Action() {
		return (EReference) transitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getArgument() {
		return argumentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getArgument_Value() {
		return (EReference) argumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getArgument_Variable() {
		return (EReference) argumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getPort() {
		return portEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getPort_Interface() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getPort_AdditionalInputs() {
		return (EReference) portEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getPort_AdditionalOutputs() {
		return (EReference) portEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getPort_Synchronous() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getConnection() {
		return connectionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getConnection_SourcePort() {
		return (EReference) connectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getConnection_TargetPort() {
		return (EReference) connectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getDPN() {
		return dpnEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getDPN_Graph() {
		return (EReference) dpnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getDPN_Instances() {
		return (EReference) dpnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getDPN_Vertex() {
		return (EReference) dpnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EDataType getJsonObject() {
		return jsonObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EDataType getJsonArray() {
		return jsonArrayEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EDataType getBiMap() {
		return biMapEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DpnFactory getDpnFactory() {
		return (DpnFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to have no affect on
	 * any invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		actionEClass = createEClass(ACTION);
		createEReference(actionEClass, ACTION__BODY);
		createEReference(actionEClass, ACTION__COMBINATIONAL);
		createEReference(actionEClass, ACTION__INPUT_PATTERN);
		createEAttribute(actionEClass, ACTION__NAME);
		createEReference(actionEClass, ACTION__OUTPUT_PATTERN);
		createEReference(actionEClass, ACTION__PEEK_PATTERN);
		createEReference(actionEClass, ACTION__SCHEDULER);

		actorEClass = createEClass(ACTOR);
		createEReference(actorEClass, ACTOR__ACTIONS);
		createEReference(actorEClass, ACTOR__BUFFERED_INPUTS);
		createEReference(actorEClass, ACTOR__FSM);

		argumentEClass = createEClass(ARGUMENT);
		createEReference(argumentEClass, ARGUMENT__VALUE);
		createEReference(argumentEClass, ARGUMENT__VARIABLE);

		connectionEClass = createEClass(CONNECTION);
		createEReference(connectionEClass, CONNECTION__SOURCE_PORT);
		createEReference(connectionEClass, CONNECTION__TARGET_PORT);

		dpnEClass = createEClass(DPN);
		createEReference(dpnEClass, DPN__GRAPH);
		createEReference(dpnEClass, DPN__INSTANCES);
		createEReference(dpnEClass, DPN__VERTEX);

		entityEClass = createEClass(ENTITY);
		createEAttribute(entityEClass, ENTITY__FILE_NAME);
		createEReference(entityEClass, ENTITY__INPUTS);
		createEAttribute(entityEClass, ENTITY__LINE_NUMBER);
		createEAttribute(entityEClass, ENTITY__NAME);
		createEReference(entityEClass, ENTITY__OUTPUTS);
		createEAttribute(entityEClass, ENTITY__PROPERTIES);
		createEReference(entityEClass, ENTITY__PROCEDURES);
		createEReference(entityEClass, ENTITY__VARIABLES);

		fsmEClass = createEClass(FSM);
		createEReference(fsmEClass, FSM__INITIAL_STATE);

		instanceEClass = createEClass(INSTANCE);
		createEReference(instanceEClass, INSTANCE__ARGUMENTS);
		createEReference(instanceEClass, INSTANCE__ENTITY);
		createEAttribute(instanceEClass, INSTANCE__NAME);
		createEAttribute(instanceEClass, INSTANCE__PROPERTIES);

		patternEClass = createEClass(PATTERN);
		createEReference(patternEClass, PATTERN__PORTS);

		portEClass = createEClass(PORT);
		createEReference(portEClass, PORT__ADDITIONAL_INPUTS);
		createEReference(portEClass, PORT__ADDITIONAL_OUTPUTS);
		createEAttribute(portEClass, PORT__INTERFACE);
		createEAttribute(portEClass, PORT__SYNCHRONOUS);

		stateEClass = createEClass(STATE);
		createEAttribute(stateEClass, STATE__NAME);

		transitionEClass = createEClass(TRANSITION);
		createEReference(transitionEClass, TRANSITION__BODY);
		createEAttribute(transitionEClass, TRANSITION__LINES);
		createEReference(transitionEClass, TRANSITION__SCHEDULER);
		createEReference(transitionEClass, TRANSITION__ACTION);

		unitEClass = createEClass(UNIT);

		gotoEClass = createEClass(GOTO);
		createEReference(gotoEClass, GOTO__TARGET);

		// Create enums
		interfaceTypeEEnum = createEEnum(INTERFACE_TYPE);

		// Create data types
		jsonObjectEDataType = createEDataType(JSON_OBJECT);
		jsonArrayEDataType = createEDataType(JSON_ARRAY);
		biMapEDataType = createEDataType(BI_MAP);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is guarded to have
	 * no affect on any invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		IrPackage theIrPackage = (IrPackage) EPackage.Registry.INSTANCE
				.getEPackage(IrPackage.eNS_URI);
		GraphPackage theGraphPackage = (GraphPackage) EPackage.Registry.INSTANCE
				.getEPackage(GraphPackage.eNS_URI);

		// Create type parameters
		addETypeParameter(biMapEDataType, "T");
		addETypeParameter(biMapEDataType, "T1");

		// Set bounds for type parameters

		// Add supertypes to classes
		actorEClass.getESuperTypes().add(this.getEntity());
		connectionEClass.getESuperTypes().add(theGraphPackage.getEdge());
		dpnEClass.getESuperTypes().add(this.getEntity());
		fsmEClass.getESuperTypes().add(theGraphPackage.getGraph());
		instanceEClass.getESuperTypes().add(theGraphPackage.getVertex());
		portEClass.getESuperTypes().add(theIrPackage.getVar());
		stateEClass.getESuperTypes().add(theGraphPackage.getVertex());
		transitionEClass.getESuperTypes().add(theGraphPackage.getEdge());
		unitEClass.getESuperTypes().add(this.getEntity());
		gotoEClass.getESuperTypes().add(theIrPackage.getInstruction());

		// Initialize classes and features; add operations and parameters
		initEClass(actionEClass, Action.class, "Action", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAction_Body(), theIrPackage.getProcedure(), null, "body", null, 0, 1,
				Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAction_Combinational(), theIrPackage.getProcedure(), null,
				"combinational", null, 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getAction_InputPattern(), this.getPattern(), null, "inputPattern", null, 0,
				1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAction_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAction_OutputPattern(), this.getPattern(), null, "outputPattern", null, 0,
				1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAction_PeekPattern(), this.getPattern(), null, "peekPattern", null, 0, 1,
				Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAction_Scheduler(), theIrPackage.getProcedure(), null, "scheduler", null,
				0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(actorEClass, Actor.class, "Actor", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getActor_Actions(), this.getAction(), null, "actions", null, 0, -1,
				Actor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getActor_BufferedInputs(), this.getPort(), null, "bufferedInputs", null, 0,
				-1, Actor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getActor_Fsm(), this.getFSM(), null, "fsm", null, 0, 1, Actor.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(argumentEClass, Argument.class, "Argument", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArgument_Value(), theIrPackage.getExpression(), null, "value", null, 0, 1,
				Argument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArgument_Variable(), theIrPackage.getVar(), null, "variable", null, 0, 1,
				Argument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(connectionEClass, Connection.class, "Connection", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnection_SourcePort(), this.getPort(), null, "sourcePort", null, 0, 1,
				Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnection_TargetPort(), this.getPort(), null, "targetPort", null, 0, 1,
				Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dpnEClass, com.synflow.models.dpn.DPN.class, "DPN", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDPN_Graph(), theGraphPackage.getGraph(), null, "graph", null, 0, 1,
				com.synflow.models.dpn.DPN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getDPN_Instances(), this.getInstance(), null, "instances", null, 0, -1,
				com.synflow.models.dpn.DPN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getDPN_Vertex(), theGraphPackage.getVertex(), null, "vertex", null, 0, 1,
				com.synflow.models.dpn.DPN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(entityEClass, Entity.class, "Entity", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntity_FileName(), ecorePackage.getEString(), "fileName", null, 0, 1,
				Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_Inputs(), this.getPort(), null, "inputs", null, 0, -1,
				Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_LineNumber(), ecorePackage.getEInt(), "lineNumber", null, 0, 1,
				Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_Outputs(), this.getPort(), null, "outputs", null, 0, -1,
				Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_Properties(), this.getJsonObject(), "properties", null, 0, 1,
				Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_Procedures(), theIrPackage.getProcedure(), null, "procedures",
				null, 0, -1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_Variables(), theIrPackage.getVar(), null, "variables", null, 0, -1,
				Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fsmEClass, com.synflow.models.dpn.FSM.class, "FSM", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFSM_InitialState(), this.getState(), null, "initialState", null, 0, 1,
				com.synflow.models.dpn.FSM.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(instanceEClass, Instance.class, "Instance", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstance_Arguments(), this.getArgument(), null, "arguments", null, 0, -1,
				Instance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstance_Entity(), this.getEntity(), null, "entity", null, 0, 1,
				Instance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInstance_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				Instance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInstance_Properties(), this.getJsonObject(), "properties", null, 0, 1,
				Instance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(patternEClass, Pattern.class, "Pattern", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPattern_Ports(), this.getPort(), null, "ports", null, 0, -1,
				Pattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(portEClass, Port.class, "Port", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPort_AdditionalInputs(), theIrPackage.getVar(), null, "additionalInputs",
				null, 0, -1, Port.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPort_AdditionalOutputs(), theIrPackage.getVar(), null,
				"additionalOutputs", null, 0, -1, Port.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getPort_Interface(), this.getInterfaceType(), "interface", null, 0, 1,
				Port.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPort_Synchronous(), ecorePackage.getEBoolean(), "synchronous", "true", 0,
				1, Port.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stateEClass, State.class, "State", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getState_Name(), ecorePackage.getEString(), "name", null, 0, 1, State.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(transitionEClass, Transition.class, "Transition", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTransition_Body(), ecorePackage.getEObject(), null, "body", null, 0, -1,
				Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransition_Lines(), ecorePackage.getEIntegerObject(), "lines", null, 0,
				-1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransition_Scheduler(), ecorePackage.getEObject(), null, "scheduler",
				null, 0, -1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTransition_Action(), this.getAction(), null, "action", null, 0, 1,
				Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(unitEClass, Unit.class, "Unit", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(gotoEClass, Goto.class, "Goto", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGoto_Target(), this.getState(), null, "target", null, 0, 1, Goto.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(interfaceTypeEEnum, InterfaceType.class, "InterfaceType");
		addEEnumLiteral(interfaceTypeEEnum, InterfaceType.BARE);
		addEEnumLiteral(interfaceTypeEEnum, InterfaceType.SYNC);
		addEEnumLiteral(interfaceTypeEEnum, InterfaceType.SYNC_READY);
		addEEnumLiteral(interfaceTypeEEnum, InterfaceType.SYNC_ACK);

		// Initialize data types
		initEDataType(jsonObjectEDataType, JsonObject.class, "JsonObject", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(jsonArrayEDataType, JsonArray.class, "JsonArray", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(biMapEDataType, BiMap.class, "BiMap", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // DpnPackageImpl
