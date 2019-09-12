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

import static com.synflow.core.ICoreConstants.PROP_GENERATOR;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

/**
 * This class defines the Synflow core plug-in, as well as various constants.
 *
 * @author Matthieu Wipliez
 *
 */
public class SynflowCore implements BundleActivator {

	private static BundleContext context;

	// The shared instance
	private static SynflowCore plugin;

	// The plug-in ID
	public static final String PLUGIN_ID = "com.synflow.core";

	/**
	 * Returns the bundle associated with this plug-in.
	 *
	 * @return the bundle associated with this plug-in
	 */
	public static Bundle getBundle() {
		return context.getBundle();
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static SynflowCore getDefault() {
		return plugin;
	}

	/**
	 * Returns the code generator used by and configured for the given project, or <code>null</code> .
	 *
	 * @param project
	 *            a project
	 * @return a code generator
	 */
	public static ICodeGenerator getGenerator(IProject project) {
		String name = getProjectPreferences(project).get(PROP_GENERATOR, null);
		if (name == null) {
			// no generator associated with the project
			try {
				IMarker marker = project.createMarker(IMarker.PROBLEM);
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				String msg = "No generator associated with project '" + project.getName()
						+ "'. Please edit project properties.";
				marker.setAttribute(IMarker.MESSAGE, msg);
			} catch (CoreException e) {
				SynflowCore.log(e);
			}
			return null;
		}

		ICodeGenerator generator;
		try {
			generator = getDefault().getInstance(ICodeGenerator.class, name);
			generator.setOutputFolder(project.getName());
		} catch (ConfigurationException e) {
			generator = null;
			try {
				IMarker marker = project.createMarker(IMarker.PROBLEM);
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				String msg = name + " code generator required to compile project '" + project.getName()
						+ "' is not available. " + "Please edit project properties.";
				marker.setAttribute(IMarker.MESSAGE, msg);
			} catch (CoreException ex) {
				SynflowCore.log(ex);
			}
		}
		return generator;
	}

	/**
	 * Returns the list of declared generators.
	 *
	 * @return a list of names of generators
	 */
	public static List<String> getGenerators() {
		// extensions
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = reg.getConfigurationElementsFor("com.synflow.core.injection");
		List<String> generators = new ArrayList<>();
		for (IConfigurationElement element : elements) {
			if ("generator".equals(element.getName())) {
				String name = element.getAttribute("name");
				//ICodeGenerator generator = getDefault().getInstance(ICodeGenerator.class, name);
				generators.add(name);
			}
		}
		return generators;
	}

	/**
	 * Returns the preferences node for the given project.
	 *
	 * @param project
	 *            a project
	 * @return a preference node
	 */
	public static IEclipsePreferences getProjectPreferences(IProject project) {
		return new ProjectScope(project).getNode(SynflowCore.PLUGIN_ID);
	}

	/**
	 * Returns the list of projects with the Synflow nature.
	 *
	 * @return the list of projects with the Synflow nature
	 */
	public static IProject[] getProjects() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		List<IProject> projects = new ArrayList<>();
		for (IProject project : root.getProjects()) {
			try {
				if (project.isOpen() && project.hasNature(SynflowNature.NATURE_ID)) {
					projects.add(project);
				}
			} catch (CoreException e) {
				log(e);
			}
		}

		return projects.toArray(new IProject[projects.size()]);
	}

	/**
	 * Returns true if this plug-in is loaded.
	 *
	 * @return true if this plug-in is loaded
	 */
	public static boolean isLoaded() {
		return plugin != null;
	}

	/**
	 * Logs an error status based on the given throwable.
	 *
	 * @param t
	 *            a throwable
	 */
	public static void log(Throwable t) {
		if (!isLoaded()) {
			t.printStackTrace();
			return;
		}

		IStatus status;
		if (t instanceof CoreException) {
			status = ((CoreException) t).getStatus();
		} else {
			status = new Status(IStatus.ERROR, PLUGIN_ID, t.getMessage(), t);
		}
		Platform.getLog(getBundle()).log(status);
	}

	/**
	 * Opens an input stream on the given file. Works within Eclipse and in a standalone environment.
	 *
	 * @param fileName
	 *            name of a file contained in this bundle or in a fragment, beginning with a /
	 * @return an input stream
	 * @throws IOException
	 */
	public static InputStream openStream(String fileName) throws IOException {
		URI uri = URI.createPlatformPluginURI("/" + SynflowCore.PLUGIN_ID + fileName, false);
		return new ExtensibleURIConverterImpl().createInputStream(uri, null);
	}

	private Injector injector;

	/**
	 * Returns the appropriate instance for the given injection type.
	 *
	 * @param type
	 *            injection type
	 * @return an instance of type T
	 */
	public <T> T getInstance(Class<T> type) {
		return injector.getInstance(type);
	}

	/**
	 * Returns the appropriate instance for the given injection type, with a Named annotation (name is
	 * given).
	 *
	 * @param type
	 *            injection type
	 * @param name
	 *            annotated name
	 * @return an instance of type T
	 */
	public <T> T getInstance(Class<T> type, String name) {
		Named annotation = Names.named(name);
		Key<T> key = Key.get(type, annotation);
		return injector.getInstance(key);
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		SynflowCore.context = bundleContext;
		SynflowCore.plugin = this;

		injector = Guice.createInjector(new SynflowModule());
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		injector = null;
		SynflowCore.context = null;
		SynflowCore.plugin = null;
	}

}
