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
package com.synflow.cx.ui.wizards;

/**
 * This class provides a wizard to create a new Cx task.
 *
 * @author Matthieu Wipliez
 */
public class NewTaskWizard extends NewFileWizard {

	public static val WIZARD_ID = "com.synflow.cx.ui.wizards.newTask"

	override getType() {
		"task"
	}

	override getStringContents(String author, int year, String package_, String entityName)
		'''
		/*
		 * Copyright (c) «year» «author»
		 * All rights reserved.
		 */
		package «package_»;

		task «entityName» {
			void loop() {
				print("TODO «entityName»");
			}
		}
		'''

}
