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
package com.synflow.cx.internal;

import static com.synflow.cx.cx.CxFactory.eINSTANCE;
import static org.eclipse.emf.ecore.util.EcoreUtil.resolveAll;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.CxType;
import com.synflow.cx.cx.ExpressionBinary;
import com.synflow.cx.cx.ExpressionBoolean;
import com.synflow.cx.cx.ExpressionCast;
import com.synflow.cx.cx.ExpressionInteger;
import com.synflow.cx.cx.ExpressionUnary;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.StatementAssign;
import com.synflow.cx.cx.StatementGoto;
import com.synflow.cx.cx.TypeDecl;
import com.synflow.cx.cx.VarRef;
import com.synflow.cx.cx.Variable;
import com.synflow.models.dpn.State;

/**
 * This class defines methods to manipulate the AST.
 *
 * @author Matthieu Wipliez
 *
 */
public class AstUtil {

	private static void addAdapters(Copier copier) {
		for (Entry<EObject, EObject> entry : copier.entrySet()) {
			if (entry.getKey() instanceof VarRef) {
				final EObject original = entry.getKey();
				final EObject copy = entry.getValue();
				copy.eAdapters().add(new CopyOf(original));
			}
		}
	}

	/**
	 * Creates the AST of <code>expr1 && expr2</code>. Expressions are copied. If
	 * <code>expr1 == true</code>, returns a copy of <code>expr2</code>, and vice-versa.
	 *
	 * @param expr1
	 *            first operand
	 * @param expr2
	 *            second operand
	 * @return a binary expression
	 */
	public static CxExpression and(CxExpression expr1, CxExpression expr2) {
		if (isTrue(expr1)) {
			return copyIfNeeded(expr2);
		} else if (isTrue(expr2)) {
			return copyIfNeeded(expr1);
		} else {
			ExpressionBinary exprBin = eINSTANCE.createExpressionBinary();
			exprBin.setLeft(copyIfNeeded(expr1));
			exprBin.setOperator("&&");
			exprBin.setRight(copyIfNeeded(expr2));
			return exprBin;
		}
	}

	/**
	 * Creates an assign statement.
	 *
	 * @param variable
	 * @param value
	 * @return
	 */
	public static StatementAssign assign(Variable variable, CxExpression value) {
		StatementAssign assign = eINSTANCE.createStatementAssign();
		assign.setTarget(expr(variable));
		assign.setOp("=");
		assign.setValue(copyIfNeeded(value));
		return assign;
	}

	public static <T extends EObject> T copy(T eObject) {
		Copier copier = new Copier();
		EObject result = copier.copy(eObject);
		copier.copyReferences();

		addAdapters(copier);

		@SuppressWarnings("unchecked")
		T t = (T) result;
		return t;
	}

	private static <T extends EObject> Collection<T> copyAll(Collection<? extends T> eObjects) {
		Copier copier = new Copier();
		Collection<T> result = new ArrayList<>(eObjects.size());
		for (EObject eObject : eObjects) {
			@SuppressWarnings("unchecked")
			T copy = (T) copier.copy(eObject);
			result.add(copy);
		}

		copier.copyReferences();
		addAdapters(copier);

		return result;
	}

	/**
	 * If expression is already contained in an expression, returns a copy of it.
	 *
	 * @param expression
	 *            a Cx expression
	 * @return <code>expression</code> itself, or a copy of it
	 */
	private static CxExpression copyIfNeeded(CxExpression expression) {
		if (expression.eContainer() == null) {
			return expression;
		}
		return copy(expression);
	}

	private static CxExpression createArithmetic(Variable variable, List<CxExpression> indexes,
			String op, CxExpression value) {
		ExpressionBinary exprBin = eINSTANCE.createExpressionBinary();

		ExpressionVariable left = expr(variable);
		if (!indexes.isEmpty()) {
			left.getIndexes().addAll(copyAll(indexes));
		}

		exprBin.setLeft(left);
		exprBin.setOperator(op);
		exprBin.setRight(copyIfNeeded(value));

		// add cast
		ExpressionCast cast = eINSTANCE.createExpressionCast();
		CxType type = copy(CxUtil.getType(variable));
		cast.setType(type);
		cast.setExpression(exprBin);
		return cast;
	}

	/**
	 * Decrements the given variable.
	 *
	 * @param variable
	 *            a variable
	 * @return an assign statement
	 */
	public static StatementAssign decrement(Variable variable) {
		StatementAssign assign = eINSTANCE.createStatementAssign();
		assign.setTarget(expr(variable));
		assign.setOp("--");
		return assign;
	}

	/**
	 * Returns a new Cx boolean expression.
	 *
	 * @param value
	 *            a boolean value
	 * @return a boolean expression
	 */
	public static ExpressionBoolean expr(boolean value) {
		ExpressionBoolean exprBool = eINSTANCE.createExpressionBoolean();
		exprBool.setValue(value);
		return exprBool;
	}

	/**
	 * Creates a new integer expression with the given value.
	 *
	 * @param value
	 *            an integer value
	 * @return an integer expression
	 */
	public static ExpressionInteger expr(int value) {
		ExpressionInteger expr = eINSTANCE.createExpressionInteger();
		expr.setValue(BigInteger.valueOf(value));
		return expr;
	}

	/**
	 * Creates a new expression variable that references the given variable.
	 *
	 * @param variable
	 *            a variable
	 * @return an expression variable
	 */
	public static ExpressionVariable expr(Variable variable) {
		ExpressionVariable expr = eINSTANCE.createExpressionVariable();
		VarRef ref = eINSTANCE.createVarRef();
		ref.getObjects().add(variable);
		expr.setSource(ref);
		return expr;
	}

	/**
	 * Returns the expression resulting from an assign created from the assignment operator
	 * (post-increment/decrement or compound operator).
	 *
	 * @param assign
	 *            an assign statement
	 * @return an expression
	 */
	public static CxExpression getAssignValue(StatementAssign assign) {
		String op = assign.getOp();
		Variable variable = assign.getTarget().getSource().getVariable();
		List<CxExpression> indexes = assign.getTarget().getIndexes();
		CxExpression value = assign.getValue();
		if (value == null) {
			// handle post-decrement/increment
			if ("++".equals(op)) {
				value = createArithmetic(variable, indexes, "+", expr(1));
			} else if ("--".equals(op)) {
				value = createArithmetic(variable, indexes, "-", expr(1));
			}
		} else {
			// compound op
			if (op.length() > 1) {
				// resolve value now, because proxies in "value" can't be
				// resolved by Xtext since no node model is attached to the AST
				// nodes created
				resolveAll(value);

				String binOp = op.substring(0, op.length() - 1);
				value = createArithmetic(variable, indexes, binOp, value);
			}
		}

		return value;
	}

	public static StatementGoto gotoState(State target) {
		StatementGoto stmtGoto = eINSTANCE.createStatementGoto();
		stmtGoto.setTarget(target);
		return stmtGoto;
	}

	private static boolean isTrue(CxExpression expression) {
		if (expression instanceof ExpressionBoolean) {
			return ((ExpressionBoolean) expression).isValue();
		}
		return false;
	}

	/**
	 * Returns <code>!expression</code>.
	 *
	 * @param expression
	 * @return
	 */
	public static CxExpression not(CxExpression expression) {
		if (expression instanceof ExpressionUnary) {
			ExpressionUnary unary = (ExpressionUnary) expression;
			if ("!".equals(unary.getOperator())) {
				return copy(unary.getExpression());
			}
		}

		ExpressionUnary not = eINSTANCE.createExpressionUnary();
		not.setExpression(copyIfNeeded(expression));
		not.setOperator("!");
		return not;
	}

	/**
	 * Returns <code>expression != 0</code>.
	 *
	 * @param expression
	 * @return
	 */
	public static ExpressionBinary notZero(CxExpression expr) {
		ExpressionBinary cmp = eINSTANCE.createExpressionBinary();
		cmp.setLeft(copyIfNeeded(expr));
		cmp.setOperator("!=");
		cmp.setRight(expr(0));
		return cmp;
	}

	/**
	 * Returns a Cx type int&lt;size&gt; or uint&lt;size&gt; depending on the <code>signed</code>
	 * flag.
	 *
	 * @param size
	 *            size of the type
	 * @param signed
	 *            signed or not
	 * @return a Cx type
	 */
	public static CxType type(int size, boolean signed) {
		TypeDecl type = eINSTANCE.createTypeDecl();
		type.setSpec((signed ? "i" : "u") + size);
		return type;
	}

}
