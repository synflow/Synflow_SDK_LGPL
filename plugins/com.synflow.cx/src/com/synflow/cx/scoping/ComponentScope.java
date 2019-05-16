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
package com.synflow.cx.scoping;

import static com.synflow.core.ICoreConstants.FILE_EXT_CX;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractScope;

/**
 * This class implements a scoping for built-in components.
 *
 * @author Matthieu Wipliez
 */
public class ComponentScope extends AbstractScope {

	private static final Map<String, URI> uriMap = new HashMap<>();

	static {
		// registers built-in components in the 'fifo' package
		// synchronous and asynchronous FIFO
		int i = 0;
		uriMap.put("std.fifo.SynchronousFIFO", createURI("Fifo", i++));
		uriMap.put("std.fifo.AsynchronousFIFO", createURI("Fifo", i++));

		// registers built-in components in the 'lib' package
		// synchronizer FF, synchronizer Mux, mux DDR, demux DDR
		i = 0;
		uriMap.put("std.lib.SynchronizerFF", createURI("Lib", i++));
		uriMap.put("std.lib.SynchronizerMux", createURI("Lib", i++));
		uriMap.put("std.lib.MuxDDR", createURI("Lib", i++));
		uriMap.put("std.lib.DemuxDDR", createURI("Lib", i++));

		// registers built-in components in the 'mem' package
		// single-port RAM, dual-port RAM, pseudo dual-port RAM
		i = 0;
		uriMap.put("std.mem.SinglePortRAM", createURI("Mem", i++));
		uriMap.put("std.mem.DualPortRAM", createURI("Mem", i++));
		uriMap.put("std.mem.PseudoDualPortRAM", createURI("Mem", i++));
	}

	private static URI createURI(String name, int index) {
		String pathName = "com.synflow.cx/model/" + name + "." + FILE_EXT_CX;
		URI uri = URI.createPlatformPluginURI(pathName, true);
		return uri.appendFragment("//@entities." + index);
	}

	private ResourceSet resourceSet;

	public ComponentScope(IScope parent, ResourceSet resourceSet) {
		super(parent, false);
		this.resourceSet = resourceSet;
	}

	@Override
	protected Iterable<IEObjectDescription> getAllLocalElements() {
		return Collections.emptySet();
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName name) {
		// we only have one description
		IEObjectDescription result = getSingleElement(name);
		if (result != null)
			return singleton(result);
		return emptySet();
	}

	@Override
	public IEObjectDescription getSingleElement(QualifiedName name) {
		URI uri = uriMap.get(name.toString());
		if (uri != null) {
			EObject eObject = resourceSet.getEObject(uri, true);
			return new EObjectDescription(name, eObject, null);
		}

		// no parent scope anyway so we simply return null
		return null;
	}

}
