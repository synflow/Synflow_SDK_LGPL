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
package com.synflow.ngDesign.ui.internal.perspectives;

import static org.eclipse.debug.ui.IDebugUIConstants.ID_DEBUG_PERSPECTIVE;
import static org.eclipse.egit.ui.UIPreferences.SHOW_HOME_DIR_WARNING;
import static org.eclipse.egit.ui.UIPreferences.SHOW_INITIAL_CONFIG_DIALOG;
import static org.eclipse.ui.IPageLayout.ID_OUTLINE;
import static org.eclipse.ui.IPageLayout.ID_PROBLEM_VIEW;
import static org.eclipse.ui.console.IConsoleConstants.ID_CONSOLE_VIEW;
import static org.eclipse.ui.progress.IProgressConstants.PROGRESS_VIEW_ID;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.egit.ui.Activator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.synflow.core.SynflowCore;
import com.synflow.cx.ui.views.FsmView;
import com.synflow.cx.ui.views.GraphView;
import com.synflow.ngDesign.ui.internal.navigator.ProjectExplorer;
import com.synflow.ngDesign.ui.internal.wizards.NewProjectWizard;

/**
 * This class is meant to serve as an example for how various contributions are made to a
 * perspective. Note that some of the extension point id's are referred to as API constants while
 * others are hardcoded and may be subject to change.
 */
@SuppressWarnings("restriction")
public class NgDesignPerspective implements IPerspectiveFactory {

	private static final String LOG_VIEW = "org.eclipse.pde.runtime.LogView";

	@Override
	public void createInitialLayout(IPageLayout layout) {
		resetBuiltinPreferences();

		// package explorer
		String editorArea = layout.getEditorArea();
		IFolderLayout folder = layout.createFolder("left", IPageLayout.LEFT, 0.2f, editorArea); //$NON-NLS-1$
		folder.addView(ProjectExplorer.VIEW_ID);
		folder.addView(ID_OUTLINE);

		// git repositories
		layout.addView("org.eclipse.egit.ui.RepositoriesView", IPageLayout.BOTTOM, 0.75f,
				ProjectExplorer.VIEW_ID);

		// problems view
		IFolderLayout outputfolder = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.75f, //$NON-NLS-1$
				editorArea);
		outputfolder.addView(ID_PROBLEM_VIEW);
		outputfolder.addView(ID_CONSOLE_VIEW);
		outputfolder.addPlaceholder(LOG_VIEW);
		outputfolder.addPlaceholder(PROGRESS_VIEW_ID);

		// outline
		IFolderLayout outlineFolder = layout.createFolder("right", IPageLayout.RIGHT, 0.6f, //$NON-NLS-1$
				editorArea);
		outlineFolder.addView(GraphView.ID);

		// FSM
		IFolderLayout views = layout.createFolder("views", IPageLayout.BOTTOM, 0.5f, "right");
		views.addView(FsmView.ID);

		// action sets
		layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);

		// view shortcut - Synflow-specific
		layout.addShowViewShortcut(ProjectExplorer.VIEW_ID);
		layout.addShowViewShortcut(FsmView.ID);
		layout.addShowViewShortcut(GraphView.ID);

		// view shortcut - debugging
		layout.addShowViewShortcut(ID_CONSOLE_VIEW);

		// view shortcuts - standard workbench
		layout.addShowViewShortcut(ID_OUTLINE);
		layout.addShowViewShortcut(ID_PROBLEM_VIEW);
		layout.addShowViewShortcut(LOG_VIEW);

		// new actions - new project creation wizard
		layout.addNewWizardShortcut(NewProjectWizard.WIZARD_ID);

		// 'Window' > 'Open Perspective' contributions
		layout.addPerspectiveShortcut(ID_DEBUG_PERSPECTIVE);
	}

	private void resetBuiltinPreferences() {
		// hide warning from Git before initializing the perspective
		// so the user don't see that popup
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setValue(SHOW_HOME_DIR_WARNING, false);
		store.setValue(SHOW_INITIAL_CONFIG_DIALOG, false);

		// disable Xtext overlay decorator
		IWorkbench workbench = PlatformUI.getWorkbench();
		IDecoratorManager mgr = workbench.getDecoratorManager();
		try {
			mgr.setEnabled("org.eclipse.xtext.builder.nature.overlay", false);
		} catch (CoreException e) {
			SynflowCore.log(e);
		}
	}

}
