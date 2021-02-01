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
package com.synflow.cx.ui.internal.views.graph;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com.synflow.core.SynflowCore;
import com.synflow.core.util.CoreUtil;
import com.synflow.cx.ui.internal.views.graph.editparts.InstancePart;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Instance;

/**
 * This class defines a double-click command that opens the entity referenced by an instance. Does
 * nothing for other edit parts (ports and DPN itself).
 *
 * @author Matthieu Wipliez
 *
 */
public class DoubleClickCommand extends Command {

	private Instance instance;

	public DoubleClickCommand(EditPart part) {
		super("Open instance");
		if (part instanceof InstancePart) {
			instance = (Instance) part.getModel();
		}
	}

	@Override
	public boolean canExecute() {
		return instance != null;
	}

	@Override
	public void execute() {
		Entity entity = instance.getEntity();
		if (CoreUtil.isBuiltin(entity)) {
			return;
		}

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			IEditorPart editor = IDE.openEditor(page, entity.getFile());
			if (editor instanceof XtextEditor) {
				XtextEditor textEditor = (XtextEditor) editor;
				int lineNumber = entity.getLineNumber();
				try {
					// select line at which entity is declared
					// this updates the views too
					int offset = textEditor.getDocument().getLineOffset(lineNumber);
					textEditor.selectAndReveal(offset, 0);
				} catch (BadLocationException e) {
					SynflowCore.log(e);
				}
			}
		} catch (PartInitException e) {
			SynflowCore.log(e);
		}
	}

}
