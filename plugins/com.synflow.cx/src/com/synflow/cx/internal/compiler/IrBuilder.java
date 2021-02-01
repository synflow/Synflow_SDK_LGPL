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
package com.synflow.cx.internal.compiler;

import static com.synflow.cx.internal.TransformerUtil.getStartLine;
import static com.synflow.cx.internal.TransformerUtil.isFalse;
import static com.synflow.cx.internal.TransformerUtil.isOne;
import static com.synflow.cx.internal.TransformerUtil.isTrue;
import static com.synflow.cx.internal.TransformerUtil.isZero;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.cx.internal.services.Typer;
import com.synflow.models.dpn.Entity;
import com.synflow.models.ir.Block;
import com.synflow.models.ir.BlockIf;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstLoad;
import com.synflow.models.ir.InstStore;
import com.synflow.models.ir.Instruction;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeArray;
import com.synflow.models.ir.TypeInt;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.util.IrUtil;
import com.synflow.models.ir.util.TypeUtil;

/**
 * This class defines an IR builder. The build state is composed of the current entity, procedure,
 * blocks, etc.
 *
 * @author Matthieu Wipliez
 *
 */
public class IrBuilder {

	protected static final IrFactory ir = IrFactory.eINSTANCE;

	private List<Block> blocks;

	private final Deque<List<Block>> deque;

	protected final Entity entity;

	private final Set<String> existingSet;

	protected final IInstantiator instantiator;

	private Map<Variable, Var> localMap;

	/**
	 * current procedure
	 */
	private Procedure procedure;

	protected Transformer transformer;

	/**
	 * Creates a new function transformer with the given entity.
	 *
	 * @param entity
	 *            target IR entity
	 */
	public IrBuilder(IInstantiator instantiator, Entity entity) {
		this.instantiator = instantiator;
		this.entity = entity;

		deque = new ArrayDeque<>();
		localMap = new HashMap<>();

		existingSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		existingSet.add(entity.getSimpleName());
		Iterables.addAll(existingSet, Iterables.transform(
				Iterables.concat(entity.getInputs(), entity.getOutputs(), entity.getVariables()),
				new Function<Var, String>() {
					@Override
					public String apply(Var var) {
						return var.getName();
					}
				}));
	}

	final void add(Block block) {
		blocks.add(block);
	}

	public final void add(Instruction instruction) {
		IrUtil.getLast(blocks).add(instruction);
	}

	final void addAll(Collection<Block> blocks) {
		this.blocks.addAll(blocks);
	}

	/**
	 * Creates a new temporary local variable based on the given hint. The local variable is
	 * guaranteed to have a unique name.
	 *
	 * @param lineNumber
	 *            line number
	 * @param type
	 *            type
	 * @param hint
	 *            suggestion for a name
	 * @return a new local variable
	 */
	public Var createLocal(int lineNumber, Type type, String hint) {
		Var var = ir.createVar(lineNumber, type, getUniqueName(hint), true, 0);
		procedure.getLocals().add(var);
		return var;
	}

	/**
	 * Returns the IR variable that corresponds to the given Cx variable.
	 *
	 * @param variable
	 *            a Cx variable
	 * @return the IR variable that corresponds to the given variable
	 */
	final Var getMapping(Variable variable) {
		Var var = localMap.get(variable);
		if (var == null) {
			var = instantiator.getMapping(entity, variable);
		}
		return var;
	}

	public final Procedure getProcedure() {
		return procedure;
	}

	/**
	 * Returns the IR procedure that corresponds to the given Cx function.
	 *
	 * @param function
	 *            a Cx function
	 * @return the IR procedure that corresponds to the given function
	 */
	final Procedure getProcedure(Variable function) {
		return instantiator.getMapping(entity, function);
	}

	private String getUniqueName(String hint) {
		String name = hint;
		int i = 0;
		while (existingSet.contains(name) || procedure.getLocal(name) != null) {
			name = hint + "_" + i;
			i++;
		}

		return name;
	}

	/**
	 * Creates a new target scalar variable, and loads the given source into it. Do not perform bit
	 * selection though.
	 *
	 * @param lineNumber
	 *            line number
	 * @param source
	 *            source variable
	 * @param indexes
	 *            indexes
	 * @return the new target scalar variable
	 */
	final Var loadVariable(int lineNumber, Var source, List<CxExpression> indexes) {
		Type type = source.getType();
		int dimensions = Typer.getNumDimensions(type);

		// creates local target variable (will be cleaned up later if necessary)
		Type varType;
		if (type.isArray()) {
			varType = ((TypeArray) type).getElementType();
		} else {
			varType = type;
		}
		Var target = createLocal(lineNumber, varType, source.getName());

		// loads (but do not perform bit selection)
		List<CxExpression> subIndexes = indexes.subList(0, dimensions);
		List<Expression> expressions = transformIndexes(type, subIndexes);

		InstLoad load = ir.createInstLoad(lineNumber, target, source, expressions);
		add(load);

		return target;
	}

	/**
	 * Restores the value of blocks that was previously saved.
	 */
	public final void restoreBlocks() {
		blocks = deque.pollFirst();
	}

	/**
	 * Saves the current value of 'blocks'.
	 */
	public final void saveBlocks() {
		if (blocks != null) {
			deque.addFirst(blocks);
		}
	}

	final void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

	public final void setProcedure(Procedure procedure) {
		this.procedure = procedure;
		if (procedure == null) {
			this.blocks = null;
		} else {
			this.blocks = procedure.getBlocks();
		}
	}

	/**
	 * Sets the current procedure of this builder from the given function.
	 *
	 * @param function
	 */
	final void setProcedure(Variable function) {
		procedure = instantiator.getMapping(entity, function);
		blocks = procedure.getBlocks();
	}

	public void setTransformer(Transformer transformer) {
		this.transformer = transformer;
	}

	/**
	 * Depending on the IR expression that value evaluates to, this method:
	 * <ul>
	 * <li>calls {@link #storeBitSet(int, Var, List, Var, int)} if value equals "true" or is an
	 * integer != 0.</li>
	 * <li>calls {@link #storeBitClear(int, Var, List, Var, int)} if value equals "false" or "0".
	 * </li>
	 * <li>otherwise it creates an if block with storeBitSet in the then block, and storeBitClear in
	 * the else block.</li>
	 * </ul>
	 *
	 * @param lineNumber
	 *            line number
	 * @param target
	 *            target variable
	 * @param indexes
	 *            indexes
	 * @param local
	 *            local variable
	 * @param index
	 *            index of the bit to set
	 * @param value
	 *            value of the bit
	 */
	private void storeBit(int lineNumber, Var target, List<Expression> indexes, Var local,
			int index, Expression expr) {
		// get value
		if (isTrue(expr)) {
			storeBitSet(lineNumber, target, indexes, local, index);
		} else if (isFalse(expr)) {
			storeBitClear(lineNumber, target, indexes, local, index);
		} else {
			saveBlocks();

			// create and add block
			BlockIf block = ir.createBlockIf();
			block.setJoinBlock(ir.createBlockBasic());
			block.setLineNumber(lineNumber);

			block.setCondition(expr);
			add(block);

			// "then" block: set bit
			setBlocks(block.getThenBlocks());
			storeBitSet(lineNumber, target, indexes, local, index);

			// "else" block: clear bit
			setBlocks(block.getElseBlocks());
			storeBitClear(lineNumber, target, indexes, local, index);

			restoreBlocks();
		}
	}

	/**
	 * Creates Store(target, indexes, local & 0b110111) (the index of the '0' is given by the index
	 * variable).
	 *
	 * @param lineNumber
	 *            line number
	 * @param target
	 *            target variable
	 * @param indexes
	 *            indexes
	 * @param local
	 *            local variable (loaded from the target)
	 * @param index
	 *            index of the bit to set
	 */
	private void storeBitClear(int lineNumber, Var target, List<Expression> indexes, Var local,
			int index) {
		Type type = local.getType();
		int size = ((TypeInt) type).getSize();

		BigInteger mask = ONE.shiftLeft(size).subtract(ONE).clearBit(index);
		Expression value = ir.createExprBinary(ir.createExprVar(local), OpBinary.BITAND,
				ir.createExprInt(mask));

		InstStore store = ir.createInstStore(lineNumber, target, indexes, value);
		add(store);
	}

	/**
	 * Creates Store(target, indexes, local | 0b001000) (the index of the '1' is given by the index
	 * variable).
	 *
	 * @param lineNumber
	 *            line number
	 * @param target
	 *            target variable
	 * @param indexes
	 *            indexes
	 * @param local
	 *            local variable (loaded from the target)
	 * @param index
	 *            index of the bit to set
	 */
	private void storeBitSet(int lineNumber, Var target, List<Expression> indexes, Var local,
			int index) {
		BigInteger mask = ZERO.setBit(index);
		Expression value = ir.createExprBinary(ir.createExprVar(local), OpBinary.BITOR,
				ir.createExprInt(mask));

		InstStore store = ir.createInstStore(lineNumber, target, indexes, value);
		add(store);
	}

	/**
	 * Creates a store to the given target variable, with the given indexes, and the given value.
	 *
	 * @param lineNumber
	 *            line number
	 * @param target
	 *            target IR variable
	 * @param indexes
	 *            list of IR expressions (may be <code>null</code>)
	 * @param value
	 *            Cx value
	 */
	public final void storeExpr(int lineNumber, Var target, List<CxExpression> indexes,
			CxExpression value) {
		Type type = target.getType();
		boolean hasIndexes = indexes != null && !indexes.isEmpty();
		int dimensions = Typer.getNumDimensions(type);
		boolean storeBit = hasIndexes && dimensions < indexes.size();

		// transform expression (with implicit cast to boolean)
		if (storeBit) {
			type = ir.createTypeBool();
		} else if (hasIndexes) {
			type = ((TypeArray) type).getElementType();
		}
		Expression expr = transformExpr(value, type);

		// bit store
		if (storeBit) {
			// loads variable (but do not perform bit selection)
			Var local = loadVariable(lineNumber, target, indexes);

			// select indexes (but not bit selection)
			List<CxExpression> subIndexes = indexes.subList(0, dimensions);
			List<Expression> expressions = transformIndexes(target.getType(), subIndexes);

			// select bit index
			CxExpression exprIndex = indexes.get(indexes.size() - 1);
			int index = instantiator.evaluateInt(entity, exprIndex);

			// store the bit
			storeBit(lineNumber, target, expressions, local, index, expr);
			return;
		}

		// transform indexes
		List<Expression> expressions = hasIndexes ? transformIndexes(target.getType(), indexes)
				: Collections.<Expression> emptyList();

		// "normal" store
		InstStore store = ir.createInstStore(lineNumber, target, expressions, expr);
		add(store);
	}

	/**
	 * Transforms the given expression, and cast to boolean if <code>target</code> is a boolean
	 * type.
	 *
	 * @param expression
	 *            an AST expression
	 * @param target
	 *            target type
	 * @return an IR expression
	 */
	protected Expression transformExpr(CxExpression expression, Type target) {
		Expression expr = transformer.transformExpr(expression);
		if (target.isBool()) {
			Type type = TypeUtil.getType(expr);
			if (isOne(expr)) {
				return ir.createExprBool(true);
			} else if (isZero(expr)) {
				return ir.createExprBool(false);
			} else if (!type.isBool()) {
				return ir.createExprBinary(expr, OpBinary.NE, ir.createExprInt(0));
			}
		}

		return expr;
	}

	/**
	 * Transforms the given AST expressions to a list of IR expressions. In the process nodes may be
	 * created and added to the current {@link #procedure}, since many expressions are expressed
	 * with IR statements.
	 *
	 * @param expressions
	 *            a list of AST expressions
	 * @return a list of IR expressions
	 */
	final List<Expression> transformExpressions(List<CxExpression> expressions) {
		int length = expressions.size();
		List<Expression> irExpressions = new ArrayList<Expression>(length);
		for (CxExpression expression : expressions) {
			irExpressions.add(transformer.transformExpr(expression));
		}

		return irExpressions;
	}

	/**
	 * Transforms the given AST expressions to a list of IR expressions and convert them to
	 * unsigned.
	 *
	 * @param expressions
	 *            a list of AST expressions
	 * @return a list of IR expressions
	 */
	private List<Expression> transformIndexes(Type type, List<CxExpression> expressions) {
		List<Expression> irExpressions = transformExpressions(expressions);
		List<Expression> casted = new ArrayList<>(irExpressions.size());

		// cast indexes (only if type is an array => there are indexes to cast)
		if (type.isArray()) {
			Iterator<Integer> itD = ((TypeArray) type).getDimensions().iterator();

			// cast indexes
			for (Expression index : irExpressions) {
				int size = itD.next();
				int amount = TypeUtil.getSize(size - 1);
				casted.add(ir.castToUnsigned(amount, index));
			}
		}
		return casted;
	}

	/**
	 * Transforms the given Cx local variable/parameter to a new IR Var.
	 *
	 * @param variable
	 *            a variable
	 * @return an IR Var
	 */
	public Var transformLocal(Variable variable) {
		int lineNumber = getStartLine(variable);
		Type type = instantiator.computeType(entity, variable);
		String name = getUniqueName(variable.getName());
		boolean assignable = !CxUtil.isConstant(variable);

		// create local variable with the given name
		Var var = ir.createVar(lineNumber, type, name, assignable);
		localMap.put(variable, var);
		return var;
	}

}
