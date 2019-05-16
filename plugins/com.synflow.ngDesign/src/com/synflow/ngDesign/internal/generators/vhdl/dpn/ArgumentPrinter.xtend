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
package com.synflow.ngDesign.internal.generators.vhdl.dpn

import com.synflow.models.ir.ExprBool
import com.synflow.models.ir.ExprInt
import com.synflow.models.ir.ExprString
import com.synflow.models.ir.util.IrSwitch

/**
 * This class defines a value printer for instance arguments
 *
 * @author Matthieu Wipliez
 */
class ArgumentPrinter extends IrSwitch<CharSequence> {

	override caseExprBool(ExprBool expr) {
		expr.value.toString
	}

	override caseExprInt(ExprInt expr) {
		expr.value.toString
	}

	override caseExprString(ExprString expr) {
		'''"«expr.value»"'''
	}

}
