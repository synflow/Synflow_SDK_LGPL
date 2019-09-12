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

import com.synflow.models.ir.ExprBool
import com.synflow.models.ir.ExprFloat
import com.synflow.models.ir.ExprInt
import com.synflow.models.ir.ExprList
import com.synflow.models.ir.util.IrSwitch

import static extension com.synflow.ngDesign.internal.generators.GeneratorExtensions.getNumberOfHexadecimalDigits

/**
 * This class defines an expression printer used for .hex files, that
 * prints boolean values as integers, with "1" representing <code>true</code>,
 * and "0" representing <code>false</code>.
 *
 * @author Matthieu Wipliez
 *
 */
class VerilogHexPrinter extends IrSwitch<CharSequence> {

	val int numDigits

	val int size

	new(int size) {
		this.size = size
		numDigits = size.numberOfHexadecimalDigits
	}

	override caseExprBool(ExprBool expr) {
		if (expr.value) {
			"1"
		} else {
			"0"
		}
	}

	override caseExprFloat(ExprFloat expr) {
		expr.value.stripTrailingZeros.toString
	}

	override caseExprInt(ExprInt expr) {
		val value = if (expr.value < 0bi) {
				expr.value + 1bi.shiftLeft(size)
			} else {
				expr.value // print hexadecimal format with the correct size
			}

		String.format("%0" + numDigits + "x", value)
	}

	override caseExprList(ExprList expr) '''
		«FOR value : expr.value»
			«doSwitch(value)»
		«ENDFOR»
	'''

}
