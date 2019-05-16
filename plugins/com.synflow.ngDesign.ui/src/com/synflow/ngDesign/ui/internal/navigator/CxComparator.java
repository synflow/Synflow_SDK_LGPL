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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

import com.synflow.core.layout.ITreeElement;
import com.synflow.core.layout.Package;
import com.synflow.core.layout.SourceFolder;
import com.synflow.cx.cx.CxEntity;

/**
 * This class defines a simple sorter for our Project Explorer view.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxComparator extends ViewerComparator {

	private static final int OTHERS = 51;

	private static final int PACKAGEFRAGMENT = 3;

	private static final int PACKAGEFRAGMENTROOTS = 2;

	private static final int PROJECTS = 1;

	private static final int RESOURCEFOLDERS = 7;

	private static final int RESOURCES = 8;

	@Override
	public int category(Object element) {
		if (element instanceof IProject) {
			return PROJECTS;
		} else if (element instanceof SourceFolder) {
			return PACKAGEFRAGMENTROOTS;
		} else if (element instanceof Package) {
			return PACKAGEFRAGMENT;
		} else if (element instanceof IContainer) {
			return RESOURCEFOLDERS;
		} else if (element instanceof IFile) {
			return RESOURCES;
		}
		return OTHERS;
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		int cat1 = category(e1);
		int cat2 = category(e2);

		if (cat1 != cat2) {
			return cat1 - cat2;
		}

		String name1 = getElementName(e1);
		String name2 = getElementName(e2);

		// use the comparator to compare the strings
		int result = getComparator().compare(name1, name2);
		return result;
	}

	private String getElementName(Object element) {
		if (element instanceof CxEntity) {
			return ((CxEntity) element).getName();
		} else if (element instanceof ITreeElement) {
			return ((ITreeElement) element).getName();
		} else {
			return element.toString();
		}
	}

}
