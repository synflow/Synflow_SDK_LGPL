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
package com.synflow.cx.ui.labeling;

import static com.synflow.core.ICoreConstants.FILE_EXT_CX;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.xtext.ui.label.DeclarativeLabelProvider;

import com.google.inject.Inject;
import com.synflow.core.SynflowCore;
import com.synflow.core.SynflowNature;
import com.synflow.core.layout.ITreeElement;
import com.synflow.core.layout.Package;
import com.synflow.core.layout.SourceFolder;

/**
 * This class defines a declarative label provider that extends {@link CxLabelProvider} with images
 * for projects, Cx files, Java source folders and packages, and text for resources and Java
 * elements.
 *
 * @author Matthieu Wipliez
 *
 */
public class NavigatorDeclarativeLabelProvider extends DeclarativeLabelProvider {

	@Inject
	public NavigatorDeclarativeLabelProvider(CxLabelProvider delegate) {
		super(delegate);
	}

	public String image(IFile file) {
		if (FILE_EXT_CX.equals(file.getFileExtension())) {
			return "cx_obj.gif";
		}
		return null;
	}

	public String image(IProject project) {
		try {
			if (project.isAccessible() && project.hasNature(SynflowNature.NATURE_ID)) {
				return "sfprj_obj.gif";
			}
		} catch (CoreException e) {
			SynflowCore.log(e);
		}
		return null;
	}

	public String image(Package package_) {
		if (package_.isEmpty()) {
			return "empty_pack_obj.gif";
		}
		return "package_obj.gif";
	}

	public String image(SourceFolder folder) {
		return "packagefolder_obj.gif";
	}

	public String text(IResource resource) {
		return resource.getName();
	}

	public String text(ITreeElement element) {
		return element.getName();
	}

}
