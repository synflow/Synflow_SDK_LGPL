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

import static com.synflow.core.IProperties.PROP_SYNTHETIC;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.EXECUTE_COMBINATIONAL;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.EXECUTE_SYNCHRONOUS;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.PORT_TYPE;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.VCD_DECLARE_SCOPE;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.VCD_UPDATE_VALUES;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.VCD_WRITER_CLASS;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.VCD_WRITER_TYPE;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.endMethod;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getClassName;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getConnectSignature;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getJavaType;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getTypeName;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.hasCombinationalExecute;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.isSynchronous;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.println;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.push;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.startMethod;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.IF_ICMPNE;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.PUTFIELD;

import java.util.List;
import java.util.Optional;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.Argument;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Endpoint;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.Instance;
import com.synflow.models.dpn.Port;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;
import com.synflow.models.graph.Edge;
import com.synflow.models.ir.Type;

/**
 * This class defines a DPN to bytecode compiler.
 *
 * @author Matthieu Wipliez
 *
 */
public class DpnCompiler extends InstantiableCompiler {

	private DPN dpn;

	@Override
	protected void compileBehavior() {
		createExecute(true);
		if (isSynchronous(dpn)) {
			createExecute(false);
			createCommit();
		}

		createMain();
	}

	@Override
	protected MethodVisitor compileEntity(Entity entity) {
		this.dpn = (DPN) entity;

		MethodVisitor constructor = super.compileEntity(dpn);

		// output ports: initialized by this constructor
		for (Port port : entity.getOutputs()) {
			// public not final because they are assigned in connect method
			addPort(ACC_PUBLIC, port, "", null);
		}

		initializeInstances(constructor, dpn.getInstances());
		connectOutputs(constructor);
		return constructor;
	}

	/**
	 * Connects all instances.
	 */
	private void connectInstances(MethodVisitor connect) {
		// connect all instances
		for (Instance instance : dpn.getInstances()) {
			Entity entity = instance.getEntity();

			loadInstance(connect, instance);

			// load arguments (ports from other instances)
			for (Port input : entity.getInputs()) {
				Endpoint endpoint = new Endpoint(instance, input);
				Endpoint incoming = dpn.getIncoming(endpoint);
				if (incoming.hasInstance()) {
					Instance source = incoming.getInstance();
					Entity ent = source.getEntity();
					String sourcePort = incoming.getPort().getName();
					connect.visitVarInsn(ALOAD, 0);
					connect.visitFieldInsn(GETFIELD, entityClass, source.getName(),
							getTypeName(ent));
					connect.visitFieldInsn(GETFIELD, getClassName(ent), sourcePort, PORT_TYPE);
				} else {
					Port port = incoming.getPort();
					connect.visitVarInsn(ALOAD, 1 + dpn.getInputs().indexOf(port));
				}
			}

			// invoke instance.connect(ports)
			String signature = getConnectSignature(entity.getInputs());
			String entityClass = getClassName(entity);
			connect.visitMethodInsn(INVOKEVIRTUAL, entityClass, "connect", signature, false);
		}
	}

	private void connectOutputs(MethodVisitor constructor) {
		// connect this dpn's output ports
		for (Port output : dpn.getOutputs()) {
			Endpoint incoming = dpn.getIncoming(output);

			Instance source = incoming.getInstance();
			Entity entity = source.getEntity();
			String sourcePort = incoming.getPort().getName();

			constructor.visitVarInsn(ALOAD, 0);
			constructor.visitInsn(DUP);
			constructor.visitFieldInsn(GETFIELD, entityClass, source.getName(),
					getTypeName(entity));
			constructor.visitFieldInsn(GETFIELD, getClassName(entity), sourcePort, PORT_TYPE);
			constructor.visitFieldInsn(PUTFIELD, entityClass, output.getName(), PORT_TYPE);
		}
	}

	/**
	 * Creates the "commit" method.
	 */
	private void createCommit() {
		MethodVisitor commit = startMethod(cw, ACC_PUBLIC, "commit", "()V");

		// calls commit on instances of synchronous entities
		for (Instance instance : dpn.getInstances()) {
			Entity entity = instance.getEntity();
			if (isSynchronous(entity)) {
				loadInstance(commit, instance);
				commit.visitMethodInsn(INVOKEVIRTUAL, getClassName(entity), "commit", "()V", false);
			}
		}

		endMethod(commit);
	}

	@Override
	protected MethodVisitor createConnect(Entity entity) {
		MethodVisitor connect = super.createConnect(entity);
		connectInstances(connect);
		return connect;
	}

	/**
	 * Creates the "execute" method.
	 */
	private void createExecute(boolean combinational) {
		String name = combinational ? EXECUTE_COMBINATIONAL : EXECUTE_SYNCHRONOUS;
		MethodVisitor execute = startMethod(cw, ACC_PUBLIC, name, "()V");

		// executes the method for all instances
		for (Instance instance : dpn.getInstances()) {
			Entity entity = instance.getEntity();
			if (combinational && hasCombinationalExecute(entity)
					|| !combinational && isSynchronous(entity)) {
				loadInstance(execute, instance);
				execute.visitMethodInsn(INVOKEVIRTUAL, getClassName(entity), name, "()V", false);
			}
		}

		endMethod(execute);
	}

	/**
	 * Creates the "main" method.
	 */
	private void createMain() {
		MethodVisitor main = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "main",
				"([Ljava/lang/String;)V", null, null);
		main.visitCode();

		main.visitTypeInsn(NEW, entityClass);
		main.visitInsn(DUP);
		main.visitMethodInsn(INVOKESPECIAL, entityClass, "<init>", "()V", false);
		main.visitInsn(DUP);
		main.visitVarInsn(ASTORE, 1);
		main.visitMethodInsn(INVOKEVIRTUAL, entityClass, "connect", "()V", false);

		createVcdWriter(main);

		println(main, "Simulation started");

		if (dpn.getProperties().has(PROP_SYNTHETIC)) {
			// execute stimulus once before anything else
			Instance stimulus = dpn.getInstance("stimulus");
			Entity entity = stimulus.getEntity();

			main.visitVarInsn(ALOAD, 1);
			main.visitFieldInsn(GETFIELD, entityClass, stimulus.getName(), getTypeName(entity));
			executeEntity(main, entity);
		}

		Optional<Instance> last = getLastInstance(dpn);
		Optional<Integer> state = getLastState(last);
		createSimulationLoop(main, last, state);

		endMethod(main);
	}

	private void createSimulationLoop(MethodVisitor main, Optional<Instance> last,
			Optional<Integer> state) {
		Label entry = new Label();
		main.visitLabel(entry);

		main.visitVarInsn(ALOAD, 1);
		executeEntity(main, dpn);

		main.visitVarInsn(ALOAD, 1);
		main.visitVarInsn(ALOAD, 2);
		main.visitInsn(DUP);
		main.visitMethodInsn(INVOKEVIRTUAL, VCD_WRITER_CLASS, "updateTimestamp", "()V", false);
		main.visitMethodInsn(INVOKEVIRTUAL, entityClass, VCD_UPDATE_VALUES,
				"(" + VCD_WRITER_TYPE + ")V", false);

		if (last.isPresent() && state.isPresent()) {
			Instance instance = last.get();
			Entity entity = instance.getEntity();
			main.visitVarInsn(ALOAD, 1);
			main.visitFieldInsn(GETFIELD, entityClass, instance.getName(), getTypeName(entity));
			main.visitFieldInsn(GETFIELD, getClassName(entity), "_state", "I");
			push(main, state.get());
			main.visitJumpInsn(IF_ICMPNE, entry);

			println(main, "End of simulation");
		} else {
			main.visitJumpInsn(GOTO, entry);
		}
	}

	@Override
	protected MethodVisitor createVcdDeclareScope(Entity entity) {
		MethodVisitor mv = super.createVcdDeclareScope(entity);
		for (Instance instance : dpn.getInstances()) {
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(instance.getName());
			mv.visitMethodInsn(INVOKEVIRTUAL, VCD_WRITER_CLASS, "scope", "(Ljava/lang/String;)V",
					false);

			loadInstance(mv, instance);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, getClassName(instance.getEntity()), VCD_DECLARE_SCOPE,
					"(" + VCD_WRITER_TYPE + ")V", false);

			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, VCD_WRITER_CLASS, "upscope", "()V", false);
		}

		return mv;
	}

	@Override
	protected MethodVisitor createVcdUpdateValues(Entity entity) {
		MethodVisitor mv = super.createVcdUpdateValues(entity);
		for (Instance instance : dpn.getInstances()) {
			loadInstance(mv, instance);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, getClassName(instance.getEntity()), VCD_UPDATE_VALUES,
					"(" + VCD_WRITER_TYPE + ")V", false);
		}

		return mv;
	}

	/**
	 * Creates a new VcdWriter, writes an header, and calls vcdDeclareScope on the dpn.
	 *
	 * @param mv
	 */
	private void createVcdWriter(MethodVisitor mv) {
		mv.visitTypeInsn(NEW, VCD_WRITER_CLASS);
		mv.visitInsn(DUP);
		mv.visitLdcInsn(dpn.getSimpleName() + ".vcd");
		mv.visitMethodInsn(INVOKESPECIAL, VCD_WRITER_CLASS, "<init>", "(Ljava/lang/String;)V",
				false);
		mv.visitInsn(DUP);
		mv.visitVarInsn(ASTORE, 2);

		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKEVIRTUAL, VCD_WRITER_CLASS, "writeHeader", "()V", false);

		mv.visitLdcInsn(dpn.getSimpleName());
		mv.visitMethodInsn(INVOKEVIRTUAL, VCD_WRITER_CLASS, "scope", "(Ljava/lang/String;)V",
				false);

		mv.visitVarInsn(ALOAD, 1);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitMethodInsn(INVOKEVIRTUAL, entityClass, VCD_DECLARE_SCOPE,
				"(" + VCD_WRITER_TYPE + ")V", false);

		mv.visitVarInsn(ALOAD, 2);
		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKEVIRTUAL, VCD_WRITER_CLASS, "upscope", "()V", false);
		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKEVIRTUAL, VCD_WRITER_CLASS, "endDefinitions", "()V", false);
		mv.visitMethodInsn(INVOKEVIRTUAL, VCD_WRITER_CLASS, "updateTimestamp", "()V", false);

		mv.visitVarInsn(ALOAD, 1);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitMethodInsn(INVOKEVIRTUAL, entityClass, VCD_UPDATE_VALUES,
				"(" + VCD_WRITER_TYPE + ")V", false);
	}

	/**
	 * Calls the executeCombinational and executeSynchronous methods
	 *
	 * @param main
	 * @param entity
	 */
	private void executeEntity(MethodVisitor main, Entity entity) {
		int opcode = INVOKEVIRTUAL;
		if (entity == dpn) {
			opcode++;
		}

		String entityClass = getClassName(entity);
		if (hasCombinationalExecute(entity)) {
			main.visitInsn(DUP);
			main.visitMethodInsn(opcode, entityClass, EXECUTE_COMBINATIONAL, "()V", false);
		}
		main.visitInsn(DUP);
		main.visitMethodInsn(opcode, entityClass, EXECUTE_SYNCHRONOUS, "()V", false);
		main.visitMethodInsn(opcode, entityClass, "commit", "()V", false);
	}

	private Optional<Instance> getLastInstance(DPN dpn) {
		return dpn.getInstances().stream().filter(instance -> instance.getOutgoing().isEmpty())
				.findFirst();
	}

	private Optional<Integer> getLastState(Optional<Instance> last) {
		return last.map(instance -> {
			Entity entity = instance.getEntity();
			if (entity instanceof Actor) {
				FSM fsm = ((Actor) entity).getFsm();
				if (fsm != null) {
					List<State> states = fsm.getStates();
					int size = states.size();
					for (int i = size - 1; i >= 0; i--) {
						State state = states.get(i);

						boolean hasOutgoing = false;
						for (Edge outgoing : state.getOutgoing()) {
							hasOutgoing |= ((Transition) outgoing).getTarget() != null;
						}

						if (!hasOutgoing) {
							return i;
						}
					}
				}
			}

			return null;
		});
	}

	/**
	 * Creates one field for each instance of the given DPN, and initializes them in the given
	 * constructor method.
	 *
	 * @param constructor
	 *            constructor method
	 * @param instances
	 *            instances
	 */
	private void initializeInstances(MethodVisitor constructor, List<Instance> instances) {
		for (Instance instance : instances) {
			Entity entity = instance.getEntity();
			String typeName = getTypeName(entity);
			cw.visitField(ACC_PRIVATE, instance.getName(), typeName, null, null);

			// load "this" before arguments
			constructor.visitVarInsn(ALOAD, 0);

			StringBuilder builder = new StringBuilder("(");
			ExpressionCompiler exprCompiler = new ExpressionCompiler(constructor);
			for (Argument arg : instance.getArguments()) {
				Type type = arg.getVariable().getType();
				builder.append(getJavaType(type));
				exprCompiler.doSwitch(arg.getValue());
			}
			builder.append(")");
			builder.append(getTypeName(entity));
			String signature = builder.toString();

			constructor.visitMethodInsn(INVOKESTATIC, getClassName(entity), "create", signature,
					false);
			constructor.visitFieldInsn(PUTFIELD, entityClass, instance.getName(), typeName);
		}
	}

	private void loadInstance(MethodVisitor mv, Instance instance) {
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, entityClass, instance.getName(),
				getTypeName(instance.getEntity()));
	}

}
