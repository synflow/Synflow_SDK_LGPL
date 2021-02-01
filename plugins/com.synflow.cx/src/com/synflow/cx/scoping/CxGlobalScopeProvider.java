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
package com.synflow.cx.scoping;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider;

import com.google.common.base.Predicate;

/**
 * This class extends the default global scope provider with a parent scope that knows about
 * built-in components.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxGlobalScopeProvider extends DefaultGlobalScopeProvider {

	@Override
	protected IScope getScope(final Resource resource, boolean ignoreCase, EClass type,
			Predicate<IEObjectDescription> filter) {
		ComponentScope parent = new ComponentScope(IScope.NULLSCOPE, resource.getResourceSet());
		return getScope(parent, resource, ignoreCase, type, filter);
	}

}
