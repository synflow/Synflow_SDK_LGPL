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
package com.synflow.ngDesign.internal.generators.verilog

import com.synflow.models.ir.ExprBinary
import com.synflow.models.ir.ExprBool
import com.synflow.models.ir.ExprInt
import com.synflow.models.ir.ExprList
import com.synflow.models.ir.ExprResize
import com.synflow.models.ir.ExprString
import com.synflow.models.ir.ExprTernary
import com.synflow.models.ir.ExprTypeConv
import com.synflow.models.ir.ExprUnary
import com.synflow.models.ir.ExprVar
import com.synflow.models.ir.OpBinary
import com.synflow.models.ir.OpUnary
import com.synflow.models.ir.TypeInt
import com.synflow.models.ir.util.TypeUtil
import com.synflow.ngDesign.internal.generators.ExpressionPrinter
import com.synflow.ngDesign.internal.generators.Namer

import static extension com.synflow.models.ir.util.TypeUtil.getSize
import static extension com.synflow.models.ir.util.TypeUtil.getType

/**
 * This class defines the expression printer for the Verilog code generator.
 *
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 */
class VerilogExpressionPrinter extends ExpressionPrinter {

	val protected Namer namer

	new(Namer namer) {
		this.namer = namer
	}

	override caseExprBinary(ExprBinary expr) {
		val e1 = expr.e1
		val e2 = expr.e2
		val op = expr.op
		val type = TypeUtil.getType(expr)

		if (op == OpBinary.SHIFT_LEFT) {
			'''{«doSwitch(e1)», {(«doSwitch(e2)»){1'b0}}}'''
		} else if (op == OpBinary.SHIFT_RIGHT) {
			// in Verilog, >>> is arithmetic shift, and >> is logical shift
			// this test is because arithmetic is only needed when shifting a signed expression
			// (arithmetic shift of an unsigned number is <=> to logical shift)
			'''(«doSwitch(e1)» «IF type.int && (type as TypeInt).signed»>>>«ELSE»>>«ENDIF» «doSwitch(e2)»)'''
		} else {
			'''(«doSwitch(e1)» «op.text» «doSwitch(e2)»)'''
		}
	}

	override caseExprBool(ExprBool expr) {
		if (expr.isValue()) "1'b1" else "1'b0"
	}

	override caseExprInt(ExprInt expr) {
		'''«expr.computedType.size»'«IF expr.computedType.signed»s«ENDIF»h«expr.value.toString(16)»'''
	}

	override caseExprList(ExprList expr)
		'''«FOR value : expr.value SEPARATOR ", "»«doSwitch(value, Integer.MAX_VALUE, 0)»«ENDFOR»'''

	override caseExprResize(ExprResize cast) {
		throw new UnsupportedOperationException
	}

	override caseExprString(ExprString expr) {
		// do not quote the value, since this is only used in $display
		expr.value
	}

	override caseExprTernary(ExprTernary expr) {
		'''(«doSwitch(expr.cond)» ? «doSwitch(expr.e1)» : «doSwitch(expr.e2)»)'''
	}

	override caseExprTypeConv(ExprTypeConv typeConv) {
		val expr = doSwitch(typeConv.expr)
		val typeName = typeConv.typeName
		if (typeName == "signed" || typeName == "unsigned") {
			return '''$«typeName»(«expr»)'''
		}

		val index = typeName.indexOf('.')
		val targetSize = Integer.parseInt(typeName.substring(index + 1))
		val sourceSize = typeConv.expr.type.size
		switch (typeName.substring(0, index)) {
			case "trunc": '''«expr»[«targetSize - 1» : 0]'''
			case "sext": '''{{«targetSize - sourceSize»{«expr»[«sourceSize - 1»]}}, «expr»}'''
			case "zext": '''{«targetSize - sourceSize»'b0, «expr»}'''
		}
	}

	override caseExprUnary(ExprUnary expr) {
		val subExpr = doSwitch(expr.expr)

		switch (expr.op) {
		case OpUnary.BITNOT: '''~ («subExpr»)'''
		case OpUnary.LOGIC_NOT: '''! («subExpr»)'''
		default: throw new UnsupportedOperationException
		}
	}

	override caseExprVar(ExprVar expr) {
		namer.getName(expr.use.variable)
	}

}
