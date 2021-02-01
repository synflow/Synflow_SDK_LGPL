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
package com.synflow.cx;

import static com.google.inject.name.Names.named;
import static org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider.NAMED_DELEGATE;

import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.debug.IStratumBreakpointSupport;
import org.eclipse.xtext.formatting.IWhitespaceInformationProvider;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.linking.ILinker;
import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider;
import org.eclipse.xtext.linking.ILinkingService;
import org.eclipse.xtext.linking.impl.ImportedNamesAdapter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parser.antlr.ISyntaxErrorMessageProvider;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.serializer.tokens.SerializerScopeProviderBinding;

import com.google.inject.Binder;
import com.google.inject.Provider;
import com.synflow.cx.conversion.CxValueConverter;
import com.synflow.cx.debug.CxStratumBreakpointSupport;
import com.synflow.cx.formatting2.WhitespaceInfoProvider;
import com.synflow.cx.generator.CxGenerator;
import com.synflow.cx.internal.validation.CxSyntaxErrorMessageProvider;
import com.synflow.cx.linking.CxLinker;
import com.synflow.cx.linking.CxLinkingDiagnosticMessageProvider;
import com.synflow.cx.linking.CxLinkingService;
import com.synflow.cx.resource.CxResourceDescriptionManager;
import com.synflow.cx.resource.CxResourceStrategy;
import com.synflow.cx.scoping.CxGlobalScopeProvider;
import com.synflow.cx.scoping.CxImportedNamesAdapterProvider;
import com.synflow.cx.scoping.CxImportedNamespaceScopeProvider;
import com.synflow.cx.scoping.CxSerializerScopeProvider;
import com.synflow.cx.services.CxQualifiedNameProvider;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension
 * registry.
 */
@SuppressWarnings("restriction")
public class CxRuntimeModule extends AbstractCxRuntimeModule {

	public Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return CxResourceStrategy.class;
	}

	public Class<? extends IGenerator> bindIGenerator() {
		return CxGenerator.class;
	}

	@Override
	public Class<? extends IGlobalScopeProvider> bindIGlobalScopeProvider() {
		return CxGlobalScopeProvider.class;
	}

	@Override
	public Class<? extends ILinker> bindILinker() {
		return CxLinker.class;
	}

	public Class<? extends ILinkingDiagnosticMessageProvider> bindILinkingDiagnosticMessageProvider() {
		return CxLinkingDiagnosticMessageProvider.class;
	}

	@Override
	public Class<? extends ILinkingService> bindILinkingService() {
		return CxLinkingService.class;
	}

	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return CxQualifiedNameProvider.class;
	}

	public Class<? extends IResourceDescription.Manager> bindIResourceDescription$Manager() {
		return CxResourceDescriptionManager.class;
	}

	public Class<? extends IStratumBreakpointSupport> bindIStratumBreakpointSupport() {
		return CxStratumBreakpointSupport.class;
	}

	public Class<? extends ISyntaxErrorMessageProvider> bindISyntaxErrorMessageProvider() {
		return CxSyntaxErrorMessageProvider.class;
	}

	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return CxValueConverter.class;
	}

	public Class<? extends IWhitespaceInformationProvider> bindIWhitespaceInformationProvider() {
		return WhitespaceInfoProvider.class;
	}

	@Override
	public void configureIScopeProviderDelegate(Binder binder) {
		binder.bind(IScopeProvider.class).annotatedWith(named(NAMED_DELEGATE))
				.to(CxImportedNamespaceScopeProvider.class);
	}

	@Override
	public void configureSerializerIScopeProvider(Binder binder) {
		binder.bind(IScopeProvider.class).annotatedWith(SerializerScopeProviderBinding.class)
				.to(CxSerializerScopeProvider.class);
	}

	public Provider<? extends ImportedNamesAdapter> provideImportedNamesAdapter() {
		return new CxImportedNamesAdapterProvider();
	}

}
