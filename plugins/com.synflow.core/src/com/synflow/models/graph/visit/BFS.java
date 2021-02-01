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

import java.util.ArrayDeque;
import java.util.Deque;

import com.synflow.models.graph.Vertex;

/**
 * This class defines Breadth-First Search (BFS) for a graph.
 *
 * @author Matthieu Wipliez
 *
 */
public class BFS extends Ordering {

	/**
	 * Builds the list of vertices that can be reached from the given vertex
	 * using breadth-first search.
	 *
	 * @param vertex
	 *            a vertex
	 */
	public BFS(Vertex vertex) {
		visitVertex(vertex);
	}

	/**
	 * Builds the search starting from the given vertex.
	 *
	 * @param vertex
	 *            a vertex
	 */
	public void visitVertex(Vertex vertex) {
		Deque<Vertex> visitList = new ArrayDeque<Vertex>();
		visitList.addLast(vertex);

		while (!visitList.isEmpty()) {
			Vertex next = visitList.removeFirst();

			// only adds the successors if they have not been visited yet.
			if (!visited.contains(next)) {
				visited.add(next);
				vertices.add(next);
				for (Vertex succ : next.getSuccessors()) {
					visitList.addLast(succ);
				}
			}
		}
	}

}
