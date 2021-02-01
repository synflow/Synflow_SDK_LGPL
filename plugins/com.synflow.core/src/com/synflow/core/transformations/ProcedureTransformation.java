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

import static com.synflow.models.util.SwitchUtil.CASCADE;
import static com.synflow.models.util.SwitchUtil.DONE;

import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Unit;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.transform.AbstractIrVisitor;
import com.synflow.models.util.Void;

/**
 * This class defines a transformation that transforms all procedures in an actor/unit.
 *
 * @author Matthieu Wipliez
 *
 */
public class ProcedureTransformation extends Transformation {

	public ProcedureTransformation(AbstractIrVisitor irVisitor) {
		super(irVisitor);
	}

	@Override
	public Void caseActor(Actor actor) {
		caseEntity(actor);

		for (Action action : actor.getActions()) {
			visitProcedure(action.getBody());
			visitProcedure(action.getCombinational());
			visitProcedure(action.getScheduler());
		}

		return DONE;
	}

	@Override
	public Void caseEntity(Entity entity) {
		for (Procedure procedure : entity.getProcedures()) {
			visitProcedure(procedure);
		}

		return DONE;
	}

	@Override
	public Void caseUnit(Unit unit) {
		return CASCADE;
	}

	protected void visitProcedure(Procedure procedure) {
		irVisitor.doSwitch(procedure);
	}

}
