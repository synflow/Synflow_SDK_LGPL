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

import org.eclipse.gef.EditPartFactory;

import com.synflow.cx.ui.internal.views.graph.DpnEditPartFactory;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Entity;
import com.synflow.models.ir.util.IrUtil;

/**
 * This class defines a view of the graph of a DPN.
 *
 * @author Matthieu Wipliez
 *
 */
public class GraphView extends AbstractGefView {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.synflow.cx.ui.views.GraphView";

	private DPN dpn;

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new DpnEditPartFactory();
	}

	@Override
	protected void irFileLoaded(Entity entity) {
		if (entity instanceof DPN) {
			dpn = (DPN) entity;

			// set part name
			String name = IrUtil.getSimpleName(getEntityName());
			setPartName(name);

			viewer.setContents(dpn);
		}
	}

}
