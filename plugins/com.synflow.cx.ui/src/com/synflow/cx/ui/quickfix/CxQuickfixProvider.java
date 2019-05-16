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
package com.synflow.cx.ui.quickfix;

import static com.synflow.cx.validation.IssueCodes.ERR_ENTRY_FUNCTION_BAD_TYPE;
import static com.synflow.cx.validation.IssueCodes.ERR_UNRESOLVED_FUNCTION;
import static com.synflow.cx.validation.IssueCodes.SHOULD_REPLACE_NAME;
import static com.synflow.cx.validation.IssueCodes.SYNTAX_ERROR_SINGLE_QUOTE;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

import com.synflow.cx.cx.Variable;

/**
 * This class provides quick fixes for several issues.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxQuickfixProvider extends DefaultQuickfixProvider {

	@Fix(ERR_ENTRY_FUNCTION_BAD_TYPE)
	public void changeTypeToVoid(final Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Change type to void", "Change function type to void", null,
				new ISemanticModification() {

					@Override
					public void apply(EObject element, IModificationContext context)
							throws Exception {
						Variable function = (Variable) element;
						INode nodeType = NodeModelUtils.findActualNodeFor(function.getType());

						IXtextDocument document = context.getXtextDocument();
						document.replace(nodeType.getOffset(), nodeType.getLength(), "void");
					}

				});
	}

	@Fix(SYNTAX_ERROR_SINGLE_QUOTE)
	public void fixSyntaxErrorSingleQuote(final Issue issue, IssueResolutionAcceptor acceptor) {
		String description = "Replaces single quotes by double quotes";

		acceptor.accept(issue, "Replace quotes", description, null, new IModification() {
			public void apply(IModificationContext context) throws BadLocationException {
				IXtextDocument document = context.getXtextDocument();
				IRegion region = document.getLineInformationOfOffset(issue.getOffset());
				int offset = issue.getOffset();
				int offsetInLine = offset - region.getOffset();
				int length = region.getLength() - offsetInLine;

				String line = document.get(offset, length);
				char[] lineChars = new char[line.length()];
				line.getChars(0, line.length(), lineChars, 0);
				int index = line.indexOf('\'');
				lineChars[index] = '"';
				lineChars[line.indexOf('\'', index + 1)] = '"';

				document.replace(offset, length, new String(lineChars));
			}
		});
	}

	@Fix(ERR_UNRESOLVED_FUNCTION)
	public void fixUnresolvedFunction(final Issue issue, IssueResolutionAcceptor acceptor) {
		final String funcName = issue.getData()[0];
		acceptor.accept(issue, "Create function " + funcName + "()", "Create function " + funcName
				+ "()", null, new ISemanticModification() {
			public void apply(final EObject element, IModificationContext context)
					throws BadLocationException {
				Variable module = EcoreUtil2.getContainerOfType(element, Variable.class);
				ICompositeNode node = NodeModelUtils.getNode(module);
				int offset = node.getOffset() + node.getLength();
				StringBuilder builder = new StringBuilder("\n\nvoid ");
				String newFunc = builder.append(funcName).append("() {\n\t// TODO\n}").toString();
				context.getXtextDocument().replace(offset, 0, newFunc);
			}
		});
		createLinkingIssueResolutions(issue, acceptor);
	}

	@Fix(SHOULD_REPLACE_NAME)
	public void replaceName(final Issue issue, IssueResolutionAcceptor acceptor) {
		final String name = issue.getData()[0];
		final String replacement = issue.getData()[1];
		final int offset = issue.getOffset();
		final int length = issue.getLength();

		String description;
		if (issue.getSeverity() == Severity.ERROR) {
			description = "Updates the name to correct the error.";
		} else {
			description = "Updates the name to respect the recommended coding style.";
		}

		acceptor.accept(issue, "Replace '" + name + "' by '" + replacement + "'", description,
				null, new IModification() {
					public void apply(IModificationContext context) throws BadLocationException {
						IXtextDocument document = context.getXtextDocument();
						document.replace(offset, length, replacement);
					}
				});
	}

}
