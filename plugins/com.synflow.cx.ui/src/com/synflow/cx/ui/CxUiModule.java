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

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.formatting.IWhitespaceInformationProvider;
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.ui.editor.XtextSourceViewer.Factory;
import org.eclipse.xtext.ui.editor.contentassist.ITemplateProposalProvider;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkHelper;
import org.eclipse.xtext.ui.editor.model.IResourceForEditorInputFactory;
import org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineFilterAndSorter.IComparator;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.ui.resource.SimpleResourceSetProvider;
import org.eclipse.xtext.ui.shared.Access;

import com.google.inject.Binder;
import com.google.inject.Provider;
import com.synflow.cx.formatting2.WhitespaceInfoProvider;
import com.synflow.cx.ui.containers.CxProjectsState;
import com.synflow.cx.ui.contentassist.CxTemplateProposalProvider;
import com.synflow.cx.ui.editor.hover.CxEObjectHoverProvider;
import com.synflow.cx.ui.editor.hyperlinking.CxHyperlinkHelper;
import com.synflow.cx.ui.editor.syntaxhighlighting.CxHighlightingConfiguration;
import com.synflow.cx.ui.editor.syntaxhighlighting.CxSemanticHighlightingCalculator;
import com.synflow.cx.ui.editor.syntaxhighlighting.CxTokenToIdMapper;
import com.synflow.cx.ui.outline.CxOutlineComparator;

/**
 * Use this class to register components to be used within the IDE.
 */
public class CxUiModule extends AbstractCxUiModule {

	public CxUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}

	public Class<? extends AbstractAntlrTokenToAttributeIdMapper> bindAbstractAntlrTokenToAttributeIdMapper() {
		return CxTokenToIdMapper.class;
	}

	public Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return CxEObjectHoverProvider.class;
	}

	public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
		return CxHighlightingConfiguration.class;
	}

	public Class<? extends IHyperlinkHelper> bindIHyperlinkHelper() {
		return CxHyperlinkHelper.class;
	}

	@Override
	public Class<? extends IResourceForEditorInputFactory> bindIResourceForEditorInputFactory() {
		return ResourceForIEditorInputFactory.class;
	}

	@Override
	public Class<? extends IResourceSetProvider> bindIResourceSetProvider() {
		return SimpleResourceSetProvider.class;
	}

	public Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator() {
		return CxSemanticHighlightingCalculator.class;
	}

	@Override
	public Class<? extends ITemplateProposalProvider> bindITemplateProposalProvider() {
		return CxTemplateProposalProvider.class;
	}

	@Override
	public Class<? extends IWhitespaceInformationProvider> bindIWhitespaceInformationProvider() {
		return WhitespaceInfoProvider.class;
	}

	@Override
	public Class<? extends IComparator> bindOutlineFilterAndSorter$IComparator() {
		return CxOutlineComparator.class;
	}

	public Class<? extends Factory> bindXtextSourceViewerFactory() {
		return SourceViewerFactory.class;
	}

	@Override
	public void configureBuilderPreferenceStoreInitializer(Binder binder) {
		binder.bind(IPreferenceStoreInitializer.class).to(Initializer.class);
	}

	@Override
	public Provider<IAllContainersState> provideIAllContainersState() {
		return Access.<IAllContainersState> provider(CxProjectsState.class);
	}

}
