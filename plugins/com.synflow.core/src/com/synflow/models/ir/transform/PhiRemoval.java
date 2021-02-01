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
 
package com.synflow.models.ir.transform;

import static com.synflow.models.ir.IrPackage.Literals.PROCEDURE__PARAMETERS;
import static com.synflow.models.util.SwitchUtil.DONE;

import java.util.ArrayList;
import java.util.List;

import com.synflow.models.ir.Block;
import com.synflow.models.ir.BlockBasic;
import com.synflow.models.ir.BlockIf;
import com.synflow.models.ir.BlockWhile;
import com.synflow.models.ir.ExprVar;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstAssign;
import com.synflow.models.ir.InstPhi;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.util.IrUtil;
import com.synflow.models.util.EcoreHelper;
import com.synflow.models.util.Void;

/**
 * This class removes phi assignments and transforms them to copies.
 *
 * @author Matthieu Wipliez
 *
 */
public class PhiRemoval extends AbstractIrVisitor {

	private class PhiRemover extends AbstractIrVisitor {

		@Override
		public Void caseInstPhi(InstPhi phi) {
			delete(phi);
			return DONE;
		}

	}

	private List<Var> localsToRemove;

	private int phiIndex;

	private BlockBasic targetBlock;

	@Override
	public Void caseBlockBasic(BlockBasic block) {
		return visit(block.getInstructions());
	}

	@Override
	public Void caseBlockIf(BlockIf block) {
		BlockBasic join = block.getJoinBlock();
		targetBlock = IrUtil.getLast(block.getThenBlocks());
		phiIndex = 0;
		doSwitch(join);

		targetBlock = IrUtil.getLast(block.getElseBlocks());
		phiIndex = 1;
		doSwitch(join);
		new PhiRemover().doSwitch(join);

		visit(block.getThenBlocks());
		return visit(block.getElseBlocks());
	}

	@Override
	public Void caseBlockWhile(BlockWhile block) {
		List<Block> blocks = EcoreHelper.getContainingList(block);
		// the block before the while.
		int indexBlock = blocks.indexOf(block);
		if (indexBlock > 0) {
			Block previousBlock = blocks.get(indexBlock - 1);
			if (previousBlock.isBlockBasic()) {
				targetBlock = (BlockBasic) previousBlock;
			} else {
				targetBlock = IrFactory.eINSTANCE.createBlockBasic();
				blocks.add(indexBlock, targetBlock);
			}
		} else {
			targetBlock = IrFactory.eINSTANCE.createBlockBasic();
			blocks.add(indexBlock, targetBlock);
		}

		BlockBasic join = block.getJoinBlock();
		phiIndex = 0;
		doSwitch(join);

		// last block of the while
		targetBlock = IrUtil.getLast(block.getBlocks());
		phiIndex = 1;
		doSwitch(join);
		new PhiRemover().doSwitch(join);

		// visit inner blocks
		return visit(block.getBlocks());
	}

	@Override
	public Void caseInstPhi(InstPhi phi) {
		Var target = phi.getTarget().getVariable();
		Expression sourceExpr = phi.getValues().get(phiIndex);

		// if source is a local variable with index = 0, we remove it from the
		// procedure and translate the PHI by an assignment of 0 (zero) to
		// target. Otherwise, we just create an assignment target = source.
		Expression expr;
		if (isExprVarZero(sourceExpr)) {
			Var source = ((ExprVar) sourceExpr).getUse().getVariable();
			localsToRemove.add(source);
			if (target.getType().isBool()) {
				expr = IrFactory.eINSTANCE.createExprBool(false);
			} else {
				expr = IrFactory.eINSTANCE.createExprInt(0);
			}
		} else {
			expr = IrUtil.copy(sourceExpr);
		}

		InstAssign assign = IrFactory.eINSTANCE.createInstAssign(target, expr);
		targetBlock.add(assign);

		return DONE;
	}

	@Override
	public Void caseProcedure(Procedure procedure) {
		localsToRemove = new ArrayList<Var>();

		super.caseProcedure(procedure);

		for (Var local : localsToRemove) {
			procedure.getLocals().remove(local);
		}

		return DONE;
	}

	private boolean isExprVarZero(Expression sourceExpr) {
		if (sourceExpr.isExprVar()) {
			Var source = ((ExprVar) sourceExpr).getUse().getVariable();
			return source.getIndex() == 0 && source.isLocal()
					&& source.eContainingFeature() != PROCEDURE__PARAMETERS;
		}

		return false;
	}

}
