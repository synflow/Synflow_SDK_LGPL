/*******************************************************************************
 * Copyright (c) 2014-2015 Synflow SAS.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthieu Wipliez - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.synflow.ngDesign.ui.internal.navigator.actions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.synflow.core.layout.ITreeElement;

/**
 * This class defines utility methods for selection.
 * 
 * @author Matthieu Wipliez
 *
 */
public class SelectionUtil {

	/**
	 * Returns <code>true</code> if the given selection contains a source folder.
	 * 
	 * @param selection
	 *            a structured selection
	 * @return a boolean indicating whether the selection contains a source folder
	 */
	public static boolean containsSourceFolder(IStructuredSelection selection) {
		for (Iterator<?> e = selection.iterator(); e.hasNext();) {
			Object next = e.next();
			if (next instanceof ITreeElement) {
				ITreeElement element = (ITreeElement) next;
				if (element.isSourceFolder()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Removes resources that are adapted from packages/source folders.
	 * 
	 * @param selectedResources
	 *            list of selected resources
	 * @param selection
	 *            structured selection
	 */
	public static void discardTreeElements(List<? extends IResource> selectedResources,
			IStructuredSelection selection) {
		for (Iterator<?> e = selection.iterator(); e.hasNext();) {
			Object next = e.next();
			if (next instanceof ITreeElement) {
				ITreeElement element = (ITreeElement) next;
				selectedResources.remove(element.getAdapter(IResource.class));
			}
		}
	}

}
