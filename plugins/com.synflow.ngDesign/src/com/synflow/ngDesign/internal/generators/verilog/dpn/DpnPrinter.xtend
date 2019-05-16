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
package com.synflow.ngDesign.internal.generators.verilog.dpn

import com.synflow.core.IPathResolver
import com.synflow.models.dpn.DPN
import com.synflow.models.dpn.Endpoint
import com.synflow.models.dpn.Instance
import com.synflow.models.dpn.Port
import com.synflow.ngDesign.internal.generators.Namer
import com.synflow.ngDesign.internal.generators.verilog.VerilogIrPrinter
import java.util.ArrayList
import java.util.List

import static com.synflow.core.IProperties.PROP_CLOCKS
import static com.synflow.core.IProperties.PROP_RESETS
import static com.synflow.ngDesign.internal.generators.GeneratorExtensions.getSignal
import static com.synflow.ngDesign.internal.generators.GeneratorExtensions.isSignalNeeded

/**
 * This class defines a printer for DPN.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 */
class DpnPrinter {

	val Namer namer

	val VerilogIrPrinter irPrinter

	new(Namer namer, IPathResolver pathResolver) {
		this.namer = namer
		irPrinter = new VerilogIrPrinter(namer, pathResolver)
	}

	/**
	 * Adds a mapping from "port" to "wire" to the given mappings list.
	 * The characteristics of the port are given by the "port" parameter
	 */
	def private addMappings(List<CharSequence> mappings, Port port, String wire) {
		mappings.add('''.«namer.getName(port)»(«wire»)''')
		for (signal : port.interface.signals) {
			mappings.add('''.«port.name»_«signal»(«IF wire !== null»«wire»_«signal»«ENDIF»)''')
		}
	}

	def private addMappings(List<CharSequence> mappings, DPN network, Instance instance) {
		for (connection : network.getIncoming(instance)) {
			val wire = getSignal(namer, connection.sourceEndpoint)
			val port = connection.targetPort
			addMappings(mappings, port, wire)
		}

		val entity = instance.entity
		for (port : entity.outputs) {
			val endpoint = new Endpoint(instance, port)
			val outgoing = network.getOutgoing(endpoint)
			val wire =
				if (isSignalNeeded(outgoing)) {
					// output port connected to a wire
					getSignal(namer, endpoint)
				} else if (outgoing.head !== null) {
					// port directly connected to the target output port
					namer.getName(outgoing.head.port)
				} else {
					// unconnected output port
					null
				}
			addMappings(mappings, port, wire)
		}
	}

	def printDPN(DPN network) {
		'''
		/**
		 * Wires
		 */
		«FOR instance : network.instances»
		«printWires(network, instance)»
		«ENDFOR»

		/**
		 * Instances
		 */
		«FOR instance : network.instances SEPARATOR "\n"»
		«printInstanceMapping(network, instance)»
		«ENDFOR»

		/**
		 * Assignments to output ports
		 */
		«FOR port : network.outputs»
		«printAssigns(network, port)»
		«ENDFOR»
		'''
	}

	/**
	 * Prints assigns to the given port when necessary
	 */
	def private printAssigns(DPN network, Port port) {
		val endpoint = network.getIncoming(port)
		val outgoing = network.getOutgoing(endpoint)
		if (isSignalNeeded(outgoing)) {
			val wire = getSignal(namer, endpoint)

			'''
			assign «namer.getName(port)» = «wire»;
			«FOR signal : port.interface.signals»
			assign «port.name»_«signal» = «wire»_«signal»;
			«ENDFOR»
			'''
		}
	}

	def private printInstanceMapping(DPN network, Instance instance) {
		val List<CharSequence> mappings = new ArrayList
		val entity = instance.entity

		// clocks
		val clocks = instance.properties.getAsJsonObject(PROP_CLOCKS)
		mappings += clocks.entrySet.map[pair|'''.«pair.key»(«pair.value.asString»)''']

		// resets
		val resets = instance.properties.getAsJsonObject(PROP_RESETS)
		mappings += resets.entrySet.map[pair|'''.«pair.key»(«pair.value.asString»)''']

		// ports
		addMappings(mappings, network, instance)

		'''
		«entity.simpleName» «IF !instance.arguments.empty»#(
		  «FOR arg: instance.arguments SEPARATOR ","»
		  .«namer.getName(arg.variable)»(«new ArgumentPrinter().doSwitch(arg.value)»)
		  «ENDFOR»
		)
		«ENDIF»
		«instance.name» (
		  «mappings.join(",\n")»
		);
		'''
	}

	/**
	 * Declares wires for each output port of the given instance when needed,
	 * as determined by the isWireNeeded function.
	 */
	def private printWires(DPN network, Instance instance) {
		val wires = new ArrayList<CharSequence>

		val entity = instance.entity
		for (port : entity.outputs) {
			// add one wire for each output port when needed
			val endpoint = new Endpoint(instance, port)
			val outgoing = network.getOutgoing(endpoint)
			if (isSignalNeeded(outgoing)) {
				val wire = getSignal(namer, endpoint)
				wires.add('''wire «irPrinter.doSwitch(port.type)» «wire»;''')
				for (signal : port.interface.signals) {
					wires.add('''wire «wire»_«signal»;''')
				}
			}
		}

		'''
		«IF !wires.empty»
		// Module : «instance.name»
		«wires.join("\n")»
		«ENDIF»
		'''
	}

}
