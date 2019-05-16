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
 * This class defines the testbench printer for VHDL.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 */
class VhdlTestbenchPrinter {

	val VhdlPrinter dfPrinter

	val Namer namer

	new(Namer namer) {
		this.namer = namer
		dfPrinter = new VhdlPrinter(namer)
	}

	def private printTestModules(DPN dpn, boolean synthetic) {
		'''
		«IF synthetic»
		«dfPrinter.doSwitch(dpn.getInstance("stimulus").entity)»

		«dfPrinter.doSwitch(dpn.getInstance("expected").entity)»

		«ENDIF»
		«dfPrinter.doSwitch(dpn)»
		'''
	}

	def printTestbench(Entity entity) {
		val name = entity.simpleName
		val project = new Path(entity.fileName).segment(0)

		val List<CharSequence> mappings = new ArrayList

		val clocks = entity.properties.getAsJsonArray(PROP_CLOCKS)
		mappings.addAll(clocks.map[clock|
			val clockName = clock.asString
			'''«clockName» => «clockName»'''
		])

		val resets = entity.properties.getAsJsonArray(PROP_RESETS)
		val resetNames = resets.map[reset|reset.asJsonObject.getAsJsonPrimitive('name').asString]
		mappings.addAll(resets.map[reset|
			val resetName = reset.asJsonObject.getAsJsonPrimitive('name').asString
			'''«resetName» => «resetName»'''
		])

		val List<CharSequence> resetAssignments = new ArrayList
		resetNames.forEach[reset, index|if (index > 0) {
			resetAssignments += '''«reset» <= «resetNames.get(index - 1)»;'''
		}]

		val synthetic = entity.properties.has('synthetic')
		val oldName = entity.name
		if (synthetic) {
			entity.name = oldName + "_test"
		}
		val modules = printTestModules(entity as DPN, synthetic)
		val entityName = namer.getName(entity)
		entity.name = oldName

		'''
		«modules»

		-------------------------------------------------------------------------------
		-- Title      : Generated from «name» by Synflow ngDesign
		-- Project    : «project»
		--
		-- File       : «name».tb.vhd
		-- Author     : «System.getProperty("user.name")»
		-- Standard   : VHDL'93
		--
		-------------------------------------------------------------------------------
		-- Copyright (c) «Calendar.instance.get(Calendar.YEAR)»
		-------------------------------------------------------------------------------


		------------------------------------------------------------------------------
		library ieee;
		use ieee.std_logic_1164.all;
		use ieee.numeric_std.all;

		-------------------------------------------------------------------------------
		entity «name»_tb is
		end «name»_tb;

		-------------------------------------------------------------------------------
		architecture arch_«name»_tb of «name»_tb is

		  -- resets
		  «resetNames.map[reset|'''signal «reset» : std_logic := '0';'''].join('\n')»
		  constant INIT_RESET : time := 10 * 10 ns;

		  -- Clocks
		  «FOR clock : clocks»
		  constant PERIOD_«clock.asString» : time := 10 ns;
		  «ENDFOR»
		  «FOR clock : clocks»
		  signal «clock.asString» : std_logic := '1';
		  «ENDFOR»

		  ---------------------------------------------------------------------------

		begin

		  -- clock generation
		  «FOR clock : clocks»
		  «clock.asString» <= not «clock.asString» after PERIOD_«clock.asString» / 2;
		  «ENDFOR»

		  -- reset generation
		  «resetNames.head» <= '1' after INIT_RESET;

		  process («clocks.get(0).asString») is
		  begin
		    «resetAssignments.join('\n')»
		  end process;

		  «entityName.toFirstLower» : entity work.«entityName»
		  port map (
		    «mappings.join(",\n")»
		  );

		end architecture arch_«name»_tb;
		'''
	}

}
