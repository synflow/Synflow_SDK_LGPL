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
package com.synflow.ngDesign.ui.internal;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class NgDesignUi extends AbstractUIPlugin {

	public static final String IMG_LIC_ERR = "icons/license_err.png";

	public static final String IMG_LIC_OK = "icons/license_ok.png";

	// The shared instance
	private static NgDesignUi plugin;

	// The plug-in identifier
	public static final String PLUGIN_ID = "com.synflow.ngDesign.ui"; //$NON-NLS-1$

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static NgDesignUi getDefault() {
		return plugin;
	}

	/**
	 * Creates and returns a new image descriptor for an image file located within this plug-in.
	 *
	 * @param path
	 *            the relative path of the image file, relative to the root of the plug-in; the path
	 *            must be legal
	 * @return an image descriptor, or <code>null</code> if no image could be found
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * The constructor
	 */
	public NgDesignUi() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

}
