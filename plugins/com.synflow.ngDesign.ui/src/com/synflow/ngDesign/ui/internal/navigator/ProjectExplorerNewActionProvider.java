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

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.WizardActionGroup;

import com.synflow.core.layout.SourceFolder;
import com.synflow.ngDesign.ui.internal.navigator.actions.NewSynflowProjectAction;

/**
 * This class defines an action provider for new items based on
 * org.eclipse.ui.internal.navigator.resources.actions.NewActionProvider. The differences are:
 * <ul>
 * <li>no generic project, replaced by Synflow Project.</li>
 * <li>no "Other" new wizard.</li>
 * </ul>
 *
 * @author Matthieu Wipliez
 *
 */
public class ProjectExplorerNewActionProvider extends CommonActionProvider {

	private static final String NEW_MENU_NAME = "common.new.menu";//$NON-NLS-1$

	private IAction newProjectAction;

	private WizardActionGroup newWizardActionGroup;

	@Override
	public void fillContextMenu(IMenuManager menu) {
		if (menu.find(ICommonMenuConstants.GROUP_NEW) == null) {
			fillSubmenu(menu);
		} else {
			// create a submenu, fill, and then insert after the GROUP_NEW group
			IMenuManager submenu = new MenuManager("&New", NEW_MENU_NAME);
			fillSubmenu(submenu);
			menu.insertAfter(ICommonMenuConstants.GROUP_NEW, submenu);
		}
	}

	private void fillSubmenu(IMenuManager submenu) {
		// Add new project wizard shortcut
		submenu.add(newProjectAction);
		submenu.add(new Separator());

		// fill the menu from the commonWizard contributions
		ISelection selection = getContext().getSelection();
		Object input = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) selection;
			input = sel.getFirstElement();
		}

		if (input instanceof IResource && !((IResource) input).isAccessible()) {
			// if input is a non-accessible resource, do not add file/folder actions
		} else {
			newWizardActionGroup.setContext(getContext());
			newWizardActionGroup.fillContextMenu(submenu);

			if (input instanceof SourceFolder) {
				// removes the "New file" wizard
				// because it's forbidden to create a file at the root of the source folder
				// note: this wizard happens to be the first entry whose id is "new"
				submenu.remove("new");
			}
		}
	}

	@Override
	public void init(ICommonActionExtensionSite anExtensionSite) {
		if (anExtensionSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {
			IWorkbenchWindow window = ((ICommonViewerWorkbenchSite) anExtensionSite.getViewSite())
					.getWorkbenchWindow();
			init(window, anExtensionSite.getContentService());
		}
	}

	public void init(IWorkbenchWindow window, INavigatorContentService contentService) {
		newProjectAction = new NewSynflowProjectAction(window);
		newWizardActionGroup = new WizardActionGroup(window,
				PlatformUI.getWorkbench().getNewWizardRegistry(), WizardActionGroup.TYPE_NEW,
				contentService);
	}

}
