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
 
package com.synflow.models.ir.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.synflow.models.ir.Block;
import com.synflow.models.ir.BlockBasic;
import com.synflow.models.ir.BlockIf;
import com.synflow.models.ir.BlockWhile;
import com.synflow.models.ir.Def;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstAssign;
import com.synflow.models.ir.InstCall;
import com.synflow.models.ir.InstLoad;
import com.synflow.models.ir.InstPhi;
import com.synflow.models.ir.InstReturn;
import com.synflow.models.ir.InstStore;
import com.synflow.models.ir.Instruction;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Use;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.util.IrUtil;
import com.synflow.models.util.EcoreHelper;
import com.synflow.models.util.Void;

/**
 * This class converts the given actor to SSA form.
 *
 * @author Matthieu Wipliez
 *
 */
public class SSATransformation extends AbstractIrVisitor {

	/**
	 * ith branch (or 0 if we are not in a branch)
	 */
	private int branch;

	/**
	 * maps a variable name to a local variable (used when creating new definitions)
	 */
	private Map<String, Var> definitions;

	/**
	 * join block (if any)
	 */
	private BlockBasic join;

	/**
	 * contains the current while block being treated (if any)
	 */
	private BlockWhile loop;

	/**
	 * maps a variable name to a local variable (used when replacing uses)
	 */
	private Map<String, Var> uses;

	/**
	 * Creates a new SSA transformation.
	 */
	public SSATransformation() {
		definitions = new HashMap<String, Var>();
		uses = new HashMap<String, Var>();
	}

	/**
	 * Commits the phi assignments in the given join block.
	 *
	 * @param innerJoin
	 *            a basic block that contains phi assignments
	 */
	private void commitPhi(BlockBasic innerJoin) {
		for (Instruction instruction : innerJoin.getInstructions()) {
			InstPhi phi = (InstPhi) instruction;
			Var oldVar = phi.getOldVariable();
			Var newVar = phi.getTarget().getVariable();

			// updates the current value of "var"
			uses.put(oldVar.getName(), newVar);

			if (join != null) {
				insertPhi(oldVar, newVar);
			}

			// remove reference to old variable in phi
			phi.setOldVariable(null);
		}
	}

	/**
	 * Inserts a phi in the (current) join block.
	 *
	 * @param oldVar
	 *            old variable
	 * @param newVar
	 *            new variable
	 */
	private void insertPhi(Var oldVar, Var newVar) {
		String name = oldVar.getName();
		InstPhi phi = null;
		for (Instruction instruction : join.getInstructions()) {
			if (instruction.isInstPhi()) {
				InstPhi tempPhi = (InstPhi) instruction;
				if (tempPhi.getTarget().getVariable().getName().equals(name)) {
					phi = tempPhi;
					break;
				}
			}
		}

		if (phi == null) {
			Var target = newDefinition(oldVar);
			List<Expression> values = new ArrayList<Expression>(2);
			values.add(IrFactory.eINSTANCE.createExprVar(oldVar));
			values.add(IrFactory.eINSTANCE.createExprVar(oldVar));

			phi = IrFactory.eINSTANCE.createInstPhi(target, values);
			phi.setOldVariable(oldVar);
			join.add(phi);

			if (loop != null) {
				List<Use> uses = new ArrayList<Use>(oldVar.getUses());
				for (Use use : uses) {
					Block block = EcoreHelper.getContainerOfType(use, Block.class);

					// only changes uses that are in the loop
					if (block != join && EcoreUtil.isAncestor(loop, block)) {
						use.setVariable(target);
					}
				}
			}
		}

		// replace use
		IrUtil.removeUses(phi.getValues().get(branch - 1));
		phi.getValues().set(branch - 1, IrFactory.eINSTANCE.createExprVar(newVar));
	}

	/**
	 * Creates a new definition based on the given old variable.
	 *
	 * @param oldVar
	 *            a variable
	 * @return a new definition based on the given old variable
	 */
	private Var newDefinition(Var oldVar) {
		String name = oldVar.getName();

		// get index
		int index;
		if (definitions.containsKey(name)) {
			index = definitions.get(name).getIndex() + 1;
		} else {
			index = 1;
		}

		// create new variable
		Var newVar = IrFactory.eINSTANCE.createVar(oldVar.getLineNumber(), oldVar.getType(), name,
				oldVar.isAssignable(), index);
		procedure.getLocals().add(newVar);
		definitions.put(name, newVar);

		return newVar;
	}

	/**
	 * Replaces the definition created.
	 *
	 * @param def
	 *            a definition
	 */
	private void replaceDef(Def def) {
		if (def == null) { // def may be null in InstCall
			return;
		}

		Var target = def.getVariable();
		if (target.getType().isArray()) {
			// only replace definitions of scalars
			return;
		}

		// oldVar is the value of the variable before the assignment
		String name = target.getName();
		Var oldVar = uses.get(name);
		if (oldVar == null) {
			// may be null if the variable is used without having been assigned first
			// happens with function parameters for instance
			oldVar = target;
		}

		Var newTarget = newDefinition(target);
		uses.put(name, newTarget);

		if (branch != 0) {
			insertPhi(oldVar, newTarget);
		}

		def.setVariable(newTarget);
	}

	/**
	 * Replaces uses in the given object.
	 *
	 * @param object
	 *            an object
	 */
	protected void replaceUses(EObject eObject) {
		for (Use use : EcoreHelper.getObjects(eObject, Use.class)) {
			Var oldVar = use.getVariable();
			if (oldVar.isLocal()) {
				Var newVar = uses.get(oldVar.getName());
				if (newVar != null) {
					// newVar may be null if oldVar is a function parameter
					// for instance
					use.setVariable(newVar);
				}
			}
		}
	}

	/**
	 * Replaces uses of oldVar by newVar in the given objects.
	 *
	 * @param objects
	 *            a list of objects
	 */
	private void replaceUses(List<? extends EObject> eObjects) {
		for (EObject eObject : eObjects) {
			replaceUses(eObject);
		}
	}

	/**
	 * Restore variables that were concerned by phi assignments.
	 */
	private void restoreVariables() {
		for (Instruction instruction : join.getInstructions()) {
			InstPhi phi = (InstPhi) instruction;
			Var oldVar = phi.getOldVariable();
			uses.put(oldVar.getName(), oldVar);
		}
	}

	@Override
	public Void caseInstAssign(InstAssign assign) {
		replaceUses(assign.getValue());
		replaceDef(assign.getTarget());
		return null;
	}

	@Override
	public Void caseInstCall(InstCall call) {
		replaceUses(call.getArguments());
		replaceDef(call.getTarget());
		return null;
	}

	@Override
	public Void caseBlockIf(BlockIf blockIf) {
		int outerBranch = branch;
		BlockBasic outerJoin = join;
		BlockWhile outerLoop = loop;

		replaceUses(blockIf.getCondition());

		join = blockIf.getJoinBlock();
		loop = null;

		branch = 1;
		visit(blockIf.getThenBlocks());

		// restore variables used in phi assignments
		restoreVariables();

		branch = 2;
		visit(blockIf.getElseBlocks());

		// commit phi
		BlockBasic innerJoin = join;
		branch = outerBranch;
		join = outerJoin;
		loop = outerLoop;
		commitPhi(innerJoin);
		return null;
	}

	@Override
	public Void caseInstLoad(InstLoad load) {
		replaceUses(load.getIndexes());
		replaceDef(load.getTarget());
		return null;
	}

	@Override
	public Void caseProcedure(Procedure procedure) {
		definitions.clear();
		uses.clear();
		branch = 0;
		return super.caseProcedure(procedure);
	}

	@Override
	public Void caseInstReturn(InstReturn returnInstr) {
		Expression value = returnInstr.getValue();
		if (value != null) {
			replaceUses(value);
		}
		return null;
	}

	@Override
	public Void caseInstStore(InstStore store) {
		replaceUses(store.getIndexes());
		replaceUses(store.getValue());
		return null;
	}

	@Override
	public Void caseBlockWhile(BlockWhile blockWhile) {
		int outerBranch = branch;
		BlockBasic outerJoin = join;
		BlockWhile outerLoop = loop;

		replaceUses(blockWhile.getCondition());

		branch = 2;
		join = blockWhile.getJoinBlock();
		loop = blockWhile;

		visit(blockWhile.getBlocks());

		// commit phi
		BlockBasic innerJoin = join;
		branch = outerBranch;
		join = outerJoin;
		loop = outerLoop;
		commitPhi(innerJoin);
		return null;
	}

}
