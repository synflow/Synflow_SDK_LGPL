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

import static com.google.common.collect.Iterables.concat;
import static com.synflow.models.util.SwitchUtil.DONE;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.PORT_CLASS;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.PORT_TYPE;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.createArray;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getArrayOpcode;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getClassName;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getJavaType;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getOpcode;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getPortFlagFieldName;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getPortValueFieldName;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getPortValueFieldType;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getSignature;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getTypeSize;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.isSynchronous;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.push;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.startMethod;
import static org.objectweb.asm.Opcodes.AALOAD;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.IALOAD;
import static org.objectweb.asm.Opcodes.IASTORE;
import static org.objectweb.asm.Opcodes.IFNE;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.ISTORE;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.RETURN;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;

import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.DpnPackage;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Goto;
import com.synflow.models.dpn.Port;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Unit;
import com.synflow.models.ir.BlockBasic;
import com.synflow.models.ir.BlockIf;
import com.synflow.models.ir.BlockWhile;
import com.synflow.models.ir.ExprVar;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstAssign;
import com.synflow.models.ir.InstCall;
import com.synflow.models.ir.InstLoad;
import com.synflow.models.ir.InstReturn;
import com.synflow.models.ir.InstStore;
import com.synflow.models.ir.Instruction;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeArray;
import com.synflow.models.ir.TypeInt;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.util.ExpressionPrinter;
import com.synflow.models.ir.util.TypeUtil;
import com.synflow.models.util.Void;

/**
 * This class compiles the body of an action to bytecode.
 *
 * @author Matthieu Wipliez
 *
 */
public class BodyCompiler extends ExpressionCompiler {

	private String entityClass;

	private Map<Var, Integer> localMap;

	private Procedure procedure;

	private final Map<State, Integer> stateMap;

	public BodyCompiler(String entityClass) {
		this(entityClass, null);
	}

	public BodyCompiler(String actorClass, Map<State, Integer> stateMap) {
		this.entityClass = actorClass;
		this.stateMap = stateMap;
	}

	private void addLineNumber(int lineNumber) {
		if (lineNumber == 0) {
			return;
		}

		Label label = new Label();
		mw.visitLabel(label);
		mw.visitLineNumber(lineNumber, label);
	}

	/**
	 * Compiles a call to assert as <code>if (!expr) { throw new AssertionError("expr"); }</code>
	 *
	 * @param expr
	 */
	private void callAssert(Expression expr) {
		final String assertClass = "java/lang/AssertionError";

		Label assertionPassed = new Label();
		doSwitch(expr);
		mw.visitJumpInsn(IFNE, assertionPassed); // if expr != 0 assertion passed

		// expr == 0 (false): throw new AssertionError
		mw.visitTypeInsn(NEW, assertClass);
		mw.visitInsn(DUP);
		mw.visitLdcInsn(new ExpressionPrinter().toString(expr));
		mw.visitMethodInsn(INVOKESPECIAL, assertClass, "<init>", "(Ljava/lang/Object;)V", false);
		mw.visitInsn(ATHROW);

		mw.visitLabel(assertionPassed);
	}

	/**
	 * Compiles the given call instruction.
	 *
	 * @param call
	 *            a call instruction
	 */
	private void callFunction(InstCall call) {
		Procedure proc = call.getProcedure();
		Entity entity = (Entity) proc.eContainer();
		int opcode;
		if (entity instanceof Unit) {
			opcode = INVOKESTATIC;
		} else {
			// method in this class
			opcode = INVOKESPECIAL;
			mw.visitVarInsn(ALOAD, 0);
		}

		// pushes arguments
		for (Expression arg : call.getArguments()) {
			doSwitch(arg);
		}

		// do actual invoke
		mw.visitMethodInsn(opcode, getClassName(entity), proc.getName(), getSignature(proc), false);

		// store result (if any)
		if (call.getTarget() != null) {
			storeLocal(call.getTarget().getVariable());
		}
	}

	/**
	 * Compiles a call to print as <code>System.out.println(arg1 + arg2 + ... + argn);</code>
	 *
	 * @param arguments
	 *            a list of expression
	 */
	private void callPrint(EList<Expression> arguments) {
		mw.visitFieldInsn(GETSTATIC, "java/lang/System", "err", "Ljava/io/PrintStream;");
		mw.visitTypeInsn(NEW, "java/lang/StringBuilder");
		mw.visitInsn(DUP);
		mw.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);

		for (Expression expr : arguments) {
			Type type = TypeUtil.getType(expr);
			String desc = getJavaType(type);
			if (type.isInt()) {
				mw.visitLdcInsn("0x");
				mw.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
						"(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

				doSwitch(expr);
				int size = ((TypeInt) type).getSize();
				if (size > 64) {
					push(mw, 16);
					mw.visitMethodInsn(INVOKEVIRTUAL, "java/math/BigInteger", "toString",
							"(I)Ljava/lang/String;", false);
				} else if (size > 32) {
					mw.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "toHexString",
							"(J)Ljava/lang/String;", false);
				} else {
					mw.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "toHexString",
							"(I)Ljava/lang/String;", false);
				}

				mw.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
						"(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
			} else {
				doSwitch(expr);
				mw.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
						"(" + desc + ")Ljava/lang/StringBuilder;", false);
			}
		}

		mw.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString",
				"()Ljava/lang/String;", false);
		mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",
				false);
	}

	@Override
	public Void caseBlockBasic(BlockBasic block) {
		visit(block.getInstructions());
		return DONE;
	}

	@Override
	public Void caseBlockIf(BlockIf block) {
		doSwitch(block.getCondition());

		Label isTrue = new Label();
		Label exit = new Label();
		mw.visitJumpInsn(IFNE, isTrue);
		visit(block.getElseBlocks());
		mw.visitJumpInsn(GOTO, exit);

		mw.visitLabel(isTrue);
		visit(block.getThenBlocks());

		mw.visitLabel(exit);
		doSwitch(block.getJoinBlock());

		return DONE;
	}

	@Override
	public Void caseBlockWhile(BlockWhile block) {
		// jump to test at the end of the loop
		Label test = new Label();
		mw.visitJumpInsn(GOTO, test);

		// entry of the loop that contains the body
		Label entry = new Label();
		mw.visitLabel(entry);
		visit(block.getBlocks());
		doSwitch(block.getJoinBlock());

		// end of the loop and condition
		mw.visitLabel(test);
		doSwitch(block.getCondition());
		mw.visitJumpInsn(IFNE, entry);

		return DONE;
	}

	@Override
	public Void caseExprVar(ExprVar expr) {
		Var source = expr.getUse().getVariable();
		if (isPortVariable(source)) {
			loadPort(source);
		} else {
			loadVar(source);
		}
		return DONE;
	}

	@Override
	public Void caseInstAssign(InstAssign assign) {
		addLineNumber(assign.getLineNumber());

		doSwitch(assign.getValue());
		storeLocal(assign.getTarget().getVariable());

		return DONE;
	}

	@Override
	public Void caseInstCall(InstCall call) {
		addLineNumber(call.getLineNumber());

		if (call.isAssert()) {
			callAssert(call.getArguments().get(0));
		} else if (call.isPrint()) {
			callPrint(call.getArguments());
		} else {
			callFunction(call);
		}
		return DONE;
	}

	@Override
	public Void caseInstLoad(InstLoad load) {
		addLineNumber(load.getLineNumber());

		Var source = load.getSource().getVariable();
		if (isPortVariable(source)) {
			loadPort(source);
		} else {
			loadVar(source);
			if (!load.getIndexes().isEmpty()) {
				loadMultiIndexes(load.getIndexes());
				mw.visitInsn(getArrayOpcode(IALOAD, (TypeArray) source.getType()));
			}
		}

		// store in target local variable
		storeLocal(load.getTarget().getVariable());

		return DONE;
	}

	@Override
	public Void caseInstReturn(InstReturn ret) {
		addLineNumber(ret.getLineNumber());

		Expression value = ret.getValue();
		if (value != null) {
			doSwitch(value);
		}
		mw.visitInsn(getOpcode(IRETURN, procedure.getReturnType()));
		return DONE;
	}

	@Override
	public Void caseInstruction(Instruction instr) {
		if (instr instanceof Goto) {
			mw.visitVarInsn(ALOAD, 0);
			State target = ((Goto) instr).getTarget();
			push(mw, stateMap.get(target));
			mw.visitFieldInsn(PUTFIELD, entityClass, "_state", "I");
		} else {
			System.err.println("TODO " + instr.getClass().getSimpleName());
		}

		return DONE;
	}

	@Override
	public Void caseInstStore(InstStore store) {
		addLineNumber(store.getLineNumber());

		Var target = store.getTarget().getVariable();
		Type type = target.getType();

		if (isPortVariable(target)) {
			storePort(target, store.getValue());
		} else if (type.isArray()) {
			loadVar(target);

			// load indexes, push value, and stores in array
			loadMultiIndexes(store.getIndexes());
			doSwitch(store.getValue());
			mw.visitInsn(getArrayOpcode(IASTORE, (TypeArray) type));
		} else {
			mw.visitVarInsn(ALOAD, 0);
			doSwitch(store.getValue());
			mw.visitFieldInsn(PUTFIELD, entityClass, target.getName(), getJavaType(type));
		}
		return DONE;
	}

	/**
	 * Compiles the given procedure to a bytecode method.
	 *
	 * @param cw
	 *            class writer to contribute a method to
	 * @param procedure
	 *            IR procedure
	 */
	public void compileProcedure(ClassWriter cw, Procedure procedure) {
		boolean inUnit = procedure.eContainer() instanceof Unit;
		this.mw = startMethod(cw, inUnit ? ACC_PUBLIC | ACC_STATIC : ACC_PRIVATE, procedure);
		this.procedure = procedure;

		int index = inUnit ? 0 : 1; // because index 0 is "this"

		localMap = new HashMap<>();

		// register parameters in localMap
		for (Var var : procedure.getParameters()) {
			localMap.put(var, index);
			index += getTypeSize(var.getType());
		}

		// register locals in localMap
		// must initialize local variables to zero to allow reading individual bits
		for (Var var : procedure.getLocals()) {
			localMap.put(var, index);

			Type type = var.getType();
			if (type.isArray()) {
				createArray(mw, (TypeArray) type);
				mw.visitVarInsn(ASTORE, index);
			} else {
				int size = TypeUtil.getSize(type);
				if (size > 64) {
					mw.visitFieldInsn(GETSTATIC, "java/math/BigInteger", "ZERO",
							"Ljava/math/BigInteger;");
					mw.visitVarInsn(ASTORE, index);
				} else {
					if (size > 32) {
						push(mw, 0L);
					} else {
						push(mw, 0);
					}
					mw.visitVarInsn(getOpcode(ISTORE, type), index);
				}
			}

			index += getTypeSize(var.getType());
		}

		// visit body
		Label start = new Label();
		mw.visitLabel(start);
		visit(procedure.getBlocks());
		Label end = new Label();
		mw.visitLabel(end);

		// visit "this" local variable for functions not in units
		if (!inUnit) {
			mw.visitLocalVariable("this", "L" + entityClass + ";", null, start, end, 0);
		}

		// visit normal local variables
		for (Var var : concat(procedure.getParameters(), procedure.getLocals())) {
			mw.visitLocalVariable(var.getName(), getJavaType(var.getType()), null, start, end,
					localMap.get(var));
		}

		// add return for void methods
		if (procedure.getReturnType().isVoid()) {
			mw.visitInsn(RETURN);
		}

		mw.visitMaxs(0, 0);
		mw.visitEnd();
	}

	/**
	 * Returns <code>true</code> if the given variable is a port variable (instance of Port or
	 * contained in a Port)
	 *
	 * @param variable
	 *            a variable
	 * @return
	 */
	private boolean isPortVariable(Var variable) {
		return variable instanceof Port || variable.eContainer() instanceof Port;
	}

	/**
	 * Loads the given indexes, properly calling AALOAD in case of multi-arrays.
	 *
	 * @param indexes
	 *            indexes
	 */
	private void loadMultiIndexes(List<Expression> indexes) {
		int last = indexes.size() - 1;
		for (int i = 0; i < last; i++) {
			doSwitch(indexes.get(i));
			mw.visitInsn(AALOAD);
		}
		doSwitch(indexes.get(last));
	}

	/**
	 * Loads the value from the given port variable.
	 *
	 * @param source
	 *            source variable (instance of Port or contained in a Port)
	 */
	private void loadPort(Var source) {
		Port port;
		String fieldName, fieldType;
		if (source instanceof Port) {
			port = (Port) source;
			fieldName = getPortValueFieldName(port);
			fieldType = getPortValueFieldType(port);
		} else {
			port = (Port) source.eContainer();
			fieldName = getPortFlagFieldName(source);
			fieldType = "Z";
		}

		mw.visitVarInsn(ALOAD, 0);
		mw.visitFieldInsn(GETFIELD, entityClass, port.getName(), PORT_TYPE);
		mw.visitFieldInsn(GETFIELD, PORT_CLASS, fieldName, fieldType);
	}

	/**
	 * Loads the given variable, whether it's local or global.
	 *
	 * @param variable
	 */
	private void loadVar(Var variable) {
		if (variable.isLocal()) {
			int opcode = getOpcode(ILOAD, variable.getType());
			int num = localMap.get(variable);
			mw.visitVarInsn(opcode, num);
		} else {
			String type = getJavaType(variable.getType());
			if (variable.isAssignable()) {
				mw.visitVarInsn(ALOAD, 0);
				mw.visitFieldInsn(GETFIELD, entityClass, variable.getName(), type);
			} else {
				Entity entity = (Entity) variable.eContainer();
				mw.visitFieldInsn(GETSTATIC, getClassName(entity), variable.getName(), type);
			}
		}
	}

	/**
	 * Generates the right *STORE instruction for the given local variable.
	 *
	 * @param target
	 *            target variable
	 */
	private void storeLocal(Var target) {
		int opcode = getOpcode(ISTORE, target.getType());
		int num = localMap.get(target);
		mw.visitVarInsn(opcode, num);
	}

	/**
	 * Stores the given value in the given port variable.
	 *
	 * @param target
	 *            target variable (instance of Port or contained in a Port)
	 * @param expr
	 *            value to store
	 */
	private void storePort(Var target, Expression expr) {
		Port port;
		String fieldName, fieldType;
		if (target instanceof Port) {
			port = (Port) target;
			fieldName = getPortValueFieldName(port);
			fieldType = getPortValueFieldType(port);
		} else {
			port = (Port) target.eContainer();
			fieldName = getPortFlagFieldName(target);
			fieldType = "Z";
		}

		mw.visitVarInsn(ALOAD, 0);
		String portName = port.getName();
		if (port.isSynchronous() && isSynchronous((Actor) port.eContainer())
				&& procedure.eContainingFeature() != DpnPackage.Literals.ACTION__COMBINATIONAL) {
			portName += "_next";
		}

		mw.visitFieldInsn(GETFIELD, entityClass, portName, PORT_TYPE);
		doSwitch(expr);
		mw.visitFieldInsn(PUTFIELD, PORT_CLASS, fieldName, fieldType);
	}

	protected <T extends EObject> void visit(EList<T> objects) {
		for (EObject object : objects) {
			doSwitch(object);
		}
	}

}
