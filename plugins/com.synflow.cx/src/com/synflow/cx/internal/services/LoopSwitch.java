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
package com.synflow.cx.internal.services;

import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.ExpressionBinary;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.Statement;
import com.synflow.cx.cx.StatementAssign;
import com.synflow.cx.cx.StatementLoop;
import com.synflow.cx.cx.StatementVariable;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.models.dpn.Entity;

/**
 * This class defines a switch that extends the statement switch to handle loops. It returns true if
 * a loop is complex (i.e. if it is not compile-time unrollable).
 *
 * @author Matthieu Wipliez
 *
 */
public class LoopSwitch extends ScheduleModifierSwitch {

	private Entity entity;

	private IInstantiator instantiator;

	public LoopSwitch(IInstantiator instantiator, Entity entity) {
		this.instantiator = instantiator;
		this.entity = entity;
	}

	@Override
	public Boolean caseStatementLoop(StatementLoop stmt) {
		// first check if loop contains cycle modifiers or other complex loops
		if (!super.caseStatementLoop(stmt)) {
			Statement init = stmt.getInit();
			StatementAssign after = stmt.getAfter();
			if (init != null && after != null) {
				if (init instanceof StatementAssign) {
					StatementAssign assign = (StatementAssign) init;
					Variable variable = assign.getTarget().getSource().getVariable();
					if (CxUtil.isLocal(variable)) {
						Object value = instantiator.evaluate(entity, assign.getValue());
						if (value != null) {
							return checkBounds(variable, value, stmt.getCondition());
						}
					}
				} else if (init instanceof StatementVariable) {
					StatementVariable stmtVar = (StatementVariable) init;
					for (Variable variable : stmtVar.getVariables()) {
						Object value = instantiator.evaluate(entity, variable.getValue());
						if (value == null || checkBounds(variable, value, stmt.getCondition())) {
							return true;
						}
					}
					return false;
				}
			}
		}

		return true;
	}

	private boolean checkBounds(Variable variable, Object init, CxExpression condition) {
		if (condition instanceof ExpressionBinary) {
			ExpressionBinary exprBin = (ExpressionBinary) condition;
			CxExpression left = exprBin.getLeft();
			CxExpression right = exprBin.getRight();

			if (left instanceof ExpressionVariable) {
				ExpressionVariable exprVar = (ExpressionVariable) left;
				if (exprVar.getSource().getVariable() == variable && exprVar.getIndexes().isEmpty()) {
					Object max = instantiator.evaluate(entity, right);
					if (max != null) {
						// loop is not complex
						return false;
					}
				}
			}
		}

		return true;
	}

}
