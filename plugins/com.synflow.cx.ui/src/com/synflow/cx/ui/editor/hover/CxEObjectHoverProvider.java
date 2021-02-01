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
package com.synflow.cx.ui.editor.hover;

import static com.synflow.cx.CxConstants.DIR_IN;
import static com.synflow.cx.CxConstants.DIR_OUT;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.synflow.core.SynflowCore;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.CxType;
import com.synflow.cx.cx.ExpressionInteger;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Instantiable;
import com.synflow.cx.cx.Network;
import com.synflow.cx.cx.Task;
import com.synflow.cx.cx.Typedef;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.cx.util.CxSwitch;
import com.synflow.cx.services.CxPrinter;
import com.synflow.cx.ui.labeling.CxLabelProvider;
import com.synflow.models.dpn.InterfaceType;
import com.synflow.models.ir.util.ValueUtil;

/**
 * This class extends the default hover provider to provide additional details, concerning ports or
 * state variables.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxEObjectHoverProvider extends DefaultEObjectHoverProvider {

	private class HoverSwitch extends CxSwitch<String> {

		private Function<Variable, String> toStringPort = new Function<Variable, String>() {
			@Override
			public String apply(Variable port) {
				String repr = doSwitch(port);
				int index = repr.indexOf(' ');
				return repr.substring(index + 1);
			}
		};

		@Override
		public String caseExpressionInteger(ExpressionInteger expr) {
			return printValue(expr.getValue());
		}

		@Override
		public String caseInstantiable(Instantiable entity) {
			Iterable<Variable> portsIn = CxUtil.getPorts(entity.getPortDecls(), DIR_IN);
			Iterable<Variable> portsOut = CxUtil.getPorts(entity.getPortDecls(), DIR_OUT);
			Iterable<String> itIn = Iterables.transform(portsIn, toStringPort);
			Iterable<String> itOut = Iterables.transform(portsOut, toStringPort);

			String name = entity.getName();
			if (name == null) {
				EObject cter = entity.eContainer();
				if (cter instanceof Inst) {
					Inst inst = (Inst) cter;
					name = inst.getName();
				} else {
					name = "anonymous";
				}
			}

			StringBuilder builder = new StringBuilder(name);
			builder.append(" {<br/>");
			if (!Iterables.isEmpty(itIn)) {
				builder.append("&nbsp;&nbsp;" + DIR_IN + " ");
				Joiner.on(", ").appendTo(builder, itIn);
				builder.append(";<br/>");
			}

			if (!Iterables.isEmpty(itOut)) {
				builder.append("&nbsp;&nbsp;" + DIR_OUT + " ");
				Joiner.on(", ").appendTo(builder, itOut);
				builder.append(";<br/>");
			}

			builder.append("}");
			return builder.toString();
		}

		@Override
		public String caseNetwork(Network network) {
			return "network " + caseInstantiable(network);
		}

		@Override
		public String caseTask(Task task) {
			return "task " + caseInstantiable(task);
		}

		@Override
		public String caseTypedef(Typedef type) {
			return "typedef " + getLabel(type.getType()) + " " + type.getName();
		}

		@Override
		public String caseVariable(Variable variable) {
			StringBuilder builder = new StringBuilder();

			// constant
			if (CxUtil.isConstant(variable)) {
				builder.append("const ");
			}

			// if port, prepends with direction
			if (CxUtil.isPort(variable)) {
				String dir = CxUtil.getDirection(variable);
				builder.append(dir);
				builder.append(' ');
			}

			// type and name
			CxType type = CxUtil.getType(variable);
			if (type == null) {
				builder.append("void");
			} else {
				builder.append(getLabel(type));
			}
			builder.append(" ");
			builder.append(new CxPrinter().toString(variable));

			// if port append flags
			if (CxUtil.isPort(variable)) {
				InterfaceType iface = CxUtil.getInterface(variable);
				if (iface.isSync()) {
					builder.append(' ');
					builder.append('(');

					if (iface.isSyncAck()) {
						builder.append("ack");
					} else if (iface.isSyncReady()) {
						builder.append("ready");
					} else if (iface.isSync()) {
						builder.append("sync");
					}
					builder.append(')');
				}
			} else {
				// dimensions
				for (CxExpression value : variable.getDimensions()) {
					builder.append('[');
					builder.append(getLabel(value));
					builder.append(']');
				}

				// if state variable, append with value (if any)
				if (CxUtil.isVarDecl(variable)) {
					CxExpression value = variable.getValue();
					if (value != null) {
						builder.append(" = ");
						builder.append(getLabel(value));
					}
				}
			}

			return builder.toString();
		}

	}

	private final Switch<String> hoverSwitch = new HoverSwitch();

	@Override
	protected String getFirstLine(EObject o) {
		String line = hoverSwitch.doSwitch(o);
		if (line == null) {
			return super.getFirstLine(o);
		}

		return "<b>" + line + "</b>";
	}

	@Override
	protected String getHoverInfoAsHtml(EObject o) {
		String html = super.getHoverInfoAsHtml(o);
		if (html == null) {
			return null;
		}

		ILabelProvider provider = getLabelProvider();
		if (provider instanceof CxLabelProvider) {
			ImageDescriptor imgDesc = ((CxLabelProvider) provider).getImageDescriptor(o);
			if (imgDesc == null) {
				// no image for this element
				return html;
			}

			String name = imgDesc.getClass().getName();
			if ("org.eclipse.jface.resource.URLImageDescriptor".equals(name)) {
				try {
					Field field = imgDesc.getClass().getDeclaredField("url");
					field.setAccessible(true);

					try {
						URL url = FileLocator.toFileURL((URL) field.get(imgDesc));
						html = "<img style=\"vertical-align: middle\" src=\"" + url + "\">" + html;
					} catch (IOException e) {
						// could not access image
						SynflowCore.log(e);
					}
				} catch (ReflectiveOperationException e) {
					// too bad no image
				}
			}
		}
		return html;
	}

	@Override
	protected boolean hasHover(EObject o) {
		return o instanceof ExpressionInteger || super.hasHover(o);
	}

	private String printValue(Object value) {
		if (value == null) {
			return "?";
		} else if (ValueUtil.isInt(value)) {
			BigInteger i = (BigInteger) value;
			StringBuilder builder = new StringBuilder(i.toString());
			builder.append(" [0x");
			builder.append(i.toString(16).toUpperCase());
			builder.append("]");
			return builder.toString();
		} else if (ValueUtil.isList(value)) {
			int size = Array.getLength(value);
			StringBuilder builder = new StringBuilder();
			builder.append('{');
			if (size > 0) {
				builder.append(printValue(Array.get(value, 0)));
				for (int i = 1; i < size; i++) {
					builder.append(", ");
					builder.append(printValue(Array.get(value, i)));
				}
			}
			builder.append('}');
			return builder.toString();
		} else {
			return value.toString();
		}
	}

}
