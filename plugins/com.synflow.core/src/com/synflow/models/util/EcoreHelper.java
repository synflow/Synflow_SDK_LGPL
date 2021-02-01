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
 
package com.synflow.models.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

/**
 * This class contains helper methods for Ecore models.
 *
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 *
 */
public class EcoreHelper {

	/**
	 * If eObject is already contained, returns a copy of it.
	 *
	 * @param expression
	 *            a Cx expression
	 * @return <code>expression</code> itself, or a copy of it
	 */
	public static <T extends EObject> T copyIfNeeded(T eObject) {
		if (eObject == null || eObject.eContainer() == null) {
			return eObject;
		}
		return EcoreUtil.copy(eObject);
	}

	/**
	 * Returns the container of <code>ele</code> with the given type, or <code>null</code> if no
	 * such container exists. This method has been copied from the EcoreUtil2 class of Xtext.
	 *
	 * @param <T>
	 *            type parameter
	 * @param ele
	 *            an object
	 * @param type
	 *            the type of the container
	 * @return the container of <code>ele</code> with the given type
	 */
	public static <T extends EObject> T getContainerOfType(EObject ele, Class<T> type) {
		for (EObject e = ele; e != null; e = e.eContainer()) {
			if (type.isInstance(e)) {
				return type.cast(e);
			}
		}

		return null;
	}

	/**
	 * Returns the list that contains this object, or <code>null</code>.
	 *
	 * @param <T>
	 *            type of the objects contained in the list
	 * @param <T1>
	 *            type of the object as a specialization of <code>T</code>
	 * @param eObject
	 *            the object
	 * @return the list that contains this object, or <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	public static <T extends EObject, T1 extends T> List<T> getContainingList(T1 eObject) {
		EStructuralFeature feature = eObject.eContainingFeature();
		if (feature == null || !feature.isMany()) {
			return null;
		}

		Object obj = eObject.eContainer().eGet(feature);
		return List.class.cast(obj);
	}

	/**
	 * Loads the resource that corresponds to the given file, and returns the first object in its
	 * contents that matches the given class, or <code>null</code> otherwise.
	 *
	 * @param file
	 *            a file
	 * @param cls
	 *            expected class
	 * @return an object or <code>null</code>
	 */
	public static <T extends EObject> T getEObject(IFile file, Class<T> cls) {
		ResourceSet set = new ResourceSetImpl();
		((ResourceSetImpl) set).setURIResourceMap(new HashMap<URI, Resource>());
		return getEObject(set, file, cls);
	}

	/**
	 * Loads the resource that corresponds to the given file, and returns the first object in its
	 * contents that matches the given class, or <code>null</code> otherwise.
	 *
	 * @param set
	 *            a resource set
	 * @param file
	 *            a file whose extension is registered within EMF
	 * @param cls
	 *            a class
	 * @return an EObject matching the given class, or <code>null</code>
	 */
	public static <T extends EObject> T getEObject(ResourceSet set, IFile file, Class<T> cls) {
		return Iterables.getFirst(getEObjects(set, file, cls), null);
	}

	/**
	 * Loads the resource that corresponds to the given file, and returns the contents that matches
	 * the given class, or <code>null</code> otherwise.
	 *
	 * @param file
	 *            a file
	 * @param cls
	 *            expected class
	 * @return an iterable of objects (may be empty)
	 */
	public static <T extends EObject> Iterable<T> getEObjects(IFile file, Class<T> cls) {
		ResourceSet set = new ResourceSetImpl();
		((ResourceSetImpl) set).setURIResourceMap(new HashMap<URI, Resource>());
		return getEObjects(set, file, cls);
	}

	/**
	 * Loads the resource that corresponds to the given file, and returns all objects in its
	 * contents that matches the given class.
	 *
	 * @param set
	 *            a resource set
	 * @param file
	 *            a file whose extension is registered within EMF
	 * @return a iterable of objects (may be empty)
	 */
	public static <T extends EObject> Iterable<T> getEObjects(ResourceSet set, IFile file,
			Class<T> cls) {
		String pathName = file.getFullPath().toString();
		URI uri = URI.createPlatformResourceURI(pathName, true);
		try {
			Resource resource = set.getResource(uri, true);
			return Iterables.filter(resource.getContents(), cls);
		} catch (RuntimeException e) {
		}

		return ImmutableSet.of();
	}

	public static IFile getFile(EObject eObject) {
		Resource resource = eObject.eResource();
		if (resource == null) {
			return null;
		}
		return getFile(resource);
	}

	/**
	 * Returns the IFile associated with the given resource, or <code>null</code> if the information
	 * is not available (e.g. the resource plugin is not loaded).
	 *
	 * @param resource
	 *            a resource
	 */
	public static IFile getFile(Resource resource) {
		if (ResourcesPlugin.getPlugin() == null) {
			return null;
		}

		String fullPath = resource.getURI().toPlatformString(true);
		IPath path = new Path(fullPath);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return root.getFile(path);
	}

	/**
	 * Returns an Iterable that contains an iterator that filters descendants of the given object
	 * that match the given class (or one of its subclasses). The iterator does not explore the
	 * descendants of the objects of the given class (in other words, the underlying iterator is
	 * pruned everytime a candidate is found): if O of type T contain objects O1 and O2 both with
	 * the type T, only O will be returned, not O1 nor O2.
	 *
	 * @param eObject
	 *            an object
	 * @param cls
	 *            class of the descendants to match
	 * @return an Iterable
	 */
	public static <T> Iterable<T> getObjects(EObject eObject, final Class<T> cls) {
		final TreeIterator<EObject> it = eObject.eAllContents();
		return new Iterable<T>() {

			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {

					private EObject nextObject;

					@Override
					public boolean hasNext() {
						while (it.hasNext()) {
							EObject next = it.next();
							if (cls.isAssignableFrom(next.getClass())) {
								// prune after next
								it.prune();

								nextObject = next;
								return true;
							}
						}
						return false;
					}

					@Override
					@SuppressWarnings("unchecked")
					public T next() {
						return (T) nextObject;
					}

					@Override
					public void remove() {
						it.remove();
					}
				};
			}

		};
	}

	/**
	 * Creates a new resource set.
	 *
	 * @return a resource set
	 */
	public static ResourceSet newResourceSet() {
		ResourceSetImpl set = new ResourceSetImpl();
		set.setURIResourceMap(new HashMap<URI, Resource>());
		return set;
	}

}
