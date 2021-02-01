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
package com.synflow.cx.linking;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.impl.LinkingDiagnosticMessageProvider;

import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.VarRef;

/**
 * This class provides custom messages for linking diagnostics.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxLinkingDiagnosticMessageProvider extends LinkingDiagnosticMessageProvider {

	@Override
	public DiagnosticMessage getUnresolvedProxyMessage(final ILinkingDiagnosticContext context) {
		EObject element = context.getContext();
		String link = context.getLinkText();
		if (element instanceof CxEntity) {
			return new DiagnosticMessage(link + " cannot be imported", Severity.ERROR, null);
		} else if (element instanceof VarRef) {
			return new DiagnosticMessage(link + " cannot be resolved", Severity.ERROR, null);
		}
		return super.getUnresolvedProxyMessage(context);
	}

}
