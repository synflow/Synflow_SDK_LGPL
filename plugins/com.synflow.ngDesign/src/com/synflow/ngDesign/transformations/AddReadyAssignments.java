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
package com.synflow.ngDesign.transformations;

import static com.google.common.collect.Iterables.isEmpty;
import static com.google.common.collect.Iterables.transform;
import static com.synflow.models.util.SwitchUtil.DONE;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.synflow.core.transformations.Transformation;
import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.Port;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;
import com.synflow.models.graph.Edge;
import com.synflow.models.ir.BlockBasic;
import com.synflow.models.ir.Def;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstStore;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.Var;
import com.synflow.models.util.Void;

/**
 * This class transforms an actor to add combinational assignments to "ready" signals, and updates
 * port references for
 *
 * @author Matthieu
 *
 */
public class AddReadyAssignments extends Transformation {

	private static final DpnFactory dpn = DpnFactory.eINSTANCE;

	private static final IrFactory ir = IrFactory.eINSTANCE;

	private Var stall;

	public AddReadyAssignments() {
		super(null);
	}

	private void addCombinationalAssignments(FSM fsm, List<Action> actions) {
		for (State state : fsm.getStates()) {
			Iterable<Port> ports = computeReadyPorts(state);

			for (Edge outgoing : state.getOutgoing()) {
				Transition transition = (Transition) outgoing;
				BlockBasic block = transition.getAction().getCombinational().getLast();

				for (Port port : computeReadyPorts(transition.getTarget())) {
					Var ready = port.getAdditionalOutputs().get(0);
					block.add(ir.createInstStore(ready, ir.not(stall)));
				}
			}

			Action defaultAction = createDefaultAction("defaultAction_" + state.getName(), ports);
			actions.add(defaultAction);

			Transition transition = DpnFactory.eINSTANCE.createTransition(state, null);
			transition.setAction(defaultAction);
		}
	}

	/**
	 * Adds combinational assignments to the list of actions in an actor with no FSM.
	 *
	 * @param actions
	 *            a list of actions
	 */
	private void addCombinationalAssignments(List<Action> actions) {
		Iterable<Port> ports = computeReadyPorts(actions);
		for (Action action : actions) {
			BlockBasic block = action.getCombinational().getLast();

			for (Port port : ports) {
				Var ready = port.getAdditionalOutputs().get(0);
				block.add(ir.createInstStore(ready, ir.not(stall)));
			}
		}

		actions.add(createDefaultAction("defaultAction", ports));
	}

	/**
	 * Adds a store to the stall variable at the end of the body of each action that has ready
	 * output ports in its output pattern.
	 *
	 * @param actions
	 *            a list of actions
	 */
	private void addStallAssignment(List<Action> actions) {
		for (Action action : actions) {
			Expression condition = null;
			for (Port port : dpn.getReadyPorts(action.getOutputPattern().getPorts())) {
				Var ready = port.getAdditionalInputs().get(0);
				condition = ir.or(condition, ir.not(ready));
			}

			// if any of the ready output ports is not ready, stall
			if (condition != null) {
				BlockBasic last = action.getBody().getLast();
				last.add(ir.createInstStore(0, stall, condition));
			}
		}
	}

	@Override
	public Void caseActor(Actor actor) {
		Iterable<Port> readyInputs = dpn.getReadyPorts(actor.getInputs());
		Iterable<Port> readyOutputs = dpn.getReadyPorts(actor.getOutputs());
		if (isEmpty(readyInputs) && isEmpty(readyOutputs)) {
			return DONE;
		}

		if (!isEmpty(readyOutputs)) {
			// add stall register
			stall = ir.createVar(ir.createTypeBool(), "stall", true);
			actor.getVariables().add(stall);

			updateValidAssignments(readyOutputs);

			// assign stall at the end of the body of each action that writes to ready outputs
			addStallAssignment(actor.getActions());
		}

		// add combinational assignments to ready signals
		FSM fsm = actor.getFsm();
		if (fsm == null) {
			addCombinationalAssignments(actor.getActions());
		} else {
			addCombinationalAssignments(fsm, actor.getActions());
		}

		return DONE;
	}

	private Iterable<Port> computeReadyPorts(Iterable<Action> actions) {
		Set<Port> ports = new LinkedHashSet<>();
		for (Action action : actions) {
			for (Port port : action.getInputPattern().getPorts()) {
				if (port.getInterface().isSyncReady()) {
					ports.add(port);
				}
			}
		}
		return ports;
	}

	private Iterable<Port> computeReadyPorts(State state) {
		return computeReadyPorts(
				transform(state.getOutgoing(), edge -> ((Transition) edge).getAction()));
	}

	/**
	 * Creates a default action.
	 *
	 * @param name
	 * @param ports
	 * @return
	 */
	private Action createDefaultAction(String name, Iterable<Port> ports) {
		Action dummy = DpnFactory.eINSTANCE.createActionNop();
		dummy.setName(name);
		dummy.getBody().setName(name);
		dummy.getScheduler().setName("isSchedulable_" + name);
		dummy.getCombinational().setName("comb_" + name);

		dummy.getScheduler().getLast().add(ir.createInstReturn(ir.createExprBool(true)));

		BlockBasic block = dummy.getCombinational().getLast();

		for (Port port : ports) {
			Var ready = port.getAdditionalOutputs().get(0);
			Var valid = port.getAdditionalInputs().get(0);
			block.add(ir.createInstStore(ready, ir.and(ir.not(stall), ir.not(valid))));
		}

		return dummy;
	}

	/**
	 * Replaces InstStore(valid, true) by InstStore(valid, !ready) for the given ready output ports.
	 *
	 * @param ports
	 *            ready output ports
	 */
	private void updateValidAssignments(Iterable<Port> ports) {
		for (Port port : ports) {
			Var ready = port.getAdditionalInputs().get(0);
			Var valid = port.getAdditionalOutputs().get(0);
			for (Def def : valid.getDefs()) {
				InstStore store = (InstStore) def.eContainer();
				store.setValue(ir.createExprVar(ready));
			}
		}
	}

}
