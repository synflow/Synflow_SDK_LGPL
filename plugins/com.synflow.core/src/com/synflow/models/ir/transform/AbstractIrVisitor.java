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

import static com.synflow.models.util.SwitchUtil.DONE;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import com.synflow.models.ir.BlockBasic;
import com.synflow.models.ir.BlockIf;
import com.synflow.models.ir.BlockWhile;
import com.synflow.models.ir.ExprBinary;
import com.synflow.models.ir.ExprUnary;
import com.synflow.models.ir.Instruction;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.util.IrSwitch;
import com.synflow.models.ir.util.IrUtil;
import com.synflow.models.util.Void;

/**
 * This abstract class implements a no-op visitor on IR procedures, blocks,
 * instructions, and (if visitFull is <code>true</code>) expressions. This class
 * should be extended by classes that implement intra-procedural IR visitors and
 * transformations.
 *
 * @author Matthieu Wipliez
 * @since 1.2
 */
public abstract class AbstractIrVisitor extends IrSwitch<Void> {

	/**
	 * current procedure being visited
	 */
	protected Procedure procedure;

	@Override
	public Void caseBlockBasic(BlockBasic block) {
		return visit(block.getInstructions());
	}

	@Override
	public Void caseBlockIf(BlockIf blockIf) {
		visit(blockIf.getThenBlocks());
		visit(blockIf.getElseBlocks());
		doSwitch(blockIf.getJoinBlock());
		return DONE;
	}

	@Override
	public Void caseBlockWhile(BlockWhile blockWhile) {
		visit(blockWhile.getBlocks());
		doSwitch(blockWhile.getJoinBlock());
		return DONE;
	}

	@Override
	public Void caseExprBinary(ExprBinary expr) {
		doSwitch(expr.getE1());
		doSwitch(expr.getE2());
		return DONE;
	}

	@Override
	public Void caseExprUnary(ExprUnary expr) {
		doSwitch(expr.getExpr());
		return DONE;
	}

	@Override
	public Void caseProcedure(Procedure procedure) {
		this.procedure = procedure;
		return visit(procedure.getBlocks());
	}

	protected void delete(EObject eObject) {
		IrUtil.delete(eObject);
	}

	@Override
	public final Void doSwitch(EObject eObject) {
		if (eObject == null) {
			return null;
		}
		return doSwitch(eObject.eClass(), eObject);
	}

	@Override
	public Void doSwitch(int classifierID, EObject eObject) {
		// just so we can use it in DfVisitor
		return super.doSwitch(classifierID, eObject);
	}

	@Override
	public boolean isSwitchFor(EPackage ePackage) {
		// just so we can use it in DfVisitor
		return super.isSwitchFor(ePackage);
	}

	protected void replace(Instruction instr, Instruction by) {
		BlockBasic block = (BlockBasic) instr.eContainer();
		int index = block.indexOf(instr);
		block.getInstructions().set(index, by);
	}

	protected <T extends EObject> Void visit(EList<T> objects) {
		int i = 0;
		while (i < objects.size()) {
			T object = objects.get(i);
			int size = objects.size();
			doSwitch(object);
			if (size == objects.size() && object == objects.get(i)) {
				i++;
			}
		}
		return DONE;
	}

}
