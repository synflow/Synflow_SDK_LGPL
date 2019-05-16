/*
 * This file is part of the ngDesign SDK.
 *
 * Copyright (c) 2019 Synflow SAS.
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
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.synflow.models.ir.IrPackage;
import com.synflow.models.ir.Type;

/**
 * This class is an abstract implementation of {@link Type}.
 *
 * @author Matthieu Wipliez
 * @generated
 *
 */
public abstract class TypeImpl extends EObjectImpl implements Type {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected TypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.TYPE;
	}

	@Override
	public boolean isBool() {
		return false;
	}

	@Override
	public boolean isFloat() {
		return false;
	}

	@Override
	public boolean isInt() {
		return false;
	}

	@Override
	public boolean isArray() {
		return false;
	}

	@Override
	public boolean isString() {
		return false;
	}

	@Override
	public boolean isVoid() {
		return false;
	}

}
