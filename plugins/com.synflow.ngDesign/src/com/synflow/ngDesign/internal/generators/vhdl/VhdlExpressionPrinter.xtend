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
package com.synflow.ngDesign.internal.generators.vhdl

import com.synflow.models.ir.ExprBinary
import com.synflow.models.ir.ExprBool
import com.synflow.models.ir.ExprInt
import com.synflow.models.ir.ExprList
import com.synflow.models.ir.ExprResize
import com.synflow.models.ir.ExprString
import com.synflow.models.ir.ExprTypeConv
import com.synflow.models.ir.ExprUnary
import com.synflow.models.ir.ExprVar
import com.synflow.models.ir.OpBinary
import com.synflow.models.ir.OpUnary
import com.synflow.models.ir.Var
import com.synflow.ngDesign.internal.generators.ExpressionPrinter
import com.synflow.ngDesign.internal.generators.Namer
import java.math.BigInteger
import java.util.Map

/**
 * This class defines the expression printer for the VHDL code generator.
 *
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 */
class VhdlExpressionPrinter extends ExpressionPrinter {

	def static printQuotedValue(int size, BigInteger value) {
		if (size % 4 == 0) {
			// print hexadecimal format with the correct size
			'''x"«String.format("%0" + (size / 4) + "x", value)»"'''
		} else {
			val str = value.toString(2)
			if (size > str.length) {
				// if necessary, append n zeroes before the string
				'''"«String.format("%0" + (size - str.length) + "d", 0)»«str»"'''
			} else if (str.length > size) {
				// if necessary, remove the first n characters
				'''"«str.substring(str.length - size)»"'''
			} else {
				'''"«str»"'''
			}
		}
	}

	val protected Namer namer

	protected Map<Var, String> varMap

	new(Namer namer) {
		this.namer = namer
	}

	override caseExprBinary(ExprBinary expr) {
		val e1 = expr.e1
		val e2 = expr.e2
		val op = expr.op

		if (op == OpBinary.SHIFT_LEFT) {
			'''shift_left(«doSwitch(e1)», «(e2 as ExprInt).value»)'''
		} else if (op == OpBinary.SHIFT_RIGHT) {
			'''shift_right(«doSwitch(e1)», «(expr.e2 as ExprInt).value»)'''
		} else {
			'''(«doSwitch(e1)» «toString(op)» «doSwitch(e2)»)'''
		}
	}

	override caseExprBool(ExprBool expr) {
		if (expr.value) "'1'" else "'0'"
	}

	override caseExprInt(ExprInt expr) {
		printQuotedValue(expr.computedType.size, expr.value)
	}

	override caseExprList(ExprList expr) {
		'''«FOR subExpr : expr.value SEPARATOR ", "»«doSwitch(subExpr, Integer.MAX_VALUE, 0)»«ENDFOR»'''
	}

	override caseExprResize(ExprResize expr) {
		'''resize(«doSwitch(expr.expr)», «expr.targetSize»)'''
	}

	override caseExprTypeConv(ExprTypeConv expr) {
		'''«expr.typeName»(«doSwitch(expr.expr)»)'''
	}

	override caseExprString(ExprString expr)
		// returns a quoted string
		'''"«expr.getValue()»"'''

	override caseExprUnary(ExprUnary expr) {
		var subExpr = doSwitch(expr.expr)

		if (expr.op == OpUnary.MINUS) {
			'''0 - («subExpr»)'''
		} else {
			'''not («subExpr»)'''
		}
	}

	override caseExprVar(ExprVar expr) {
		val variable = expr.use.variable
		getName(variable)
	}

	/**
	 * Returns the adjusted name of the given variable (if present in varMap,
	 * otherwise returns variable.name)
	 */
	def protected getName(Var variable) {
		if (varMap !== null) {
			val name = varMap.get(variable)
			if (name !== null) {
				return name
			}
		}

		namer.getName(variable)
	}

	def toString(OpBinary op) {
		switch (op) {
		case OpBinary.BITAND: "and"
		case OpBinary.BITOR: "or"
		case OpBinary.BITXOR: "xor"

		case OpBinary.DIV: "/"

		case OpBinary.EQ: "="
		case OpBinary.LOGIC_AND: "and"
		case OpBinary.LOGIC_OR: "or"
		case OpBinary.NE: "/="

		default:
			op.text
		}
	}

}
