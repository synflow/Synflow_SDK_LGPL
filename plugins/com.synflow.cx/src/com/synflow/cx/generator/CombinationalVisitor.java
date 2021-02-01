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
package com.synflow.cx.generator;

import static com.google.common.collect.Iterables.all;
import static com.synflow.core.IProperties.PROP_CLOCKS;
import static com.synflow.models.util.SwitchUtil.DONE;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.Port;
import com.synflow.models.ir.InstStore;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.transform.StoreOnceTransformation;
import com.synflow.models.ir.util.IrUtil;
import com.synflow.models.util.Void;

/**
 * This class splits code between combinational and body procedures depending on the type of ports
 * written (combinational or not).
 *
 * @author Matthieu Wipliez
 *
 */
public class CombinationalVisitor {

	private static class CodeRemover extends SideEffectRemover {

		private boolean combinational;

		public CodeRemover(boolean combinational) {
			this.combinational = combinational;
		}

		@Override
		protected void delete(EObject eObject) {
			if (combinational) {
				// only deletes prints, gotos, etc. in combinational procedure
				IrUtil.delete(eObject);
			}
		}

		@Override
		public Void caseInstStore(InstStore store) {
			Var target = store.getTarget().getVariable();
			Port port;
			if (target instanceof Port) {
				port = (Port) target;
			} else {
				EObject cter = target.eContainer();
				if (cter instanceof Port) {
					port = (Port) cter;
				} else {
					// store target is not a port or port signal, delegates to super
					return super.caseInstStore(store);
				}
			}

			if (combinational == port.isSynchronous()) {
				// deletes synchronous ports in combinational procedure
				// and non-synchronous ports in body procedure
				IrUtil.delete(store);
			}

			return DONE;
		}

	}

	public void visit(Actor actor) {
		boolean combinational = actor.getProperties().getAsJsonArray(PROP_CLOCKS).size() == 0;
		if (combinational) {
			// makes all ports asynchronous
			for (Port port : actor.getOutputs()) {
				port.setSynchronous(false);
			}
		} else if (all(actor.getOutputs(), port -> port.isSynchronous())) {
			// actor not combinational, and no combinational ports, nothing to do
			return;
		}

		for (Action action : actor.getActions()) {
			if (combinational) {
				// easy: all the body becomes combinational
				Procedure comb = action.getCombinational();
				action.setCombinational(action.getBody());
				action.setBody(comb);
			} else {
				List<Port> combPorts = new ArrayList<>();
				for (Port port : action.getOutputPattern().getPorts()) {
					if (!port.isSynchronous()) {
						combPorts.add(port);
					}
				}

				// if action does not write to a combinational port, ignore
				if (combPorts.isEmpty()) {
					continue;
				}

				String name = action.getCombinational().getName();
				action.setCombinational(IrUtil.copy(action.getBody()));
				action.getCombinational().setName(name);

				// removes stores to combinational ports in body
				new CodeRemover(false).doSwitch(action.getBody());

				// removes side effects except stores to combinational ports in combinational proc
				new StoreOnceTransformation().doSwitch(action.getCombinational());
				new CodeRemover(true).doSwitch(action.getCombinational());
			}
		}
	}

}
