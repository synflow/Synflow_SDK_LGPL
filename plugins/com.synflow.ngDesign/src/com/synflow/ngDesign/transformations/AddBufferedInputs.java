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
package com.synflow.ngDesign.transformations;

import static com.synflow.models.ir.OpBinary.LOGIC_OR;
import static com.synflow.models.util.SwitchUtil.DONE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.common.collect.Iterables;
import com.synflow.core.transformations.Transformation;
import com.synflow.models.dpn.Action;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Port;
import com.synflow.models.ir.BlockBasic;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.IrFactory;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Use;
import com.synflow.models.ir.Var;
import com.synflow.models.util.EcoreHelper;
import com.synflow.models.util.Void;

/**
 * This class updates references to ports and additional signals to use generated wire signals
 * instead.
 *
 * @author Matthieu Wipliez
 *
 */
public class AddBufferedInputs extends Transformation {

	private static final DpnFactory dpn = DpnFactory.eINSTANCE;

	private static final IrFactory ir = IrFactory.eINSTANCE;

	private Actor actor;

	private Map<Var, Var> internalSignals;

	private List<Port> ports;

	public AddBufferedInputs() {
		super(null);
		internalSignals = new HashMap<>();
	}

	@Override
	public Void caseActor(Actor actor) {
		Iterable<Port> readyOutputs = dpn.getReadyPorts(actor.getOutputs());
		if (Iterables.isEmpty(readyOutputs)) {
			return DONE;
		}

		// compute buffered inputs
		// use naive assumption that all ready input ports need to be buffered
		Iterable<Port> readyInputs = dpn.getReadyPorts(actor.getInputs());
		Iterables.addAll(actor.getBufferedInputs(), readyInputs);

		// set port visitor's actor field
		this.actor = actor;
		this.ports = actor.getBufferedInputs();
		for (Port port : actor.getBufferedInputs()) {
			visitUses(port);
			for (Var var : port.getAdditionalInputs()) {
				visitUses(var);
			}
		}

		// update references to buffered ready input ports
		for (Action action : actor.getActions()) {
			resetInternalValid(action.getBody(), action.getInputPattern().getPorts());
		}

		return DONE;
	}

	@Override
	public Void caseEntity(Entity entity) {
		return DONE;
	}

	/**
	 * Returns a new expression <code>internal_port_valid ? internal_port : expr</code> where
	 * <code>expr</code> is an ExprVar of the port.
	 *
	 * @param valid
	 * @param port
	 * @param expr
	 * @return
	 */
	private Expression createExprPort(Var valid, Port port, Expression expr) {
		return ir.createExprTernary(getInternal(valid), getInternal(port), expr);
	}

	/**
	 * Returns the internal signal for the given port or port additional input. Creates it if it
	 * does not exist yet.
	 *
	 * @param var
	 *            a port or a port additional input
	 * @return an expression referencing the internal signal
	 */
	private Expression getInternal(Var var) {
		Var internal = internalSignals.get(var);
		if (internal == null) {
			internal = ir.createVar(var.getType(), "internal_" + var.getName(), true);
			actor.getVariables().add(internal);
			internalSignals.put(var, internal);
		}
		return ir.createExprVar(internal);
	}

	/**
	 * Resets the internal "valid" flag for all given buffered input ports in the given action body.
	 *
	 * @param body
	 *            body of an action
	 * @param inputs
	 *            list of ports
	 */
	public void resetInternalValid(Procedure body, List<Port> inputs) {
		BlockBasic block = body.getLast();

		for (Port port : inputs) {
			if (ports.contains(port)) {
				Var valid = internalSignals.get(port.getAdditionalInputs().get(0));
				block.add(ir.createInstStore(0, valid, ir.createExprBool(false)));
			}
		}
	}

	/**
	 * Replaces each use of the given buffered input <code>port</code> by a ternary expression of
	 * the form: <code>internal_port_valid ? internal_port : port</code>.
	 *
	 * @param port
	 */
	private void visitUses(Port port) {
		for (Use use : new ArrayList<>(port.getUses())) {
			Var valid = port.getAdditionalInputs().get(0);
			Expression expr = (Expression) use.eContainer();

			EStructuralFeature feature = expr.eContainingFeature();
			if (feature.isMany()) {
				// list (happens rarely, only in function calls)
				List<Expression> list = EcoreHelper.getContainingList(expr);
				int index = list.indexOf(expr);
				list.add(index, createExprPort(valid, port, expr));
			} else {
				// scalar (most common case)
				EObject cter = expr.eContainer();
				cter.eSet(feature, createExprPort(valid, port, expr));
			}
		}
	}

	private void visitUses(Var var) {
		for (Use use : var.getUses()) {
			Expression expr = (Expression) use.eContainer();
			EStructuralFeature feature = expr.eContainingFeature();
			EObject cter = expr.eContainer();
			cter.eSet(feature, ir.createExprBinary(getInternal(var), LOGIC_OR, expr));
		}
	}

}
