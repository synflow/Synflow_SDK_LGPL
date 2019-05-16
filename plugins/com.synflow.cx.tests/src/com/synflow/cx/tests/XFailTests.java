/*******************************************************************************
 * Copyright (c) 2012-2015 Synflow SAS.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthieu Wipliez - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.synflow.cx.tests;

import static com.synflow.core.ICoreConstants.FILE_EXT_CX;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.junit4.validation.AssertableDiagnostics.DiagnosticPredicate;
import org.eclipse.xtext.validation.AbstractValidationDiagnostic;
import org.junit.Test;

import com.synflow.cx.CxInjectorProvider;
import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.Element;
import com.synflow.cx.cx.ExpressionString;
import com.synflow.cx.cx.Module;
import com.synflow.cx.cx.Obj;
import com.synflow.cx.cx.Pair;
import com.synflow.cx.cx.Primitive;

/**
 * This class defines Cx tests that are expected to fail.
 * 
 * @author Matthieu Wipliez
 * 
 */
@InjectWith(CxInjectorProvider.class)
public class XFailTests extends AbstractCxTest {

	/**
	 * Asserts that the entity has an expected issue.
	 * 
	 * @param entity
	 *            a Cx entity
	 */
	private void assertExpectedFail(CxEntity entity) {
		final String issue = getExpectedIssue(entity);
		if (issue == null) {
			throw new IllegalArgumentException("entity " + entity.getName()
					+ " has no \"issue\" property");
		}

		DiagnosticPredicate predicate = new DiagnosticPredicate() {
			public boolean apply(Diagnostic d) {
				if (d instanceof AbstractValidationDiagnostic) {
					AbstractValidationDiagnostic diag = (AbstractValidationDiagnostic) d;
					return issue.equals(diag.getIssueCode());
				}
				return false;
			}
		};

		tester.validate(entity).assertAny(predicate);
	}

	@Test
	public void declarations() throws Exception {
		test("Declarations");
	}

	private String getExpectedIssue(CxEntity entity) {
		Obj properties = entity.getProperties();
		for (Pair pair : properties.getMembers()) {
			if ("issue".equals(pair.getKey())) {
				Element value = pair.getValue();
				if (value instanceof Primitive) {
					Primitive primitive = (Primitive) value;
					EObject eObject = primitive.getValue();
					if (eObject instanceof ExpressionString) {
						ExpressionString expr = (ExpressionString) eObject;
						return expr.getValue();
					}
				}
			}
		}
		return null;
	}

	private void test(String name) throws Exception {
		int failed = 0;
		String fileName = "com/synflow/test/xfail/" + name + "." + FILE_EXT_CX;
		System.out.println("testing " + fileName);

		// initialize to '1' in case compileFile fails
		int total = 1;
		try {
			Module module = getModule(fileName);
			tester.validate(module);

			// set to 0
			total = 0;
			for (CxEntity entity : module.getEntities()) {
				total++;
				try {
					assertExpectedFail(entity);
				} catch (Throwable t) {
					printFailure(fileName, t);
					failed++;
				}
			}
		} catch (Throwable t) {
			printFailure(fileName, t);
			failed++;
		}

		testEnded(failed, total);
	}

}
