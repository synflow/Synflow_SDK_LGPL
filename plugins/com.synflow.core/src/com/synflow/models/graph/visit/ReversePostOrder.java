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
 
package com.synflow.models.graph.visit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.ecore.EReference;

import com.google.common.collect.Lists;
import com.synflow.models.graph.Edge;
import com.synflow.models.graph.Graph;
import com.synflow.models.graph.GraphPackage;
import com.synflow.models.graph.Vertex;

/**
 * This class defines a reverse post order based on a post-order DFS.
 *
 * @author Matthieu Wipliez
 *
 */
public class ReversePostOrder extends DFS {

	/**
	 * Creates the reverse post-ordering of the given graph, starting from the given roots. If
	 * <code>roots</code> is <code>null</code> or empty, this method visits the graph to find roots.
	 * If no entry vertex is found, the first vertex of the graph is used.
	 *
	 * @param graph
	 *            a graph
	 * @param refEdges
	 *            the EReference that returns either the incoming or outgoing edges of a vertex
	 * @param refVertex
	 *            the EReference that returns either the source or target of an edge
	 * @param roots
	 *            a list of vertex
	 */
	@SuppressWarnings("unchecked")
	public ReversePostOrder(Graph graph, EReference refEdges, EReference refVertex,
			List<? extends Vertex> roots) {
		super(refEdges, refVertex, graph.getVertices().size());

		if (roots == null || roots.isEmpty()) {
			roots = new ArrayList<Vertex>();
			EReference opposite = refVertex.getEOpposite();
			for (Vertex vertex : graph.getVertices()) {
				List<Edge> edges = (List<Edge>) vertex.eGet(opposite);
				if (edges.isEmpty()) {
					((List<Vertex>) roots).add(vertex);
				}
			}
		}

		if (roots.isEmpty()) {
			// no entry point in the graph, take the first vertex
			if (!graph.getVertices().isEmpty()) {
				visitPost(graph.getVertices().get(0));
			}
		} else {
			ListIterator<? extends Vertex> it = roots.listIterator(roots.size());
			while (it.hasPrevious()) {
				visitPost(it.previous());
			}
		}
	}

	/**
	 * Creates the reverse post-ordering of the given graph, starting from the given roots. If
	 * <code>roots</code> is <code>null</code> or empty, this method visits the graph to find roots.
	 * If no entry vertex is found, the first vertex of the graph is used.
	 *
	 * @param graph
	 *            a graph
	 * @param refEdges
	 *            the EReference that returns either the incoming or outgoing edges of a vertex
	 * @param refVertex
	 *            the EReference that returns either the source or target of an edge
	 * @param entries
	 *            entry vertices given individually
	 */
	public ReversePostOrder(Graph graph, EReference refEdges, EReference refVertex,
			Vertex... entries) {
		this(graph, refEdges, refVertex, Arrays.asList(entries));
	}

	/**
	 * Creates the reverse post-ordering of the given graph, starting from the given entries. If
	 * <code>entries</code> is <code>null</code> or empty, this method visits the graph to find
	 * roots. If no entry vertex is found, the first vertex of the graph is used.
	 *
	 * @param graph
	 *            a graph
	 * @param entries
	 *            a list of vertex
	 */
	public ReversePostOrder(Graph graph, List<? extends Vertex> entries) {
		this(graph, GraphPackage.Literals.VERTEX__OUTGOING, GraphPackage.Literals.EDGE__TARGET,
				entries);
	}

	/**
	 * Creates the reverse post-ordering of the given graph, starting from the given entries. This
	 * is a convenience constructor equivalent to <code>this(graph, Arrays.asList(entries));</code>.
	 *
	 * @param graph
	 *            a graph
	 * @param entries
	 *            entry vertices given individually
	 */
	public ReversePostOrder(Graph graph, Vertex... entries) {
		this(graph, Arrays.asList(entries));
	}

	@Override
	public Iterator<Vertex> iterator() {
		return Lists.reverse(vertices).iterator();
	}

}
