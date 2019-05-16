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
package com.synflow.cx.internal.scheduler;

import com.synflow.models.node.Node;

/**
 * This interface defines methods to handle the scheduling of if statements:
 * <ul>
 * <li>fork</li>
 * <li>join</li>
 * <li>startBranch</li>
 * </ul>
 *
 * @author Matthieu Wipliez
 *
 */
public interface IfBehavior {

	/**
	 * Forks at the current node.
	 *
	 * @return the current node.
	 */
	Node fork();

	/**
	 * Joins fork's branches together, collecting input/output patterns of fork's children into
	 * fork's action. This method also sets fork as the current node, but do not clear its children.
	 *
	 * @param fork
	 *            the node that was saved before visiting branches
	 */
	void join(Node fork);

	/**
	 * Starts a new 'if' branch from the given fork node (obtained from the 'fork' method).
	 *
	 * @param fork
	 *            the fork node
	 */
	void startBranch(Node fork);

}
