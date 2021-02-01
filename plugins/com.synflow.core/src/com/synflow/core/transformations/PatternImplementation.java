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
 
package com.synflow.core.transformations;

import static com.synflow.models.util.SwitchUtil.DONE;

import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.Port;
import com.synflow.models.dpn.util.DpnSwitch;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstReturn;
import com.synflow.models.ir.Instruction;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Var;
import com.synflow.models.util.Void;

/**
 * This class defines a transformation that replaces input patterns with tests on ports' additional
 * signals in schedulers, and output patterns with assignments to ports' additional signals.
 *
 * @author Matthieu Wipliez
 *
 */
public class PatternImplementation extends DpnSwitch<Void> {

	private static final IrFactory ir = IrFactory.eINSTANCE;

	@Override
	public Void caseActor(Actor actor) {
		for (Action action : actor.getActions()) {
			updateScheduler(action.getScheduler(), action.getInputPattern().getPorts());
		}

		return DONE;
	}

	private void updateScheduler(Procedure scheduler, List<Port> ports) {
		Instruction last = scheduler.getLast().lastListIterator().previous();
		InstReturn ret = (InstReturn) last;
		Expression expr = null;
		for (Port port : ports) {
			for (Var signal : port.getAdditionalInputs()) {
				if (expr == null) {
					expr = ir.createExprVar(signal);
				} else {
					expr = ir.createExprBinary(expr, OpBinary.LOGIC_AND, ir.createExprVar(signal));
				}
			}
		}

		if (expr != null) {
			if (EcoreUtil.equals(ir.createExprBool(true), ret.getValue())) {
				ret.setValue(expr);
			} else {
				ret.setValue(ir.createExprBinary(expr, OpBinary.LOGIC_AND, ret.getValue()));
			}
		}
	}

}
