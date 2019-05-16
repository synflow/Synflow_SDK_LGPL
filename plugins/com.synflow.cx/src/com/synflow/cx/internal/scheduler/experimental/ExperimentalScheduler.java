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
package com.synflow.cx.internal.scheduler.experimental;

import static com.synflow.cx.internal.AstUtil.and;
import static com.synflow.cx.internal.AstUtil.expr;
import static com.synflow.cx.internal.AstUtil.gotoState;
import static com.synflow.cx.internal.AstUtil.not;

import java.util.ArrayList;
import java.util.List;

import com.synflow.cx.cx.Block;
import com.synflow.cx.cx.Branch;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.CxFactory;
import com.synflow.cx.cx.StatementIf;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.cx.internal.AstUtil;
import com.synflow.cx.internal.scheduler.CycleScheduler;
import com.synflow.cx.internal.scheduler.ICycleListener;
import com.synflow.cx.internal.scheduler.IfBehavior;
import com.synflow.cx.internal.scheduler.ScheduleFsm;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;
import com.synflow.models.node.Node;

public class ExperimentalScheduler extends CycleScheduler {

	private class CycleListener implements ICycleListener {

		private Branch branch;

		private Node fork;

		public CycleListener(Node fork, Branch branch) {
			this.fork = fork;
			this.branch = branch;
		}

		@Override
		public void newCycleStarted() {
			schedule.removeListener(this);

			Transition currentTransition = schedule.getTransition();

			System.out.println("newCycleStarted");
			for (Transition transition : ScheduleFsm.getTransitions(fork)) {
				System.out.println(transition);
				if (transition != currentTransition) {
					addGoto(transition, currentTransition.getSource(), branch.getCondition());
				}
			}

			// Transition transition = ScheduleFsm.getTransition(fork);
			// addGoto(transition, currentTransition.getSource(), branch.getCondition());
		}

	}

	public ExperimentalScheduler(IInstantiator instantiator, Actor actor) {
		super(instantiator, actor);
	}

	private void addGoto(Transition transition, State target, CxExpression condition) {
		StatementIf _if = getIfWithGotos(transition.getBody());
		if (_if == null) {
			_if = CxFactory.eINSTANCE.createStatementIf();
			transition.getBody().add(_if);
		}

		Block block = CxFactory.eINSTANCE.createBlock();
		block.getStmts().add(gotoState(target));

		Branch _branch = CxFactory.eINSTANCE.createBranch();
		_branch.setCondition(AstUtil.copy(condition));
		_branch.setBody(block);
		_if.getBranches().add(_branch);
	}

	/**
	 * Translates a multi-cycle if statement.
	 *
	 * @param stmtIf
	 */
	protected void translateMultiCycleIf(StatementIf stmtIf) {
		IfBehavior behavior = new IfBehaviorMulti(schedule);
		Node forkNode = behavior.fork();

		// visits all branches
		List<CxExpression> previousConditions = new ArrayList<>();
		for (Branch branch : stmtIf.getBranches()) {
			ICycleListener listener = new CycleListener(forkNode, branch);
			schedule.addListener(listener);

			behavior.startBranch(forkNode);

			// reverse previous conditions
			CxExpression condition = expr(true);
			for (CxExpression previous : previousConditions) {
				// visit each previous condition as if it were reversed
				// to properly update peek patterns
				schedule.visitCondition(this, previous);
				condition = and(condition, not(previous));
			}

			// visit this branch's condition
			if (branch.getCondition() != null) {
				condition = and(condition, branch.getCondition());
				previousConditions.add(branch.getCondition());
			}

			// visits the branch
			schedule.visitBranch(this, branch);

			schedule.removeListener(listener);
		}

		behavior.join(forkNode);
	}

}
