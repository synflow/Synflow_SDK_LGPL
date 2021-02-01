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
package com.synflow.ngDesign.ui.internal.navigator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

/**
 * This class defines a simple sorter for our Project Explorer view.
 *
 * @author Matthieu Wipliez
 *
 */
public class ProjectExplorerSorter extends ViewerComparator {

	private final CxComparator fComparator;

	/**
	 * Constructor.
	 */
	public ProjectExplorerSorter() {
		super(null); // delay initialization of collator
		fComparator = new CxComparator();
	}

	@Override
	public int category(Object element) {
		return fComparator.category(element);
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		return fComparator.compare(viewer, e1, e2);
	}

}
