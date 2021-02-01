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
package com.synflow.cx.ui.labeling;

import static com.synflow.cx.CxConstants.NAME_LOOP;
import static com.synflow.cx.CxConstants.NAME_SETUP;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;
import org.eclipse.xtext.ui.label.StylerFactory;

import com.google.inject.Inject;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Bundle;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.CxType;
import com.synflow.cx.cx.Import;
import com.synflow.cx.cx.Imported;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Network;
import com.synflow.cx.cx.Task;
import com.synflow.cx.cx.TypeDecl;
import com.synflow.cx.cx.TypeRef;
import com.synflow.cx.cx.Typedef;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.services.CxPrinter;

/**
 * Provides labels for a EObjects.
 *
 * see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class CxLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	private StylerFactory stylerFactory;

	/**
	 * Appends " : " and the string representation of the given type, and returns a StyledString, or
	 * <code>null</code> if any of the two arguments is null.
	 *
	 * @param styledText
	 *            a styled string
	 * @param element
	 *            an object
	 * @return a StyledString or <code>null</code>
	 */
	private StyledString appendSimpleName(StyledString styledText, Object element) {
		if (styledText == null) {
			return null;
		}

		if (element == null) {
			return styledText;
		}

		String typeName = " : "
				+ (element instanceof String ? element.toString() : getText(element));
		return styledText.append(new StyledString(typeName, stylerFactory
				.createXtextStyleAdapterStyler(getTypeTextStyle())));
	}

	private Object getTypeName(CxType type) {
		return type == null ? "void" : type;
	}

	/**
	 * Returns the text style for the "type" part.
	 *
	 * @return a TextStyle instance
	 */
	private TextStyle getTypeTextStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(new RGB(149, 125, 71));
		return textStyle;
	}

	public Object image(Bundle bundle) {
		return "type_bundle.png";
	}

	public String image(Import import_) {
		return "imp_obj.gif";
	}

	public Object image(Imported imported) {
		return getImage(imported.getType());
	}

	public Object image(Inst inst) {
		if (inst.getTask() != null) {
			return "type_inner_task.png";
		}
		return getImage(inst.getEntity());
	}

	public Object image(Network network) {
		return "type_network.png";
	}

	public Object image(Task task) {
		return "type_task.png";
	}

	public String image(Typedef typeDef) {
		return "typedef.png";
	}

	public String image(Variable variable) {
		if (CxUtil.isPort(variable)) {
			String dir = CxUtil.getDirection(variable);
			if ("in".equals(dir)) {
				return "input_port.png";
			} else {
				return "output_port.png";
			}
		} else if (CxUtil.isFunction(variable)) {
			String name = variable.getName();
			if (CxUtil.isConstant(variable) || NAME_LOOP.equals(name) || NAME_SETUP.equals(name)) {
				return "methpub_obj.gif";
			} else {
				return "methpri_obj.gif";
			}
		} else if (CxUtil.isConstant(variable)) {
			return "field_public_obj.gif";
		} else {
			return "field_private_obj.gif";
		}
	}

	public String text(CxExpression expression) {
		return new CxPrinter().toString(expression);
	}

	public StyledString text(Inst inst) {
		StyledString styledText = convertToStyledString(inst.getName());
		return appendSimpleName(styledText, inst.getEntity());
	}

	public String text(TypeDecl type) {
		return new CxPrinter().toString(type);
	}

	public StyledString text(Typedef typeDef) {
		StyledString styledText = convertToStyledString(typeDef.getName());
		return appendSimpleName(styledText, getTypeName(typeDef.getType()));
	}

	public String text(TypeRef type) {
		return new CxPrinter().toString(type);
	}

	public StyledString text(Variable variable) {
		StyledString styledText = convertToStyledString(new CxPrinter().toString(variable));
		CxType type = CxUtil.getType(variable);
		return appendSimpleName(styledText, getTypeName(type));
	}

}
