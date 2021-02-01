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
 
package com.synflow.core.internal;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IResource;

import com.synflow.core.layout.ProjectLayout;

/**
 * This class defines a property tester.
 *
 * @author Matthieu Wipliez
 *
 */
public class CorePropertyTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		IResource resource = (IResource) receiver;
		if ("isPackage".equals(property)) {
			return ProjectLayout.isPackage(resource);
		} else if ("isSourceFolder".equals(property)) {
			return ProjectLayout.isSourceFolder(resource);
		}

		return false;
	}

}
