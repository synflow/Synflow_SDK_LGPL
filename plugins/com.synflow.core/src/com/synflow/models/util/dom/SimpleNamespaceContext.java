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
 
package com.synflow.models.util.dom;

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.Iterators;

/**
 * This class describes a simple namespace context implementation.
 *
 * @author Matthieu Wipliez
 *
 */
public class SimpleNamespaceContext implements NamespaceContext {

	private Element docElement;

	public SimpleNamespaceContext(Document document) {
		this.docElement = document.getDocumentElement();
	}

	@Override
	public String getNamespaceURI(String prefix) {
		return docElement.lookupNamespaceURI(prefix);
	}

	@Override
	public String getPrefix(String namespaceURI) {
		return docElement.lookupPrefix(namespaceURI);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Iterator getPrefixes(String namespaceURI) {
		return Iterators.singletonIterator(getPrefix(namespaceURI));
	}

}
