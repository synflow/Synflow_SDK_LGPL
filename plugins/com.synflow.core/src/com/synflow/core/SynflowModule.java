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

import static org.osgi.framework.Constants.BUNDLE_CLASSPATH;
import static org.osgi.framework.Constants.REQUIRE_BUNDLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.util.ManifestElement;
import org.osgi.framework.BundleException;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;

/**
 * This class defines a module made of modules contributed by extensions.
 *
 * @author Matthieu Wipliez
 *
 */
public class SynflowModule extends AbstractModule {

	/**
	 * This class sorts configuration elements based on bundle dependencies.
	 *
	 * @author Matthieu Wipliez
	 *
	 */
	private static class BundleSorter implements Comparator<IConfigurationElement> {

		private Map<String, List<String>> dependencies;

		public BundleSorter() {
			dependencies = new HashMap<>();
		}

		@Override
		public int compare(IConfigurationElement e1, IConfigurationElement e2) {
			String bundle1 = e1.getContributor().getName();
			String bundle2 = e2.getContributor().getName();

			if (dependencies.get(bundle1).contains(bundle2)) {
				return 1;
			} else if (dependencies.get(bundle2).contains(bundle1)) {
				return -1;
			} else {
				return 0;
			}
		}

		private void computeRequired(String name) {
			List<String> required = new ArrayList<>();
			String require = Platform.getBundle(name).getHeaders().get(REQUIRE_BUNDLE);
			try {
				ManifestElement[] elements = ManifestElement.parseHeader(BUNDLE_CLASSPATH, require);
				for (ManifestElement manifestElement : elements) {
					required.add(manifestElement.getValue());
				}
			} catch (BundleException e) {
				SynflowCore.log(e);
			}

			dependencies.put(name, required);
		}

		/**
		 * Sorts the given configuration elements based on bundle dependencies.
		 *
		 * @param elements
		 */
		public void sort(IConfigurationElement[] elements) {
			for (IConfigurationElement element : elements) {
				computeRequired(element.getContributor().getName());
			}

			Arrays.sort(elements, this);
		}

	}

	@Override
	protected void configure() {
		// configure additional modules with this binder
		if (Platform.isRunning()) {
			IExtensionRegistry reg = Platform.getExtensionRegistry();

			IConfigurationElement[] elements = reg
					.getConfigurationElementsFor("com.synflow.core.injection");
			new BundleSorter().sort(elements);

			Module module = null;
			for (IConfigurationElement element : elements) {
				if ("module".equals(element.getName())) {
					try {
						Object obj = element.createExecutableExtension("class");
						if (module == null) {
							module = (Module) obj;
						} else {
							module = Modules.override(module).with((Module) obj);
						}
					} catch (CoreException e) {
						SynflowCore.log(e);
					}
				}
			}
			binder().install(module);
		}
	}

}
