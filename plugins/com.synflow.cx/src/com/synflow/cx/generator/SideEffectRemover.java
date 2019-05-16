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

import com.synflow.models.dpn.Goto;
import com.synflow.models.ir.InstCall;
import com.synflow.models.ir.InstStore;
import com.synflow.models.ir.Instruction;
import com.synflow.models.ir.transform.AbstractIrVisitor;
import com.synflow.models.util.Void;

/**
 * This class defines an IR transformation that removes stores and prints in scheduler.
 *
 * @author Matthieu Wipliez
 *
 */
public class SideEffectRemover extends AbstractIrVisitor {

	@Override
	public Void caseInstCall(InstCall call) {
		if (call.isPrint()) {
			delete(call);
		}

		return DONE;
	}

	@Override
	public Void caseInstruction(Instruction inst) {
		if (inst instanceof Goto) {
			delete(inst);
		}
		return DONE;
	}

	@Override
	public Void caseInstStore(InstStore store) {
		delete(store);
		return DONE;
	}

}
