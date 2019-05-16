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
package com.synflow.ngDesign.internal.generators.vhdl.dpn

import com.synflow.models.dpn.DPN
import com.synflow.models.dpn.Endpoint
import com.synflow.models.dpn.Instance
import com.synflow.models.dpn.Port
import com.synflow.models.ir.TypeInt
import com.synflow.ngDesign.internal.generators.Namer
import java.util.ArrayList
import java.util.List

import static com.synflow.core.IProperties.PROP_CLOCKS
import static com.synflow.core.IProperties.PROP_RESETS
import static com.synflow.ngDesign.internal.generators.GeneratorExtensions.getSignal
import static com.synflow.ngDesign.internal.generators.GeneratorExtensions.isSignalNeeded

/**
 * This class defines a VHDL module (actor or unit) printer.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 */
class DpnPrinter {

	val Namer namer

	new(Namer namer) {
		this.namer = namer
	}

	def private addMappings(List<CharSequence> mappings, DPN network, Instance instance) {
		for (connection : network.getIncoming(instance)) {
			val signal = getSignal(namer, connection.sourceEndpoint)
			val port = connection.targetPort
			addMappings(mappings, port, signal)
		}

		val entity = instance.entity
		for (port : entity.outputs) {
			val endpoint = new Endpoint(instance, port)
			val outgoing = network.getOutgoing(endpoint)
			val signal =
				if (isSignalNeeded(outgoing)) {
					// output port connected to a signal
					getSignal(namer, endpoint)
				} else if (outgoing.head !== null) {
					// port directly connected to the target output port
					outgoing.head.port.name
				} else {
					// unconnected output port
					"open"
				}
			addMappings(mappings, port, signal)
		}
	}

	/**
	 * Adds a mapping from "port" to "signal" to the given mappings list.
	 * The characteristics of the port are given by the "port" parameter
	 */
	def private addMappings(List<CharSequence> mappings, Port port, String signal) {
		mappings.add('''«port.name» => «signal»''')
		for (flag : port.interface.signals) {
			mappings.add('''«port.name»_«flag» => «IF signal !== null»«IF signal != "open"»«signal»_«flag»«ELSE»open«ENDIF»«ENDIF»''')
		}
	}

	/**
	 * Prints assigns to the given port when necessary
	 */
	def private printAssigns(DPN network, Port port) {
		val endpoint = network.getIncoming(port)
		val outgoing = network.getOutgoing(endpoint)

		if (isSignalNeeded(outgoing)) {
			val signal = getSignal(namer, endpoint)

			'''
			«port.name» <= «signal»;
			«FOR flag : port.interface.signals»
			«port.name»_«flag»  <= «signal»_«flag»;
			«ENDFOR»
			'''
		}
	}

	def printDPN(DPN network)
		'''
		------------------------------------------------------------------------------
		architecture rtl_«network.simpleName» of «network.simpleName» is

		  ---------------------------------------------------------------------------
		  -- Signals declaration
		  ---------------------------------------------------------------------------
		  «FOR instance : network.instances»
		  «printSignals(network, instance)»
		  «ENDFOR»
		  ---------------------------------------------------------------------------

		begin

		  ---------------------------------------------------------------------------
		  -- Actors and Networks
		  ---------------------------------------------------------------------------
		  «FOR instance : network.instances SEPARATOR "\n"»
		  «printInstanceMapping(network, instance)»
		  «ENDFOR»

		  «FOR port : network.outputs»
		  «printAssigns(network, port)»
		  «ENDFOR»

		end architecture rtl_«network.simpleName»;
		'''

	def private printInstanceMapping(DPN network, Instance instance) {
		val List<CharSequence> mappings = new ArrayList
		val entity = instance.entity

		// clocks
		val clocks = instance.properties.getAsJsonObject(PROP_CLOCKS)
		mappings += clocks.entrySet.map[pair|'''«pair.key» => «pair.value.asString»''']

		// resets
		val resets = instance.properties.getAsJsonObject(PROP_RESETS)
		mappings += resets.entrySet.map[pair|'''«pair.key» => «pair.value.asString»''']

		// ports
		addMappings(mappings, network, instance)

		'''
		«namer.getName(instance)» : entity work.«namer.getName(entity)»
		  «IF !instance.arguments.empty»generic map(
		    «FOR arg: instance.arguments SEPARATOR ","»
		    «arg.variable.name» => «new ArgumentPrinter().doSwitch(arg.value)»
		    «ENDFOR»
		  )
		«ENDIF»
		  port map (
		    «mappings.join(",\n")»
		  );
		'''
	}

	/**
	 * If the port is TypeInt, prints "std_logic_vector(size - 1 downto 0)".
	 * If the port is TypeBool, prints "std_logic"
	 */
	def printPortType(Port port) {
		if (port.type.int) {
			val typeInt = port.type as TypeInt
			'''std_logic_vector(«typeInt.size - 1» downto 0)'''
		} else {
			'''std_logic'''
		}
	}

	/**
	 * Declares signals for each output port of the given instance when needed,
	 * as determined by the isSignalNeeded function.
	 */
	def private printSignals(DPN network, Instance instance) {
		val signals = new ArrayList<CharSequence>

		val entity = instance.entity
		for (port : entity.outputs) {
			// add one signal for each output port when needed
			val endpoint = new Endpoint(instance, port)
			val outgoing = network.getOutgoing(endpoint)
			if (isSignalNeeded(outgoing)) {
				val signal = getSignal(namer, endpoint)
				signals.add('''signal «signal» : «printPortType(port)»;''')
				for (flag : port.interface.signals) {
					signals.add('''signal «signal»_«flag» : std_logic;''')
				}
			}
		}

		'''
		«IF !signals.empty»
		-- Module : «instance.name»
		«signals.join("\n")»
		«ENDIF»
		'''
	}

}
