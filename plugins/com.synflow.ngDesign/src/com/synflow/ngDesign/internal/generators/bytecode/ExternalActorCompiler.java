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

import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.PORT_TYPE;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getTypeName;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.startMethod;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.RETURN;

import org.eclipse.emf.common.util.EList;
import org.objectweb.asm.MethodVisitor;

import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Port;

/**
 * This class defines an Actor to bytecode compiler.
 *
 * @author Matthieu Wipliez
 *
 */
public class ExternalActorCompiler extends ActorCompiler {

	private static final String JSON_READER = "com/synflow/runtime/JsonReader";

	private static final String JSON_WRITER = "com/synflow/runtime/JsonWriter";

	private static final String READER = "_reader";

	private static final String WRITER = "_writer";

	private MethodVisitor mw;

	@Override
	protected void compileActions(EList<Action> actions) {
		Action action = actions.iterator().next();

		BodyCompiler compiler = new BodyCompiler(entityClass);
		compiler.compileProcedure(cw, action.getScheduler());

		this.mw = startMethod(cw, ACC_PRIVATE, action.getBody());
		if (actor.getInputs().isEmpty()) {
			compileStimulus(compiler);
		} else if (actor.getOutputs().isEmpty()) {
			compileExpected(compiler);
		}
		mw.visitInsn(RETURN);
		mw.visitMaxs(0, 0);
		mw.visitEnd();

		compiler.compileProcedure(cw, action.getCombinational());
	}

	@Override
	protected MethodVisitor compileEntity(Entity entity) {
		MethodVisitor constructor = super.compileEntity(entity);

		String fieldName;
		String className;
		if (actor.getInputs().isEmpty()) {
			fieldName = READER;
			className = JSON_READER;
		} else if (actor.getOutputs().isEmpty()) {
			fieldName = WRITER;
			className = JSON_WRITER;
		} else {
			throw new IllegalArgumentException();
		}

		cw.visitField(ACC_PRIVATE, fieldName, getTypeName(className), null, null);

		constructor.visitVarInsn(ALOAD, 0);

		constructor.visitTypeInsn(NEW, className);
		constructor.visitInsn(DUP);
		constructor.visitMethodInsn(INVOKESPECIAL, className, "<init>", "()V", false);

		constructor.visitFieldInsn(PUTFIELD, entityClass, fieldName, getTypeName(className));

		return constructor;
	}

	private void compileExpected(BodyCompiler compiler) {
		for (Port port : actor.getInputs()) {
			mw.visitVarInsn(ALOAD, 0);
			mw.visitFieldInsn(GETFIELD, entityClass, WRITER, getTypeName(JSON_WRITER));
			mw.visitVarInsn(ALOAD, 0);
			mw.visitFieldInsn(GETFIELD, entityClass, port.getName(), PORT_TYPE);
			mw.visitMethodInsn(INVOKEVIRTUAL, JSON_WRITER, "writeValue", "(" + PORT_TYPE + ")V", false);
		}

		mw.visitVarInsn(ALOAD, 0);
		mw.visitFieldInsn(GETFIELD, entityClass, WRITER, getTypeName(JSON_WRITER));
		mw.visitMethodInsn(INVOKEVIRTUAL, JSON_WRITER, "flush", "()V", false);
	}

	private void compileStimulus(BodyCompiler compiler) {
		mw.visitVarInsn(ALOAD, 0);
		mw.visitFieldInsn(GETFIELD, entityClass, READER, getTypeName(JSON_READER));
		mw.visitMethodInsn(INVOKEVIRTUAL, JSON_READER, "next", "()V", false);

		for (Port port : actor.getOutputs()) {
			mw.visitVarInsn(ALOAD, 0);
			mw.visitFieldInsn(GETFIELD, entityClass, READER, getTypeName(JSON_READER));
			mw.visitVarInsn(ALOAD, 0);
			mw.visitFieldInsn(GETFIELD, entityClass, port.getName() + "_next", PORT_TYPE);
			mw.visitMethodInsn(INVOKEVIRTUAL, JSON_READER, "readValue", "(" + PORT_TYPE + ")V", false);
		}
	}

}
