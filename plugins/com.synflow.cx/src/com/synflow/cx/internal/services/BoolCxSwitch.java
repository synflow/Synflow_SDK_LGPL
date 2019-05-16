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

import static com.synflow.models.util.SwitchUtil.check;

import com.google.common.collect.Iterables;
import com.synflow.cx.cx.Block;
import com.synflow.cx.cx.Branch;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.ExpressionBinary;
import com.synflow.cx.cx.ExpressionIf;
import com.synflow.cx.cx.ExpressionList;
import com.synflow.cx.cx.ExpressionUnary;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.Statement;
import com.synflow.cx.cx.StatementAssert;
import com.synflow.cx.cx.StatementAssign;
import com.synflow.cx.cx.StatementIf;
import com.synflow.cx.cx.StatementLabeled;
import com.synflow.cx.cx.StatementLoop;
import com.synflow.cx.cx.StatementPrint;
import com.synflow.cx.cx.StatementReturn;
import com.synflow.cx.cx.StatementVariable;
import com.synflow.cx.cx.StatementWrite;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.cx.util.CxSwitch;

/**
 * This class defines a full boolean switch.
 *
 * @author Matthieu Wipliez
 *
 */
public class BoolCxSwitch extends CxSwitch<Boolean> {

	@Override
	public Boolean caseBlock(Block block) {
		return check(this, block.getStmts());
	}

	@Override
	public Boolean caseBranch(Branch branch) {
		return check(this, branch.getCondition(), branch.getBody());
	}

	@Override
	public Boolean caseCxExpression(CxExpression expr) {
		return false;
	}

	@Override
	public Boolean caseExpressionBinary(ExpressionBinary expr) {
		return check(this, expr.getLeft(), expr.getRight());
	}

	@Override
	public Boolean caseExpressionIf(ExpressionIf expr) {
		return check(this, expr.getCondition(), expr.getThen(), expr.getElse());
	}

	@Override
	public Boolean caseExpressionList(ExpressionList list) {
		return check(this, list.getValues());
	}

	@Override
	public Boolean caseExpressionUnary(ExpressionUnary expr) {
		return doSwitch(expr.getExpression());
	}

	@Override
	public Boolean caseExpressionVariable(ExpressionVariable expr) {
		return check(this, Iterables.concat(expr.getIndexes(), expr.getParameters()));
	}

	@Override
	public Boolean caseStatement(Statement stmt) {
		return false;
	}

	@Override
	public Boolean caseStatementAssert(StatementAssert stmt) {
		return doSwitch(stmt.getCondition());
	}

	@Override
	public Boolean caseStatementAssign(StatementAssign stmt) {
		return check(this, stmt.getTarget(), stmt.getValue());
	}

	@Override
	public Boolean caseStatementIf(StatementIf stmtIf) {
		return check(this, stmtIf.getBranches());
	}

	@Override
	public Boolean caseStatementLabeled(StatementLabeled stmt) {
		return check(this, stmt.getStmt());
	}

	@Override
	public Boolean caseStatementLoop(StatementLoop stmt) {
		return check(this, stmt.getInit(), stmt.getCondition(), stmt.getBody(), stmt.getAfter());
	}

	@Override
	public Boolean caseStatementPrint(StatementPrint print) {
		return check(this, print.getArgs());
	}

	@Override
	public Boolean caseStatementReturn(StatementReturn stmt) {
		return check(this, stmt.getValue());
	}

	@Override
	public Boolean caseStatementVariable(StatementVariable stmt) {
		return check(this, stmt.getVariables());
	}

	@Override
	public Boolean caseStatementWrite(StatementWrite write) {
		return doSwitch(write.getValue());
	}

	@Override
	public Boolean caseVariable(Variable variable) {
		if (check(this, Iterables.concat(variable.getDimensions(), variable.getParameters()))) {
			return true;
		}
		return check(this, variable.getBody(), variable.getValue());
	}

}
