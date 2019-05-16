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
 
package com.synflow.models.ir.util;

import static com.synflow.models.ir.util.IrUtil.getNameSSA;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EReference;

import com.synflow.models.dpn.Actor;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Var;

/**
 * This class defines an adapter that maintains a map of variables from a list
 * of variables.
 *
 * @author Matthieu Wipliez
 *
 */
public class MapAdapter extends AdapterImpl {

	private final Map<String, Var> map;

	private final EReference reference;

	public MapAdapter(Map<String, Var> map, EReference reference) {
		this.map = map;
		this.reference = reference;
	}

	private void add(Object object) {
		Var var = (Var) object;
		map.put(getNameSSA(var), var);
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return (type == Actor.class || type == Procedure.class);
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (reference != notification.getFeature()) {
			return;
		}

		switch (notification.getEventType()) {
		case Notification.ADD:
			add(notification.getNewValue());
			break;

		case Notification.ADD_MANY: {
			List<?> list = (List<?>) notification.getNewValue();
			for (Object object : list) {
				add(object);
			}
			break;
		}

		case Notification.MOVE: {
			remove(notification.getOldValue());
			add(notification.getNewValue());
			break;
		}

		case Notification.REMOVE:
			remove(notification.getOldValue());
			break;

		case Notification.REMOVE_MANY: {
			List<?> list = (List<?>) notification.getOldValue();
			for (Object object : list) {
				remove(object);
			}
			break;
		}
		}
	}

	private void remove(Object object) {
		map.remove(getNameSSA((Var) object));
	}

}
