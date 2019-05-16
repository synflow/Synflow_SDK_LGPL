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
package com.synflow.cx.ui.outline;

import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.SortOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;

import com.synflow.cx.cx.CxPackage;

/**
 * This class provides a comparator for entries of the outline tree.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxOutlineComparator extends SortOutlineContribution.DefaultComparator {

	@Override
	public int getCategory(IOutlineNode node) {
		if (node instanceof EObjectNode) {
			EObjectNode objNode = (EObjectNode) node;
			switch (objNode.getEClass().getClassifierID()) {
			case CxPackage.MODULE:
				return Integer.MIN_VALUE;
			case CxPackage.VAR_DECL:
				return -10;
			default:
				return 0;
			}
		}

		return 0;
	}

}
