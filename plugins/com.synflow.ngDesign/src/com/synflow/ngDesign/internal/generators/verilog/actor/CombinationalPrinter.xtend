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
package com.synflow.ngDesign.internal.generators.verilog.actor

import com.synflow.models.dpn.Actor
import com.synflow.ngDesign.internal.generators.Namer
import java.util.ArrayList
import com.synflow.core.IPathResolver

/**
 * This class generates the combinational process for an actor.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 */
class CombinationalPrinter extends ProcessPrinter {

	new(Namer namer, IPathResolver pathResolver) {
		super(namer, pathResolver)
	}

	/**
	 * Print a combinational process.
	 */
	def printProcess(Actor actor, boolean combinational) {
		val signals = new ArrayList
		if (combinational) {
			actor.inputs.forEach[port|
				signals += namer.getName(port)
				signals += port.additionalInputs.map[name]
			]
		}

		// workaround for empty sensitivity list
		if (signals.empty) {
			signals += '*'
		}

		'''
		always @(«signals.join(' or ')») begin
		  «resetPortFlags(actor.inputs, true)»
		  «resetPorts(actor.outputs, true)»
		  «IF actor.hasFsm»
		  «printFsm(actor.fsm, true)»
		  «ELSE»
		  «printActions(actor.actions, true)»
		  «ENDIF»
		end
		'''
	}

}
