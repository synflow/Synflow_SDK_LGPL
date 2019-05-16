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
package com.synflow.ngDesign.internal.generators.vhdl.actor

import com.synflow.models.dpn.Action
import com.synflow.models.dpn.Actor
import com.synflow.models.dpn.FSM
import com.synflow.models.dpn.Port
import com.synflow.models.dpn.State
import com.synflow.models.dpn.Transition
import com.synflow.ngDesign.internal.generators.Namer
import com.synflow.ngDesign.internal.generators.vhdl.VhdlIrPrinter
import java.util.ArrayList
import java.util.List

import static com.synflow.core.IProperties.ACTIVE_LOW
import static com.synflow.core.IProperties.PROP_ACTIVE
import static com.synflow.core.IProperties.PROP_CLOCKS
import static com.synflow.core.IProperties.PROP_RESETS
import static com.synflow.core.IProperties.PROP_TYPE
import static com.synflow.core.IProperties.RESET_ASYNCHRONOUS

import static extension com.synflow.ngDesign.internal.generators.GeneratorExtensions.getExpression
import static extension com.synflow.ngDesign.internal.generators.GeneratorExtensions.isInlinable

/**
 * This class extends the IR printer for load/store to pattern variables, and
 * declares methods to create the asynchronous and synchronous processes.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 */
class ProcessPrinter {

	val VhdlIrPrinter irPrinter

	val Namer namer

	new(Namer namer) {
		this.namer = namer
		irPrinter = new VhdlIrPrinter(namer)
	}

	def printActions(Actor actor)
		'''
		«FOR action : actor.actions»
		«IF !action.scheduler.inlinable»
		«printSchedulerFunction(action)»
		«ENDIF»
		«ENDFOR»
		'''

	def private printActionBody(Action action)
		'''
		to_boolean(«printSchedulerTest(action)») then
		  -- Body of «action.name» (line «action.body.lineNumber»)
		  «irPrinter.doSwitch(action.body.blocks)»
		'''

	/**
	 * Print the asynchronous process which manages the firing of the tasks
	 */
	def printAsyncProcess(Actor actor) {
		val signals = new ArrayList
		actor.inputs.forEach[port|
			signals += namer.getName(port)
			signals += port.additionalInputs.map[signal|namer.getName(signal)]
		]

		'''
		«actor.simpleName»_comb : process («signals.join(', ')») is
		  «printBodyVariables(actor)»
		begin
		  «resetPorts(actor.outputs) /* we reset all outputs, this provides a "default" case */»
		  «printSyncActions(actor.actions)»
		end process «actor.simpleName»_comb;
		'''
	}

	def private printBodyVariables(Actor actor) {
		val variables = irPrinter.computeVarMap(actor.actions.map([a|a.body]))

		'''
		«irPrinter.declareTypeList(variables)»
		«FOR variable : variables»
		variable «irPrinter.doSwitch(variable)»;
		«ENDFOR»
		'''
	}

	def private printSchedulerFunction(Action action) {
		'''
		-- Scheduler of «action.name» (line «action.scheduler.lineNumber»)
		impure function «action.scheduler.name» return std_logic is
		  «irPrinter.declareTypeList(action.scheduler.locals)»
		  «FOR variable : action.scheduler.locals»
		  variable «irPrinter.doSwitch(variable)»;
		  «ENDFOR»
		begin
		  «irPrinter.doSwitch(action.scheduler.blocks)»
		end function «action.scheduler.name»;

		'''
	}

	def protected final printSchedulerTest(Action action) {
		if (action.scheduler.inlinable) {
			irPrinter.doSwitch(action.scheduler.expression)
		} else {
			action.scheduler.name
		}
	}

	def private printSyncFsm(FSM fsm)
		'''
		case FSM is
		  «FOR state : fsm.states SEPARATOR "\n"»
		  when s_«state.name» =>
		    «printSyncTransitions(state)»
		  «ENDFOR»
		end case;
		'''

	def private printSyncTransitions(State state) {
		val transitions = state.outgoing as List<?> as List<Transition>
		'''
		«IF !transitions.empty»
		if «printActionBody(transitions.head.action)»
		  FSM <= s_«transitions.head.target.name»;
		«FOR transition : transitions.tail»
		elsif «printActionBody(transition.action)»
		  FSM <= s_«transition.target.name»;
		«ENDFOR»
		end if;
		«ENDIF»
		'''
	}

	def private printSyncActions(List<Action> actions)
		'''
		«IF !actions.empty»
		if «printActionBody(actions.head)»
		«FOR action : actions.tail»
			elsif «printActionBody(action)»
		«ENDFOR»
		end if;
		«ENDIF»
		'''

	/**
	 * Print the synchronous process which contains the body of the tasks
	 */
	def printSyncProcess(Actor actor) {
		val sensitivity = new ArrayList<CharSequence>
		val resets = actor.properties.getAsJsonArray(PROP_RESETS)
		var asynchronousReset = false
		var String resetName = null
		var negateReset = false
		var CharSequence resetCondition = null

		val hasReset = resets.size > 0
		if (hasReset) {
			val resetObj = resets.get(0).asJsonObject

			negateReset = ACTIVE_LOW.equals(resetObj.get(PROP_ACTIVE))
			resetName = resetObj.getAsJsonPrimitive('name').asString
			asynchronousReset = RESET_ASYNCHRONOUS.equals(resetObj.get(PROP_TYPE))
			if (asynchronousReset) {
				sensitivity.add(resetName)
			}
			resetCondition = '''«resetName»«IF negateReset» = '0'«ENDIF»'''
		}

		// only tasks with clocks use this method, so we know we have one clock
		val clocks = actor.properties.getAsJsonArray(PROP_CLOCKS)
		val clock = clocks.head.asString
		sensitivity.add(clock)

		'''
		«actor.simpleName»_execute : process («sensitivity.join(', ')») is
		  «printBodyVariables(actor)»
		begin
		  «IF asynchronousReset»
		  if «resetCondition» then
		    «resetActor(actor)»
		  --
		  elsif rising_edge(«clock») then
		    «printSynchronousStuff(actor)»
		  end if;
		  «ELSE»
		  if rising_edge(«clock») then
		    «IF hasReset»
		    if «resetCondition» then
		      «resetActor(actor)»
		    else
		      «printSynchronousStuff(actor)»
		    end if;
		    «ELSE»
		    «printSynchronousStuff(actor)»
		    «ENDIF»
		  end if;
		  «ENDIF»
		end process «actor.simpleName»_execute;
		'''
	}

	def private printSynchronousStuff(Actor actor)
		'''
		«FOR port : actor.outputs»
		«resetPortFlags(port)»
		«ENDFOR»
		--
		«IF actor.hasFsm»
			«printSyncFsm(actor.fsm)»
		«ELSE»
			«printSyncActions(actor.actions)»
		«ENDIF»
		'''

	def private resetActor(Actor actor)
		'''
		«resetStateVars(actor)»
		«resetPorts(actor.outputs)»
		«IF actor.hasFsm»
		FSM <= s_«actor.fsm.initialState.name»;
		«ENDIF»
		'''

	/**
	 * Resets the given port. If the port is sync, resets data and any additional output signal.
	 */
	def private resetPort(Port port) {
		'''
		«namer.getName(port)» <= «IF port.type.bool»'0'«ELSE»(others => '0')«ENDIF»;
		«resetPortFlags(port)»
		'''
	}

	/**
	 * Resets any additional output signals (if any) of the given port. If the port interface has no output signals,
	 * don't reset anything.
	 */
	def private resetPortFlags(Port port) {
		'''
		«FOR signal : port.additionalOutputs»
			«signal.name» <= '0';
		«ENDFOR»
		'''
	}

	/**
	 * Resets the given ports.
	 */
	def private resetPorts(Iterable<Port> ports)
		'''
		«FOR port : ports»
			«resetPort(port)»
		«ENDFOR»
		'''

	def private resetStateVars(Actor actor)
		'''
		«FOR variable : actor.variables»
			«IF variable.assignable»
				«namer.getName(variable)» <= «irPrinter.printInitialValue(variable)»;
			«ENDIF»
		«ENDFOR»
		'''

}
