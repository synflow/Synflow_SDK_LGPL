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

import static org.eclipse.xtext.nodemodel.util.NodeModelUtils.findNodesForFeature;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider;

import com.google.common.collect.Lists;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.CxPackage;
import com.synflow.cx.cx.CxPackage.Literals;
import com.synflow.cx.cx.Import;
import com.synflow.cx.cx.Imported;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Module;
import com.synflow.cx.cx.Network;
import com.synflow.cx.cx.Variable;

/**
 * This class defines a simple namespace aware scope provider for Java-like imports in Cx.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxImportedNamespaceScopeProvider extends ImportedNamespaceAwareLocalScopeProvider {

	/**
	 * Creates a new imported namespace resolver and adds it to the normalizers list.
	 *
	 * @param normalizers
	 *            a list of normalizers
	 * @param name
	 *            name
	 * @param ignoreCase
	 *            case (in)sensitive flag
	 */
	private void addImportNormalizer(List<ImportNormalizer> normalizers, String name,
			boolean ignoreCase) {
		ImportNormalizer normalizer = createImportedNamespaceResolver(name, ignoreCase);
		if (normalizer != null) {
			normalizers.add(normalizer);
		}
	}

	@Override
	protected ImportNormalizer doCreateImportNormalizer(QualifiedName importedNamespace,
			boolean wildcard, boolean ignoreCase) {
		return new CxImportNormalizer(importedNamespace, wildcard);
	}

	@Override
	public IScope getScope(EObject context, EReference reference) {
		if (context == null)
			throw new NullPointerException("context");

		IScope result = null;
		if (context.eContainer() != null) {
			IScope outer = getScope(context.eContainer(), reference);
			if (context instanceof Network && reference == CxPackage.Literals.VAR_REF__OBJECTS) {
				Network network = (Network) context;
				Iterable<Inst> instances = network.getInstances();
				Iterable<Variable> ports = CxUtil.getPorts(network.getPortDecls());
				result = Scopes.scopeFor(instances, Scopes.scopeFor(ports, outer));
			} else {
				result = outer;
			}
		} else {
			result = getResourceScope(context.eResource(), CxPackage.Literals.IMPORTED__TYPE);
		}
		return getLocalElementsScope(result, context, reference);
	}

	/**
	 * Returns a list of import normalizers.
	 *
	 * @param imports
	 *            a list of import directives
	 * @param ignoreCase
	 *            case (in)sensitive flag
	 * @return
	 */
	private List<ImportNormalizer> getImportNormalizers(EList<Import> imports, boolean ignoreCase) {
		List<ImportNormalizer> resolvers = Lists.newArrayList();
		for (Import imp : imports) {
			for (Imported imported : imp.getImported()) {
				String name = getName(imported);
				if (imported.isWildcard()) {
					addImportNormalizer(resolvers, name + ".*", ignoreCase);
				} else {
					addImportNormalizer(resolvers, name, ignoreCase);
				}
			}
		}
		return resolvers;
	}

	/**
	 * Returns the name of the given object.
	 *
	 * @param to
	 *            an object
	 * @return a name
	 */
	private String getName(Imported imp) {
		List<INode> nodes = findNodesForFeature(imp, Literals.IMPORTED__TYPE);
		if (!nodes.isEmpty()) {
			INode node = nodes.get(0);
			return NodeModelUtils.getTokenText(node);
		}
		return null;
	}

	@Override
	protected List<ImportNormalizer> internalGetImportedNamespaceResolvers(final EObject context,
			boolean ignoreCase) {
		if (context instanceof Module) {
			Module module = (Module) context;
			return getImportNormalizers(module.getImports(), ignoreCase);
		} else if (context instanceof CxEntity) {
			CxEntity entity = (CxEntity) context;
			return getImportNormalizers(entity.getImports(), ignoreCase);
		} else {
			return Collections.emptyList();
		}
	}

}
