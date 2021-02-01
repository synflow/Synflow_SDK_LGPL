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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.synflow.models.graph.Vertex;

/**
 * This class defines an ordering.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class Ordering implements Iterable<Vertex> {

	protected final List<Vertex> vertices;

	protected final Set<Vertex> visited;

	/**
	 * Creates a new topological sorter.
	 */
	public Ordering() {
		vertices = new ArrayList<Vertex>();
		visited = new HashSet<Vertex>();
	}

	/**
	 * Creates a new topological sorter.
	 *
	 * @param n
	 *            the expected number of vertices
	 */
	protected Ordering(int n) {
		vertices = new ArrayList<Vertex>(n);
		visited = new HashSet<Vertex>(n);
	}

	@Override
	public Iterator<Vertex> iterator() {
		return vertices.iterator();
	}

}
