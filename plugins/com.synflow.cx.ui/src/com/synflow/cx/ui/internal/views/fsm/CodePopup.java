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

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.synflow.cx.ui.CxEditorCreator;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;
import com.synflow.models.graph.Edge;

/**
 * This class defines a code popup that shows Cx code.
 *
 * @author Matthieu Wipliez
 *
 */
public class CodePopup extends PopupDialog {

	private Composite composite;

	private IFile file;

	private Transition transition;

	private Point location;

	public CodePopup(Shell parent) {
		super(parent, SWT.TOOL, false, false, false, false, false, null, null);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		CxEditorCreator creator = CxEditorCreator.get();
		composite = new Composite(parent, SWT.NONE);
		creator.createEditor(file, transition, composite);
		composite.setLayout(new GridLayout());
		return composite;
	}

	public Composite getComposite() {
		return composite;
	}

	@Override
	protected Point getInitialLocation(Point initialSize) {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public void setModel(State state) {
		for (Edge edge : state.getOutgoing()) {
			Transition transition = (Transition) edge;
			setModel(transition);
			return;
		}
	}

	public void setModel(Transition transition) {
		Actor actor = (Actor) transition.eContainer().eContainer();
		file = actor.getFile();
		this.transition = transition;
	}

}
