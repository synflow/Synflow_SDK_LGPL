/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.ir.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import com.synflow.models.ir.Block;
import com.synflow.models.ir.BlockBasic;
import com.synflow.models.ir.IrPackage;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.IrPackage.Literals;
import com.synflow.models.ir.util.IrUtil;
import com.synflow.models.ir.util.MapAdapter;
import java.lang.Integer;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Procedure</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.ir.impl.ProcedureImpl#getLineNumber <em>Line Number</em>}</li>
 * <li>{@link com.synflow.models.ir.impl.ProcedureImpl#getLocals <em>Locals</em>}</li>
 * <li>{@link com.synflow.models.ir.impl.ProcedureImpl#getName <em>Name</em>}</li>
 * <li>{@link com.synflow.models.ir.impl.ProcedureImpl#getBlocks <em>Blocks</em>}</li>
 * <li>{@link com.synflow.models.ir.impl.ProcedureImpl#getParameters <em>Parameters</em>}</li>
 * <li>{@link com.synflow.models.ir.impl.ProcedureImpl#getReturnType <em>Return Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProcedureImpl extends EObjectImpl implements Procedure {

	/**
	 * The default value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected int lineNumber = LINE_NUMBER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLocals() <em>Locals</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLocals()
	 * @generated
	 * @ordered
	 */
	protected EList<Var> locals;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * A map from name to index in the locals list.
	 */
	private Map<String, Var> mapLocals;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBlocks() <em>Blocks</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBlocks()
	 * @generated
	 * @ordered
	 */
	protected EList<Block> blocks;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Var> parameters;

	/**
	 * The cached value of the '{@link #getReturnType() <em>Return Type</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getReturnType()
	 * @generated
	 * @ordered
	 */
	protected Type returnType;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	protected ProcedureImpl() {
		super();

		mapLocals = new HashMap<String, Var>();
		eAdapters().add(new MapAdapter(mapLocals, Literals.PROCEDURE__LOCALS));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetReturnType(Type newReturnType, NotificationChain msgs) {
		Type oldReturnType = returnType;
		returnType = newReturnType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IrPackage.PROCEDURE__RETURN_TYPE, oldReturnType, newReturnType);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IrPackage.PROCEDURE__LINE_NUMBER:
			return getLineNumber();
		case IrPackage.PROCEDURE__LOCALS:
			return getLocals();
		case IrPackage.PROCEDURE__NAME:
			return getName();
		case IrPackage.PROCEDURE__BLOCKS:
			return getBlocks();
		case IrPackage.PROCEDURE__PARAMETERS:
			return getParameters();
		case IrPackage.PROCEDURE__RETURN_TYPE:
			return getReturnType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
			NotificationChain msgs) {
		switch (featureID) {
		case IrPackage.PROCEDURE__LOCALS:
			return ((InternalEList<?>) getLocals()).basicRemove(otherEnd, msgs);
		case IrPackage.PROCEDURE__BLOCKS:
			return ((InternalEList<?>) getBlocks()).basicRemove(otherEnd, msgs);
		case IrPackage.PROCEDURE__PARAMETERS:
			return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
		case IrPackage.PROCEDURE__RETURN_TYPE:
			return basicSetReturnType(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case IrPackage.PROCEDURE__LINE_NUMBER:
			return lineNumber != LINE_NUMBER_EDEFAULT;
		case IrPackage.PROCEDURE__LOCALS:
			return locals != null && !locals.isEmpty();
		case IrPackage.PROCEDURE__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case IrPackage.PROCEDURE__BLOCKS:
			return blocks != null && !blocks.isEmpty();
		case IrPackage.PROCEDURE__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		case IrPackage.PROCEDURE__RETURN_TYPE:
			return returnType != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IrPackage.PROCEDURE__LINE_NUMBER:
			setLineNumber((Integer) newValue);
			return;
		case IrPackage.PROCEDURE__LOCALS:
			getLocals().clear();
			getLocals().addAll((Collection<? extends Var>) newValue);
			return;
		case IrPackage.PROCEDURE__NAME:
			setName((String) newValue);
			return;
		case IrPackage.PROCEDURE__BLOCKS:
			getBlocks().clear();
			getBlocks().addAll((Collection<? extends Block>) newValue);
			return;
		case IrPackage.PROCEDURE__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends Var>) newValue);
			return;
		case IrPackage.PROCEDURE__RETURN_TYPE:
			setReturnType((Type) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.PROCEDURE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.PROCEDURE__LINE_NUMBER:
			setLineNumber(LINE_NUMBER_EDEFAULT);
			return;
		case IrPackage.PROCEDURE__LOCALS:
			getLocals().clear();
			return;
		case IrPackage.PROCEDURE__NAME:
			setName(NAME_EDEFAULT);
			return;
		case IrPackage.PROCEDURE__BLOCKS:
			getBlocks().clear();
			return;
		case IrPackage.PROCEDURE__PARAMETERS:
			getParameters().clear();
			return;
		case IrPackage.PROCEDURE__RETURN_TYPE:
			setReturnType((Type) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Block> getBlocks() {
		if (blocks == null) {
			blocks = new EObjectContainmentEList<Block>(Block.class, this,
					IrPackage.PROCEDURE__BLOCKS);
		}
		return blocks;
	}

	/**
	 * Returns the first block in the list of blocks of this procedure. A new block is created if
	 * there is no block in the given block list.
	 * 
	 * @return the first block in the list of blocks of this procedure
	 */
	@Override
	public BlockBasic getFirst() {
		return IrUtil.getFirst(getBlocks());
	}

	/**
	 * Returns the last block in the list of blocks of this procedure. A new block is created if
	 * there is no block in the given block list.
	 * 
	 * @return the last block in the list of blocks of this procedure
	 */
	@Override
	public BlockBasic getLast() {
		return IrUtil.getLast(getBlocks());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getLineNumber() {
		return lineNumber;
	}

	@Override
	public Var getLocal(String name) {
		return mapLocals.get(name);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Var> getLocals() {
		if (locals == null) {
			locals = new EObjectContainmentEList<Var>(Var.class, this, IrPackage.PROCEDURE__LOCALS);
		}
		return locals;
	}

	/**
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Var> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Var>(Var.class, this,
					IrPackage.PROCEDURE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Type getReturnType() {
		return returnType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLineNumber(int newLineNumber) {
		int oldLineNumber = lineNumber;
		lineNumber = newLineNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.PROCEDURE__LINE_NUMBER,
					oldLineNumber, lineNumber));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.PROCEDURE__NAME,
					oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setReturnType(Type newReturnType) {
		if (newReturnType != returnType) {
			NotificationChain msgs = null;
			if (returnType != null)
				msgs = ((InternalEObject) returnType).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.PROCEDURE__RETURN_TYPE, null, msgs);
			if (newReturnType != null)
				msgs = ((InternalEObject) newReturnType).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.PROCEDURE__RETURN_TYPE, null, msgs);
			msgs = basicSetReturnType(newReturnType, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.PROCEDURE__RETURN_TYPE,
					newReturnType, newReturnType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (lineNumber: ");
		result.append(lineNumber);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // ProcedureImpl
