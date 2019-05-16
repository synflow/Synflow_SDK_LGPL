/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.ir.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import com.synflow.models.ir.BlockBasic;
import com.synflow.models.ir.Instruction;
import com.synflow.models.ir.IrPackage;
import java.lang.Integer;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Instruction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.ir.impl.InstructionImpl#getLineNumber <em>Line Number</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class InstructionImpl extends EObjectImpl implements Instruction {

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected InstructionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IrPackage.INSTRUCTION__LINE_NUMBER:
			return getLineNumber();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case IrPackage.INSTRUCTION__LINE_NUMBER:
			return lineNumber != LINE_NUMBER_EDEFAULT;
		}
		return super.eIsSet(featureID);
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
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IrPackage.INSTRUCTION__LINE_NUMBER:
			setLineNumber((Integer) newValue);
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
		return IrPackage.Literals.INSTRUCTION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.INSTRUCTION__LINE_NUMBER:
			setLineNumber(LINE_NUMBER_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	@Override
	public BlockBasic getBlock() {
		return (BlockBasic) eContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	@Override
	public boolean isInstAssign() {
		return false;
	}

	@Override
	public boolean isInstCall() {
		return false;
	}

	@Override
	public boolean isInstLoad() {
		return false;
	}

	@Override
	public boolean isInstPhi() {
		return false;
	}

	@Override
	public boolean isInstReturn() {
		return false;
	}

	@Override
	public boolean isInstStore() {
		return false;
	}

	@Override
	public boolean isInstSpecific() {
		return false;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLineNumber(int newLineNumber) {
		int oldLineNumber = lineNumber;
		lineNumber = newLineNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.INSTRUCTION__LINE_NUMBER, oldLineNumber, lineNumber));
	}

} // InstructionImpl
