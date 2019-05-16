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
 
package com.synflow.core;

import com.google.gson.JsonPrimitive;

/**
 * This interface defines useful well-known constants.
 *
 * @author Matthieu Wipliez
 *
 */
public interface IProperties {

	JsonPrimitive ACTIVE_HIGH = new JsonPrimitive("high");

	JsonPrimitive ACTIVE_LOW = new JsonPrimitive("low");

	JsonPrimitive DEFAULT_CLOCK = new JsonPrimitive("clock");

	/**
	 * implementation: builtin
	 */
	JsonPrimitive IMPL_BUILTIN = new JsonPrimitive("builtin");

	/**
	 * implementation: external
	 */
	JsonPrimitive IMPL_EXTERNAL = new JsonPrimitive("external");

	/**
	 * active: high, low
	 */
	String PROP_ACTIVE = "active";

	/**
	 * clocks
	 */
	String PROP_CLOCKS = "clocks";

	/**
	 * comments: an object whose keys are lines and values are comments at those lines
	 */
	String PROP_COMMENTS = "comments";

	/**
	 * copyright: copyright statement (before package declaration)
	 */
	String PROP_COPYRIGHT = "copyright";

	/**
	 * dependencies: a list of strings, where each string is either a path or class name
	 */
	String PROP_DEPENDENCIES = "dependencies";

	/**
	 * domains: an association between ports and clocks, or clocks and ports
	 */
	String PROP_DOMAINS = "domains";

	/**
	 * for synthetic entites, means there are no test values
	 */
	String PROP_EMPTY = "empty";

	/**
	 * in implementation, specifies the file in which the external entity is implemented
	 */
	String PROP_FILE = "file";

	/**
	 * implementation
	 */
	String PROP_IMPLEMENTATION = "implementation";

	/**
	 * imports object
	 */
	String PROP_IMPORTS = "imports";

	/**
	 * javadoc: documentation of current task/network
	 */
	String PROP_JAVADOC = "javadoc";

	/**
	 * name. Applies to: reset.
	 */
	String PROP_NAME = "name";

	/**
	 * reset signals.
	 */
	String PROP_RESETS = "resets";

	/**
	 * when true, means that the entity was created for a testbench.
	 */
	String PROP_SYNTHETIC = "synthetic";

	/**
	 * test property
	 */
	String PROP_TEST = "test";

	/**
	 * type: synchronous, asynchronous, combinational
	 */
	String PROP_TYPE = "type";

	/**
	 * reset type: asynchronous
	 */
	JsonPrimitive RESET_ASYNCHRONOUS = new JsonPrimitive("asynchronous");

	/**
	 * reset type: synchronous
	 */
	JsonPrimitive RESET_SYNCHRONOUS = new JsonPrimitive("synchronous");

}
