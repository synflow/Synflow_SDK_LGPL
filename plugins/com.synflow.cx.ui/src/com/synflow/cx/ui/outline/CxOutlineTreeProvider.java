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
package com.synflow.cx.ui.outline;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.eclipse.xtext.ui.editor.outline.impl.EStructuralFeatureNode;

import com.google.inject.Inject;
import com.synflow.cx.cx.Connect;
import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.CxPackage.Literals;
import com.synflow.cx.cx.Import;
import com.synflow.cx.cx.Imported;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Module;
import com.synflow.cx.cx.MultiPortDecl;
import com.synflow.cx.cx.Named;
import com.synflow.cx.cx.Obj;
import com.synflow.cx.cx.PortDef;
import com.synflow.cx.cx.SinglePortDecl;
import com.synflow.cx.cx.Typedef;
import com.synflow.cx.cx.VarDecl;
import com.synflow.cx.cx.Variable;

/**
 * This class provides an outline tree for a Cx file. Most methods are declared protected so that
 * JDT does not complain of unused methods.
 *
 * @author Matthieu Wipliez
 *
 */
public class CxOutlineTreeProvider extends DefaultOutlineTreeProvider {

	@Inject
	private IImageHelper imageHelper;

	protected void _createNode(DocumentRootNode parentNode, Module module) {
		// package name
		String packageName = module.getPackage();
		createEStructuralFeatureNode(parentNode, module, Literals.MODULE__PACKAGE,
				imageHelper.getImage("package_obj.gif"), packageName, true);

		// imports
		if (!module.getImports().isEmpty()) {
			Image image = imageHelper.getImage("impc_obj.gif");
			EStructuralFeatureNode importsNode = createEStructuralFeatureNode(parentNode, module,
					Literals.MODULE__IMPORTS, image, "imports", false);
			_createChildren(importsNode, module);
		}

		// create node
		for (CxEntity entity : module.getEntities()) {
			EObjectNode node = createEObjectNode(parentNode, entity);
			createNode(node, entity);
		}
	}

	protected void _createNode(EObjectNode parent, CxEntity entity) {
		// entity imports
		if (!entity.getImports().isEmpty()) {
			Image image = imageHelper.getImage("impc_obj.gif");
			createEStructuralFeatureNode(parent, entity, Literals.CX_ENTITY__IMPORTS, image,
					"imports", false);
		}

		createChildren(parent, entity);
	}

	protected void _createNode(EObjectNode parent, Import import_) {
		// do not show imports like this, they are already handled below
	}

	protected void _createNode(EObjectNode parent, Inst inst) {
		EObjectNode node = createEObjectNode(parent, inst);
		if (inst.getTask() != null) {
			createNode(node, inst.getTask());
		}
	}

	protected void _createNode(EObjectNode parent, MultiPortDecl portDecls) {
		for (SinglePortDecl portDecl : portDecls.getDecls()) {
			createNode(parent, portDecl);
		}
	}

	protected void _createNode(EObjectNode parent, Obj object) {
		// do not show properties
	}

	protected void _createNode(EObjectNode parent, SinglePortDecl portDecls) {
		for (PortDef portDecl : portDecls.getPorts()) {
			createNode(parent, portDecl.getVar());
		}
	}

	protected void _createNode(EObjectNode parent, VarDecl stateVars) {
		for (Variable stateVar : stateVars.getVariables()) {
			createNode(parent, stateVar);
		}
	}

	protected void _createNode(EStructuralFeatureNode parent, Import import_) {
		for (Imported imported : import_.getImported()) {
			Named type = imported.getType();
			if (type != null) {
				Object text = textDispatcher.invoke(imported);
				Image image = imageDispatcher.invoke(imported);
				createEObjectNode(parent, imported, image, text, true);
			}
		}
	}

	protected boolean _isLeaf(Connect connect) {
		return true;
	}

	protected boolean _isLeaf(Inst inst) {
		return inst.getTask() == null;
	}

	protected boolean _isLeaf(Obj obj) {
		return true;
	}

	protected boolean _isLeaf(Typedef typeDef) {
		return true;
	}

	protected boolean _isLeaf(Variable variable) {
		return true;
	}

	protected String _text(Imported imported) {
		List<INode> nodes = NodeModelUtils.findNodesForFeature(imported, Literals.IMPORTED__TYPE);
		String name = NodeModelUtils.getTokenText(nodes.get(0));
		if (imported.isWildcard()) {
			name += ".*";
		}
		return name;
	}

	/**
	 * Handles null elements.
	 */
	@Override
	protected void createNode(IOutlineNode parent, EObject modelElement) {
		if (modelElement != null) {
			super.createNode(parent, modelElement);
		}
	}

}
