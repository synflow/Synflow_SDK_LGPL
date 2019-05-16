/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.ir.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import com.synflow.models.dpn.DpnPackage;
import com.synflow.models.dpn.impl.DpnPackageImpl;
import com.synflow.models.graph.GraphPackage;
import com.synflow.models.graph.impl.GraphPackageImpl;
import com.synflow.models.ir.Block;
import com.synflow.models.ir.BlockBasic;
import com.synflow.models.ir.BlockIf;
import com.synflow.models.ir.BlockWhile;
import com.synflow.models.ir.Def;
import com.synflow.models.ir.ExprBinary;
import com.synflow.models.ir.ExprBool;
import com.synflow.models.ir.ExprFloat;
import com.synflow.models.ir.ExprInt;
import com.synflow.models.ir.ExprList;
import com.synflow.models.ir.ExprResize;
import com.synflow.models.ir.ExprString;
import com.synflow.models.ir.ExprTernary;
import com.synflow.models.ir.ExprTypeConv;
import com.synflow.models.ir.ExprUnary;
import com.synflow.models.ir.ExprVar;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstAssign;
import com.synflow.models.ir.InstCall;
import com.synflow.models.ir.InstLoad;
import com.synflow.models.ir.InstPhi;
import com.synflow.models.ir.InstReturn;
import com.synflow.models.ir.InstStore;
import com.synflow.models.ir.Instruction;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.IrPackage;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.OpUnary;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeBool;
import com.synflow.models.ir.TypeFloat;
import com.synflow.models.ir.TypeInt;
import com.synflow.models.ir.TypeArray;
import com.synflow.models.ir.TypeString;
import com.synflow.models.ir.TypeVoid;
import com.synflow.models.ir.Use;
import com.synflow.models.ir.Var;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class IrPackageImpl extends EPackageImpl implements IrPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass expressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass typeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass typeBoolEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass typeFloatEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass typeIntEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass typeStringEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass typeVoidEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass instructionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass procedureEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass blockEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass blockBasicEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass blockIfEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass blockWhileEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass instAssignEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass instCallEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass instLoadEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass instPhiEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass instReturnEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass instStoreEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass varEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass useEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass typeArrayEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass exprBinaryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass exprBoolEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass exprFloatEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass exprIntEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass exprListEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass exprStringEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass exprResizeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass exprTernaryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass exprTypeConvEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass exprUnaryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass exprVarEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass defEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum opBinaryEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum opUnaryEEnum = null;

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
	 * @see com.synflow.models.ir.IrPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IrPackageImpl() {
		super(eNS_URI, IrFactory.eINSTANCE);
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
	 * This method is used to initialize {@link IrPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to
	 * obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IrPackage init() {
		if (isInited)
			return (IrPackage) EPackage.Registry.INSTANCE.getEPackage(IrPackage.eNS_URI);

		// Obtain or create and register package
		IrPackageImpl theIrPackage = (IrPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof IrPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new IrPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		DpnPackageImpl theDpnPackage = (DpnPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(DpnPackage.eNS_URI) instanceof DpnPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(DpnPackage.eNS_URI)
						: DpnPackage.eINSTANCE);
		GraphPackageImpl theGraphPackage = (GraphPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(GraphPackage.eNS_URI) instanceof GraphPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(GraphPackage.eNS_URI)
						: GraphPackage.eINSTANCE);

		// Create package meta-data objects
		theIrPackage.createPackageContents();
		theDpnPackage.createPackageContents();
		theGraphPackage.createPackageContents();

		// Initialize created meta-data
		theIrPackage.initializePackageContents();
		theDpnPackage.initializePackageContents();
		theGraphPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIrPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IrPackage.eNS_URI, theIrPackage);
		return theIrPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExpression() {
		return expressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getExpression_ComputedType() {
		return (EReference) expressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getType() {
		return typeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTypeBool() {
		return typeBoolEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTypeFloat() {
		return typeFloatEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTypeFloat_Size() {
		return (EAttribute) typeFloatEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTypeGen() {
		return typeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTypeInt() {
		return typeIntEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTypeInt_Size() {
		return (EAttribute) typeIntEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTypeInt_Signed() {
		return (EAttribute) typeIntEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTypeString() {
		return typeStringEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTypeString_Size() {
		return (EAttribute) typeStringEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTypeVoid() {
		return typeVoidEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getInstruction() {
		return instructionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getInstruction_LineNumber() {
		return (EAttribute) instructionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getProcedure() {
		return procedureEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getProcedure_Name() {
		return (EAttribute) procedureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getProcedure_ReturnType() {
		return (EReference) procedureEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getBlock() {
		return blockEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getBlockBasic() {
		return blockBasicEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getBlockBasic_Instructions() {
		return (EReference) blockBasicEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getBlockIf() {
		return blockIfEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getBlockIf_Condition() {
		return (EReference) blockIfEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getBlockIf_ElseBlocks() {
		return (EReference) blockIfEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getBlockIf_JoinBlock() {
		return (EReference) blockIfEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getBlockIf_LineNumber() {
		return (EAttribute) blockIfEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getBlockIf_ThenBlocks() {
		return (EReference) blockIfEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getBlockWhile() {
		return blockWhileEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getBlockWhile_Condition() {
		return (EReference) blockWhileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getBlockWhile_JoinBlock() {
		return (EReference) blockWhileEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getBlockWhile_LineNumber() {
		return (EAttribute) blockWhileEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getBlockWhile_Blocks() {
		return (EReference) blockWhileEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getProcedure_Blocks() {
		return (EReference) procedureEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getProcedure_Locals() {
		return (EReference) procedureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getProcedure_Parameters() {
		return (EReference) procedureEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getProcedure_LineNumber() {
		return (EAttribute) procedureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getInstAssign() {
		return instAssignEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstAssign_Target() {
		return (EReference) instAssignEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstAssign_Value() {
		return (EReference) instAssignEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getInstCall() {
		return instCallEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstCall_Arguments() {
		return (EReference) instCallEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstCall_Procedure() {
		return (EReference) instCallEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstCall_Target() {
		return (EReference) instCallEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInstCall_Print() {
		return (EAttribute) instCallEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInstCall_Assert() {
		return (EAttribute) instCallEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getInstLoad() {
		return instLoadEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstLoad_Indexes() {
		return (EReference) instLoadEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstLoad_Source() {
		return (EReference) instLoadEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstLoad_Target() {
		return (EReference) instLoadEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getInstPhi() {
		return instPhiEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstPhi_OldVariable() {
		return (EReference) instPhiEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstPhi_Target() {
		return (EReference) instPhiEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstPhi_Values() {
		return (EReference) instPhiEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getInstReturn() {
		return instReturnEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstReturn_Value() {
		return (EReference) instReturnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getInstStore() {
		return instStoreEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstStore_Indexes() {
		return (EReference) instStoreEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstStore_Target() {
		return (EReference) instStoreEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getInstStore_Value() {
		return (EReference) instStoreEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getVar() {
		return varEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVar_Index() {
		return (EAttribute) varEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getVar_InitialValue() {
		return (EReference) varEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVar_Name() {
		return (EAttribute) varEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getVar_Type() {
		return (EReference) varEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVar_Value() {
		return (EAttribute) varEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVar_Assignable() {
		return (EAttribute) varEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVar_Global() {
		return (EAttribute) varEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getVar_Uses() {
		return (EReference) varEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getVar_Defs() {
		return (EReference) varEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVar_LineNumber() {
		return (EAttribute) varEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVar_Local() {
		return (EAttribute) varEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getUse() {
		return useEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getUse_Variable() {
		return (EReference) useEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTypeArray() {
		return typeArrayEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTypeArray_Dimensions() {
		return (EAttribute) typeArrayEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTypeArray_ElementType() {
		return (EReference) typeArrayEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExprBinary() {
		return exprBinaryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getExprBinary_E1() {
		return (EReference) exprBinaryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getExprBinary_E2() {
		return (EReference) exprBinaryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExprBinary_Op() {
		return (EAttribute) exprBinaryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExprBool() {
		return exprBoolEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExprBool_Value() {
		return (EAttribute) exprBoolEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExprFloat() {
		return exprFloatEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExprFloat_Value() {
		return (EAttribute) exprFloatEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExprInt() {
		return exprIntEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExprInt_Value() {
		return (EAttribute) exprIntEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExprList() {
		return exprListEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getExprList_Value() {
		return (EReference) exprListEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExprString() {
		return exprStringEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExprString_Value() {
		return (EAttribute) exprStringEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getExprResize() {
		return exprResizeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getExprResize_Expr() {
		return (EReference) exprResizeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getExprResize_TargetSize() {
		return (EAttribute) exprResizeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getExprTernary() {
		return exprTernaryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getExprTernary_Cond() {
		return (EReference) exprTernaryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getExprTernary_E1() {
		return (EReference) exprTernaryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getExprTernary_E2() {
		return (EReference) exprTernaryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getExprTypeConv() {
		return exprTypeConvEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getExprTypeConv_Expr() {
		return (EReference) exprTypeConvEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getExprTypeConv_TypeName() {
		return (EAttribute) exprTypeConvEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExprUnary() {
		return exprUnaryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getExprUnary_Expr() {
		return (EReference) exprUnaryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExprUnary_Op() {
		return (EAttribute) exprUnaryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExprVar() {
		return exprVarEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getExprVar_Use() {
		return (EReference) exprVarEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getDef() {
		return defEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getDef_Variable() {
		return (EReference) defEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getOpBinary() {
		return opBinaryEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getOpUnary() {
		return opUnaryEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public IrFactory getIrFactory() {
		return (IrFactory) getEFactoryInstance();
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
		procedureEClass = createEClass(PROCEDURE);
		createEAttribute(procedureEClass, PROCEDURE__LINE_NUMBER);
		createEReference(procedureEClass, PROCEDURE__LOCALS);
		createEAttribute(procedureEClass, PROCEDURE__NAME);
		createEReference(procedureEClass, PROCEDURE__BLOCKS);
		createEReference(procedureEClass, PROCEDURE__PARAMETERS);
		createEReference(procedureEClass, PROCEDURE__RETURN_TYPE);

		blockEClass = createEClass(BLOCK);

		blockBasicEClass = createEClass(BLOCK_BASIC);
		createEReference(blockBasicEClass, BLOCK_BASIC__INSTRUCTIONS);

		blockIfEClass = createEClass(BLOCK_IF);
		createEReference(blockIfEClass, BLOCK_IF__CONDITION);
		createEReference(blockIfEClass, BLOCK_IF__ELSE_BLOCKS);
		createEReference(blockIfEClass, BLOCK_IF__JOIN_BLOCK);
		createEAttribute(blockIfEClass, BLOCK_IF__LINE_NUMBER);
		createEReference(blockIfEClass, BLOCK_IF__THEN_BLOCKS);

		blockWhileEClass = createEClass(BLOCK_WHILE);
		createEReference(blockWhileEClass, BLOCK_WHILE__CONDITION);
		createEReference(blockWhileEClass, BLOCK_WHILE__JOIN_BLOCK);
		createEAttribute(blockWhileEClass, BLOCK_WHILE__LINE_NUMBER);
		createEReference(blockWhileEClass, BLOCK_WHILE__BLOCKS);

		instructionEClass = createEClass(INSTRUCTION);
		createEAttribute(instructionEClass, INSTRUCTION__LINE_NUMBER);

		instAssignEClass = createEClass(INST_ASSIGN);
		createEReference(instAssignEClass, INST_ASSIGN__TARGET);
		createEReference(instAssignEClass, INST_ASSIGN__VALUE);

		instCallEClass = createEClass(INST_CALL);
		createEReference(instCallEClass, INST_CALL__ARGUMENTS);
		createEReference(instCallEClass, INST_CALL__PROCEDURE);
		createEReference(instCallEClass, INST_CALL__TARGET);
		createEAttribute(instCallEClass, INST_CALL__PRINT);
		createEAttribute(instCallEClass, INST_CALL__ASSERT);

		instLoadEClass = createEClass(INST_LOAD);
		createEReference(instLoadEClass, INST_LOAD__INDEXES);
		createEReference(instLoadEClass, INST_LOAD__SOURCE);
		createEReference(instLoadEClass, INST_LOAD__TARGET);

		instPhiEClass = createEClass(INST_PHI);
		createEReference(instPhiEClass, INST_PHI__OLD_VARIABLE);
		createEReference(instPhiEClass, INST_PHI__TARGET);
		createEReference(instPhiEClass, INST_PHI__VALUES);

		instReturnEClass = createEClass(INST_RETURN);
		createEReference(instReturnEClass, INST_RETURN__VALUE);

		instStoreEClass = createEClass(INST_STORE);
		createEReference(instStoreEClass, INST_STORE__INDEXES);
		createEReference(instStoreEClass, INST_STORE__TARGET);
		createEReference(instStoreEClass, INST_STORE__VALUE);

		expressionEClass = createEClass(EXPRESSION);
		createEReference(expressionEClass, EXPRESSION__COMPUTED_TYPE);

		exprBinaryEClass = createEClass(EXPR_BINARY);
		createEReference(exprBinaryEClass, EXPR_BINARY__E1);
		createEReference(exprBinaryEClass, EXPR_BINARY__E2);
		createEAttribute(exprBinaryEClass, EXPR_BINARY__OP);

		exprBoolEClass = createEClass(EXPR_BOOL);
		createEAttribute(exprBoolEClass, EXPR_BOOL__VALUE);

		exprFloatEClass = createEClass(EXPR_FLOAT);
		createEAttribute(exprFloatEClass, EXPR_FLOAT__VALUE);

		exprIntEClass = createEClass(EXPR_INT);
		createEAttribute(exprIntEClass, EXPR_INT__VALUE);

		exprListEClass = createEClass(EXPR_LIST);
		createEReference(exprListEClass, EXPR_LIST__VALUE);

		exprStringEClass = createEClass(EXPR_STRING);
		createEAttribute(exprStringEClass, EXPR_STRING__VALUE);

		exprResizeEClass = createEClass(EXPR_RESIZE);
		createEReference(exprResizeEClass, EXPR_RESIZE__EXPR);
		createEAttribute(exprResizeEClass, EXPR_RESIZE__TARGET_SIZE);

		exprTernaryEClass = createEClass(EXPR_TERNARY);
		createEReference(exprTernaryEClass, EXPR_TERNARY__COND);
		createEReference(exprTernaryEClass, EXPR_TERNARY__E1);
		createEReference(exprTernaryEClass, EXPR_TERNARY__E2);

		exprTypeConvEClass = createEClass(EXPR_TYPE_CONV);
		createEReference(exprTypeConvEClass, EXPR_TYPE_CONV__EXPR);
		createEAttribute(exprTypeConvEClass, EXPR_TYPE_CONV__TYPE_NAME);

		exprUnaryEClass = createEClass(EXPR_UNARY);
		createEReference(exprUnaryEClass, EXPR_UNARY__EXPR);
		createEAttribute(exprUnaryEClass, EXPR_UNARY__OP);

		exprVarEClass = createEClass(EXPR_VAR);
		createEReference(exprVarEClass, EXPR_VAR__USE);

		typeEClass = createEClass(TYPE);

		typeArrayEClass = createEClass(TYPE_ARRAY);
		createEAttribute(typeArrayEClass, TYPE_ARRAY__DIMENSIONS);
		createEReference(typeArrayEClass, TYPE_ARRAY__ELEMENT_TYPE);

		typeBoolEClass = createEClass(TYPE_BOOL);

		typeFloatEClass = createEClass(TYPE_FLOAT);
		createEAttribute(typeFloatEClass, TYPE_FLOAT__SIZE);

		typeIntEClass = createEClass(TYPE_INT);
		createEAttribute(typeIntEClass, TYPE_INT__SIGNED);
		createEAttribute(typeIntEClass, TYPE_INT__SIZE);

		typeStringEClass = createEClass(TYPE_STRING);
		createEAttribute(typeStringEClass, TYPE_STRING__SIZE);

		typeVoidEClass = createEClass(TYPE_VOID);

		defEClass = createEClass(DEF);
		createEReference(defEClass, DEF__VARIABLE);

		varEClass = createEClass(VAR);
		createEAttribute(varEClass, VAR__ASSIGNABLE);
		createEReference(varEClass, VAR__DEFS);
		createEAttribute(varEClass, VAR__GLOBAL);
		createEAttribute(varEClass, VAR__INDEX);
		createEReference(varEClass, VAR__INITIAL_VALUE);
		createEAttribute(varEClass, VAR__LINE_NUMBER);
		createEAttribute(varEClass, VAR__LOCAL);
		createEAttribute(varEClass, VAR__NAME);
		createEReference(varEClass, VAR__TYPE);
		createEReference(varEClass, VAR__USES);
		createEAttribute(varEClass, VAR__VALUE);

		useEClass = createEClass(USE);
		createEReference(useEClass, USE__VARIABLE);

		// Create enums
		opBinaryEEnum = createEEnum(OP_BINARY);
		opUnaryEEnum = createEEnum(OP_UNARY);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		blockBasicEClass.getESuperTypes().add(this.getBlock());
		blockIfEClass.getESuperTypes().add(this.getBlock());
		blockWhileEClass.getESuperTypes().add(this.getBlock());
		instAssignEClass.getESuperTypes().add(this.getInstruction());
		instCallEClass.getESuperTypes().add(this.getInstruction());
		instLoadEClass.getESuperTypes().add(this.getInstruction());
		instPhiEClass.getESuperTypes().add(this.getInstruction());
		instReturnEClass.getESuperTypes().add(this.getInstruction());
		instStoreEClass.getESuperTypes().add(this.getInstruction());
		exprBinaryEClass.getESuperTypes().add(this.getExpression());
		exprBoolEClass.getESuperTypes().add(this.getExpression());
		exprFloatEClass.getESuperTypes().add(this.getExpression());
		exprIntEClass.getESuperTypes().add(this.getExpression());
		exprListEClass.getESuperTypes().add(this.getExpression());
		exprStringEClass.getESuperTypes().add(this.getExpression());
		exprResizeEClass.getESuperTypes().add(this.getExpression());
		exprTernaryEClass.getESuperTypes().add(this.getExpression());
		exprTypeConvEClass.getESuperTypes().add(this.getExpression());
		exprUnaryEClass.getESuperTypes().add(this.getExpression());
		exprVarEClass.getESuperTypes().add(this.getExpression());
		typeArrayEClass.getESuperTypes().add(this.getType());
		typeBoolEClass.getESuperTypes().add(this.getType());
		typeFloatEClass.getESuperTypes().add(this.getType());
		typeIntEClass.getESuperTypes().add(this.getType());
		typeStringEClass.getESuperTypes().add(this.getType());
		typeVoidEClass.getESuperTypes().add(this.getType());

		// Initialize classes and features; add operations and parameters
		initEClass(procedureEClass, Procedure.class, "Procedure", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProcedure_LineNumber(), ecorePackage.getEInt(), "lineNumber", null, 0, 1,
				Procedure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProcedure_Locals(), this.getVar(), null, "locals", null, 0, -1,
				Procedure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProcedure_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				Procedure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProcedure_Blocks(), this.getBlock(), null, "blocks", null, 0, -1,
				Procedure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProcedure_Parameters(), this.getVar(), null, "parameters", null, 0, -1,
				Procedure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProcedure_ReturnType(), this.getType(), null, "returnType", null, 0, 1,
				Procedure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(blockEClass, Block.class, "Block", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(blockBasicEClass, BlockBasic.class, "BlockBasic", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlockBasic_Instructions(), this.getInstruction(), null, "instructions",
				null, 0, -1, BlockBasic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(blockIfEClass, BlockIf.class, "BlockIf", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlockIf_Condition(), this.getExpression(), null, "condition", null, 0, 1,
				BlockIf.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBlockIf_ElseBlocks(), this.getBlock(), null, "elseBlocks", null, 0, -1,
				BlockIf.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBlockIf_JoinBlock(), this.getBlockBasic(), null, "joinBlock", null, 0, 1,
				BlockIf.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBlockIf_LineNumber(), ecorePackage.getEInt(), "lineNumber", null, 0, 1,
				BlockIf.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBlockIf_ThenBlocks(), this.getBlock(), null, "thenBlocks", null, 0, -1,
				BlockIf.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(blockWhileEClass, BlockWhile.class, "BlockWhile", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlockWhile_Condition(), this.getExpression(), null, "condition", null, 0,
				1, BlockWhile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBlockWhile_JoinBlock(), this.getBlockBasic(), null, "joinBlock", null, 0,
				1, BlockWhile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBlockWhile_LineNumber(), ecorePackage.getEInt(), "lineNumber", null, 0, 1,
				BlockWhile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBlockWhile_Blocks(), this.getBlock(), null, "blocks", null, 0, -1,
				BlockWhile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instructionEClass, Instruction.class, "Instruction", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInstruction_LineNumber(), ecorePackage.getEInt(), "lineNumber", null, 0,
				1, Instruction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instAssignEClass, InstAssign.class, "InstAssign", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstAssign_Target(), this.getDef(), null, "target", null, 0, 1,
				InstAssign.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstAssign_Value(), this.getExpression(), null, "value", null, 0, 1,
				InstAssign.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instCallEClass, InstCall.class, "InstCall", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstCall_Arguments(), this.getExpression(), null, "arguments", null, 0,
				-1, InstCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstCall_Procedure(), this.getProcedure(), null, "procedure", null, 0, 1,
				InstCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstCall_Target(), this.getDef(), null, "target", null, 0, 1,
				InstCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInstCall_Print(), ecorePackage.getEBoolean(), "print", null, 0, 1,
				InstCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInstCall_Assert(), ecorePackage.getEBoolean(), "assert", null, 0, 1,
				InstCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instLoadEClass, InstLoad.class, "InstLoad", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstLoad_Indexes(), this.getExpression(), null, "indexes", null, 0, -1,
				InstLoad.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstLoad_Source(), this.getUse(), null, "source", null, 0, 1,
				InstLoad.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstLoad_Target(), this.getDef(), null, "target", null, 0, 1,
				InstLoad.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instPhiEClass, InstPhi.class, "InstPhi", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstPhi_OldVariable(), this.getVar(), null, "oldVariable", null, 0, 1,
				InstPhi.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstPhi_Target(), this.getDef(), null, "target", null, 0, 1,
				InstPhi.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstPhi_Values(), this.getExpression(), null, "values", null, 0, -1,
				InstPhi.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instReturnEClass, InstReturn.class, "InstReturn", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstReturn_Value(), this.getExpression(), null, "value", null, 0, 1,
				InstReturn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instStoreEClass, InstStore.class, "InstStore", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstStore_Indexes(), this.getExpression(), null, "indexes", null, 0, -1,
				InstStore.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstStore_Target(), this.getDef(), null, "target", null, 0, 1,
				InstStore.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstStore_Value(), this.getExpression(), null, "value", null, 0, 1,
				InstStore.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expressionEClass, Expression.class, "Expression", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExpression_ComputedType(), this.getType(), null, "computedType", null, 0,
				1, Expression.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exprBinaryEClass, ExprBinary.class, "ExprBinary", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExprBinary_E1(), this.getExpression(), null, "e1", null, 0, 1,
				ExprBinary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExprBinary_E2(), this.getExpression(), null, "e2", null, 0, 1,
				ExprBinary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExprBinary_Op(), this.getOpBinary(), "op", null, 0, 1, ExprBinary.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(exprBoolEClass, ExprBool.class, "ExprBool", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExprBool_Value(), ecorePackage.getEBoolean(), "value", null, 0, 1,
				ExprBool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exprFloatEClass, ExprFloat.class, "ExprFloat", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExprFloat_Value(), ecorePackage.getEBigDecimal(), "value", null, 0, 1,
				ExprFloat.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exprIntEClass, ExprInt.class, "ExprInt", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExprInt_Value(), ecorePackage.getEBigInteger(), "value", null, 0, 1,
				ExprInt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exprListEClass, ExprList.class, "ExprList", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExprList_Value(), this.getExpression(), null, "value", null, 0, -1,
				ExprList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exprStringEClass, ExprString.class, "ExprString", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExprString_Value(), ecorePackage.getEString(), "value", null, 0, 1,
				ExprString.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exprResizeEClass, ExprResize.class, "ExprResize", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExprResize_Expr(), this.getExpression(), null, "expr", null, 0, 1,
				ExprResize.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExprResize_TargetSize(), ecorePackage.getEInt(), "targetSize", null, 0, 1,
				ExprResize.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exprTernaryEClass, ExprTernary.class, "ExprTernary", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExprTernary_Cond(), this.getExpression(), null, "cond", null, 0, 1,
				ExprTernary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExprTernary_E1(), this.getExpression(), null, "e1", null, 0, 1,
				ExprTernary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExprTernary_E2(), this.getExpression(), null, "e2", null, 0, 1,
				ExprTernary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exprTypeConvEClass, ExprTypeConv.class, "ExprTypeConv", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExprTypeConv_Expr(), this.getExpression(), null, "expr", null, 0, 1,
				ExprTypeConv.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExprTypeConv_TypeName(), ecorePackage.getEString(), "typeName", null, 0,
				1, ExprTypeConv.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exprUnaryEClass, ExprUnary.class, "ExprUnary", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExprUnary_Expr(), this.getExpression(), null, "expr", null, 0, 1,
				ExprUnary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExprUnary_Op(), this.getOpUnary(), "op", null, 0, 1, ExprUnary.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(exprVarEClass, ExprVar.class, "ExprVar", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExprVar_Use(), this.getUse(), null, "use", null, 0, 1, ExprVar.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeEClass, Type.class, "Type", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(typeArrayEClass, TypeArray.class, "TypeArray", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeArray_Dimensions(), ecorePackage.getEIntegerObject(), "dimensions",
				null, 0, -1, TypeArray.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTypeArray_ElementType(), this.getType(), null, "elementType", null, 0, 1,
				TypeArray.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeBoolEClass, TypeBool.class, "TypeBool", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(typeFloatEClass, TypeFloat.class, "TypeFloat", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeFloat_Size(), ecorePackage.getEInt(), "size", null, 0, 1,
				TypeFloat.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeIntEClass, TypeInt.class, "TypeInt", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeInt_Signed(), ecorePackage.getEBoolean(), "signed", null, 0, 1,
				TypeInt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTypeInt_Size(), ecorePackage.getEInt(), "size", null, 0, 1, TypeInt.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(typeStringEClass, TypeString.class, "TypeString", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeString_Size(), ecorePackage.getEInt(), "size", null, 0, 1,
				TypeString.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeVoidEClass, TypeVoid.class, "TypeVoid", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(defEClass, Def.class, "Def", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDef_Variable(), this.getVar(), this.getVar_Defs(), "variable", null, 0, 1,
				Def.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(varEClass, Var.class, "Var", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVar_Assignable(), ecorePackage.getEBoolean(), "assignable", null, 0, 1,
				Var.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVar_Defs(), this.getDef(), this.getDef_Variable(), "defs", null, 0, -1,
				Var.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVar_Global(), ecorePackage.getEBoolean(), "global", null, 0, 1, Var.class,
				IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);
		initEAttribute(getVar_Index(), ecorePackage.getEInt(), "index", null, 0, 1, Var.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getVar_InitialValue(), this.getExpression(), null, "initialValue", null, 0,
				1, Var.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVar_LineNumber(), ecorePackage.getEInt(), "lineNumber", null, 0, 1,
				Var.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVar_Local(), ecorePackage.getEBoolean(), "local", null, 0, 1, Var.class,
				IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);
		initEAttribute(getVar_Name(), ecorePackage.getEString(), "name", null, 0, 1, Var.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getVar_Type(), this.getType(), null, "type", null, 0, 1, Var.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVar_Uses(), this.getUse(), this.getUse_Variable(), "uses", null, 0, -1,
				Var.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVar_Value(), ecorePackage.getEJavaObject(), "value", null, 0, 1,
				Var.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(useEClass, Use.class, "Use", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUse_Variable(), this.getVar(), this.getVar_Uses(), "variable", null, 0, 1,
				Use.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(opBinaryEEnum, OpBinary.class, "OpBinary");
		addEEnumLiteral(opBinaryEEnum, OpBinary.BITAND);
		addEEnumLiteral(opBinaryEEnum, OpBinary.BITOR);
		addEEnumLiteral(opBinaryEEnum, OpBinary.BITXOR);
		addEEnumLiteral(opBinaryEEnum, OpBinary.DIV);
		addEEnumLiteral(opBinaryEEnum, OpBinary.DIV_INT);
		addEEnumLiteral(opBinaryEEnum, OpBinary.EQ);
		addEEnumLiteral(opBinaryEEnum, OpBinary.EXP);
		addEEnumLiteral(opBinaryEEnum, OpBinary.GE);
		addEEnumLiteral(opBinaryEEnum, OpBinary.GT);
		addEEnumLiteral(opBinaryEEnum, OpBinary.LE);
		addEEnumLiteral(opBinaryEEnum, OpBinary.LOGIC_AND);
		addEEnumLiteral(opBinaryEEnum, OpBinary.LOGIC_OR);
		addEEnumLiteral(opBinaryEEnum, OpBinary.LT);
		addEEnumLiteral(opBinaryEEnum, OpBinary.MINUS);
		addEEnumLiteral(opBinaryEEnum, OpBinary.MOD);
		addEEnumLiteral(opBinaryEEnum, OpBinary.NE);
		addEEnumLiteral(opBinaryEEnum, OpBinary.PLUS);
		addEEnumLiteral(opBinaryEEnum, OpBinary.SHIFT_LEFT);
		addEEnumLiteral(opBinaryEEnum, OpBinary.SHIFT_RIGHT);
		addEEnumLiteral(opBinaryEEnum, OpBinary.TIMES);

		initEEnum(opUnaryEEnum, OpUnary.class, "OpUnary");
		addEEnumLiteral(opUnaryEEnum, OpUnary.BITNOT);
		addEEnumLiteral(opUnaryEEnum, OpUnary.LOGIC_NOT);
		addEEnumLiteral(opUnaryEEnum, OpUnary.MINUS);

		// Create resource
		createResource(eNS_URI);
	}

} // IrPackageImpl
