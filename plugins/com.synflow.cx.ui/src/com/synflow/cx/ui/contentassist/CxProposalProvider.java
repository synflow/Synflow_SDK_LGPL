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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.synflow.cx.cx.CxPackage;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Network;
import com.synflow.cx.cx.StatementWrite;

/**
 * see http://www.eclipse.org/Xtext/documentation.html#contentAssist on how to customize content
 * assistant
 */
public class CxProposalProvider extends AbstractCxProposalProvider {

	@Override
	public void complete_ValidID(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		if (model instanceof StatementWrite) {
			StatementWrite stmt = (StatementWrite) model;
			IScope scope = getScopeProvider().getScope(stmt.getPort(),
					CxPackage.Literals.VAR_REF__OBJECTS);
			for (IEObjectDescription candidate : scope.getAllElements()) {
				String proposal = getQualifiedNameConverter().toString(candidate.getName());
				StyledString displayString = getStyledDisplayString(candidate);
				EObject objectOrProxy = candidate.getEObjectOrProxy();
				Image image = getImage(objectOrProxy);
				acceptor.accept(createCompletionProposal(proposal, displayString, image, context));
			}
		} else {
			super.complete_ValidID(model, ruleCall, context, acceptor);
		}
	}

	@Override
	public void completeImported_Type(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		final URI uriRoot = EcoreUtil.getURI(EcoreUtil.getRootContainer(model));
		lookupCrossReference(((CrossReference) assignment.getTerminal()), context, acceptor,
				new Predicate<IEObjectDescription>() {
					@Override
					public boolean apply(IEObjectDescription candidate) {
						URI uriCandidate = candidate.getEObjectURI();
						return !uriRoot.equals(uriCandidate);
					}
				});
	}

	@Override
	public void completeKeyword(Keyword keyword, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		if (isKeywordWorthyToPropose(keyword)) {
			super.completeKeyword(keyword, context, acceptor);
		}
	}

	@Override
	protected ConfigurableCompletionProposal doCreateProposal(String proposal,
			StyledString displayString, Image image, int priority, ContentAssistContext context) {
		int replacementOffset = context.getReplaceRegion().getOffset();
		int replacementLength = context.getReplaceRegion().getLength();

		ConfigurableCompletionProposal result;
		if (context.getCurrentModel() instanceof Inst) {
			Network network = (Network) context.getCurrentModel().eContainer();
			result = new ImportingCompletionProposal(proposal, replacementOffset, replacementLength,
					proposal.length(), image, displayString, network);
		} else {
			result = doCreateProposal(proposal, displayString, image, replacementOffset,
					replacementLength);
		}
		result.setPriority(priority);
		result.setMatcher(context.getMatcher());
		result.setReplaceContextLength(context.getReplaceContextLength());
		return result;
	}

	@Override
	protected ConfigurableCompletionProposal doCreateProposal(String proposal,
			StyledString displayString, Image image, int replacementOffset, int replacementLength) {
		return new ConfigurableCompletionProposal(proposal, replacementOffset, replacementLength,
				proposal.length(), image, displayString, null, null);
	}

	@Override
	protected Function<IEObjectDescription, ICompletionProposal> getProposalFactory(String ruleName,
			ContentAssistContext contentAssistContext) {
		return new DefaultProposalCreator(contentAssistContext, "FullyQualifiedName",
				getQualifiedNameConverter());
	}

	/**
	 * Returns <code>true</code> if the given keyword should be proposed to the user. Taken from
	 * Xbase code.
	 *
	 * @param keyword
	 * @return
	 */
	private boolean isKeywordWorthyToPropose(Keyword keyword) {
		return keyword.getValue().length() > 1 && Character.isLetter(keyword.getValue().charAt(0));
	}

}
