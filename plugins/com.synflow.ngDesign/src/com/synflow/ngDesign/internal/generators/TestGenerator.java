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
package com.synflow.ngDesign.internal.generators;

import static com.synflow.core.IProperties.ACTIVE_LOW;
import static com.synflow.core.IProperties.DEFAULT_CLOCK;
import static com.synflow.core.IProperties.PROP_ACTIVE;
import static com.synflow.core.IProperties.PROP_CLOCKS;
import static com.synflow.core.IProperties.PROP_EMPTY;
import static com.synflow.core.IProperties.PROP_NAME;
import static com.synflow.core.IProperties.PROP_RESETS;
import static com.synflow.core.IProperties.PROP_SYNTHETIC;
import static com.synflow.core.IProperties.PROP_TEST;
import static com.synflow.core.IProperties.PROP_TYPE;
import static com.synflow.core.IProperties.RESET_ASYNCHRONOUS;
import static com.synflow.models.ir.util.IrUtil.array;
import static com.synflow.models.ir.util.IrUtil.obj;
import static java.lang.Math.max;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.xtext.xbase.lib.StringExtensions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.dpn.Endpoint;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.Goto;
import com.synflow.models.dpn.Instance;
import com.synflow.models.dpn.Port;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;
import com.synflow.models.ir.BlockBasic;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstCall;
import com.synflow.models.ir.InstReturn;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.util.IrUtil;

/**
 * This class defines a test generator.
 *
 * @author Matthieu Wipliez
 *
 */
public class TestGenerator {

	/**
	 * df = Dpn Factory
	 */
	private DpnFactory df = DpnFactory.eINSTANCE;

	private IrFactory ir = IrFactory.eINSTANCE;

	private void addAssert(Action action, Var port, Expression value) {
		Expression assertExpr = ir.createExprBinary(ir.createExprVar(port), OpBinary.EQ, value);
		InstCall inst = ir.createInstCall();
		inst.setAssert(true);
		inst.getArguments().add(assertExpr);
		action.getBody().getLast().add(inst);
	}

	private void addPrint(Action action, Port port) {
		InstCall inst = ir.createInstCall();
		inst.setPrint(true);
		inst.getArguments().add(ir.createExprString("value on port " + port.getName() + " = "));
		inst.getArguments().add(ir.createExprVar(port));
		action.getBody().getLast().add(inst);
	}

	/**
	 * Add a new transition from source in the given FSM using the given action.
	 *
	 * @param fsm
	 * @param source
	 * @param action
	 * @return the newly-created target state
	 */
	private State addTransition(FSM fsm, State source, Action action) {
		String name = "s" + (Integer.parseInt(source.getName().substring(1)) + 1);

		State target = df.createState(name);
		fsm.add(target);

		// add return true to scheduler
		InstReturn inst = ir.createInstReturn(ir.createExprBool(true));
		action.getScheduler().getLast().add(inst);

		// add goto target to body
		Goto stGoto = DpnFactory.eINSTANCE.createGoto();
		stGoto.setTarget(target);
		IrUtil.getLast(action.getBody().getBlocks()).add(stGoto);

		// add action to actor
		Actor actor = (Actor) fsm.eContainer();
		actor.getActions().add(action);
		action.getBody().setName("action_" + source.getName());
		action.getCombinational().setName("comb_" + source.getName());
		action.getScheduler().setName("isSchedulable_" + action.getBody().getName());

		// add transition referencing action
		Transition transition = df.createTransition(source, target);
		fsm.add(transition);
		transition.setAction(action);

		return target;
	}

	/**
	 * <p>
	 * Wraps the given entity in a network with two instances "stimulus" and "expected". The "stimulus"
	 * instance writes stimulus to <code>entity</code>, and the "expected" instance reads results from
	 * <code>entity</code> and asserts they match expected values.
	 * </p>
	 *
	 * <p>
	 * The network returned has the same name as the entity.
	 * </p>
	 *
	 * @param entity
	 * @return
	 */
	public DPN createDpn(Entity entity) {
		DPN dpn = df.createDPN();
		dpn.init();
		dpn.setFileName(entity.getFileName());
		dpn.setName(entity.getName());

		// just to get access to resource set
		entity.eResource().getContents().add(dpn);

		// add dut instance
		String name = StringExtensions.toFirstLower(entity.getSimpleName());
		Instance dut = df.createInstance(name, entity);
		dpn.add(dut);

		setProperties(dut, entity);
		setProperties(dpn, entity);

		// here we go!
		createStimulus(dpn, dut);
		createExpected(dpn, dut);

		return dpn;
	}

	/**
	 * Creates an "expected" instance and connect the given DUT's outputs to its inputs.
	 *
	 * @param dpn
	 *            wrapper DPN
	 * @param dut
	 *            Design Under Test
	 */
	private void createExpected(DPN dpn, Instance dut) {
		JsonArray clocks = dpn.getProperties().getAsJsonArray(PROP_CLOCKS);
		String clock = clocks.get(clocks.size() - 1).getAsString();
		Instance expected = createInstance(dpn, "expected", clock);

		Map<Port, JsonArray> ports = new HashMap<>();
		JsonObject test = dut.getEntity().getProperties().getAsJsonObject(PROP_TEST);
		int numValues = 0;
		for (Port output : dut.getEntity().getOutputs()) {
			Port input = df.createPort(output.getType(), output.getName(), output.getInterface(),
					expected.getEntity().getInputs());

			Endpoint source = new Endpoint(dut, output);
			Endpoint target = new Endpoint(expected, input);
			dpn.getGraph().add(df.createConnection(source, target));

			if (test != null) {
				JsonArray values = test.getAsJsonArray(input.getName());
				numValues = max(numValues, values.size());
				ports.put(input, values);
			}
		}

		if (numValues == 0) {
			expected.getEntity().getProperties().addProperty(PROP_EMPTY, true);
		}

		FSM fsm = createFsm(expected);
		State source = fsm.getInitialState();

		// unless the entity is combinational, add an empty transition
		if (dut.getEntity().getProperties().getAsJsonArray(PROP_CLOCKS).size() > 0) {
			Action action = df.createActionNop();
			source = addTransition(fsm, source, action);
		}

		for (int i = 0; i < numValues; i++) {
			Action action = df.createActionNop();
			for (Port input : expected.getEntity().getInputs()) {
				JsonArray values = ports.get(input);
				if (i < values.size() && !values.get(i).isJsonNull()) {
					action.getInputPattern().add(input);

					JsonPrimitive primitive = values.get(i).getAsJsonPrimitive();
					Expression value = getExpression(input.getType(), primitive);
					addPrint(action, input);
					addAssert(action, input, value);
				}
			}

			source = addTransition(fsm, source, action);
		}
	}

	private FSM createFsm(Instance instance) {
		FSM fsm = df.createFSM();
		((Actor) instance.getEntity()).setFsm(fsm);

		State state = df.createState("s0");
		fsm.add(state);
		fsm.setInitialState(state);

		return fsm;
	}

	/**
	 * Creates an instance of a newly-created actor using the given name, and adds it to the dpn.
	 *
	 * @param dpn
	 *            wrapper DPN
	 * @param name
	 *            name of the instance to create
	 * @return the new instance
	 */
	private Instance createInstance(DPN dpn, String name, String clock) {
		Actor actor = df.createActor();
		actor.setName(dpn.getPackage() + "." + dpn.getSimpleName() + "_" + name);
		actor.setFileName(dpn.getFileName());

		// set actor properties
		actor.setProperties(obj(PROP_CLOCKS, array(DEFAULT_CLOCK), PROP_RESETS,
				array(obj(PROP_TYPE, RESET_ASYNCHRONOUS, PROP_ACTIVE, ACTIVE_LOW, PROP_NAME, "reset_n")),
				PROP_SYNTHETIC, true));

		Instance instance = df.createInstance(name, actor);
		dpn.add(instance);

		// set instance properties
		instance.setProperties(obj(PROP_CLOCKS, obj("clock", clock), PROP_RESETS, obj("reset_n", "reset_n")));

		return instance;
	}

	/**
	 * Creates a "stimulus" instance and connect its outputs to the given DUT's inputs.
	 *
	 * @param dpn
	 *            wrapper DPN
	 * @param dut
	 *            Design Under Test
	 */
	private void createStimulus(DPN dpn, Instance dut) {
		JsonArray clocks = dpn.getProperties().getAsJsonArray(PROP_CLOCKS);
		Instance stimulus = createInstance(dpn, "stimulus", clocks.get(0).getAsString());

		// add association of reset to reset_stimulus
		stimulus.getProperties().add(PROP_RESETS, obj("reset_n", "reset_stimulus"));

		Map<Port, JsonArray> ports = new HashMap<>();
		JsonObject test = dut.getEntity().getProperties().getAsJsonObject(PROP_TEST);
		int numValues = 0;
		for (Port input : dut.getEntity().getInputs()) {
			Port output = df.createPort(input.getType(), input.getName(), input.getInterface(),
					stimulus.getEntity().getOutputs());

			Endpoint source = new Endpoint(stimulus, output);
			Endpoint target = new Endpoint(dut, input);
			dpn.getGraph().add(df.createConnection(source, target));

			if (test != null) {
				JsonArray values = test.getAsJsonArray(input.getName());
				numValues = max(numValues, values.size());
				ports.put(output, values);
			}
		}

		if (numValues == 0) {
			stimulus.getEntity().getProperties().addProperty(PROP_EMPTY, true);

			// create "always true" action
			Action action = df.createActionNop();
			action.getScheduler().getLast().add(ir.createInstReturn(ir.createExprBool(true)));

			// add action to actor
			Actor actor = (Actor) stimulus.getEntity();
			actor.getActions().add(action);
			action.getBody().setName("action_main");
			action.getCombinational().setName("comb_main");
			action.getScheduler().setName("isSchedulable_main");

			return;
		}

		FSM fsm = createFsm(stimulus);
		State source = fsm.getInitialState();

		for (int i = 0; i < numValues; i++) {
			Action action = df.createActionNop();
			BlockBasic block = action.getBody().getLast();

			for (Port output : stimulus.getEntity().getOutputs()) {
				JsonArray values = ports.get(output);
				if (i < values.size() && !values.get(i).isJsonNull()) {
					action.getOutputPattern().add(output);

					JsonPrimitive primitive = values.get(i).getAsJsonPrimitive();
					Expression value = getExpression(output.getType(), primitive);
					block.add(ir.createInstStore(0, output, value));

					for (Var signal : output.getAdditionalOutputs()) {
						block.add(ir.createInstStore(0, signal, ir.createExprBool(true)));
					}
				}
			}

			source = addTransition(fsm, source, action);
		}
	}

	private Expression getExpression(Type target, JsonPrimitive primitive) {
		Expression expr;
		if (primitive.isBoolean()) {
			expr = ir.createExprBool(primitive.getAsBoolean());
		} else if (primitive.isNumber()) {
			expr = ir.createExprInt(primitive.getAsBigInteger());
		} else if (primitive.isString()) {
			expr = ir.createExprString(primitive.getAsString());
		} else {
			return null;
		}

		return expr;
	}

	private void setProperties(DPN dpn, Entity entity) {
		JsonObject properties = new JsonObject();
		properties.add(PROP_SYNTHETIC, new JsonPrimitive(true));

		JsonArray clocks = new JsonArray();
		JsonArray clocksDeclared = entity.getProperties().getAsJsonArray(PROP_CLOCKS);
		if (clocksDeclared.size() > 0) {
			for (JsonElement clock : clocksDeclared) {
				clocks.add(clock);
			}
		} else {
			// DPN must always have a clock (even though DUT may not)
			clocks.add(DEFAULT_CLOCK);
		}
		properties.add(PROP_CLOCKS, clocks);

		// note: order of resets is important, stimulus needs to be reset first
		JsonArray resets = array(obj("name", "reset_stimulus"), obj("name", "reset_n"));
		properties.add(PROP_RESETS, resets);

		dpn.setProperties(properties);
	}

	private void setProperties(Instance dut, Entity entity) {
		JsonObject clocks = new JsonObject();
		JsonObject resetAssoc = new JsonObject();

		JsonArray clocksDeclared = entity.getProperties().getAsJsonArray(PROP_CLOCKS);
		if (clocksDeclared.size() > 0) {
			// if DUT has clocks
			for (JsonElement clock : clocksDeclared) {
				String clockName = clock.getAsString();
				clocks.add(clockName, new JsonPrimitive(clockName));
			}

			JsonArray resets = entity.getProperties().getAsJsonArray(PROP_RESETS);
			for (JsonElement reset : resets) {
				String resetName = reset.getAsJsonObject().getAsJsonPrimitive(PROP_NAME).getAsString();
				resetAssoc.add(resetName, new JsonPrimitive(resetName));
			}
		}

		dut.setProperties(obj(PROP_CLOCKS, clocks, PROP_RESETS, resetAssoc));
	}

}
