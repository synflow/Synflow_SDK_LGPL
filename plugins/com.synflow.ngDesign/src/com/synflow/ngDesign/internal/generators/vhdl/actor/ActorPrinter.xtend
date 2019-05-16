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

import com.synflow.models.dpn.Actor
import com.synflow.models.dpn.Port
import com.synflow.models.ir.TypeInt
import com.synflow.ngDesign.internal.generators.Namer
import com.synflow.ngDesign.internal.generators.vhdl.VhdlIrPrinter

import static com.synflow.core.IProperties.PROP_CLOCKS

/**
 * This class defines the VHDL printer for actors, units, networks.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 */
class ActorPrinter {

	val ProcessPrinter actionPrinter

	val VhdlIrPrinter irPrinter

	val Namer namer

	new(Namer namer) {
		this.namer = namer
		actionPrinter = new ProcessPrinter(namer)
		irPrinter = new VhdlIrPrinter(namer)
	}

	def printActor(Actor actor) {
		val combinational = actor.properties.getAsJsonArray(PROP_CLOCKS).empty

		'''
		-------------------------------------------------------------------------------
		architecture rtl_«actor.simpleName» of «namer.getName(actor)» is

		  «irPrinter.declareTypeList(actor.variables)»
		  «FOR stateVar : actor.variables»
		  «irPrinter.printStateVar(stateVar)»
		  «ENDFOR»

		  «IF !actor.procedures.empty»
		  -----------------------------------------------------------------------------
		  -- Declaration of functions
		  -----------------------------------------------------------------------------
		  «FOR proc : actor.procedures»
		  «irPrinter.printFunctionSignature(proc)»;
		  «ENDFOR»

		  -----------------------------------------------------------------------------
		  -- Implementation of functions
		  -----------------------------------------------------------------------------
		  «FOR proc : actor.procedures»
		  «irPrinter.printFunction(proc)»
		  «ENDFOR»
		  «ENDIF»
		  «IF actor.hasFsm»

		  type FSM_type is («FOR state : actor.fsm.states SEPARATOR ", "»s_«state.name»«ENDFOR»);
		  signal FSM : FSM_type;
		  «ENDIF»

		  «actionPrinter.printActions(actor)»

		begin

		  «IF combinational»
		  «actionPrinter.printAsyncProcess(actor)»
		  «ELSE»
		  «actionPrinter.printSyncProcess(actor)»
		  «ENDIF»

		end architecture rtl_«actor.simpleName»;

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

}
