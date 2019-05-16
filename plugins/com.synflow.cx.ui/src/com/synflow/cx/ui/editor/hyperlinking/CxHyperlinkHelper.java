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
package com.synflow.cx.ui.editor.hyperlinking;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor;

import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.Variable;

/**
 * This class defines a custom hyperlink helper for Cx.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxHyperlinkHelper extends HyperlinkHelper {

	@Override
	protected void createHyperlinksTo(XtextResource resource, INode node, EObject target,
			IHyperlinkAcceptor acceptor) {
		if (target instanceof Variable && target.eContainer() instanceof ExpressionVariable) {
			return;
		}

		super.createHyperlinksTo(resource, node, target, acceptor);
	}

}
