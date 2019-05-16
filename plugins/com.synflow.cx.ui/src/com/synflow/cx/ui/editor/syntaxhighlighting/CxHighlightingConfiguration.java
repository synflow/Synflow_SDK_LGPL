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
package com.synflow.cx.ui.editor.syntaxhighlighting;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

/**
 * This class extends the default highlighting configuration for Cx.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxHighlightingConfiguration extends DefaultHighlightingConfiguration {

	/**
	 * style for deprecated functions
	 */
	public static final String DEPRECATED_ID = "deprecated";

	/**
	 * style for special functions 'setup' and 'loop'
	 */
	public static final String SPECIAL_ID = "special";

	/**
	 * style for type aliases defined by typedef
	 */
	public static final String TYPE_ID = "type";

	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		acceptor.acceptDefaultHighlighting(DEPRECATED_ID, "Deprecated", deprecatedTextStyle());
		acceptor.acceptDefaultHighlighting(SPECIAL_ID, "Special function", entryTextStyle());
		acceptor.acceptDefaultHighlighting(TYPE_ID, "Type alias", typeTextStyle());
	}

	public TextStyle deprecatedTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setStyle(TextAttribute.STRIKETHROUGH);
		return textStyle;
	}

	public TextStyle entryTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setStyle(SWT.BOLD | TextAttribute.UNDERLINE);
		return textStyle;
	}

	public TextStyle typeTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 0, 255));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

}
