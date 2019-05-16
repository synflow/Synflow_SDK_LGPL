/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.dpn.impl;

import org.eclipse.emf.ecore.EClass;

import com.synflow.models.dpn.DpnPackage;
import com.synflow.models.dpn.Unit;
import com.synflow.models.ir.Procedure;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Unit</b></em>'. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class UnitImpl extends EntityImpl implements Unit {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected UnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DpnPackage.Literals.UNIT;
	}

	@Override
	public Procedure getProcedure(String name) {
		for (Procedure procedure : getProcedures()) {
			if (procedure.getName().equals(name)) {
				return procedure;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();
		return "unit " + getName();
	}

}
