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
 
package com.synflow.models.dpn;

import com.synflow.models.graph.Edge;

/**
 * This class represents a connection in a network. A connection can have a number of attributes,
 * that can be types or expressions.
 *
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * @model
 */
public interface Connection extends Edge {

	Endpoint getSourceEndpoint();

	/**
	 * Returns the value of the '<em><b>Source Port</b></em>' reference. <!-- begin-user-doc --><!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Source Port</em>' reference.
	 * @see #setSourcePort(Port)
	 * @see com.synflow.models.dpn.DpnPackage#getConnection_SourcePort()
	 * @model
	 * @generated
	 */
	Port getSourcePort();

	Endpoint getTargetEndpoint();

	/**
	 * Returns the value of the '<em><b>Target Port</b></em>' reference. <!-- begin-user-doc --><!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Target Port</em>' reference.
	 * @see #setTargetPort(Port)
	 * @see com.synflow.models.dpn.DpnPackage#getConnection_TargetPort()
	 * @model
	 * @generated
	 */
	Port getTargetPort();

	/**
	 * @generated
	 */
	void setSourcePort(Port value);

	/**
	 * @generated
	 */
	void setTargetPort(Port value);

}
