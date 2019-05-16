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
package com.synflow.cx.ui.internal.views.fsm;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.synflow.cx.ui.internal.views.CommonSelectionEditPolicy;
import com.synflow.models.dpn.State;
import com.synflow.models.dpn.Transition;

/**
 * This class defines a layout edit policy that shows a piece of Cx code when hovering.
 *
 * @author Matthieu Wipliez
 *
 */
public class LayoutEditPolicy extends XYLayoutEditPolicy {

	private CodePopup dialog;

	private boolean mouseEntered;

	private org.eclipse.draw2d.geometry.Point referenceLocation;

	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return new CommonSelectionEditPolicy();
	}

	@Override
	public void eraseTargetFeedback(Request request) {
		if (!REQ_SELECTION_HOVER.equals(request.getType())) {
			return;
		}

		Display.getDefault().timerExec(500, new Runnable() {

			@Override
			public void run() {
				if (!mouseEntered) {
					if (dialog != null) {
						dialog.close();
						dialog = null;
					}
				}
			}

		});
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}

	@Override
	public void showTargetFeedback(Request request) {
		if (!REQ_SELECTION_HOVER.equals(request.getType())) {
			return;
		}

		Object model = getHost().getModel();
		if (!(model instanceof State) && !(model instanceof Transition)) {
			return;
		}

		if (dialog != null) {
			dialog.close();
		}

		// get relative point at which tooltip should be displayed
		LocationRequest locReq = (LocationRequest) request;
		referenceLocation = locReq.getLocation();
		Point relPoint = new Point(referenceLocation.x, referenceLocation.y + 20);

		// translate relative to absolute coordinates
		Control control = getHost().getViewer().getControl();
		Display display = control.getDisplay();
		Point absPoint = display.map(control, null, relPoint);

		// create dialog
		Shell shell = control.getShell();
		dialog = new CodePopup(shell);
		dialog.setLocation(absPoint);

		// set model
		if (model instanceof State) {
			dialog.setModel((State) model);
		} else {
			dialog.setModel((Transition) model);
		}

		// open dialog
		mouseEntered = false;
		dialog.open();

		Composite composite = dialog.getComposite();
		composite.addMouseTrackListener(new MouseTrackListener() {

			@Override
			public void mouseEnter(MouseEvent e) {
				mouseEntered = true;
			}

			@Override
			public void mouseExit(MouseEvent e) {
			}

			@Override
			public void mouseHover(MouseEvent e) {
			}

		});
	}

}
