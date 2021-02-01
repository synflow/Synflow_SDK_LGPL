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
package com.synflow.cx.scoping;

import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;

/**
 * This class describes an import normalizer that allows import of x.y.Bundle and properly resolves
 * references to Bundle.A, Bundle.B etc.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxImportNormalizer extends ImportNormalizer {

	public CxImportNormalizer(QualifiedName importedNamespace, boolean wildCard) {
		super(importedNamespace, wildCard, false);
	}

	@Override
	public QualifiedName deresolve(QualifiedName fullyQualifiedName) {
		if (getImportedNamespacePrefix().equals(fullyQualifiedName)) {
			return QualifiedName.create(fullyQualifiedName.getLastSegment());
		} else if (fullyQualifiedName.startsWith(getImportedNamespacePrefix()) && fullyQualifiedName
				.getSegmentCount() != getImportedNamespacePrefix().getSegmentCount()) {
			return fullyQualifiedName.skipFirst(getImportedNamespacePrefix().getSegmentCount());
		}
		return null;
	}

	@Override
	public QualifiedName resolve(QualifiedName relativeName) {
		if (relativeName.isEmpty()) {
			return null;
		} else if (hasWildCard()) {
			return getImportedNamespacePrefix().append(relativeName);
		} else if (getImportedNamespacePrefix().getLastSegment()
				.equals(relativeName.getFirstSegment())) {
			return getImportedNamespacePrefix().skipLast(1).append(relativeName);
		}
		return null;
	}

}
