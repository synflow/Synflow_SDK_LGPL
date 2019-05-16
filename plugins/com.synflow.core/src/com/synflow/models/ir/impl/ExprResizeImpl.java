/**
 */
package com.synflow.models.ir.impl;

import com.synflow.models.ir.ExprResize;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.IrPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Expr Resize</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.ir.impl.ExprResizeImpl#getExpr <em>Expr</em>}</li>
 * <li>{@link com.synflow.models.ir.impl.ExprResizeImpl#getTargetSize <em>Target Size</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExprResizeImpl extends ExpressionImpl implements ExprResize {
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
	 * The default value of the '{@link #getTargetSize() <em>Target Size</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTargetSize()
	 * @generated
	 * @ordered
	 */
	protected static final int TARGET_SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTargetSize() <em>Target Size</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTargetSize()
	 * @generated
	 * @ordered
	 */
	protected int targetSize = TARGET_SIZE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ExprResizeImpl() {
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
					IrPackage.EXPR_RESIZE__EXPR, oldExpr, newExpr);
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
		case IrPackage.EXPR_RESIZE__EXPR:
			return getExpr();
		case IrPackage.EXPR_RESIZE__TARGET_SIZE:
			return getTargetSize();
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
		case IrPackage.EXPR_RESIZE__EXPR:
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
		case IrPackage.EXPR_RESIZE__EXPR:
			return expr != null;
		case IrPackage.EXPR_RESIZE__TARGET_SIZE:
			return targetSize != TARGET_SIZE_EDEFAULT;
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
		case IrPackage.EXPR_RESIZE__EXPR:
			setExpr((Expression) newValue);
			return;
		case IrPackage.EXPR_RESIZE__TARGET_SIZE:
			setTargetSize((Integer) newValue);
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
		return IrPackage.Literals.EXPR_RESIZE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.EXPR_RESIZE__EXPR:
			setExpr((Expression) null);
			return;
		case IrPackage.EXPR_RESIZE__TARGET_SIZE:
			setTargetSize(TARGET_SIZE_EDEFAULT);
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
	public int getTargetSize() {
		return targetSize;
	}

	@Override
	public boolean isExprResize() {
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
						EOPPOSITE_FEATURE_BASE - IrPackage.EXPR_RESIZE__EXPR, null, msgs);
			if (newExpr != null)
				msgs = ((InternalEObject) newExpr).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.EXPR_RESIZE__EXPR, null, msgs);
			msgs = basicSetExpr(newExpr, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.EXPR_RESIZE__EXPR,
					newExpr, newExpr));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTargetSize(int newTargetSize) {
		int oldTargetSize = targetSize;
		targetSize = newTargetSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.EXPR_RESIZE__TARGET_SIZE, oldTargetSize, targetSize));
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
		result.append(" (targetSize: ");
		result.append(targetSize);
		result.append(')');
		return result.toString();
	}

}
