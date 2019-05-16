/*******************************************************************************
 * Copyright (c) 2013 Synflow SAS.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthieu Wipliez - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.synflow.cx.internal.scheduler.path;

import java.util.Iterator;

import com.synflow.models.node.Node;

/**
 * This class defines an iterable over paths.
 * 
 * @author Matthieu Wipliez
 */
public class PathIterable implements Iterable<Path> {

	private Node node;

	public PathIterable(Node node) {
		this.node = node;
	}

	@Override
	public Iterator<Path> iterator() {
		return new PathIterator(node);
	}

}
