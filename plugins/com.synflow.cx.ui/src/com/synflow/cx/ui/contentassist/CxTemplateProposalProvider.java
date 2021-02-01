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
package com.synflow.cx.ui.contentassist;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.ui.editor.templates.ContextTypeIdHelper;
import org.eclipse.xtext.ui.editor.templates.DefaultTemplateProposalProvider;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.synflow.cx.ui.internal.CxActivator;

/**
 * This class extends the default template proposal provider to provide a better image.
 *
 * @author Matthieu Wipliez
 *
 */
@Singleton
public class CxTemplateProposalProvider extends DefaultTemplateProposalProvider {

	private Image image;

	@Inject
	public CxTemplateProposalProvider(TemplateStore templateStore, ContextTypeRegistry registry,
			ContextTypeIdHelper helper) {
		super(templateStore, registry, helper);
	}

	@Override
	public Image getImage(Template template) {
		if (image == null) {
			ImageDescriptor imageDescriptor = CxActivator.imageDescriptorFromPlugin(
					"com.synflow.cx.ui", "icons/template_obj.gif"); //$NON-NLS-1$
			image = imageDescriptor.createImage();
		}
		return image;
	}

}
