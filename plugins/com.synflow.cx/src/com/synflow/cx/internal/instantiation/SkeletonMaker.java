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
package com.synflow.cx.internal.instantiation;

import static com.synflow.cx.internal.TransformerUtil.getStartLine;
import static com.synflow.models.ir.IrFactory.eINSTANCE;
import static com.synflow.models.ir.util.ValueUtil.isBool;
import static com.synflow.models.ir.util.ValueUtil.isFloat;
import static com.synflow.models.ir.util.ValueUtil.isInt;
import static com.synflow.models.ir.util.ValueUtil.isList;
import static com.synflow.models.ir.util.ValueUtil.isString;
import static com.synflow.models.util.SwitchUtil.DONE;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;

import com.google.inject.Inject;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Bundle;
import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.Instantiable;
import com.synflow.cx.cx.Module;
import com.synflow.cx.cx.Network;
import com.synflow.cx.cx.PortDef;
import com.synflow.cx.cx.Task;
import com.synflow.cx.cx.Typedef;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.InterfaceType;
import com.synflow.models.dpn.Port;
import com.synflow.models.dpn.Unit;
import com.synflow.models.dpn.util.DpnSwitch;
import com.synflow.models.ir.ExprList;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Type;
import com.synflow.models.ir.Var;
import com.synflow.models.util.Void;

/**
 * This class creates the skeleton of an IR entity: state variables, ports.
 *
 * @author Matthieu Wipliez
 *
 */
public class SkeletonMaker extends DpnSwitch<Void> {

	/**
	 * Converts a POJO into an Expression. Special case for strings, a string is converted to a list
	 * of ints.
	 *
	 * @param value
	 *            a runtime value
	 * @return an IR expression
	 */
	public static Expression getExpression(Object value) {
		if (isBool(value)) {
			return eINSTANCE.createExprBool((Boolean) value);
		} else if (isFloat(value)) {
			return eINSTANCE.createExprFloat((BigDecimal) value);
		} else if (isInt(value)) {
			return eINSTANCE.createExprInt((BigInteger) value);
		} else if (isString(value)) {
			ExprList list = eINSTANCE.createExprList();
			String str = (String) value;
			for (int i = 0; i < str.length(); i++) {
				list.getValue().add(eINSTANCE.createExprInt(str.charAt(i)));
			}
			return list;
		} else if (isList(value)) {
			ExprList list = eINSTANCE.createExprList();
			int length = Array.getLength(value);
			for (int i = 0; i < length; i++) {
				list.getValue().add(getExpression(Array.get(value, i)));
			}
			return list;
		} else {
			return null;
		}
	}

	private CxEntity cxEntity;

	@Inject
	private IInstantiator instantiator;

	@Override
	public Void caseActor(Actor actor) {
		Task task = (Task) cxEntity;
		translateTypesAndVars(actor, task.getTypes(), CxUtil.getVariables(task));
		translatePorts(actor, task);
		return DONE;
	}

	@Override
	public Void caseDPN(DPN dpn) {
		Network network = (Network) cxEntity;
		setFileAndLine(dpn, network);
		translateTypesAndVars(dpn, network.getTypes(), Collections.emptyList());
		translatePorts(dpn, network);
		return DONE;
	}

	@Override
	public Void caseUnit(Unit unit) {
		Bundle bundle = (Bundle) cxEntity;
		setFileAndLine(unit, bundle);
		translateTypesAndVars(unit, CxUtil.getTypes(bundle), CxUtil.getVariables(bundle));
		return DONE;
	}

	public void createSkeleton(CxEntity cxEntity, Entity entity) {
		this.cxEntity = cxEntity;
		try {
			doSwitch(entity);
		} finally {
			this.cxEntity = null;
		}
	}

	/**
	 * Sets filename and line number of IR entity from Cx entity.
	 *
	 * @param entity
	 *            IR entity
	 * @param cxEntity
	 *            Cx entity
	 */
	private void setFileAndLine(Entity entity, CxEntity cxEntity) {
		// set file name
		Module module = EcoreUtil2.getContainerOfType(cxEntity, Module.class);
		String fileName = CxUtil.getFileName(module);
		entity.setFileName(fileName);

		// set line number
		int lineNumber = getStartLine(cxEntity);
		entity.setLineNumber(lineNumber);
	}

	private void transformPort(final Entity entity, final Variable port) {
		InterfaceType ifType = CxUtil.getInterface(port);
		Type type = instantiator.computeType(entity, port);
		String name = port.getName();

		List<Port> ports = CxUtil.isInput(port) ? entity.getInputs() : entity.getOutputs();
		Port dpnPort = DpnFactory.eINSTANCE.createPort(type, name, ifType, ports);
		if (((PortDef) port.eContainer()).isCombinational()) {
			dpnPort.setSynchronous(false);
		}

		instantiator.putMapping(entity, port, dpnPort);
	}

	/**
	 * Translates the given Cx variable into an IR Procedure or Var.
	 *
	 * @param variable
	 * @return
	 */
	private void transformVariable(Entity entity, Variable variable) {
		int lineNumber = getStartLine(variable);
		Type type = instantiator.computeType(entity, variable);
		String name = variable.getName();

		if (CxUtil.isFunction(variable)) {
			Procedure procedure = eINSTANCE.createProcedure(name, lineNumber, type);
			entity.getProcedures().add(procedure);
			instantiator.putMapping(entity, variable, procedure);
		} else {
			boolean assignable = !CxUtil.isConstant(variable);

			// retrieve initial value (may be null)
			Object value = instantiator.evaluate(entity, variable.getValue());
			Expression init = getExpression(value);

			// create var
			Var var = eINSTANCE.createVar(lineNumber, type, name, assignable, init);

			// add to variables list of containing entity
			entity.getVariables().add(var);
			instantiator.putMapping(entity, variable, var);
		}
	}

	private void translatePorts(Entity entity, Instantiable instantiable) {
		// transform ports
		for (Variable variable : CxUtil.getPorts(instantiable.getPortDecls())) {
			transformPort(entity, variable);
		}
	}

	private void translateTypesAndVars(Entity entity, Iterable<Typedef> types,
			Iterable<Variable> variables) {
		DependencySolver solver = new DependencySolver();

		// transform variables and constant functions
		for (Variable variable : variables) {
			if (CxUtil.isConstant(variable) || !CxUtil.isFunction(variable)) {
				solver.add(variable);
			}
		}

		solver.addAll(types);

		solver.computeOrder();

		for (EObject eObject : solver.getObjects()) {
			if (eObject instanceof Variable) {
				transformVariable(entity, (Variable) eObject);
			} else if (eObject instanceof Typedef) {
				Typedef typedef = (Typedef) eObject;
				Type type = instantiator.computeType(entity, typedef.getType());
				instantiator.putMapping(entity, typedef, type);
			}
		}
	}

}
