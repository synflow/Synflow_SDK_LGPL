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
package com.synflow.cx.debug;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.debug.IStratumBreakpointSupport;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;

import com.synflow.cx.cx.Statement;

/**
 * This class implements breakpoint support for Cx based on Xbase's breakpoint
 * support class.
 *
 * @author Sven Efftinge - Initial contribution and API
 * @author Matthieu Wipliez support for Cx
 */
@SuppressWarnings("restriction")
public class CxStratumBreakpointSupport implements IStratumBreakpointSupport {

	public boolean isValidLineForBreakPoint(XtextResource resource, int line) {
		IParseResult parseResult = resource.getParseResult();
		if (parseResult == null)
			return false;
		ICompositeNode node = parseResult.getRootNode();
		return isValidLineForBreakpoint(node, line);
	}

	protected boolean isValidLineForBreakpoint(ICompositeNode node, int line) {
		for (INode n : node.getChildren()) {
			if (n.getStartLine() <= line && n.getEndLine() >= line) {
				EObject eObject = n.getSemanticElement();
				if (eObject instanceof Statement) {
					return true;
				}
				if (n instanceof ICompositeNode
						&& isValidLineForBreakpoint((ICompositeNode) n, line)) {
					return true;
				}
			}
			if (n.getStartLine() > line) {
				return false;
			}
		}
		return false;
	}

}
