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
package com.synflow.cx.instantiation;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.google.inject.ImplementedBy;
import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.Module;
import com.synflow.cx.cx.VarRef;
import com.synflow.cx.internal.instantiation.InstantiatorImpl;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Port;
import com.synflow.models.ir.Type;
import com.synflow.models.util.Executable;

/**
 * This interface defines an instantiator.
 *
 * @author Matthieu Wipliez
 *
 */
@ImplementedBy(InstantiatorImpl.class)
public interface IInstantiator {

	/**
	 * Clears all data retained by the instantiator.
	 */
	void clearData();

	/**
	 * Computes the type of the given object.
	 *
	 * @param eObject
	 *            an object
	 * @return the type of the given object
	 */
	Type computeType(Entity entity, EObject eObject);

	/**
	 * Returns the value associated with the given object.
	 *
	 * @param eObject
	 *            an AST node
	 * @return the value associated with the given object
	 */
	Object evaluate(Entity entity, EObject eObject);

	/**
	 * Returns the integer value associated with the given object. Returns -1 if the value is not an
	 * integer.
	 *
	 * @param eObject
	 *            an AST node
	 * @return the integer value associated with the given object
	 */
	int evaluateInt(Entity entity, EObject eObject);

	/**
	 * Retrieves all IR entities associated to the given Cx entity, and for each <code>entity</code>
	 * , calls <code>executable.exec(entity)</code>.
	 *
	 * @param cxEntity
	 *            Cx entity
	 * @param executable
	 *            an executable
	 * @see #execute(Entity, Executable)
	 */
	void forEachMapping(CxEntity cxEntity, Executable<Entity> executable);

	/**
	 * Returns the Cx entity currently associated with the given URI.
	 *
	 * @param uri
	 *            URI of a Cx entity
	 * @return a Cx entity (may be <code>null</code>)
	 */
	CxEntity getEntity(URI uri);

	/**
	 * Returns the IR object that corresponds to the given Cx object in the given entity.
	 *
	 * @param entity
	 *            the entity to use to find the mapping
	 * @param cxObj
	 *            a Cx object (entity, variable, port...)
	 * @return the IR mapping
	 */
	<T extends EObject> T getMapping(Entity entity, Object cxObj);

	/**
	 * Returns the IR port that corresponds to the given reference.
	 *
	 * @param entity
	 *            the entity in which the mapping exists
	 * @param ref
	 *            a reference to a Cx port
	 * @return an IR port
	 */
	Port getPort(Entity entity, VarRef ref);

	/**
	 * Checks whether the object at the given URI is specialized.
	 *
	 * @param uri
	 *            URI
	 * @return true if the object at the given URI is specialized
	 */
	boolean isSpecialized(URI uri);

	/**
	 * Adds a mapping from the given Cx object to the given IR object in the given entity.
	 *
	 * @param entity
	 *            an IR entity
	 * @param cxObj
	 *            a Cx object (entity, variable, port...)
	 * @param irObj
	 *            the IR object that corresponds to <code>cxObj</code> in the given entity
	 */
	void putMapping(Entity entity, Object cxObj, EObject irObj);

	/**
	 * Updates the instantiation tree with entities of the given module. Most of the time this
	 * method only performs a partial update of the tree.
	 *
	 * @param module
	 *            a module
	 */
	void update(Module module);

}
