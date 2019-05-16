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
package com.synflow.cx.formatting2;

import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.eclipse.xtext.util.Arrays;
import org.eclipse.xtext.util.Pair;

import com.synflow.cx.services.CxGrammarAccess;
import com.synflow.cx.services.CxGrammarAccess.ExpressionUnaryElements;

/**
 * This class contains custom formatting description.
 *
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#formatting on how and when to
 * use it
 *
 * Also see {@link org.eclipse.xtext.xtext.XtextFormattingTokenSerializer} as an example
 */
public class CxFormatterOld extends AbstractDeclarativeFormatter {

	private void configureBracketsIndenting(FormattingConfig cfg) {
		CxGrammarAccess g = getGrammarAccess();

		setIndent(cfg, g.getBundleAccess().getLeftCurlyBracketKeyword_3(),
				g.getBundleAccess().getRightCurlyBracketKeyword_7());
		setIndent(cfg, g.getInstAnonTaskAccess().getLeftCurlyBracketKeyword_2(),
				g.getInstAnonTaskAccess().getRightCurlyBracketKeyword_6());
		setIndent(cfg, g.getNetworkAccess().getLeftCurlyBracketKeyword_2(),
				g.getNetworkAccess().getRightCurlyBracketKeyword_6());
		setIndent(cfg, g.getTaskAccess().getLeftCurlyBracketKeyword_2(),
				g.getTaskAccess().getRightCurlyBracketKeyword_6());

		setIndentOkOneLine(cfg, g.getMultiPortDeclAccess().getLeftCurlyBracketKeyword_2(),
				g.getMultiPortDeclAccess().getRightCurlyBracketKeyword_4());

		setIndent(cfg, g.getBlockAccess().getLeftCurlyBracketKeyword_1(),
				g.getBlockAccess().getRightCurlyBracketKeyword_3());
	}

	@Override
	protected void configureFormatting(FormattingConfig cfg) {
		CxGrammarAccess g = getGrammarAccess();

		cfg.setAutoLinewrap(120);

		// Comments
		cfg.setLinewrap(0, 1, 2).before(g.getSL_COMMENTRule());
		cfg.setLinewrap(0, 1, 2).before(g.getML_COMMENTRule());
		cfg.setLinewrap(0, 1, 1).after(g.getML_COMMENTRule());

		// punctuation: () [] , ; . ++ --
		configurePunctuation(cfg);

		// special semicolon package
		cfg.setLinewrap(2).after(g.getModuleAccess().getSemicolonKeyword_2());

		// port
		cfg.setLinewrap(1, 1, 2).before(g.getPortDeclRule());

		// statement if
		cfg.setLinewrap(0, 0, 2).before(g.getBranchAccess().getIfKeyword_0());

		// no line wrap after "for" semicolons
		cfg.setNoLinewrap().after(g.getStatementForAccess().getSemicolonKeyword_3());
		cfg.setNoLinewrap().after(g.getStatementForAccess().getSemicolonKeyword_5());

		// indentation of { } syntax
		configureBracketsIndenting(cfg);

		// rest: { } in properties, generic types, etc.
		configureMisc(cfg);
	}

	private void configureMisc(FormattingConfig cfg) {
		CxGrammarAccess g = getGrammarAccess();

		cfg.setNoSpace().around(g.getTypeDeclAccess().getLessThanSignKeyword_0_1_0_0());
		cfg.setNoSpace().around(g.getTypeDeclAccess().getLessThanSignKeyword_0_1_3_1_0());
		cfg.setNoSpace().around(g.getTypeDeclAccess().getLessThanSignKeyword_5_1_0());
		cfg.setNoSpace().around(g.getTypeDeclAccess().getLessThanSignKeyword_9_1_0());

		cfg.setNoSpace().before(g.getTypeDeclAccess().getGreaterThanSignKeyword_0_1_0_2());
		cfg.setNoSpace().before(g.getTypeDeclAccess().getGreaterThanSignKeyword_0_1_3_1_2());
		cfg.setNoSpace().before(g.getTypeDeclAccess().getGreaterThanSignKeyword_5_1_2());
		cfg.setNoSpace().before(g.getTypeDeclAccess().getGreaterThanSignKeyword_9_1_2());

		// unary operators
		ExpressionUnaryElements elt = g.getExpressionUnaryAccess();
		cfg.setNoSpace().after(elt.getOperatorAssignment_0_1_0_0());
		cfg.setNoSpace().after(elt.getOperatorAssignment_0_1_1_0());

		// { } with one-liner in properties
		setIndentOkOneLine(cfg, g.getObjAccess().getLeftCurlyBracketKeyword_1(),
				g.getObjAccess().getRightCurlyBracketKeyword_3());
	}

	/**
	 * Configure punctuation: () [] , ; . ++ --
	 *
	 * @param cfg
	 */
	private void configurePunctuation(FormattingConfig cfg) {
		CxGrammarAccess g = getGrammarAccess();

		// parentheses
		for (Pair<Keyword, Keyword> pair : g.findKeywordPairs("(", ")")) {
			cfg.setNoSpace().after(pair.getFirst());
			cfg.setNoSpace().before(pair.getSecond());
		}

		// left parenthesis
		Keyword[] exceptions = { g.getExpressionCastAccess().getLeftParenthesisKeyword_0(),
				g.getPrimaryAccess().getLeftParenthesisKeyword_1_0(),
				g.getBranchAccess().getLeftParenthesisKeyword_1(),
				g.getStatementForAccess().getLeftParenthesisKeyword_1(),
				g.getStatementWhileAccess().getLeftParenthesisKeyword_1() };

		for (Keyword leftParen : g.findKeywords("(")) {
			if (!Arrays.contains(exceptions, leftParen)) {
				cfg.setNoSpace().before(leftParen);
			}
		}

		// square brackets
		for (Pair<Keyword, Keyword> pair : g.findKeywordPairs("[", "]")) {
			if (pair.getFirst() != g.getArrayAccess().getLeftSquareBracketKeyword_1()) {
				cfg.setNoSpace().before(pair.getFirst());
			}
			cfg.setNoSpace().after(pair.getFirst());
			cfg.setNoSpace().before(pair.getSecond());
		}

		// comma
		for (Keyword comma : g.findKeywords(",")) {
			cfg.setNoSpace().before(comma);
			cfg.setLinewrap(0, 1, 1).after(comma);
		}

		// colon
		cfg.setNoSpace().before(g.getPairAccess().getColonKeyword_1());
		cfg.setNoSpace().before(g.getStatementLabeledAccess().getColonKeyword_1());

		// semicolon
		for (Keyword semicolon : g.findKeywords(";")) {
			cfg.setNoSpace().before(semicolon);
			cfg.setLinewrap(0, 1, 2).after(semicolon);
		}

		// expression index
		for (Keyword kwd : g.findKeywords(".", "++", "--")) {
			cfg.setNoSpace().around(kwd);
		}
	}

	@Override
	protected CxGrammarAccess getGrammarAccess() {
		return (CxGrammarAccess) super.getGrammarAccess();
	}

	private void setIndent(FormattingConfig cfg, Keyword left, Keyword right) {
		cfg.setIndentation(left, right);
		cfg.setLinewrap(1).after(left);
		cfg.setLinewrap(1).before(right);
		cfg.setLinewrap(0, 1, 2).after(right);
	}

	private void setIndentOkOneLine(FormattingConfig cfg, Keyword left, Keyword right) {
		cfg.setIndentation(left, right);

		cfg.setLinewrap(0, 1, 1).after(left);
		cfg.setLinewrap(0, 1, 1).before(right);
		cfg.setLinewrap(0, 1, 2).after(right);
	}

}
