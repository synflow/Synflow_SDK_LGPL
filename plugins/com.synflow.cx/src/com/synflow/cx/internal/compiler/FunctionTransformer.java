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
package com.synflow.cx.internal.compiler;

import static com.synflow.cx.CxConstants.NAME_SIZEOF;
import static com.synflow.cx.CxConstants.PROP_LENGTH;
import static com.synflow.cx.internal.TransformerUtil.getStartLine;
import static com.synflow.models.ir.OpBinary.BITAND;
import static com.synflow.models.ir.OpBinary.DIV;
import static com.synflow.models.ir.OpBinary.MOD;
import static com.synflow.models.ir.OpBinary.SHIFT_LEFT;
import static com.synflow.models.ir.OpBinary.SHIFT_RIGHT;
import static java.math.BigInteger.ONE;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Block;
import com.synflow.cx.cx.Branch;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.ExpressionBinary;
import com.synflow.cx.cx.ExpressionBoolean;
import com.synflow.cx.cx.ExpressionCast;
import com.synflow.cx.cx.ExpressionFloat;
import com.synflow.cx.cx.ExpressionIf;
import com.synflow.cx.cx.ExpressionInteger;
import com.synflow.cx.cx.ExpressionString;
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
import com.synflow.cx.cx.Variable;
import com.synflow.cx.cx.util.CxSwitch;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.cx.internal.AstUtil;
import com.synflow.cx.internal.services.Typer;
import com.synflow.models.dpn.Entity;
import com.synflow.models.ir.BlockIf;
import com.synflow.models.ir.BlockWhile;
import com.synflow.models.ir.ExprBinary;
import com.synflow.models.ir.ExprInt;
import com.synflow.models.ir.ExprVar;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstCall;
import com.synflow.models.ir.InstReturn;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.OpUnary;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.util.IrUtil;
import com.synflow.models.ir.util.ValueUtil;

/**
 * This class transforms Cx statement into IR blocks and instructions.
 *
 * @author Matthieu Wipliez
 * @see IrBuilder
 */
public class FunctionTransformer extends CxSwitch<EObject>implements Transformer {

	protected static final IrFactory ir = IrFactory.eINSTANCE;

	protected final IrBuilder builder;

	/**
	 * Creates a new function transformer with the given entity.
	 *
	 * @param entity
	 *            IR entity being created
	 */
	public FunctionTransformer(IInstantiator instantiator, Entity entity) {
		this(new IrBuilder(instantiator, entity));
	}

	/**
	 * Creates a new FunctionTransformer with the given IR builder, and set its expression
	 * transformer to <code>this</code>.
	 *
	 * @param builder
	 *            IR builder
	 */
	protected FunctionTransformer(IrBuilder builder) {
		this.builder = builder;
		builder.setTransformer(this);
	}

	@Override
	public EObject caseBlock(Block block) {
		for (Statement statement : block.getStmts()) {
			doSwitch(statement);
		}

		return null;
	}

	@Override
	public Expression caseExpressionBinary(ExpressionBinary expression) {
		OpBinary op = OpBinary.getOperator(expression.getOperator());
		Expression e1 = transformExpr(expression.getLeft());
		Expression e2;

		if (op == DIV || op == MOD || op == SHIFT_LEFT || op == SHIFT_RIGHT) {
			Object value = builder.instantiator.evaluate(builder.entity, expression.getRight());

			if (op == OpBinary.DIV) {
				// div n <=> right shift by log2(n)
				// when n is constant and power of two
				op = SHIFT_RIGHT;
				e2 = ir.createExprInt(((BigInteger) value).bitLength() - 1);
			} else if (op == MOD) {
				// mod n <=> & (n - 1) when n is constant power of two
				op = BITAND;
				e2 = ir.createExprInt(((BigInteger) value).subtract(ONE));
			} else /* if (op == SHIFT_LEFT || op == SHIFT_RIGHT) */ {
				// shifts must have constant second operand
				e2 = ir.createExprInt((BigInteger) value);
			}
		} else {
			// default case: transform expression
			e2 = transformExpr(expression.getRight());
		}

		return ir.createExprBinary(e1, op, e2);
	}

	@Override
	public Expression caseExpressionBoolean(ExpressionBoolean expression) {
		return ir.createExprBool(expression.isValue());
	}

	@Override
	public Expression caseExpressionCast(ExpressionCast expression) {
		Type targetType = builder.instantiator.computeType(builder.entity, expression.getType());

		CxExpression subExpr = expression.getExpression();
		Type sourceType = builder.instantiator.computeType(builder.entity, subExpr);
		Expression expr = transformExpr(subExpr);

		return ir.cast(targetType, sourceType, expr);
	}

	@Override
	public Expression caseExpressionFloat(ExpressionFloat expression) {
		return ir.createExprFloat(expression.getValue());
	}

	@Override
	public Expression caseExpressionIf(ExpressionIf expression) {
		builder.saveBlocks();

		// create block
		BlockIf block = ir.createBlockIf();
		block.setJoinBlock(ir.createBlockBasic());
		int lineNumber = getStartLine(expression);
		block.setLineNumber(lineNumber);

		// translate condition
		Expression condition = transformExpr(expression.getCondition());
		block.setCondition(condition);

		// add block (must be done after condition has been transformed)
		builder.add(block);

		// update target if necessary
		Type type = builder.instantiator.computeType(builder.entity, expression);
		Var target = builder.createLocal(lineNumber, type, "tmp_if");

		// transforms "then" and "else" expressions
		builder.setBlocks(block.getThenBlocks());
		builder.storeExpr(lineNumber, target, null, expression.getThen());

		builder.setBlocks(block.getElseBlocks());
		builder.storeExpr(lineNumber, target, null, expression.getElse());
		builder.restoreBlocks();

		// return expr
		return ir.createExprVar(target);
	}

	@Override
	public Expression caseExpressionInteger(ExpressionInteger expression) {
		return ir.createExprInt(expression.getValue());
	}

	@Override
	public Expression caseExpressionString(ExpressionString expression) {
		return ir.createExprString(expression.getValue());
	}

	@Override
	public Expression caseExpressionUnary(ExpressionUnary expression) {
		if (NAME_SIZEOF.equals(expression.getOperator())) {
			Object value = builder.instantiator.evaluate(builder.entity, expression);
			return ir.createExprInt(ValueUtil.getInt(value));
		}

		OpUnary op = OpUnary.getOperator(expression.getOperator());
		CxExpression subExpr = expression.getExpression();
		switch (op) {
		case MINUS:
			// replace ExprUnary(-, n) by ExprInt(-n)
			if (subExpr instanceof ExpressionInteger) {
				ExpressionInteger exprInt = (ExpressionInteger) subExpr;
				return ir.createExprInt(exprInt.getValue().negate());
			}

		default:
			// fall-through for other expressions
			Expression expr = transformExpr(subExpr);
			return ir.createExprUnary(op, expr);
		}
	}

	@Override
	public Expression caseExpressionVariable(ExpressionVariable expression) {
		Variable variable = expression.getSource().getVariable();
		if (CxUtil.isFunction(variable)) {
			return translateCall(expression);
		}

		String prop = expression.getPropertyName();
		if (PROP_LENGTH.equals(prop)) {
			Object value = builder.instantiator.evaluate(builder.entity, expression);
			return ir.createExprInt((BigInteger) value);
		}

		Var source = builder.getMapping(variable);
		Type type = source.getType();
		int dimensions = Typer.getNumDimensions(type);

		// loads variable (do not perform bit selection though)
		int lineNumber = getStartLine(expression);
		Var target = builder.loadVariable(lineNumber, source, expression.getIndexes());

		// bit selection
		ExprVar exprVar = ir.createExprVar(target);
		if (dimensions < expression.getIndexes().size()) {
			CxExpression exprIndex = expression.getIndexes().get(dimensions);
			int index = builder.instantiator.evaluateInt(builder.entity, exprIndex);
			ExprInt mask = ir.createExprInt(ONE.shiftLeft(index));
			ExprBinary exprBin = ir.createExprBinary(exprVar, OpBinary.BITAND, mask);

			ExprInt shift = ir.createExprInt(0);
			return ir.createExprBinary(exprBin, OpBinary.NE, shift);
		} else {
			return exprVar;
		}
	}

	@Override
	public EObject caseStatementAssert(StatementAssert stmtAssert) {
		hookBefore(stmtAssert);

		int lineNumber = getStartLine(stmtAssert);
		Expression condition = transformExpr(stmtAssert.getCondition());

		InstCall call = ir.createInstCall(lineNumber, null, null, Arrays.asList(condition));
		call.setAssert(true);
		builder.add(call);

		return null;
	}

	@Override
	public EObject caseStatementAssign(StatementAssign assign) {
		hookBefore(assign);

		if (assign.getOp() == null) {
			// no target
			transformExpr(assign.getValue());
		} else {
			// get target
			Variable variable = assign.getTarget().getSource().getVariable();
			Var target = builder.getMapping(variable);

			// transform value
			int lineNumber = getStartLine(assign);
			CxExpression value = AstUtil.getAssignValue(assign);
			builder.storeExpr(lineNumber, target, assign.getTarget().getIndexes(), value);
		}

		return null;
	}

	@Override
	public EObject caseStatementIf(StatementIf stmtIf) {
		hookBefore(stmtIf);

		builder.saveBlocks();

		// transforms all branches (including 'else' branch)
		for (Branch stmt : stmtIf.getBranches()) {
			CxExpression condition = stmt.getCondition();
			if (condition == null) {
				// 'else' branch
				doSwitch(stmt.getBody());
			} else {
				// create If block
				BlockIf node = ir.createBlockIf();
				node.setJoinBlock(ir.createBlockBasic());
				node.setLineNumber(getStartLine(stmtIf));
				node.setCondition(transformExpr(condition));

				// add If to blocks
				builder.add(node);

				// transforms body in the "then" blocks
				builder.setBlocks(node.getThenBlocks());
				doSwitch(stmt.getBody());

				// next branch/else will be appended to the "else" blocks
				builder.setBlocks(node.getElseBlocks());
			}
		}

		builder.restoreBlocks();

		return null;
	}

	@Override
	public EObject caseStatementLabeled(StatementLabeled stmt) {
		return doSwitch(stmt.getStmt());
	}

	@Override
	public EObject caseStatementLoop(StatementLoop stmtFor) {
		hookBefore(stmtFor);

		// translate init
		doSwitch(stmtFor.getInit());

		builder.saveBlocks();

		// to track the instructions created when condition was transformed
		List<com.synflow.models.ir.Block> initNodes = new ArrayList<>();
		builder.setBlocks(initNodes);

		// create the while
		BlockWhile nodeWhile = ir.createBlockWhile();
		nodeWhile.setJoinBlock(ir.createBlockBasic());
		int lineNumber = getStartLine(stmtFor);
		nodeWhile.setLineNumber(lineNumber);

		// transform condition
		Expression condition = transformExpr(stmtFor.getCondition());
		nodeWhile.setCondition(condition);

		// transform body and after
		builder.setBlocks(nodeWhile.getBlocks());
		doSwitch(stmtFor.getBody());
		doSwitch(stmtFor.getAfter());

		// copy instructions
		nodeWhile.getBlocks().addAll(IrUtil.copy(initNodes));

		builder.restoreBlocks();

		// add init nodes and while
		builder.addAll(initNodes);
		builder.add(nodeWhile);

		return null;
	}

	@Override
	public EObject caseStatementPrint(StatementPrint print) {
		hookBefore(print);

		int lineNumber = getStartLine(print);
		InstCall call = ir.createInstCall(lineNumber, null, null,
				builder.transformExpressions(print.getArgs()));
		call.setPrint(true);
		builder.add(call);

		return null;
	}

	@Override
	public EObject caseStatementReturn(StatementReturn stmtReturn) {
		hookBefore(stmtReturn);

		InstReturn instReturn = ir.createInstReturn(transformExpr(stmtReturn.getValue()));
		builder.add(instReturn);
		return instReturn;
	}

	@Override
	public EObject caseStatementVariable(StatementVariable stmtVar) {
		for (Variable variable : stmtVar.getVariables()) {
			doSwitch(variable);
		}

		return null;
	}

	@Override
	public EObject caseVariable(Variable variable) {
		if (CxUtil.isFunction(variable)) {
			// function
			builder.setProcedure(variable);

			// transform parameters
			for (Variable parameter : variable.getParameters()) {
				builder.getProcedure().getParameters().add(builder.transformLocal(parameter));
			}

			// transform body
			doSwitch(variable.getBody());

			return builder.getProcedure();
		} else {
			// local variable
			hookBefore(variable);

			// creates local variable and adds it to this procedure
			Var var = builder.transformLocal(variable);
			builder.getProcedure().getLocals().add(var);

			// assign a value (if any) to the variable
			CxExpression value = (CxExpression) variable.getValue();
			if (value != null) {
				builder.storeExpr(var.getLineNumber(), var, null, value);
			}

			return null;
		}
	}

	@Override
	public EObject doSwitch(EObject eObject) {
		if (eObject == null) {
			return null;
		}
		return doSwitch(eObject.eClass(), eObject);
	}

	/**
	 * Hook called at the beginning of a statement/variable.
	 */
	protected void hookBefore(EObject eObject) {
		// only used by ActionTransformer
	}

	@Override
	public final Expression transformExpr(CxExpression expression) {
		return (Expression) doSwitch(expression);
	}

	/**
	 * Translates a call represented by the given expression to an IR InstCall.
	 *
	 * @param expression
	 *            an expression variable referencing a function
	 * @return an ExprVar
	 */
	private Expression translateCall(ExpressionVariable expression) {
		Variable variable = expression.getSource().getVariable();

		// retrieve IR procedure
		Procedure proc = builder.getProcedure(variable);

		// transform parameters
		List<Expression> parameters = builder.transformExpressions(expression.getParameters());

		// add call
		int lineNumber = getStartLine(expression);
		Var target = builder.createLocal(lineNumber, proc.getReturnType(), variable.getName());
		InstCall call = ir.createInstCall(lineNumber, target, proc, parameters);
		builder.add(call);

		// return expr
		return ir.createExprVar(target);
	}

}
