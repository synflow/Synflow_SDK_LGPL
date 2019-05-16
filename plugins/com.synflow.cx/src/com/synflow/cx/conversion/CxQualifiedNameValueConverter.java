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
package com.synflow.cx.conversion;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.QualifiedNameValueConverter;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

/**
 * This class defines a qualified name value converter. Implementation is copied from Xbase. No idea
 * why it is necessary.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxQualifiedNameValueConverter extends QualifiedNameValueConverter {

	@Override
	protected String getDelegateRuleName() {
		return "ValidID";
	}

	@Override
	public String toValue(String string, INode node) throws ValueConverterException {
		StringBuilder buffer = new StringBuilder();
		boolean isFirst = true;
		if (node != null) {
			for (INode child : node.getAsTreeIterable()) {
				EObject grammarElement = child.getGrammarElement();
				if (isDelegateRuleCall(grammarElement) || isWildcardLiteral(grammarElement)) {
					if (!isFirst)
						buffer.append(getValueNamespaceDelimiter());
					isFirst = false;
					if (isDelegateRuleCall(grammarElement))
						for (ILeafNode leafNode : child.getLeafNodes()) {
							if (!leafNode.isHidden())
								buffer.append(delegateToValue(leafNode));
						}
					else
						buffer.append(getWildcardLiteral());
				}
			}
		} else {
			for (String segment : Strings.split(string, getStringNamespaceDelimiter())) {
				if (!isFirst)
					buffer.append(getValueNamespaceDelimiter());
				isFirst = false;
				if (getWildcardLiteral().equals(segment)) {
					buffer.append(getWildcardLiteral());
				} else {
					buffer.append((String) valueConverterService.toValue(segment,
							getDelegateRuleName(), null));
				}
			}
		}
		return buffer.toString();
	}

}
