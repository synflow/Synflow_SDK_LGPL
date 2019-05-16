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
package com.synflow.ngDesign.ui.internal.navigator;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.common.CommandException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.navigator.CommonNavigator;

import com.synflow.core.SynflowCore;

/**
 * This class defines the Project Explorer view. We define our own view so it is not polluted with
 * all JDT actions.
 *
 * @author Matthieu Wipliez
 *
 */
public class ProjectExplorer extends CommonNavigator {

	public static final String VIEW_ID = "com.synflow.ngDesign.ui.projectExplorer";

	@Override
	protected void handleDoubleClick(DoubleClickEvent anEvent) {
		ICommandService commandService = (ICommandService) getViewSite().getService(
				ICommandService.class);
		Command openProjectCommand = commandService
				.getCommand(IWorkbenchCommandConstants.PROJECT_OPEN_PROJECT);
		if (openProjectCommand != null && openProjectCommand.isHandled()) {
			IStructuredSelection selection = (IStructuredSelection) anEvent.getSelection();
			Object element = selection.getFirstElement();
			if (element instanceof IProject && !((IProject) element).isOpen()) {
				try {
					openProjectCommand.executeWithChecks(new ExecutionEvent());
				} catch (CommandException e) {
					SynflowCore.log(e);
				}
				return;
			}
		}
		super.handleDoubleClick(anEvent);
	}

}
