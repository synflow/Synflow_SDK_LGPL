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
package com.synflow.cx.internal.instantiation.properties;

import com.google.gson.JsonElement;

/**
 * This interface defines a method to report an error with a given JSON element.
 *
 * @author Matthieu Wipliez
 *
 */
public interface IJsonErrorHandler {

	/**
	 * Adds an error message caused by the given JSON element.
	 *
	 * @param element
	 *            the JSON element
	 * @param message
	 *            a message
	 */
	void addError(JsonElement element, String message);

}
