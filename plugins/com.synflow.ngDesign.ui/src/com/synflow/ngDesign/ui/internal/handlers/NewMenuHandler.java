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
package com.synflow.ngDesign.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.navigator.INavigatorContentService;

import com.synflow.ngDesign.ui.internal.navigator.ProjectExplorer;
import com.synflow.ngDesign.ui.internal.navigator.ProjectExplorerNewActionProvider;

/**
 * This class defines a handler for "new" menu. Shows a New menu using the ProjectExplorerNewActionProvider.
 *
 * @author Matthieu Wipliez
 *
 */
public class NewMenuHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPart part = HandlerUtil.getActivePartChecked(event);
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		if (ProjectExplorer.VIEW_ID.equals(part.getSite().getId())) {
			ProjectExplorer explorer = (ProjectExplorer) part.getAdapter(ProjectExplorer.class);
			INavigatorContentService service = explorer.getNavigatorContentService();

			// creates and fills a menu manager
			MenuManager menuManager = new MenuManager();
			ProjectExplorerNewActionProvider provider = new ProjectExplorerNewActionProvider();

			ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
			provider.setContext(new ActionContext(selection));
			provider.init(window, service);
			provider.fillContextMenu(menuManager);

			// create context menu and show it
			TreeViewer commonViewer = explorer.getCommonViewer();
			showMenu(menuManager, commonViewer.getTree());
		}

		return null;
	}

	private void showMenu(MenuManager menuManager, Tree tree) {
		Menu menu = menuManager.createContextMenu(tree);

		TreeItem[] items = tree.getSelection();
		Rectangle rect;
		if (items.length > 0) {
			rect = items[0].getBounds();
		} else {
			rect = new Rectangle(41, 0, 0, 18);
		}
		Point location = tree.toDisplay(rect.x, rect.y + rect.height);
		menu.setLocation(location);

		menu.setVisible(true);
	}

}
