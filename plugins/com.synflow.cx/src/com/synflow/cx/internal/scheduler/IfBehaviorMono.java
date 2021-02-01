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

import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.node.Node;

/**
 * This class defines a if behavior to handle mono-cycle if statements.
 *
 * @author Matthieu Wipliez
 *
 */
public class IfBehaviorMono implements IfBehavior {

	private Action action;

	private List<Action> actions;

	private ScheduleFsm schedule;

	public IfBehaviorMono(ScheduleFsm schedule) {
		this.schedule = schedule;
		this.action = schedule.getAction();
		actions = new ArrayList<>();
	}

	@Override
	public Node fork() {
		return schedule.getNode();
	}

	@Override
	public void join(Node fork) {
		schedule.getTransition().setAction(action);

		schedule.setNode(fork);
		for (Action branchAction : actions) {
			DpnFactory.eINSTANCE.addPatterns(action, branchAction);
		}
		fork.clearChildren();
	}

	@Override
	public void startBranch(Node fork) {
		Action copy = DpnFactory.eINSTANCE.copy(action);
		schedule.getTransition().setAction(copy);
		actions.add(copy);

		schedule.setNode(new Node(fork, fork.getContent()));
	}

}
