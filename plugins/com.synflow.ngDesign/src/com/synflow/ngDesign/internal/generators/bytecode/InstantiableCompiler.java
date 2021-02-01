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

import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.PORT_CLASS;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.PORT_TYPE;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.VCD_DECLARE_SCOPE;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.VCD_UPDATE_VALUES;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.VCD_WRITER_CLASS;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.VCD_WRITER_TYPE;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getClassName;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getConnectSignature;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getJavaType;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.push;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.startMethod;
import static org.objectweb.asm.Opcodes.AALOAD;
import static org.objectweb.asm.Opcodes.AASTORE;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ANEWARRAY;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.PUTFIELD;

import org.objectweb.asm.MethodVisitor;

import com.synflow.models.dpn.Direction;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.InterfaceType;
import com.synflow.models.dpn.Port;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.util.TypeUtil;

/**
 * This class extends EntityCompiler with methods for actor & dpn.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class InstantiableCompiler extends EntityCompiler {

	public static final int READY = 2;

	public static final int VALID = 1;

	protected void addPort(int access, Port port, String suffix, MethodVisitor mv) {
		String fieldName = port.getName() + suffix;
		cw.visitField(access, fieldName, PORT_TYPE, null, null);

		if (mv != null) {
			mv.visitVarInsn(ALOAD, 0);
			// create port and initialize field with it
			mv.visitTypeInsn(NEW, PORT_CLASS);
			mv.visitInsn(DUP);

			// determine port flags
			push(mv, getPortFlags(port));
			push(mv, TypeUtil.getSize(port.getType()));
			mv.visitMethodInsn(INVOKESPECIAL, PORT_CLASS, "<init>", "(II)V", false);
			mv.visitFieldInsn(PUTFIELD, entityClass, fieldName, PORT_TYPE);
		}
	}

	protected abstract void compileBehavior();

	@Override
	protected MethodVisitor compileEntity(Entity entity) {
		MethodVisitor constructor = super.compileEntity(entity);

		// input ports: must be connected to another entity's output port
		for (Port port : entity.getInputs()) {
			addPort(ACC_PRIVATE, port, "", null);
		}

		return constructor;
	}

	/**
	 * Creates the connect method in the class for the given entity.
	 *
	 * @param entity
	 *            an entity
	 */
	protected MethodVisitor createConnect(Entity entity) {
		String signature = getConnectSignature(entity.getInputs());
		MethodVisitor connect = startMethod(cw, ACC_PUBLIC, "connect", signature);

		int i = 1;
		for (Port port : entity.getInputs()) {
			connect.visitVarInsn(ALOAD, 0);
			connect.visitVarInsn(ALOAD, i++); // load port
			connect.visitFieldInsn(PUTFIELD, getClassName(entity), port.getName(), PORT_TYPE);
		}

		return connect;
	}

	protected MethodVisitor createVcdDeclareScope(Entity entity) {
		MethodVisitor mv = startMethod(cw, ACC_PUBLIC, VCD_DECLARE_SCOPE,
				"(" + VCD_WRITER_TYPE + ")V");

		cw.visitField(ACC_PRIVATE, "_signals", "[Ljava/lang/String;", null, null);

		int size = entity.getVariables().size();

		mv.visitVarInsn(ALOAD, 0);
		push(mv, size);
		mv.visitTypeInsn(ANEWARRAY, "Ljava/lang/String;");
		mv.visitFieldInsn(PUTFIELD, entityClass, "_signals", "[Ljava/lang/String;");

		for (Port port : entity.getInputs()) {
			declarePort(mv, port);
		}

		int i = 0;
		for (Var var : entity.getVariables()) {
			if (include(var)) {
				declareVariable(mv, var.getType(), var.getName(), i++);
			}
		}

		for (Port port : entity.getOutputs()) {
			declarePort(mv, port);
		}

		return mv;
	}

	/**
	 * Creates the vcdUpdateValues method, with calls to update state variables of the given entity.
	 *
	 * @param entity
	 * @return
	 */
	protected MethodVisitor createVcdUpdateValues(Entity entity) {
		MethodVisitor mv = startMethod(cw, ACC_PUBLIC, VCD_UPDATE_VALUES,
				"(" + VCD_WRITER_TYPE + ")V");

		int i = 0;
		for (Var var : entity.getVariables()) {
			if (include(var)) {
				updateVar(mv, var, i++);
			}
		}

		return mv;
	}

	/**
	 * Declares the given port.
	 *
	 * @param mv
	 * @param port
	 */
	private void declarePort(MethodVisitor mv, Port port) {
		mv.visitVarInsn(ALOAD, 1);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, entityClass, port.getName(), PORT_TYPE);
		push(mv, Direction.OUTPUT == port.getDirection());
		mv.visitLdcInsn(port.getName());
		mv.visitMethodInsn(INVOKEVIRTUAL, VCD_WRITER_CLASS, "declare",
				"(Lcom/synflow/runtime/Port;ZLjava/lang/String;)V", false);
	}

	/**
	 * Declares a state variable with the given type, name, and stores its newly-created identifier
	 * at index <code>i</code> in the signals array.
	 *
	 * @param mv
	 * @param type
	 * @param name
	 * @param i
	 */
	private void declareVariable(MethodVisitor mv, Type type, String name, int i) {
		pushSignalsAndIndex(mv, i);

		mv.visitVarInsn(ALOAD, 1);
		mv.visitLdcInsn("reg");
		push(mv, TypeUtil.getSize(type));
		mv.visitLdcInsn(name);
		mv.visitMethodInsn(INVOKEVIRTUAL, VCD_WRITER_CLASS, "declare",
				"(Ljava/lang/String;ILjava/lang/String;)Ljava/lang/String;", false);

		mv.visitInsn(AASTORE);
	}

	private int getPortFlags(Port port) {
		InterfaceType iface = port.getInterface();
		int flags = 0;
		if (iface.isSync()) {
			flags |= VALID;
		}
		if (iface.isSyncReady()) {
			flags |= READY;
		}
		return flags;
	}

	/**
	 * Returns true if the given variable should be included in the VCD output.
	 *
	 * @param var
	 * @return
	 */
	private boolean include(Var var) {
		return var.isAssignable() && !var.getType().isArray();
	}

	private void pushSignalsAndIndex(MethodVisitor mv, int i) {
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, entityClass, "_signals", "[Ljava/lang/String;");
		push(mv, i);
	}

	private void updateVar(MethodVisitor mv, Var var, int i) {
		mv.visitVarInsn(ALOAD, 1);

		pushSignalsAndIndex(mv, i);
		mv.visitInsn(AALOAD);

		String type = getJavaType(var.getType());
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, entityClass, var.getName(), type);

		mv.visitMethodInsn(INVOKEVIRTUAL, VCD_WRITER_CLASS, "update",
				"(Ljava/lang/String;" + type + ")V", false);
	}

}
