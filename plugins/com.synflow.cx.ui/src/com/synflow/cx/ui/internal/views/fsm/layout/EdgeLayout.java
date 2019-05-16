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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.geometry.Point;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;
import com.synflow.models.graph.Edge;

/**
 * This class implements a layout algorithm for the edges of an FSM.
 *
 * @author Matthieu Wipliez
 *
 */
public class EdgeLayout {

	private static final int BREADTH = 30;

	private void layoutDoubleEdges(List<State> states) {
		for (State state : states) {
			Multimap<State, Transition> map = HashMultimap.create();
			for (Edge edge : state.getOutgoing()) {
				map.put((State) edge.getTarget(), (Transition) edge);
			}

			for (State target : map.keySet()) {
				Collection<Transition> transitions = map.get(target);
				if (transitions.size() >= 2) {
					Point l1 = state.get("location");
					Point l2 = target.get("location");

					int inc = BREADTH / (transitions.size() - 1);
					int x = (l1.x + l2.x) / 2 - BREADTH / 2 + 15;
					final int y = (l1.y + l2.y + 20) / 2;

					for (Transition transition : transitions) {
						List<Bendpoint> bendpoints = new ArrayList<>();
						AbsoluteBendpoint bp;
						bp = new AbsoluteBendpoint(x, y - 5);
						bendpoints.add(bp);

						bp = new AbsoluteBendpoint(x, y + 5);
						bendpoints.add(bp);

						setBendpoints(transition, bendpoints);
						x += inc;
					}
				}
			}
		}
	}

	/**
	 * Lay out the edges of the given FSM based on the given box information.
	 *
	 * @param fsm
	 *            FSM
	 * @param top
	 *            top box
	 */
	public void layoutEdges(FSM fsm, Box top) {
		layoutDoubleEdges(fsm.getStates());
		layoutFeedbackEdges(top, fsm.getTransitions());
	}

	private void layoutFeedbackEdges(Box top, List<Transition> transitions) {
		List<Transition> feedbackEdges = new ArrayList<>();

		// filter feedback edges
		for (Transition transition : transitions) {
			State source = transition.getSource();
			State target = transition.getTarget();

			int sourceN = source.getNumber();
			int targetN = target.getNumber();
			if (sourceN < targetN) {
				Point l1 = source.get("location");
				Point l2 = target.get("location");
				int height = Math.abs(l2.y - l1.y);
				transition.put("height", height);

				feedbackEdges.add(transition);
			}
		}

		// sort by ascending height
		Collections.sort(feedbackEdges, new Comparator<Transition>() {

			@Override
			public int compare(Transition o1, Transition o2) {
				Integer h1 = o1.get("height");
				Integer h2 = o2.get("height");
				return h1.compareTo(h2);
			}

		});

		int x = top.getStartX() - 20;
		for (Transition transition : feedbackEdges) {
			State source = transition.getSource();
			State target = transition.getTarget();

			List<Bendpoint> bendpoints = new ArrayList<>();
			Point l1 = source.get("location");
			Point l2 = target.get("location");

			int minX = Math.min(l1.x, l2.x) - 15;

			int height = transition.get("height");
			int edgeX;
			if (height > 100) {
				x -= 10;
				edgeX = x;
			} else {
				edgeX = minX;
			}

			AbsoluteBendpoint bp;
			bp = new AbsoluteBendpoint(l1.x + 15, l1.y + 60);
			bendpoints.add(bp);

			bp = new AbsoluteBendpoint(edgeX, l1.y + 60);
			bendpoints.add(bp);

			bp = new AbsoluteBendpoint(edgeX, l2.y + 15);
			bendpoints.add(bp);

			setBendpoints(transition, bendpoints);
		}
	}

	private void setBendpoints(Transition transition, List<Bendpoint> bendpoints) {
		transition.put("bendpoints", bendpoints);
	}

}
