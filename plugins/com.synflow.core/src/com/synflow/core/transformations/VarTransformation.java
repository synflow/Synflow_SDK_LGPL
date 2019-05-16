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
 
package com.synflow.core.transformations;

import static com.synflow.models.util.SwitchUtil.DONE;

import com.google.common.collect.Iterables;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Port;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.transform.AbstractIrVisitor;
import com.synflow.models.util.Void;

/**
 * This class defines a module transformation that transforms all variables in an actor/unit.
 *
 * @author Matthieu Wipliez
 *
 */
public class VarTransformation extends ProcedureTransformation {

	public VarTransformation(AbstractIrVisitor irVisitor) {
		super(irVisitor);
	}

	@Override
	public Void caseEntity(Entity entity) {
		for (Port port : Iterables.concat(entity.getInputs(), entity.getOutputs())) {
			irVisitor.doSwitch(port);
		}

		for (Var var : entity.getVariables()) {
			irVisitor.doSwitch(var);
		}

		for (Procedure procedure : entity.getProcedures()) {
			visitProcedure(procedure);
		}

		return DONE;
	}

	protected void visitProcedure(Procedure procedure) {
		for (Var var : procedure.getParameters()) {
			irVisitor.doSwitch(var);
		}

		for (Var var : procedure.getLocals()) {
			irVisitor.doSwitch(var);
		}
	}

}
