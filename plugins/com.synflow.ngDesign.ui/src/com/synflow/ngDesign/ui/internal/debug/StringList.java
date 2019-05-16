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
package com.synflow.ngDesign.ui.internal.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

/**
 * This class defines a component for a list of strings that supports reordering with Drag and drop.
 *
 * @author Matthieu Wipliez
 *
 */
public class StringList {

	private class DropListener extends ViewerDropAdapter {

		public DropListener(TableViewer viewer) {
			super(viewer);
		}

		@Override
		public boolean performDrop(Object data) {
			// get element and remove from input
			String element = (String) getSelectedObject();
			input.remove(element);

			// add element before/after target
			int indexTarget = input.indexOf(getCurrentTarget());
			if (getCurrentLocation() == LOCATION_BEFORE) {
				input.add(indexTarget, element);
			} else if (getCurrentLocation() == LOCATION_AFTER) {
				input.add(indexTarget + 1, element);
			}

			// update viewer
			getViewer().refresh();
			updateLaunchConfigurationDialog.accept(null);
			return true;
		}

		@Override
		public boolean validateDrop(Object target, int operation, TransferData transferType) {
			return getCurrentLocation() != LOCATION_NONE && getCurrentLocation() != LOCATION_ON;
		}

	}

	private static class ListContentProvider implements IStructuredContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return ((List<?>) inputElement).toArray();
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	private class StringCellModifier implements ICellModifier {

		@Override
		public boolean canModify(Object element, String property) {
			return true;
		}

		@Override
		public Object getValue(Object element, String property) {
			return element;
		}

		@Override
		public void modify(Object element, String property, Object value) {
			if (element instanceof Item) {
				element = ((Item) element).getData();
			}

			int index = input.indexOf(element);
			value = ((String) value).trim();
			if ("".equals(value)) {
				// removing the whole value removes the item
				input.remove(index);
			} else {
				input.set(index, (String) value);
			}
			refresh();
		}

	}

	/**
	 * Returns a width hint for a button control.
	 */
	public static int getButtonWidthHint(Button button) {
		PixelConverter converter = new PixelConverter(button);
		int widthHint = converter.convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		return Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
	}

	private List<String> input;

	private Label label;

	private Consumer<Void> updateLaunchConfigurationDialog;

	private TableViewer viewer;

	public StringList(Group group, Consumer<Void> updateLaunchConfigurationDialog) {
		this.updateLaunchConfigurationDialog = updateLaunchConfigurationDialog;
		createControl(group);
	}

	private void createControl(Composite parent) {
		label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(SWT.BEGINNING, SWT.TOP, false, false));

		viewer = new TableViewer(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ListContentProvider());

		final Table table = viewer.getTable();

		// add text editor support
		TextCellEditor textEditor = new TextCellEditor(table);
		((Text) textEditor.getControl()).setTextLimit(60);

		CellEditor[] editors = { textEditor };
		viewer.setColumnProperties(new String[] { "column" });
		viewer.setCellEditors(editors);

		// set activation strategy to double-click
		ColumnViewerEditorActivationStrategy editorActivationStrategy = new ColumnViewerEditorActivationStrategy(
				viewer) {
			@Override
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
						&& ((MouseEvent) event.sourceEvent).button == 1;
			}
		};
		TableViewerEditor.create(viewer, editorActivationStrategy, ColumnViewerEditor.DEFAULT);

		// Set the cell modifier for the viewer
		viewer.setCellModifier(new StringCellModifier());

		// add drag and drop
		int operations = DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[] { LocalSelectionTransfer.getTransfer() };
		viewer.addDragSupport(operations, transferTypes, new DragSourceAdapter());
		viewer.addDropSupport(operations, transferTypes, new DropListener(viewer));

		// add remove support with DEL key
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.DEL) {
					IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
					if (selection.isEmpty()) {
						return;
					}

					int index = input.indexOf(selection.getFirstElement());
					input.remove(index);
					refresh();

					// update selection to allow fast removal of multiple elements
					table.select(index);
				}
			}
		});

		// set layout
		GridData gd = new GridData(SWT.FILL, SWT.TOP, true, false);
		gd.heightHint = 60;
		table.setLayoutData(gd);
	}

	/**
	 * Returns the list maintained by this component.
	 *
	 * @return
	 */
	public List<String> getList() {
		return input;
	}

	/**
	 * Sets the onClick handler for the Add button.
	 *
	 * @param fn
	 *            a function that takes a selection event and returns a string to add
	 */
	public void onClickAdd(Button button, Function<SelectionEvent, String> fn) {
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String result = fn.apply(e);
				if (result == null) {
					return;
				}

				// preventive trim in case input comes from input dialog
				// this is to be consistent with removal behavior
				result = result.trim();
				if (result.isEmpty()) {
					// happens when pressing escape in the input dialog
					return;
				}

				if (!input.contains(result)) {
					input.add(result);
					refresh();
				}
			}
		});
	}

	/**
	 * Refreshes the viewer and update the launch configuration dialog.
	 */
	private void refresh() {
		viewer.refresh();
		updateLaunchConfigurationDialog.accept(null);
	}

	/**
	 * Uses a copy of the given list as an input.
	 *
	 * @param list
	 */
	public void setInput(List<String> list) {
		this.input = new ArrayList<>(list);
		viewer.setInput(input);
	}

	/**
	 * Sets the text of the label.
	 *
	 * @param text
	 */
	public void setLabel(String text) {
		label.setText(text);
	}

}
