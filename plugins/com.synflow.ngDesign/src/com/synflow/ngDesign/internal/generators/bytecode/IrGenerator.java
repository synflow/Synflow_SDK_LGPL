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
package com.synflow.ngDesign.internal.generators.bytecode;

import static com.google.common.collect.Iterables.any;
import static com.synflow.core.IProperties.PROP_CLOCKS;
import static org.objectweb.asm.Opcodes.ANEWARRAY;
import static org.objectweb.asm.Opcodes.BASTORE;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.NEWARRAY;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.T_BOOLEAN;
import static org.objectweb.asm.Opcodes.T_BYTE;
import static org.objectweb.asm.Opcodes.T_FLOAT;
import static org.objectweb.asm.Opcodes.T_INT;
import static org.objectweb.asm.Opcodes.T_LONG;

import java.math.BigInteger;
import java.util.List;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Port;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeArray;
import com.synflow.models.ir.TypeInt;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.util.IrSwitch;
import com.synflow.models.ir.util.IrUtil;
import com.synflow.models.util.Void;

/**
 * This class defines utility methods for generating bytecode.
 *
 * @author Matthieu Wipliez
 *
 */
public class IrGenerator extends IrSwitch<Void> {

	public static final String EXECUTE_COMBINATIONAL = "executeCombinational";

	public static final String EXECUTE_SYNCHRONOUS = "execute";

	public static final String PORT_CLASS = "com/synflow/runtime/Port";

	public static final String PORT_TYPE = getTypeName(PORT_CLASS);

	public static final String VCD_DECLARE_SCOPE = "vcdDeclareScope";

	public static final String VCD_UPDATE_VALUES = "vcdUpdateValues";

	public static final String VCD_WRITER_CLASS = "com/synflow/runtime/VcdWriter";

	public static final String VCD_WRITER_TYPE = getTypeName(VCD_WRITER_CLASS);

	/**
	 * Creates a new array based on the given <code>array</code> type.
	 *
	 * @param mv
	 *            method visitor
	 * @param array
	 *            array type
	 */
	public static void createArray(MethodVisitor mv, TypeArray array) {
		List<Integer> dims = array.getDimensions();
		for (int dim : dims) {
			push(mv, dim);
		}

		if (dims.size() == 1) {
			Type elementType = array.getElementType();
			if (isBigInteger(elementType)) {
				mv.visitTypeInsn(ANEWARRAY, getJavaType(elementType));
			} else {
				mv.visitIntInsn(NEWARRAY, getArrayType(array));
			}
		} else {
			mv.visitMultiANewArrayInsn(getJavaType(array), dims.size());
		}
	}

	public static void endMethod(MethodVisitor mw) {
		mw.visitInsn(RETURN);
		mw.visitMaxs(0, 0);
		mw.visitEnd();
	}

	/**
	 * Gets the appropriate opcode for the given array type.
	 *
	 * @param opcode
	 *            opcode IALOAD or IASTORE
	 * @param array
	 *            array type
	 * @return an opcode for the correct array type
	 */
	public static int getArrayOpcode(int opcode, TypeArray array) {
		Type element = array.getElementType();
		return getOpcode(opcode, element);
	}

	/**
	 * Returns the appropriate type argument for NEWARRAY from the given array type.
	 *
	 * @param array
	 * @return
	 */
	public static int getArrayType(TypeArray array) {
		Type type = array.getElementType();
		if (type.isBool()) {
			return T_BOOLEAN;
		} else if (type.isFloat()) {
			return T_FLOAT;
		} else if (type.isInt()) {
			TypeInt ti = (TypeInt) type;
			if (ti.getSize() <= 32) {
				return T_INT;
			} else if (ti.getSize() <= 64) {
				return T_LONG;
			}
		}

		throw new IllegalArgumentException();
	}

	/**
	 * Returns the bytecode class name (e.g. <code>com/synflow/Entity</code>) for the given entity.
	 *
	 * @param entity
	 * @return
	 */
	public static String getClassName(Entity entity) {
		return IrUtil.getFile(entity.getName());
	}

	/**
	 * Returns the signature of the "connect" method from the given list of ports.
	 *
	 * @param ports
	 *            a list of ports
	 * @return a signature
	 */
	public static String getConnectSignature(List<Port> ports) {
		StringBuilder builder = new StringBuilder("(");
		for (int i = 0; i < ports.size(); i++) {
			builder.append(PORT_TYPE);
		}
		builder.append(")V");
		return builder.toString();
	}

	/**
	 * Returns the Java representation of the given IR type.
	 *
	 * @param type
	 *            an IR type
	 * @return a String representing the IR type in Java
	 */
	public static String getJavaType(Type type) {
		if (type.isArray()) {
			TypeArray array = (TypeArray) type;
			int count = array.getDimensions().size();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < count; i++) {
				builder.append('[');
			}
			builder.append(getJavaType(array.getElementType()));
			return builder.toString();
		} else if (type.isBool()) {
			return "Z";
		} else if (type.isFloat()) {
			return "F";
		} else if (type.isInt()) {
			TypeInt ti = (TypeInt) type;
			if (ti.getSize() <= 32) {
				return "I";
			} else if (ti.getSize() <= 64) {
				return "J";
			} else {
				return "Ljava/math/BigInteger;";
			}
		} else if (type.isString()) {
			return "Ljava/lang/String;";
		} else if (type.isVoid()) {
			return "V";
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns the opcode adapted to the given type.
	 *
	 * @param opcode
	 *            opcode
	 * @param type
	 *            IR type
	 * @return an opcode
	 */
	public static int getOpcode(int opcode, Type type) {
		return org.objectweb.asm.Type.getType(getJavaType(type)).getOpcode(opcode);
	}

	/**
	 * Returns the name of the field in a Port* class from the given signal (that is contained in a
	 * port).
	 *
	 * @param target
	 *            a variable contained in a port
	 * @return the name of a field
	 */
	public static String getPortFlagFieldName(Var target) {
		String name = target.getName();
		return name.substring(name.lastIndexOf("_") + 1);
	}

	/**
	 * Returns the name of the right "value" field (e.g. "intValue", "longValue"...) for the given
	 * Port depending on the port's type.
	 *
	 * @param port
	 *            a port
	 * @return the name of the right "value" field
	 */
	public static String getPortValueFieldName(Port port) {
		Type type = port.getType();
		if (type.isBool()) {
			return "intValue";
		} else if (type.isFloat()) {
			return "floatValue";
		} else if (type.isInt()) {
			TypeInt ti = (TypeInt) type;
			if (ti.getSize() <= 32) {
				return "intValue";
			} else if (ti.getSize() <= 64) {
				return "longValue";
			} else {
				return "value";
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns the type description for the right "value" field (e.g. "intValue", "longValue"...)
	 * for the given Port.
	 *
	 * @param port
	 *            a port
	 * @return a String representing a bytecode type
	 */
	public static String getPortValueFieldType(Port port) {
		Type type = port.getType();
		if (type.isBool()) {
			return "I";
		}
		return getJavaType(type);
	}

	/**
	 * Returns the signature of the given procedure.
	 *
	 * @param procedure
	 *            a procedure
	 * @return the method signature
	 */
	public static String getSignature(Procedure procedure) {
		StringBuilder builder = new StringBuilder("(");
		for (Var param : procedure.getParameters()) {
			builder.append(getJavaType(param.getType()));
		}
		builder.append(")");
		builder.append(getJavaType(procedure.getReturnType()));
		return builder.toString();
	}

	/**
	 * Returns the bytecode type name (e.g. <code>Lcom/synflow/Entity;</code>) for the given entity.
	 *
	 * @param entity
	 * @return
	 */
	public static final String getTypeName(Entity entity) {
		return getTypeName(getClassName(entity));
	}

	/**
	 * Returns the bytecode type name (e.g. <code>Lcom/synflow/Entity;</code>) for the given class name.
	 *
	 * @param className
	 * @return
	 */
	public static final String getTypeName(String className) {
		return "L" + className + ";";
	}

	/**
	 * Returns the size of a value of the given type: 1 if the type can be represented as an int or
	 * a reference, 2 if it is represented as a long.
	 *
	 * @param type
	 * @return
	 */
	public static int getTypeSize(Type type) {
		if (type.isInt()) {
			int size = ((TypeInt) type).getSize();
			if (size > 32 && size <= 64) {
				return 2;
			}
		}

		// all other types (boolean, float, int < 32, int > 64, array) are 1
		return 1;
	}

	/**
	 * Entity has a combinational execute if it is a DPN (always has a combinational execute,
	 * because any one of its instances may have one directly or indirectly), or if it is
	 * combinational, or if it has sync ready inputs, or asynchronous outputs.
	 *
	 * @param entity
	 * @return
	 */
	public static boolean hasCombinationalExecute(Entity entity) {
		return entity instanceof DPN || !isSynchronous(entity)
				|| any(entity.getInputs(), port -> port.getInterface().isSyncReady())
				|| any(entity.getOutputs(), port -> !port.isSynchronous());
	}

	/**
	 * Returns true if the given type is represented by a BigInteger (a TypeInt whose size is &gt;
	 * 64).
	 *
	 * @param type
	 *            a type
	 * @return
	 */
	public static boolean isBigInteger(Type type) {
		return type.isInt() && ((TypeInt) type).getSize() > 64;
	}

	/**
	 * Returns <code>true</code> if the given entity is synchronous, i.e. it has a
	 * <code>clocks</code> properties that is an array with at least one entry.
	 *
	 * @param entity
	 *            an entity
	 * @return a boolean indicating whether the entity is synchronous
	 */
	public static boolean isSynchronous(Entity entity) {
		return entity.getProperties().getAsJsonArray(PROP_CLOCKS).size() != 0;
	}

	public static void println(MethodVisitor mw, String text) {
		mw.visitFieldInsn(GETSTATIC, "java/lang/System", "err", "Ljava/io/PrintStream;");
		mw.visitLdcInsn(text);
		mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",
				false);
	}

	/**
	 * Pushes the given BigInteger value onto the stack. Invokes new BigInteger(byte[]).
	 *
	 * @param mv
	 *            method visitor
	 * @param value
	 *            BigInteger value
	 */
	public static void push(MethodVisitor mv, BigInteger value) {
		mv.visitTypeInsn(NEW, "java/math/BigInteger");
		mv.visitInsn(DUP);

		byte[] ba = value.toByteArray();
		int length = ba.length;
		push(mv, length);
		mv.visitIntInsn(NEWARRAY, T_BYTE);
		for (int i = 0; i < length; i++) {
			mv.visitInsn(DUP);
			push(mv, i);
			push(mv, ba[i]);
			mv.visitInsn(BASTORE);
		}

		mv.visitMethodInsn(INVOKESPECIAL, "java/math/BigInteger", "<init>", "([B)V", false);
	}

	/**
	 * Generates the instruction to push the given value on the stack. Copied and adapted from
	 * GeneratorAdapter.
	 *
	 * @param value
	 *            the value to be pushed on the stack.
	 */
	public static void push(MethodVisitor mv, final boolean value) {
		push(mv, value ? 1 : 0);
	}

	/**
	 * Pushes the given byte.
	 *
	 * @param mv
	 *            method visitor
	 * @param value
	 *            byte value
	 */
	public static void push(MethodVisitor mv, final byte value) {
		mv.visitIntInsn(Opcodes.BIPUSH, value);
	}

	/**
	 * Generates the instruction to push the given value on the stack.Copied and adapted from
	 * GeneratorAdapter.
	 *
	 * @param value
	 *            the value to be pushed on the stack.
	 */
	public static void push(MethodVisitor mv, final float value) {
		int bits = Float.floatToIntBits(value);
		if (bits == 0L || bits == 0x3f800000 || bits == 0x40000000) { // 0..2
			mv.visitInsn(Opcodes.FCONST_0 + (int) value);
		} else {
			mv.visitLdcInsn(value);
		}
	}

	/**
	 * Generates the instruction to push the given value on the stack. Copied and adapted from
	 * GeneratorAdapter.
	 *
	 * @param value
	 *            the value to be pushed on the stack.
	 */
	public static void push(MethodVisitor mv, final int value) {
		if (value >= -1 && value <= 5) {
			mv.visitInsn(Opcodes.ICONST_0 + value);
		} else if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
			mv.visitIntInsn(Opcodes.BIPUSH, value);
		} else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
			mv.visitIntInsn(Opcodes.SIPUSH, value);
		} else {
			mv.visitLdcInsn(value);
		}
	}

	/**
	 * Generates the instruction to push the given value on the stack. Copied from GeneratorAdapter.
	 *
	 * @param value
	 *            the value to be pushed on the stack.
	 */
	public static void push(MethodVisitor mv, final long value) {
		if (value == 0L || value == 1L) {
			mv.visitInsn(Opcodes.LCONST_0 + (int) value);
		} else {
			mv.visitLdcInsn(value);
		}
	}

	/**
	 * Starts a method that corresponds to the given procedure.
	 *
	 * @param cw
	 *            class writer
	 * @param procedure
	 *            an IR procedure
	 * @return method visitor
	 */
	public static MethodVisitor startMethod(ClassWriter cw, int flags, Procedure procedure) {
		return startMethod(cw, flags, procedure.getName(), getSignature(procedure));
	}

	/**
	 * Starts a method with the given signature
	 *
	 * @param cw
	 *            class writer
	 * @param name
	 *            method name
	 * @param signature
	 *            method signature
	 * @return method visitor
	 */
	public static MethodVisitor startMethod(ClassWriter cw, int flags, String name,
			String signature) {
		MethodVisitor mv = cw.visitMethod(flags, name, signature, null, null);
		mv.visitCode();
		return mv;
	}

}
