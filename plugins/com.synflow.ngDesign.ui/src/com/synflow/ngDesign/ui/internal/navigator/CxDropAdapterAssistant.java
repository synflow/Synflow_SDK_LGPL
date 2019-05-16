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

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.CopyFilesAndFoldersOperation;
import org.eclipse.ui.actions.MoveFilesAndFoldersOperation;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;
import org.eclipse.ui.part.ResourceTransfer;

import com.synflow.core.layout.ITreeElement;

/**
 * This class defines a very simple drop adapter assistant. It is loosely based on
 * {@link org.eclipse.ui.navigator.resources.ResourceDropAdapterAssistant}, but kept as simple as
 * possible.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxDropAdapterAssistant extends CommonDropAdapterAssistant {

	private static final IResource[] NO_RESOURCES = new IResource[0];

	/**
	 * Returns the actual target of the drop, given the resource under the mouse. If the mouse
	 * target is a file, then the drop actually occurs in its parent. If the drop location is before
	 * or after the mouse target and feedback is enabled, the target is also the parent.
	 */
	private IContainer getActualTarget(Object target) {
		IResource resource = getResource(target);
		if (resource.getType() == IResource.FILE) {
			return resource.getParent();
		}
		return (IContainer) resource;
	}

	private IResource getResource(Object object) {
		if (object instanceof IResource) {
			return (IResource) object;
		} else if (object instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) object;
			return (IResource) adaptable.getAdapter(IResource.class);
		} else {
			return null;
		}
	}

	/**
	 * Returns the resource selection from the LocalSelectionTransfer.
	 *
	 * @return the resource selection from the LocalSelectionTransfer
	 */
	private IResource[] getSelectedResources() {
		ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
		if (selection instanceof IStructuredSelection) {
			return getSelectedResources((IStructuredSelection) selection);
		}
		return NO_RESOURCES;
	}

	/**
	 * Returns the resource selection from the LocalSelectionTransfer.
	 *
	 * @return the resource selection from the LocalSelectionTransfer
	 */
	private IResource[] getSelectedResources(IStructuredSelection selection) {
		ArrayList<IResource> selectedResources = new ArrayList<IResource>();
		for (Iterator<?> it = selection.iterator(); it.hasNext();) {
			Object object = it.next();
			IResource resource = getResource(object);
			if (resource != null) {
				selectedResources.add(resource);
			}
		}
		return selectedResources.toArray(new IResource[selectedResources.size()]);
	}

	@Override
	public IStatus handleDrop(CommonDropAdapter aDropAdapter, DropTargetEvent aDropTargetEvent,
			Object aTarget) {
		// alwaysOverwrite = false;
		if (aTarget == null || aDropTargetEvent.data == null) {
			return Status.CANCEL_STATUS;
		}

		IStatus status = null;
		IResource[] resources = null;
		TransferData currentTransfer = aDropAdapter.getCurrentTransfer();
		if (LocalSelectionTransfer.getTransfer().isSupportedType(currentTransfer)) {
			resources = getSelectedResources();
		} else if (ResourceTransfer.getInstance().isSupportedType(currentTransfer)) {
			resources = (IResource[]) aDropTargetEvent.data;
		}

		if (FileTransfer.getInstance().isSupportedType(currentTransfer)) {
			status = performFileDrop(aDropAdapter, aDropTargetEvent.data);
		} else if (resources != null && resources.length > 0) {
			if ((aDropAdapter.getCurrentOperation() == DND.DROP_COPY)
					|| (aDropAdapter.getCurrentOperation() == DND.DROP_LINK)) {
				status = performResourceCopy(aDropAdapter, getShell(), resources);
			} else {
				status = performResourceMove(aDropAdapter, getShell(), resources);
			}
		}

		IContainer target = getActualTarget(aTarget);
		if (target != null && target.isAccessible()) {
			try {
				target.refreshLocal(IResource.DEPTH_ONE, null);
			} catch (CoreException e) {
			}
		}
		return status;
	}

	/**
	 * Performs a drop using the FileTransfer transfer type.
	 */
	private IStatus performFileDrop(final CommonDropAdapter anAdapter, Object data) {
		return Status.CANCEL_STATUS;
	}

	/**
	 * Performs a resource copy
	 */
	private IStatus performResourceCopy(CommonDropAdapter dropAdapter, Shell shell,
			IResource[] sources) {
		CopyFilesAndFoldersOperation operation = new CopyFilesAndFoldersOperation(shell);
		IContainer target = getActualTarget(dropAdapter.getCurrentTarget());
		operation.copyResources(sources, target);
		return Status.OK_STATUS;
	}

	/**
	 * Performs a resource move
	 */
	private IStatus performResourceMove(CommonDropAdapter dropAdapter, Shell shell,
			IResource[] sources) {
		MoveFilesAndFoldersOperation operation = new MoveFilesAndFoldersOperation(shell);
		IContainer target = getActualTarget(dropAdapter.getCurrentTarget());
		operation.copyResources(sources, target);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus validateDrop(Object target, int operation, TransferData transferType) {
		IResource resource = getResource(target);
		if (resource == null) {
			return Status.CANCEL_STATUS;
		}

		// cannot drop directly into source folders (must be package)
		if (target instanceof ITreeElement) {
			ITreeElement element = (ITreeElement) target;
			if (element.isSourceFolder()) {
				return Status.CANCEL_STATUS;
			}
		}

		if (LocalSelectionTransfer.getTransfer().isSupportedType(transferType)) {
			IResource[] selectedResources = getSelectedResources();
			if (selectedResources.length == 0) {
				return Status.CANCEL_STATUS;
			}

			for (IResource selectedResource : selectedResources) {
				if (selectedResource.getType() != IResource.FILE) {
					return Status.CANCEL_STATUS;
				}
			}

			return Status.OK_STATUS;
		} else {
			return Status.CANCEL_STATUS;
		}
	}

}
