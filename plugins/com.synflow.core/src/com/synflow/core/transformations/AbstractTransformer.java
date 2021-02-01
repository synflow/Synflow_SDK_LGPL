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
 
package com.synflow.core.transformations;

import static com.synflow.core.IProperties.PROP_IMPORTS;
import static com.synflow.models.ir.util.IrUtil.array;
import static com.synflow.models.util.EcoreHelper.getContainerOfType;
import static com.synflow.models.util.SwitchUtil.CASCADE;
import static com.synflow.models.util.SwitchUtil.DONE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil.ExternalCrossReferencer;

import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Instance;
import com.synflow.models.dpn.Unit;
import com.synflow.models.dpn.util.DpnSwitch;
import com.synflow.models.graph.Vertex;
import com.synflow.models.graph.visit.Ordering;
import com.synflow.models.graph.visit.ReversePostOrder;
import com.synflow.models.util.Void;

/**
 * This class defines an abstract code transformer.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class AbstractTransformer extends DpnSwitch<Void> {

	@Override
	public Void caseDPN(DPN dpn) {
		List<Vertex> entries = getEntries(dpn);
		if (!entries.isEmpty()) {
			sortInstances(dpn, entries);
		}

		return CASCADE;
	}

	@Override
	public Void caseEntity(Entity entity) {
		// implement patterns with tests and assignments
		new PatternImplementation().doSwitch(entity);

		for (Transformation transformation : getTransformations()) {
			transformation.doSwitch(entity);
		}

		return DONE;
	}

	/**
	 * Computes the import list of the given entity (sorted by alphabetical order), and adds it to
	 * the "imports" attribute of the template data of the given entity.
	 *
	 * @param entity
	 *            an entity
	 */
	protected void computeImportList(Entity entity) {
		Set<String> imports = new TreeSet<>();

		Map<EObject, Collection<Setting>> crossRefs = ExternalCrossReferencer.find(entity);
		for (Collection<Setting> settings : crossRefs.values()) {
			for (Setting setting : settings) {
				Object object = setting.get(true);
				if (object instanceof EObject) {
					EObject eObject = (EObject) object;
					Unit unit = getContainerOfType(eObject, Unit.class);
					if (unit != null) {
						imports.add(unit.getName());
					}
				}
			}
		}

		entity.getProperties().add(PROP_IMPORTS, array(imports));
	}

	/**
	 * Compute the list of entries
	 *
	 * @param dpn
	 * @return
	 */
	private List<Vertex> getEntries(DPN dpn) {
		List<Vertex> entries = new ArrayList<>();

		// all source instances
		for (Instance instance : dpn.getInstances()) {
			if (instance.getIncoming().isEmpty()) {
				entries.add(instance);
			}
		}

		// all outgoing connections of input ports come from DPN itself
		if (!dpn.getVertex().getOutgoing().isEmpty()) {
			entries.add(dpn.getVertex());
		}

		return entries;
	}

	protected abstract Iterable<Transformation> getTransformations();

	/**
	 * Sorts the instances of the given DPN by topological order/reverse post order. Nothing happens
	 * if no entry vertex can be found; an entry is a vertex with no incoming connections, such as
	 * an input port or an instance with no input ports.
	 *
	 * @param dpn
	 *            a DPN
	 */
	private void sortInstances(DPN dpn, List<Vertex> entries) {
		// sorts instances of the given network with topological order
		Ordering ordering = new ReversePostOrder(dpn.getGraph(), entries);

		final Map<Vertex, Integer> position = new HashMap<Vertex, Integer>();
		int i = 0;
		for (Vertex vertex : ordering) {
			position.put(vertex, i);
			i++;
		}

		// sorts vertices according to topological order
		// vertices not in the "position" map are not sorted
		ECollections.sort(dpn.getInstances(), new Comparator<Instance>() {
			@Override
			public int compare(Instance o1, Instance o2) {
				Integer p1 = position.get(o1);
				if (p1 == null) {
					p1 = 0;
				}

				Integer p2 = position.get(o2);
				if (p2 == null) {
					p2 = 0;
				}
				return p1.compareTo(p2);
			}
		});
	}

}
