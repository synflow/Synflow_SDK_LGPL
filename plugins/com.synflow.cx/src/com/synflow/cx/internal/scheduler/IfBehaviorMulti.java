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

import com.synflow.models.dpn.State;
import com.synflow.models.node.Node;

/**
 * This class defines a if behavior to handle multi-cycle if statements.
 *
 * @author Matthieu Wipliez
 *
 */
public class IfBehaviorMulti implements IfBehavior {

	private ScheduleFsm schedule;

	private State state;

	public IfBehaviorMulti(ScheduleFsm schedule, State fork) {
		this.schedule = schedule;
		this.state = fork;
	}

	@Override
	public Node fork() {
		return schedule.getNode();
	}

	@Override
	public void join(Node fork) {
		schedule.setNode(fork);
	}

	@Override
	public void startBranch(Node fork) {
		schedule.setNode(new Node(fork));
		schedule.addTransitionFrom(state);
	}

}
