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
package com.synflow.ngDesign.internal.generators.verilog.transformations;

import java.util.List;

import com.synflow.models.dpn.Entity;
import com.synflow.models.ir.Block;
import com.synflow.models.ir.BlockBasic;
import com.synflow.models.ir.ExprResize;
import com.synflow.models.ir.ExprTypeConv;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.Instruction;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeInt;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.transform.AbstractExpressionTransformer;
import com.synflow.models.ir.transform.UniqueNameComputer;
import com.synflow.models.ir.util.TypeUtil;
import com.synflow.models.util.EcoreHelper;

/**
 * This class implements resizes in a lint-friendly way using temporary variables.
 *
 * @author Matthieu Wipliez
 *
 */
public class ResizeExtractor extends AbstractExpressionTransformer {

	private static final IrFactory ir = IrFactory.eINSTANCE;

	private UniqueNameComputer nameComputer;

	@Override
	public Expression caseExprResize(ExprResize resize) {
		resize = (ExprResize) super.caseExprResize(resize);

		Expression expr = resize.getExpr();
		Type type = TypeUtil.getType(expr);
		if (!type.isInt()) {
			return resize;
		}

		TypeInt ti = (TypeInt) type;

		int targetSize = resize.getTargetSize();
		int sourceSize = ti.getSize();
		if (sourceSize < targetSize) {
			return extract(ti.isSigned() ? "sext" : "zext", ti, targetSize, expr);
		} else if (targetSize < sourceSize) {
			return extract("trunc", ti, targetSize, expr);
		} else {
			// unnecessary resize, ignore it and replace by expr
			return expr;
		}
	}

	private Expression extract(String operation, TypeInt source, int targetSize, Expression expr) {
		if (!expr.isExprVar()) {
			// must extract expr to a temp variable
			Type type = ir.createTypeInt(source.getSize(), source.isSigned());
			String name = nameComputer.getUniqueName("temp");
			Var temp = ir.newTempLocalVariable(getProcedure(), type, name);

			Instruction inst = EcoreHelper.getContainerOfType(expr, Instruction.class);
			if (inst == null) {
				Block block = EcoreHelper.getContainerOfType(expr, Block.class);
				List<Block> blocks = EcoreHelper.getContainingList(block);
				BlockBasic blockBasic = ir.createBlockBasic();
				blocks.add(blocks.indexOf(block), blockBasic);
				blockBasic.add(ir.createInstAssign(temp, expr));
			} else {
				BlockBasic block = (BlockBasic) inst.eContainer();
				block.add(block.indexOf(inst), ir.createInstAssign(temp, expr));
			}

			expr = ir.createExprVar(temp);
		}

		ExprTypeConv truncated = ir.convert(operation + "." + targetSize, expr);
		truncated.setComputedType(ir.createTypeInt(targetSize, source.isSigned()));
		return truncated;
	}

	@Override
	public void setProcedure(Procedure procedure) {
		if (nameComputer == null) {
			Entity entity = EcoreHelper.getContainerOfType(procedure, Entity.class);
			nameComputer = new UniqueNameComputer(entity.getVariables());
		}

		super.setProcedure(procedure);
	}

}
