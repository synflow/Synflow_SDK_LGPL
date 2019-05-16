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
package com.synflow.cx.ui.internal.views.fsm.layout;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.State;
import com.synflow.models.graph.Vertex;
import com.synflow.models.graph.visit.DominatorComputer;

/**
 * This class implements a layout algorithm for the vertices of an FSM.
 *
 * @author Matthieu Wipliez
 *
 */
public class VertexLayout {

	private static final int SEP_X = 50;

	private static final int SEP_Y = 70;

	private static final int START_X = 120;

	private static final int START_Y = -50;

	public VertexLayout() {
	}

	private Box createBoxTree(FSM fsm) {
		Vertex init = fsm.getInitialState();
		DominatorComputer computer = new DominatorComputer(fsm, init, false);
		Map<Vertex, Vertex> dom = computer.computeDominance();

		// new Dota().print(fsm, "D:\\tmp", "toto.txt");
		List<Vertex> vertices = computer.getVertices();

		Iterator<Vertex> it = vertices.iterator();

		// the current box starts at the top
		Box top = new Box(START_X, START_Y);
		Box current = top;

		// The previous vertex is initially null. Two things can happen:
		// - the first vertex is a fork, a new box is created, and previous
		// is set as usual
		// - the first vertex is not a fork/join, then it has no immediate
		// dominator, and dom.get(vertex) == null == previous,
		// and previous is set as usual
		Vertex previous = null;

		while (it.hasNext()) {
			Vertex vertex = it.next();

			if (isFork(vertex)) {
				// fork vertex
				updateLocation(vertex, current);

				// creates a new box
				current = new Box(current);

				current.x -= SEP_X;
			} else if (isJoin(vertex)) {
				// join vertex

				// update size of box
				current.updateSize();

				// go back to parent
				if (current.getParent() != null) {
					Box parent = current.getParent();
					parent.y = current.getBottom();
					current = parent;
				}

				// place vertex
				updateLocation(vertex, current);
			} else {
				if (dom.get(vertex) == previous) {
					// normal behavior: previous immediately dominates vertex
				} else {
					// switching branch
					Box parent = current.getParent();
					current.x = parent.x + SEP_X;
					current.y = parent.y;
				}

				updateLocation(vertex, current);
			}

			previous = vertex;
		}

		top.updateSize();
		return top;
	}

	/**
	 * A vertex is a fork if it has more than two successors other than itself.
	 *
	 * @param vertex
	 *            a vertex
	 * @return <code>true</code> if <code>vertex</code> is a valid fork
	 */
	private boolean isFork(Vertex vertex) {
		List<Vertex> successors = vertex.getSuccessors();
		int size = successors.size();
		if (successors.contains(vertex)) {
			size--;
		}

		for (Vertex succ : successors) {
			if (vertex.getNumber() < succ.getNumber()) {
				return false;
			}
		}

		return size >= 2;
	}

	/**
	 * A vertex is a join if it has more than two predecessors other than
	 * itself.
	 *
	 * @param vertex
	 *            a vertex
	 * @return <code>true</code> if <code>vertex</code> is a valid join
	 */
	private boolean isJoin(Vertex vertex) {
		List<Vertex> predecessors = vertex.getPredecessors();
		int size = predecessors.size();
		if (predecessors.contains(vertex)) {
			size--;
		}

		for (Vertex succ : vertex.getSuccessors()) {
			if (vertex.getNumber() < succ.getNumber()) {
				return false;
			}
		}

		return size >= 2;
	}

	public Box layoutVertices(FSM fsm) {
		Box tree = createBoxTree(fsm);
		return tree;
	}

	private void updateLocation(Vertex vertex, Box current) {
		State state = (State) vertex;
		current.add(state);
		current.y += SEP_Y;
		state.put("location", current.getLocation());
	}

}
