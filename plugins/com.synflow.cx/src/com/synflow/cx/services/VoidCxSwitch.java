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
package com.synflow.cx.services;

import static com.synflow.models.util.SwitchUtil.DONE;
import static com.synflow.models.util.SwitchUtil.visit;

import com.google.common.collect.Iterables;
import com.synflow.cx.cx.Block;
import com.synflow.cx.cx.Branch;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.ExpressionBinary;
import com.synflow.cx.cx.ExpressionCast;
import com.synflow.cx.cx.ExpressionIf;
import com.synflow.cx.cx.ExpressionList;
import com.synflow.cx.cx.ExpressionUnary;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Module;
import com.synflow.cx.cx.Network;
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
import com.synflow.cx.cx.TypeDecl;
import com.synflow.cx.cx.Typedef;
import com.synflow.cx.cx.VarDecl;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.cx.util.CxSwitch;
import com.synflow.models.util.Void;

/**
 * This class defines a full void switch.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class VoidCxSwitch extends CxSwitch<Void> {

	@Override
	public Void caseBlock(Block block) {
		return visit(this, block.getStmts());
	}

	@Override
	public Void caseBranch(Branch branch) {
		return visit(this, branch.getCondition(), branch.getBody());
	}

	@Override
	public Void caseCxExpression(CxExpression expr) {
		return DONE;
	}

	@Override
	public Void caseExpressionBinary(ExpressionBinary expr) {
		return visit(this, expr.getLeft(), expr.getRight());
	}

	@Override
	public Void caseExpressionCast(ExpressionCast expr) {
		return visit(this, expr.getExpression());
	}

	@Override
	public Void caseExpressionIf(ExpressionIf expr) {
		return visit(this, expr.getCondition(), expr.getThen(), expr.getElse());
	}

	@Override
	public Void caseExpressionList(ExpressionList list) {
		return visit(this, list.getValues());
	}

	@Override
	public Void caseExpressionUnary(ExpressionUnary expr) {
		return visit(this, expr.getExpression());
	}

	@Override
	public Void caseExpressionVariable(ExpressionVariable expr) {
		return visit(this, Iterables.concat(expr.getIndexes(), expr.getParameters()));
	}

	@Override
	public Void caseInst(Inst inst) {
		return visit(this, inst.getTask());
	}

	@Override
	public Void caseModule(Module module) {
		return visit(this, module.getEntities());
	}

	@Override
	public Void caseNetwork(Network network) {
		return visit(this, network.getInstances());
	}

	@Override
	public Void caseStatement(Statement stmt) {
		return DONE;
	}

	@Override
	public Void caseStatementAssert(StatementAssert stmt) {
		return visit(this, stmt.getCondition());
	}

	@Override
	public Void caseStatementAssign(StatementAssign stmt) {
		return visit(this, stmt.getTarget(), stmt.getValue());
	}

	@Override
	public Void caseStatementIf(StatementIf stmtIf) {
		return visit(this, stmtIf.getBranches());
	}

	@Override
	public Void caseStatementLabeled(StatementLabeled stmt) {
		return visit(this, stmt.getStmt());
	}

	@Override
	public Void caseStatementLoop(StatementLoop stmt) {
		return visit(this, stmt.getInit(), stmt.getCondition(), stmt.getBody(), stmt.getAfter());
	}

	@Override
	public Void caseStatementPrint(StatementPrint stmt) {
		return visit(this, stmt.getArgs());
	}

	@Override
	public Void caseStatementReturn(StatementReturn stmt) {
		return visit(this, stmt.getValue());
	}

	@Override
	public Void caseStatementVariable(StatementVariable stmt) {
		return visit(this, stmt.getVariables());
	}

	@Override
	public Void caseStatementWrite(StatementWrite write) {
		return visit(this, write.getValue());
	}

	@Override
	public Void caseTypeDecl(TypeDecl type) {
		return visit(this, type.getSize());
	}

	@Override
	public Void caseTypedef(Typedef typedef) {
		return visit(this, typedef.getType());
	}

	@Override
	public Void caseVarDecl(VarDecl decl) {
		return visit(this, decl.getVariables());
	}

	@Override
	public Void caseVariable(Variable variable) {
		visit(this, variable.getDimensions());
		visit(this, variable.getBody());
		return visit(this, variable.getValue());
	}

}
