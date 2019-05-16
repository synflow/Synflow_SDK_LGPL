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
import com.synflow.models.dpn.Port
import com.synflow.ngDesign.internal.generators.Namer
import java.util.ArrayList
import java.util.List

import static com.synflow.core.IProperties.ACTIVE_LOW
import static com.synflow.core.IProperties.PROP_ACTIVE
import static com.synflow.core.IProperties.PROP_CLOCKS
import static com.synflow.core.IProperties.PROP_RESETS
import static com.synflow.core.IProperties.PROP_TYPE
import static com.synflow.core.IProperties.RESET_ASYNCHRONOUS
import com.synflow.core.IPathResolver

/**
 * This class generates the synchronous process for an actor.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 */
class SynchronousPrinter extends ProcessPrinter {

	new(Namer namer, IPathResolver pathResolver) {
		super(namer, pathResolver)
	}

	/**
	 * Print the synchronous process which contains the body of the tasks
	 */
	def printProcess(Actor actor, List<Port> readyOutputPorts) {
		val sensitivity = new ArrayList<CharSequence>
		val resets = actor.properties.getAsJsonArray(PROP_RESETS)
		var String resetName = null
		var negateReset = false

		val hasReset = resets.size > 0
		if (hasReset) {
			val resetObj = resets.get(0).asJsonObject

			negateReset = ACTIVE_LOW.equals(resetObj.get(PROP_ACTIVE))
			resetName = resetObj.getAsJsonPrimitive('name').asString

			if (RESET_ASYNCHRONOUS.equals(resetObj.get(PROP_TYPE))) {
				val active = if (negateReset) 'negedge' else 'posedge'
				sensitivity.add(active + ' ' + resetName)
			}
		}

		// only tasks with clocks use this method, so we know we have one clock
		sensitivity.add('posedge ' + actor.properties.getAsJsonArray(PROP_CLOCKS).head.asString)

		'''
		always @(«sensitivity.join(' or ')») begin // body of «actor.simpleName»
		  «IF hasReset»
		  if («IF negateReset»~«ENDIF»«resetName») begin
		    «resetStateVars(actor)»
		    «resetPorts(actor.outputs, false)»
		    «IF actor.hasFsm»
		    FSM <= «actor.fsm.initialState.name»;
		    «ENDIF»
		  end else begin
		    «printSynchronousStuff(actor, readyOutputPorts)»
		  end
		  «ELSE /* no reset */»
		  «printSynchronousStuff(actor, readyOutputPorts)»
		  «ENDIF»
		end
		'''
	}

	def private printSynchronousStuff(Actor actor, List<Port> readyOutputPorts) {
		val seq = if (actor.hasFsm) printFsm(actor.fsm, false) else printActions(actor.actions, false)

		'''
		«FOR port : actor.bufferedInputs»
		if («port.additionalInputs.map[name].join()») begin
		  internal_«port.name»_valid <= 1'b1;
		  internal_«port.name» <= «port.name»;
		end

		«ENDFOR»
		«resetPortFlags(actor.outputs.filter[synchronous], false)»

		«IF readyOutputPorts.empty»
		«seq»
		«ELSE»
		if (stall) begin
		  if («readyOutputPorts.map([additionalInputs.map[name].join]).join(' && ')») begin
		    «FOR signal : readyOutputPorts.map[additionalOutputs.map[name].join]»
		    «signal» <= 1'b1;
		    «ENDFOR»
		    stall <= 1'b0;
		  end
		end else begin
		  «seq»
		end
		«ENDIF»
		'''
	}

	def private resetStateVars(Actor actor)
		'''
		«FOR variable : actor.variables»
			«IF variable.assignable && !variable.type.array»
				«namer.getName(variable)» <= «irPrinter.printInitialValue(variable)»;
			«ENDIF»
		«ENDFOR»
		'''

}
