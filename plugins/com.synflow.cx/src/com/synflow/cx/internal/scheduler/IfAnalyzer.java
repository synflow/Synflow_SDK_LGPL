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
package com.synflow.cx.internal.scheduler;

import static com.synflow.models.util.SwitchUtil.DONE;
import static com.synflow.models.util.SwitchUtil.visit;

import java.util.List;

import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Branch;
import com.synflow.cx.cx.CxFactory;
import com.synflow.cx.cx.StatementIf;
import com.synflow.cx.services.VoidCxSwitch;
import com.synflow.models.node.Node;
import com.synflow.models.util.Void;

/**
 * This class builds an n-ary hierarchical tree that matches the conditions of the Cx code.
 *
 * @author Matthieu Wipliez
 *
 */
public class IfAnalyzer extends VoidCxSwitch {

	private Node node;

	public IfAnalyzer() {
		node = new Node();
	}

	@Override
	public Void caseBranch(Branch branch) {
		node = new Node(node, branch);
		visit(this, branch.getBody());
		node = node.getParent();
		return DONE;
	}

	@Override
	public Void caseStatementIf(StatementIf stmt) {
		if (CxUtil.isIfSimple(stmt)) {
			return DONE;
		}

		node = new Node(node, stmt);
		List<Branch> branches = stmt.getBranches();
		for (Branch branch : branches) {
			doSwitch(branch);
		}

		// if this 'if' has no 'else', we add an artificial 'else' branch
		if (branches.get(branches.size() - 1).getCondition() != null) {
			new Node(node, CxFactory.eINSTANCE.createBranch());
		}
		node = node.getParent();

		return DONE;
	}

	public Node getRoot() {
		return node;
	}

}
