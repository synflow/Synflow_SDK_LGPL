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
package com.synflow.cx.scoping;

import static com.synflow.cx.CxConstants.DIR_IN;
import static com.synflow.cx.CxConstants.DIR_OUT;
import static com.synflow.cx.CxConstants.TYPE_READS;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.scoping.impl.SimpleScope;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Block;
import com.synflow.cx.cx.Bundle;
import com.synflow.cx.cx.Connect;
import com.synflow.cx.cx.CxPackage.Literals;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Instantiable;
import com.synflow.cx.cx.Module;
import com.synflow.cx.cx.Named;
import com.synflow.cx.cx.Network;
import com.synflow.cx.cx.PortDecl;
import com.synflow.cx.cx.Statement;
import com.synflow.cx.cx.StatementLoop;
import com.synflow.cx.cx.StatementVariable;
import com.synflow.cx.cx.Task;
import com.synflow.cx.cx.VarRef;
import com.synflow.cx.cx.Variable;

/**
 * This class contains custom scoping description.
 *
 */
public class CxScopeProvider extends AbstractDeclarativeScopeProvider {

	private Iterable<IEObjectDescription> getPortDescs(final Inst inst, String direction) {
		Iterable<PortDecl> portDecls;
		Task task = inst.getTask();
		if (task == null) {
			Instantiable entity = inst.getEntity();
			if (entity == null) {
				return ImmutableSet.of();
			}
			portDecls = entity.getPortDecls();
		} else {
			portDecls = task.getPortDecls();
		}

		// names are computed as "instance.port"
		Iterable<Variable> ports = CxUtil.getPorts(portDecls, direction);
		return Iterables.transform(ports, new Function<Variable, IEObjectDescription>() {
			@Override
			public IEObjectDescription apply(Variable port) {
				QualifiedName name = QualifiedName.create(inst.getName(), port.getName());
				return new EObjectDescription(name, port, null);
			}
		});
	}

	private IScope getScope(IScope outer, VarRef ref, String direction) {
		// if the ref has a non-proxy instance
		Named named = ((InternalEList<Named>) ref.getObjects()).basicGet(0);
		if (!named.eIsProxy() && named instanceof Inst) {
			Inst inst = (Inst) named;
			return new SimpleScope(outer, getPortDescs(inst, direction));
		}

		return outer;
	}

	/**
	 * Returns the scope for a variable referenced inside a bundle. Returns the scope of global
	 * variables.
	 */
	public IScope scope_VarRef_objects(Bundle bundle, EReference reference) {
		Iterable<Variable> variables = CxUtil.getVariables(bundle);
		IScope outer = delegateGetScope(bundle, reference);
		return Scopes.scopeFor(variables, outer);
	}

	/**
	 * Returns the scope for a variable referenced inside a task. Returns the scope of global
	 * variables.
	 */
	public IScope scope_VarRef_objects(Module module, EReference reference) {
		return delegateGetScope(module, reference);
	}

	/**
	 * Returns the scope for a variable referenced inside a network.
	 */
	public IScope scope_VarRef_objects(Network network, EReference reference) {
		return delegateGetScope(network, reference);
	}

	/**
	 * Returns the scope for a variable referenced inside a statement.
	 */
	public IScope scope_VarRef_objects(Statement statement, EReference reference) {
		List<Variable> variables = new ArrayList<Variable>();

		// go up until we find a function, collecting local variables along the way
		EObject cter = statement;
		while (cter != null) {
			EObject last = cter;
			cter = cter.eContainer();

			if (cter instanceof Block) {
				Block block = (Block) cter;
				List<Statement> stmts = block.getStmts();
				int index = ECollections.indexOf(stmts, last, 0);

				// includes the current statement in the scope
				ListIterator<Statement> it = stmts.listIterator(index + 1);
				while (it.hasPrevious()) {
					Statement stmt = it.previous();
					if (stmt instanceof StatementVariable) {
						variables.addAll(((StatementVariable) stmt).getVariables());
					}
				}
			} else if (cter instanceof Variable) {
				// got up to the containing function
				break;
			} else if (cter instanceof StatementLoop) {
				// specific case for a loop, if it declares a variable take it into account
				StatementLoop loop = (StatementLoop) cter;
				Statement init = loop.getInit();
				if (init instanceof StatementVariable) {
					variables.addAll(((StatementVariable) init).getVariables());
				}
			}
		}

		// build scope (from outer to inner)
		IScope outer = getScope(cter, reference);
		if (variables.isEmpty()) {
			return outer;
		}
		return Scopes.scopeFor(variables, outer);
	}

	/**
	 * Returns the scope for a variable referenced inside a statement.
	 */
	public IScope scope_VarRef_objects(StatementLoop loop, EReference reference) {
		IScope outer = scope_VarRef_objects((Statement) loop, reference);

		Statement init = loop.getInit();
		if (init instanceof StatementVariable) {
			StatementVariable stmt = (StatementVariable) init;
			return Scopes.scopeFor(stmt.getVariables(), outer);
		}
		return outer;
	}

	/**
	 * Returns the scope for a variable referenced inside a task. Returns the scope of global
	 * variables.
	 */
	public IScope scope_VarRef_objects(Task task, EReference reference) {
		Iterable<Variable> variables = CxUtil.getVariables(task);
		Iterable<Variable> ports = CxUtil.getPorts(task.getPortDecls());
		IScope outer = delegateGetScope(task, reference);
		return Scopes.scopeFor(variables, Scopes.scopeFor(ports, outer));
	}

	/**
	 * Returns the scope for a variable referenced inside a function.
	 */
	public IScope scope_VarRef_objects(Variable function, EReference reference) {
		IScope outer = getScope(function.eContainer(), reference);
		return Scopes.scopeFor(function.getParameters(), outer);
	}

	/**
	 * Returns the scope for a variable referenced inside an expression. If used with the
	 * 'available' or 'read' property, returns the scope of input ports. Otherwise, resolves the
	 * scope with the expression's container.
	 */
	public IScope scope_VarRef_objects(VarRef ref, EReference reference) {
		IScope outer = getScope(ref.eContainer(), reference);

		EObject cter = ref.eContainer();
		EStructuralFeature feature = ref.eContainingFeature();
		if (feature == Literals.CONNECT__PORTS) {
			Connect connect = (Connect) cter;
			String direction = TYPE_READS.equals(connect.getType()) ? DIR_OUT : DIR_IN;

			return getScope(outer, ref, direction);
		} else if (feature == Literals.STATEMENT_WRITE__PORT) {
			return getScope(outer, ref, DIR_IN);
		} else /* if (feature == Literals.EXPRESSION_VARIABLE__SOURCE) */ {
			return getScope(outer, ref, null);
		}
	}

}
