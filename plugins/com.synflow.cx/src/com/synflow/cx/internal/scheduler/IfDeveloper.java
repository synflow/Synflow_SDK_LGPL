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

import static com.synflow.cx.internal.AstUtil.and;
import static com.synflow.cx.internal.AstUtil.expr;
import static com.synflow.cx.internal.AstUtil.not;
import static com.synflow.models.util.SwitchUtil.DONE;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Branch;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.Enter;
import com.synflow.cx.cx.Leave;
import com.synflow.cx.cx.StatementIf;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.cx.internal.scheduler.path.Path;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.dpn.Transition;
import com.synflow.models.util.SwitchUtil;
import com.synflow.models.util.Void;

/**
 * This class develops a single transition with 'if' statements into multiple transitions according
 * to a code path. This extends CycleScheduler with cases for statement if (obviously) but also for
 * Enter and Leave. Indeed, these are artificial objects (in the sense not generated directly from
 * the source) created by the CycleScheduler in the first pass. When the IfDeveloper develops a
 * transition, these objects must be associated with the current transition.
 *
 * @author Matthieu Wipliez
 *
 */
public class IfDeveloper extends AbstractCycleScheduler {

	private Path path;

	public IfDeveloper(IInstantiator instantiator, Actor actor) {
		super(instantiator, actor);
	}

	@Override
	protected Void associate(EObject eObject) {
		Transition transition = schedule.getTransition();
		transition.getBody().add(eObject);
		transition.getScheduler().add(eObject);
		return DONE;
	}

	@Override
	public Void caseEnter(Enter enter) {
		// simply associate this enter with the current transition
		associate(enter);
		return DONE;
	}

	@Override
	public Void caseLeave(Leave leave) {
		// simply associate this leave with the current transition
		associate(leave);
		return DONE;
	}

	@Override
	public Void caseStatementIf(StatementIf stmt) {
		if (CxUtil.isIfSimple(stmt)) {
			associate(stmt);
			return DONE;
		}

		Branch chosen = path.getNext();
		List<Branch> branches = stmt.getBranches();
		CxExpression condition = expr(true);
		for (Branch branch : branches) {
			if (branch == chosen) {
				break;
			}

			schedule.visitCondition(this, branch.getCondition());
			condition = and(condition, not(branch.getCondition()));
		}

		Transition transition = schedule.getTransition();
		if (chosen.getCondition() != null) {
			condition = and(condition, chosen.getCondition());
		}

		// adds condition
		transition.getScheduler().add(condition);

		// visits branch
		schedule.visitBranch(this, chosen);

		return DONE;
	}

	/**
	 * Visits the given transition with the given code path.
	 *
	 * @param transition
	 *            a transition
	 * @param path
	 *            the path to take
	 * @return the new transition
	 */
	public Transition visit(Transition transition, Path path) {
		Transition newTrans = DpnFactory.eINSTANCE.createTransition(transition.getSource(),
				transition.getTarget());
		schedule.setTransition(newTrans);

		// adds scheduling conditions of original transition
		newTrans.getAction().getPeekPattern().add(transition.getAction().getPeekPattern());
		newTrans.getScheduler().addAll(transition.getScheduler());

		// set iterator, visit objects, save pattern
		this.path = path;
		SwitchUtil.visit(this, transition.getBody());
		schedule.promotePeeks(transition.getAction());

		return newTrans;
	}

}
