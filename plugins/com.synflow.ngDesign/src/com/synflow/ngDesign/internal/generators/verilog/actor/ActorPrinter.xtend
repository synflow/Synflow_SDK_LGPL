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

import com.synflow.core.IPathResolver
import com.synflow.models.dpn.Action
import com.synflow.models.dpn.Actor
import com.synflow.models.dpn.FSM
import com.synflow.models.ir.util.TypeUtil
import com.synflow.ngDesign.internal.generators.Namer
import com.synflow.ngDesign.internal.generators.verilog.VerilogIrPrinter
import java.util.ArrayList

import static com.synflow.core.IProperties.PROP_CLOCKS

import static extension com.synflow.ngDesign.internal.generators.GeneratorExtensions.isInlinable

/**
 * This class prints the inside of a module for an actor: functions, tasks, states, processes.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 */
class ActorPrinter {

	val VerilogIrPrinter irPrinter

	val IPathResolver pathResolver

	val protected Namer namer

	new(Namer namer, IPathResolver pathResolver) {
		this.namer = namer
		irPrinter = new VerilogIrPrinter(namer, pathResolver)
		this.pathResolver = pathResolver
	}

	def private printSchedulerFunctions(Actor actor)
		'''
		«FOR action : actor.actions»
			«IF !action.scheduler.inlinable»
				«printSchedulerFunction(action)»
			«ENDIF»
		«ENDFOR»
		'''

	def printActor(Actor actor) {
		val combinational = actor.properties.getAsJsonArray(PROP_CLOCKS).empty
		val readyInputPorts = actor.inputs.filter[port|port.interface.syncReady]
		val readyOutputPorts = actor.outputs.filter[port|port.interface.syncReady].toList

		// print a combinational process if the actor is combinational, or has sync ready inputs, or has asynchronous outputs
		val printCombinational = combinational || !readyInputPorts.empty || actor.outputs.exists[!synchronous]

		'''
		/**
		 * State variables
		 */
		«FOR stateVar : actor.variables»
		«irPrinter.printStateVar(actor, stateVar)»
		«ENDFOR»

		«IF !actor.procedures.empty»

		/**
		 * Functions
		 */
		«FOR proc : actor.procedures»
		«irPrinter.printFunction(proc)»
		«ENDFOR»
		«ENDIF»

		«IF actor.hasFsm»

		/**
		 * FSM
		 */
		«printStates(actor.fsm)»
		«ENDIF»

		«printSchedulerFunctions(actor)»

		«IF printCombinational»
		/**
		 * Combinational process
		 */
		«new CombinationalPrinter(namer, pathResolver).printProcess(actor, combinational)»
		«ENDIF»

		«IF !combinational»
		/**
		 * Synchronous process
		 */
		«new SynchronousPrinter(namer, pathResolver).printProcess(actor, readyOutputPorts)»
		«ENDIF»
		'''
	}

	def private printSchedulerFunction(Action action) {
		'''
		// Scheduler of «action.name» (line «action.scheduler.lineNumber»)
		function «action.scheduler.name»(input _dummy);
		  «FOR variable : action.scheduler.locals»
		  	reg «irPrinter.doSwitch(variable)»;
		  «ENDFOR»
		begin
		  «irPrinter.doSwitch(action.scheduler.blocks)»
		end
		endfunction

		'''
	}

	def private printState(String name, int i, int size) {
		var value = Integer.toBinaryString(i)
		val n = Math.max(0, size - value.length)
		'''localparam «name» = «size»'b«if (n > 0) String.format("%0" + n + "d", 0)»«value»;'''
	}

	def private printStates(FSM fsm) {
		val states = fsm.states
		val size = TypeUtil.getSize(states.size - 1)
		val parameters = new ArrayList<CharSequence>

		states.forEach[state, i|parameters += printState(state.name, i, size)]

		'''
		reg «IF size > 1 »[«size - 1» : 0] «ENDIF»FSM;

		«parameters.join('\n')»
		'''
	}

}
