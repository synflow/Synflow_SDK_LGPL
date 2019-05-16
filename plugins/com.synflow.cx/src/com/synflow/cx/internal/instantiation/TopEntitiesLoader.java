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
package com.synflow.cx.internal.instantiation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.synflow.cx.cx.Bundle;
import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.CxPackage.Literals;

/**
 * This class defines a loader of top-level entities.
 *
 * @author Matthieu Wipliez
 *
 */
public class TopEntitiesLoader {

	@Inject
	private IResourceDescription.Manager manager;

	@Inject
	private ResourceDescriptionsProvider provider;

	/**
	 * If the given qualified name is the name of a bundle, or the name of a variable or type
	 * defined by a bundle, adds the bundle to the <code>bundleUris</code> set.
	 *
	 * @param bundleUris
	 *            a set of bundle URIs
	 * @param descs
	 *            an IResourceDescriptions
	 * @param name
	 *            a qualified name
	 */
	private void addBundle(Set<URI> bundleUris, IResourceDescriptions descs, QualifiedName name) {
		Iterable<IEObjectDescription> objDescs;
		objDescs = descs.getExportedObjects(EcorePackage.Literals.EOBJECT, name, false);
		for (IEObjectDescription objDesc : objDescs) {
			EClass type = objDesc.getEClass();
			if (type == Literals.BUNDLE) {
				bundleUris.add(objDesc.getEObjectURI());
			} else if (type == Literals.VARIABLE || type == Literals.TYPEDEF) {
				addBundle(bundleUris, descs, name.skipLast(1));
			}
		}
	}

	/**
	 * Collects the URIs of all resource descriptions that are available.
	 *
	 * @param resourceSet
	 *            resource set
	 * @return a set of URIs
	 */
	private Collection<URI> collectAllURIs(ResourceSet resourceSet) {
		Set<URI> allUris = Sets.newLinkedHashSet();

		IResourceDescriptions resourceDescriptions = provider.getResourceDescriptions(resourceSet);

		// collect bundles first, because we need to map their typedef declarations
		EClass type = Literals.BUNDLE;
		for (IEObjectDescription objDesc : resourceDescriptions.getExportedObjectsByType(type)) {
			// we normalize the URI because URIs of reference descriptions are normalized too
			// note that 'normalized' by EMF means from resource to plugin
			URI uri = resourceSet.getURIConverter().normalize(objDesc.getEObjectURI());
			allUris.add(uri);
		}

		// collect instantiable entities
		type = Literals.INSTANTIABLE;
		for (IEObjectDescription objDesc : resourceDescriptions.getExportedObjectsByType(type)) {
			// we normalize the URI because URIs of reference descriptions are normalized too
			// note that 'normalized' by EMF means from resource to plugin
			URI uri = resourceSet.getURIConverter().normalize(objDesc.getEObjectURI());

			// filters out objects whose URI is platform:/plugin (they can never be 'top' URIs)
			if (!uri.isPlatformPlugin()) {
				allUris.add(uri);
			}
		}

		return allUris;
	}

	public Iterable<Bundle> loadBundles(ResourceSet resourceSet, CxEntity entity) {
		Set<Bundle> bundles = Sets.newLinkedHashSet();
		Resource resource = entity.eResource();
		if (resource != null) {
			IResourceDescription resDesc = manager.getResourceDescription(resource);
			IResourceDescriptions descs = provider.getResourceDescriptions(resourceSet);

			// URIs of bundles
			Set<URI> bundleUris = Sets.newHashSet();
			for (QualifiedName name : resDesc.getImportedNames()) {
				addBundle(bundleUris, descs, name);
			}

			// load bundles
			for (URI uri : bundleUris) {
				Bundle bundle = (Bundle) resourceSet.getEObject(uri, true);
				if (bundle != entity) {
					bundles.add(bundle);
				}
			}
		}

		return bundles;
	}

	private Iterable<CxEntity> loadEntities(ResourceSet resourceSet, Collection<URI> topUris) {
		IResourceDescriptions resourceDescriptions = provider.getResourceDescriptions(resourceSet);

		// loads objects from topUris
		// note that URIs in topUris must be normalized in the Xtext sense for this to work
		// (platform:/plugin mapped to platform:/resource)
		List<CxEntity> entities = new ArrayList<>(topUris.size());
		for (URI uri : topUris) {
			URI uriRes = uri.trimFragment();
			IResourceDescription resDesc = resourceDescriptions.getResourceDescription(uriRes);
			EClass type = Literals.CX_ENTITY;
			for (IEObjectDescription objDesc : resDesc.getExportedObjectsByType(type)) {
				if (uri.equals(objDesc.getEObjectURI())) {
					EObject resolved = EcoreUtil.resolve(objDesc.getEObjectOrProxy(), resourceSet);
					entities.add((CxEntity) resolved);
				}
			}
		}
		return entities;
	}

	/**
	 * Finds all CxEntity objects that are at the top of the hierarchy. Computed as the set of URIs
	 * of all entities, minus the set of URIs of entities that are instantiated. Currently the
	 * collection this method returns includes bundles.
	 *
	 * @param resourceSet
	 *            a resource set from which we obtain an IResourceDescriptions object and that we
	 *            use for solving proxies
	 * @return an iterable over CxEntity
	 */
	public Iterable<CxEntity> loadTopEntities(ResourceSet resourceSet) {
		Collection<URI> allUris = collectAllURIs(resourceSet);
		Collection<URI> topUris = removeInstantiated(resourceSet, allUris);
		return loadEntities(resourceSet, topUris);
	}

	private Collection<URI> removeInstantiated(ResourceSet resourceSet, Collection<URI> allUris) {
		Collection<URI> topUris = allUris;
		IResourceDescriptions resourceDescriptions = provider.getResourceDescriptions(resourceSet);

		// remove all entities that are instantiated
		// we use the manager to get an IResourceDescription because
		// ResourceDescriptionsProvider may return CopiedResourceDescriptions
		// which do not have reference descriptions
		for (IResourceDescription resDesc : resourceDescriptions.getAllResourceDescriptions()) {
			URI uri = resDesc.getURI();
			Resource resource;
			try {
				resource = resourceSet.getResource(uri, true);
			} catch (WrappedException e) {
				// resource can't be created/loaded, just skip
				continue;
			}

			resDesc = manager.getResourceDescription(resource);
			for (IReferenceDescription refDesc : resDesc.getReferenceDescriptions()) {
				if (refDesc.getEReference() == Literals.INST__ENTITY) {
					URI uriInstantiable = refDesc.getTargetEObjectUri();
					topUris.remove(uriInstantiable);
				}
			}
		}
		return topUris;
	}

}
