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
package com.synflow.cx.internal.validation;

import static com.synflow.cx.validation.IssueCodes.ERR_TYPE_MISMATCH;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.synflow.cx.services.VoidCxSwitch;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.util.TypePrinter;
import com.synflow.models.ir.util.TypeUtil;

/**
 * This class defines an abstract checker, that provides "error" protected methods similar to
 * Validator.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class Checker extends VoidCxSwitch {

	protected ValidationMessageAcceptor acceptor;

	public Checker() {
	}

	public Checker(ValidationMessageAcceptor acceptor) {
		this.acceptor = acceptor;
	}

	/**
	 * Checks the expression can be assigned to the variable.
	 *
	 * @param source
	 *            AST node to use to signal an error
	 * @param typeTgt
	 *            type of target
	 * @param typeSrc
	 *            type of source
	 */
	protected void checkAssign(Type typeTgt, Type typeSrc, EObject source,
			EStructuralFeature feature, int index) {
		if (typeTgt == null || typeSrc == null) {
			return;
		}

		if (!TypeUtil.canAssign(typeSrc, typeTgt)) {
			error("Type mismatch: cannot convert from " + new TypePrinter().toString(typeSrc)
					+ " to " + new TypePrinter().toString(typeTgt), source, feature, index,
					ERR_TYPE_MISMATCH);
		}
	}

	/**
	 * Checks the expression can be assigned to the variable, and implicitly cast to bool if needed.
	 *
	 * @param source
	 *            AST node to use to signal an error
	 * @param typeTgt
	 *            type of target
	 * @param typeSrc
	 *            type of source
	 */
	protected void checkAssignImplicitBool(Type typeTgt, Type typeSrc, EObject source,
			EStructuralFeature feature, int index) {
		if (typeTgt == null || typeSrc == null) {
			return;
		}

		if (!TypeUtil.canAssignBool(typeSrc, typeTgt)) {
			error("Type mismatch: cannot convert from " + new TypePrinter().toString(typeSrc)
					+ " to " + new TypePrinter().toString(typeTgt), source, feature, index,
					ERR_TYPE_MISMATCH);
		}
	}

	protected void error(String message, EObject source, EStructuralFeature feature, int index) {
		error(message, source, feature, index, null);
	}

	protected void error(String message, EObject source, EStructuralFeature feature, int index,
			String code, String... issueData) {
		acceptor.acceptError(message, source, feature, index, code, issueData);
	}

	protected void error(String message, EObject source, EStructuralFeature feature, String code,
			String... issueData) {
		acceptor.acceptError(message, source, feature,
				ValidationMessageAcceptor.INSIGNIFICANT_INDEX, code, issueData);
	}

	/**
	 * If the acceptor is not set at construction time (for example if this checker is injected),
	 * this method allows users to set it later.
	 *
	 * @param acceptor
	 */
	public void setValidator(ValidationMessageAcceptor acceptor) {
		this.acceptor = acceptor;
	}

	protected void warning(String message, EObject source, EStructuralFeature feature) {
		warning(message, source, feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
	}

	protected void warning(String message, EObject source, EStructuralFeature feature, int index) {
		warning(message, source, feature, index, null);
	}

	protected void warning(String message, EObject source, EStructuralFeature feature, int index,
			String code, String... issueData) {
		acceptor.acceptWarning(message, source, feature, index, code, issueData);
	}

}
