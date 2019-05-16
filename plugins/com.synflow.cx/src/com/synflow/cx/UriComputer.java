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
 
package com.synflow.cx;

import static com.synflow.core.ICoreConstants.FILE_EXT_IR;
import static com.synflow.core.ICoreConstants.FOLDER_IR;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;

/**
 * This class describes a special URI mapper that returns a valid absolute URI to a target .ir file
 * from a .cx URI.
 *
 * @author Matthieu Wipliez
 *
 */
public class UriComputer extends ExtensibleURIConverterImpl {

	/**
	 * the unique instance of this mapper.
	 */
	public static final UriComputer INSTANCE = new UriComputer();

	private UriComputer() {
	}

	/**
	 * Computes the URI to a .ir file based on the context, uri, and name of the entity.
	 *
	 * @param name
	 *            name of the entity
	 * @param uri
	 *            uri of a .cx resource
	 * @param context
	 *            URI to a module (may be <code>null</code>)
	 * @return a new absolute URI
	 */
	public URI computeUri(String name, URI uri, URI context) {
		URI result;
		if (uri.isPlatformResource()) {
			result = URI.createPlatformResourceURI(uri.segment(1) + "/" + FOLDER_IR, false);
		} else if (uri.isFile()) {
			URI trimmedURI = uri.trimFragment().trimQuery();
			URI normalized = getInternalURIMap().getURI(trimmedURI);
			result = keepPrefix(normalized, uri);
		} else if (uri.isPlatformPlugin()) {
			// return URI in the right place based on the context
			return computeUri(name, context, null);
		} else {
			result = uri;
		}

		result = result.appendSegments(name.split("\\.")).appendFileExtension(FILE_EXT_IR);

		return normalize(result);
	}

	private URI keepPrefix(URI normalized, URI uri) {
		String[] uriSegs = uri.segments();
		String[] normSegs = normalized.segments();
		int index = normSegs.length - 1;
		for (int i = uriSegs.length - 1; i >= 0 && index >= 0; i--, index--) {
			if (!uriSegs[i].equals(normSegs[index])) {
				break;
			}
		}
		return normalized.trimSegments(normSegs.length - index - 1);
	}

}
