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

package com.synflow.ngDesign.internal.generators.verilog

import com.google.gson.JsonArray
import com.synflow.core.IPathResolver
import com.synflow.models.dpn.Actor
import com.synflow.models.dpn.DPN
import com.synflow.models.dpn.Direction
import com.synflow.models.dpn.Entity
import com.synflow.models.dpn.Port
import com.synflow.models.dpn.Unit
import com.synflow.models.dpn.util.DpnSwitch
import com.synflow.ngDesign.internal.generators.Namer
import com.synflow.ngDesign.internal.generators.verilog.actor.ActorPrinter
import com.synflow.ngDesign.internal.generators.verilog.dpn.DpnPrinter
import java.util.ArrayList
import java.util.Calendar
import java.util.List
import org.eclipse.core.runtime.Path

import static com.synflow.core.IProperties.PROP_CLOCKS
import static com.synflow.core.IProperties.PROP_COPYRIGHT
import static com.synflow.core.IProperties.PROP_IMPORTS
import static com.synflow.core.IProperties.PROP_JAVADOC
import static com.synflow.core.IProperties.PROP_RESETS

import static extension com.synflow.models.ir.util.IrUtil.getSimpleName

/**
 * This class defines the Verilog printer for actors, units, networks.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 */
class VerilogPrinter extends DpnSwitch<CharSequence> {

	def private static printImports(Entity entity) {
		var imports = entity.properties.getAsJsonArray(PROP_IMPORTS)
		'''
		«FOR path : imports»
			`include "«path.asString.simpleName».v"
		«ENDFOR»
		'''
	}

	val VerilogIrPrinter irPrinter

	val Namer namer

	val IPathResolver pathResolver

	new(Namer namer, IPathResolver pathResolver) {
		this.namer = namer
		irPrinter = new VerilogIrPrinter(namer, pathResolver)
		this.pathResolver = pathResolver
	}

	def private void addPorts(List<CharSequence> signals, Port port) {
		val needReg = port.eContainer instanceof Actor
		val outputDir = '''output«IF needReg» reg«ENDIF»'''
		val signalDir = if (port.direction == Direction.INPUT) 'input' else outputDir

		signals.add('''«signalDir» «irPrinter.doSwitch(port.type)» «namer.getName(port)»''')

		for (signal : port.additionalInputs) {
			signals.add('''input «signal.name»''')
		}

		for (signal : port.additionalOutputs) {
			signals.add('''«outputDir» «signal.name»''')
		}
	}

	override caseActor(Actor actor) {
		'''
		«printModuleDeclaration(actor)»

		  «new ActorPrinter(namer, pathResolver).printActor(actor)»

		endmodule //«actor.simpleName»
		'''
	}

	override caseDPN(DPN dpn) {
		'''
		«printModuleDeclaration(dpn)»

		  «new DpnPrinter(namer, pathResolver).printDPN(dpn)»

		endmodule //«dpn.simpleName»
		'''
	}

	override caseUnit(Unit unit) {
		'''
		«printHeader(unit)»

		«printImports(unit)»

		/**
		 * Constants
		 */
		«FOR constant : unit.variables»
		«irPrinter.printStateVar(unit, constant)»
		«ENDFOR»

		«IF !unit.procedures.empty»
		/**
		 * Functions
		 */
		«FOR proc : unit.procedures»
		«irPrinter.printFunction(proc)»
		«ENDFOR»
		«ENDIF»

		'''
	}

	def private printComment(JsonArray lines) {
		'''
		«FOR line : lines»
		* «line.asString»
		«ENDFOR»
		'''
	}

	def private printHeader(Entity entity) {
		val project = new Path(entity.fileName).segment(0)
		val copyright = entity.properties.getAsJsonArray(PROP_COPYRIGHT)
		val javadoc = entity.properties.getAsJsonArray(PROP_JAVADOC)

		if (copyright === null && javadoc === null) {
			'''
			/**
			 * Title      : Generated from «entity.name» by Synflow ngDesign
			 * Project    : «project»
			 *
			 * File       : «entity.name».v
			 * Author     : «System.getProperty("user.name")»
			 * Standard   : Verilog-2001
			 *
			 *
			 * Copyright (c) «Calendar.instance.get(Calendar.YEAR)»
			 *
			 *
			 */
			'''
		} else {
			'''
			«IF copyright !== null»
			/*
			 «copyright.printComment»
			 */
			«ENDIF»
			«IF javadoc !== null»

			/**
			 «javadoc.printComment»
			 */
			«ENDIF»
			'''
		}
	}

	def private printModuleDeclaration(Entity entity) {
		val signals = new ArrayList<CharSequence>

		// clocks
		val clocks = entity.properties.getAsJsonArray(PROP_CLOCKS)
		signals += clocks.map[clock|'input ' + clock.asString]

		// resets
		val resets = entity.properties.getAsJsonArray(PROP_RESETS)
		signals += resets.map[reset|'input ' + reset.asJsonObject.getAsJsonPrimitive('name').asString]

		// add ports
		(entity.inputs + entity.outputs).forEach[p|addPorts(signals, p)]

		'''
		«printHeader(entity)»
		module «entity.simpleName»(«signals.join(', ')»);

		  «printImports(entity)»
		'''
	}

}
