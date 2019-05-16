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
 
package com.synflow.models.graph.visit;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.ecore.EReference;

import com.synflow.models.graph.Edge;
import com.synflow.models.graph.GraphPackage;
import com.synflow.models.graph.Vertex;

/**
 * This class defines Depth-First Search (DFS) for a graph.
 *
 * @author Matthieu Wipliez
 *
 */
public class DFS extends Ordering {

	private boolean containsCycle = false;

	/**
	 * current number. Starts at one.
	 */
	private int num = 1;

	private final EReference refEdges;

	private final EReference refVertex;

	/**
	 * Creates a new DFS. This constructor only delegates to
	 * <code>super(n)</code> and does not perform any visit.
	 *
	 * @param n
	 *            the expected number of vertices
	 */
	protected DFS(EReference refEdges, EReference refVertex, int n) {
		super(n);
		this.refEdges = refEdges;
		this.refVertex = refVertex;
	}

	/**
	 * Initializes this DFS for a graph of <code>n</code> vertices. Does not
	 * perform any kind of visit.
	 *
	 * @param n
	 *            the expected number of vertices
	 */
	public DFS(int n) {
		this(GraphPackage.Literals.VERTEX__OUTGOING,
				GraphPackage.Literals.EDGE__TARGET, n);
	}

	/**
	 * Builds the list of vertices that can be reached from the given vertex
	 * using depth-first search. Equivalent to <code>this(vertex, false)</code>
	 *
	 * @param vertex
	 *            a vertex
	 * @see {@link #DFS(Vertex, boolean)}
	 */
	public DFS(Vertex vertex) {
		this(vertex, false);
	}

	/**
	 * Builds the list of vertices that can be reached from the given vertex
	 * using depth-first search.
	 *
	 * @param vertex
	 *            a vertex
	 * @param order
	 *            if <code>true</code>, the vertices are visited in a post-order
	 *            manner, otherwise in a pre-order manner
	 * @throws NullPointerException
	 *             if the given vertex is not contained in a graph
	 */
	public DFS(Vertex vertex, boolean order) {
		this(vertex.getGraph().getVertices().size());

		if (order) {
			visitPost(vertex);
		} else {
			visitPre(vertex);
		}
	}

	/**
	 * Returns true if the visited graph contains at least one cycle.
	 *
	 * @return true if the visited graph contains at least one cycle
	 */
	public boolean containsCycle() {
		return containsCycle;
	}

	/**
	 * Visits the given vertex. Adds it to the vertices list and sets its
	 * number.
	 *
	 * @param v
	 *            a vertex
	 */
	protected void visit(Vertex v) {
		vertices.add(v);
		v.setNumber(num);
		num++;
	}

	/**
	 * Visits the given vertex and its successors in a post-order manner. The
	 * successors are visited in a reverse order (i.e. last successor is visited
	 * first) to give a more natural reverse post-ordering.
	 *
	 * @param v
	 *            a vertex
	 */
	public final void visitPost(Vertex v) {
		visited.add(v);

		@SuppressWarnings("unchecked")
		List<Edge> edges = (List<Edge>) v.eGet(refEdges);
		ListIterator<Edge> it = edges.listIterator(edges.size());
		while (it.hasPrevious()) {
			Vertex w = (Vertex) it.previous().eGet(refVertex);
			if (!visited.contains(w)) {
				visitPost(w);
			} else {
				containsCycle = true;
			}
		}

		visit(v);
	}

	/**
	 * Visits the given vertex and its successors in a pre-order manner.
	 *
	 * @param v
	 *            a vertex
	 */
	public final void visitPre(Vertex v) {
		visited.add(v);
		visit(v);

		@SuppressWarnings("unchecked")
		List<Edge> edges = (List<Edge>) v.eGet(refEdges);
		for (Edge edge : edges) {
			Vertex w = (Vertex) edge.eGet(refVertex);
			if (!visited.contains(w)) {
				visitPre(w);
			} else {
				containsCycle = true;
			}
		}
	}

}
