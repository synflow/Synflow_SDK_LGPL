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

import static com.synflow.cx.internal.AstUtil.assign;
import static com.synflow.cx.internal.AstUtil.decrement;
import static com.synflow.cx.internal.AstUtil.expr;
import static com.synflow.cx.internal.AstUtil.not;
import static com.synflow.cx.internal.AstUtil.notZero;
import static com.synflow.cx.internal.AstUtil.type;
import static com.synflow.models.util.SwitchUtil.DONE;

import org.eclipse.emf.ecore.EObject;

import com.synflow.cx.cx.CxFactory;
import com.synflow.cx.cx.ExpressionBinary;
import com.synflow.cx.cx.StatementIdle;
import com.synflow.cx.cx.Variable;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.transform.UniqueNameComputer;
import com.synflow.models.ir.util.TypeUtil;
import com.synflow.models.util.Void;

/**
 * This class defines how to schedule a idle statement.
 *
 * @author Matthieu Wipliez
 *
 */
public class IdleScheduler {

	private static final int IDLE_THRESHOLD = 8;

	private ScheduleFsm schedule;

	public IdleScheduler(ScheduleFsm schedule) {
		this.schedule = schedule;
	}

	/**
	 * Associates the given object to the current transition(s).
	 *
	 * @param eObject
	 *            a statement or an expression
	 * @return DONE
	 */
	protected Void associate(EObject eObject) {
		for (Transition transition : schedule.getTransitions()) {
			transition.getBody().add(eObject);
		}
		return DONE;
	}

	private void createIdleLoop(StatementIdle idle, int numCycles) {
		int size = TypeUtil.getSize(numCycles);

		// create Cx variable
		Variable cntIdle = CxFactory.eINSTANCE.createVariable();
		cntIdle.setType(type(size, false));

		// create IR variable
		String name = new UniqueNameComputer(schedule.actor.getVariables())
				.getUniqueName("cnt_idle");
		Type type = IrFactory.eINSTANCE.createTypeInt(size, false);
		Var irCntIdle = IrFactory.eINSTANCE.createVar(type, name, true);
		schedule.actor.getVariables().add(irCntIdle);

		// add mapping
		schedule.instantiator.putMapping(schedule.actor, cntIdle, irCntIdle);

		// add assign to cntIdle
		Transition transition = schedule.getTransition();
		transition.getBody().add(assign(cntIdle, expr(numCycles)));

		// start new cycle for loop's body
		schedule.startNewCycle();
		transition = schedule.getTransition();
		associate(idle);

		// save 'fork' state
		State fork = transition.getSource();

		// add condition to transition's scheduler, and decrement counter in loop body
		ExpressionBinary cntIdleNotZero = notZero(expr(cntIdle));
		transition.getScheduler().add(cntIdleNotZero);
		transition.getBody().add(decrement(cntIdle));

		// make edges loop back to fork state
		schedule.mergeTransitions(fork);

		// starts a new cycle from the 'fork' state for exit edge and updates its condition
		transition = schedule.startNewCycleFrom(fork);
		transition.getScheduler().add(not(cntIdleNotZero));
	}

	/**
	 * Schedules the given idle statement
	 *
	 * @param idle
	 *            a 'idle' statement
	 */
	public void scheduleIdle(StatementIdle idle) {
		int numCycles = schedule.instantiator.evaluateInt(schedule.actor, idle.getNumCycles());

		// check whether we can start idling in this transition or we need a fence first
		boolean isFenceNeeded = false;
		for (Transition transition : schedule.getTransitions()) {
			if (!Schedule.isEmpty(transition.getBody())) {
				isFenceNeeded = true;
				break;
			}
		}

		if (!isFenceNeeded) {
			associate(idle);
			numCycles--;
		}

		if (numCycles > IDLE_THRESHOLD) {
			createIdleLoop(idle, numCycles - 1);
		} else {
			// adds as many transitions as numCycles
			for (int i = 0; i < numCycles; i++) {
				schedule.startNewCycle();
				associate(idle);
			}
		}

		schedule.startNewCycle();
	}

}
