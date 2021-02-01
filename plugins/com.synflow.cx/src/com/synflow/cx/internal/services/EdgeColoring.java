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
package com.synflow.cx.internal.services;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;

import com.synflow.core.SynflowCore;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;
import com.synflow.models.graph.Edge;
import com.synflow.models.graph.Vertex;

/**
 * This class defines a simple edge-coloring BFS implementation.
 *
 * @author Matthieu Wipliez
 *
 */
public class EdgeColoring {

	public static final String TYPE = "com.synflow.cx.cycleIndicator";

	private IFile file;

	private Set<Vertex> visited;

	public EdgeColoring() {
		visited = new HashSet<>();
	}

	private void visit(Actor actor) {
		FSM fsm = actor.getFsm();
		if (fsm == null) {
			return;
		}

		State state = fsm.getInitialState();
		visit(state, 0);
	}

	private void visit(Edge edge, int index) {
		Transition transition = (Transition) edge;

		try {
			for (int line : transition.getLines()) {
				IMarker marker = file.createMarker(TYPE);
				marker.setAttribute(IMarker.LINE_NUMBER, line);
				marker.setAttribute("index", index);
			}
		} catch (CoreException e) {
			SynflowCore.log(e);
		}
	}

	public void visit(Entity entity) {
		this.file = entity.getFile();
		if (file == null) {
			return;
		}

		if (entity instanceof Actor) {
			visit((Actor) entity);
		}
	}

	private void visit(Vertex vertex, int index) {
		visited.add(vertex);

		for (Edge edge : vertex.getOutgoing()) {
			visit(edge, index);
		}
		index++;

		for (Vertex succ : vertex.getSuccessors()) {
			if (!visited.contains(succ)) {
				// only visits successor if it has not been visited yet
				visit(succ, index);
			}
		}
	}

}
