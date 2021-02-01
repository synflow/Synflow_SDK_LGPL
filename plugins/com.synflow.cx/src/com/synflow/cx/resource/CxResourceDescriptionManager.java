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
package com.synflow.cx.resource;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescription.Manager.AllChangeAware;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionManager;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.CxPackage.Literals;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Instantiable;
import com.synflow.cx.cx.Network;
import com.synflow.cx.instantiation.IInstantiator;

/**
 * This class describes a resource description manager.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxResourceDescriptionManager extends DefaultResourceDescriptionManager implements
		AllChangeAware {

	@Inject
	private IInstantiator instantiator;

	@Override
	public boolean isAffectedByAny(Collection<Delta> deltas, IResourceDescription candidate,
			IResourceDescriptions context) throws IllegalArgumentException {
		for (Delta delta : deltas) {
			IResourceDescription resDesc = delta.getNew();
			if (resDesc == null) {
				// ignore deleted/closed resources
				continue;
			}

			if (!Iterables.isEmpty(candidate.getExportedObjectsByType(Literals.BUNDLE))) {
				// a candidate is a bundle, is it loaded by the deltas?
				if (isAffected(getImportedNames(resDesc), candidate)) {
					return true;
				}
			}

			// check instantiator to see if necessary to revalidate specialized sub-entities
			for (IEObjectDescription objDesc : resDesc.getExportedObjectsByType(Literals.NETWORK)) {
				CxEntity entity = instantiator.getEntity(objDesc.getEObjectURI());
				if (entity != null) {
					Network network = (Network) entity;
					if (isAffected(network, candidate)) {
						return true;
					}
				}
			}
		}

		return isAffected(deltas, candidate, context);
	}

	private boolean isAffected(Network network, IResourceDescription candidate) {
		for (Inst inst : network.getInstances()) {
			Instantiable entity = inst.getEntity();
			if (entity != null) {
				URI uri = EcoreUtil.getURI(entity);
				if (candidate.getURI().equals(uri.trimFragment())) {
					// candidate is being instantiated
					if (instantiator.isSpecialized(uri)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
