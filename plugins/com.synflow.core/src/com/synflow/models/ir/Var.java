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
 
package com.synflow.models.ir;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * This interface represents a variable. A variable has a location, a type, a name and a list of
 * uses. It may be global or not, assignable or not. It has a list of instructions where it is
 * assigned (for local variables this list has one entry). Finally, it has a value that is only used
 * by the interpreter.
 *
 * @author Matthieu Wipliez
 * @model
 */
public interface Var extends EObject {

	/**
	 * Returns the definitions of this variable. A definition is an instruction whose target is this
	 * variable. If this variable is a local scalar, at most one definition is expected.
	 *
	 * @return the definitions of this variable
	 * @model
	 */
	EList<Def> getDefs();

	/**
	 * Returns the SSA index of this variable. Valid only for local scalar variables.
	 *
	 * @return the SSA index of this variable
	 * @model
	 */
	int getIndex();

	/**
	 * Returns the initial value of this variable as an expression. Only valid for global variables.
	 *
	 * @return the initial value of this variable as an expression
	 * @model containment="true"
	 */
	Expression getInitialValue();

	/**
	 * Returns the line number of this variable.
	 *
	 * @return the line number of this variable
	 * @model
	 */
	public int getLineNumber();

	/**
	 * Returns the name of this variable.
	 *
	 * @return the name of this variable
	 * @model dataType="org.eclipse.emf.ecore.EString"
	 */
	String getName();

	/**
	 * Returns the type of this variable.
	 *
	 * @return the type of this variable
	 * @model containment="true"
	 */
	Type getType();

	/**
	 * Returns the list of uses of this variable. The list is a reference.
	 *
	 * @return the list of uses of this variable.
	 * @model
	 */
	EList<Use> getUses();

	/**
	 * Returns the current value of this variable. Used by the interpreter.
	 *
	 * @return the current value of this variable
	 * @model transient="true"
	 */
	Object getValue();

	/**
	 * Returns <code>true</code> if this variable can be assigned to.
	 *
	 * @return <code>true</code> if this variable can be assigned to
	 * @model
	 */
	boolean isAssignable();

	/**
	 * Returns true if this variable is defined at least once.
	 *
	 * @return true if this variable is defined at least once.
	 */
	boolean isDefined();

	/**
	 * Returns the value of the '<em><b>Global</b></em>' attribute. <!-- begin-user-doc -->Returns
	 * <code>true</code> if this variable is global.<!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Global</em>' attribute.
	 * @see com.synflow.models.ir.IrPackage#getVar_Global()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	boolean isGlobal();

	/**
	 * <!-- begin-user-doc -->Returns <code>true</code> if this variable is local.<!-- end-user-doc
	 * -->
	 *
	 * @model changeable="false" derived="true" transient="true" volatile="true"
	 */
	boolean isLocal();

	/**
	 * Returns true if this variable is used at least once.
	 *
	 * @return true if this variable is used at least once.
	 */
	boolean isUsed();

	/**
	 * Sets this variable as assignable or not.
	 *
	 * @param assignable
	 *            <code>true</code> if the variable is assignable
	 */
	void setAssignable(boolean assignable);

	/**
	 * Sets the SSA index of this variable. Valid only for local scalar variables.
	 *
	 * @param index
	 *            the SSA index of this variable
	 */
	void setIndex(int index);

	/**
	 * Sets the initial value of this variable as an expression.
	 *
	 * @param expression
	 *            the initial value of this variable as an expression
	 */
	void setInitialValue(Expression expression);

	/**
	 * Sets the line number of this variable.
	 *
	 * @param newLineNumber
	 *            the line number of this variable
	 */
	public void setLineNumber(int newLineNumber);

	/**
	 * Sets the name of this variable.
	 *
	 * @param name
	 *            the new name of this variable
	 */
	void setName(String name);

	/**
	 * Sets the type of this variable.
	 *
	 * @param type
	 *            the new type of this variable
	 */
	void setType(Type type);

	/**
	 * Sets the value of this variable. Used by the interpreter.
	 *
	 * @param value
	 *            the value of this variable
	 */
	void setValue(Object value);

}
