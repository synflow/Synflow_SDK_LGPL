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
package com.synflow.cx.internal;

import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines an adapter that records the original object from which a copy is made.
 *
 * @author Matthieu Wipliez
 *
 */
public class CopyOf extends AdapterImpl implements Adapter {

	/**
	 * If the given object is a copy of an original object (it has a CopyOf adapter), returns the
	 * original object. Otherwise, returns the given object.
	 *
	 * @param eObject
	 *            an EObject
	 * @return another EObject if the one given was a copy, or <code>eObject</code> itself otherwise
	 */
	public static final <T extends EObject> T getOriginal(T eObject) {
		List<Adapter> adapters = eObject.eAdapters();
		for (int i = 0; i < adapters.size(); i++) {
			Adapter adapter = adapters.get(i);
			if (adapter instanceof CopyOf) {
				CopyOf copyOf = (CopyOf) adapter;

				@SuppressWarnings("unchecked")
				T original = (T) copyOf.getOriginal();
				return original;
			}
		}
		return eObject;
	}

	private final EObject original;

	public CopyOf(EObject eObject) {
		// it turns out that eObject is often a copy of another object
		// (especially when invoked from IfDeveloper and CycleScheduler)
		// to avoid a multiple (possibly long) copy-of chain, we look for the original object
		// (and if eObject is already an original object, getOriginal will simply return it)
		// also, this way we don't have to make getOriginal recursive
		this.original = getOriginal(eObject);
	}

	public EObject getOriginal() {
		return original;
	}

}
