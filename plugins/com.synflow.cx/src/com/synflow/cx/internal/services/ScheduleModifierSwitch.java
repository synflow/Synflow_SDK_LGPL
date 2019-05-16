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
package com.synflow.cx.internal.services;

import static com.synflow.cx.CxConstants.PROP_AVAILABLE;
import static com.synflow.cx.CxConstants.PROP_READ;
import static com.synflow.cx.CxConstants.PROP_READY;

import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.StatementFence;
import com.synflow.cx.cx.StatementIdle;
import com.synflow.cx.cx.StatementWrite;
import com.synflow.cx.cx.Variable;

/**
 * This class defines a switch that visits a statement and returns true if it (or any expression or
 * statement it contains) may be a schedule modifier like fence and idle, and any action on a port
 * (available, read, write).
 *
 * @author Matthieu Wipliez
 *
 */
public class ScheduleModifierSwitch extends BoolCxSwitch {

	@Override
	public Boolean caseExpressionVariable(ExpressionVariable expr) {
		String prop = expr.getPropertyName();
		if (PROP_AVAILABLE.equals(prop) || PROP_READ.equals(prop) || PROP_READY.equals(prop)) {
			return true;
		}

		Variable variable = expr.getSource().getVariable();
		if (CxUtil.isFunctionNotConstant(variable)) {
			// if function has side-effect, we visit it
			if (doSwitch(variable)) {
				return true;
			}
		}

		return super.caseExpressionVariable(expr);
	}

	@Override
	public Boolean caseStatementFence(StatementFence stmt) {
		return true;
	}

	@Override
	public Boolean caseStatementIdle(StatementIdle stmt) {
		return true;
	}

	@Override
	public Boolean caseStatementWrite(StatementWrite stmt) {
		return true;
	}

}
