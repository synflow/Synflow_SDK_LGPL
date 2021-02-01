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

import static com.synflow.core.IProperties.PROP_EMPTY;
import static com.synflow.core.IProperties.PROP_SYNTHETIC;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.createArray;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.endMethod;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getArrayOpcode;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getClassName;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getJavaType;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getTypeName;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.isBigInteger;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.push;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.startMethod;
import static org.objectweb.asm.Opcodes.AALOAD;
import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.IASTORE;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.PUTSTATIC;
import static org.objectweb.asm.Opcodes.V1_8;

import java.util.List;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import com.google.gson.JsonObject;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Unit;
import com.synflow.models.dpn.util.DpnSwitch;
import com.synflow.models.ir.ExprList;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeArray;
import com.synflow.models.ir.Var;

/**
 * This class defines an entity to bytecode compiler, and calls specific *Compiler classes for Actor
 * and DPN.
 *
 * @author Matthieu Wipliez
 *
 */
public class EntityCompiler {

	public static byte[] compile(Entity entity) {
		return new DpnSwitch<byte[]>() {

			@Override
			public byte[] caseActor(Actor actor) {
				JsonObject properties = actor.getProperties();
				ActorCompiler compiler;
				if (properties.has(PROP_SYNTHETIC) && properties.has(PROP_EMPTY)) {
					compiler = new ExternalActorCompiler();
				} else {
					compiler = new ActorCompiler();
				}
				return compileInstantiable(compiler, actor);
			}

			@Override
			public byte[] caseDPN(DPN dpn) {
				return compileInstantiable(new DpnCompiler(), dpn);
			}

			@Override
			public byte[] caseUnit(Unit unit) {
				EntityCompiler compiler = new EntityCompiler();
				endMethod(compiler.compileEntity(entity));

				return compiler.getBytecode();
			}

			private byte[] compileInstantiable(InstantiableCompiler compiler, Entity entity) {
				endMethod(compiler.compileEntity(entity));
				endMethod(compiler.createConnect(entity));
				endMethod(compiler.createVcdDeclareScope(entity));
				endMethod(compiler.createVcdUpdateValues(entity));
				compiler.compileBehavior();
				return compiler.getBytecode();
			}

		}.doSwitch(entity);
	}

	protected ClassWriter cw;

	protected String entityClass;

	protected EntityCompiler() {
	}

	/**
	 * Compiles the structure (state variables, functions) of the given entity to bytecode. Returns
	 * the constructor created.
	 *
	 * @param entity
	 *            an entity
	 * @return the method visitor for the constructor
	 */
	protected MethodVisitor compileEntity(Entity entity) {
		createClass(entity);

		// functions
		for (Procedure procedure : entity.getProcedures()) {
			BodyCompiler compiler = new BodyCompiler(entityClass);
			compiler.compileProcedure(cw, procedure);
		}

		// create constructor
		MethodVisitor constructor = startMethod(cw, ACC_PUBLIC, "<init>", "()V");
		constructor.visitVarInsn(ALOAD, 0);
		constructor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);

		// state variables
		compileStateVariables(constructor, entity.getVariables());

		return constructor;
	}

	private void compileStateVariables(MethodVisitor constructor, List<Var> variables) {
		MethodVisitor ctorStatic = startMethod(cw, ACC_STATIC, "<clinit>", "()V");
		for (Var var : variables) {
			String typeName = getJavaType(var.getType());
			if (var.isAssignable()) {
				cw.visitField(ACC_PRIVATE, var.getName(), typeName, null, null);
				initializeVar(var, constructor);
			} else {
				cw.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, var.getName(), typeName, null,
						null);
				initializeVar(var, ctorStatic);
			}
		}
		endMethod(ctorStatic);
	}

	private void createClass(Entity entity) {
		this.entityClass = getClassName(entity);

		cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cw.visit(V1_8, ACC_PUBLIC | ACC_SUPER, entityClass, null, "java/lang/Object", null);

		String typeName = getTypeName(entity);

		MethodVisitor create = startMethod(cw, ACC_PUBLIC | ACC_STATIC, "create", "()" + typeName);
		create.visitTypeInsn(NEW, entityClass);
		create.visitInsn(DUP);
		create.visitMethodInsn(INVOKESPECIAL, entityClass, "<init>", "()V", false);
		create.visitInsn(ARETURN);
		create.visitMaxs(0, 0);
		create.visitEnd();
	}

	/**
	 * Ends the visit of this class writer, and returns the corresponding bytecode.
	 *
	 * @return a byte array with the bytecode of the class generated by this entity compiler
	 */
	byte[] getBytecode() {
		cw.visitEnd();

		byte[] code = cw.toByteArray();
		cw = null;
		return code;
	}

	private void initArray(MethodVisitor constructor, TypeArray array, ExprList expr) {
		List<Expression> values = expr.getValue();
		for (int i = 0; i < values.size(); i++) {
			Expression subExpr = values.get(i);
			if (subExpr.isExprList()) {
				constructor.visitInsn(DUP);
				push(constructor, i);
				constructor.visitInsn(AALOAD);
				initArray(constructor, array, (ExprList) subExpr);
				constructor.visitInsn(POP);
			} else {
				constructor.visitInsn(DUP);
				push(constructor, i);
				new ExpressionCompiler(constructor).doSwitch(subExpr);
				constructor.visitInsn(getArrayOpcode(IASTORE, array));
			}
		}
	}

	/**
	 * Initializes the field for the given variable.
	 *
	 * @param var
	 * @param constructor
	 */
	private void initializeVar(Var var, MethodVisitor constructor) {
		Expression init = var.getInitialValue();
		Type type = var.getType();
		if (init == null && !type.isArray() && !isBigInteger(type)) {
			// nothing to do
			return;
		}

		String typeName = getJavaType(var.getType());
		if (var.isAssignable()) {
			constructor.visitVarInsn(ALOAD, 0);
		}

		// arrays must be created regardless of the value of "init"
		if (type.isArray()) {
			createArray(constructor, (TypeArray) type);
		}

		if (init == null) {
			if (type.isArray()) {
				Type element = ((TypeArray) type).getElementType();
				if (isBigInteger(element)) {
					constructor.visitInsn(DUP);
					constructor.visitFieldInsn(GETSTATIC, "java/math/BigInteger", "ZERO",
							"Ljava/math/BigInteger;");
					constructor.visitMethodInsn(INVOKESTATIC, "java/util/Arrays", "fill",
							"([Ljava/lang/Object;Ljava/lang/Object;)V", false);
				}
			} else { /* implied isBigInteger(type) */
				constructor.visitFieldInsn(GETSTATIC, "java/math/BigInteger", "ZERO",
						"Ljava/math/BigInteger;");
			}
		} else {
			if (type.isArray()) {
				initArray(constructor, (TypeArray) type, (ExprList) init);
			} else {
				new ExpressionCompiler(constructor).doSwitch(init);
			}
		}

		if (var.isAssignable()) {
			constructor.visitFieldInsn(PUTFIELD, entityClass, var.getName(), typeName);
		} else {
			constructor.visitFieldInsn(PUTSTATIC, entityClass, var.getName(), typeName);
		}
	}

}
