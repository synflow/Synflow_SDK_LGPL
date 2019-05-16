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

import static com.synflow.cx.CxConstants.PROP_AVAILABLE;
import static com.synflow.cx.CxConstants.PROP_READ;
import static com.synflow.cx.CxConstants.PROP_READY;
import static com.synflow.models.util.SwitchUtil.DONE;
import static com.synflow.models.util.SwitchUtil.visit;

import org.eclipse.emf.ecore.EObject;

import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Branch;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.StatementFence;
import com.synflow.cx.cx.StatementIdle;
import com.synflow.cx.cx.StatementIf;
import com.synflow.cx.cx.StatementLoop;
import com.synflow.cx.cx.StatementWrite;
import com.synflow.cx.cx.VarRef;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.cx.services.VoidCxSwitch;
import com.synflow.models.dpn.Actor;
import com.synflow.models.node.Node;
import com.synflow.models.util.Void;

/**
 * This class defines a cycle detector.
 *
 * @author Matthieu Wipliez
 *
 */
public class CycleDetector extends VoidCxSwitch implements ICycleListener {

	private final Schedule schedule;

	/**
	 * Creates a new cycle detector with a new schedule.
	 */
	public CycleDetector(IInstantiator instantiator, Actor actor) {
		schedule = new Schedule(instantiator, actor);
	}

	/**
	 * Creates a new cycle detector with a copy of the given schedule, so if cycle breaks occur, the
	 * given schedule is not modified.
	 *
	 * @param schedule
	 *            a schedule
	 */
	public CycleDetector(Schedule schedule) {
		this.schedule = new Schedule(schedule);
	}

	@Override
	public Void caseExpressionVariable(ExpressionVariable expr) {
		VarRef ref = expr.getSource();
		String property = expr.getPropertyName();
		if (PROP_READ.equals(property)) {
			schedule.read(ref);
		} else if (PROP_AVAILABLE.equals(property)) {
			schedule.available(ref);
		} else if (PROP_READY.equals(property)) {
			schedule.ready(ref);
		}

		Variable variable = ref.getVariable();
		super.caseExpressionVariable(expr);
		if (CxUtil.isFunctionNotConstant(variable)) {
			// if variable is a function with side-effect, we visit it
			doSwitch(variable);
		}

		return DONE;
	}

	@Override
	public Void caseStatementFence(StatementFence stmt) {
		schedule.startNewCycle();
		return DONE;
	}

	@Override
	public Void caseStatementIdle(StatementIdle stmt) {
		schedule.startNewCycle();
		return DONE;
	}

	@Override
	public Void caseStatementIf(StatementIf stmtIf) {
		IfBehavior ifBehavior = new IfBehaviorBasic(schedule);
		Node fork = ifBehavior.fork();
		for (Branch branch : stmtIf.getBranches()) {
			ifBehavior.startBranch(fork);
			schedule.visitBranch(this, branch);
		}
		ifBehavior.join(fork);

		return DONE;
	}

	@Override
	public Void caseStatementLoop(StatementLoop stmt) {
		if (CxUtil.isLoopSimple(schedule.instantiator, schedule.actor, stmt)) {
			return DONE;
		}

		visit(this, stmt.getInit());
		schedule.startNewCycle();

		// visit condition and body
		schedule.visitCondition(this, stmt.getCondition());
		visit(this, stmt.getBody(), stmt.getAfter());

		// starts a new cycle
		schedule.startNewCycle();

		// this deserves an explanation
		// we visit the condition again because we're on the exit edge of the loop
		// and we must record the peeks we do here
		schedule.visitCondition(this, stmt.getCondition());

		return DONE;
	}

	@Override
	public Void caseStatementWrite(StatementWrite stmt) {
		schedule.write(this, stmt);
		return DONE;
	}

	/**
	 * Returns <code>true</code> if the given object contains cycle breaks.
	 *
	 * @param eObject
	 *            an EObject
	 * @return a boolean indicating if the object has cycle breaks
	 */
	public boolean hasCycleBreaks(EObject eObject) {
		Schedule oldSchedule = schedule;
		try {
			oldSchedule.addListener(this);
			doSwitch(eObject);
			return false;
		} catch (CycleBreakException e) {
			return true;
		} finally {
			oldSchedule.removeListener(this);
		}
	}

	@Override
	public void newCycleStarted() {
		throw new CycleBreakException();
	}

}
