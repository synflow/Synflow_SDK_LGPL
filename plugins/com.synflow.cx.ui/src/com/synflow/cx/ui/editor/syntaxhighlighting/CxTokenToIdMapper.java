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
package com.synflow.cx.ui.editor.syntaxhighlighting;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;

/**
 * This class maps tokens to attribute ids.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxTokenToIdMapper extends DefaultAntlrTokenToAttributeIdMapper {

	@Override
	protected String calculateId(String tokenName, int tokenType) {
		if ("RULE_TYPE_INT".equals(tokenName) || "RULE_TYPE_UINT".equals(tokenName)
				|| "RULE_BOOL".equals(tokenName)) {
			return DefaultHighlightingConfiguration.KEYWORD_ID;
		}

		return super.calculateId(tokenName, tokenType);
	}

}
