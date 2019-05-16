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
 
package com.synflow.core;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.osgi.framework.Bundle;

/**
 * This class defines a factory that injects objects.
 *
 * @author Matthieu Wipliez
 *
 */
public class InjectableExtensionFactory implements IExecutableExtension,
		IExecutableExtensionFactory {

	private String clazzName;

	private IConfigurationElement config;

	@Override
	public Object create() throws CoreException {
		try {
			Class<?> clazz = getBundle().loadClass(clazzName);

			// if class found, inject and return result
			Object result = SynflowCore.getDefault().getInstance(clazz);
			if (result instanceof IExecutableExtension) {
				((IExecutableExtension) result).setInitializationData(config, null, null);
			}
			return result;
		} catch (ClassNotFoundException e) {
		}

		// could not find class in this bundle, return null
		return null;
	}

	/**
	 * Returns the bundle to use to load classes.
	 *
	 * @return the bundle to use to load classes
	 */
	protected Bundle getBundle() {
		return SynflowCore.getBundle();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		if (data instanceof String) {
			clazzName = (String) data;
		} else if (data instanceof Map<?, ?>) {
			clazzName = ((Map<String, String>) data).get("className");
		}
		this.config = config;
	}

}
