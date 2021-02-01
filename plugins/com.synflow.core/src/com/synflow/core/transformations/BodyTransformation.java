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

import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.Actor;
import com.synflow.models.ir.transform.AbstractIrVisitor;
import com.synflow.models.util.Void;

/**
 * This class defines a transformation that transforms the body of actions.
 *
 * @author Matthieu Wipliez
 *
 */
public class BodyTransformation extends Transformation {

	public BodyTransformation(AbstractIrVisitor irVisitor) {
		super(irVisitor);
	}

	@Override
	public Void caseActor(Actor actor) {
		for (Action action : actor.getActions()) {
			irVisitor.doSwitch(action.getBody());
		}

		return DONE;
	}

}
