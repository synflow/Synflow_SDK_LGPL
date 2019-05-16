/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.ir;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see com.synflow.models.ir.IrFactory
 * @model kind="package"
 * @generated
 */
public interface IrPackage extends EPackage {
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
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.DefImpl <em>Def</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.DefImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getDef()
		 * @generated
		 */
		EClass DEF = eINSTANCE.getDef();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference DEF__VARIABLE = eINSTANCE.getDef_Variable();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.ExprBinaryImpl
		 * <em>Expr Binary</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.ExprBinaryImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprBinary()
		 * @generated
		 */
		EClass EXPR_BINARY = eINSTANCE.getExprBinary();

		/**
		 * The meta object literal for the '<em><b>E1</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPR_BINARY__E1 = eINSTANCE.getExprBinary_E1();

		/**
		 * The meta object literal for the '<em><b>E2</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPR_BINARY__E2 = eINSTANCE.getExprBinary_E2();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXPR_BINARY__OP = eINSTANCE.getExprBinary_Op();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.ExprBoolImpl
		 * <em>Expr Bool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.ExprBoolImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprBool()
		 * @generated
		 */
		EClass EXPR_BOOL = eINSTANCE.getExprBool();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXPR_BOOL__VALUE = eINSTANCE.getExprBool_Value();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.ExprFloatImpl
		 * <em>Expr Float</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.ExprFloatImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprFloat()
		 * @generated
		 */
		EClass EXPR_FLOAT = eINSTANCE.getExprFloat();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXPR_FLOAT__VALUE = eINSTANCE.getExprFloat_Value();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.ExprIntImpl
		 * <em>Expr Int</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.ExprIntImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprInt()
		 * @generated
		 */
		EClass EXPR_INT = eINSTANCE.getExprInt();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXPR_INT__VALUE = eINSTANCE.getExprInt_Value();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.ExprListImpl
		 * <em>Expr List</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.ExprListImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprList()
		 * @generated
		 */
		EClass EXPR_LIST = eINSTANCE.getExprList();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPR_LIST__VALUE = eINSTANCE.getExprList_Value();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.ExprStringImpl
		 * <em>Expr String</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.ExprStringImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprString()
		 * @generated
		 */
		EClass EXPR_STRING = eINSTANCE.getExprString();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXPR_STRING__VALUE = eINSTANCE.getExprString_Value();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.ExprResizeImpl
		 * <em>Expr Resize</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.ExprResizeImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprResize()
		 * @generated
		 */
		EClass EXPR_RESIZE = eINSTANCE.getExprResize();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPR_RESIZE__EXPR = eINSTANCE.getExprResize_Expr();

		/**
		 * The meta object literal for the '<em><b>Target Size</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXPR_RESIZE__TARGET_SIZE = eINSTANCE.getExprResize_TargetSize();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.ExprTernaryImpl
		 * <em>Expr Ternary</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.ExprTernaryImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprTernary()
		 * @generated
		 */
		EClass EXPR_TERNARY = eINSTANCE.getExprTernary();

		/**
		 * The meta object literal for the '<em><b>Cond</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPR_TERNARY__COND = eINSTANCE.getExprTernary_Cond();

		/**
		 * The meta object literal for the '<em><b>E1</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPR_TERNARY__E1 = eINSTANCE.getExprTernary_E1();

		/**
		 * The meta object literal for the '<em><b>E2</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPR_TERNARY__E2 = eINSTANCE.getExprTernary_E2();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.ExprTypeConvImpl
		 * <em>Expr Type Conv</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.ExprTypeConvImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprTypeConv()
		 * @generated
		 */
		EClass EXPR_TYPE_CONV = eINSTANCE.getExprTypeConv();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPR_TYPE_CONV__EXPR = eINSTANCE.getExprTypeConv_Expr();

		/**
		 * The meta object literal for the '<em><b>Type Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXPR_TYPE_CONV__TYPE_NAME = eINSTANCE.getExprTypeConv_TypeName();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.ExprUnaryImpl
		 * <em>Expr Unary</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.ExprUnaryImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprUnary()
		 * @generated
		 */
		EClass EXPR_UNARY = eINSTANCE.getExprUnary();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPR_UNARY__EXPR = eINSTANCE.getExprUnary_Expr();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXPR_UNARY__OP = eINSTANCE.getExprUnary_Op();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.ExprVarImpl
		 * <em>Expr Var</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.ExprVarImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprVar()
		 * @generated
		 */
		EClass EXPR_VAR = eINSTANCE.getExprVar();

		/**
		 * The meta object literal for the '<em><b>Use</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPR_VAR__USE = eINSTANCE.getExprVar_Use();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.ExpressionImpl
		 * <em>Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.ExpressionImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '<em><b>Computed Type</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPRESSION__COMPUTED_TYPE = eINSTANCE.getExpression_ComputedType();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.InstAssignImpl
		 * <em>Inst Assign</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.InstAssignImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getInstAssign()
		 * @generated
		 */
		EClass INST_ASSIGN = eINSTANCE.getInstAssign();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_ASSIGN__TARGET = eINSTANCE.getInstAssign_Target();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_ASSIGN__VALUE = eINSTANCE.getInstAssign_Value();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.InstCallImpl
		 * <em>Inst Call</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.InstCallImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getInstCall()
		 * @generated
		 */
		EClass INST_CALL = eINSTANCE.getInstCall();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_CALL__ARGUMENTS = eINSTANCE.getInstCall_Arguments();

		/**
		 * The meta object literal for the '<em><b>Procedure</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_CALL__PROCEDURE = eINSTANCE.getInstCall_Procedure();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_CALL__TARGET = eINSTANCE.getInstCall_Target();

		/**
		 * The meta object literal for the '<em><b>Print</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute INST_CALL__PRINT = eINSTANCE.getInstCall_Print();

		/**
		 * The meta object literal for the '<em><b>Assert</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute INST_CALL__ASSERT = eINSTANCE.getInstCall_Assert();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.InstLoadImpl
		 * <em>Inst Load</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.InstLoadImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getInstLoad()
		 * @generated
		 */
		EClass INST_LOAD = eINSTANCE.getInstLoad();

		/**
		 * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_LOAD__INDEXES = eINSTANCE.getInstLoad_Indexes();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_LOAD__SOURCE = eINSTANCE.getInstLoad_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_LOAD__TARGET = eINSTANCE.getInstLoad_Target();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.InstPhiImpl
		 * <em>Inst Phi</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.InstPhiImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getInstPhi()
		 * @generated
		 */
		EClass INST_PHI = eINSTANCE.getInstPhi();

		/**
		 * The meta object literal for the '<em><b>Old Variable</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_PHI__OLD_VARIABLE = eINSTANCE.getInstPhi_OldVariable();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_PHI__TARGET = eINSTANCE.getInstPhi_Target();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_PHI__VALUES = eINSTANCE.getInstPhi_Values();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.InstReturnImpl
		 * <em>Inst Return</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.InstReturnImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getInstReturn()
		 * @generated
		 */
		EClass INST_RETURN = eINSTANCE.getInstReturn();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_RETURN__VALUE = eINSTANCE.getInstReturn_Value();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.InstStoreImpl
		 * <em>Inst Store</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.InstStoreImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getInstStore()
		 * @generated
		 */
		EClass INST_STORE = eINSTANCE.getInstStore();

		/**
		 * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_STORE__INDEXES = eINSTANCE.getInstStore_Indexes();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_STORE__TARGET = eINSTANCE.getInstStore_Target();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INST_STORE__VALUE = eINSTANCE.getInstStore_Value();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.InstructionImpl
		 * <em>Instruction</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.InstructionImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getInstruction()
		 * @generated
		 */
		EClass INSTRUCTION = eINSTANCE.getInstruction();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute INSTRUCTION__LINE_NUMBER = eINSTANCE.getInstruction_LineNumber();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.OpBinary <em>Op Binary</em>
		 * }' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.OpBinary
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getOpBinary()
		 * @generated
		 */
		EEnum OP_BINARY = eINSTANCE.getOpBinary();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.OpUnary <em>Op Unary</em>}'
		 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.OpUnary
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getOpUnary()
		 * @generated
		 */
		EEnum OP_UNARY = eINSTANCE.getOpUnary();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.ProcedureImpl
		 * <em>Procedure</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.ProcedureImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getProcedure()
		 * @generated
		 */
		EClass PROCEDURE = eINSTANCE.getProcedure();

		/**
		 * The meta object literal for the '<em><b>Locals</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROCEDURE__LOCALS = eINSTANCE.getProcedure_Locals();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROCEDURE__NAME = eINSTANCE.getProcedure_Name();

		/**
		 * The meta object literal for the '<em><b>Blocks</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROCEDURE__BLOCKS = eINSTANCE.getProcedure_Blocks();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROCEDURE__PARAMETERS = eINSTANCE.getProcedure_Parameters();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROCEDURE__LINE_NUMBER = eINSTANCE.getProcedure_LineNumber();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROCEDURE__RETURN_TYPE = eINSTANCE.getProcedure_ReturnType();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.BlockImpl
		 * <em>Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.BlockImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getBlock()
		 * @generated
		 */
		EClass BLOCK = eINSTANCE.getBlock();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.BlockBasicImpl
		 * <em>Block Basic</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.BlockBasicImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getBlockBasic()
		 * @generated
		 */
		EClass BLOCK_BASIC = eINSTANCE.getBlockBasic();

		/**
		 * The meta object literal for the '<em><b>Instructions</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BLOCK_BASIC__INSTRUCTIONS = eINSTANCE.getBlockBasic_Instructions();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.BlockIfImpl
		 * <em>Block If</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.BlockIfImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getBlockIf()
		 * @generated
		 */
		EClass BLOCK_IF = eINSTANCE.getBlockIf();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BLOCK_IF__CONDITION = eINSTANCE.getBlockIf_Condition();

		/**
		 * The meta object literal for the '<em><b>Else Blocks</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BLOCK_IF__ELSE_BLOCKS = eINSTANCE.getBlockIf_ElseBlocks();

		/**
		 * The meta object literal for the '<em><b>Join Block</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BLOCK_IF__JOIN_BLOCK = eINSTANCE.getBlockIf_JoinBlock();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute BLOCK_IF__LINE_NUMBER = eINSTANCE.getBlockIf_LineNumber();

		/**
		 * The meta object literal for the '<em><b>Then Blocks</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BLOCK_IF__THEN_BLOCKS = eINSTANCE.getBlockIf_ThenBlocks();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.BlockWhileImpl
		 * <em>Block While</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.BlockWhileImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getBlockWhile()
		 * @generated
		 */
		EClass BLOCK_WHILE = eINSTANCE.getBlockWhile();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BLOCK_WHILE__CONDITION = eINSTANCE.getBlockWhile_Condition();

		/**
		 * The meta object literal for the '<em><b>Join Block</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BLOCK_WHILE__JOIN_BLOCK = eINSTANCE.getBlockWhile_JoinBlock();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute BLOCK_WHILE__LINE_NUMBER = eINSTANCE.getBlockWhile_LineNumber();

		/**
		 * The meta object literal for the '<em><b>Blocks</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BLOCK_WHILE__BLOCKS = eINSTANCE.getBlockWhile_Blocks();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.TypeImpl <em>Type</em>
		 * }' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.TypeImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.TypeBoolImpl
		 * <em>Type Bool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.TypeBoolImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getTypeBool()
		 * @generated
		 */
		EClass TYPE_BOOL = eINSTANCE.getTypeBool();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.TypeFloatImpl
		 * <em>Type Float</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.TypeFloatImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getTypeFloat()
		 * @generated
		 */
		EClass TYPE_FLOAT = eINSTANCE.getTypeFloat();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TYPE_FLOAT__SIZE = eINSTANCE.getTypeFloat_Size();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.TypeIntImpl
		 * <em>Type Int</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.TypeIntImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getTypeInt()
		 * @generated
		 */
		EClass TYPE_INT = eINSTANCE.getTypeInt();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TYPE_INT__SIZE = eINSTANCE.getTypeInt_Size();

		/**
		 * The meta object literal for the '<em><b>Signed</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TYPE_INT__SIGNED = eINSTANCE.getTypeInt_Signed();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.TypeStringImpl
		 * <em>Type String</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.TypeStringImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getTypeString()
		 * @generated
		 */
		EClass TYPE_STRING = eINSTANCE.getTypeString();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TYPE_STRING__SIZE = eINSTANCE.getTypeString_Size();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.TypeVoidImpl
		 * <em>Type Void</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.TypeVoidImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getTypeVoid()
		 * @generated
		 */
		EClass TYPE_VOID = eINSTANCE.getTypeVoid();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.UseImpl <em>Use</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.UseImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getUse()
		 * @generated
		 */
		EClass USE = eINSTANCE.getUse();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference USE__VARIABLE = eINSTANCE.getUse_Variable();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.TypeArrayImpl
		 * <em>Type Array</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.TypeArrayImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getTypeArray()
		 * @generated
		 */
		EClass TYPE_ARRAY = eINSTANCE.getTypeArray();

		/**
		 * The meta object literal for the '<em><b>Dimensions</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TYPE_ARRAY__DIMENSIONS = eINSTANCE.getTypeArray_Dimensions();

		/**
		 * The meta object literal for the '<em><b>Element Type</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TYPE_ARRAY__ELEMENT_TYPE = eINSTANCE.getTypeArray_ElementType();

		/**
		 * The meta object literal for the '{@link com.synflow.models.ir.impl.VarImpl <em>Var</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.synflow.models.ir.impl.VarImpl
		 * @see com.synflow.models.ir.impl.IrPackageImpl#getVar()
		 * @generated
		 */
		EClass VAR = eINSTANCE.getVar();

		/**
		 * The meta object literal for the '<em><b>Assignable</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VAR__ASSIGNABLE = eINSTANCE.getVar_Assignable();

		/**
		 * The meta object literal for the '<em><b>Defs</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VAR__DEFS = eINSTANCE.getVar_Defs();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VAR__LINE_NUMBER = eINSTANCE.getVar_LineNumber();

		/**
		 * The meta object literal for the '<em><b>Local</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VAR__LOCAL = eINSTANCE.getVar_Local();

		/**
		 * The meta object literal for the '<em><b>Global</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VAR__GLOBAL = eINSTANCE.getVar_Global();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VAR__INDEX = eINSTANCE.getVar_Index();

		/**
		 * The meta object literal for the '<em><b>Initial Value</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VAR__INITIAL_VALUE = eINSTANCE.getVar_InitialValue();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VAR__NAME = eINSTANCE.getVar_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VAR__TYPE = eINSTANCE.getVar_Type();

		/**
		 * The meta object literal for the '<em><b>Uses</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VAR__USES = eINSTANCE.getVar_Uses();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VAR__VALUE = eINSTANCE.getVar_Value();

	}

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.DefImpl <em>Def</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.DefImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getDef()
	 * @generated
	 */
	int DEF = 31;

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	IrPackage eINSTANCE = com.synflow.models.ir.impl.IrPackageImpl.init();

	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "ir";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "com.synflow.models.ir";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.synflow.com/2013/Ir";

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.ExprBinaryImpl
	 * <em>Expr Binary</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.ExprBinaryImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprBinary()
	 * @generated
	 */
	int EXPR_BINARY = 13;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.ExprBoolImpl <em>Expr Bool</em>
	 * }' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.ExprBoolImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprBool()
	 * @generated
	 */
	int EXPR_BOOL = 14;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.ExprFloatImpl
	 * <em>Expr Float</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.ExprFloatImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprFloat()
	 * @generated
	 */
	int EXPR_FLOAT = 15;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.ExprIntImpl <em>Expr Int</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.ExprIntImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprInt()
	 * @generated
	 */
	int EXPR_INT = 16;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.ExprListImpl <em>Expr List</em>
	 * }' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.ExprListImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprList()
	 * @generated
	 */
	int EXPR_LIST = 17;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.ExprStringImpl
	 * <em>Expr String</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.ExprStringImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprString()
	 * @generated
	 */
	int EXPR_STRING = 18;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.ExprUnaryImpl
	 * <em>Expr Unary</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.ExprUnaryImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprUnary()
	 * @generated
	 */
	int EXPR_UNARY = 22;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.ExprVarImpl <em>Expr Var</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.ExprVarImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprVar()
	 * @generated
	 */
	int EXPR_VAR = 23;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.ExpressionImpl
	 * <em>Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.ExpressionImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getExpression()
	 * @generated
	 */
	int EXPRESSION = 12;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.InstructionImpl
	 * <em>Instruction</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.InstructionImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getInstruction()
	 * @generated
	 */
	int INSTRUCTION = 5;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.InstAssignImpl
	 * <em>Inst Assign</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.InstAssignImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getInstAssign()
	 * @generated
	 */
	int INST_ASSIGN = 6;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.InstCallImpl <em>Inst Call</em>
	 * }' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.InstCallImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getInstCall()
	 * @generated
	 */
	int INST_CALL = 7;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.InstLoadImpl <em>Inst Load</em>
	 * }' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.InstLoadImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getInstLoad()
	 * @generated
	 */
	int INST_LOAD = 8;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.InstPhiImpl <em>Inst Phi</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.InstPhiImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getInstPhi()
	 * @generated
	 */
	int INST_PHI = 9;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.InstReturnImpl
	 * <em>Inst Return</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.InstReturnImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getInstReturn()
	 * @generated
	 */
	int INST_RETURN = 10;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.InstStoreImpl
	 * <em>Inst Store</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.InstStoreImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getInstStore()
	 * @generated
	 */
	int INST_STORE = 11;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.OpBinary <em>Op Binary</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.OpBinary
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getOpBinary()
	 * @generated
	 */
	int OP_BINARY = 34;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.OpUnary <em>Op Unary</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.OpUnary
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getOpUnary()
	 * @generated
	 */
	int OP_UNARY = 35;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.ProcedureImpl
	 * <em>Procedure</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.ProcedureImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getProcedure()
	 * @generated
	 */
	int PROCEDURE = 0;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__LINE_NUMBER = 0;

	/**
	 * The feature id for the '<em><b>Locals</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__LOCALS = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__NAME = 2;

	/**
	 * The feature id for the '<em><b>Blocks</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__BLOCKS = 3;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__PARAMETERS = 4;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__RETURN_TYPE = 5;

	/**
	 * The number of structural features of the '<em>Procedure</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROCEDURE_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.BlockImpl <em>Block</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.BlockImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getBlock()
	 * @generated
	 */
	int BLOCK = 1;

	/**
	 * The number of structural features of the '<em>Block</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.BlockBasicImpl
	 * <em>Block Basic</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.BlockBasicImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getBlockBasic()
	 * @generated
	 */
	int BLOCK_BASIC = 2;

	/**
	 * The feature id for the '<em><b>Instructions</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_BASIC__INSTRUCTIONS = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Block Basic</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_BASIC_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.BlockIfImpl <em>Block If</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.BlockIfImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getBlockIf()
	 * @generated
	 */
	int BLOCK_IF = 3;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_IF__CONDITION = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Else Blocks</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_IF__ELSE_BLOCKS = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Join Block</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_IF__JOIN_BLOCK = BLOCK_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_IF__LINE_NUMBER = BLOCK_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Then Blocks</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_IF__THEN_BLOCKS = BLOCK_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Block If</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_IF_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.BlockWhileImpl
	 * <em>Block While</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.BlockWhileImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getBlockWhile()
	 * @generated
	 */
	int BLOCK_WHILE = 4;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_WHILE__CONDITION = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Block</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_WHILE__JOIN_BLOCK = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_WHILE__LINE_NUMBER = BLOCK_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Blocks</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_WHILE__BLOCKS = BLOCK_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Block While</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_WHILE_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INSTRUCTION__LINE_NUMBER = 0;

	/**
	 * The number of structural features of the '<em>Instruction</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INSTRUCTION_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN__LINE_NUMBER = INSTRUCTION__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN__TARGET = INSTRUCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN__VALUE = INSTRUCTION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Inst Assign</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN_FEATURE_COUNT = INSTRUCTION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_CALL__LINE_NUMBER = INSTRUCTION__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_CALL__ARGUMENTS = INSTRUCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Procedure</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_CALL__PROCEDURE = INSTRUCTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_CALL__TARGET = INSTRUCTION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Print</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_CALL__PRINT = INSTRUCTION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Assert</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_CALL__ASSERT = INSTRUCTION_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Inst Call</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_CALL_FEATURE_COUNT = INSTRUCTION_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_LOAD__LINE_NUMBER = INSTRUCTION__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_LOAD__INDEXES = INSTRUCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_LOAD__SOURCE = INSTRUCTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_LOAD__TARGET = INSTRUCTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Inst Load</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_LOAD_FEATURE_COUNT = INSTRUCTION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_PHI__LINE_NUMBER = INSTRUCTION__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Old Variable</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_PHI__OLD_VARIABLE = INSTRUCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_PHI__TARGET = INSTRUCTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_PHI__VALUES = INSTRUCTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Inst Phi</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_PHI_FEATURE_COUNT = INSTRUCTION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_RETURN__LINE_NUMBER = INSTRUCTION__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_RETURN__VALUE = INSTRUCTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Inst Return</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_RETURN_FEATURE_COUNT = INSTRUCTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_STORE__LINE_NUMBER = INSTRUCTION__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_STORE__INDEXES = INSTRUCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_STORE__TARGET = INSTRUCTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_STORE__VALUE = INSTRUCTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Inst Store</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_STORE_FEATURE_COUNT = INSTRUCTION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Computed Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__COMPUTED_TYPE = 0;

	/**
	 * The number of structural features of the '<em>Expression</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Computed Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_BINARY__COMPUTED_TYPE = EXPRESSION__COMPUTED_TYPE;

	/**
	 * The feature id for the '<em><b>E1</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_BINARY__E1 = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>E2</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_BINARY__E2 = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_BINARY__OP = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Expr Binary</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_BINARY_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Computed Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_BOOL__COMPUTED_TYPE = EXPRESSION__COMPUTED_TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_BOOL__VALUE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expr Bool</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_BOOL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Computed Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_FLOAT__COMPUTED_TYPE = EXPRESSION__COMPUTED_TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_FLOAT__VALUE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expr Float</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_FLOAT_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Computed Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_INT__COMPUTED_TYPE = EXPRESSION__COMPUTED_TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_INT__VALUE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expr Int</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_INT_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Computed Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_LIST__COMPUTED_TYPE = EXPRESSION__COMPUTED_TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_LIST__VALUE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expr List</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_LIST_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Computed Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_STRING__COMPUTED_TYPE = EXPRESSION__COMPUTED_TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_STRING__VALUE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expr String</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_STRING_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.ExprResizeImpl
	 * <em>Expr Resize</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.ExprResizeImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprResize()
	 * @generated
	 */
	int EXPR_RESIZE = 19;

	/**
	 * The feature id for the '<em><b>Computed Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_RESIZE__COMPUTED_TYPE = EXPRESSION__COMPUTED_TYPE;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_RESIZE__EXPR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target Size</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_RESIZE__TARGET_SIZE = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Expr Resize</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_RESIZE_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.ExprTernaryImpl
	 * <em>Expr Ternary</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.ExprTernaryImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprTernary()
	 * @generated
	 */
	int EXPR_TERNARY = 20;

	/**
	 * The feature id for the '<em><b>Computed Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_TERNARY__COMPUTED_TYPE = EXPRESSION__COMPUTED_TYPE;

	/**
	 * The feature id for the '<em><b>Cond</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_TERNARY__COND = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>E1</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_TERNARY__E1 = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>E2</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_TERNARY__E2 = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Expr Ternary</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_TERNARY_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.ExprTypeConvImpl
	 * <em>Expr Type Conv</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.ExprTypeConvImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getExprTypeConv()
	 * @generated
	 */
	int EXPR_TYPE_CONV = 21;

	/**
	 * The feature id for the '<em><b>Computed Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_TYPE_CONV__COMPUTED_TYPE = EXPRESSION__COMPUTED_TYPE;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_TYPE_CONV__EXPR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_TYPE_CONV__TYPE_NAME = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Expr Type Conv</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_TYPE_CONV_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Computed Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_UNARY__COMPUTED_TYPE = EXPRESSION__COMPUTED_TYPE;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_UNARY__EXPR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_UNARY__OP = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Expr Unary</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_UNARY_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Computed Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_VAR__COMPUTED_TYPE = EXPRESSION__COMPUTED_TYPE;

	/**
	 * The feature id for the '<em><b>Use</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_VAR__USE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expr Var</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_VAR_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.TypeImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 24;

	/**
	 * The number of structural features of the '<em>Type</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.TypeBoolImpl <em>Type Bool</em>
	 * }' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.TypeBoolImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getTypeBool()
	 * @generated
	 */
	int TYPE_BOOL = 26;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.TypeFloatImpl
	 * <em>Type Float</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.TypeFloatImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getTypeFloat()
	 * @generated
	 */
	int TYPE_FLOAT = 27;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.TypeIntImpl <em>Type Int</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.TypeIntImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getTypeInt()
	 * @generated
	 */
	int TYPE_INT = 28;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.TypeStringImpl
	 * <em>Type String</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.TypeStringImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getTypeString()
	 * @generated
	 */
	int TYPE_STRING = 29;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.TypeVoidImpl <em>Type Void</em>
	 * }' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.TypeVoidImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getTypeVoid()
	 * @generated
	 */
	int TYPE_VOID = 30;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.UseImpl <em>Use</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.UseImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getUse()
	 * @generated
	 */
	int USE = 33;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.VarImpl <em>Var</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.VarImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getVar()
	 * @generated
	 */
	int VAR = 32;

	/**
	 * The meta object id for the '{@link com.synflow.models.ir.impl.TypeArrayImpl
	 * <em>Type Array</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.synflow.models.ir.impl.TypeArrayImpl
	 * @see com.synflow.models.ir.impl.IrPackageImpl#getTypeArray()
	 * @generated
	 */
	int TYPE_ARRAY = 25;

	/**
	 * The feature id for the '<em><b>Dimensions</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_ARRAY__DIMENSIONS = TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_ARRAY__ELEMENT_TYPE = TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Type Array</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_ARRAY_FEATURE_COUNT = TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Type Bool</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_BOOL_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_FLOAT__SIZE = TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Float</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_FLOAT_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Signed</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__SIGNED = TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__SIZE = TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Type Int</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_INT_FEATURE_COUNT = TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING__SIZE = TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type String</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Type Void</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_VOID_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEF__VARIABLE = 0;

	/**
	 * The number of structural features of the '<em>Def</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEF_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Assignable</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__ASSIGNABLE = 0;

	/**
	 * The feature id for the '<em><b>Defs</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__DEFS = 1;

	/**
	 * The feature id for the '<em><b>Global</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__GLOBAL = 2;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__INDEX = 3;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__INITIAL_VALUE = 4;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__LINE_NUMBER = 5;

	/**
	 * The feature id for the '<em><b>Local</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__LOCAL = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__NAME = 7;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__TYPE = 8;

	/**
	 * The feature id for the '<em><b>Uses</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__USES = 9;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__VALUE = 10;

	/**
	 * The number of structural features of the '<em>Var</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR_FEATURE_COUNT = 11;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USE__VARIABLE = 0;

	/**
	 * The number of structural features of the '<em>Use</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USE_FEATURE_COUNT = 1;

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.Def <em>Def</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Def</em>'.
	 * @see com.synflow.models.ir.Def
	 * @generated
	 */
	EClass getDef();

	/**
	 * Returns the meta object for the reference '{@link com.synflow.models.ir.Def#getVariable
	 * <em>Variable</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see com.synflow.models.ir.Def#getVariable()
	 * @see #getDef()
	 * @generated
	 */
	EReference getDef_Variable();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.ExprBinary
	 * <em>Expr Binary</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expr Binary</em>'.
	 * @see com.synflow.models.ir.ExprBinary
	 * @generated
	 */
	EClass getExprBinary();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.ExprBinary#getE1 <em>E1</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>E1</em>'.
	 * @see com.synflow.models.ir.ExprBinary#getE1()
	 * @see #getExprBinary()
	 * @generated
	 */
	EReference getExprBinary_E1();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.ExprBinary#getE2 <em>E2</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>E2</em>'.
	 * @see com.synflow.models.ir.ExprBinary#getE2()
	 * @see #getExprBinary()
	 * @generated
	 */
	EReference getExprBinary_E2();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.ExprBinary#getOp
	 * <em>Op</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see com.synflow.models.ir.ExprBinary#getOp()
	 * @see #getExprBinary()
	 * @generated
	 */
	EAttribute getExprBinary_Op();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.ExprBool <em>Expr Bool</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expr Bool</em>'.
	 * @see com.synflow.models.ir.ExprBool
	 * @generated
	 */
	EClass getExprBool();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.ExprBool#isValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.synflow.models.ir.ExprBool#isValue()
	 * @see #getExprBool()
	 * @generated
	 */
	EAttribute getExprBool_Value();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.Expression
	 * <em>Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see com.synflow.models.ir.Expression
	 * @generated
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.Expression#getComputedType <em>Computed Type</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Computed Type</em>'.
	 * @see com.synflow.models.ir.Expression#getComputedType()
	 * @see #getExpression()
	 * @generated
	 */
	EReference getExpression_ComputedType();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.ExprFloat <em>Expr Float</em>
	 * }'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expr Float</em>'.
	 * @see com.synflow.models.ir.ExprFloat
	 * @generated
	 */
	EClass getExprFloat();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.ExprFloat#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.synflow.models.ir.ExprFloat#getValue()
	 * @see #getExprFloat()
	 * @generated
	 */
	EAttribute getExprFloat_Value();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.ExprInt <em>Expr Int</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expr Int</em>'.
	 * @see com.synflow.models.ir.ExprInt
	 * @generated
	 */
	EClass getExprInt();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.ExprInt#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.synflow.models.ir.ExprInt#getValue()
	 * @see #getExprInt()
	 * @generated
	 */
	EAttribute getExprInt_Value();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.ExprList <em>Expr List</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expr List</em>'.
	 * @see com.synflow.models.ir.ExprList
	 * @generated
	 */
	EClass getExprList();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.ir.ExprList#getValue <em>Value</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Value</em>'.
	 * @see com.synflow.models.ir.ExprList#getValue()
	 * @see #getExprList()
	 * @generated
	 */
	EReference getExprList_Value();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.ExprString
	 * <em>Expr String</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expr String</em>'.
	 * @see com.synflow.models.ir.ExprString
	 * @generated
	 */
	EClass getExprString();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.ExprString#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.synflow.models.ir.ExprString#getValue()
	 * @see #getExprString()
	 * @generated
	 */
	EAttribute getExprString_Value();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.ExprResize
	 * <em>Expr Resize</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expr Resize</em>'.
	 * @see com.synflow.models.ir.ExprResize
	 * @generated
	 */
	EClass getExprResize();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.ExprResize#getExpr <em>Expr</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see com.synflow.models.ir.ExprResize#getExpr()
	 * @see #getExprResize()
	 * @generated
	 */
	EReference getExprResize_Expr();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.synflow.models.ir.ExprResize#getTargetSize <em>Target Size</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Target Size</em>'.
	 * @see com.synflow.models.ir.ExprResize#getTargetSize()
	 * @see #getExprResize()
	 * @generated
	 */
	EAttribute getExprResize_TargetSize();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.ExprTernary
	 * <em>Expr Ternary</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expr Ternary</em>'.
	 * @see com.synflow.models.ir.ExprTernary
	 * @generated
	 */
	EClass getExprTernary();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.ExprTernary#getCond <em>Cond</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Cond</em>'.
	 * @see com.synflow.models.ir.ExprTernary#getCond()
	 * @see #getExprTernary()
	 * @generated
	 */
	EReference getExprTernary_Cond();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.ExprTernary#getE1 <em>E1</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>E1</em>'.
	 * @see com.synflow.models.ir.ExprTernary#getE1()
	 * @see #getExprTernary()
	 * @generated
	 */
	EReference getExprTernary_E1();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.ExprTernary#getE2 <em>E2</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>E2</em>'.
	 * @see com.synflow.models.ir.ExprTernary#getE2()
	 * @see #getExprTernary()
	 * @generated
	 */
	EReference getExprTernary_E2();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.ExprTypeConv
	 * <em>Expr Type Conv</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expr Type Conv</em>'.
	 * @see com.synflow.models.ir.ExprTypeConv
	 * @generated
	 */
	EClass getExprTypeConv();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.ExprTypeConv#getExpr <em>Expr</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see com.synflow.models.ir.ExprTypeConv#getExpr()
	 * @see #getExprTypeConv()
	 * @generated
	 */
	EReference getExprTypeConv_Expr();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.synflow.models.ir.ExprTypeConv#getTypeName <em>Type Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type Name</em>'.
	 * @see com.synflow.models.ir.ExprTypeConv#getTypeName()
	 * @see #getExprTypeConv()
	 * @generated
	 */
	EAttribute getExprTypeConv_TypeName();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.ExprUnary <em>Expr Unary</em>
	 * }'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expr Unary</em>'.
	 * @see com.synflow.models.ir.ExprUnary
	 * @generated
	 */
	EClass getExprUnary();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.ExprUnary#getExpr <em>Expr</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see com.synflow.models.ir.ExprUnary#getExpr()
	 * @see #getExprUnary()
	 * @generated
	 */
	EReference getExprUnary_Expr();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.ExprUnary#getOp
	 * <em>Op</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see com.synflow.models.ir.ExprUnary#getOp()
	 * @see #getExprUnary()
	 * @generated
	 */
	EAttribute getExprUnary_Op();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.ExprVar <em>Expr Var</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expr Var</em>'.
	 * @see com.synflow.models.ir.ExprVar
	 * @generated
	 */
	EClass getExprVar();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.ExprVar#getUse <em>Use</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Use</em>'.
	 * @see com.synflow.models.ir.ExprVar#getUse()
	 * @see #getExprVar()
	 * @generated
	 */
	EReference getExprVar_Use();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.InstAssign
	 * <em>Inst Assign</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Inst Assign</em>'.
	 * @see com.synflow.models.ir.InstAssign
	 * @generated
	 */
	EClass getInstAssign();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.InstAssign#getTarget <em>Target</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see com.synflow.models.ir.InstAssign#getTarget()
	 * @see #getInstAssign()
	 * @generated
	 */
	EReference getInstAssign_Target();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.InstAssign#getValue <em>Value</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see com.synflow.models.ir.InstAssign#getValue()
	 * @see #getInstAssign()
	 * @generated
	 */
	EReference getInstAssign_Value();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.InstCall <em>Inst Call</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Inst Call</em>'.
	 * @see com.synflow.models.ir.InstCall
	 * @generated
	 */
	EClass getInstCall();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.ir.InstCall#getArguments <em>Arguments</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Arguments</em>'.
	 * @see com.synflow.models.ir.InstCall#getArguments()
	 * @see #getInstCall()
	 * @generated
	 */
	EReference getInstCall_Arguments();

	/**
	 * Returns the meta object for the reference '{@link com.synflow.models.ir.InstCall#getProcedure
	 * <em>Procedure</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Procedure</em>'.
	 * @see com.synflow.models.ir.InstCall#getProcedure()
	 * @see #getInstCall()
	 * @generated
	 */
	EReference getInstCall_Procedure();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.InstCall#getTarget <em>Target</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see com.synflow.models.ir.InstCall#getTarget()
	 * @see #getInstCall()
	 * @generated
	 */
	EReference getInstCall_Target();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.InstCall#isPrint
	 * <em>Print</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Print</em>'.
	 * @see com.synflow.models.ir.InstCall#isPrint()
	 * @see #getInstCall()
	 * @generated
	 */
	EAttribute getInstCall_Print();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.InstCall#isAssert
	 * <em>Assert</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Assert</em>'.
	 * @see com.synflow.models.ir.InstCall#isAssert()
	 * @see #getInstCall()
	 * @generated
	 */
	EAttribute getInstCall_Assert();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.InstLoad <em>Inst Load</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Inst Load</em>'.
	 * @see com.synflow.models.ir.InstLoad
	 * @generated
	 */
	EClass getInstLoad();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.ir.InstLoad#getIndexes <em>Indexes</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Indexes</em>'.
	 * @see com.synflow.models.ir.InstLoad#getIndexes()
	 * @see #getInstLoad()
	 * @generated
	 */
	EReference getInstLoad_Indexes();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.InstLoad#getSource <em>Source</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Source</em>'.
	 * @see com.synflow.models.ir.InstLoad#getSource()
	 * @see #getInstLoad()
	 * @generated
	 */
	EReference getInstLoad_Source();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.InstLoad#getTarget <em>Target</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see com.synflow.models.ir.InstLoad#getTarget()
	 * @see #getInstLoad()
	 * @generated
	 */
	EReference getInstLoad_Target();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.InstPhi <em>Inst Phi</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Inst Phi</em>'.
	 * @see com.synflow.models.ir.InstPhi
	 * @generated
	 */
	EClass getInstPhi();

	/**
	 * Returns the meta object for the reference '
	 * {@link com.synflow.models.ir.InstPhi#getOldVariable <em>Old Variable</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Old Variable</em>'.
	 * @see com.synflow.models.ir.InstPhi#getOldVariable()
	 * @see #getInstPhi()
	 * @generated
	 */
	EReference getInstPhi_OldVariable();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.InstPhi#getTarget <em>Target</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see com.synflow.models.ir.InstPhi#getTarget()
	 * @see #getInstPhi()
	 * @generated
	 */
	EReference getInstPhi_Target();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.ir.InstPhi#getValues <em>Values</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see com.synflow.models.ir.InstPhi#getValues()
	 * @see #getInstPhi()
	 * @generated
	 */
	EReference getInstPhi_Values();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.InstReturn
	 * <em>Inst Return</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Inst Return</em>'.
	 * @see com.synflow.models.ir.InstReturn
	 * @generated
	 */
	EClass getInstReturn();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.InstReturn#getValue <em>Value</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see com.synflow.models.ir.InstReturn#getValue()
	 * @see #getInstReturn()
	 * @generated
	 */
	EReference getInstReturn_Value();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.Instruction
	 * <em>Instruction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Instruction</em>'.
	 * @see com.synflow.models.ir.Instruction
	 * @generated
	 */
	EClass getInstruction();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.synflow.models.ir.Instruction#getLineNumber <em>Line Number</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see com.synflow.models.ir.Instruction#getLineNumber()
	 * @see #getInstruction()
	 * @generated
	 */
	EAttribute getInstruction_LineNumber();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.InstStore <em>Inst Store</em>
	 * }'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Inst Store</em>'.
	 * @see com.synflow.models.ir.InstStore
	 * @generated
	 */
	EClass getInstStore();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.ir.InstStore#getIndexes <em>Indexes</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Indexes</em>'.
	 * @see com.synflow.models.ir.InstStore#getIndexes()
	 * @see #getInstStore()
	 * @generated
	 */
	EReference getInstStore_Indexes();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.InstStore#getTarget <em>Target</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see com.synflow.models.ir.InstStore#getTarget()
	 * @see #getInstStore()
	 * @generated
	 */
	EReference getInstStore_Target();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.InstStore#getValue <em>Value</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see com.synflow.models.ir.InstStore#getValue()
	 * @see #getInstStore()
	 * @generated
	 */
	EReference getInstStore_Value();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IrFactory getIrFactory();

	/**
	 * Returns the meta object for enum '{@link com.synflow.models.ir.OpBinary <em>Op Binary</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Op Binary</em>'.
	 * @see com.synflow.models.ir.OpBinary
	 * @generated
	 */
	EEnum getOpBinary();

	/**
	 * Returns the meta object for enum '{@link com.synflow.models.ir.OpUnary <em>Op Unary</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Op Unary</em>'.
	 * @see com.synflow.models.ir.OpUnary
	 * @generated
	 */
	EEnum getOpUnary();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.Procedure <em>Procedure</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Procedure</em>'.
	 * @see com.synflow.models.ir.Procedure
	 * @generated
	 */
	EClass getProcedure();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.ir.Procedure#getLocals <em>Locals</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Locals</em>'.
	 * @see com.synflow.models.ir.Procedure#getLocals()
	 * @see #getProcedure()
	 * @generated
	 */
	EReference getProcedure_Locals();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.Procedure#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.synflow.models.ir.Procedure#getName()
	 * @see #getProcedure()
	 * @generated
	 */
	EAttribute getProcedure_Name();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.ir.Procedure#getBlocks <em>Blocks</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Blocks</em>'.
	 * @see com.synflow.models.ir.Procedure#getBlocks()
	 * @see #getProcedure()
	 * @generated
	 */
	EReference getProcedure_Blocks();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.ir.Procedure#getParameters <em>Parameters</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see com.synflow.models.ir.Procedure#getParameters()
	 * @see #getProcedure()
	 * @generated
	 */
	EReference getProcedure_Parameters();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.synflow.models.ir.Procedure#getLineNumber <em>Line Number</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see com.synflow.models.ir.Procedure#getLineNumber()
	 * @see #getProcedure()
	 * @generated
	 */
	EAttribute getProcedure_LineNumber();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.Procedure#getReturnType <em>Return Type</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Return Type</em>'.
	 * @see com.synflow.models.ir.Procedure#getReturnType()
	 * @see #getProcedure()
	 * @generated
	 */
	EReference getProcedure_ReturnType();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.Block <em>Block</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Block</em>'.
	 * @see com.synflow.models.ir.Block
	 * @generated
	 */
	EClass getBlock();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.BlockBasic
	 * <em>Block Basic</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Block Basic</em>'.
	 * @see com.synflow.models.ir.BlockBasic
	 * @generated
	 */
	EClass getBlockBasic();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.ir.BlockBasic#getInstructions <em>Instructions</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Instructions</em>'.
	 * @see com.synflow.models.ir.BlockBasic#getInstructions()
	 * @see #getBlockBasic()
	 * @generated
	 */
	EReference getBlockBasic_Instructions();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.BlockIf <em>Block If</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Block If</em>'.
	 * @see com.synflow.models.ir.BlockIf
	 * @generated
	 */
	EClass getBlockIf();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.BlockIf#getCondition <em>Condition</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see com.synflow.models.ir.BlockIf#getCondition()
	 * @see #getBlockIf()
	 * @generated
	 */
	EReference getBlockIf_Condition();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.ir.BlockIf#getElseBlocks <em>Else Blocks</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Else Blocks</em>'.
	 * @see com.synflow.models.ir.BlockIf#getElseBlocks()
	 * @see #getBlockIf()
	 * @generated
	 */
	EReference getBlockIf_ElseBlocks();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.BlockIf#getJoinBlock <em>Join Block</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Join Block</em>'.
	 * @see com.synflow.models.ir.BlockIf#getJoinBlock()
	 * @see #getBlockIf()
	 * @generated
	 */
	EReference getBlockIf_JoinBlock();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.synflow.models.ir.BlockIf#getLineNumber <em>Line Number</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see com.synflow.models.ir.BlockIf#getLineNumber()
	 * @see #getBlockIf()
	 * @generated
	 */
	EAttribute getBlockIf_LineNumber();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.ir.BlockIf#getThenBlocks <em>Then Blocks</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Then Blocks</em>'.
	 * @see com.synflow.models.ir.BlockIf#getThenBlocks()
	 * @see #getBlockIf()
	 * @generated
	 */
	EReference getBlockIf_ThenBlocks();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.BlockWhile
	 * <em>Block While</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Block While</em>'.
	 * @see com.synflow.models.ir.BlockWhile
	 * @generated
	 */
	EClass getBlockWhile();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.BlockWhile#getCondition <em>Condition</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see com.synflow.models.ir.BlockWhile#getCondition()
	 * @see #getBlockWhile()
	 * @generated
	 */
	EReference getBlockWhile_Condition();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.BlockWhile#getJoinBlock <em>Join Block</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Join Block</em>'.
	 * @see com.synflow.models.ir.BlockWhile#getJoinBlock()
	 * @see #getBlockWhile()
	 * @generated
	 */
	EReference getBlockWhile_JoinBlock();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.synflow.models.ir.BlockWhile#getLineNumber <em>Line Number</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see com.synflow.models.ir.BlockWhile#getLineNumber()
	 * @see #getBlockWhile()
	 * @generated
	 */
	EAttribute getBlockWhile_LineNumber();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.ir.BlockWhile#getBlocks <em>Blocks</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Blocks</em>'.
	 * @see com.synflow.models.ir.BlockWhile#getBlocks()
	 * @see #getBlockWhile()
	 * @generated
	 */
	EReference getBlockWhile_Blocks();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.Type <em>Type</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type</em>'.
	 * @see com.synflow.models.ir.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.TypeBool <em>Type Bool</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type Bool</em>'.
	 * @see com.synflow.models.ir.TypeBool
	 * @generated
	 */
	EClass getTypeBool();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.TypeFloat <em>Type Float</em>
	 * }'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type Float</em>'.
	 * @see com.synflow.models.ir.TypeFloat
	 * @generated
	 */
	EClass getTypeFloat();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.TypeFloat#getSize
	 * <em>Size</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see com.synflow.models.ir.TypeFloat#getSize()
	 * @see #getTypeFloat()
	 * @generated
	 */
	EAttribute getTypeFloat_Size();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.Type <em>Type</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type</em>'.
	 * @see com.synflow.models.ir.Type
	 * @generated
	 */
	EClass getTypeGen();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.TypeInt <em>Type Int</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type Int</em>'.
	 * @see com.synflow.models.ir.TypeInt
	 * @generated
	 */
	EClass getTypeInt();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.TypeInt#getSize
	 * <em>Size</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see com.synflow.models.ir.TypeInt#getSize()
	 * @see #getTypeInt()
	 * @generated
	 */
	EAttribute getTypeInt_Size();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.TypeInt#isSigned
	 * <em>Signed</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Signed</em>'.
	 * @see com.synflow.models.ir.TypeInt#isSigned()
	 * @see #getTypeInt()
	 * @generated
	 */
	EAttribute getTypeInt_Signed();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.TypeString
	 * <em>Type String</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type String</em>'.
	 * @see com.synflow.models.ir.TypeString
	 * @generated
	 */
	EClass getTypeString();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.TypeString#getSize
	 * <em>Size</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see com.synflow.models.ir.TypeString#getSize()
	 * @see #getTypeString()
	 * @generated
	 */
	EAttribute getTypeString_Size();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.TypeVoid <em>Type Void</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type Void</em>'.
	 * @see com.synflow.models.ir.TypeVoid
	 * @generated
	 */
	EClass getTypeVoid();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.Use <em>Use</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Use</em>'.
	 * @see com.synflow.models.ir.Use
	 * @generated
	 */
	EClass getUse();

	/**
	 * Returns the meta object for the reference '{@link com.synflow.models.ir.Use#getVariable
	 * <em>Variable</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see com.synflow.models.ir.Use#getVariable()
	 * @see #getUse()
	 * @generated
	 */
	EReference getUse_Variable();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.TypeArray <em>Type Array</em>
	 * }'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type Array</em>'.
	 * @see com.synflow.models.ir.TypeArray
	 * @generated
	 */
	EClass getTypeArray();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.synflow.models.ir.TypeArray#getDimensions <em>Dimensions</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Dimensions</em>'.
	 * @see com.synflow.models.ir.TypeArray#getDimensions()
	 * @see #getTypeArray()
	 * @generated
	 */
	EAttribute getTypeArray_Dimensions();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.TypeArray#getElementType <em>Element Type</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Element Type</em>'.
	 * @see com.synflow.models.ir.TypeArray#getElementType()
	 * @see #getTypeArray()
	 * @generated
	 */
	EReference getTypeArray_ElementType();

	/**
	 * Returns the meta object for class '{@link com.synflow.models.ir.Var <em>Var</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Var</em>'.
	 * @see com.synflow.models.ir.Var
	 * @generated
	 */
	EClass getVar();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.Var#isAssignable
	 * <em>Assignable</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Assignable</em>'.
	 * @see com.synflow.models.ir.Var#isAssignable()
	 * @see #getVar()
	 * @generated
	 */
	EAttribute getVar_Assignable();

	/**
	 * Returns the meta object for the reference list '{@link com.synflow.models.ir.Var#getDefs
	 * <em>Defs</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Defs</em>'.
	 * @see com.synflow.models.ir.Var#getDefs()
	 * @see #getVar()
	 * @generated
	 */
	EReference getVar_Defs();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.Var#getLineNumber
	 * <em>Line Number</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see com.synflow.models.ir.Var#getLineNumber()
	 * @see #getVar()
	 * @generated
	 */
	EAttribute getVar_LineNumber();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.Var#isLocal
	 * <em>Local</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Local</em>'.
	 * @see com.synflow.models.ir.Var#isLocal()
	 * @see #getVar()
	 * @generated
	 */
	EAttribute getVar_Local();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.Var#isGlobal
	 * <em>Global</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Global</em>'.
	 * @see com.synflow.models.ir.Var#isGlobal()
	 * @see #getVar()
	 * @generated
	 */
	EAttribute getVar_Global();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.Var#getIndex
	 * <em>Index</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see com.synflow.models.ir.Var#getIndex()
	 * @see #getVar()
	 * @generated
	 */
	EAttribute getVar_Index();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.Var#getInitialValue <em>Initial Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Initial Value</em>'.
	 * @see com.synflow.models.ir.Var#getInitialValue()
	 * @see #getVar()
	 * @generated
	 */
	EReference getVar_InitialValue();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.Var#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.synflow.models.ir.Var#getName()
	 * @see #getVar()
	 * @generated
	 */
	EAttribute getVar_Name();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link com.synflow.models.ir.Var#getType <em>Type</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see com.synflow.models.ir.Var#getType()
	 * @see #getVar()
	 * @generated
	 */
	EReference getVar_Type();

	/**
	 * Returns the meta object for the reference list '{@link com.synflow.models.ir.Var#getUses
	 * <em>Uses</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Uses</em>'.
	 * @see com.synflow.models.ir.Var#getUses()
	 * @see #getVar()
	 * @generated
	 */
	EReference getVar_Uses();

	/**
	 * Returns the meta object for the attribute '{@link com.synflow.models.ir.Var#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.synflow.models.ir.Var#getValue()
	 * @see #getVar()
	 * @generated
	 */
	EAttribute getVar_Value();

} // IrPackage
