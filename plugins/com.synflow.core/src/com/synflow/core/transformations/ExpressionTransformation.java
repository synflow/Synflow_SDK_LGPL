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
 
package com.synflow.core.transformations;

import static com.synflow.models.ir.IrFactory.eINSTANCE;
import static com.synflow.models.util.SwitchUtil.DONE;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.synflow.models.dpn.Entity;
import com.synflow.models.ir.BlockIf;
import com.synflow.models.ir.BlockWhile;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstAssign;
import com.synflow.models.ir.InstCall;
import com.synflow.models.ir.InstLoad;
import com.synflow.models.ir.InstReturn;
import com.synflow.models.ir.InstStore;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeArray;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.transform.AbstractExpressionTransformer;
import com.synflow.models.ir.transform.AbstractIrVisitor;
import com.synflow.models.ir.util.TypeUtil;
import com.synflow.models.util.Void;

/**
 * This class defines a module transformation that visits all expressions in an actor/unit.
 *
 * @author Matthieu Wipliez
 *
 */
public class ExpressionTransformation extends ProcedureTransformation {

	/**
	 * This class defines a visitor that visits all expressions in blocks, instructions, and initial
	 * values of variables.
	 *
	 * @author Matthieu Wipliez
	 *
	 */
	private static class ExpressionVisitor extends AbstractIrVisitor {

		private AbstractExpressionTransformer transformer;

		public ExpressionVisitor(AbstractExpressionTransformer transformer) {
			this.transformer = transformer;
		}

		@Override
		public Void caseBlockIf(BlockIf block) {
			block.setCondition(visitExpr(eINSTANCE.createTypeBool(), block.getCondition()));

			visit(block.getThenBlocks());
			visit(block.getElseBlocks());
			return doSwitch(block.getJoinBlock());
		}

		@Override
		public Void caseBlockWhile(BlockWhile block) {
			block.setCondition(visitExpr(eINSTANCE.createTypeBool(), block.getCondition()));

			visit(block.getBlocks());
			doSwitch(block.getJoinBlock());
			return DONE;
		}

		@Override
		public Void caseInstAssign(InstAssign assign) {
			Type type = assign.getTarget().getVariable().getType();
			assign.setValue(visitExpr(type, assign.getValue()));
			return DONE;
		}

		@Override
		public Void caseInstCall(InstCall call) {
			Iterable<? extends Type> types;
			if (call.isAssert()) {
				types = ImmutableSet.of(eINSTANCE.createTypeBool());
			} else if (call.isPrint()) {
				// create a list rather than an iterable to avoid concurrent modifications later
				List<Type> list = new ArrayList<>();
				for (Expression expr : call.getArguments()) {
					list.add(TypeUtil.getType(expr));
				}
				types = list;
			} else {
				List<Var> parameters = call.getProcedure().getParameters();
				types = Iterables.transform(parameters, new Function<Var, Type>() {
					@Override
					public Type apply(Var variable) {
						return variable.getType();
					}
				});
			}
			transformer.visitExprList(types, call.getArguments());
			return DONE;
		}

		@Override
		public Void caseInstLoad(InstLoad load) {
			if (!load.getIndexes().isEmpty()) {
				Type type = load.getSource().getVariable().getType();
				visitIndexes(type, load.getIndexes());
			}
			return DONE;
		}

		@Override
		public Void caseInstReturn(InstReturn instReturn) {
			final Expression value = instReturn.getValue();
			if (value != null) {
				instReturn.setValue(visitExpr(procedure.getReturnType(), value));
			}
			return DONE;
		}

		@Override
		public Void caseInstStore(InstStore store) {
			Type type = store.getTarget().getVariable().getType();
			if (!store.getIndexes().isEmpty()) {
				visitIndexes(type, store.getIndexes());
			}

			store.setValue(visitExpr(type, store.getValue()));
			return DONE;
		}

		@Override
		public Void caseProcedure(Procedure procedure) {
			this.procedure = procedure;
			transformer.setProcedure(procedure);
			return visit(procedure.getBlocks());
		}

		@Override
		public Void caseVar(Var variable) {
			Expression value = variable.getInitialValue();
			if (value != null) {
				variable.setInitialValue(visitExpr(variable.getType(), value));
			}
			return DONE;
		}

		private Expression visitExpr(Type target, Expression expression) {
			Type type;
			if (target.isArray()) {
				type = ((TypeArray) target).getElementType();
			} else {
				type = target;
			}
			return transformer.visitExpr(type, expression);
		}

		private void visitIndexes(Type type, EList<Expression> indexes) {
			if (type.isArray()) {
				EList<Integer> dimensions = ((TypeArray) type).getDimensions();
				Iterable<Type> types = Iterables.transform(dimensions,
						new Function<Integer, Type>() {
							@Override
							public Type apply(Integer size) {
								return eINSTANCE.createTypeInt(TypeUtil.getSize(size - 1), false);
							}
						});
				transformer.visitExprList(types, indexes);
			}
		}

	}

	public ExpressionTransformation(AbstractExpressionTransformer transformer) {
		super(new ExpressionVisitor(transformer));
	}

	@Override
	public Void caseEntity(Entity entity) {
		for (Var var : entity.getVariables()) {
			irVisitor.doSwitch(var);
		}

		for (Procedure procedure : entity.getProcedures()) {
			visitProcedure(procedure);
		}

		return DONE;
	}

	protected AbstractExpressionTransformer getTransformer() {
		return ((ExpressionVisitor) irVisitor).transformer;
	}

}
