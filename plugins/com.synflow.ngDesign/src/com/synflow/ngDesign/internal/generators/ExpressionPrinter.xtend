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
package com.synflow.ngDesign.internal.generators

import com.synflow.models.ir.Expression
import com.synflow.models.ir.util.IrSwitch

/**
 * This class defines common methods to expression printer in code generators.
 *
 * @author Matthieu Wipliez
 */
class ExpressionPrinter extends IrSwitch<CharSequence> {

	protected int branch = 0 // left

	protected int precedence = Integer.MAX_VALUE

	def doSwitch(Expression expression, int newPrecedence, int newBranch) {
		val oldBranch = branch
		val oldPrecedence = precedence

		branch = newBranch
		precedence = newPrecedence

		val result = doSwitch(expression)

		precedence = oldPrecedence
		branch = oldBranch

		result
	}

}
