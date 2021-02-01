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
 
package com.synflow.models.ir.impl;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * This class defines a resource implementation for the Df model which is used to serialize
 * to/deserialize from XDF.
 *
 * @author mwipliez
 *
 */
public class IrResourceImpl extends XMIResourceImpl {

	public IrResourceImpl() {
		initResource();
	}

	public IrResourceImpl(URI uri) {
		super(uri);
		initResource();
	}

	private void initResource() {
		setOptions();
		setEncoding("UTF-8");
	}

	private void setOptions() {
		getDefaultSaveOptions().put(OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
		getDefaultSaveOptions().put(OPTION_LINE_WIDTH, 80);

		setOptions(getDefaultLoadOptions());
		setOptions(getDefaultSaveOptions());
	}

	private void setOptions(Map<Object, Object> options) {
		options.put(OPTION_URI_HANDLER, new URIHandlerImpl.PlatformSchemeAware());
	}

}
