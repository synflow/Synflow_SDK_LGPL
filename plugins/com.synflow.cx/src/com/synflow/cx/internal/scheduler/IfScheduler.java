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
package com.synflow.cx.internal.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.cx.internal.scheduler.path.Path;
import com.synflow.cx.internal.scheduler.path.PathIterable;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.Transition;
import com.synflow.models.graph.Edge;
import com.synflow.models.node.Node;
import com.synflow.models.util.SwitchUtil;

/**
 * This class defines a scheduler of 'if' statements.
 *
 * @author Matthieu Wipliez
 *
 */
public class IfScheduler {

	private final Actor actor;

	private final IInstantiator instantiator;

	public IfScheduler(IInstantiator instantiator, Actor actor) {
		this.instantiator = instantiator;
		this.actor = actor;
	}

	private void move(EList<Edge> edges, Transition oldTransition, List<Transition> transitions) {
		int index = edges.indexOf(oldTransition);
		for (Transition transition : transitions) {
			edges.move(index++, transition);
		}
	}

	public void visit() {
		FSM fsm = actor.getFsm();
		for (Transition transition : new ArrayList<>(fsm.getTransitions())) {
			visit(transition);
		}
	}

	private void visit(Transition transition) {
		List<EObject> eObjects = transition.getBody();
		IfAnalyzer analyzer = new IfAnalyzer();
		SwitchUtil.visit(analyzer, eObjects);

		Node node = analyzer.getRoot();
		if (!node.hasChildren()) {
			// no 'if' statement
			return;
		}

		List<Transition> transitions = new ArrayList<>();
		IfDeveloper developer = new IfDeveloper(instantiator, actor);
		for (Path path : new PathIterable(node)) {
			// System.out.println(path);
			transitions.add(developer.visit(transition, path));
		}

		// watch this: we must insert the new transitions AT THE SAME PLACE as the old one
		// why? because order is important: in the case of a loop, we test the condition first
		// so this order MUST BE MAINTAINED
		move(transition.getSource().getOutgoing(), transition, transitions);
		move(transition.getTarget().getIncoming(), transition, transitions);

		FSM fsm = actor.getFsm();
		int index = fsm.getTransitions().indexOf(transition);
		fsm.remove(transition);
		fsm.getTransitions().addAll(index, transitions);
	}

}
