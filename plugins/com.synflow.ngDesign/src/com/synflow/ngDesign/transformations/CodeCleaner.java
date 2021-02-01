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
package com.synflow.ngDesign.transformations;

import static com.synflow.models.util.SwitchUtil.DONE;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.synflow.models.ir.BlockBasic;
import com.synflow.models.ir.BlockIf;
import com.synflow.models.ir.BlockWhile;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstAssign;
import com.synflow.models.ir.InstLoad;
import com.synflow.models.ir.InstPhi;
import com.synflow.models.ir.InstStore;
import com.synflow.models.ir.Instruction;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Use;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.transform.AbstractIrVisitor;
import com.synflow.models.ir.util.IrUtil;
import com.synflow.models.util.Void;

/**
 * This class defines an IR transformation that cleans up code.
 *
 * @author Matthieu Wipliez
 *
 */
public class CodeCleaner extends AbstractIrVisitor {

	private boolean changed;

	@Override
	public Void caseBlockBasic(BlockBasic block) {
		visit(block.getInstructions());

		if (block.getInstructions().isEmpty()) {
			delete(block);
		}
		return DONE;
	}

	@Override
	public Void caseBlockIf(BlockIf blockIf) {
		doSwitch(blockIf.getJoinBlock());
		visit(blockIf.getThenBlocks());
		visit(blockIf.getElseBlocks());

		if (blockIf.getThenBlocks().isEmpty() && blockIf.getElseBlocks().isEmpty()
				&& blockIf.getJoinBlock() == null) {
			delete(blockIf);
		}

		return DONE;
	}

	@Override
	public Void caseBlockWhile(BlockWhile blockWhile) {
		BlockBasic join = blockWhile.getJoinBlock();
		visit(blockWhile.getBlocks());
		doSwitch(join);

		if (blockWhile.getBlocks().isEmpty()) {
			// if the join's phi target are all used only within this while, delete it
			for (Instruction inst : join.getInstructions()) {
				if (inst.isInstPhi()) {
					InstPhi phi = (InstPhi) inst;
					for (Use use : phi.getTarget().getVariable().getUses()) {
						if (!EcoreUtil.isAncestor(blockWhile, use)) {
							return DONE;
						}
					}
				}
			}

			delete(blockWhile);
		}

		return DONE;
	}

	@Override
	public Void caseInstAssign(InstAssign assign) {
		Var target = assign.getTarget().getVariable();
		Expression value = assign.getValue();
		List<Use> uses = target.getUses();
		if (uses.size() >= 2 && value.isExprBinary()) {
			// do not expand arithmetic expressions used in at least 2 places
			return DONE;
		}

		if (!uses.isEmpty()) {
			for (Use use : uses) {
				Expression expr = IrUtil.copy(value);
				EcoreUtil.replace(use.eContainer(), expr);
				tryFoldExpression(expr);
			}
			target.getUses().clear();
		}

		// nobody uses the target variable after this instruction/block
		// we can safely remove
		clean(target, assign);

		return DONE;
	}

	@Override
	public Void caseInstLoad(InstLoad load) {
		Var target = load.getTarget().getVariable();
		if (!target.getType().isArray() && load.getIndexes().isEmpty()) {
			// only replace if load of scalar
			Var source = load.getSource().getVariable();
			for (Use use : target.getUses()) {
				Expression expr = IrFactory.eINSTANCE.createExprVar(source);
				EcoreUtil.replace(use.eContainer(), expr);
			}
			target.getUses().clear();
		}

		if (target.getUses().isEmpty()) {
			// nobody uses the target variable after this instruction/block
			// we can safely remove
			clean(target, load);
		}

		return DONE;
	}

	@Override
	public Void caseInstPhi(InstPhi phi) {
		Var target = phi.getTarget().getVariable();
		if (!target.isUsed()) {
			clean(target, phi);
		}

		return DONE;
	}

	@Override
	public Void caseInstStore(InstStore store) {
		return DONE;
	}

	@Override
	public Void caseProcedure(Procedure procedure) {
		changed = true;
		while (changed) {
			changed = false;
			visit(procedure.getBlocks());
		}

		// last: removes locals not used by any instruction
		Iterator<Var> it = procedure.getLocals().iterator();
		while (it.hasNext()) {
			Var local = it.next();
			if (!local.isDefined() && !local.isUsed()) {
				it.remove();
			}
		}

		return DONE;
	}

	private void clean(Var target, Instruction instruction) {
		delete(instruction);
		EcoreUtil.remove(target);
	}

	@Override
	protected void delete(EObject eObject) {
		super.delete(eObject);
		changed = true;
	}

	private void tryFoldExpression(Expression expr) {
		EObject cter = expr.eContainer();
		while (cter instanceof Expression) {
			expr = (Expression) cter;
			cter = expr.eContainer();
		}

		Expression constant = new ConstantFolder().doSwitch(expr);
		if (constant != expr) {
			// sets new expression
			EStructuralFeature feature = expr.eContainingFeature();
			cter.eSet(feature, constant);

			// removes old expression
			delete(expr);
		}
	}

	@Override
	protected <T extends EObject> Void visit(EList<T> objects) {
		int i = objects.size() - 1;
		while (i >= 0) {
			int size = objects.size();
			if (size == 0) {
				break;
			}

			T object = objects.get(i);
			doSwitch(object);
			if (objects.size() <= size) {
				// either there are the same number of objects
				// or one was removed
				i--;
			}
		}
		return DONE;
	}

}
