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

import static com.synflow.core.ICoreConstants.FILE_EXT_CX;
import static com.synflow.models.util.EcoreHelper.getEObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.IPipelinedTreeContentProvider2;
import org.eclipse.ui.navigator.PipelinedShapeModification;
import org.eclipse.ui.navigator.PipelinedViewerUpdate;

import com.synflow.core.SynflowCore;
import com.synflow.core.layout.ITreeElement;
import com.synflow.core.layout.Package;
import com.synflow.core.layout.ProjectLayout;
import com.synflow.core.layout.SourceFolder;
import com.synflow.cx.cx.Bundle;
import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Module;
import com.synflow.cx.cx.Network;
import com.synflow.cx.cx.Task;
import com.synflow.cx.cx.VarDecl;

/**
 * This class defines a Cx content provider. Many methods are copied from JDT's
 * org.eclipse.jdt.internal.ui.navigator.JavaNavigatorContentProvider and are Copyright (c) IBM
 * Corporation and others (see above).
 *
 * @author Matthieu Wipliez
 *
 */
public class CxContentProvider
		implements IPipelinedTreeContentProvider2, IResourceChangeListener, ITreeContentProvider {

	/**
	 * This class defines a resource delta visitor that visits a delta and if it contains something
	 * in the source folder it refreshes it.
	 *
	 * @author Matthieu Wipliez
	 *
	 */
	private static class RefreshComputerVisitor implements IResourceDeltaVisitor {

		private ITreeElement toRefresh;

		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource resource = delta.getResource();
			ITreeElement element = ProjectLayout.getTreeElement(resource);
			if (element != null) {
				toRefresh = element;
				return false;
			}

			return true;
		}

	}

	private ColumnViewer viewer;

	/**
	 * Converts the shape modification to use tree elements.
	 *
	 * @param modification
	 *            the shape modification to convert
	 * @return returns true if the conversion took place
	 */
	@SuppressWarnings("unchecked")
	private boolean convertModification(PipelinedShapeModification modification) {
		Object parent = modification.getParent();
		if (parent instanceof IContainer) {
			return convertToTreeElements(modification.getChildren());
		}
		return false;
	}

	/**
	 * Converts the shape modification to use Java elements.
	 *
	 *
	 * @param currentChildren
	 *            The set of current children that would be contributed or refreshed in the viewer.
	 * @return returns true if the conversion took place
	 */
	private boolean convertToTreeElements(Set<Object> currentChildren) {
		LinkedHashSet<Object> convertedChildren = new LinkedHashSet<Object>();
		for (Iterator<Object> childrenItr = currentChildren.iterator(); childrenItr.hasNext();) {
			Object child = childrenItr.next();
			// only IFolders need to be converted, either to source folder or to package
			if (child instanceof IFolder) {
				ITreeElement element = ProjectLayout.getTreeElement((IFolder) child);
				if (element != null) {
					childrenItr.remove();
					convertedChildren.add(element);
				}
			}
		}

		if (!convertedChildren.isEmpty()) {
			currentChildren.addAll(convertedChildren);
			return true;
		}
		return false;
	}

	/**
	 * Adapted from the Common Navigator Content Provider
	 *
	 * @param javaElements
	 *            the java elements
	 * @param proposedChildren
	 *            the proposed children
	 */
	private void customize(Object[] javaElements, Set<Object> proposedChildren) {
		List<?> elementList = Arrays.asList(javaElements);
		for (Iterator<?> iter = proposedChildren.iterator(); iter.hasNext();) {
			Object element = iter.next();
			IResource resource = null;
			if (element instanceof IResource) {
				resource = (IResource) element;
			} else if (element instanceof IAdaptable) {
				resource = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
			}
			if (resource != null) {
				int i = elementList.indexOf(resource);
				if (i >= 0) {
					javaElements[i] = null;
				}
			}
		}
		for (int i = 0; i < javaElements.length; i++) {
			Object element = javaElements[i];
			if (element instanceof ITreeElement) {
				ITreeElement cElement = (ITreeElement) element;
				IResource resource = cElement.getResource();
				if (resource != null) {
					proposedChildren.remove(resource);
				}
				proposedChildren.add(element);
			} else if (element != null) {
				proposedChildren.add(element);
			}
		}
	}

	@Override
	public void dispose() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.removeResourceChangeListener(this);
	}

	private Object[] getChildren(CxEntity entity) {
		List<Object> children = new ArrayList<>();
		children.addAll(entity.getTypes());

		if (entity instanceof Bundle) {
			Bundle bundle = (Bundle) entity;
			for (VarDecl decl : bundle.getDecls()) {
				children.addAll(decl.getVariables());
			}
		} else if (entity instanceof Task) {
			Task task = (Task) entity;
			for (VarDecl decl : task.getDecls()) {
				children.addAll(decl.getVariables());
			}
		} else if (entity instanceof Network) {
			Network network = (Network) entity;
			children.addAll(network.getInstances());
		}

		return children.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IProject) {
			IProject project = (IProject) parentElement;
			return ProjectLayout.getChildren(project);
		}

		if (parentElement instanceof SourceFolder) {
			SourceFolder root = (SourceFolder) parentElement;
			return root.getPackages();
		}

		if (parentElement instanceof Package) {
			Package package_ = (Package) parentElement;
			return package_.getFiles();
		}

		if (parentElement instanceof IFolder) {
			IFolder folder = (IFolder) parentElement;
			try {
				return folder.members();
			} catch (CoreException e) {
				SynflowCore.log(e);
			}
		} else if (parentElement instanceof IFile) {
			IFile file = (IFile) parentElement;
			if (FILE_EXT_CX.equals(file.getFileExtension())) {
				Module module = getEObject(file, Module.class);
				if (module != null) {
					return getChildren(module);
				}
			}
		} else if (parentElement instanceof Module) {
			Module module = (Module) parentElement;
			return module.getEntities().toArray();
		} else if (parentElement instanceof CxEntity) {
			return getChildren((CxEntity) parentElement);
		} else if (parentElement instanceof Inst) {
			Inst inst = (Inst) parentElement;
			return getChildren(inst.getTask() == null ? inst.getEntity() : inst.getTask());
		}
		return new Object[0];
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof IWorkspaceRoot) {
			IWorkspaceRoot root = (IWorkspaceRoot) inputElement;
			return root.getProjects();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		// copied and modified from JDT
		if (element == null) {
			return false;
		}

		// try to map resources to the containing package fragment
		if (element instanceof IResource) {
			IResource parent = ((IResource) element).getParent();
			if (parent == null) {
				return null;
			}

			ITreeElement tree = ProjectLayout.getTreeElement(parent);
			if (tree != null) {
				return tree;
			}
			return parent;
		} else if (element instanceof Package) {
			Package package_ = (Package) element;
			return package_.getSourceFolder();
		} else if (element instanceof SourceFolder) {
			SourceFolder folder = (SourceFolder) element;
			return folder.getProject();
		}

		return null;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getPipelinedChildren(Object parent, Set currentChildren) {
		customize(getChildren(parent), currentChildren);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getPipelinedElements(Object input, Set currentElements) {
		customize(getElements(input), currentElements);
	}

	@Override
	public Object getPipelinedParent(Object object, Object suggestedParent) {
		return getParent(object);
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IProject) {
			return ((IProject) element).isAccessible();
		}

		return getChildren(element).length > 0;
	}

	@Override
	public boolean hasPipelinedChildren(Object element, boolean currentHasChildren) {
		return hasChildren(element);
	}

	@Override
	public void init(ICommonContentExtensionSite aConfig) {
		IMemento memento = aConfig.getMemento();
		restoreState(memento);

		// nothing else to do at the moment
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (ColumnViewer) viewer;

		if (newInput instanceof IWorkspaceRoot) {
			IWorkspaceRoot root = (IWorkspaceRoot) newInput;
			root.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
		}
	}

	@Override
	public PipelinedShapeModification interceptAdd(PipelinedShapeModification modification) {
		convertModification(modification);
		return modification;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean interceptRefresh(PipelinedViewerUpdate refreshSynchronization) {
		return convertToTreeElements(refreshSynchronization.getRefreshTargets());
	}

	@Override
	public PipelinedShapeModification interceptRemove(PipelinedShapeModification modification) {
		convertModification(modification);
		return modification;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean interceptUpdate(PipelinedViewerUpdate updateSynchronization) {
		return convertToTreeElements(updateSynchronization.getRefreshTargets());
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		try {
			final RefreshComputerVisitor visitor = new RefreshComputerVisitor();
			delta.accept(visitor);
			if (visitor.toRefresh != null) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						viewer.refresh(visitor.toRefresh);
					}
				});
			}
		} catch (CoreException e) {
			SynflowCore.log(e);
		}
	}

	@Override
	public void restoreState(IMemento aMemento) {
		// nothing to do here
	}

	@Override
	public void saveState(IMemento aMemento) {
		// nothing to do here
	}

}
