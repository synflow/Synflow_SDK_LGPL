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

import static com.synflow.cx.validation.IssueCodes.ERR_CMP_ALWAYS_FALSE;
import static com.synflow.cx.validation.IssueCodes.ERR_CMP_ALWAYS_TRUE;
import static com.synflow.cx.validation.IssueCodes.ERR_DIV_MOD_NOT_CONST_POW_Of_TWO;
import static com.synflow.cx.validation.IssueCodes.ERR_ENTRY_FUNCTION_BAD_TYPE;
import static com.synflow.cx.validation.IssueCodes.ERR_EXPECTED_CONST;
import static com.synflow.cx.validation.IssueCodes.ERR_ILLEGAL_FENCE;
import static com.synflow.cx.validation.IssueCodes.ERR_LOCAL_NOT_INITIALIZED;
import static com.synflow.cx.validation.IssueCodes.ERR_MULTIPLE_READS;
import static com.synflow.cx.validation.IssueCodes.ERR_SIDE_EFFECTS_FUNCTION;
import static com.synflow.cx.validation.IssueCodes.ERR_TYPE_MISMATCH;

import java.util.ArrayList;
import java.util.List;

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
import com.synflow.cx.validation.IssueCodes;

/**
 * This class defines Cx tests that are expected to fail.
 * 
 * @author Matthieu Wipliez
 * 
 */
@InjectWith(CxInjectorProvider.class)
public class XFailTestsOld extends AbstractCxTest {

	/**
	 * Asserts that the validator produces at least one predicate that matches the given error code
	 * when validating the given object.
	 * 
	 * @param object
	 *            an object
	 * @param code
	 *            expected error code
	 */
	protected final void assertError(Module object, final String code) {
		DiagnosticPredicate predicate = new DiagnosticPredicate() {

			@Override
			public boolean apply(Diagnostic d) {
				if (d instanceof AbstractValidationDiagnostic) {
					AbstractValidationDiagnostic diag = (AbstractValidationDiagnostic) d;
					return code.equals(diag.getIssueCode());
				}
				return false;
			}

		};
		tester.validate(object).assertAny(predicate);
	}

	/**
	 * Asserts that the validator produces all given error codes when validating the given object.
	 * 
	 * @param object
	 *            an object
	 * @param code
	 *            expected error code
	 */
	protected final void assertErrors(Module object, String... codes) {
		for (String code : codes) {
			assertError(object, code);
		}
	}

	private void assertExpectedFail(Module module) {
		List<DiagnosticPredicate> predicates = new ArrayList<>();
		for (CxEntity entity : module.getEntities()) {
			final String expected = getExpectedIssue(entity);

			DiagnosticPredicate predicate = new DiagnosticPredicate() {
				public boolean apply(Diagnostic d) {
					if (d instanceof AbstractValidationDiagnostic) {
						AbstractValidationDiagnostic diag = (AbstractValidationDiagnostic) d;
						if (expected != null) {
							return expected.equals(diag.getIssueCode());
						}
					}
					return true;
				}
			};
			predicates.add(predicate);
		}

		tester.validate(module).assertAll(
				(DiagnosticPredicate[]) predicates.toArray(new DiagnosticPredicate[0]));
	}

	/**
	 * Asserts that the resource from which the given object was loaded has parse errors.
	 * 
	 * @param object
	 *            an object (top-level node of the AST)
	 */
	protected final void assertHasParseErrors(Module object) {
		assertNotNull(object);
		assertFalse(object.eResource().getErrors().isEmpty());
	}

	@Test
	public void checkAvailable1() throws Exception {
		assertError(getModule("com/synflow/test/xfail/Available1.cx"), IssueCodes.ERR_AVAILABLE);
	}

	@Test
	public void checkAvailable2() throws Exception {
		assertError(getModule("com/synflow/test/xfail/Available2.cx"), IssueCodes.ERR_AVAILABLE);
	}

	@Test
	public void checkAvailable3() throws Exception {
		assertError(getModule("com/synflow/test/xfail/Available3.cx"), IssueCodes.ERR_AVAILABLE);
	}

	@Test
	public void checkAvailable4() throws Exception {
		assertError(getModule("com/synflow/test/xfail/Available4.cx"), IssueCodes.ERR_AVAILABLE);
	}

	@Test
	public void checkCompareAlwaysFalse1() throws Exception {
		assertError(getModule("com/synflow/test/xfail/CompareAlwaysFalse1.cx"),
				ERR_CMP_ALWAYS_FALSE);
	}

	@Test
	public void checkCompareAlwaysFalse2() throws Exception {
		assertError(getModule("com/synflow/test/xfail/CompareAlwaysFalse2.cx"),
				ERR_CMP_ALWAYS_FALSE);
	}

	@Test
	public void checkCompareAlwaysTrue1() throws Exception {
		assertError(getModule("com/synflow/test/xfail/CompareAlwaysTrue1.cx"), ERR_CMP_ALWAYS_TRUE);
	}

	@Test
	public void checkCompareAlwaysTrue2() throws Exception {
		assertError(getModule("com/synflow/test/xfail/CompareAlwaysTrue2.cx"), ERR_CMP_ALWAYS_TRUE);
	}

	@Test
	public void checkDivByNonConst() throws Exception {
		assertError(getModule("com/synflow/test/xfail/DivByNonConst.cx"),
				ERR_DIV_MOD_NOT_CONST_POW_Of_TWO);
	}

	@Test
	public void checkDivByNonPowerOfTwo() throws Exception {
		assertError(getModule("com/synflow/test/xfail/DivByNonPowerOfTwo.cx"),
				ERR_DIV_MOD_NOT_CONST_POW_Of_TWO);
	}

	@Test
	public void checkDuplicate1() throws Exception {
		assertError(getModule("com/synflow/test/xfail/Duplicate1.cx"),
				IssueCodes.ERR_DUPLICATE_DECLARATIONS);

	}

	@Test
	public void checkDuplicate2() throws Exception {
		assertError(getModule("com/synflow/test/xfail/Duplicate2.cx"),
				IssueCodes.ERR_DUPLICATE_DECLARATIONS);
	}

	@Test
	public void checkDuplicate3() throws Exception {
		assertError(getModule("com/synflow/test/xfail/Duplicate3.cx"),
				IssueCodes.ERR_DUPLICATE_DECLARATIONS);
	}

	@Test
	public void checkExpectedConstantIdle() throws Exception {
		assertError(getModule("com/synflow/test/xfail/ExpectedConstantIdle.cx"), ERR_EXPECTED_CONST);
	}

	@Test
	public void checkExpectedConstantIdle2() throws Exception {
		assertError(getModule("com/synflow/test/xfail/ExpectedConstantIdle2.cx"),
				ERR_EXPECTED_CONST);
	}

	@Test
	public void checkExpectedConstantInitValue() throws Exception {
		assertError(getModule("com/synflow/test/xfail/ExpectedConstantInitValue.cx"),
				ERR_EXPECTED_CONST);
	}

	@Test
	public void checkExpectedConstantShift() throws Exception {
		assertError(getModule("com/synflow/test/xfail/ExpectedConstantShift.cx"),
				ERR_EXPECTED_CONST);
	}

	@Test
	public void checkFunctionCall1() throws Exception {
		assertError(getModule("com/synflow/test/xfail/FunctionCall1.cx"), ERR_TYPE_MISMATCH);
	}

	@Test
	public void checkFunctionCall2() throws Exception {
		assertError(getModule("com/synflow/test/xfail/FunctionCall1.cx"), ERR_TYPE_MISMATCH);
	}

	@Test
	public void checkIllegalFence1() throws Exception {
		assertError(getModule("com/synflow/test/xfail/IllegalFence1.cx"), ERR_ILLEGAL_FENCE);
	}

	@Test
	public void checkIllegalFence2() throws Exception {
		assertError(getModule("com/synflow/test/xfail/IllegalFence2.cx"), ERR_ILLEGAL_FENCE);
	}

	@Test
	public void checkIllegalFence3() throws Exception {
		assertError(getModule("com/synflow/test/xfail/IllegalFence3.cx"), ERR_ILLEGAL_FENCE);
	}

	@Test
	public void checkIllegalFence4() throws Exception {
		assertError(getModule("com/synflow/test/xfail/IllegalFence4.cx"), ERR_ILLEGAL_FENCE);
	}

	@Test
	public void checkMainFunctionIncorrectType() throws Exception {
		assertError(getModule("com/synflow/test/xfail/MainFunctionIncorrectType.cx"),
				ERR_ENTRY_FUNCTION_BAD_TYPE);
	}

	@Test
	public void checkModByNonConst() throws Exception {
		assertError(getModule("com/synflow/test/xfail/ModByNonConst.cx"),
				ERR_DIV_MOD_NOT_CONST_POW_Of_TWO);
	}

	@Test
	public void checkModByNonPowerOfTwo() throws Exception {
		assertError(getModule("com/synflow/test/xfail/ModByNonPowerOfTwo.cx"),
				ERR_DIV_MOD_NOT_CONST_POW_Of_TWO);
	}

	@Test
	public void checkMultipleReads() throws Exception {
		assertError(getModule("com/synflow/test/xfail/MultipleReads.cx"), ERR_MULTIPLE_READS);
	}

	@Test
	public void checkMultipleReads2() throws Exception {
		assertError(getModule("com/synflow/test/xfail/MultipleReads2.cx"), ERR_MULTIPLE_READS);
	}

	@Test
	public void checkMultipleReads3() throws Exception {
		assertError(getModule("com/synflow/test/xfail/MultipleReads3.cx"), ERR_MULTIPLE_READS);
	}

	@Test
	public void checkNonPowerOfTwoMultiDimensionalArray() throws Exception {
		assertError(getModule("com/synflow/test/xfail/NonPowerOfTwoMultiDimensionalArray.cx"),
				IssueCodes.ERR_ARRAY_MULTI_NON_POWER_OF_TWO);
	}

	@Test
	public void checkNoSideEffectCallFunction() throws Exception {
		assertError(getModule("com/synflow/test/xfail/NoSideEffectCallFunction.cx"),
				IssueCodes.ERR_NO_SIDE_EFFECTS);
	}

	@Test
	public void checkNoSideEffectCallFunction2() throws Exception {
		assertError(getModule("com/synflow/test/xfail/NoSideEffectCallFunction2.cx"),
				IssueCodes.ERR_NO_SIDE_EFFECTS);
	}

	@Test
	public void checkNoSideEffectFunction1() throws Exception {
		assertError(getModule("com/synflow/test/xfail/NoSideEffectFunction1.cx"),
				ERR_SIDE_EFFECTS_FUNCTION);
	}

	@Test
	public void checkNoSideEffectFunction2() throws Exception {
		assertError(getModule("com/synflow/test/xfail/NoSideEffectFunction2.cx"),
				ERR_SIDE_EFFECTS_FUNCTION);
	}

	@Test
	public void checkNoSideEffectFunction3() throws Exception {
		assertError(getModule("com/synflow/test/xfail/NoSideEffectFunction3.cx"),
				ERR_SIDE_EFFECTS_FUNCTION);
	}

	@Test
	public void checkNoType_i1() throws Exception {
		assertError(getModule("com/synflow/test/xfail/NoType_i1.cx"), IssueCodes.ERR_TYPE_ONE_BIT);
	}

	@Test
	public void checkNoType_u1() throws Exception {
		assertError(getModule("com/synflow/test/xfail/NoType_u1.cx"), IssueCodes.ERR_TYPE_ONE_BIT);
	}

	@Test
	public void checkSyntaxErrorPort() throws Exception {
		assertHasParseErrors(getModule("com/synflow/test/xfail/FailSyntaxPort.cx"));
	}

	@Test
	public void defined() throws Exception {
		test("no_init", ERR_LOCAL_NOT_INITIALIZED, "NoInit1", "NoInit2");
	}

	private String getExpectedIssue(CxEntity entity) {
		Obj properties = entity.getProperties();
		for (Pair pair : properties.getMembers()) {
			if ("expected".equals(pair.getKey())) {
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

	@Test
	public void intepreterReallyChecks() throws Exception {
		// checkExecution("com/synflow/test/xfail/XfailBadCounter.cx", false);
	}

	@Test
	public void newCheckDeclarations() throws Exception {
		assertExpectedFail(getModule("com/synflow/test/xfail/Declarations.cx"));
	}

	private void test(String pack, String code, String... tests) throws Exception {
		int failed = 0;
		for (String test : tests) {
			String name = "com/synflow/test/xfail/" + pack + "/" + test + ".cx";
			System.out.println("testing " + name);
			try {
				assertError(getModule(name), code);
			} catch (Throwable t) {
				printFailure(name, t);
				failed++;
			}
		}

		testEnded(failed, tests.length);
	}

	@Test
	public void type() throws Exception {
		test("type", ERR_TYPE_MISMATCH, "TypeMismatchAssign1", "TypeMismatchAssign2",
				"TypeMismatchAssign3", "TypeMismatchBitSelect1", "TypeMismatchBitSelect2",
				"TypeMismatchDecl", "TypeMismatchIf", "TypeMismatchIndex", "TypeMismatchRead",
				"TypeMismatchStore1", "TypeMismatchStore2", "TypeMismatchTernary1",
				"TypeMismatchTernary2", "TypeMismatchWrite");
	}

}
