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

import static com.synflow.models.ir.util.IrUtil.getNameSSA;
import static com.synflow.models.util.SwitchUtil.DONE;

import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Var;
import com.synflow.models.util.Void;

/**
 * This class defines an IR transformation that renames local variables when
 * using SSA.
 *
 * @author Matthieu Wipliez
 *
 */
public class SSAVariableRenamer extends AbstractIrVisitor {

	@Override
	public Void caseProcedure(Procedure procedure) {
		for (Var local : procedure.getLocals()) {
			local.setName(getNameSSA(local));
		}

		return DONE;
	}

}
