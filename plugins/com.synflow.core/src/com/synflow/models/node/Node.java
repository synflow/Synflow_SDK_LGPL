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
 
package com.synflow.models.node;

import java.util.Iterator;

import com.google.common.base.Strings;

/**
 * This class defines a Node to create tree models. A Node has links to its parent, previous and
 * next siblings, and first and last child. All fields are updated automatically when a new Node is
 * created with an existing parent node. To get an iterable over a node's children, call the
 * {@link #getChildren()} method.
 *
 * @author Matthieu Wipliez
 */
public class Node {

	private Object content;

	private Node firstChild;

	private Node lastChild;

	private Node next;

	private Node parent;

	private Node previous;

	/**
	 * Creates a root empty node, i.e. with no parent and no content.
	 */
	public Node() {
		this(null, null);
	}

	/**
	 * Creates an empty node, i.e. with no content, and the given parent.
	 *
	 * @param parent
	 *            a parent node (may be <code>null</code>)
	 */
	public Node(Node parent) {
		this(parent, null);
	}

	/**
	 * Creates a node with the given parent and content. If <code>parent != null</code>, this node
	 * is added as a child of <code>parent</code>.
	 *
	 * @param parent
	 *            a parent node (may be <code>null</code>)
	 * @param content
	 *            an object to use as this node's content (may be <code>null</code>)
	 */
	public Node(Node parent, Object content) {
		if (parent != null) {
			parent.add(this);
		}

		this.content = content;
		this.parent = parent;
	}

	/**
	 * Creates a root node, i.e. with no parent and the given content.
	 *
	 * @param content
	 *            an object to use as this node's content
	 */
	public Node(Object content) {
		this(null, content);
	}

	/**
	 * Adds a child node to this node.
	 *
	 * @param node
	 *            a node
	 */
	private void add(Node node) {
		if (!hasChildren()) {
			lastChild = firstChild = node;
		} else {
			lastChild.addSibling(node);
			lastChild = node;
		}
	}

	/**
	 * Adds the given node as a sibling of this node.
	 *
	 * @param node
	 *            a node
	 */
	private void addSibling(Node node) {
		next = node;
		node.previous = this;
	}

	/**
	 * Removes children of this node. This method just removes links to the first and last child of
	 * this node.
	 */
	public void clearChildren() {
		firstChild = lastChild = null;
	}

	/**
	 * Deletes this node and all its descendants (children, grand-children, etc).
	 */
	public void delete() {
		if (hasChildren()) {
			removeChildren();
		}
		remove();
	}

	/**
	 * Returns an iterable over this node's children.
	 *
	 * @return an {@link Iterable}&lt;{@link Node}&gt;
	 */
	public Iterable<Node> getChildren() {
		return new NodeIterable(this);
	}

	/**
	 * Returns this node's content
	 *
	 * @return an object
	 */
	public Object getContent() {
		return content;
	}

	/**
	 * Returns this node's first child.
	 *
	 * @return a node, or <code>null</code> if this node has no children
	 */
	public Node getFirstChild() {
		return firstChild;
	}

	/**
	 * Returns this node's last child.
	 *
	 * @return a node, or <code>null</code> if this node has no children
	 */
	public Node getLastChild() {
		return lastChild;
	}

	/**
	 * Returns this node's next sibling.
	 *
	 * @return a node, or <code>null</code> if this node has no next sibling
	 */
	public Node getNextSibling() {
		return next;
	}

	/**
	 * Returns this node's parent.
	 *
	 * @return a node, or <code>null</code> if this node has no parent
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * Returns this node's previous sibling.
	 *
	 * @return a node, or <code>null</code> if this node has no previous sibling
	 */
	public Node getPreviousSibling() {
		return previous;
	}

	/**
	 * Returns a boolean indicating if this node has children.
	 *
	 * @return a boolean
	 */
	public boolean hasChildren() {
		return firstChild != null;
	}

	/**
	 * Removes this node.
	 */
	public void remove() {
		// remove incoming link from previous
		if (previous != null) {
			previous.next = next;
		}

		// remove incoming link from next
		if (next != null) {
			next.previous = previous;
		}

		// remove incoming links from parent
		if (parent != null) {
			if (parent.firstChild == this) {
				parent.firstChild = next;
			}

			if (parent.lastChild == this) {
				parent.lastChild = previous;
			}
		}

		// remove outgoing links
		parent = previous = next = null;
	}

	/**
	 * Removes children recursively.
	 */
	void removeChildren() {
		Iterator<Node> it = getChildren().iterator();
		while (it.hasNext()) {
			it.next().removeChildren();
			it.remove();
		}
	}

	/**
	 * Sets the content of this node to the given object.
	 *
	 * @param content
	 *            an object
	 */
	public void setContent(Object content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return toString(0);
	}

	private String toString(int indent) {
		StringBuilder builder = new StringBuilder(Strings.repeat("|  ", indent));
		builder.append(content);
		builder.append('\n');
		for (Node child : getChildren()) {
			builder.append(child.toString(indent + 1));
		}
		return builder.toString();
	}

}
