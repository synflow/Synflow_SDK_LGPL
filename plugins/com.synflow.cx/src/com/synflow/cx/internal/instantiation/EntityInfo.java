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
package com.synflow.cx.internal.instantiation;

import org.eclipse.emf.common.util.URI;

import com.synflow.cx.cx.CxEntity;

/**
 * This class holds information about a Cx entity, its specialized name and URI of the corresponding
 * IR resource.
 *
 * @author Matthieu Wipliez
 *
 */
public class EntityInfo {

	private final CxEntity cxEntity;

	private final String name;

	private final boolean specialized;

	private final URI uri;

	public EntityInfo(CxEntity cxEntity, String name, URI uri, boolean specialized) {
		this.cxEntity = cxEntity;
		this.name = name;
		this.uri = uri;
		this.specialized = specialized;
	}

	public CxEntity getCxEntity() {
		return cxEntity;
	}

	public String getName() {
		return name;
	}

	public URI getURI() {
		return uri;
	}

	public boolean isSpecialized() {
		return specialized;
	}

	@Override
	public String toString() {
		return name + " " + uri;
	}

}
