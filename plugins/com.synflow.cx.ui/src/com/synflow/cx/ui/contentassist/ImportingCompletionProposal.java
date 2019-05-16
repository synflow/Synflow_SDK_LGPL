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
package com.synflow.cx.ui.contentassist;

import static org.eclipse.xtext.nodemodel.util.NodeModelUtils.findNodesForFeature;

import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;

import com.synflow.core.SynflowCore;
import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.CxPackage.Literals;
import com.synflow.cx.cx.Import;
import com.synflow.cx.cx.Imported;

/**
 * This class defines a configurable completion proposal that also adds imports.
 *
 * @author Matthieu Wipliez
 *
 */
public class ImportingCompletionProposal extends ConfigurableCompletionProposal {

	private CxEntity entity;

	public ImportingCompletionProposal(String replacementString, int replacementOffset,
			int replacementLength, int cursorPosition, Image image, StyledString displayString,
			CxEntity entity) {
		super(replacementString, replacementOffset, replacementLength, cursorPosition, image,
				displayString, null, null);
		this.entity = entity;
	}

	private void addImport(IDocument document, int simpleNameLength) {
		List<INode> nodes = findNodesForFeature(entity, Literals.CX_ENTITY__IMPORTS);
		if (nodes.isEmpty()) {
			nodes = findNodesForFeature(entity, Literals.NAMED__NAME);
		}

		// find offset of entity name/last import statement
		int offset = 0;
		for (INode node : nodes) {
			offset = node.getOffset();
		}

		// adds import to the line after the end of the line
		String original = document.get();
		try {
			IRegion region = document.getLineInformationOfOffset(offset);
			offset = region.getOffset() + region.getLength();
			String importText = "\n\timport " + getReplacementString() + ";";
			document.replace(offset, 0, importText);

			// update cursor position
			// no need to update selection start
			// since selection depends solely on replacementOffset + cursorPosition
			setCursorPosition(simpleNameLength + importText.length());
		} catch (BadLocationException e) {
			SynflowCore.log(e);
			document.set(original);
		}
	}

	private void addSimpleName(IDocument document, int index) {
		String qualifiedName = getReplacementString();
		String simpleName = qualifiedName.substring(index + 1);

		setReplacementString(simpleName);
		super.apply(document);
		setReplacementString(qualifiedName);
	}

	@Override
	public void apply(final IDocument document) {
		int index = getReplacementString().lastIndexOf('.');
		if (index == -1) {
			// simple name
			super.apply(document);
		} else {
			addSimpleName(document, index);
			if (isImportNeeded()) {
				int length = getReplacementString().length() - index - 1;
				addImport(document, length);
			}
		}
	}

	/**
	 * Returns the name of the given object.
	 *
	 * @param to
	 *            an object
	 * @return a name
	 */
	private String getName(Imported imported) {
		List<INode> nodes = findNodesForFeature(imported, Literals.IMPORTED__TYPE);
		if (!nodes.isEmpty()) {
			INode node = nodes.get(0);
			return NodeModelUtils.getTokenText(node);
		}
		return null;
	}

	private boolean isImportNeeded() {
		for (Import oneImport : entity.getImports()) {
			for (Imported imported : oneImport.getImported()) {
				if (getReplacementString().equals(getName(imported))) {
					return false;
				}
			}
		}
		return true;
	}

}
