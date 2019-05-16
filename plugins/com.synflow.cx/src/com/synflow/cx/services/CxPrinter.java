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
package com.synflow.cx.services;

import static com.synflow.cx.CxConstants.NAME_SIZEOF;
import static com.synflow.models.util.SwitchUtil.DONE;
import static org.eclipse.xtext.nodemodel.util.NodeModelUtils.getNode;
import static org.eclipse.xtext.nodemodel.util.NodeModelUtils.getTokenText;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;

import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Array;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.Element;
import com.synflow.cx.cx.ExpressionBinary;
import com.synflow.cx.cx.ExpressionBoolean;
import com.synflow.cx.cx.ExpressionCast;
import com.synflow.cx.cx.ExpressionFloat;
import com.synflow.cx.cx.ExpressionIf;
import com.synflow.cx.cx.ExpressionInteger;
import com.synflow.cx.cx.ExpressionList;
import com.synflow.cx.cx.ExpressionString;
import com.synflow.cx.cx.ExpressionUnary;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.Null;
import com.synflow.cx.cx.Obj;
import com.synflow.cx.cx.Pair;
import com.synflow.cx.cx.Primitive;
import com.synflow.cx.cx.TypeDecl;
import com.synflow.cx.cx.TypeRef;
import com.synflow.cx.cx.VarRef;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.cx.util.CxSwitch;
import com.synflow.models.util.Void;

/**
 * This class defines a simple Cx pretty printer (a lightweight alternative to the whole Xtext
 * serialization thing).
 *
 * @author Matthieu Wipliez
 *
 */
public class CxPrinter extends CxSwitch<Void> {

	private StringBuilder builder;

	public CxPrinter() {
		this.builder = new StringBuilder();
	}

	public CxPrinter(StringBuilder builder) {
		this.builder = builder;
	}

	@Override
	public Void caseArray(Array array) {
		builder.append('[');
		Iterator<Element> it = array.getElements().iterator();
		if (it.hasNext()) {
			doSwitch(it.next());
			while (it.hasNext()) {
				builder.append(',');
				doSwitch(it.next());
			}
		}
		builder.append(']');
		return DONE;
	}

	@Override
	public Void caseExpressionBinary(ExpressionBinary expr) {
		builder.append('(');
		doSwitch(expr.getLeft());
		builder.append(expr.getOperator());
		doSwitch(expr.getRight());
		builder.append(')');
		return DONE;
	}

	@Override
	public Void caseExpressionBoolean(ExpressionBoolean expr) {
		builder.append(expr.isValue());
		return DONE;
	}

	@Override
	public Void caseExpressionCast(ExpressionCast expr) {
		builder.append('(');
		doSwitch(expr.getType());
		builder.append(')');

		builder.append('(');
		doSwitch(expr.getExpression());
		builder.append(')');

		return DONE;
	}

	@Override
	public Void caseExpressionFloat(ExpressionFloat expr) {
		builder.append(expr.getValue());
		return DONE;
	}

	@Override
	public Void caseExpressionIf(ExpressionIf expr) {
		builder.append('(');
		doSwitch(expr.getCondition());
		builder.append('?');
		doSwitch(expr.getThen());
		builder.append(':');
		doSwitch(expr.getElse());
		builder.append(')');
		return DONE;
	}

	@Override
	public Void caseExpressionInteger(ExpressionInteger expr) {
		builder.append(expr.getValue());
		return DONE;
	}

	@Override
	public Void caseExpressionList(ExpressionList expr) {
		builder.append('{');
		Iterator<CxExpression> it = expr.getValues().iterator();
		if (it.hasNext()) {
			doSwitch(it.next());
			while (it.hasNext()) {
				builder.append(',');
				doSwitch(it.next());
			}
		}
		builder.append('}');
		return DONE;
	}

	@Override
	public Void caseExpressionString(ExpressionString expr) {
		builder.append('"');
		builder.append(expr.getValue());
		builder.append('"');
		return DONE;
	}

	@Override
	public Void caseExpressionUnary(ExpressionUnary expr) {
		boolean isSizeof = NAME_SIZEOF.equals(expr.getOperator());
		builder.append(expr.getOperator());
		if (isSizeof) {
			builder.append('(');
		}
		doSwitch(expr.getExpression());
		if (isSizeof) {
			builder.append(')');
		}
		return DONE;
	}

	@Override
	public Void caseExpressionVariable(ExpressionVariable expr) {
		doSwitch(expr.getSource());

		for (CxExpression index : expr.getIndexes()) {
			builder.append('[');
			doSwitch(index);
			builder.append(']');
		}

		String property = expr.getPropertyName();
		if (property != null) {
			builder.append('.');
			builder.append(property);
		}

		Iterator<CxExpression> it = expr.getParameters().iterator();
		if (it.hasNext()) {
			builder.append('(');
			doSwitch(it.next());
			while (it.hasNext()) {
				builder.append(',');
				doSwitch(it.next());
			}
			builder.append(')');
		}

		return DONE;
	}

	@Override
	public Void caseNull(Null null_) {
		builder.append("null");
		return DONE;
	}

	@Override
	public Void caseObj(Obj obj) {
		builder.append('{');
		Iterator<Pair> it = obj.getMembers().iterator();
		if (it.hasNext()) {
			doSwitch(it.next());
			while (it.hasNext()) {
				builder.append(',');
				doSwitch(it.next());
			}
		}
		builder.append('}');
		return DONE;
	}

	@Override
	public Void casePair(Pair pair) {
		builder.append('"');
		builder.append(pair.getKey());
		builder.append('"');
		builder.append(':');

		Element value = pair.getValue();
		if (value == null) {
			builder.append("null");
		} else {
			doSwitch(pair.getValue());
		}
		return DONE;
	}

	@Override
	public Void casePrimitive(Primitive primitive) {
		doSwitch(primitive.getValue());
		return DONE;
	}

	@Override
	public Void caseTypeDecl(TypeDecl type) {
		String spec = type.getSpec();
		if ("bool".equals(spec) || "char".equals(spec) || "float".equals(spec)
				|| "void".equals(spec)) {
			builder.append(spec);
			return DONE;
		}

		if (spec == null) {
			spec = "int";
		}

		char ch = spec.charAt(0);
		if ((ch == 'i' || ch == 'u') && Character.isDigit(spec.charAt(1))) {
			builder.append(spec);
		} else {
			if (ch == 'u') {
				builder.append("unsigned ");
				builder.append(spec.substring(1));
			} else {
				boolean signed = type.isSigned() || !type.isUnsigned();
				builder.append(signed ? "signed" : "unsigned");
				builder.append(" ");
				builder.append(spec);
			}
		}

		CxExpression size = type.getSize();
		if (size != null) {
			builder.append('<');
			doSwitch(size);
			builder.append('>');
		}

		return DONE;
	}

	@Override
	public Void caseTypeRef(TypeRef ref) {
		builder.append(getTokenText(getNode(ref)));
		return DONE;
	}

	@Override
	public Void caseVariable(Variable variable) {
		builder.append(variable.getName());
		if (CxUtil.isFunction(variable)) {
			builder.append('(');
			Iterator<Variable> it = variable.getParameters().iterator();
			if (it.hasNext()) {
				doSwitch(CxUtil.getType(it.next()));
				while (it.hasNext()) {
					builder.append(", ");
					doSwitch(CxUtil.getType(it.next()));
				}
			}
			builder.append(')');
		}
		return DONE;
	}

	@Override
	public Void caseVarRef(VarRef ref) {
		builder.append(getTokenText(getNode(ref)));
		return DONE;
	}

	@Override
	public Void doSwitch(EObject eObject) {
		if (eObject == null) {
			builder.append("null");
			return DONE;
		} else {
			return doSwitch(eObject.eClass(), eObject);
		}
	}

	public String toString(EObject eObject) {
		doSwitch(eObject);
		return builder.toString();
	}

}
