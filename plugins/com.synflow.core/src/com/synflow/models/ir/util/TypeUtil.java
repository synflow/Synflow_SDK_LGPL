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
 
package com.synflow.models.ir.util;

import java.math.BigInteger;
import java.util.Iterator;

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
import com.synflow.models.ir.ExprVar;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.OpUnary;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeArray;
import com.synflow.models.ir.TypeInt;

/**
 * This class defines static utility methods to deal with types.
 *
 * @author Matthieu Wipliez
 *
 */
public class TypeUtil {

	private static class IrTyper extends IrSwitch<Type> {

		@Override
		public Type caseExprBinary(ExprBinary expr) {
			Type t1 = computeType(expr.getE1());
			Type t2 = computeType(expr.getE2());
			Object amount = ValueUtil.getValue(expr.getE2());
			return getTypeBinary(expr.getOp(), t1, t2, amount);
		}

		@Override
		public Type caseExprBool(ExprBool expr) {
			return ir.createTypeBool();
		}

		@Override
		public Type caseExprFloat(ExprFloat expr) {
			int precision = expr.getValue().precision();
			if (precision <= 11) {
				return ir.createTypeFloat(16);
			} else if (precision <= 24) {
				return ir.createTypeFloat(32);
			} else {
				return ir.createTypeFloat(64);
			}
		}

		@Override
		public Type caseExprInt(ExprInt expr) {
			return ir.createTypeIntOrUint(expr.getValue());
		}

		@Override
		public Type caseExprList(ExprList expr) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Type caseExprResize(ExprResize cast) {
			Type type = computeType(cast.getExpr());
			boolean signed = false;
			if (type != null && type.isInt()) {
				signed = ((TypeInt) type).isSigned();
				return ir.createTypeInt(cast.getTargetSize(), signed);
			}
			return type;
		}

		@Override
		public Type caseExprString(ExprString expr) {
			return ir.createTypeString();
		}

		@Override
		public Type caseExprTernary(ExprTernary ternary) {
			Type t1 = computeType(ternary.getE1());
			Type t2 = computeType(ternary.getE2());
			return getLargest(t1, t2);
		}

		@Override
		public Type caseExprTypeConv(ExprTypeConv cast) {
			Type typeExpr = computeType(cast.getExpr());
			boolean signed = false;
			if (typeExpr != null && typeExpr.isInt()) {
				signed = ((TypeInt) typeExpr).isSigned();
			}

			TypeInt type = ir.createTypeInt();
			if (ExprTypeConv.SIGNED.equals(cast.getTypeName())) {
				signed = true;
			} else if (ExprTypeConv.UNSIGNED.equals(cast.getTypeName())) {
				signed = false;
			}
			type.setSigned(signed);
			type.setSize(TypeUtil.getSize(typeExpr));
			return type;
		}

		@Override
		public Type caseExprUnary(ExprUnary expr) {
			Type type = computeType(expr.getExpr());
			Object amount = ValueUtil.getValue(expr.getExpr());
			return getTypeUnary(expr.getOp(), type, amount);
		}

		@Override
		public Type caseExprVar(ExprVar expr) {
			return expr.getUse().getVariable().getType();
		}

		private Type computeType(Expression expr) {
			if (expr.getComputedType() != null) {
				return expr.getComputedType();
			}

			return doSwitch(expr);
		}

	}

	private static final IrFactory ir = IrFactory.eINSTANCE;

	/**
	 * Returns <code>true</code> if an expression of type src can be assigned to a target of type
	 * dst.
	 *
	 * @param src
	 *            a type
	 * @param dst
	 *            the type src should be converted to
	 * @return <code>true</code> if type src can be converted to type dst
	 */
	public static boolean canAssign(Type src, Type dst) {
		if (src == null || dst == null) {
			return false;
		}

		if (src.isBool() && dst.isBool() || src.isString() && dst.isString()) {
			return true;
		}

		int srcSize = 0;
		if (src.isInt()) {
			TypeInt srcInt = (TypeInt) src;
			srcSize = srcInt.getSize();
		}

		int dstSize = 0;
		if (dst.isInt()) {
			TypeInt dstInt = (TypeInt) dst;
			dstSize = dstInt.getSize();
		}

		if (src.isInt() && dst.isInt()) {
			return srcSize <= dstSize;
		}

		if (src.isInt() && dst.isFloat()) {
			return true;
		}

		if (src.isArray() && dst.isArray()) {
			TypeArray typeSrc = (TypeArray) src;
			TypeArray typeDst = (TypeArray) dst;
			// Recursively check type convertibility
			if (canAssign(typeSrc.getElementType(), typeDst.getElementType())) {
				Iterator<Integer> itD = typeDst.getDimensions().iterator();
				Iterator<Integer> itS = typeSrc.getDimensions().iterator();
				while (itD.hasNext()) {
					if (!itS.hasNext()) {
						// not the same dimensions
						return false;
					}
				}

				if (itS.hasNext()) {
					return false;
				}
			}
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if an expression of type src can be assigned to a target of type
	 * dst, or if src is 0 or 1 and dst is a bool type.
	 *
	 * @param src
	 *            a type
	 * @param dst
	 *            the type src should be converted to
	 * @return <code>true</code> if type src can be converted to type dst
	 */
	public static boolean canAssignBool(Type src, Type dst) {
		if (canAssign(src, dst)) {
			return true;
		}

		if (dst.isBool()) {
			if (src.isInt()) {
				return ((TypeInt) src).getSize() == 1;
			}
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if an expression of type src can be casted to a target of type dst.
	 *
	 * @param src
	 *            source type
	 * @param dst
	 *            target type
	 * @return <code>true</code> if type src can be casted to type dst
	 */
	public static boolean canCast(Type src, Type dst) {
		if (src == null || dst == null) {
			return false;
		}

		return src.isBool() && dst.isBool() || src.isString() && dst.isString()
				|| (src.isInt() || src.isFloat()) && (dst.isInt() || dst.isFloat());
	}

	/**
	 * Returns a new type that is as large as the biggest type and compatible with both. If no such
	 * type exists (e.g. when types are not compatible with each other), <code>null</code> is
	 * returned.
	 *
	 * @param t1
	 *            a type
	 * @param t2
	 *            another type
	 * @return a type compatible with the given types and big enough to contain both
	 */
	public static Type getLargest(Type t1, Type t2) {
		if (t1 == null || t2 == null) {
			return null;
		}

		if (t1.isBool() && t2.isBool()) {
			return ir.createTypeBool();
		} else if (t1.isString() && t2.isString()) {
			return ir.createTypeString();
		} else if (t1.isInt() && t2.isInt()) {
			TypeInt ti1 = (TypeInt) t1;
			TypeInt ti2 = (TypeInt) t2;
			boolean signed = ti1.isSigned() || ti2.isSigned();
			int size = Math.max(ti1.getSize(), ti2.getSize());
			return ir.createTypeInt(size, signed);
		} else if (t1.isArray() && t2.isArray()) {
			TypeArray lt1 = (TypeArray) t1;
			TypeArray lt2 = (TypeArray) t2;
			Type type = getLargest(lt1.getElementType(), lt2.getElementType());
			if (type != null) {
				// only return a list when the underlying type is valid
				TypeArray array = ir.createTypeArray();
				array.setElementType(type);
				Iterator<Integer> itD = lt1.getDimensions().iterator();
				Iterator<Integer> itS = lt2.getDimensions().iterator();
				boolean ok = true;
				while (itD.hasNext()) {
					if (!itS.hasNext()) {
						// not the same dimensions
						ok = false;
					} else {
						int sizeD = itD.next();
						int sizeS = itS.next();
						int dim = Math.min(sizeD, sizeS);
						array.getDimensions().add(dim);
					}
				}

				// not ok if itD finished but not itS
				ok &= !itS.hasNext();

				if (ok) {
					return array;
				}

				// not ok: delete array
				IrUtil.delete(array);
			}
		}

		return null;
	}

	public static Type getLargestPlus1(Type t1, Type t2) {
		Type type = TypeUtil.getLargest(t1, t2);
		if (type instanceof TypeInt) {
			TypeInt typeInt = (TypeInt) type;
			typeInt.setSize(typeInt.getSize() + 1);
		}
		return type;
	}

	/**
	 * Returns the number of bits in the two's-complement representation of the given number,
	 * including a sign bit <i>only</i> if <code>number</code> is less than zero.
	 *
	 * @param number
	 *            a number
	 * @return the number of bits in the two's-complement representation of the given number,
	 *         <i>including</i> a sign bit
	 */
	public static int getSize(BigInteger number) {
		int cmp = number.compareTo(BigInteger.ZERO);
		if (cmp == 0) {
			// 0 is represented as a u1
			return 1;
		} else {
			int bitLength = number.bitLength();
			return (cmp > 0) ? bitLength : bitLength + 1;
		}
	}

	/**
	 * Returns the number of bits in the two's-complement representation of the given number,
	 * including a sign bit <i>only</i> if <code>number</code> is less than zero.
	 *
	 * @param number
	 *            a number
	 * @return the number of bits in the two's-complement representation of the given number,
	 *         <i>including</i> a sign bit
	 */
	public static int getSize(long number) {
		return getSize(BigInteger.valueOf(number));
	}

	/**
	 * Returns the size in bits of the given type. Returns <code>0</code> if the size does not make
	 * sense (e.g. for void).
	 *
	 * @param type
	 *            a type
	 * @return a size: 1 for bool, n for TypeInt(n), n * d1 * d2 * ... for TypeArray(n, dims), 0 for
	 *         others.
	 */
	public static int getSize(Type type) {
		if (type.isBool()) {
			return 1;
		} else if (type.isInt()) {
			TypeInt typeInt = (TypeInt) type;
			return typeInt.getSize();
		} else if (type.isArray()) {
			TypeArray array = (TypeArray) type;
			int size = getSize(array.getElementType());
			for (int dim : array.getDimensions()) {
				size *= dim;
			}
			return size;
		}

		return 0;
	}

	/**
	 * Returns the smallest type of (t1, t2). When t1 and t2 have different signedness (e.g. one is
	 * a int and the other is a uint), an int is returned.
	 *
	 * @param t1
	 *            a type
	 * @param t2
	 *            another type
	 * @return the smallest of the given types
	 */
	public static Type getSmallest(Type t1, Type t2) {
		if (t1.isBool() && t2.isBool()) {
			return ir.createTypeBool();
		} else if (t1.isInt() && t2.isInt()) {
			TypeInt ti1 = (TypeInt) t1;
			TypeInt ti2 = (TypeInt) t2;
			boolean signed = ti1.isSigned() || ti2.isSigned();
			int size = Math.min(ti1.getSize(), ti2.getSize());
			return ir.createTypeInt(size, signed);
		}

		return null;
	}

	/**
	 * Returns a new type whose size is the sum of the two given types' sizes. If types are not
	 * compatible, returns <code>null</code>.
	 *
	 * @param t1
	 *            type 1
	 * @param t2
	 *            type 2
	 * @return a type
	 */
	public static Type getSum(Type t1, Type t2) {
		Type type = TypeUtil.getLargest(t1, t2);
		if (type instanceof TypeInt) {
			TypeInt ti1 = (TypeInt) t1;
			TypeInt ti2 = (TypeInt) t2;
			TypeInt typeInt = (TypeInt) type;
			int size1 = ti1.getSize();
			int size2 = ti2.getSize();
			typeInt.setSize(size1 + size2);
		}
		return type;
	}

	/**
	 * Returns the type of the given expression.
	 *
	 * @param expr
	 *            an expression
	 * @return a type
	 */
	public static Type getType(Expression expr) {
		return new IrTyper().computeType(expr);
	}

	/**
	 * Returns the type of a binary expression whose left operand has type t1 and right operand has
	 * type t2, and whose operator is given.
	 *
	 * @param op
	 *            operator
	 * @param t1
	 *            type of the first operand
	 * @param t2
	 *            type of the second operand
	 * @return the type of the binary expression, or <code>null</code>
	 */
	public static Type getTypeBinary(OpBinary op, Type t1, Type t2, Object amount) {
		if (t1 == null || t2 == null) {
			return null;
		}

		Type type = tryGetPreciseType(t1, op, amount);
		if (type != null) {
			return type;
		}

		switch (op) {
		case BITAND:
			return TypeUtil.getSmallest(t1, t2);

		case BITOR:
		case BITXOR:
			return TypeUtil.getLargest(t1, t2);

		case TIMES:
			return TypeUtil.getSum(t1, t2);

		case MINUS:
		case PLUS:
			return TypeUtil.getLargestPlus1(t1, t2);

		case EQ:
		case NE:
			// can compare (in)equality of any two compatible types
			if (getLargest(t1, t2) != null) {
				return ir.createTypeBool();
			}
			return null;

		case GE:
		case GT:
		case LE:
		case LT: {
			// can compare any two compatible types (except bool)
			Type largest = getLargest(t1, t2);
			if (largest != null && !largest.isBool()) {
				return ir.createTypeBool();
			}
			return null;
		}

		case LOGIC_AND:
		case LOGIC_OR:
			// operands must be booleans
			if (t1.isBool() && t2.isBool()) {
				return ir.createTypeBool();
			}
			return null;

		default:
			// shifts, div, mod are typed by tryGetPreciseType
			// exp is not typed
			return null;
		}
	}

	/**
	 * Returns the type of a unary expression whose operand has type type and whose operator is
	 * given.
	 *
	 * @param op
	 *            operator
	 * @param type
	 *            type of the first operand
	 * @return the type of the unary expression, or <code>null</code>
	 */
	public static Type getTypeUnary(OpUnary op, Type type, Object value) {
		if (type == null) {
			return null;
		}

		switch (op) {
		case BITNOT:
			// operand must be int
			if (type.isInt()) {
				return IrUtil.copy(type);
			}
			return null;

		case LOGIC_NOT:
			// operand must be boolean
			if (type.isBool()) {
				return ir.createTypeBool();
			}
			return null;

		case MINUS:
			// operand is constant: type as literal
			if (ValueUtil.isInt(value)) {
				BigInteger negated = (BigInteger) ValueUtil.negate(value);
				return ir.createTypeIntOrUint(negated);
			}

			// -x <=> 0 - x <=> adds one bit to the type of x
			if (type.isInt()) {
				int size = ((TypeInt) type).getSize();
				return ir.createTypeInt(size + 1, true);
			}
		default:
			return null;
		}
	}

	/**
	 * Try and get a precise type if the given expression evaluates to a compile-time integer.
	 *
	 * @param t1
	 * @param op
	 * @param right
	 * @return
	 */
	private static Type tryGetPreciseType(Type t1, OpBinary op, Object val2) {
		if (!ValueUtil.isInt(val2)) {
			return null;
		}

		int n = ((BigInteger) val2).intValue();
		if (n <= 0) {
			return null;
		}

		if (!(t1 instanceof TypeInt)) {
			return null;
		}

		TypeInt typeInt = (TypeInt) IrUtil.copy(t1);
		if (op == OpBinary.DIV) {
			// valid because we know "n" is a multiple of two (see validator)
			int size = BigInteger.valueOf(n - 1).bitLength();
			typeInt.setSize(typeInt.getSize() - size);
		} else if (op == OpBinary.MOD) {
			// valid because we know "n" is a multiple of two (see validator)
			int size = BigInteger.valueOf(n - 1).bitLength();
			typeInt.setSize(size);
		} else if (op == OpBinary.SHIFT_LEFT) {
			typeInt.setSize(typeInt.getSize() + n);
		} else if (op == OpBinary.SHIFT_RIGHT) {
			typeInt.setSize(typeInt.getSize() - n);
		} else {
			return null;
		}

		return typeInt;
	}

}
