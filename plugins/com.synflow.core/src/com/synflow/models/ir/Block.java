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
 
package com.synflow.models.ir;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> This class defines a block.<!-- end-user-doc -->
 *
 *
 * @see com.synflow.models.ir.IrPackage#getBlock()
 * @model abstract="true"
 * @generated
 */
public interface Block extends EObject {

	/**
	 * Returns <code>true</code> if this block is a BlockBasic.
	 *
	 * @return <code>true</code> if this block is a BlockBasic
	 */
	boolean isBlockBasic();

	/**
	 * Returns <code>true</code> if this block is an BlockIf.
	 *
	 * @return <code>true</code> if this block is an BlockIf
	 */
	boolean isBlockIf();

	/**
	 * Returns <code>true</code> if this block is a BlockWhile.
	 *
	 * @return <code>true</code> if this block is a BlockWhile
	 */
	boolean isBlockWhile();

}
