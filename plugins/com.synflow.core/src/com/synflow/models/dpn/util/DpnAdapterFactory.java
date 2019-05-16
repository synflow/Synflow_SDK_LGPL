/**
 */
package com.synflow.models.dpn.util;

import com.synflow.models.dpn.*;
import com.synflow.models.graph.Edge;
import com.synflow.models.graph.Graph;
import com.synflow.models.graph.Vertex;
import com.synflow.models.ir.Instruction;
import com.synflow.models.ir.Var;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model. <!-- end-user-doc -->
 * 
 * @see com.synflow.models.dpn.DpnPackage
 * @generated
 */
public class DpnAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static DpnPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DpnAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DpnPackage.eINSTANCE;
		}
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

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected DpnSwitch<Adapter> modelSwitch = new DpnSwitch<Adapter>() {
		@Override
		public Adapter caseAction(Action object) {
			return createActionAdapter();
		}

		@Override
		public Adapter caseActor(Actor object) {
			return createActorAdapter();
		}

		@Override
		public Adapter caseArgument(Argument object) {
			return createArgumentAdapter();
		}

		@Override
		public Adapter caseConnection(Connection object) {
			return createConnectionAdapter();
		}

		@Override
		public Adapter caseDPN(DPN object) {
			return createDPNAdapter();
		}

		@Override
		public Adapter caseEntity(Entity object) {
			return createEntityAdapter();
		}

		@Override
		public Adapter caseFSM(FSM object) {
			return createFSMAdapter();
		}

		@Override
		public Adapter caseInstance(Instance object) {
			return createInstanceAdapter();
		}

		@Override
		public Adapter casePattern(Pattern object) {
			return createPatternAdapter();
		}

		@Override
		public Adapter casePort(Port object) {
			return createPortAdapter();
		}

		@Override
		public Adapter caseState(State object) {
			return createStateAdapter();
		}

		@Override
		public Adapter caseTransition(Transition object) {
			return createTransitionAdapter();
		}

		@Override
		public Adapter caseUnit(Unit object) {
			return createUnitAdapter();
		}

		@Override
		public Adapter caseGoto(Goto object) {
			return createGotoAdapter();
		}

		@Override
		public Adapter caseEdge(Edge object) {
			return createEdgeAdapter();
		}

		@Override
		public Adapter caseVertex(Vertex object) {
			return createVertexAdapter();
		}

		@Override
		public Adapter caseGraph(Graph object) {
			return createGraphAdapter();
		}

		@Override
		public Adapter caseVar(Var object) {
			return createVarAdapter();
		}

		@Override
		public Adapter caseInstruction(Instruction object) {
			return createInstructionAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

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
	 * Creates a new adapter for an object of class '{@link com.synflow.models.dpn.Unit
	 * <em>Unit</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.dpn.Unit
	 * @generated
	 */
	public Adapter createUnitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.dpn.Goto
	 * <em>Goto</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.dpn.Goto
	 * @generated
	 */
	public Adapter createGotoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.dpn.Instance
	 * <em>Instance</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.dpn.Instance
	 * @generated
	 */
	public Adapter createInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.dpn.Entity
	 * <em>Entity</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.dpn.Entity
	 * @generated
	 */
	public Adapter createEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.dpn.Actor
	 * <em>Actor</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.dpn.Actor
	 * @generated
	 */
	public Adapter createActorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.dpn.Action
	 * <em>Action</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.dpn.Action
	 * @generated
	 */
	public Adapter createActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.dpn.FSM <em>FSM</em>}
	 * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.dpn.FSM
	 * @generated
	 */
	public Adapter createFSMAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.dpn.Pattern
	 * <em>Pattern</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.dpn.Pattern
	 * @generated
	 */
	public Adapter createPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.dpn.State
	 * <em>State</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.dpn.State
	 * @generated
	 */
	public Adapter createStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.dpn.Transition
	 * <em>Transition</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.dpn.Transition
	 * @generated
	 */
	public Adapter createTransitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.dpn.Argument
	 * <em>Argument</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.dpn.Argument
	 * @generated
	 */
	public Adapter createArgumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.dpn.Port
	 * <em>Port</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.dpn.Port
	 * @generated
	 */
	public Adapter createPortAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.dpn.Connection
	 * <em>Connection</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.dpn.Connection
	 * @generated
	 */
	public Adapter createConnectionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.dpn.DPN <em>DPN</em>}
	 * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.dpn.DPN
	 * @generated
	 */
	public Adapter createDPNAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.graph.Vertex
	 * <em>Vertex</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.graph.Vertex
	 * @generated
	 */
	public Adapter createVertexAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.graph.Graph
	 * <em>Graph</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.graph.Graph
	 * @generated
	 */
	public Adapter createGraphAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.synflow.models.graph.Edge
	 * <em>Edge</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.synflow.models.graph.Edge
	 * @generated
	 */
	public Adapter createEdgeAdapter() {
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
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This default
	 * implementation returns null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // DpnAdapterFactory
