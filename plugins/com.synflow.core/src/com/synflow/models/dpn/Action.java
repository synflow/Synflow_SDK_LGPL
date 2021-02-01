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

import org.eclipse.emf.ecore.EObject;

import com.synflow.models.ir.Procedure;

/**
 * This class defines an action.
 *
 * @author Matthieu Wipliez
 * @author Samuel Keller
 * @model
 */
public interface Action extends EObject {

	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc
	 * -->Returns the procedure that holds the body of this action.<!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(Procedure)
	 * @see com.synflow.models.dpn.DpnPackage#getAction_Body()
	 * @model containment="true"
	 * @generated
	 */
	Procedure getBody();

	/**
	 * Returns the value of the '<em><b>Combinational</b></em>' containment reference. <!--
	 * begin-user-doc -->Returns the procedure that holds the combinational assignments to output
	 * ports in this action.<!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Combinational</em>' containment reference.
	 * @see #setCombinational(Procedure)
	 * @see com.synflow.models.dpn.DpnPackage#getAction_Combinational()
	 * @model containment="true"
	 * @generated
	 */
	Procedure getCombinational();

	/**
	 * Returns the value of the '<em><b>Input Pattern</b></em>' containment reference. <!--
	 * begin-user-doc -->Returns the input pattern of this action.<!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Input Pattern</em>' containment reference.
	 * @see #setInputPattern(Pattern)
	 * @see com.synflow.models.dpn.DpnPackage#getAction_InputPattern()
	 * @model containment="true"
	 * @generated
	 */
	Pattern getInputPattern();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --><!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.synflow.models.dpn.DpnPackage#getAction_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Output Pattern</b></em>' containment reference. <!--
	 * begin-user-doc -->Returns the output pattern of this action.<!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Output Pattern</em>' containment reference.
	 * @see #setOutputPattern(Pattern)
	 * @see com.synflow.models.dpn.DpnPackage#getAction_OutputPattern()
	 * @model containment="true"
	 * @generated
	 */
	Pattern getOutputPattern();

	/**
	 * Returns the value of the '<em><b>Peek Pattern</b></em>' containment reference. <!--
	 * begin-user-doc -->Returns the peek pattern of this action.<!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Peek Pattern</em>' containment reference.
	 * @see #setPeekPattern(Pattern)
	 * @see com.synflow.models.dpn.DpnPackage#getAction_PeekPattern()
	 * @model containment="true"
	 * @generated
	 */
	Pattern getPeekPattern();

	/**
	 * Returns the value of the '<em><b>Scheduler</b></em>' containment reference. <!--
	 * begin-user-doc -->Returns the procedure that holds the scheduling information of this
	 * action.<!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Scheduler</em>' containment reference.
	 * @see #setScheduler(Procedure)
	 * @see com.synflow.models.dpn.DpnPackage#getAction_Scheduler()
	 * @model containment="true"
	 * @generated
	 */
	Procedure getScheduler();

	/**
	 * Sets the value of the '{@link com.synflow.models.dpn.Action#getBody <em>Body</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Procedure value);

	/**
	 * Sets the value of the '{@link com.synflow.models.dpn.Action#getCombinational
	 * <em>Combinational</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Combinational</em>' containment reference.
	 * @see #getCombinational()
	 * @generated
	 */
	void setCombinational(Procedure value);

	/**
	 * Sets the value of the '{@link com.synflow.models.dpn.Action#getInputPattern
	 * <em>Input Pattern</em>}' containment reference. <!-- begin-user-doc -->Sets the input pattern
	 * of this action to the given pattern.<!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Input Pattern</em>' containment reference.
	 * @see #getInputPattern()
	 * @generated
	 */
	void setInputPattern(Pattern value);

	/**
	 * Sets the value of the '{@link com.synflow.models.dpn.Action#getName <em>Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * <!-- begin-user-doc -->Sets the output pattern of this action to the given pattern.<!--
	 * end-user-doc -->
	 *
	 * @param pattern
	 *            a pattern
	 */
	void setOutputPattern(Pattern pattern);

	/**
	 * <!-- begin-user-doc -->Sets the peek pattern of this action to the given pattern.<!--
	 * end-user-doc -->
	 *
	 * @param pattern
	 *            a pattern
	 */
	void setPeekPattern(Pattern pattern);

	/**
	 * Sets the value of the '{@link com.synflow.models.dpn.Action#getScheduler <em>Scheduler</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Scheduler</em>' containment reference.
	 * @see #getScheduler()
	 * @generated
	 */
	void setScheduler(Procedure value);

}
