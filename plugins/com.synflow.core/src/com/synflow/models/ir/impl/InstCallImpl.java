/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.ir.impl;

import static com.synflow.models.ir.util.IrUtil.getNameSSA;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.synflow.models.ir.Def;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstCall;
import com.synflow.models.ir.IrPackage;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.util.ExpressionPrinter;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Inst Call</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.ir.impl.InstCallImpl#getArguments <em>Arguments</em>}</li>
 * <li>{@link com.synflow.models.ir.impl.InstCallImpl#getProcedure <em>Procedure</em>}</li>
 * <li>{@link com.synflow.models.ir.impl.InstCallImpl#getTarget <em>Target</em>}</li>
 * <li>{@link com.synflow.models.ir.impl.InstCallImpl#isPrint <em>Print</em>}</li>
 * <li>{@link com.synflow.models.ir.impl.InstCallImpl#isAssert <em>Assert</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InstCallImpl extends InstructionImpl implements InstCall {
	/**
	 * The cached value of the '{@link #getArguments() <em>Arguments</em>}' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getArguments()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> arguments;

	/**
	 * The cached value of the '{@link #getProcedure() <em>Procedure</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProcedure()
	 * @generated
	 * @ordered
	 */
	protected Procedure procedure;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Def target;

	/**
	 * The default value of the '{@link #isPrint() <em>Print</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isPrint()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PRINT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPrint() <em>Print</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isPrint()
	 * @generated
	 * @ordered
	 */
	protected boolean print = PRINT_EDEFAULT;

	/**
	 * The default value of the '{@link #isAssert() <em>Assert</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isAssert()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ASSERT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAssert() <em>Assert</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isAssert()
	 * @generated
	 * @ordered
	 */
	protected boolean assert_ = ASSERT_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected InstCallImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Procedure basicGetProcedure() {
		return procedure;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetTarget(Def newTarget, NotificationChain msgs) {
		Def oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IrPackage.INST_CALL__TARGET, oldTarget, newTarget);
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
		case IrPackage.INST_CALL__ARGUMENTS:
			return getArguments();
		case IrPackage.INST_CALL__PROCEDURE:
			if (resolve)
				return getProcedure();
			return basicGetProcedure();
		case IrPackage.INST_CALL__TARGET:
			return getTarget();
		case IrPackage.INST_CALL__PRINT:
			return isPrint();
		case IrPackage.INST_CALL__ASSERT:
			return isAssert();
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
		case IrPackage.INST_CALL__ARGUMENTS:
			return ((InternalEList<?>) getArguments()).basicRemove(otherEnd, msgs);
		case IrPackage.INST_CALL__TARGET:
			return basicSetTarget(null, msgs);
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
		case IrPackage.INST_CALL__ARGUMENTS:
			return arguments != null && !arguments.isEmpty();
		case IrPackage.INST_CALL__PROCEDURE:
			return procedure != null;
		case IrPackage.INST_CALL__TARGET:
			return target != null;
		case IrPackage.INST_CALL__PRINT:
			return print != PRINT_EDEFAULT;
		case IrPackage.INST_CALL__ASSERT:
			return assert_ != ASSERT_EDEFAULT;
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
		case IrPackage.INST_CALL__ARGUMENTS:
			getArguments().clear();
			getArguments().addAll((Collection<? extends Expression>) newValue);
			return;
		case IrPackage.INST_CALL__PROCEDURE:
			setProcedure((Procedure) newValue);
			return;
		case IrPackage.INST_CALL__TARGET:
			setTarget((Def) newValue);
			return;
		case IrPackage.INST_CALL__PRINT:
			setPrint((Boolean) newValue);
			return;
		case IrPackage.INST_CALL__ASSERT:
			setAssert((Boolean) newValue);
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
		return IrPackage.Literals.INST_CALL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.INST_CALL__ARGUMENTS:
			getArguments().clear();
			return;
		case IrPackage.INST_CALL__PROCEDURE:
			setProcedure((Procedure) null);
			return;
		case IrPackage.INST_CALL__TARGET:
			setTarget((Def) null);
			return;
		case IrPackage.INST_CALL__PRINT:
			setPrint(PRINT_EDEFAULT);
			return;
		case IrPackage.INST_CALL__ASSERT:
			setAssert(ASSERT_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Expression> getArguments() {
		if (arguments == null) {
			arguments = new EObjectContainmentEList<Expression>(Expression.class, this,
					IrPackage.INST_CALL__ARGUMENTS);
		}
		return arguments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Procedure getProcedure() {
		if (procedure != null && procedure.eIsProxy()) {
			InternalEObject oldProcedure = (InternalEObject) procedure;
			procedure = (Procedure) eResolveProxy(oldProcedure);
			if (procedure != oldProcedure) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IrPackage.INST_CALL__PROCEDURE, oldProcedure, procedure));
			}
		}
		return procedure;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Def getTarget() {
		return target;
	}

	@Override
	public boolean hasResult() {
		return (getTarget() != null);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isAssert() {
		return assert_;
	}

	@Override
	public boolean isInstCall() {
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isPrint() {
		return print;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setAssert(boolean newAssert) {
		boolean oldAssert = assert_;
		assert_ = newAssert;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.INST_CALL__ASSERT,
					oldAssert, assert_));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setPrint(boolean newPrint) {
		boolean oldPrint = print;
		print = newPrint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.INST_CALL__PRINT,
					oldPrint, print));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setProcedure(Procedure newProcedure) {
		Procedure oldProcedure = procedure;
		procedure = newProcedure;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.INST_CALL__PROCEDURE,
					oldProcedure, procedure));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTarget(Def newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject) target).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.INST_CALL__TARGET, null, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject) newTarget).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.INST_CALL__TARGET, null, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.INST_CALL__TARGET,
					newTarget, newTarget));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("Call(");
		if (getTarget() != null) {
			builder.append(getNameSSA(getTarget().getVariable())).append(", ");
		}

		builder.append(getProcedure().getName());
		for (Expression expr : getArguments()) {
			builder.append(", ");
			builder.append(new ExpressionPrinter().toString(expr));
		}
		return builder.append(")").toString();
	}

} // InstCallImpl
