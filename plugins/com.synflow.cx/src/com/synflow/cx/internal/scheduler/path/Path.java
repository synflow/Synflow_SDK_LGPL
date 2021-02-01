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
package com.synflow.cx.internal.scheduler.path;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.synflow.cx.cx.Branch;
import com.synflow.cx.cx.CxExpression;
import com.synflow.cx.services.CxPrinter;
import com.synflow.models.node.Node;

/**
 * This class defines a code path as an iterable over StatementCond.
 *
 * @author Matthieu Wipliez
 *
 */
public class Path {

	private Deque<Branch> conds;

	private Iterator<Branch> it;

	public Path() {
		conds = new ArrayDeque<>();
	}

	public void add(Node node) {
		Object content = node.getContent();
		if (content instanceof Branch) {
			Branch cond = (Branch) content;
			conds.addFirst(cond);
		}
	}

	/**
	 * Returns the next statement cond of this path.
	 *
	 * @return a StatementCond
	 */
	public Branch getNext() {
		if (it == null) {
			it = conds.iterator();
		}
		return it.next();
	}

	@Override
	public String toString() {
		return Joiner.on(", ").join(Iterables.transform(conds, new Function<Branch, String>() {
			@Override
			public String apply(Branch cond) {
				CxExpression condition = cond.getCondition();
				if (condition == null) {
					return "(else)";
				} else {
					return new CxPrinter().toString(condition);
				}
			}
		}));
	}

}
