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
package com.synflow.cx;

import com.google.gson.JsonPrimitive;

/**
 * This interface defines constants.
 *
 * @author Matthieu Wipliez
 *
 */
public interface CxConstants {

	/**
	 * value of the direction attribute to indicate an input port
	 */
	String DIR_IN = "in";

	/**
	 * value of the direction attribute to indicate an output port
	 */
	String DIR_OUT = "out";

	/**
	 * name of the 'loop' special function.
	 */
	String NAME_LOOP = "loop";

	String NAME_LOOP_DEPRECATED = "run";

	/**
	 * name of the 'setup' special function.
	 */
	String NAME_SETUP = "setup";

	String NAME_SETUP_DEPRECATED = "init";

	String NAME_SIZEOF = "sizeof";

	String PROP_AVAILABLE = "available";

	String PROP_LENGTH = "length";

	String PROP_READ = "read";

	String PROP_READY = "ready";

	String PROP_TYPE = "type";

	JsonPrimitive TYPE_COMBINATIONAL = new JsonPrimitive("combinational");

	/**
	 * value of the type attribute to indicate a "reads" connection
	 */
	String TYPE_READS = "reads";

	/**
	 * value of the type attribute to indicate a "writes" connection
	 */
	String TYPE_WRITES = "writes";

}
