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
package com.synflow.ngDesign.ui.internal;

import org.osgi.framework.Bundle;

import com.synflow.core.InjectableExtensionFactory;

/**
 * This class defines a factory that injects objects. This needs to be redefined here to use this
 * bundle to load classes defined in this plug-in.
 *
 * @author Matthieu Wipliez
 *
 */
public class UiInjectableExtensionFactory extends InjectableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return NgDesignUi.getDefault().getBundle();
	}

}
