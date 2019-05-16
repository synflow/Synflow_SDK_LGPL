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
package com.synflow.ngDesign;

import static org.eclipse.core.runtime.Platform.getPreferencesService;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;

import com.synflow.core.SynflowCore;

/**
 * This class defines the ngDesignPro plug-in.
 *
 * @author Matthieu Wipliez
 *
 */
public class NgDesign implements BundleActivator {

	private static BundleContext context;

	public static final String PLUGIN_ID = "com.synflow.ngDesign";

	/**
	 * Returns the bundle associated with this plug-in.
	 *
	 * @return the bundle associated with this plug-in
	 */
	public static Bundle getBundle() {
		return context.getBundle();
	}


	/**
	 * Return the value stored in the preference store for the given key. If the key is not defined
	 * then return the specified default value.
	 *
	 * @param key
	 *            the name of the preference
	 * @param defaultValue
	 *            the value to use if the preference is not defined
	 * @return the value of the preference or the given default value
	 */
	public static boolean getPreference(String key, boolean defaultValue) {
		return getPreferencesService().getBoolean(PLUGIN_ID, key, defaultValue, null);
	}

	/**
	 * Return the value stored in the preference store for the given key. If the key is not defined
	 * then return the specified default value.
	 *
	 * @param key
	 *            the name of the preference
	 * @param defaultValue
	 *            the value to use if the preference is not defined
	 * @return the value of the preference or the given default value
	 */
	public static String getPreference(String key, String defaultValue) {
		return getPreferencesService().getString(PLUGIN_ID, key, defaultValue, null);
	}

	/**
	 * Sets the value of the given key in the preference store.
	 *
	 * @param key
	 *            key with which the specified value is to be associated
	 * @param value
	 *            value to be associated with the specified key
	 */
	public static void setPreference(String key, boolean value) {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(PLUGIN_ID);
		prefs.putBoolean(key, value);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			SynflowCore.log(e);
		}
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		NgDesign.context = bundleContext;
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		NgDesign.context = null;
	}

}
