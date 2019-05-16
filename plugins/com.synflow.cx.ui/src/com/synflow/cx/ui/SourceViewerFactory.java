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

import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.ui.editor.XtextSourceViewer;

/**
 * This class defines a factory that creates XtextSourceViewer instances with
 * different styles.
 *
 * @author Matthieu Wipliez
 *
 */
public class SourceViewerFactory implements XtextSourceViewer.Factory {

	public static Integer style;

	@Override
	public XtextSourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, IOverviewRuler overviewRuler,
			boolean showsAnnotationOverview, int styles) {
		if (style != null) {
			styles = style;
		}

		return new XtextSourceViewer(parent, ruler, overviewRuler,
				showsAnnotationOverview, styles);
	}

}
