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
package com.synflow.ngDesign.internal.generators.bytecode;

import static com.google.common.collect.Iterables.filter;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.EXECUTE_COMBINATIONAL;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.EXECUTE_SYNCHRONOUS;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.PORT_CLASS;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.PORT_TYPE;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.VCD_WRITER_CLASS;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.endMethod;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getJavaType;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getPortFlagFieldName;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getPortValueFieldName;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getPortValueFieldType;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getSignature;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.hasCombinationalExecute;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.isSynchronous;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.println;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.push;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.startMethod;
import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.FCONST_0;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.LCONST_0;
import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.RETURN;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import com.google.common.collect.Iterables;
import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.Port;
import com.synflow.models.dpn.State;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Var;

/**
 * This class defines an Actor to bytecode compiler.
 *
 * @author Matthieu Wipliez
 *
 */
public class ActorCompiler extends InstantiableCompiler {

	private static final DpnFactory df = DpnFactory.eINSTANCE;

	protected Actor actor;

	private Map<State, Integer> stateMap;

	/**
	 * Calls the given procedure.
	 *
	 * @param mv
	 *            method visitor
	 * @param procedure
	 *            procedure to call
	 */
	private void call(MethodVisitor mv, Procedure procedure) {
		mv.visitVarInsn(ALOAD, 0);
		String signature = getSignature(procedure);
		mv.visitMethodInsn(INVOKESPECIAL, entityClass, procedure.getName(), signature, false);
	}

	/**
	 * Compile calls to actions.
	 *
	 * @param execute
	 *            execute method visitor
	 * @param actions
	 *            list of actions to test and call
	 */
	private void compileActionCalls(MethodVisitor execute, boolean combinational,
			List<Action> actions) {
		for (int i = 0; i < actions.size(); i++) {
			Action action = actions.get(i);
			call(execute, action.getScheduler());

			Label target = new Label();
			execute.visitJumpInsn(IFEQ, target); // if not schedulable, try next action

			// if schedulable, invokes action combinational/body procedure and then return
			if (combinational) {
				call(execute, action.getCombinational());
			} else {
				call(execute, action.getBody());
			}
			execute.visitInsn(RETURN);

			execute.visitLabel(target);
		}

		execute.visitInsn(RETURN);
	}

	/**
	 * Compile actions
	 *
	 * @param actions
	 *            a list of actions
	 */
	protected void compileActions(EList<Action> actions) {
		for (Action action : actions) {
			BodyCompiler compiler = new BodyCompiler(entityClass, stateMap);
			compiler.compileProcedure(cw, action.getScheduler());
			compiler.compileProcedure(cw, action.getBody());
			compiler.compileProcedure(cw, action.getCombinational());
		}
	}

	@Override
	protected void compileBehavior() {
		FSM fsm = actor.getFsm();
		if (fsm != null) {
			// adds state field (public so we can read it from DPN)
			cw.visitField(ACC_PUBLIC, "_state", "I", null, null);

			stateMap = new HashMap<>();
			int i = 0;
			for (State state : fsm.getStates()) {
				stateMap.put(state, i++);
			}
		}

		if (hasCombinationalExecute(actor)) {
			createExecuteCombinational(actor);
		}

		// combinational actors don't need "commit"
		if (isSynchronous(actor)) {
			createExecuteSynchronous(actor);
			createCommit(actor);
		}

		compileActions(actor.getActions());
	}

	/**
	 * Compiles bytecode to copy the value of ready input ports that need to be buffered.
	 *
	 * @param execute
	 *            execute method
	 * @param ports
	 *            iterable of ready output ports
	 */
	private void compileCopyInternal(MethodVisitor execute, Iterable<Port> ports) {
		for (Port port : ports) {
			execute.visitVarInsn(ALOAD, 0);
			execute.visitFieldInsn(GETFIELD, entityClass, port.getName(), PORT_TYPE);
			execute.visitFieldInsn(GETFIELD, PORT_CLASS, "valid", "Z");

			Label next = new Label();
			execute.visitJumpInsn(IFEQ, next);

			execute.visitVarInsn(ALOAD, 0);
			execute.visitInsn(ICONST_1);
			String name = "internal_" + port.getAdditionalInputs().get(0).getName();
			execute.visitFieldInsn(PUTFIELD, entityClass, name, "Z");

			// copy port value
			execute.visitVarInsn(ALOAD, 0);
			execute.visitInsn(DUP);
			execute.visitFieldInsn(GETFIELD, entityClass, port.getName(), PORT_TYPE);
			execute.visitFieldInsn(GETFIELD, PORT_CLASS, getPortValueFieldName(port),
					getPortValueFieldType(port));
			name = "internal_" + port.getName();
			execute.visitFieldInsn(PUTFIELD, entityClass, name, getJavaType(port.getType()));

			execute.visitLabel(next);
		}
	}

	@Override
	protected MethodVisitor compileEntity(Entity entity) {
		this.actor = (Actor) entity;
		MethodVisitor constructor = super.compileEntity(entity);

		// output ports: initialized by this constructor
		for (Port port : entity.getOutputs()) {
			addPort(ACC_PUBLIC | ACC_FINAL, port, "", constructor); // current value is public
			addPort(ACC_PRIVATE, port, "_next", constructor); // future value is private
		}

		return constructor;
	}

	/**
	 * Compiles stall logic at the beginning of the synchronous execute method.
	 *
	 * @param execute
	 *            execute method
	 * @param ports
	 *            iterable of ready output ports
	 */
	private void compileStall(MethodVisitor execute, Iterable<Port> ports) {
		execute.visitVarInsn(ALOAD, 0);
		execute.visitFieldInsn(GETFIELD, entityClass, "stall", "Z");
		Label notStalled = new Label();
		execute.visitJumpInsn(IFEQ, notStalled);

		// for all ports, if not ready go to label not ready and return
		Label notReady = new Label();
		for (Port port : ports) {
			execute.visitVarInsn(ALOAD, 0);
			execute.visitFieldInsn(GETFIELD, entityClass, port.getName(), PORT_TYPE);
			execute.visitFieldInsn(GETFIELD, PORT_CLASS, "ready", "Z");
			execute.visitJumpInsn(IFEQ, notReady);
		}

		// for each port writes valid = true
		for (Port port : ports) {
			execute.visitVarInsn(ALOAD, 0);
			execute.visitFieldInsn(GETFIELD, entityClass, port.getName() + "_next", PORT_TYPE);
			execute.visitInsn(ICONST_1);
			execute.visitFieldInsn(PUTFIELD, PORT_CLASS, "valid", "Z");
		}

		// stall = false
		execute.visitVarInsn(ALOAD, 0);
		execute.visitInsn(ICONST_0);
		execute.visitFieldInsn(PUTFIELD, entityClass, "stall", "Z");

		// if stalled and not ready, just return
		execute.visitLabel(notReady);
		execute.visitInsn(RETURN);

		// not stalled, continue normally
		execute.visitLabel(notStalled);
	}

	/**
	 * Generates the bytecode to copy the field <code>fieldName</code> with the given IR type.
	 *
	 * @param mv
	 *            method visitor
	 * @param port
	 *            port
	 * @param fieldName
	 *            name of the field in the port's class
	 * @param fieldType
	 *            type of the field
	 */
	private void copy(MethodVisitor mv, Port port, String fieldName, String fieldType) {
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, entityClass, port.getName(), PORT_TYPE);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, entityClass, port.getName() + "_next", PORT_TYPE);

		// copy value
		mv.visitFieldInsn(GETFIELD, PORT_CLASS, fieldName, fieldType);
		mv.visitFieldInsn(PUTFIELD, PORT_CLASS, fieldName, fieldType);
	}

	/**
	 * Creates the commit method in the class for the given actor.
	 *
	 * @param actor
	 *            an actor
	 */
	private void createCommit(Actor actor) {
		MethodVisitor commit = startMethod(cw, ACC_PUBLIC, "commit", "()V");

		for (Port port : actor.getOutputs()) {
			if (!port.isSynchronous()) {
				continue;
			}

			copy(commit, port, getPortValueFieldName(port), getPortValueFieldType(port));
			for (String name : port.getInterface().getOutputs(port.getDirection())) {
				copy(commit, port, name, "Z");
			}
		}

		endMethod(commit);
	}

	/**
	 * Creates the execute method in the class for the given actor.
	 *
	 * @param execute
	 *            execute method
	 * @param actor
	 *            an actor
	 * @param combinational
	 *            true if combinational
	 */
	private void createExecute(MethodVisitor execute, Actor actor, boolean combinational) {
		FSM fsm = actor.getFsm();
		if (fsm == null) {
			compileActionCalls(execute, combinational, actor.getActions());
		} else {
			int size = fsm.getStates().size();
			Label[] labels = new Label[size];
			for (int i = 0; i < size; i++) {
				labels[i] = new Label();
			}

			// loads state field and switch on it
			execute.visitVarInsn(ALOAD, 0);
			execute.visitFieldInsn(GETFIELD, entityClass, "_state", "I");
			Label exit = new Label();
			execute.visitTableSwitchInsn(0, size - 1, exit, labels);

			for (State state : fsm.getStates()) {
				execute.visitLabel(labels[stateMap.get(state)]);
				compileActionCalls(execute, combinational, state.getActions());
			}

			execute.visitLabel(exit);
			println(execute, "illegal state");
			execute.visitInsn(RETURN);
		}

		execute.visitMaxs(0, 0);
		execute.visitEnd();
	}

	/**
	 * Creates the "executeCombinational" method for the given actor
	 *
	 * @param actor
	 *            an actor
	 */
	private void createExecuteCombinational(Actor actor) {
		MethodVisitor execute = startMethod(cw, ACC_PUBLIC, EXECUTE_COMBINATIONAL, "()V");

		// reset output flags of input ports (ready)
		resetPortFlags(execute, actor.getInputs(), true);

		// reset combinational ports and their output flags (valid)
		resetPorts(execute, filter(actor.getOutputs(), port -> !port.isSynchronous()));

		createExecute(execute, actor, true);
	}

	/**
	 * Creates the "executeSynchronous" method for the given actor.
	 *
	 * @param actor
	 *            an actor
	 */
	private void createExecuteSynchronous(Actor actor) {
		MethodVisitor execute = startMethod(cw, ACC_PUBLIC, EXECUTE_SYNCHRONOUS, "()V");

		if (!actor.getBufferedInputs().isEmpty()) {
			compileCopyInternal(execute, actor.getBufferedInputs());
		}

		// reset output flags of synchronous output ports (valid)
		// combinational output ports (and their output flags) are reset by combinational process
		resetPortFlags(execute, filter(actor.getOutputs(), port -> port.isSynchronous()), false);

		// compile stall behavior
		Iterable<Port> readyOutputs = df.getReadyPorts(actor.getOutputs());
		if (!Iterables.isEmpty(readyOutputs)) {
			compileStall(execute, readyOutputs);
		}

		createExecute(execute, actor, false);
	}

	@Override
	protected MethodVisitor createVcdUpdateValues(Entity entity) {
		MethodVisitor mv = super.createVcdUpdateValues(entity);

		for (Port port : entity.getOutputs()) {
			mv.visitVarInsn(ALOAD, 1);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, entityClass, port.getName(), PORT_TYPE);
			mv.visitMethodInsn(INVOKEVIRTUAL, VCD_WRITER_CLASS, "update",
					"(Lcom/synflow/runtime/Port;)V", false);
		}

		return mv;
	}

	/**
	 * Resets port flags for the output ports of the given actor.
	 *
	 * @param execute
	 *            execute method visitor
	 * @param actor
	 *            an actor
	 */
	private void resetPortFlags(MethodVisitor execute, Iterable<Port> ports,
			boolean combinational) {
		for (Port port : ports) {
			String fieldName = port.getName();
			if (port.isSynchronous() && !combinational) {
				fieldName += "_next";
			}

			for (Var signal : port.getAdditionalOutputs()) {
				execute.visitVarInsn(ALOAD, 0);
				execute.visitFieldInsn(GETFIELD, entityClass, fieldName, PORT_TYPE);
				push(execute, false);
				execute.visitFieldInsn(PUTFIELD, PORT_CLASS, getPortFlagFieldName(signal), "Z");
			}
		}
	}

	/**
	 * Resets the content and flags of the given combinational output ports.
	 *
	 * @param execute
	 * @param ports
	 */
	private void resetPorts(MethodVisitor execute, Iterable<Port> ports) {
		for (Port port : ports) {
			execute.visitVarInsn(ALOAD, 0);
			execute.visitFieldInsn(GETFIELD, entityClass, port.getName(), PORT_TYPE);

			String fieldName = getPortValueFieldName(port);
			if ("floatValue".equals(fieldName)) {
				execute.visitInsn(FCONST_0);
			} else if ("intValue".equals(fieldName)) {
				execute.visitInsn(ICONST_0);
			} else if ("longValue".equals(fieldName)) {
				execute.visitInsn(LCONST_0);
			} else if ("value".equals(fieldName)) {
				execute.visitFieldInsn(GETSTATIC, "java/math/BigInteger", "ZERO",
						"Ljava/math/BigInteger;");
			} else {
				throw new IllegalArgumentException();
			}

			String fieldType = getPortValueFieldType(port);
			execute.visitFieldInsn(PUTFIELD, PORT_CLASS, fieldName, fieldType);
		}
		resetPortFlags(execute, ports, true);
	}

}
