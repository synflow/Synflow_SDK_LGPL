/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.ir.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.synflow.models.ir.ExprUnary;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.IrPackage;
import com.synflow.models.ir.OpUnary;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Expr Unary</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.ir.impl.ExprUnaryImpl#getExpr <em>Expr</em>}</li>
 * <li>{@link com.synflow.models.ir.impl.ExprUnaryImpl#getOp <em>Op</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExprUnaryImpl extends ExpressionImpl implements ExprUnary {
	/**
	 * The cached value of the '{@link #getExpr() <em>Expr</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExpr()
	 * @generated
	 * @ordered
	 */
	protected Expression expr;
	/**
	 * The default value of the '{@link #getOp() <em>Op</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getOp()
	 * @generated
	 * @ordered
	 */
	protected static final OpUnary OP_EDEFAULT = OpUnary.BITNOT;

	/**
	 * The cached value of the '{@link #getOp() <em>Op</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getOp()
	 * @generated
	 * @ordered
	 */
	protected OpUnary op = OP_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ExprUnaryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetExpr(Expression newExpr, NotificationChain msgs) {
		Expression oldExpr = expr;
		expr = newExpr;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IrPackage.EXPR_UNARY__EXPR, oldExpr, newExpr);
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
		case IrPackage.EXPR_UNARY__EXPR:
			return getExpr();
		case IrPackage.EXPR_UNARY__OP:
			return getOp();
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
		case IrPackage.EXPR_UNARY__EXPR:
			return basicSetExpr(null, msgs);
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
		case IrPackage.EXPR_UNARY__EXPR:
			return expr != null;
		case IrPackage.EXPR_UNARY__OP:
			return op != OP_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IrPackage.EXPR_UNARY__EXPR:
			setExpr((Expression) newValue);
			return;
		case IrPackage.EXPR_UNARY__OP:
			setOp((OpUnary) newValue);
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
		return IrPackage.Literals.EXPR_UNARY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.EXPR_UNARY__EXPR:
			setExpr((Expression) null);
			return;
		case IrPackage.EXPR_UNARY__OP:
			setOp(OP_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression getExpr() {
		return expr;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OpUnary getOp() {
		return op;
	}

	@Override
	public boolean isExprUnary() {
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setExpr(Expression newExpr) {
		if (newExpr != expr) {
			NotificationChain msgs = null;
			if (expr != null)
				msgs = ((InternalEObject) expr).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.EXPR_UNARY__EXPR, null, msgs);
			if (newExpr != null)
				msgs = ((InternalEObject) newExpr).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.EXPR_UNARY__EXPR, null, msgs);
			msgs = basicSetExpr(newExpr, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.EXPR_UNARY__EXPR,
					newExpr, newExpr));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setOp(OpUnary newOp) {
		OpUnary oldOp = op;
		op = newOp == null ? OP_EDEFAULT : newOp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.EXPR_UNARY__OP, oldOp,
					op));
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
		result.append(" (op: ");
		result.append(op);
		result.append(')');
		return result.toString();
	}

} // ExprUnaryImpl
