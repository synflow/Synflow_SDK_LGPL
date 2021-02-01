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
 
package com.synflow.models.ir.util;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.synflow.models.ir.Block;
import com.synflow.models.ir.BlockBasic;
import com.synflow.models.ir.Def;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.Use;
import com.synflow.models.ir.Var;
import com.synflow.models.util.EcoreHelper;

/**
 * This class contains several methods to help the manipulation of EMF models.
 *
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 *
 */
public class IrUtil {

	public static JsonArray array(Iterable<?> objects) {
		JsonArray array = new JsonArray();
		for (Object obj : objects) {
			array.add(json(obj));
		}
		return array;
	}

	public static JsonArray array(Object... objects) {
		JsonArray array = new JsonArray();
		for (Object obj : objects) {
			array.add(json(obj));
		}
		return array;
	}

	/**
	 * Returns a deep copy of the given objects, and updates def/use chains.
	 *
	 * @param eObjects
	 *            A Collection of objects
	 * @return a deep copy of the given objects with def/use chains correctly updated
	 */
	public static <T extends EObject> Collection<T> copy(Collection<T> eObjects) {
		return copy(new Copier(), eObjects, true);
	}

	/**
	 * Returns a deep copy of the given objects, using the given Copier instance and updates def/use
	 * chains. If <i>copyReferences</i> is set to true, referenced objects will be duplicated in the
	 * same time their referrer will be.
	 *
	 * @param copier
	 *            A Copier instance
	 * @param eObjects
	 *            A Collection of objects
	 * @param copyReferences
	 *            Flag to control if references must be copied
	 * @return a deep copy of the given objects with def/use chains correctly updated
	 */
	public static <T extends EObject> Collection<T> copy(Copier copier, Collection<T> eObjects,
			boolean copyReferences) {
		Collection<T> result = copier.copyAll(eObjects);
		if (copyReferences) {
			copier.copyReferences();
		}

		TreeIterator<EObject> it = EcoreUtil.getAllContents(eObjects);
		while (it.hasNext()) {
			EObject object = it.next();

			if (object instanceof Def) {
				Def def = (Def) object;
				Def copyDef = (Def) copier.get(def);
				if (copyDef.getVariable() == null) {
					copyDef.setVariable(def.getVariable());
				}
			} else if (object instanceof Use) {
				Use use = (Use) object;
				Use copyUse = (Use) copier.get(use);
				if (copyUse.getVariable() == null) {
					copyUse.setVariable(use.getVariable());
				}
			}
		}

		return result;
	}

	/**
	 * Returns a deep copy of the given object, using the given Copier instance and updates def/use
	 * chains.
	 *
	 * @param copier
	 *            A Copier instance
	 * @param eObject
	 *            The EObject to copy
	 * @return a deep copy of the given object with uses correctly updated
	 */
	public static <T extends EObject> T copy(Copier copier, T eObject) {
		return copy(copier, eObject, true);
	}

	/**
	 * Returns a deep copy of the given object, using the given Copier instance and updates def/use
	 * chains. If <i>copyReferences</i> is set to true, referenced objects will be duplicated in the
	 * same time their referrer will be.
	 *
	 * @param copier
	 *            A Copier instance
	 * @param eObject
	 *            The EObject to copy
	 * @param copyReferences
	 *            Flag to control if references must be copied
	 * @return a deep copy of the given object with uses correctly updated
	 */
	public static <T extends EObject> T copy(Copier copier, T eObject, boolean copyReferences) {
		@SuppressWarnings("unchecked")
		T result = (T) copier.copy(eObject);
		if (copyReferences) {
			copier.copyReferences();
		}

		TreeIterator<EObject> it = EcoreUtil.getAllContents(eObject, true);
		while (it.hasNext()) {
			EObject object = it.next();

			if (object instanceof Def) {
				Def def = (Def) object;
				Def copyDef = (Def) copier.get(def);
				if (copyDef.getVariable() == null) {
					copyDef.setVariable(def.getVariable());
				}
			} else if (object instanceof Use) {
				Use use = (Use) object;
				Use copyUse = (Use) copier.get(use);
				if (copyUse.getVariable() == null) {
					copyUse.setVariable(use.getVariable());
				}
			}
		}

		return result;
	}

	/**
	 * Returns a deep copy of the given object, and updates def/use chains.
	 *
	 * @param eObject
	 *            The EObject to copy
	 * @return a deep copy of the given object with uses correctly updated
	 */
	public static <T extends EObject> T copy(T eObject) {
		return copy(new Copier(), eObject);
	}

	/**
	 * Returns a deep copy of the given object, and updates def/use chains. If <i>copyReferences</i>
	 * is set to true, referenced objects will be duplicated in the same time their referrer will
	 * be.
	 *
	 * @param eObject
	 *            The EObject to copy
	 * @param copyReferences
	 *            Flag to control if references must be copied
	 * @return a deep copy of the given object with uses correctly updated
	 */
	public static <T extends EObject> T copy(T eObject, boolean copyReferences) {
		return copy(new Copier(), eObject, copyReferences);
	}

	/**
	 * Removes the def/use chains of the given object, and then removes the object itself from its
	 * container.
	 *
	 * @param eObject
	 *            an EObject
	 */
	public static void delete(EObject eObject) {
		removeUses(eObject);
		removeDefs(eObject);
		EcoreUtil.remove(eObject);
	}

	/**
	 * Deletes the given objects, and updates the def/use chains.
	 *
	 * @param objects
	 *            a list of objects
	 */
	public static void delete(List<? extends EObject> eObjects) {
		while (!eObjects.isEmpty()) {
			delete(eObjects.get(0));
		}
	}

	/**
	 * Returns a file name from the given qualified name. Equivalent to
	 * <code>name.replace('.', '/');</code>
	 *
	 * @param name
	 *            a qualified name (identifiers separated with dots)
	 * @return a file name
	 */
	public static String getFile(String name) {
		return name.replace('.', '/');
	}

	/**
	 * Returns the first block in the given list of blocks. A new block is created if there is no
	 * block in the given block list.
	 *
	 * @param blocks
	 *            a list of blocks
	 * @return a block
	 */
	public static BlockBasic getFirst(List<Block> blocks) {
		BlockBasic block;
		if (blocks.isEmpty()) {
			block = IrFactory.eINSTANCE.createBlockBasic();
			blocks.add(block);
		} else {
			Block firstBlock = blocks.get(0);
			if (firstBlock.isBlockBasic()) {
				block = (BlockBasic) firstBlock;
			} else {
				block = IrFactory.eINSTANCE.createBlockBasic();
				blocks.add(0, block);
			}
		}

		return block;
	}

	/**
	 * Returns the last block in the given list of blocks. A new block is created if there is no
	 * block in the given block list.
	 *
	 * @param blocks
	 *            a list of blocks
	 * @return a block
	 */
	public static BlockBasic getLast(List<Block> blocks) {
		BlockBasic block;
		if (blocks.isEmpty()) {
			block = IrFactory.eINSTANCE.createBlockBasic();
			blocks.add(block);
		} else {
			Block lastBlock = blocks.get(blocks.size() - 1);
			if (lastBlock.isBlockBasic()) {
				block = (BlockBasic) lastBlock;
			} else {
				block = IrFactory.eINSTANCE.createBlockBasic();
				blocks.add(block);
			}
		}

		return block;
	}

	/**
	 * Returns the name of the given local variable when using SSA.
	 *
	 * @param local
	 *            local variable
	 * @return the local name
	 */
	public static String getNameSSA(Var local) {
		if (local.getIndex() == 0) {
			return local.getName();
		} else {
			return local.getName() + "_" + local.getIndex();
		}
	}

	/**
	 * Returns the package of the given name, which is composed of all the components but the last
	 * one.
	 *
	 * @param name
	 *            a qualified name
	 * @return the package of the given name
	 */
	public static String getPackage(String name) {
		int index = name.lastIndexOf('.');
		if (index == -1) {
			return "";
		} else {
			return name.substring(0, index);
		}
	}

	/**
	 * Returns the last component of the given qualified name.
	 *
	 * @param name
	 *            a qualified name
	 * @return the last component of the given qualified name.
	 */
	public static String getSimpleName(String name) {
		int index = name.lastIndexOf('.');
		if (index != -1) {
			name = name.substring(index + 1);
		}
		return name;
	}

	private static JsonElement json(Object obj) {
		if (obj instanceof JsonElement) {
			return (JsonElement) obj;
		} else if (obj instanceof Boolean) {
			return new JsonPrimitive((Boolean) obj);
		} else if (obj instanceof Number) {
			return new JsonPrimitive((Number) obj);
		} else {
			return new JsonPrimitive(obj.toString());
		}
	}

	public static JsonObject obj(Object... args) {
		JsonObject object = new JsonObject();
		for (int i = 0; i < args.length - 1; i += 2) {
			object.add((String) args[i], json(args[i + 1]));
		}
		return object;
	}

	/**
	 * Removes the defs present in the given object.
	 *
	 * @param eObject
	 *            an EObject
	 */
	public static void removeDefs(EObject eObject) {
		for (Def def : EcoreHelper.getObjects(eObject, Def.class)) {
			def.setVariable(null);
		}
	}

	/**
	 * Removes the uses present in the given object.
	 *
	 * @param eObject
	 *            an EObject
	 */
	public static void removeUses(EObject eObject) {
		for (Use use : EcoreHelper.getObjects(eObject, Use.class)) {
			use.setVariable(null);
		}
	}

}
