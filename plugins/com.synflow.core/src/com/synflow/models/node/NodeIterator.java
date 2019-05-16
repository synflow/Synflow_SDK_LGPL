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
 
package com.synflow.models.node;

import java.util.ListIterator;

/**
 * This class defines an iterator over a node's children.
 *
 * @author Matthieu Wipliez
 *
 */
public class NodeIterator implements ListIterator<Node> {

	private Node currentNode;

	private Node referenceNode;

	public NodeIterator(Node node) {
		this.referenceNode = node;
	}

	@Override
	public void add(Node e) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the current node.
	 *
	 * @return a node
	 */
	public Node current() {
		if (currentNode == null) {
			currentNode = referenceNode.getFirstChild();
		}
		return currentNode;
	}

	/**
	 * Returns what the next node would be, but does not advance this iterator.
	 *
	 * @return a node
	 */
	public Node getNext() {
		if (currentNode == null) {
			return referenceNode.getFirstChild();
		} else {
			return currentNode.getNextSibling();
		}
	}

	/**
	 * Returns what the previous node would be, but does not advance this iterator.
	 *
	 * @return a node
	 */
	private Node getPrevious() {
		if (currentNode == null) {
			return referenceNode.getLastChild();
		} else {
			return currentNode.getPreviousSibling();
		}
	}

	@Override
	public boolean hasNext() {
		return getNext() != null;
	}

	@Override
	public boolean hasPrevious() {
		return getPrevious() != null;
	}

	@Override
	public Node next() {
		currentNode = getNext();
		return currentNode;
	}

	@Override
	public int nextIndex() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node previous() {
		currentNode = getPrevious();
		return currentNode;
	}

	@Override
	public int previousIndex() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove() {
		currentNode.remove();
		currentNode = null;
	}

	/**
	 * Resets this node iterator.
	 */
	public void reset() {
		currentNode = null;
	}

	@Override
	public void set(Node e) {
		throw new UnsupportedOperationException();
	}

}
