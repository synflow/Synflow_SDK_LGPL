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

import com.synflow.core.IPathResolver
import com.synflow.models.dpn.DPN
import com.synflow.models.dpn.Entity
import com.synflow.ngDesign.internal.generators.Namer
import java.util.ArrayList
import java.util.Calendar
import java.util.List
import org.eclipse.core.runtime.Path

import static com.synflow.core.IProperties.PROP_CLOCKS
import static com.synflow.core.IProperties.PROP_RESETS

/**
 * This class defines the testbench printer for Verilog.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 */
class VerilogTestbenchPrinter {

	val VerilogPrinter dfPrinter

	new(Namer namer, IPathResolver pathResolver) {
		
		dfPrinter = new VerilogPrinter(namer, pathResolver)
	}

	def private printTestModules(DPN dpn, boolean synthetic) {
		if (synthetic) {
			dpn.name = dpn.name + "_test"

			'''
			«dfPrinter.doSwitch(dpn)»

			«dfPrinter.doSwitch(dpn.getInstance("stimulus").entity)»

			«dfPrinter.doSwitch(dpn.getInstance("expected").entity)»
			'''
		} else {
			dfPrinter.doSwitch(dpn)
		}
	}

	def printTestbench(Entity entity) {
		val name = entity.simpleName
		val project = new Path(entity.fileName).segment(0)

		val List<CharSequence> mappings = new ArrayList

		val clocks = entity.properties.getAsJsonArray(PROP_CLOCKS)
		mappings.addAll(clocks.map[clock|
			val clockName = clock.asString
			'''.«clockName»(«clockName»)'''
		])

		val resets = entity.properties.getAsJsonArray(PROP_RESETS)
		val resetNames = resets.map[reset|reset.asJsonObject.getAsJsonPrimitive('name').asString]
		mappings.addAll(resets.map[reset|
			val resetName = reset.asJsonObject.getAsJsonPrimitive('name').asString
			'''.«resetName»(«resetName»)'''
		])

		val List<CharSequence> resetAssignments = new ArrayList
		resetNames.forEach[reset, index|if (index > 0) {
			resetAssignments += '''«reset» <= «resetNames.get(index - 1)»;'''
		}]

		val synthetic = entity.properties.has('synthetic')

		'''
		/**
		 * Title      : Generated from «name» by Synflow ngDesign
		 * Project    : «project»
		 *
		 * File       : «name».tb.v
		 * Author     : «System.getProperty("user.name")»
		 * Standard   : Verilog-2001
		 *
		 **
		 * Copyright (c) «Calendar.instance.get(Calendar.YEAR)»
		 **
		 *
		 */

		module «name»_tb;

		  // clock declarations
		  «FOR clock : clocks»
		  parameter PERIOD_«clock.asString» = 5;
		  «ENDFOR»
		  «FOR clock : clocks»
		  reg «clock.asString» = 0;
		  «ENDFOR»

		  // declarations of resets
		  parameter INIT_RESET = 10 * 10;
		  reg «resetNames.map[reset|'''«reset» = 0'''].join(', ')»;

		  // reset assignments
		  initial #INIT_RESET «resetNames.head» <= 1;
		  «IF !resetNames.tail.empty»
		  always @(posedge «clocks.head.asString») begin
		    «resetAssignments.join('\n')»
		  end
		  «ENDIF»

		  // generation of clock(s)
		  «FOR clock : clocks»
		  always #PERIOD_«clock.asString» «clock.asString» <= !«clock.asString»;
		  «ENDFOR»

		  «name»«IF synthetic»_test«ENDIF» «name.toFirstLower»«IF synthetic»_test«ENDIF» (
		    «mappings.join(",\n")»
		  );

		endmodule

		«printTestModules(entity as DPN, synthetic)»
		'''
	}

}
