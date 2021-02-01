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
package com.synflow.cx.ui.views;

import static com.synflow.core.ICoreConstants.FILE_EXT_CX;
import static com.synflow.models.util.EcoreHelper.getEObject;
import static com.synflow.models.util.EcoreHelper.getFile;
import static org.eclipse.core.resources.IResourceChangeEvent.POST_CHANGE;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.IXtextModelListener;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;
import com.synflow.core.util.CoreUtil;
import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.Module;
import com.synflow.cx.ui.internal.views.graph.DirtyMarqueeTool;
import com.synflow.models.dpn.Entity;

/**
 * This class defines a GEF-based view of a Cx entity.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class AbstractGefView extends ViewPart
		implements IPartListener, IResourceChangeListener, IXtextModelListener {

	private class CaretListenerImpl implements CaretListener {

		@Override
		public void caretMoved(CaretEvent event) {
			StyledText text = (StyledText) event.widget;
			int offset = event.caretOffset;
			selectLine(text, offset);
		}

	}

	private CaretListenerImpl caretListener;

	private EditDomain editDomain;

	private String entityName;

	@Inject
	private IQualifiedNameProvider nameProvider;

	private XtextResource resource;

	private SelectionSynchronizer synchronizer;

	protected GraphicalViewer viewer;

	private IXtextDocument xtextDocument;

	public AbstractGefView() {
		caretListener = new CaretListenerImpl();
		entityName = "";
	}

	/**
	 * Creates the GraphicalViewer on the specified <code>Composite</code>.
	 *
	 * @param parent
	 *            the parent composite
	 */
	protected void createGraphicalViewer(Composite parent) {
		GraphicalViewer viewer = new ScrollingGraphicalViewer();
		Control control = viewer.createControl(parent);
		control.setBackground(ColorConstants.white);

		editDomain = new EditDomain();
		viewer.setEditDomain(editDomain);
		editDomain.addViewer(viewer);

		viewer.setRootEditPart(new FreeformGraphicalRootEditPart() {
			@Override
			public DragTracker getDragTracker(Request req) {
				return new DirtyMarqueeTool();
			}
		});
		viewer.setEditPartFactory(getEditPartFactory());

		getSelectionSynchronizer().addViewer(viewer);
		getSite().setSelectionProvider(viewer);

		setGraphicalViewer(viewer);
	}

	@Override
	public void createPartControl(Composite parent) {
		createGraphicalViewer(parent);

		// add part listener
		getSite().getPage().addPartListener(this);

		// add resource change listener
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.addResourceChangeListener(this, POST_CHANGE);
	}

	@Override
	public void dispose() {
		// remove part listener
		getSite().getPage().removePartListener(this);

		// remove resource change listener
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.removeResourceChangeListener(this);
	}

	private String getClassName(Module module, int offset) {
		if (module == null) {
			return null;
		}

		ICompositeNode rootNode = NodeModelUtils.findActualNodeFor(module);
		ILeafNode leafNode = NodeModelUtils.findLeafNodeAtOffset(rootNode, offset);

		EObject eObject = NodeModelUtils.findActualSemanticObjectFor(leafNode);
		CxEntity entity = EcoreUtil2.getContainerOfType(eObject, CxEntity.class);
		if (entity != null) {
			QualifiedName name = nameProvider.getFullyQualifiedName(entity);
			if (name != null) {
				return name.toString();
			}
		}

		return null;
	}

	protected abstract EditPartFactory getEditPartFactory();

	protected String getEntityName() {
		return entityName;
	}

	protected SelectionSynchronizer getSelectionSynchronizer() {
		if (synchronizer == null) {
			synchronizer = new SelectionSynchronizer();
		}
		return synchronizer;
	}

	/**
	 * Installs cursor listener and selects current line.
	 *
	 * @param part
	 */
	private void initCursorListener(IWorkbenchPart part) {
		Control control = (Control) part.getAdapter(Control.class);
		if (control instanceof StyledText) {
			StyledText text = (StyledText) control;
			text.addCaretListener(caretListener);

			// initial selection to load the .ir file
			selectLine(text, text.getCaretOffset());
		}
	}

	protected abstract void irFileLoaded(Entity entity);

	protected void lineSelected(StyledText text, int offset) {
		// do nothing, may be overriden by subclasses
	}

	/**
	 * Loads the .ir file that corresponds to the given className
	 *
	 * @param className
	 */
	private void loadIrFile(String className) {
		if (className != null) {
			IFile cfFile = getFile(resource);
			IProject project = cfFile.getProject();
			IFile irFile = CoreUtil.getIrFile(project, className);
			if (irFile.exists()) {
				// use a new resource set each time so we make sure the resource is reloaded
				// we can probably do something more clever, e.g. by leveraging resourceChanged
				// will do if necessary
				Entity entity = getEObject(irFile, Entity.class);
				if (entity != null) {
					entityName = entity.getName();
					irFileLoaded(entity);
				}
			}
		}
	}

	@Override
	public void modelChanged(XtextResource resource) {
		this.resource = resource;
	}

	@Override
	public void partActivated(IWorkbenchPart part) {
		if (part instanceof XtextEditor) {
			XtextEditor xtextEditor = (XtextEditor) part;
			IEditorInput input = xtextEditor.getEditorInput();
			if (input instanceof IFileEditorInput) {
				IFileEditorInput fileInput = (IFileEditorInput) input;
				IFile file = fileInput.getFile();
				if (FILE_EXT_CX.equals(file.getFileExtension())) {
					// set xtextDocument field
					xtextDocument = xtextEditor.getDocument();

					// initialize resource field
					xtextDocument.readOnly(new IUnitOfWork.Void<XtextResource>() {
						@Override
						public void process(XtextResource state) throws Exception {
							AbstractGefView.this.resource = state;
						}
					});

					// add listener to track updates to model and to track caret
					xtextDocument.addModelListener(this);
					initCursorListener(part);
				}
			}
		}
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
		Control control = (Control) part.getAdapter(Control.class);
		if (control instanceof StyledText) {
			StyledText text = (StyledText) control;
			text.removeCaretListener(caretListener);
		}

		if (xtextDocument != null) {
			xtextDocument.removeModelListener(this);
			xtextDocument = null;
		}
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		if (resource == null || entityName == null) {
			return;
		}

		IResourceDelta delta = event.getDelta();

		IFile cfFile = getFile(resource);
		IProject project = cfFile.getProject();
		IFile irFile = CoreUtil.getIrFile(project, entityName);

		IResourceDelta irDelta = delta.findMember(irFile.getFullPath());
		if (irDelta != null) {
			Display display = PlatformUI.getWorkbench().getDisplay();
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					loadIrFile(entityName);
				}
			});
		}
	}

	/**
	 * Called when a line is selected in the current editor.
	 *
	 * @param text
	 *            text
	 * @param offset
	 *            offset
	 */
	private void selectLine(StyledText text, int offset) {
		if (resource != null && !resource.getContents().isEmpty()) {
			Module module = (Module) resource.getContents().get(0);

			String className = getClassName(module, offset);
			if (className == null) {
				entityName = null;
			} else if (!className.equals(entityName)) {
				// className is different from current actorName, must reload
				loadIrFile(className);
			}

			lineSelected(text, offset);
		}
	}

	@Override
	public void setFocus() {
	}

	private void setGraphicalViewer(GraphicalViewer viewer) {
		this.viewer = viewer;
	}

}
