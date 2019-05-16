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
package com.synflow.cx.linking;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.IDiagnosticConsumer;
import org.eclipse.xtext.linking.lazy.LazyLinker;

import com.synflow.cx.cx.ExpressionVariable;

/**
 * This class extends the lazy linker to clear the property variables in ExpressionVariable.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxLinker extends LazyLinker {

	@Override
	protected void beforeModelLinked(EObject model, IDiagnosticConsumer diagnosticsConsumer) {
		super.beforeModelLinked(model, diagnosticsConsumer);

		Resource resource = model.eResource();
		TreeIterator<EObject> it = EcoreUtil.getAllContents(resource, false);
		while (it.hasNext()) {
			EObject eObject = it.next();
			if (eObject instanceof ExpressionVariable) {
				ExpressionVariable expr = (ExpressionVariable) eObject;
				if (expr.getProperty() != null) {
					expr.setProperty(null);
				}
			}
		}
	}

}
