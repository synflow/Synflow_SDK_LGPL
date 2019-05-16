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
 
package com.synflow.models.dpn;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.synflow.models.graph.Graph;

/**
 * This class defines a Finite State Machine (FSM). A FSM is a directed multi-graph, where a vertex
 * is a state, and an edge is a list of actions.
 *
 * @author Matthieu Wipliez
 * @model
 */
public interface FSM extends Graph {

	/**
	 * Returns the initial state.
	 *
	 * @return the initial state
	 * @model
	 */
	State getInitialState();

	/**
	 * Returns the list of this FSM's transitions. This returns the same as {@link #getVertices()}
	 * but as a list of {@link State}s rather than as a list of {@link Vertex}s.
	 *
	 * @return the list of states
	 */
	EList<State> getStates();

	/**
	 * Returns the list of actions that are the target of transitions from the given source state.
	 *
	 * @param source
	 *            a state.
	 * @return the list of actions that are the target of transitions from the given source state
	 */
	List<Action> getTargetActions(State source);

	/**
	 * Returns the list of this FSM's transitions. This returns the same as {@link #getEdges()} but
	 * as a list of {@link Transition}s rather than as a list of edges.
	 *
	 * @return the list of this FSM's transitions
	 */
	EList<Transition> getTransitions();

	/**
	 * Sets the initial state of this FSM to the given state.
	 *
	 * @param state
	 *            a state
	 */
	void setInitialState(State state);

}
