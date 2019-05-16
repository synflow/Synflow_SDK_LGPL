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
import com.synflow.models.dpn.FSM
import com.synflow.models.dpn.Port
import com.synflow.models.dpn.Transition
import com.synflow.models.ir.Procedure
import com.synflow.models.ir.util.TypeUtil
import com.synflow.ngDesign.internal.generators.Namer
import com.synflow.ngDesign.internal.generators.verilog.VerilogIrPrinter
import java.util.List

import static extension com.synflow.ngDesign.internal.generators.GeneratorExtensions.getExpression
import static extension com.synflow.ngDesign.internal.generators.GeneratorExtensions.isInlinable

/**
 * This class defines all functionality common to synchronous and combinational process printers.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 */
abstract class ProcessPrinter {

	val protected Namer namer

	val protected VerilogIrPrinter irPrinter

	new(Namer namer, IPathResolver pathResolver) {
		this.namer = namer
		irPrinter = new VerilogIrPrinter(namer, pathResolver)
	}

	def protected final printActions(List<Action> actions, boolean combinational) {
		var CharSequence acc = null
		for (action : actions) {
			val test = printSchedulerTest(action)
			val body = printBody(if (combinational) action.combinational else action.body)
			if (test === null) {
				return '''«IF acc !== null»«acc» else «ENDIF»«body»'''
			} else {
				acc = '''«IF acc !== null»«acc» else «ENDIF»if («test») «body»'''
			}
		}
		acc
	}

	def private printBody(Procedure procedure) {
		val hasLocals = !procedure.locals.empty

		'''
		begin«IF hasLocals» : «procedure.name»«ENDIF» // line «procedure.lineNumber»
		  «IF hasLocals»
		  «FOR variable : procedure.locals»
		  reg «irPrinter.doSwitch(variable)»;
		  «ENDFOR»

		  «ENDIF»
		  «irPrinter.doSwitch(procedure.blocks)»
		end'''
	}

	def protected final printFsm(FSM fsm, boolean combinational)
		'''
		case (FSM)
		  «FOR state : fsm.states»
		  «state.name»: begin
		    «printActions(state.outgoing.map[edge|(edge as Transition).action], combinational)»
		  end

		«ENDFOR»
		  default: $stop;
		endcase
		'''

	def private String printSchedulerCall(Action action) {
		if (action.scheduler.inlinable) {
			'''«irPrinter.doSwitch(action.scheduler.expression)»'''
		} else {
			'''«action.scheduler.name»(1'b0)'''
		}
	}

	def protected final printSchedulerTest(Action action) {
		val test = printSchedulerCall(action)
		if (test == "1'b1") null else test
	}

	/**
	 * Resets any additional output signals (if any) of the given ports.
	 * @see resetPortFlags(Port)
	 */
	def protected final resetPortFlags(Iterable<Port> ports, boolean combinational) {
		'''
		«FOR port : ports»
			«FOR signal : port.additionalOutputs»
				«signal.name» «IF !combinational»<«ENDIF»= 1'b0;
			«ENDFOR»
		«ENDFOR»
		'''
	}

	/**
	 * Resets the given ports.
	 */
	def protected final resetPorts(Iterable<Port> ports, boolean combinational) {
		val filtered = ports.filter[synchronous != combinational]

		'''
		«FOR port : filtered»
			«namer.getName(port)» «IF !combinational»<«ENDIF»= «TypeUtil.getSize(port.type)»'b0;
		«ENDFOR»
		«resetPortFlags(filtered, combinational)»
		'''
	}

}
