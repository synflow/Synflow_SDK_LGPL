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
 
package com.synflow.core;

import org.w3c.dom.Document;

import com.synflow.models.dpn.Entity;

/**
 * This interface defines an exporter.
 *
 * @author Matthieu Wipliez
 *
 */
public interface IExporter {

	/**
	 * name of the folder where synthesis projects are generated
	 */
	String FOLDER_PROJECTS = "projects";

	/**
	 * Runs this exporter on the given entity.
	 *
	 * @param entity
	 *            an entity
	 */
	ProcessBuilder createProject(Entity entity, Document xmlDoc);

	/**
	 * Runs this exporter on the given entity.
	 *
	 * @param entity
	 *            an entity
	 */
	ProcessBuilder buildProject(Entity entity, Document xmlDoc);

	/**
	 * Runs this exporter on the given entity.
	 *
	 * @param entity
	 *            an entity
	 */
	ProcessBuilder setupPR(Entity entity);

	/**
	 * Runs this exporter on the given entity.
	 *
	 * @param entity
	 *            a second entity for Partial Reconfig
	 *
	 * @param entity
	 *            a third entity for Partial Reconfig
	 */
	/* TODO
	 * Make exportPR generic i.e. a table of entities rather than two entities
	 */
	ProcessBuilder exportPR(Entity entity, Entity variant1, Entity variant2, Document xmlDoc);


}
