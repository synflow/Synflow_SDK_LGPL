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
package com.synflow.cx.ui;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorFactory;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

import com.google.inject.Inject;
import com.synflow.core.SynflowCore;
import com.synflow.models.dpn.Transition;

/**
 * This class defines a creator of Cx editors.
 *
 * @author Matthieu Wipliez
 *
 */
@SuppressWarnings("restriction")
public class CxEditorCreator {

	private class CxEditedResourceProvider implements IEditedResourceProvider {

		private final IFile file;

		public CxEditedResourceProvider(IFile file) {
			this.file = file;
		}

		@Override
		public XtextResource createResource() {
			IProject project = file.getProject();
			ResourceSet resourceSet = resourceSetProvider.get(project);
			String path = file.getFullPath().toString();
			URI uri = URI.createPlatformResourceURI(path, true);
			return (XtextResource) resourceSet.getResource(uri, true);
		}
	}

	private static CxEditorCreator instance;

	/**
	 * Returns the instance of a Cx editor creator.
	 *
	 * @return the instance of a Cx editor creator.
	 */
	public static CxEditorCreator get() {
		if (instance == null) {
			CxExecutableExtensionFactory factory = new CxExecutableExtensionFactory();
			try {
				factory.setInitializationData(null, null, CxEditorCreator.class.getName());
				instance = (CxEditorCreator) factory.create();
			} catch (CoreException e) {
				SynflowCore.log(e);
			}
		}
		return instance;
	}

	@Inject
	private EmbeddedEditorFactory editorFactory;

	@Inject
	private IResourceSetProvider resourceSetProvider;

	/**
	 * Creates an editor on the given composite.
	 *
	 * @param file
	 *            file to open
	 * @param from
	 *            from
	 * @param to
	 *            to
	 * @param parent
	 *            parent composite
	 * @return control of the editor
	 */
	public Control createEditor(IFile file, Transition transition, Composite parent) {
		SourceViewerFactory.style = SWT.NONE;
		EmbeddedEditor embeddedEditor = editorFactory.newEditor(new CxEditedResourceProvider(file))
				.readOnly().withParent(parent);
		SourceViewerFactory.style = null;

		String content = "";
		try {
			Path path = Paths.get(file.getLocationURI());
			Charset cs = Charset.forName("UTF-8");
			List<String> lines = Files.readAllLines(path, cs);

			StringBuilder result = new StringBuilder();
			for (int lineNumber : transition.getLines()) {
				String line = lines.get(lineNumber - 1).trim();
				if (!line.isEmpty()) {
					result.append(line);
					result.append(" /* line " + lineNumber + " */\n");
				}
			}

			content = result.toString();
		} catch (IOException e) {
			SynflowCore.log(e);
		}

		// set content
		embeddedEditor.createPartialEditor("", content, "", true);

		return embeddedEditor.getViewer().getControl();
	}

}
