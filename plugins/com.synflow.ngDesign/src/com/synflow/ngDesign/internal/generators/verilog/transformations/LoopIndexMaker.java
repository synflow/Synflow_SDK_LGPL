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
package com.synflow.ngDesign.internal.generators.verilog.transformations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.google.common.collect.Lists;
import com.synflow.models.ir.InstLoad;
import com.synflow.models.ir.InstStore;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.transform.AbstractIrVisitor;
import com.synflow.models.util.EcoreHelper;
import com.synflow.models.util.Void;

/**
 * This class defines an IR visitor that adds synthetic variables to use in "for" loops to copy
 * arrays in procedures.
 *
 * @author Matthieu Wipliez
 *
 */
public class LoopIndexMaker extends AbstractIrVisitor {

	private static final IrFactory ir = IrFactory.eINSTANCE;

	private Var index;

	private void addIndex(Procedure procedure, Var local) {
		InstLoad load = getFirst(local.getDefs(), InstLoad.class);
		if (load == null) {
			return;
		}

		if (index == null) {
			Type type = ir.createTypeInt(32, false);
			index = ir.newTempLocalVariable(procedure, type, "loop_idx");
		}

		load.getIndexes().add(ir.createExprVar(index));

		InstStore store = getFirst(Lists.reverse(local.getUses()), InstStore.class);
		if (store != null) {
			store.getIndexes().add(ir.createExprVar(index));
		}
	}

	private <T extends EObject> T getFirst(List<? extends EObject> eObjects, Class<T> clz) {
		Iterator<? extends EObject> it = eObjects.iterator();
		return it.hasNext() ? EcoreHelper.getContainerOfType(it.next(), clz) : null;
	}

	@Override
	public Void caseProcedure(Procedure procedure) {
		index = null;
		for (Var local : new ArrayList<>(procedure.getLocals())) {
			if (local.getType().isArray()) {
				addIndex(procedure, local);
			}
		}

		this.procedure = procedure;
		return visit(procedure.getBlocks());
	}

}
