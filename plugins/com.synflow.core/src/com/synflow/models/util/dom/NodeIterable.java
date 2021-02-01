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
 
package com.synflow.models.util.dom;

import java.util.Iterator;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.UnmodifiableIterator;

/**
 * This class defines an iterable over DOM Nodes.
 *
 * @author Matthieu Wipliez
 *
 */
public class NodeIterable implements Iterable<Node> {

	private class NodeIterator extends UnmodifiableIterator<Node> {

		private int i;

		public NodeIterator() {
			i = 0;
		}

		@Override
		public boolean hasNext() {
			return i < nodes.getLength();
		}

		@Override
		public Node next() {
			return nodes.item(i++);
		}

	}

	private NodeList nodes;

	public NodeIterable(NodeList nodes) {
		this.nodes = nodes;
	}

	@Override
	public Iterator<Node> iterator() {
		return new NodeIterator();
	}

}
