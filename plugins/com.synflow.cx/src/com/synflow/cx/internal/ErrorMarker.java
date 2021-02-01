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
package com.synflow.cx.internal;

import static org.eclipse.xtext.validation.ValidationMessageAcceptor.INSIGNIFICANT_INDEX;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This class defines an error marker. Like a diagnostic but more lightweight.
 *
 * @author Matthieu Wipliez
 *
 */
final public class ErrorMarker {

	private final EStructuralFeature feature;

	private final int index;

	private final String message;

	private final EObject source;

	public ErrorMarker(EObject source) {
		this(null, source, null, INSIGNIFICANT_INDEX);
	}

	public ErrorMarker(String message, EObject source) {
		this(message, source, null, INSIGNIFICANT_INDEX);
	}

	public ErrorMarker(String message, EObject source, EStructuralFeature feature) {
		this(message, source, feature, INSIGNIFICANT_INDEX);
	}

	public ErrorMarker(String message, EObject source, EStructuralFeature feature, int index) {
		this.message = message;
		this.source = source;
		this.feature = feature;
		this.index = index;
	}

	/**
	 * Creates a new marker with the given message and using the same source, feature, index as the
	 * given marker.
	 *
	 * @param marker
	 * @param message
	 */
	public ErrorMarker(String message, ErrorMarker marker) {
		this.message = message;
		this.source = marker.getSource();
		this.feature = marker.getFeature();
		this.index = marker.getIndex();
	}

	public EStructuralFeature getFeature() {
		return feature;
	}

	public int getIndex() {
		return index;
	}

	public String getMessage() {
		return message;
	}

	public EObject getSource() {
		return source;
	}

}
