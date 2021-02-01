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
package com.synflow.cx.ui.internal.views.fsm;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;

/**
 * This class defines the edit part factory for the FSM model.
 *
 * @author Matthieu Wipliez
 *
 */
public class FsmEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		AbstractGraphicalEditPart part;

		if (model instanceof FSM) {
			part = new FsmPart();
		} else if (model instanceof State) {
			part = new StatePart();
		} else if (model instanceof Transition) {
			part = new TransitionPart();
		} else {
			return null;
		}

		part.setModel(model);
		return part;
	}

}
