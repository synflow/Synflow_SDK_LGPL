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
package com.synflow.cx.ui.internal.views.graph;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.synflow.cx.ui.internal.views.CommonSelectionEditPolicy;

/**
 * This class defines a simple layout edit policy.
 *
 * @author Matthieu Wipliez
 *
 */
public class LayoutEditPolicy extends XYLayoutEditPolicy {

	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return new CommonSelectionEditPolicy();
	}

	@Override
	public Command getCommand(Request request) {
		if (REQ_OPEN.equals(request.getType())) {
			return new DoubleClickCommand(getHost());
		} else {
			return super.getCommand(request);
		}
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}

}
