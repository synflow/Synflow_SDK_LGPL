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
package com.synflow.cx.internal.compiler;

import org.eclipse.emf.ecore.EObject;

import com.synflow.cx.cx.CxExpression;
import com.synflow.models.ir.Expression;

/**
 * This interface defines a method for a class that can transform a Cx expression into an IR
 * expression.
 *
 * @author Matthieu Wipliez
 *
 */
public interface Transformer {

	EObject doSwitch(EObject eObject);

	/**
	 * Transforms the given expression without assigning to a particular target.
	 *
	 * @param expression
	 *            an AST expression
	 * @return an IR expression
	 */
	Expression transformExpr(CxExpression expression);

}
