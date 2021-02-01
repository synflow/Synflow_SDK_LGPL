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
package com.synflow.cx.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.Module;
import com.synflow.cx.cx.Task;
import com.synflow.cx.cx.Variable;

/**
 * This class defines a qualified name provider for Cx that computes the qualified name of a module
 * as its simple name (file name without extension).
 *
 * @author Matthieu Wipliez
 *
 */
public class CxQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {

	@Override
	public QualifiedName getFullyQualifiedName(final EObject obj) {
		if (obj instanceof Variable) {
			EObject cter = obj.eContainer();
			if (cter instanceof ExpressionVariable) {
				// ignore synthetic variables that belong to ExpressionVariable
				return null;
			}
		}

		return super.getFullyQualifiedName(obj);
	}

	protected QualifiedName qualifiedName(Module module) {
		return getConverter().toQualifiedName(module.getPackage());
	}

	protected QualifiedName qualifiedName(Task task) {
		String name = task.getName();
		if (name == null) {
			// anonymous task
			EObject cter = task.eContainer();
			QualifiedName qName = getFullyQualifiedName(cter);
			int size = qName.getSegmentCount();
			String taskName = qName.getSegment(size - 2) + "_" + qName.getLastSegment();
			return qName.skipLast(2).append(taskName);
		}

		// will use task's "name" attribute and container
		return null;
	}

}
