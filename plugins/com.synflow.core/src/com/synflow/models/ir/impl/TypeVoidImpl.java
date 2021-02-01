/*
 * This file is part of the ngDesign SDK.
 *
 * Copyright (c) 2019 - 2021 Synflow SAS.
 *
 * ngDesign is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ngDesign is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ngDesign.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
 
package com.synflow.models.ir.impl;

import org.eclipse.emf.ecore.EClass;

import com.synflow.models.ir.IrPackage;
import com.synflow.models.ir.TypeVoid;
import com.synflow.models.ir.util.TypePrinter;

/**
 * This class defines a void type.
 *
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 *
 */
public class TypeVoidImpl extends TypeImpl implements TypeVoid {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected TypeVoidImpl() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof TypeVoid);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.TYPE_VOID;
	}

	@Override
	public boolean isVoid() {
		return true;
	}

	@Override
	public String toString() {
		return new TypePrinter().toString(this);
	}

}
