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

import java.util.function.Predicate;

import org.junit.Test;

import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Entity;

/**
 * This abstract class defines the whole "pass" test suite. It must be extended by concrete classes.
 * 
 * @author Matthieu Wipliez
 */
public abstract class AbstractPassTests extends AbstractCxTest {

	@Test
	public void app_fifos() throws Exception {
		testApp("FIFOs");
	}

	@Test
	public void app_inner() throws Exception {
		testApp("Inner");
	}

	@Test
	public void app_rams() throws Exception {
		testApp("RAMs");
	}

	@Test
	public void app_ready() throws Exception {
		testApp("Ready");
	}

	@Test
	public void app_simple() throws Exception {
		testApp("Simple");
	}

	@Test
	public void app_synchronizers() throws Exception {
		testApp("Synchronizers");
	}

	@Test
	public void arith() throws Exception {
		testPass("Arith");
	}

	@Test
	public void array() throws Exception {
		testPass("Array");
	}

	@Test
	public void bits() throws Exception {
		testPass("Bits");
	}

	@Test
	public void bools() throws Exception {
		testPass("Bools");
	}

	@Test
	public void breaks() throws Exception {
		testPass("Breaks");
	}

	@Test
	public void calls() throws Exception {
		testPass("Calls");
	}

	/**
	 * Checks that the task with the given name executes as expected.
	 * 
	 * @param name
	 *            name of the actor
	 * @param expected
	 *            flag
	 * @throws Exception
	 *             if something goes wrong
	 */
	abstract protected void checkEntity(Entity entity, boolean expected) throws Exception;

	@Test
	public void comb() throws Exception {
		testPass("Comb");
	}

	@Test
	public void for_sync() throws Exception {
		testPass("For_sync");
	}

	@Test
	public void if_cont_easy() throws Exception {
		testPass("If_cont_easy");
	}

	@Test
	public void if_cont_hard() throws Exception {
		testPass("If_cont_hard");
	}

	@Test
	public void if_sync() throws Exception {
		testPass("If_sync");
	}

	@Test
	public void misc() throws Exception {
		testPass("Misc");
	}

	@Test
	public void signed() throws Exception {
		testPass("Signed");
	}

	final protected void test(String pack, String name, Predicate<Entity> predicate)
			throws Exception {
		int failed = 0;
		String fileName = "com/synflow/test/" + pack + "/" + name + "." + FILE_EXT_CX;
		System.out.println("testing " + fileName);

		// initialize to '1' in case compileFile fails
		int total = 1;
		try {
			Iterable<Entity> entities = compileFile(fileName);

			// set to 0
			total = 0;
			for (Entity entity : entities) {
				if (predicate.test(entity)) {
					total++;
					try {
						checkEntity(entity, true);
					} catch (Throwable t) {
						printFailure(fileName, t);
						failed++;
					}
				}
			}
		} catch (Throwable t) {
			printFailure(fileName, t);
			failed++;
		}

		testEnded(failed, total);
	}

	protected final void testApp(String name) throws Exception {
		test("app", name, entity -> entity instanceof DPN);
	}

	private void testPass(String name) throws Exception {
		test("pass", name, entity -> true);
	}

	@Test
	public void torture() throws Exception {
		testPass("Torture");
	}

}
