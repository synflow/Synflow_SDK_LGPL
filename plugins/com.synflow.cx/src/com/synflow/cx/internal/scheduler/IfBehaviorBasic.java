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

import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.node.Node;

/**
 * This class defines a basic if behavior for use in CycleDetector based on Schedule.
 *
 * @author Matthieu Wipliez
 *
 */
public class IfBehaviorBasic implements IfBehavior {

	private Schedule schedule;

	public IfBehaviorBasic(Schedule schedule) {
		this.schedule = schedule;
	}

	@Override
	public Node fork() {
		return schedule.getNode();
	}

	@Override
	public void join(Node fork) {
		schedule.setNode(fork);
		Action action = schedule.getAction();
		for (Node child : fork.getChildren()) {
			DpnFactory.eINSTANCE.addPatterns(action, schedule.getAction(child));
		}
		fork.clearChildren();
	}

	@Override
	public void startBranch(Node fork) {
		Node node = new Node(fork, DpnFactory.eINSTANCE.copy((Action) fork.getContent()));
		schedule.setNode(node);
	}

}
