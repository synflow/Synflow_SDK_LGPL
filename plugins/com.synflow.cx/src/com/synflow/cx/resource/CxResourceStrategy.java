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
package com.synflow.cx.resource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy;
import org.eclipse.xtext.util.IAcceptor;

import com.google.common.collect.ImmutableMap;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Bundle;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.CxType;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Obj;
import com.synflow.cx.cx.Typedef;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.services.CxPrinter;
import com.synflow.models.dpn.InterfaceType;

/**
 * This class describes a strategy that exports ports and bundle variables. It creates object
 * descriptions with user data that describes the type and value (if any) of variables.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxResourceStrategy extends DefaultResourceDescriptionStrategy {

	@Override
	public boolean createEObjectDescriptions(EObject eObject,
			IAcceptor<IEObjectDescription> acceptor) {
		if (eObject instanceof Variable) {
			Variable variable = (Variable) eObject;
			if (CxUtil.isPort(variable)) {
				createVariable(variable, acceptor);
			} else {
				Bundle bundle = EcoreUtil2.getContainerOfType(variable, Bundle.class);
				if (bundle != null) {
					// only bundles export constants and functions
					createVariable(variable, acceptor);
				}
			}
		} else if (eObject instanceof Typedef) {
			Typedef typedef = (Typedef) eObject;
			Bundle bundle = EcoreUtil2.getContainerOfType(typedef, Bundle.class);
			if (bundle != null) {
				// only bundles export typedefs
				createTypedef(typedef, acceptor);
			}
		} else if (eObject instanceof Inst) {
			Inst inst = (Inst) eObject;
			createInst(inst, acceptor);
		} else {
			return super.createEObjectDescriptions(eObject, acceptor);
		}

		// no need to visit contents of variable or typedef
		return false;
	}

	private void createInst(Inst inst, IAcceptor<IEObjectDescription> acceptor) {
		Map<String, String> userData = null;

		// if inst has arguments, adds it to user data
		Obj obj = inst.getArguments();
		if (obj != null) {
			userData = ImmutableMap.of("properties", new CxPrinter().toString(obj));
		}

		// create eobject description
		QualifiedName qualifiedName = getQualifiedNameProvider().getFullyQualifiedName(inst);
		if (qualifiedName != null) {
			acceptor.accept(EObjectDescription.create(qualifiedName, inst, userData));
		}
	}

	private void createTypedef(Typedef typedef, IAcceptor<IEObjectDescription> acceptor) {
		QualifiedName qualifiedName = getQualifiedNameProvider().getFullyQualifiedName(typedef);
		if (qualifiedName != null && typedef.getType() != null) {
			String type = new CxPrinter().toString(typedef.getType());
			Map<String, String> userData = ImmutableMap.of("type", type);
			acceptor.accept(EObjectDescription.create(qualifiedName, typedef, userData));
		}
	}

	private void createVariable(Variable variable, IAcceptor<IEObjectDescription> acceptor) {
		StringBuilder builder = new StringBuilder();
		Iterator<Variable> it = variable.getParameters().iterator();
		if (it.hasNext()) {
			builder.append('(');
			getType(builder, it.next());
			while (it.hasNext()) {
				builder.append(',');
				getType(builder, it.next());
			}
			builder.append(")->");
		}
		getType(builder, variable);

		for (CxExpression dim : variable.getDimensions()) {
			builder.append('[');
			new CxPrinter(builder).doSwitch(dim);
			builder.append(']');
		}

		// add type to user data
		Map<String, String> userData = new HashMap<>(2);
		userData.put("type", builder.toString());

		// if variable has value, adds it to user data
		EObject value = variable.getValue();
		if (value != null) {
			userData.put("value", new CxPrinter().toString(value));
		}

		// add interface type for ports
		if (CxUtil.isPort(variable)) {
			InterfaceType iface = CxUtil.getInterface(variable);
			userData.put("interface", iface.toString());
		}

		// create eobject description
		QualifiedName qualifiedName = getQualifiedNameProvider().getFullyQualifiedName(variable);
		if (qualifiedName != null) {
			acceptor.accept(EObjectDescription.create(qualifiedName, variable, userData));
		}
	}

	private void getType(StringBuilder builder, Variable variable) {
		CxType type = CxUtil.getType(variable);
		if (type != null) {
			new CxPrinter(builder).doSwitch(type);
		}
	}

}
