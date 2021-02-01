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

import static com.synflow.models.util.SwitchUtil.DONE;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.getOpcode;
import static com.synflow.ngDesign.internal.generators.bytecode.IrGenerator.push;
import static java.math.BigInteger.ONE;
import static org.objectweb.asm.Opcodes.FCMPG;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.I2L;
import static org.objectweb.asm.Opcodes.IADD;
import static org.objectweb.asm.Opcodes.IAND;
import static org.objectweb.asm.Opcodes.ICONST_M1;
import static org.objectweb.asm.Opcodes.IDIV;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.IFGE;
import static org.objectweb.asm.Opcodes.IFGT;
import static org.objectweb.asm.Opcodes.IFLE;
import static org.objectweb.asm.Opcodes.IFLT;
import static org.objectweb.asm.Opcodes.IFNE;
import static org.objectweb.asm.Opcodes.IMUL;
import static org.objectweb.asm.Opcodes.INEG;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.IOR;
import static org.objectweb.asm.Opcodes.IREM;
import static org.objectweb.asm.Opcodes.ISHL;
import static org.objectweb.asm.Opcodes.ISHR;
import static org.objectweb.asm.Opcodes.ISUB;
import static org.objectweb.asm.Opcodes.IUSHR;
import static org.objectweb.asm.Opcodes.IXOR;
import static org.objectweb.asm.Opcodes.L2I;
import static org.objectweb.asm.Opcodes.LAND;
import static org.objectweb.asm.Opcodes.LCMP;
import static org.objectweb.asm.Opcodes.LSUB;
import static org.objectweb.asm.Opcodes.LXOR;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import com.synflow.models.ir.ExprBinary;
import com.synflow.models.ir.ExprBool;
import com.synflow.models.ir.ExprFloat;
import com.synflow.models.ir.ExprInt;
import com.synflow.models.ir.ExprList;
import com.synflow.models.ir.ExprResize;
import com.synflow.models.ir.ExprString;
import com.synflow.models.ir.ExprTernary;
import com.synflow.models.ir.ExprTypeConv;
import com.synflow.models.ir.ExprUnary;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeInt;
import com.synflow.models.ir.util.IrSwitch;
import com.synflow.models.ir.util.TypeUtil;
import com.synflow.models.util.Void;

/**
 * This class compiles an expression to bytecode.
 *
 * @author Matthieu Wipliez
 *
 */
public class ExpressionCompiler extends IrSwitch<Void> {

	protected MethodVisitor mw;

	protected ExpressionCompiler() {
		// subclasses must set mw before invoking methods in this class
	}

	public ExpressionCompiler(MethodVisitor mv) {
		this.mw = mv;
	}

	@Override
	public Void caseExprBinary(ExprBinary expr) {
		doSwitch(expr.getE1());
		doSwitch(expr.getE2());

		Type t1 = TypeUtil.getType(expr.getE1());
		OpBinary op = expr.getOp();
		if (op.isComparison()) {
			Type t2 = TypeUtil.getType(expr.getE2());
			compareOperands(op, TypeUtil.getLargest(t1, t2));
			return DONE;
		}

		Type type = TypeUtil.getType(expr);
		int size = TypeUtil.getSize(type);
		if (size > 64) {
			return compileBinaryBigInteger(expr);
		} else {
			int offset = size > 32 ? 1 : 0;
			return compileBinaryPrimitive(expr, t1, offset);
		}
	}

	@Override
	public Void caseExprBool(ExprBool expr) {
		push(mw, expr.isValue());
		return DONE;
	}

	@Override
	public Void caseExprFloat(ExprFloat expr) {
		BigDecimal value = expr.getValue();
		push(mw, value.floatValue());
		return DONE;
	}

	@Override
	public Void caseExprInt(ExprInt expr) {
		BigInteger value = expr.getValue();
		int size = TypeUtil.getSize(TypeUtil.getType(expr));
		if (size <= 32) {
			push(mw, value.intValue());
		} else if (size <= 64) {
			push(mw, value.longValue());
		} else {
			push(mw, value);
		}

		return DONE;
	}

	@Override
	public Void caseExprList(ExprList expr) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Void caseExprResize(ExprResize resize) {
		Expression expr = resize.getExpr();
		doSwitch(expr);

		Type type = TypeUtil.getType(expr);
		if (!type.isInt()) {
			return DONE;
		}

		TypeInt ti = (TypeInt) type;
		int source = ti.getSize();
		int target = resize.getTargetSize();
		if (target < source) {
			// truncate
			truncate(source, target, ti.isSigned());
		} else if (source < target) {
			// extend
			extend(source, target, ti.isSigned());
		} else {
			// unnecessary resize, ignore it and return expr
		}
		return DONE;
	}

	@Override
	public Void caseExprString(ExprString expr) {
		mw.visitLdcInsn(expr.getValue());
		return DONE;
	}

	@Override
	public Void caseExprTernary(ExprTernary expr) {
		doSwitch(expr.getCond());

		Label isTrue = new Label();
		Label exit = new Label();
		mw.visitJumpInsn(IFNE, isTrue);
		doSwitch(expr.getE2());
		mw.visitJumpInsn(GOTO, exit);

		mw.visitLabel(isTrue);
		doSwitch(expr.getE1());

		mw.visitLabel(exit);

		return DONE;
	}

	@Override
	public Void caseExprTypeConv(ExprTypeConv typeConv) {
		Expression expr = typeConv.getExpr();
		doSwitch(expr);

		Type type = TypeUtil.getType(expr);
		if (!type.isInt()) {
			return DONE;
		}

		TypeInt ti = (TypeInt) type;
		int bits = ti.getSize();

		boolean source = ti.isSigned();
		boolean target = ExprTypeConv.SIGNED.equals(typeConv.getTypeName());

		if (source && !target) {
			// to unsigned
			if (bits > 64) {
				truncBigInteger(bits);
			} else if (bits > 32) {
				truncLong(bits);
			} else {
				truncInt(bits);
			}
		} else if (target && !source) {
			// to signed
			if (bits > 64) {
				signedWrapAroundBigInteger(bits);
			} else if (bits > 32) {
				signedWrapAroundLong(bits);
			} else {
				signedWrapAroundInt(bits);
			}
		}

		return DONE;
	}

	@Override
	public Void caseExprUnary(ExprUnary unary) {
		Expression expr = unary.getExpr();
		Type type = TypeUtil.getType(expr);
		int size = TypeUtil.getSize(type);
		switch (unary.getOp()) {
		case BITNOT:
			if (size > 64) {
				doSwitch(expr);
				invokeBigInteger("not");
				truncBigInteger(size);
			} else if (size > 32) {
				mw.visitInsn(ICONST_M1);
				mw.visitInsn(I2L); // extend to long
				doSwitch(expr);
				mw.visitInsn(LXOR);
				truncLong(size);
			} else {
				mw.visitInsn(ICONST_M1);
				doSwitch(expr);
				mw.visitInsn(IXOR);
				truncInt(size);
			}
			break;
		case LOGIC_NOT:
			// implement !e as (1 - e) (because e is either 0 or 1)
			push(mw, 1);
			doSwitch(expr);
			mw.visitInsn(ISUB);
			break;
		case MINUS:
			doSwitch(expr);
			if (size > 64) {
				invokeBigInteger("negate");
			} else {
				mw.visitInsn(getOpcode(INEG, type));
			}
		}

		return DONE;
	}

	/**
	 * Compare operands of the given common type.
	 *
	 * @param op
	 *            comparison operator
	 * @param type
	 *            type
	 */
	private void compareOperands(OpBinary op, Type type) {
		boolean compareInt;
		if (type.isBool() || type.isInt() && ((TypeInt) type).getSize() <= 32) {
			// JVM treats "int" as a first-class citizen
			compareInt = true;
		} else {
			// and other types as second-class
			compareInt = false;
			if (type.isFloat()) {
				mw.visitInsn(FCMPG);
			} else {
				int size = ((TypeInt) type).getSize();
				if (size > 64) {
					invokeBigInteger("compareTo", "(Ljava/math/BigInteger;)I");
				} else {
					mw.visitInsn(LCMP);
				}
			}
		}

		int opcode;
		switch (op) {
		case EQ:
			opcode = IFEQ;
			break;
		case GE:
			opcode = IFGE;
			break;
		case GT:
			opcode = IFGT;
			break;
		case LE:
			opcode = IFLE;
			break;
		case LT:
			opcode = IFLT;
			break;
		case NE:
			opcode = IFNE;
			break;
		default:
			throw new IllegalArgumentException();
		}

		// IF_ICMPEQ = IFEQ + 6, IF_ICMPNE = IFNE + 6, etc.
		if (compareInt) {
			opcode += 6;
		}

		// generate jump to return 1 when opcode is true
		Label isTrue = new Label();
		Label exit = new Label();
		mw.visitJumpInsn(opcode, isTrue);
		push(mw, false);
		mw.visitJumpInsn(GOTO, exit);
		mw.visitLabel(isTrue);
		push(mw, true);
		mw.visitLabel(exit);
	}

	private Void compileBinaryBigInteger(ExprBinary expr) {
		// other operators
		String method;
		switch (expr.getOp()) {
		case BITAND:
		case LOGIC_AND:
			method = "and";
			break;
		case BITOR:
		case LOGIC_OR:
			method = "or";
			break;
		case BITXOR:
			method = "xor";
			break;
		case DIV:
		case DIV_INT:
			method = "divide";
			break;
		case MOD:
			method = "remainder";
		case MINUS:
			method = "subtract";
			break;
		case PLUS:
			method = "add";
			break;
		case TIMES:
			method = "multiply";
			break;
		case SHIFT_LEFT:
			invokeBigInteger("shiftLeft", "(I)Ljava/math/BigInteger;");
			return DONE;
		case SHIFT_RIGHT:
			invokeBigInteger("shiftRight", "(I)Ljava/math/BigInteger;");
			return DONE;
		case EXP:
			throw new UnsupportedOperationException();
		default:
			throw new IllegalArgumentException(expr.getOp().getName());
		}

		invokeBigInteger(method, "(Ljava/math/BigInteger;)Ljava/math/BigInteger;");
		return DONE;
	}

	private Void compileBinaryPrimitive(ExprBinary expr, Type left, int offset) {
		int opcode;
		switch (expr.getOp()) {
		case BITAND:
		case LOGIC_AND:
			opcode = IAND;
			break;
		case BITOR:
		case LOGIC_OR:
			opcode = IOR;
			break;
		case BITXOR:
			opcode = IXOR;
			break;
		case DIV:
		case DIV_INT:
			opcode = IDIV;
			break;
		case MOD:
			opcode = IREM;
		case MINUS:
			opcode = ISUB;
			break;
		case PLUS:
			opcode = IADD;
			break;
		case SHIFT_LEFT:
			opcode = ISHL;
			break;
		case SHIFT_RIGHT:
			opcode = (((TypeInt) left).isSigned()) ? ISHR : IUSHR;
			break;
		case TIMES:
			opcode = IMUL;
			break;
		case EXP:
			throw new UnsupportedOperationException();
		default:
			throw new IllegalArgumentException(expr.getOp().getName());
		}

		mw.visitInsn(opcode + offset);
		return DONE;
	}

	/**
	 * Extends from <code>source</code> bits to <code>target</code> bits.
	 *
	 * @param source
	 *            number of bits of source operand
	 * @param target
	 *            number of bits of target operand
	 */
	private void extend(int source, int target, boolean signed) {
		// we use cascading ifs because int -> BigInteger conversion must go through long

		if (target > 32 && source <= 32) {
			// convert int to long
			mw.visitInsn(I2L);
			if (!signed) {
				// if converting to long in unsigned, must not extend sign
				truncLong(source);
			}
		}

		if (target > 64 && source <= 64) {
			// invokes BigInteger.valueOf(long)
			mw.visitMethodInsn(INVOKESTATIC, "java/math/BigInteger", "valueOf",
					"(J)Ljava/math/BigInteger;", false);
			if (!signed) {
				// if converting to long in unsigned, must not extend sign
				truncBigInteger(source);
			}
		}
	}

	private void invokeBigInteger(String method) {
		invokeBigInteger(method, "()Ljava/math/BigInteger;");
	}

	private void invokeBigInteger(String method, String signature) {
		mw.visitMethodInsn(INVOKEVIRTUAL, "java/math/BigInteger", method, signature, false);
	}

	private void signedWrapAroundBigInteger(int bits) {
		BigInteger mask = ONE.shiftLeft(bits - 1);
		push(mw, mask);
		invokeBigInteger("xor", "(Ljava/math/BigInteger;)Ljava/math/BigInteger;");
		push(mw, mask);
		invokeBigInteger("subtract", "(Ljava/math/BigInteger;)Ljava/math/BigInteger;");
	}

	private void signedWrapAroundInt(int bits) {
		// if bits == 32 wrapping around is nop
		if (bits < 32) {
			int mask = 1 << (bits - 1);
			push(mw, mask);
			mw.visitInsn(IXOR);
			push(mw, mask);
			mw.visitInsn(ISUB);
		}
	}

	private void signedWrapAroundLong(int bits) {
		// if bits == 64 wrapping around is nop
		if (bits < 64) {
			long mask = 1L << (bits - 1);
			push(mw, mask);
			mw.visitInsn(LXOR);
			push(mw, mask);
			mw.visitInsn(LSUB);
		}
	}

	/**
	 * Truncates operand from <code>source</code> bits to <code>target</code> bits.
	 *
	 * @param source
	 *            number of bits of source operand
	 * @param target
	 *            number of bits of target operand
	 * @param signed
	 */
	private void truncate(int source, int target, boolean signed) {
		if (target > 64) {
			// big integer
			truncBigInteger(target);
			if (signed) {
				signedWrapAroundBigInteger(target);
			}
		} else if (target > 32) {
			// long
			if (source > 64) {
				invokeBigInteger("longValue", "()J");
			}

			truncLong(target);
			if (signed) {
				signedWrapAroundLong(target);
			}
		} else {
			// int
			if (source > 64) {
				invokeBigInteger("intValue", "()I");
			} else if (source > 32) {
				mw.visitInsn(L2I);
			}

			truncInt(target);
			if (signed) {
				signedWrapAroundInt(target);
			}
		}
	}

	private void truncBigInteger(int bits) {
		BigInteger mask = ONE.shiftLeft(bits).subtract(ONE);
		push(mw, mask);
		invokeBigInteger("and", "(Ljava/math/BigInteger;)Ljava/math/BigInteger;");
	}

	private void truncInt(int bits) {
		// bits must be less than 32 bits, otherwise mask == 0
		if (bits < 32) {
			int mask = (1 << bits) - 1;
			push(mw, mask);
			mw.visitInsn(IAND);
		}
	}

	private void truncLong(int bits) {
		// bits must be less than 64 bits, otherwise mask == 0
		if (bits < 64) {
			long mask = (1L << bits) - 1;
			push(mw, mask);
			mw.visitInsn(LAND);
		}
	}

}
