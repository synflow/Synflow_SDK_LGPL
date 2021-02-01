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
 
package com.synflow.models.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Switch;

/**
 * This class defines utility stuff for EMF-switch based code transformations.
 *
 * @author Matthieu Wipliez
 *
 */
public class SwitchUtil {

	/**
	 * to use in cascading switch
	 */
	public static final Void CASCADE = null;

	/**
	 * to use for non-cascading switch;
	 */
	public static final Void DONE = new Void();

	/**
	 * Checks the given objects with the given EMF switch, and returns <code>true</code> as soon as
	 * the {@link Switch#doSwitch(EObject)} method returns true. Otherwise returns false. If an
	 * object is null, returns false.
	 *
	 * @param emfSwitch
	 *            an EMF switch
	 * @param eObjects
	 *            a varargs array of objects
	 * @return a boolean
	 */
	public static boolean check(Switch<Boolean> emfSwitch, EObject... eObjects) {
		for (EObject eObject : eObjects) {
			if (doSwitchBoolean(emfSwitch, eObject)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks the given objects with the given EMF switch, and returns <code>true</code> as soon as
	 * the {@link Switch#doSwitch(EObject)} method returns true. Otherwise returns false. If an
	 * object is null, returns false.
	 *
	 * @param emfSwitch
	 *            an EMF switch
	 * @param eObjects
	 *            an iterable of objects
	 * @return a boolean
	 */
	public static boolean check(Switch<Boolean> emfSwitch, Iterable<? extends EObject> eObjects) {
		for (EObject eObject : eObjects) {
			if (doSwitchBoolean(emfSwitch, eObject)) {
				return true;
			}
		}
		return false;
	}

	private static boolean doSwitchBoolean(Switch<Boolean> emfSwitch, EObject eObject) {
		if (eObject == null) {
			return false;
		}
		return emfSwitch.doSwitch(eObject);
	}

	public static Void visit(Switch<Void> emfSwitch, EObject... eObjects) {
		for (EObject eObject : eObjects) {
			if (eObject != null) {
				emfSwitch.doSwitch(eObject);
			}
		}
		return DONE;
	}

	public static Void visit(Switch<Void> emfSwitch, Iterable<? extends EObject> eObjects) {
		for (EObject eObject : eObjects) {
			if (eObject != null) {
				emfSwitch.doSwitch(eObject);
			}
		}
		return DONE;
	}

}
