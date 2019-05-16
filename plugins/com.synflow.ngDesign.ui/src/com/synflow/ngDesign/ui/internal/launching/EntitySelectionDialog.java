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
package com.synflow.ngDesign.ui.internal.launching;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

import com.synflow.cx.cx.Instantiable;
import com.synflow.ngDesign.ui.internal.NgDesignUi;

/**
 * This class defines a custom filtered items selection dialog.
 *
 * @author Matthieu Wipliez
 *
 */
public class EntitySelectionDialog extends FilteredItemsSelectionDialog {

	/**
	 * This class defines a filter.
	 *
	 * @author Matthieu Wipliez
	 *
	 */
	private class EntityItemsFilter extends ItemsFilter {

		@Override
		public boolean isConsistentItem(Object item) {
			return item instanceof Instantiable;
		}

		@Override
		public boolean matchItem(Object item) {
			if (!isConsistentItem(item) || !Arrays.asList(instantiables).contains(item)) {
				return false;
			}
			return matches(getElementName(item));
		}

	}

	private class EntityLabelProvider extends LabelProvider {

		@Override
		public String getText(Object element) {
			return getElementName(element);
		}

	}

	private static final String SETTINGS_ID = NgDesignUi.PLUGIN_ID + ".ENTITY_SELECTION_DIALOG";

	private Instantiable[] instantiables;

	/**
	 * Creates a new filtered actors dialog.
	 *
	 * @param shell
	 *            shell
	 * @param instantiables
	 *            instantiable entities
	 */
	public EntitySelectionDialog(Shell shell, Instantiable[] instantiables, String title) {
		super(shell);
		setTitle(title);
		this.instantiables = instantiables;

		setMessage("Select &type (? = any character, * = any String):");
		setInitialPattern("**");
		ILabelProvider provider = new EntityLabelProvider();
		setListLabelProvider(provider);
		setDetailsLabelProvider(provider);
	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {
		// do nothing here
		return null;
	}

	@Override
	protected ItemsFilter createFilter() {
		return new EntityItemsFilter();
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider,
			ItemsFilter itemsFilter, IProgressMonitor progressMonitor) throws CoreException {
		if (instantiables != null && instantiables.length > 0) {
			for (int i = 0; i < instantiables.length; i++) {
				if (itemsFilter.isConsistentItem(instantiables[i])) {
					contentProvider.add(instantiables[i], itemsFilter);
				}
			}
		}
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = NgDesignUi.getDefault().getDialogSettings();
		IDialogSettings section = settings.getSection(SETTINGS_ID);
		if (section == null) {
			section = settings.addNewSection(SETTINGS_ID);
		}
		return section;
	}

	@Override
	public String getElementName(Object item) {
		if (item instanceof Instantiable) {
			return ((Instantiable) item).getName();
		}
		return null;
	}

	@Override
	protected Comparator<?> getItemsComparator() {
		Comparator<?> comp = new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				if (o1 instanceof Instantiable && o2 instanceof Instantiable) {
					return ((Instantiable) o1).getName().compareTo(((Instantiable) o2).getName());
				}
				return -1;
			}
		};
		return comp;
	}

	@Override
	protected IStatus validateItem(Object item) {
		return Status.OK_STATUS;
	}

}
