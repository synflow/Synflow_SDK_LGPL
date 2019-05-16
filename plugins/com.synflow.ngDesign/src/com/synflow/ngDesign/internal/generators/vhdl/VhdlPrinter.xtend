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
package com.synflow.ngDesign.internal.generators.vhdl

import com.google.gson.JsonArray
import com.synflow.models.dpn.Actor
import com.synflow.models.dpn.DPN
import com.synflow.models.dpn.Direction
import com.synflow.models.dpn.Entity
import com.synflow.models.dpn.Port
import com.synflow.models.dpn.Unit
import com.synflow.models.dpn.util.DpnSwitch
import com.synflow.models.ir.TypeInt
import com.synflow.ngDesign.internal.generators.Namer
import com.synflow.ngDesign.internal.generators.vhdl.actor.ActorPrinter
import com.synflow.ngDesign.internal.generators.vhdl.dpn.DpnPrinter
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
 * This class defines the VHDL printer for actors, units, networks.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 */
class VhdlPrinter extends DpnSwitch<CharSequence> {

	def static private printImports(Entity importer) {
		var imports = importer.properties.getAsJsonArray(PROP_IMPORTS)
		'''
		library work;
		use work.Helper_functions.all;
		«FOR entityName : imports»
		use work.«entityName.asString.simpleName».all;
		«ENDFOR»
		'''
	}

	val VhdlIrPrinter irPrinter

	val Namer namer

	new(Namer namer) {
		this.namer = namer
		irPrinter = new VhdlIrPrinter(namer)
	}

	def private void addPorts(List<CharSequence> signals, Port port) {
		val signalDir = if (port.direction == Direction.INPUT) 'in' else 'out'

		signals.add('''«namer.getName(port)» : «signalDir» «printPortType(port)»''')

		for (signal : port.additionalInputs) {
			signals.add('''«signal.name» : in std_logic''')
		}

		for (signal : port.additionalOutputs) {
			signals.add('''«signal.name» : out std_logic''')
		}
	}

	override caseActor(Actor actor) {
		'''
		«printModuleDeclaration(actor)»

		«new ActorPrinter(namer).printActor(actor)»
		'''
	}

	override caseDPN(DPN dpn)
		'''
		«printModuleDeclaration(dpn)»

		«new DpnPrinter(namer).printDPN(dpn)»
		'''

	override caseUnit(Unit unit)
		'''
		«printHeader(unit)»

		------------------------------------------------------------------------------
		package «unit.simpleName» is

		  ---------------------------------------------------------------------------
		  -- Declaration of constants
		  ---------------------------------------------------------------------------
		  «irPrinter.declareTypeList(unit.variables)»
		  «FOR constant : unit.variables»
		  «irPrinter.printStateVar(constant)»
		  «ENDFOR»

		  «IF !unit.procedures.empty»
		  ---------------------------------------------------------------------------
		  -- Declaration of functions
		  ---------------------------------------------------------------------------
		  «FOR proc : unit.procedures»
		  «irPrinter.printFunctionSignature(proc)»;
		  «ENDFOR»
		  «ENDIF»

		end «unit.simpleName»;

		------------------------------------------------------------------------------
		package body «unit.simpleName» is

		  «IF !unit.procedures.empty»
		  ---------------------------------------------------------------------------
		  -- Implementation of functions
		  ---------------------------------------------------------------------------
		  «FOR proc : unit.procedures»
		  «irPrinter.printFunction(proc)»
		  «ENDFOR»
		  «ENDIF»

		end package body «unit.simpleName»;
		'''

	def private printComment(JsonArray lines) {
		'''
		«FOR line : lines»
		-- «line.asString»
		«ENDFOR»
		'''
	}

	def private printHeader(Entity entity) {
		val project = new Path(entity.fileName).segment(0)
		val copyright = entity.properties.getAsJsonArray(PROP_COPYRIGHT)
		val javadoc = entity.properties.getAsJsonArray(PROP_JAVADOC)

		val header =
			if (copyright === null && javadoc === null) {
				'''
				-------------------------------------------------------------------------------
				-- Title      : Generated from «entity.name» by Synflow ngDesign
				-- Project    : «project»
				--
				-- File       : «entity.name».v
				-- Author     : «System.getProperty("user.name")»
				-- Standard   : VHDL'93
				--
				-------------------------------------------------------------------------------
				-- Copyright (c) «Calendar.instance.get(Calendar.YEAR)»
				-------------------------------------------------------------------------------
				'''
			} else {
				'''
				«IF copyright !== null»
				-------------------------------------------------------------------------------
				«copyright.printComment»
				-------------------------------------------------------------------------------
				«ENDIF»

				«IF javadoc !== null»
				-------------------------------------------------------------------------------
				«javadoc.printComment»
				-------------------------------------------------------------------------------
				«ENDIF»
				'''
			}

		'''
		«header»

		-------------------------------------------------------------------------------
		library ieee;
		use ieee.std_logic_1164.all;
		use ieee.numeric_std.all;

		library std;
		use std.textio.all;

		«printImports(entity)»
		'''
	}

	def private printModuleDeclaration(Entity entity) {
		val signals = new ArrayList<CharSequence>

		// clocks
		val clocks = entity.properties.getAsJsonArray(PROP_CLOCKS)
		signals += clocks.map[clock|clock.asString + ' : in std_logic']

		// resets
		val resets = entity.properties.getAsJsonArray(PROP_RESETS)
		signals += resets.map[reset|reset.asJsonObject.getAsJsonPrimitive('name').asString + ' : in std_logic']

		// add ports
		(entity.inputs + entity.outputs).forEach[p|addPorts(signals, p)]

		'''
		«printHeader(entity)»

		-------------------------------------------------------------------------------
		entity «namer.getName(entity)» is
		  port (
		    «signals.join(";\n")»);
		end «namer.getName(entity)»;
		'''
	}

	/**
	 * If the port is TypeInt, prints "std_logic_vector(size - 1 downto 0)".
	 * If the port is TypeBool, prints "std_logic"
	 */
	def private printPortType(Port port) {
		if (port.type.int) {
			val typeInt = port.type as TypeInt
			'''std_logic_vector(«typeInt.size - 1» downto 0)'''
		} else {
			'''std_logic'''
		}
	}

}
