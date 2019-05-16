/**
 */
package com.synflow.models.ir.impl;

import com.synflow.models.ir.ExprTypeConv;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.IrPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Expr Type Conv</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.ir.impl.ExprTypeConvImpl#getExpr <em>Expr</em>}</li>
 * <li>{@link com.synflow.models.ir.impl.ExprTypeConvImpl#getTypeName <em>Type Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExprTypeConvImpl extends ExpressionImpl implements ExprTypeConv {
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
	 * The default value of the '{@link #getTypeName() <em>Type Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTypeName()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypeName() <em>Type Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTypeName()
	 * @generated
	 * @ordered
	 */
	protected String typeName = TYPE_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ExprTypeConvImpl() {
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
					IrPackage.EXPR_TYPE_CONV__EXPR, oldExpr, newExpr);
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
		case IrPackage.EXPR_TYPE_CONV__EXPR:
			return getExpr();
		case IrPackage.EXPR_TYPE_CONV__TYPE_NAME:
			return getTypeName();
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
		case IrPackage.EXPR_TYPE_CONV__EXPR:
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
		case IrPackage.EXPR_TYPE_CONV__EXPR:
			return expr != null;
		case IrPackage.EXPR_TYPE_CONV__TYPE_NAME:
			return TYPE_NAME_EDEFAULT == null ? typeName != null
					: !TYPE_NAME_EDEFAULT.equals(typeName);
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
		case IrPackage.EXPR_TYPE_CONV__EXPR:
			setExpr((Expression) newValue);
			return;
		case IrPackage.EXPR_TYPE_CONV__TYPE_NAME:
			setTypeName((String) newValue);
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
		return IrPackage.Literals.EXPR_TYPE_CONV;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.EXPR_TYPE_CONV__EXPR:
			setExpr((Expression) null);
			return;
		case IrPackage.EXPR_TYPE_CONV__TYPE_NAME:
			setTypeName(TYPE_NAME_EDEFAULT);
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
	public String getTypeName() {
		return typeName;
	}

	@Override
	public boolean isExprTypeConv() {
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
						EOPPOSITE_FEATURE_BASE - IrPackage.EXPR_TYPE_CONV__EXPR, null, msgs);
			if (newExpr != null)
				msgs = ((InternalEObject) newExpr).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.EXPR_TYPE_CONV__EXPR, null, msgs);
			msgs = basicSetExpr(newExpr, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.EXPR_TYPE_CONV__EXPR,
					newExpr, newExpr));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTypeName(String newTypeName) {
		String oldTypeName = typeName;
		typeName = newTypeName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.EXPR_TYPE_CONV__TYPE_NAME, oldTypeName, typeName));
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
		result.append(" (typeName: ");
		result.append(typeName);
		result.append(')');
		return result.toString();
	}

}
