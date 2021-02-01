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

import static com.synflow.cx.cx.CxPackage.Literals.VAR_REF__OBJECTS;
import static com.synflow.cx.cx.CxPackage.Literals.INST__ENTITY;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.scoping.impl.SimpleScope;
import org.eclipse.xtext.scoping.impl.SingletonScope;

import com.google.inject.Inject;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Instantiable;
import com.synflow.cx.cx.Named;
import com.synflow.cx.cx.VarRef;

/**
 * This class defines a scope provider for use when serializing cross references, because the
 * default scope provider provides qualified name, and this is not what Xtext's default reference
 * updater seems to expect. Also this is how Xtend does it, so I suppose this is the "official" way
 * :-/
 *
 * @author Matthieu Wipliez
 *
 */
public class CxSerializerScopeProvider implements IScopeProvider {

	@Inject
	private IScopeProvider delegate;

	private IScope createInstSerializationScope(Inst inst) {
		Instantiable instantiable = inst.getEntity();
		String name = instantiable.getName();
		return new SingletonScope(EObjectDescription.create(name, instantiable), IScope.NULLSCOPE);
	}

	private IScope createReferenceSerializationScope(VarRef ref) {
		List<IEObjectDescription> descriptions = new ArrayList<>();
		for (Named named : ref.getObjects()) {
			if (!named.eIsProxy() && !(named.eContainer() instanceof ExpressionVariable)) {
				descriptions.add(EObjectDescription.create(named.getName(), named));
			}
		}

		return new SimpleScope(descriptions);
	}

	@Override
	public IScope getScope(EObject context, EReference reference) {
		if (reference == VAR_REF__OBJECTS) {
			IScope result = createReferenceSerializationScope((VarRef) context);
			return result;
		} else if (reference == INST__ENTITY) {
			IScope result = createInstSerializationScope((Inst) context);
			return result;
		}
		return delegate.getScope(context, reference);
	}

}
