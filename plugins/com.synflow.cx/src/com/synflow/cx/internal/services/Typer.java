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
package com.synflow.cx.internal.services;

import static com.synflow.cx.CxConstants.NAME_SIZEOF;
import static com.synflow.cx.CxConstants.PROP_AVAILABLE;
import static com.synflow.cx.CxConstants.PROP_LENGTH;
import static com.synflow.cx.CxConstants.PROP_READ;
import static com.synflow.cx.CxConstants.PROP_READY;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.synflow.core.SynflowCore;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.cx.CxType;
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
import com.synflow.cx.cx.TypeDecl;
import com.synflow.cx.cx.TypeRef;
import com.synflow.cx.cx.Typedef;
import com.synflow.cx.cx.VarRef;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.cx.util.CxSwitch;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Port;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.OpBinary;
import com.synflow.models.ir.OpUnary;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.TypeArray;
import com.synflow.models.ir.util.IrUtil;
import com.synflow.models.ir.util.TypeUtil;
import com.synflow.models.ir.util.ValueUtil;

/**
 * This class defines a typer for Cx AST. Note that types must have been
 * transformed to IR types first.
 *
 * @author Matthieu Wipliez
 *
 */
public class Typer extends CxSwitch<Type> {

	private static final IrFactory ir = IrFactory.eINSTANCE;

	/**
	 * Returns an array containing the dimensions of the given type. Returns an
	 * empty array if <code>type</code> is not an array.
	 *
	 * @param type
	 *            a type
	 * @return an array of dimensions
	 */
	public static int getNumDimensions(final Type type) {
		if (type instanceof TypeArray) {
			return ((TypeArray) type).getDimensions().size();
		} else {
			return 0;
		}
	}

	private Entity entity;

	private IInstantiator instantiator;

	public Typer(IInstantiator instantiator) {
		this.instantiator = instantiator;
	}

	@Override
	public Type caseExpressionBinary(ExpressionBinary expression) {
		OpBinary op = OpBinary.getOperator(expression.getOperator());
		Type t1 = doSwitch(expression.getLeft());
		Type t2 = doSwitch(expression.getRight());

		CxExpression right = expression.getRight();
		Object amount = instantiator.evaluate(entity, right);
		return TypeUtil.getTypeBinary(op, t1, t2, amount);
	}

	@Override
	public Type caseExpressionBoolean(ExpressionBoolean expression) {
		return ir.createTypeBool();
	}

	@Override
	public Type caseExpressionCast(ExpressionCast expression) {
		return doSwitch(expression.getType());
	}

	@Override
	public Type caseExpressionFloat(ExpressionFloat expr) {
		return ir.createTypeFloat();
	}

	@Override
	public Type caseExpressionIf(ExpressionIf expression) {
		Type t1 = doSwitch(expression.getThen());
		Type t2 = doSwitch(expression.getElse());
		return TypeUtil.getLargest(t1, t2);
	}

	@Override
	public Type caseExpressionInteger(ExpressionInteger expression) {
		BigInteger value = expression.getValue();
		return ir.createTypeIntOrUint(value);
	}

	@Override
	public Type caseExpressionList(ExpressionList list) {
		List<CxExpression> values = list.getValues();
		int size = values.size();

		// compute the LUB of all expressions
		Type type = getType(values);
		return ir.createTypeArray(type, size);
	}

	@Override
	public Type caseExpressionString(ExpressionString expression) {
		return ir.createTypeString();
	}

	@Override
	public Type caseExpressionUnary(ExpressionUnary expression) {
		if (NAME_SIZEOF.equals(expression.getOperator())) {
			Object size = instantiator.evaluate(entity, expression);
			if (size == null) {
				// cannot evaluate size => returns null type
				return null;
			}
			return ir.createTypeIntOrUint((BigInteger) size);
		}

		OpUnary op = OpUnary.getOperator(expression.getOperator());
		CxExpression subExpr = expression.getExpression();
		Type type = doSwitch(subExpr);
		Object amount = instantiator.evaluate(entity, subExpr);
		return TypeUtil.getTypeUnary(op, type, amount);
	}

	@Override
	public Type caseExpressionVariable(ExpressionVariable expression) {
		VarRef ref = expression.getSource();
		Type type = doSwitch(ref);

		String prop = expression.getPropertyName();
		boolean isPort = CxUtil.isPort(ref.getVariable());
		if (PROP_AVAILABLE.equals(prop) || PROP_READY.equals(prop)) {
			return isPort ? ir.createTypeBool() : null;
		} else if (PROP_READ.equals(prop)) {
			return isPort ? doSwitch(ref) : null;
		} else if (PROP_LENGTH.equals(prop)) {
			Object length = instantiator.evaluate(entity, expression);
			return ValueUtil.isInt(length) ? ir.createTypeIntOrUint((BigInteger) length) : null;
		}

		// if type is not valid
		if (type == null || type.isArray() && ((TypeArray) type).getElementType() == null) {
			return null;
		}

		int numDims = getNumDimensions(type);
		List<CxExpression> indexes = expression.getIndexes();
		if (indexes.isEmpty()) {
			return type;
		} else if (indexes.size() > numDims + 1) {
			// too many indexes
			return null;
		} else if (indexes.size() == numDims + 1) {
			// bit selection
			return ir.createTypeBool();
		} else {
			// scalar
			type = ((TypeArray) type).getElementType();
			return IrUtil.copy(type);
		}
	}

	@Override
	public Type caseTypeDecl(TypeDecl type) {
		String spec = type.getSpec();

		if ("bool".equals(spec)) {
			return ir.createTypeBool();
		} else if ("float".equals(spec)) {
			return ir.createTypeFloat();
		} else if ("void".equals(spec)) {
			return ir.createTypeVoid();
		}

		boolean signed = type.isSigned() || !type.isUnsigned();
		int size;

		if (spec == null || spec.isEmpty()) {
			// signed or unsigned with no qualifier means implicit "int"
			// if type is used in a TypeGen the size will be overridden
			size = 32;
		} else {
			char ch = spec.charAt(0);
			String rest;
			if (ch == 'u') {
				rest = spec.substring(1);
				signed = false;
			} else {
				rest = spec;
			}

			if ("char".equals(rest)) {
				size = 8;
				signed = type.isSigned(); // unless explicitly signed, char
											// defaults to unsigned
			} else if ("short".equals(rest)) {
				size = 16;
			} else if ("int".equals(rest)) {
				size = 32;
			} else if ("long".equals(rest)) {
				size = 64;
			} else {
				// like i15 or u29
				if (rest == spec) {
					// must use substring
					rest = spec.substring(1);
				}
				size = new BigInteger(rest).intValue();
			}
		}

		if (type.getSize() != null) {
			size = instantiator.evaluateInt(entity, type.getSize());
		}

		return ir.createTypeInt(size, signed);
	}

	@Override
	public Type caseTypedef(Typedef typedef) {
		Type type = instantiator.getMapping(entity, typedef);
		return EcoreUtil.copy(type);
	}

	@Override
	public Type caseTypeRef(TypeRef type) {
		return doSwitch(type.getTypeDef());
	}

	@Override
	public Type caseVariable(Variable variable) {
		if (CxUtil.isFunction(variable) && CxUtil.isVoid(variable)) {
			return ir.createTypeVoid();
		}

		CxType varType = CxUtil.getType(variable);

		Type type = doSwitch(varType);
		List<CxExpression> dimensions = variable.getDimensions();
		if (dimensions.isEmpty()) {
			return type;
		}

		TypeArray array = ir.createTypeArray();
		array.setElementType(type);

		for (CxExpression dimension : dimensions) {
			Object value = instantiator.evaluate(entity, dimension);
			if (ValueUtil.isInt(value)) {
				array.getDimensions().add(((BigInteger) value).intValue());
			} else {
				array.getDimensions().add(0);
			}
		}
		return array;
	}

	@Override
	public Type caseVarRef(VarRef ref) {
		Variable variable = ref.getVariable();
		if (CxUtil.isPort(variable)) {
			Port port = instantiator.getPort(entity, ref);
			if (port == null) {
				printError(variable);
				return null;
			}
			return port.getType();
		}
		return doSwitch(ref.getVariable());
	}

	public void printError (Variable variable) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		try {
			root.deleteMarkers(IMarker.PROBLEM, false, IResource.DEPTH_ZERO);
			IMarker marker = root.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			marker.setAttribute(IMarker.MESSAGE,
					"There is a problem with " + entity.getName() + ". The port " + variable.getName() + " is unconnected, undifined or unknown");
		} catch (CoreException e) {
			SynflowCore.log(e);
		}
	}

	@Override
	public Type doSwitch(EObject eObject) {
		if (eObject == null) {
			return null;
		}

		return super.doSwitch(eObject);
	}

	/**
	 * Computes and returns the type of the given object.
	 *
	 * @param eObject
	 *            an AST node
	 * @return the type of the given object
	 */
	public Type getType(Entity entity, EObject eObject) {
		Entity oldEntity = this.entity;
		this.entity = entity;
		try {
			return doSwitch(eObject);
		} finally {
			this.entity = oldEntity;
		}
	}

	/**
	 * Returns the type of the given list of objects using their URI.
	 *
	 * @param eObject
	 *            an AST node
	 * @return the type of the given object
	 */
	public Type getType(List<? extends EObject> eObjects) {
		Iterator<? extends EObject> it = eObjects.iterator();
		if (!it.hasNext()) {
			return null;
		}

		Type type = doSwitch(it.next());
		while (it.hasNext()) {
			type = TypeUtil.getLargest(type, doSwitch(it.next()));
		}

		return type;
	}

}
