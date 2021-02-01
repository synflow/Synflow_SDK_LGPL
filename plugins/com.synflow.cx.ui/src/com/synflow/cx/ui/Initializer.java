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
package com.synflow.cx.ui;

import static com.synflow.core.ICoreConstants.FOLDER_IR;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_CLEANUP_DERIVED;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_CLEAN_DIRECTORY;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_CREATE_DIRECTORY;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_DERIVED;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_DESCRIPTION;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_DIRECTORY;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_NAME;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_OVERRIDE;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_PREFERENCE_TAG;
import static org.eclipse.xtext.ui.editor.occurrences.MarkOccurrenceActionContributor.EDITOR_MARK_OCCURRENCES;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfigurationProvider;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;
import org.eclipse.xtext.ui.editor.preferences.PreferenceConstants;

import com.google.inject.Inject;

/**
 * This class providers an initializer for the Builder Preference Access that
 * sets default output directory to ".ir" instead of "./src-gen".
 *
 * @author Matthieu Wipliez
 *
 */
public class Initializer implements IPreferenceStoreInitializer {

	private OutputConfigurationProvider outputConfigurationProvider;

	private String getKey(OutputConfiguration outputConfiguration,
			String preferenceName) {
		return OUTPUT_PREFERENCE_TAG + PreferenceConstants.SEPARATOR
				+ outputConfiguration.getName() + PreferenceConstants.SEPARATOR
				+ preferenceName;
	}

	public OutputConfigurationProvider getOutputConfigurationProvider() {
		return outputConfigurationProvider;
	}

	@Override
	public void initialize(IPreferenceStoreAccess preferenceStoreAccess) {
		IPreferenceStore store = preferenceStoreAccess
				.getWritablePreferenceStore();
		intializeBuilderPreferences(store);
		initializeOutputPreferences(store);

		preferenceStoreAccess.getWritablePreferenceStore().setDefault(
				EDITOR_MARK_OCCURRENCES, true);
	}

	private void initializeOutputPreferences(IPreferenceStore store) {
		for (OutputConfiguration configuration : getOutputConfigurationProvider()
				.getOutputConfigurations()) {
			store.setDefault(getKey(configuration, OUTPUT_NAME),
					configuration.getName());
			store.setDefault(getKey(configuration, OUTPUT_DESCRIPTION),
					configuration.getDescription());
			store.setDefault(getKey(configuration, OUTPUT_DERIVED),
					configuration.isSetDerivedProperty());

			store.setDefault(getKey(configuration, OUTPUT_DIRECTORY), FOLDER_IR);

			store.setDefault(getKey(configuration, OUTPUT_CREATE_DIRECTORY),
					configuration.isCreateOutputDirectory());
			store.setDefault(getKey(configuration, OUTPUT_CLEAN_DIRECTORY),
					configuration.isCanClearOutputDirectory());
			store.setDefault(getKey(configuration, OUTPUT_OVERRIDE),
					configuration.isOverrideExistingResources());
			store.setDefault(getKey(configuration, OUTPUT_CLEANUP_DERIVED),
					configuration.isCleanUpDerivedResources());
		}
	}

	private void intializeBuilderPreferences(IPreferenceStore store) {
		store.setDefault("autobuilding", true);
	}

	@Inject
	public void setOutputConfigurationProvider(
			OutputConfigurationProvider outputConfigurationProvider) {
		this.outputConfigurationProvider = outputConfigurationProvider;
	}

}
