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
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.synflow.cx.ui.internal.views.graph.editparts.ConnectionPart;
import com.synflow.cx.ui.internal.views.graph.editparts.DPNPart;
import com.synflow.cx.ui.internal.views.graph.editparts.InstancePart;
import com.synflow.cx.ui.internal.views.graph.editparts.PortPart;
import com.synflow.models.dpn.Connection;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Instance;
import com.synflow.models.dpn.Port;

/**
 * This class defines the edit part factory for the DPN model.
 *
 * @author Matthieu Wipliez
 *
 */
public class DpnEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		AbstractGraphicalEditPart part;

		if (model instanceof DPN) {
			part = new DPNPart();
		} else if (model instanceof Instance) {
			part = new InstancePart();
		} else if (model instanceof Port) {
			part = new PortPart();
		} else if (model instanceof Connection) {
			part = new ConnectionPart();
		} else {
			return null;
		}

		part.setModel(model);
		return part;
	}

}
