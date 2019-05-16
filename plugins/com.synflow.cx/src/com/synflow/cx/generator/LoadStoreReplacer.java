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
package com.synflow.cx.generator;

import static com.synflow.models.util.SwitchUtil.DONE;

import java.util.ArrayList;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.synflow.models.dpn.Port;
import com.synflow.models.ir.InstAssign;
import com.synflow.models.ir.InstLoad;
import com.synflow.models.ir.InstStore;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.Use;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.transform.AbstractIrVisitor;
import com.synflow.models.util.Void;

/**
 * Replaces loads of local variables and ports by direct references (use), and replaces stores to
 * scalar local variables by assignments.
 *
 * @author Matthieu Wipliez
 *
 */
public class LoadStoreReplacer extends AbstractIrVisitor {

	@Override
	public Void caseInstLoad(InstLoad load) {
		Var source = load.getSource().getVariable();
		if ((source.isLocal() || source instanceof Port) && load.getIndexes().isEmpty()) {
			// a load of a global variable, or with indexes
			// must not be replaced

			// replace uses of target by source
			Var target = load.getTarget().getVariable();
			for (Use use : new ArrayList<>(target.getUses())) {
				use.setVariable(source);
			}

			// remove target
			EcoreUtil.remove(target);

			delete(load);
		}

		return DONE;
	}

	@Override
	public Void caseInstStore(InstStore store) {
		Var target = store.getTarget().getVariable();
		if (!target.isLocal() || !store.getIndexes().isEmpty()) {
			// a store to a global variable, or with indexes
			// must not be replaced by an assign
			return DONE;
		}

		// create assign
		InstAssign assign = IrFactory.eINSTANCE.createInstAssign();
		assign.setTarget(store.getTarget());
		assign.setValue(store.getValue());

		// replace store by assign
		replace(store, assign);

		return DONE;
	}

}
