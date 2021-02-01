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
package com.synflow.cx.internal.instantiation;

import static com.synflow.models.util.SwitchUtil.DONE;
import static com.synflow.models.util.SwitchUtil.visit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.common.collect.Iterables;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.CxType;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.TypeRef;
import com.synflow.cx.cx.Typedef;
import com.synflow.cx.cx.VarRef;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.services.VoidCxSwitch;
import com.synflow.models.graph.Edge;
import com.synflow.models.graph.Graph;
import com.synflow.models.graph.GraphFactory;
import com.synflow.models.graph.Vertex;
import com.synflow.models.util.Void;

/**
 * This class defines a solver of dependency between variables.
 *
 * @author Matthieu Wipliez
 *
 */
public class DependencySolver extends VoidCxSwitch {

	private static class VertexAdapter extends AdapterImpl {

		private final EObject contents;

		public VertexAdapter(EObject contents) {
			Objects.requireNonNull(contents);
			this.contents = contents;
		}

		@SuppressWarnings("unchecked")
		public <T extends EObject> T getContent() {
			return (T) contents;
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return type == getClass();
		}

		@Override
		public String toString() {
			return contents.toString();
		}

	}

	private static VertexAdapter getVertexAdapter(EObject eObject) {
		return (VertexAdapter) EcoreUtil.getAdapter(eObject.eAdapters(), VertexAdapter.class);
	}

	private Vertex declaration;

	private List<EObject> eObjects;

	private Graph graph;

	public DependencySolver() {
		eObjects = new ArrayList<>();
	}

	public void add(Variable variable) {
		eObjects.add(variable);
	}

	public void addAll(Iterable<? extends EObject> iterable) {
		Iterables.addAll(eObjects, iterable);
	}

	@Override
	public Void caseExpressionVariable(ExpressionVariable expr) {
		super.caseExpressionVariable(expr);
		return visit(this, expr.getSource());
	}

	@Override
	public Void caseTypeRef(TypeRef ref) {
		Typedef typedef = ref.getTypeDef();
		return handle(typedef);
	}

	@Override
	public Void caseVariable(Variable variable) {
		CxType type = CxUtil.getType(variable);
		visit(this, type);
		return super.caseVariable(variable);
	}

	@Override
	public Void caseVarRef(VarRef ref) {
		Variable variable = ref.getVariable();
		return handle(variable);
	}

	public void computeOrder() {
		graph = GraphFactory.eINSTANCE.createGraph();
		for (EObject eObject : eObjects) {
			Vertex vertex = GraphFactory.eINSTANCE.createVertex();
			vertex.eAdapters().add(new VertexAdapter(eObject));
			eObject.eAdapters().add(new VertexAdapter(vertex));
			graph.add(vertex);
		}

		for (EObject eObject : eObjects) {
			declaration = getVertexAdapter(eObject).getContent();
			visit(this, eObject);
		}
	}

	public Iterable<EObject> getObjects() {
		Set<EObject> ordered = new LinkedHashSet<>(eObjects.size());
		// use a linked list to remove fast
		List<EObject> workList = new LinkedList<>(eObjects);
		while (!workList.isEmpty()) {
			Iterator<EObject> it = workList.iterator();
			next: while (it.hasNext()) {
				EObject item = it.next();
				Vertex vertex = (Vertex) getVertexAdapter(item).getContent();
				for (Vertex pred : vertex.getPredecessors()) {
					EObject previous = getVertexAdapter(pred).getContent();
					if (!ordered.contains(previous)) {
						continue next;
					}
				}

				it.remove();
				ordered.add(item);
			}
		}

		// remove adapters from objects
		// very important in the case of bundles, whose constants are used in many places
		for (EObject eObject : eObjects) {
			VertexAdapter adapter = getVertexAdapter(eObject);
			eObject.eAdapters().remove(adapter);
		}

		return ordered;
	}

	private Void handle(EObject eObject) {
		VertexAdapter adapter = getVertexAdapter(eObject);
		if (adapter == null) {
			// type not in current entity
			return DONE;
		}

		Edge edge = GraphFactory.eINSTANCE.createEdge();

		Vertex vertex = adapter.getContent();
		edge.setSource(vertex);
		edge.setTarget(declaration);
		graph.add(edge);
		return DONE;
	}

}
