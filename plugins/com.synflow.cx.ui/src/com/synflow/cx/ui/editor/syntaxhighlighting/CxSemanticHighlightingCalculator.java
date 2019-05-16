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
package com.synflow.cx.ui.editor.syntaxhighlighting;

import static com.synflow.cx.CxConstants.NAME_LOOP;
import static com.synflow.cx.CxConstants.NAME_LOOP_DEPRECATED;
import static com.synflow.cx.CxConstants.NAME_SETUP;
import static com.synflow.cx.CxConstants.NAME_SETUP_DEPRECATED;
import static com.synflow.cx.cx.CxPackage.Literals.NAMED__NAME;
import static com.synflow.cx.cx.CxPackage.Literals.VAR_REF__OBJECTS;
import static com.synflow.cx.ui.editor.syntaxhighlighting.CxHighlightingConfiguration.DEPRECATED_ID;
import static com.synflow.cx.ui.editor.syntaxhighlighting.CxHighlightingConfiguration.SPECIAL_ID;
import static com.synflow.cx.ui.editor.syntaxhighlighting.CxHighlightingConfiguration.TYPE_ID;
import static com.synflow.models.util.SwitchUtil.CASCADE;
import static com.synflow.models.util.SwitchUtil.DONE;
import static com.synflow.models.util.SwitchUtil.visit;
import static org.eclipse.xtext.nodemodel.util.NodeModelUtils.findActualNodeFor;
import static org.eclipse.xtext.nodemodel.util.NodeModelUtils.findNodesForFeature;
import static org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration.KEYWORD_ID;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.collect.Iterables;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Bundle;
import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.ExpressionCast;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.Instantiable;
import com.synflow.cx.cx.Module;
import com.synflow.cx.cx.Network;
import com.synflow.cx.cx.Task;
import com.synflow.cx.cx.TypeRef;
import com.synflow.cx.cx.Typedef;
import com.synflow.cx.cx.VarRef;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.services.VoidCxSwitch;
import com.synflow.models.util.Void;

/**
 * This class defines a highlighting calculator for typedefs.
 *
 * @author Matthieu Wipliez
 */
public class CxSemanticHighlightingCalculator implements ISemanticHighlightingCalculator {

	private class VariableSwitch extends VoidCxSwitch {

		private IHighlightedPositionAcceptor acceptor;

		public VariableSwitch(IHighlightedPositionAcceptor acceptor) {
			this.acceptor = acceptor;
		}

		@Override
		public Void caseBundle(Bundle bundle) {
			visit(this, bundle.getDecls());
			return CASCADE;
		}

		@Override
		public Void caseCxEntity(CxEntity entity) {
			for (Typedef typeDef : entity.getTypes()) {
				addPosition(acceptor, TYPE_ID, typeDef, NAMED__NAME);
			}

			return DONE;
		}

		@Override
		public Void caseExpressionCast(ExpressionCast expr) {
			visit(this, expr.getType());
			return super.caseExpressionCast(expr);
		}

		@Override
		public Void caseExpressionVariable(ExpressionVariable expr) {
			if (expr.getProperty() != null) {
				VarRef ref = expr.getSource();
				List<INode> nodes = findNodesForFeature(ref, VAR_REF__OBJECTS);
				if (!nodes.isEmpty()) {
					addPosition(acceptor, KEYWORD_ID, nodes.get(nodes.size() - 1));
				}
			}

			return visit(this, Iterables.concat(expr.getIndexes(), expr.getParameters()));
		}

		@Override
		public Void caseInstantiable(Instantiable instantiable) {
			visit(this, CxUtil.getPorts(instantiable.getPortDecls()));
			return CASCADE;
		}

		@Override
		public Void caseNetwork(Network network) {
			visit(this, network.getInstances());
			return CASCADE;
		}

		@Override
		public Void caseTask(Task task) {
			visit(this, task.getDecls());
			return CASCADE;
		}

		@Override
		public Void caseTypeRef(TypeRef typeRef) {
			addPosition(acceptor, TYPE_ID, typeRef);
			return DONE;
		}

		@Override
		public Void caseVariable(Variable variable) {
			if (CxUtil.isFunction(variable)) {
				String name = variable.getName();
				if (NAME_SETUP.equals(name) || NAME_LOOP.equals(name)) {
					addPosition(acceptor, SPECIAL_ID, variable, NAMED__NAME);
				} else if ((NAME_SETUP_DEPRECATED.equals(name))
						|| (NAME_LOOP_DEPRECATED.equals(name))) {
					addPosition(acceptor, DEPRECATED_ID, variable, NAMED__NAME);
				}
			}

			visit(this, CxUtil.getType(variable));
			return super.caseVariable(variable);
		}

	}

	private void addPosition(IHighlightedPositionAcceptor acceptor, String id, EObject eObject) {
		addPosition(acceptor, id, findActualNodeFor(eObject));
	}

	private void addPosition(IHighlightedPositionAcceptor acceptor, String id, EObject eObject,
			EStructuralFeature feature) {
		List<INode> nodes = findNodesForFeature(eObject, feature);
		if (!nodes.isEmpty()) {
			addPosition(acceptor, id, nodes.get(0));
		}
	}

	private void addPosition(IHighlightedPositionAcceptor acceptor, String id, INode node) {
		int offset = node.getOffset();
		int length = node.getLength();
		acceptor.addPosition(offset, length, id);
	}

	@Override
	public void provideHighlightingFor(XtextResource resource,
			IHighlightedPositionAcceptor acceptor, CancelIndicator indicator) {
		if (resource == null || resource.getParseResult() == null) {
			return;
		}

		INode root = resource.getParseResult().getRootNode();
		Module module = (Module) root.getSemanticElement();
		if (module != null) {
			VariableSwitch sw = new VariableSwitch(acceptor);
			visit(sw, module.getEntities());
		}
	}

}
