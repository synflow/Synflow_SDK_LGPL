/*******************************************************************************
 * Copyright (c) 2014-2015 Synflow SAS.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthieu Wipliez - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.synflow.cx.tests;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.synflow.cx.CxInjectorProvider;
import com.synflow.cx.CxStandaloneSetup;
import com.synflow.ngDesign.NgDesignModule;

/**
 * This class configures additional modules in addition to the CxRuntimeModule.
 * 
 * @author Matthieu Wipliez
 *
 */
public class CustomCxInjectorProvider extends CxInjectorProvider {

	@Override
	protected Injector internalCreateInjector() {
		return new CxStandaloneSetup() {
			@Override
			public Injector createInjector() {
				return Guice.createInjector(new com.synflow.cx.CxRuntimeModule() {
					@SuppressWarnings("unused")
					public void configureNgDesign(Binder binder) {
						new NgDesignModule().configure(binder);
					}
				});
			}
		}.createInjectorAndDoEMFRegistration();
	}
	
}
