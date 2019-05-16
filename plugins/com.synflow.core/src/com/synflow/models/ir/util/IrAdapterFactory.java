/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.ir.util;

import com.synflow.models.ir.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model. <!-- end-user-doc -->
 * 
 * @see com.synflow.models.ir.IrPackage
 * @generated
 */
public class IrAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static IrPackage modelPackage;

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected IrSwitch<Adapter> modelSwitch = new IrSwitch<Adapter>() {
		@Override
		public Adapter caseProcedure(Procedure object) {
			return createProcedureAdapter();
		}

		@Override
		public Adapter caseBlock(Block object) {
			return createBlockAdapter();
		}

		@Override
		public Adapter caseBlockBasic(BlockBasic object) {
			return createBlockBasicAdapter();
		}

		@Override
		public Adapter caseBlockIf(BlockIf object) {
			return createBlockIfAdapter();
		}

		@Override
		public Adapter caseBlockWhile(BlockWhile object) {
			return createBlockWhileAdapter();
		}

		@Override
		public Adapter caseInstruction(Instruction object) {
			return createInstructionAdapter();
		}

		@Override
		public Adapter caseInstAssign(InstAssign object) {
			return createInstAssignAdapter();
		}

		@Override
		public Adapter caseInstCall(InstCall object) {
			return createInstCallAdapter();
		}

		@Override
		public Adapter caseInstLoad(InstLoad object) {
			return createInstLoadAdapter();
		}

		@Override
		public Adapter caseInstPhi(InstPhi object) {
			return createInstPhiAdapter();
		}

		@Override
		public Adapter caseInstReturn(InstReturn object) {
			return createInstReturnAdapter();
		}

		@Override
		public Adapter caseInstStore(InstStore object) {
			return createInstStoreAdapter();
		}

		@Override
		public Adapter caseExpression(Expression object) {
			return createExpressionAdapter();
		}

		@Override
		public Adapter caseExprBinary(ExprBinary object) {
			return createExprBinaryAdapter();
		}

		@Override
		public Adapter caseExprBool(ExprBool object) {
			return createExprBoolAdapter();
		}

		@Override
		public Adapter caseExprFloat(ExprFloat object) {
			return createExprFloatAdapter();
		}

		@Override
		public Adapter caseExprInt(ExprInt object) {
			return createExprIntAdapter();
		}

		@Override
		public Adapter caseExprList(ExprList object) {
			return createExprListAdapter();
		}

		@Override
		public Adapter caseExprString(ExprString object) {
			return createExprStringAdapter();
		}

		@Override
		public Adapter caseExprResize(ExprResize object) {
			return createExprResizeAdapter();
		}

		@Override
		public Adapter caseExprTernary(ExprTernary object) {
			return createExprTernaryAdapter();
		}

		@Override
		public Adapter caseExprTypeConv(ExprTypeConv object) {
			return createExprTypeConvAdapter();
		}

		@Override
		public Adapter caseExprUnary(ExprUnary object) {
			return createExprUnaryAdapter();
		}

		@Override
		public Adapter caseExprVar(ExprVar object) {
			return createExprVarAdapter();
		}

		@Override
		public Adapter caseType(Type object) {
			return createTypeAdapter();
		}

		@Override
		public Adapter caseTypeArray(TypeArray object) {
			return createTypeArrayAdapter();
		}

		@Override
		public Adapter caseTypeBool(TypeBool object) {
			return createTypeBoolAdapter();
		}

		@Override
		public Adapter caseTypeFloat(TypeFloat object) {
			return createTypeFloatAdapter();
		}

		@Override
		public Adapter caseTypeInt(TypeInt object) {
			return createTypeIntAdapter();
		}

		@Override
		public Adapter caseTypeString(TypeString object) {
			return createTypeStringAdapter();
		}

		@Override
		public Adapter caseTypeVoid(TypeVoid object) {
			return createTypeVoidAdapter();
		}

		@Override
		public Adapter caseDef(Def object) {
			return createDefAdapter();
		}

		@Override
		public Adapter caseVar(Var object) {
			return createVarAdapter();
		}

		@Override
		public Adapter caseUse(Use object) {
			return createUseAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IrAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = IrPackage.eINSTANCE;
		}
	}

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.Def <em>Def</em>}
	 * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.Def
	 * @generated
	 */
	public Adapter createDefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This default
	 * implementation returns null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.ExprBinary
	 * <em>Expr Binary</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.ExprBinary
	 * @generated
	 */
	public Adapter createExprBinaryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.ExprBool
	 * <em>Expr Bool</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.ExprBool
	 * @generated
	 */
	public Adapter createExprBoolAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.Expression
	 * <em>Expression</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.Expression
	 * @generated
	 */
	public Adapter createExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.ExprFloat
	 * <em>Expr Float</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.ExprFloat
	 * @generated
	 */
	public Adapter createExprFloatAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.ExprInt
	 * <em>Expr Int</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.ExprInt
	 * @generated
	 */
	public Adapter createExprIntAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.ExprList
	 * <em>Expr List</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.ExprList
	 * @generated
	 */
	public Adapter createExprListAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.ExprString
	 * <em>Expr String</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.ExprString
	 * @generated
	 */
	public Adapter createExprStringAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.ExprResize
	 * <em>Expr Resize</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.ExprResize
	 * @generated
	 */
	public Adapter createExprResizeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.ExprTernary
	 * <em>Expr Ternary</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.ExprTernary
	 * @generated
	 */
	public Adapter createExprTernaryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.ExprTypeConv
	 * <em>Expr Type Conv</em>}'. <!-- begin-user-doc --> This default implementation returns null
	 * so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.ExprTypeConv
	 * @generated
	 */
	public Adapter createExprTypeConvAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.ExprUnary
	 * <em>Expr Unary</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.ExprUnary
	 * @generated
	 */
	public Adapter createExprUnaryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.ExprVar
	 * <em>Expr Var</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.ExprVar
	 * @generated
	 */
	public Adapter createExprVarAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.InstAssign
	 * <em>Inst Assign</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.InstAssign
	 * @generated
	 */
	public Adapter createInstAssignAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.InstCall
	 * <em>Inst Call</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.InstCall
	 * @generated
	 */
	public Adapter createInstCallAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.InstLoad
	 * <em>Inst Load</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.InstLoad
	 * @generated
	 */
	public Adapter createInstLoadAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.InstPhi
	 * <em>Inst Phi</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.InstPhi
	 * @generated
	 */
	public Adapter createInstPhiAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.InstReturn
	 * <em>Inst Return</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.InstReturn
	 * @generated
	 */
	public Adapter createInstReturnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.Instruction
	 * <em>Instruction</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.Instruction
	 * @generated
	 */
	public Adapter createInstructionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.InstStore
	 * <em>Inst Store</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.InstStore
	 * @generated
	 */
	public Adapter createInstStoreAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.Procedure
	 * <em>Procedure</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.Procedure
	 * @generated
	 */
	public Adapter createProcedureAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.Block
	 * <em>Block</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.Block
	 * @generated
	 */
	public Adapter createBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.BlockBasic
	 * <em>Block Basic</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.BlockBasic
	 * @generated
	 */
	public Adapter createBlockBasicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.BlockIf
	 * <em>Block If</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.BlockIf
	 * @generated
	 */
	public Adapter createBlockIfAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.BlockWhile
	 * <em>Block While</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.BlockWhile
	 * @generated
	 */
	public Adapter createBlockWhileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.Type <em>Type</em>
	 * }'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.Type
	 * @generated
	 */
	public Adapter createTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.TypeBool
	 * <em>Type Bool</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.TypeBool
	 * @generated
	 */
	public Adapter createTypeBoolAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.TypeFloat
	 * <em>Type Float</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.TypeFloat
	 * @generated
	 */
	public Adapter createTypeFloatAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.TypeInt
	 * <em>Type Int</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.TypeInt
	 * @generated
	 */
	public Adapter createTypeIntAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.TypeString
	 * <em>Type String</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.TypeString
	 * @generated
	 */
	public Adapter createTypeStringAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.TypeVoid
	 * <em>Type Void</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.TypeVoid
	 * @generated
	 */
	public Adapter createTypeVoidAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.Use <em>Use</em>}
	 * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.Use
	 * @generated
	 */
	public Adapter createUseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.TypeArray
	 * <em>Type Array</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.TypeArray
	 * @generated
	 */
	public Adapter createTypeArrayAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.ir.Var <em>Var</em>}
	 * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.ir.Var
	 * @generated
	 */
	public Adapter createVarAdapter() {
		return null;
	}

	/**
	 * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc
	 * --> This implementation returns <code>true</code> if the object is either the model's package
	 * or is an instance object of the model. <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

} // IrAdapterFactory
