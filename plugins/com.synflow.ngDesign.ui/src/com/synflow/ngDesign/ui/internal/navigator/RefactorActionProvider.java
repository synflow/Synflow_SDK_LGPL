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

import java.util.Iterator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.synflow.core.layout.ITreeElement;
import com.synflow.ngDesign.ui.internal.navigator.actions.RenameAction;

/**
 * This class describes a refactor action provider.
 *
 * @author Matthieu Wipliez
 *
 */
public class RefactorActionProvider extends CommonActionProvider {

	private RenameAction renameAction;

	private Shell shell;

	private Tree tree;

	@Override
	public void fillActionBars(IActionBars actionBars) {
		updateActionBars();

		actionBars.setGlobalActionHandler(ActionFactory.RENAME.getId(), renameAction);
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();

		boolean canRename = true;
		Iterator<?> it = selection.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof ITreeElement) {
				// cannot rename source folder
				ITreeElement element = (ITreeElement) obj;
				if (element.isSourceFolder()) {
					canRename = false;
				}
			}
		}

		if (canRename) {
			renameAction.selectionChanged(selection);
			menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE, renameAction);
		}
	}

	@Override
	public void init(ICommonActionExtensionSite site) {
		shell = site.getViewSite().getShell();
		tree = (Tree) site.getStructuredViewer().getControl();

		makeActions();
	}

	protected void makeActions() {
		IShellProvider sp = new IShellProvider() {
			@Override
			public Shell getShell() {
				return shell;
			}
		};

		renameAction = new RenameAction(sp, tree);
		renameAction.setActionDefinitionId(IWorkbenchCommandConstants.FILE_RENAME);
	}

	@Override
	public void updateActionBars() {
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();

		renameAction.selectionChanged(selection);
	}

}
