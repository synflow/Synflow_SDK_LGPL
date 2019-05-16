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
package com.synflow.cx.generator;

import static com.synflow.models.util.SwitchUtil.DONE;

import java.util.Iterator;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.Goto;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;
import com.synflow.models.graph.Edge;
import com.synflow.models.graph.Vertex;
import com.synflow.models.graph.visit.ReversePostOrder;
import com.synflow.models.ir.Instruction;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.transform.AbstractIrVisitor;
import com.synflow.models.util.Void;

/**
 * This class defines a beautifier that renames actions/states of the actor's
 * FSM that it visits. It also sorts by topological order.
 *
 * @author Matthieu Wipliez
 *
 */
public class FsmBeautifier {

	private static class GotoRemover extends AbstractIrVisitor {

		@Override
		public Void caseInstruction(Instruction inst) {
			if (inst instanceof Goto) {
				delete(inst);
			}
			return DONE;
		}

	}

	private static final int FIRST = 97;

	private static final int RANGE = (122 - FIRST) + 1;

	/**
	 * Generates a new identifier.
	 *
	 * @return a new identifier
	 */
	private static String newIdentifier(int count) {
		StringBuilder builder = new StringBuilder();
		int work = count;
		do {
			builder.append((char) ((work % RANGE) + FIRST));
			work = (work / RANGE) - 1;
		} while (work >= 0);

		return builder.reverse().toString();
	}

	public static void main(String[] args) {
		System.out.println(newIdentifier(0));
		System.out.println(newIdentifier(1));
		System.out.println(newIdentifier(25));
		System.out.println();
		System.out.println(newIdentifier(26));
		System.out.println(newIdentifier(27));
		System.out.println(newIdentifier(51));
		System.out.println();
		System.out.println(newIdentifier(52));
		System.out.println(newIdentifier(77));
		System.out.println();
		System.out.println(newIdentifier(78));
		System.out.println(newIdentifier(103));
		System.out.println();
		System.out.println(newIdentifier(676));
		System.out.println(newIdentifier(701));
		System.out.println();
		System.out.println(newIdentifier(702));
		System.out.println(newIdentifier(727));
		System.out.println();
		System.out.println(newIdentifier(728));
		System.out.println(newIdentifier(753));
	}

	private FSM fsm;

	private void removeFsm(Actor actor) {
		actor.setFsm(null);

		GotoRemover remover = new GotoRemover();
		for (Action action : actor.getActions()) {
			remover.doSwitch(action.getBody());
		}
	}

	/**
	 * Renames the states of the FSM.
	 *
	 * @param actorName
	 *            base name of the states (simple name of the actor)
	 */
	private void renameStates(String actorName) {
		// the "currentName" is set by a state with a name
		String currentName = null;
		for (State state : fsm.getStates()) {
			String stateName = state.getName();
			if (stateName == null) {
				if (currentName == null) {
					// this is the case for an actor whose first state has no
					// name
					currentName = "FSM_" + actorName;
				}
				state.setName(currentName);
			} else {
				currentName = stateName;
			}
		}

		// rename consecutive states
		Multiset<String> visited = HashMultiset.create();
		for (State state : fsm.getStates()) {
			String name = state.getName();
			int n = visited.count(name);
			if (n > 0) {
				state.setName(name + "_" + n);
			}

			visited.add(name);
		}
	}

	/**
	 * Sorts the FSM by reverse post-order (equivalent to topological order, but
	 * cycle tolerant).
	 */
	private void sortFsm() {
		ReversePostOrder order = new ReversePostOrder(fsm, fsm.getInitialState());
		int i = 0;
		for (Vertex vertex : order) {
			fsm.getVertices().move(i, vertex);
			i++;
		}
	}

	/**
	 * Visits the given actor
	 *
	 * @param actor
	 *            an actor
	 */
	public void visit(Actor actor) {
		fsm = actor.getFsm();

		// if FSM is empty removes it from actor and leave
		if (fsm.getStates().isEmpty()) {
			removeFsm(actor);
			return;
		}

		sortFsm();
		renameStates(actor.getSimpleName());
		visitTransitions();

		// if there is only one state, removes the FSM
		if (fsm.getStates().size() <= 1) {
			int i = 0;
			for (Transition transition : fsm.getTransitions()) {
				actor.getActions().move(i, transition.getAction());
				i++;
			}

			// remove FSM
			removeFsm(actor);
		}
	}

	/**
	 * Visits the given transition, renames actions according to the given name,
	 * and set up the line numbers of its body and scheduler procedures.
	 *
	 * @param transition
	 *            transition
	 * @param name
	 *            name of the action
	 */
	private void visitTransition(Transition transition, String name) {
		Iterator<Integer> it = transition.getLines().iterator();
		int lineNumber = it.hasNext() ? it.next() : 0;

		Action action = transition.getAction();
		action.setName(name);

		Procedure body = action.getBody();
		body.setLineNumber(lineNumber);
		body.setName(name);

		Procedure comb = action.getCombinational();
		comb.setLineNumber(lineNumber);
		comb.setName("comb_" + name);

		Procedure scheduler = action.getScheduler();
		scheduler.setLineNumber(lineNumber);
		scheduler.setName("isSchedulable_" + name);
	}

	/**
	 * Visits all transitions, renaming actions and setting up their line
	 * numbers.
	 */
	private void visitTransitions() {
		for (State state : fsm.getStates()) {
			int i = 0;
			for (Edge edge : state.getOutgoing()) {
				String name = state.getName();
				name += "_" + newIdentifier(i);
				i++;

				visitTransition((Transition) edge, name);
			}
		}
	}

}
