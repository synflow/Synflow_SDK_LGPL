/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.ir.impl;

import org.eclipse.emf.ecore.EClass;

import com.synflow.models.ir.IrPackage;
import com.synflow.models.ir.TypeBool;
import com.synflow.models.ir.util.TypePrinter;

/**
 * This class defines a boolean type.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * 
 */
public class TypeBoolImpl extends TypeImpl implements TypeBool {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TypeBoolImpl() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof TypeBool);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.TYPE_BOOL;
	}

	@Override
	public boolean isBool() {
		return true;
	}

	@Override
	public String toString() {
		return new TypePrinter().toString(this);
	}

}
