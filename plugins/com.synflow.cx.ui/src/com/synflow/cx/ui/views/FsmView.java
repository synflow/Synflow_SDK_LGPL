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
package com.synflow.cx.ui.views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.custom.StyledText;

import com.synflow.cx.ui.internal.views.fsm.FsmEditPartFactory;
import com.synflow.cx.ui.internal.views.fsm.layout.FsmLayout;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.FSM;
import com.synflow.models.dpn.Transition;
import com.synflow.models.ir.util.IrUtil;

/**
 * This class defines a view of the FSM of a task.
 *
 * @author Matthieu Wipliez
 *
 */
public class FsmView extends AbstractGefView {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.synflow.cx.ui.views.FsmView";

	private FSM fsm;

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new FsmEditPartFactory();
	}

	@Override
	protected void irFileLoaded(Entity entity) {
		if (entity instanceof Actor) {
			// fsm is actor's FSM (may be null)
			Actor actor = (Actor) entity;
			fsm = actor.getFsm();
		} else {
			// no FSM
			fsm = null;
		}

		// set part name
		String partName = IrUtil.getSimpleName(getEntityName()) + ": ";
		if (fsm == null) {
			partName += "no FSM";
			if (!(entity instanceof Actor)) {
				partName += " (not a task)";
			}
		} else {
			int states = fsm.getStates().size();
			int transitions = fsm.getTransitions().size();
			partName += states + " states, " + transitions + " transitions";

			// layout FSM
			new FsmLayout().visit(fsm);
		}
		setPartName(partName);

		viewer.setContents(fsm);
	}

	@Override
	protected void lineSelected(StyledText text, int offset) {
		if (fsm == null) {
			// no FSM, nothing to do
			return;
		}

		int line = text.getLineAtOffset(offset) + 1;
		List<EditPart> parts = new ArrayList<>();
		for (Transition transition : fsm.getTransitions()) {
			if (transition.getLines().contains(line)) {
				EditPart part = (EditPart) viewer.getEditPartRegistry().get(transition);
				parts.add(part);
			}
		}

		// selects all appropriate edit parts
		viewer.setSelection(new StructuredSelection(parts));

		// reveals the first one (avoids scrolling when FSM is big)
		Iterator<EditPart> it = parts.iterator();
		if (it.hasNext()) {
			viewer.reveal(it.next());
		}
	}

}
