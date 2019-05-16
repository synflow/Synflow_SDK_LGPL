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
package com.synflow.cx.ui.labeling;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

import com.google.inject.Inject;

/**
 * This class defines a label provider for the Cx navigator (Project Explorer). This delegates calls
 * to the {@link NavigatorDeclarativeLabelProvider}, and decorates images and text with the
 * {@link ProblemsLabelDecorator}.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxNavigatorLabelProvider implements ICommonLabelProvider, IStyledLabelProvider {

	private ILabelDecorator decorator;

	private ILabelProvider delegateLabelProvider;

	@Inject
	public CxNavigatorLabelProvider(NavigatorDeclarativeLabelProvider provider) {
		delegateLabelProvider = provider;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		delegateLabelProvider.addListener(listener);
	}

	@Override
	public void dispose() {
		delegateLabelProvider.dispose();
	}

	@Override
	public String getDescription(Object element) {
		// no description
		return null;
	}

	@Override
	public Image getImage(Object element) {
		Image image = delegateLabelProvider.getImage(element);
		return decorator.decorateImage(image, element);
	}

	@Override
	public StyledString getStyledText(Object element) {
		return ((IStyledLabelProvider) delegateLabelProvider).getStyledText(element);
	}

	@Override
	public String getText(Object element) {
		String text = delegateLabelProvider.getText(element);
		return decorator.decorateText(text, element);
	}

	@Override
	public void init(ICommonContentExtensionSite aConfig) {
		decorator = new ProblemsLabelDecorator();
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return delegateLabelProvider.isLabelProperty(element, property);
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		delegateLabelProvider.removeListener(listener);
	}

	@Override
	public void restoreState(IMemento aMemento) {
	}

	@Override
	public void saveState(IMemento aMemento) {
	}

}
