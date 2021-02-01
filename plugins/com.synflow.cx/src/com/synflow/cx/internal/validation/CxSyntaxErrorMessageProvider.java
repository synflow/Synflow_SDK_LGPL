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
package com.synflow.cx.internal.validation;

import static com.synflow.cx.validation.IssueCodes.SYNTAX_ERROR_ARRAY_BRACE;
import static com.synflow.cx.validation.IssueCodes.SYNTAX_ERROR_SINGLE_QUOTE;

import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.parser.antlr.SyntaxErrorMessageProvider;

/**
 * This class provides custom messages for syntax error messages.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxSyntaxErrorMessageProvider extends SyntaxErrorMessageProvider {

	@Override
	public SyntaxErrorMessage getSyntaxErrorMessage(IParserErrorContext context) {
		RecognitionException exc = context.getRecognitionException();
		if (exc == null) {
			// lexer error
			String msg = context.getDefaultMessage();
			if (msg.startsWith("mismatched character") && msg.endsWith("expecting '''")) {
				return new SyntaxErrorMessage(
						"Syntax error: expected one character surrounded by single quotes, for strings use double quotes",
						SYNTAX_ERROR_SINGLE_QUOTE);
			}
		} else if (exc instanceof NoViableAltException) {
			INode node = context.getCurrentNode();
			if (node != null) {
				EObject element = node.getGrammarElement();
				if (element != null && element instanceof RuleCall) {
					RuleCall call = (RuleCall) element;
					if ("Value".equals(call.getRule().getName())) {
						return new SyntaxErrorMessage(
								"Syntax error: expected curly braces { } for array",
								SYNTAX_ERROR_ARRAY_BRACE);
					}
				}
			}
		}

		return super.getSyntaxErrorMessage(context);
	}

}
