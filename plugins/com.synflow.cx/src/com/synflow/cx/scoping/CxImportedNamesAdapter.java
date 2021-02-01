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

import java.util.Iterator;

import org.eclipse.xtext.linking.impl.ImportedNamesAdapter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * This class extends the default imported names adapter to use case-sensitive imported names. Makes
 * it easier to find objects directly using their qualified name.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxImportedNamesAdapter extends ImportedNamesAdapter {

	public class CxWrappingScope extends WrappingScope {

		private IScope delegate;

		public CxWrappingScope(IScope scope) {
			super(scope);
			this.delegate = scope;
		}

		@Override
		public Iterable<IEObjectDescription> getElements(final QualifiedName name) {
			return new Iterable<IEObjectDescription>() {
				public Iterator<IEObjectDescription> iterator() {
					getImportedNames().add(name);
					final Iterable<IEObjectDescription> elements = delegate.getElements(name);
					return elements.iterator();
				}
			};
		}

		@Override
		public IEObjectDescription getSingleElement(QualifiedName name) {
			getImportedNames().add(name);
			return delegate.getSingleElement(name);
		}

	}

	@Override
	public IScope wrap(IScope scope) {
		return new CxWrappingScope(scope);
	}

}
