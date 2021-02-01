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
package com.synflow.cx.internal.scheduler;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableSet;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.Port;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;
import com.synflow.models.node.Node;

/**
 * This class defines FSM capabilities on top of the Schedule class. It overrides
 * {@link #startNewCycle()} to create a new transition, and has several methods to manipulate the
 * FSM being created.
 *
 * @author Matthieu
 *
 */
public class ScheduleFsm extends Schedule {

	private static void fillTransitions(List<Transition> transitions, Node node) {
		transitions.add(getTransition(node));
		for (Node child : node.getChildren()) {
			fillTransitions(transitions, child);
		}
	}

	/**
	 * Returns the transition associated with the given node.
	 *
	 * @param node
	 *            a node
	 * @return a transition
	 */
	public static final Transition getTransition(Node node) {
		return (Transition) node.getContent();
	}

	/**
	 * Returns the current transitions.
	 *
	 * @return a transition
	 */
	public static final Iterable<Transition> getTransitions(Node node) {
		if (node.hasChildren()) {
			List<Transition> transitions = new ArrayList<>();
			fillTransitions(transitions, node);
			return transitions;
		}
		return ImmutableSet.of(getTransition(node));
	}

	/**
	 * Creates a new empty schedule that will use the given actor.
	 */
	public ScheduleFsm(IInstantiator instantiator, Actor actor) {
		super(instantiator, actor);

		setNode(new Node());

		FSM fsm = actor.getFsm();
		if (fsm == null) {
			// if the actor has no FSM, create one
			fsm = DpnFactory.eINSTANCE.createFSM();
			actor.setFsm(fsm);

			// adds an initial state
			State source = DpnFactory.eINSTANCE.createState();
			fsm.add(source);
			fsm.setInitialState(source);

			// and adds a transition departing from that state
			addTransitionFrom(source);
		}
	}

	/**
	 * Adds a transition from the given source state to a new target state (whose name is taken from
	 * the stateName attribute).
	 *
	 * @param source
	 *            source state
	 */
	protected final Transition addTransitionFrom(State source) {
		FSM fsm = (FSM) source.eContainer();

		// create new transition
		Transition transition = DpnFactory.eINSTANCE.createTransition();
		transition.setSource(source);
		fsm.add(transition);

		// create action and associate to transition
		createAction(transition);

		// update node
		getNode().setContent(transition);

		return transition;
	}

	private void createAction(Transition transition) {
		Action action = DpnFactory.eINSTANCE.createActionNop();
		actor.getActions().add(action);
		transition.setAction(action);
	}

	/**
	 * Adds a new state and merges all transitions at this state.
	 *
	 * @return the new state
	 */
	public State fence() {
		State join = DpnFactory.eINSTANCE.createState();
		getFsm().add(join);
		mergeTransitions(join);
		return join;
	}

	@Override
	protected Action getAction(Node node) {
		return getTransition(node).getAction();
	}

	Actor getActor() {
		return actor;
	}

	public FSM getFsm() {
		return actor.getFsm();
	}

	/**
	 * Returns the current transition.
	 *
	 * @return a transition
	 */
	public Transition getTransition() {
		return getTransition(getNode());
	}

	/**
	 * Returns the current transitions.
	 *
	 * @return a transition
	 */
	protected final Iterable<Transition> getTransitions() {
		return getTransitions(getNode());
	}

	@Override
	protected boolean hasBeenRead(Port port) {
		for (Transition transition : getTransitions()) {
			if (transition.getAction().getInputPattern().contains(port)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean hasBeenWritten(Port port) {
		for (Transition transition : getTransitions()) {
			if (transition.getAction().getOutputPattern().contains(port)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns <code>true</code> if there are more than one open-ended transition in parallel at
	 * this point. Equivalent to <code>getNode().hasChildren()</code>.
	 *
	 * @return a boolean
	 */
	public boolean hasMultipleTransitions() {
		return getNode().hasChildren();
	}

	/**
	 * Merges all the current transitions at the given join state. Promotes peeks of all transitions
	 * in the process, and clears the current node's children afterwards.
	 *
	 * @param join
	 *            a join state
	 */
	public void mergeTransitions(State join) {
		for (Transition transition : getTransitions()) {
			promotePeeks(transition.getAction());

			// only merges transition if it has a dangling target
			if (transition.getTarget() == null) {
				transition.setTarget(join);
			}
		}
		getNode().clearChildren();
	}

	/**
	 * Sets the name of the current transition's source (or if it is already set, target).
	 *
	 * @param name
	 *            state name
	 */
	public void setStateName(String name) {
		Transition transition = getTransition();
		if (transition != null && transition.getSource()!= null) {
			State source = transition.getSource();

			if (source.getName() == null) {
				source.setName(name);
			}
		}
	}

	public void setTransition(Transition transition) {
		getNode().setContent(transition);
		createAction(transition);
	}

	@Override
	public void startNewCycle() {
		// adds a new state and join all transitions at this state
		State join = fence();

		// start a new cycle from target
		startNewCycleFrom(join);
	}

	/**
	 * Starts a new cycle from the given source state.
	 *
	 * @param source
	 *            source state
	 * @return the new transition created
	 */
	public Transition startNewCycleFrom(State source) {
		Transition transition = addTransitionFrom(source);
		newCycleStarted();
		return transition;
	}

}
