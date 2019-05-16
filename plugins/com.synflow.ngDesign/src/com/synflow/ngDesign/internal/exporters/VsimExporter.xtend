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
package com.synflow.ngDesign.internal.exporters

import com.synflow.core.IExporter
import com.synflow.core.util.CoreUtil
import com.synflow.models.dpn.Actor
import com.synflow.models.dpn.DPN
import com.synflow.models.dpn.Direction
import com.synflow.models.dpn.Entity
import com.synflow.models.dpn.Instance
import com.synflow.models.dpn.Port
import com.synflow.models.ir.Var
import com.synflow.models.ir.util.TypeUtil
import com.synflow.ngDesign.ExportHelper
import com.synflow.ngDesign.NgDesign
import java.io.File
import org.eclipse.core.resources.IProject
import org.w3c.dom.Document

import static com.synflow.core.ICoreConstants.*
import static com.synflow.core.IProperties.*
import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_MODELSIM_BIN

/**
 * This class defines a generator for Modelsim.
 *
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 *
 */
class VsimExporter implements IExporter {

	override buildProject(Entity entity, Document xmlDoc) {
		val project = entity.file.project
		val helper = new ExportHelper(entity, FOLDER_SIM, "other")

		val name = entity.simpleName
		helper.write(FOLDER_SIM + "/compile_" + name + ".tcl", printTcl(helper, name))
		helper.write(FOLDER_SIM + "/wave.do", printWave(entity))

		runModelsim(project, name)
	}

	def private printTcl(ExportHelper helper, String name) {
		val pathList = helper.computePathList
		val includePath = helper.includePath.nullOrEmpty
		'''
		puts {
		  ModelSimPE compile script
		  Generated by Synflow Studio for the «name» project
		}

		set PrefMain(font) {Courier 10 roman normal}

		set work work
		if {![file isdirectory $work]} {
		  vlib work
		  vmap work work
		}

		set library_file_list {
		  design_library {
		    «pathList.join("\n")»
		  }
		  test_library {
		  	«'../' + helper.computePathTb()»
		  }
		}

		# Compile out of date files
		set time_now [clock seconds]
		if [catch {set last_compile_time}] {
		  set last_compile_time 0
		}

		foreach {library file_list} $library_file_list {
		  foreach file $file_list {
		    if { $last_compile_time < [file mtime $file] } {
		      if [regexp {.vhd$} $file] {
		        vcom -2008 -reportprogress 30 -work work $file
		      } else {
		      	«IF includePath»
		      	vlog -reportprogress 300 -work work $file
		      	«ELSE»
		      	vlog -reportprogress 300 -work work «FOR path: helper.includePath»+incdir+«path» «ENDFOR»$file
		        «ENDIF»
		      }
		      set last_compile_time 0
		    }
		  }
		}
		set last_compile_time $time_now

		# Simulate
		vsim -voptargs=+acc «name»_tb
		do wave.do
		run 10 us
		'''
	}

	def printWave(Entity entity) {
		val clocks = entity.properties.getAsJsonArray(PROP_CLOCKS)
		val name = entity.simpleName

		val instanceName = name.toFirstLower
		val top =
			if (entity instanceof Actor || !entity.inputs.empty || !entity.outputs.empty) {
				'''«instanceName»_test/«instanceName»'''
			} else {
				instanceName
			}

		'''
		onerror {resume}
		quietly WaveActivateNextPane {} 0
		«FOR clock: clocks»
		add wave -noupdate -color White /«name»_tb/«clock.asString»
		«ENDFOR»
		add wave -noupdate -color White /«name»_tb/reset_n
		«IF entity instanceof DPN»
		«FOR instance : entity.instances»
			«printVertex('''/«name»_tb/«top»''', "", instance)»
		«ENDFOR»
		«ENDIF»
		TreeUpdate [SetDefaultTree]
		WaveRestoreCursors {{Cursor 1} {0 ps} 0}
		configure wave -namecolwidth 222
		configure wave -valuecolwidth 100
		configure wave -justifyvalue left
		configure wave -signalnamewidth 1
		configure wave -snapdistance 10
		configure wave -datasetprefix 0
		configure wave -rowmargin 4
		configure wave -childrowmargin 2
		configure wave -gridoffset 0
		configure wave -gridperiod 1
		configure wave -griddelta 40
		configure wave -timeline 0
		configure wave -timelineunits ns
		update
		WaveRestoreZoom {0 ps} {10 us}
		'''
	}

	def CharSequence printVertex(CharSequence parentHier, String group, Instance instance) {
		val entity = instance.entity
		val hier = '''«parentHier»/«instance.name»'''

		'''
		add wave -noupdate -expand«group» -divider <NULL>
		add wave -noupdate -expand«group» -group «instance.name» -divider <NULL>
		add wave -noupdate -expand«group» -group «instance.name» -divider «instance.name»
		add wave -noupdate -expand«group» -group «instance.name» -divider <NULL>
		«FOR clock: instance.entity.properties.getAsJsonArray(PROP_CLOCKS)»
		add wave -noupdate -expand«group» -group «instance.name» -color White «hier»/«clock.asString»
		«ENDFOR»
		«FOR port : entity.inputs»
			«printPort(group, hier, instance.name, port)»
		«ENDFOR»
		«FOR port : entity.outputs»
			«printPort(group, hier, instance.name, port)»
		«ENDFOR»
		«IF entity instanceof Actor»
			«FOR stateVar : entity.variables»
				«printStateVar(group, hier, instance.name, stateVar)»
			«ENDFOR»
			«IF entity.hasFsm»
				add wave -noupdate -expand«group» -group «instance.name» -expand -group StateVar -color {Magenta} «hier»/FSM
			«ENDIF»
		«ENDIF»
		«IF entity instanceof DPN»
			«FOR inst : entity.instances»
				«printVertex('''«hier»''', group + " -group " + instance.name, inst)»
			«ENDFOR»
		«ENDIF»
		'''
	}

	def printPort(String group, CharSequence hier, String instName, Port port) {
		val name = '''«hier»/«port.name»'''
		val color = if (port.direction == Direction.INPUT) "Cadet Blue" else "Orange Red"
		val dir = if (port.direction == Direction.INPUT) "input" else "output"

		'''
		add wave -noupdate -expand«group» -group «instName» -expand -group «dir» -color {«color»} -radix hexadecimal «name»
		«FOR signal : port.additionalInputs»
		add wave -noupdate -expand«group» -group «instName» -expand -group input -color {Cadet Blue} «hier»/«signal.name»
		«ENDFOR»
		«FOR signal : port.additionalOutputs»
		add wave -noupdate -expand«group» -group «instName» -expand -group output -color {Orange Red} «hier»/«signal.name»
		«ENDFOR»
		'''
	}

	def printStateVar(String group, CharSequence hier, String instName, Var stateVar)
		'''
		«IF stateVar.assignable && TypeUtil.getSize(stateVar.type) <= 1024»
		add wave -noupdate -expand«group» -group «instName» -expand -group StateVar -color {Magenta} -radix hexadecimal «hier»/«stateVar.name»
		«ENDIF»
		'''

	def private runModelsim(IProject project, String name) {
		val bin = NgDesign.getPreference(PREF_MODELSIM_BIN, "")
		if (bin.isEmpty()) {
			return null
		}

		val modelsim = CoreUtil.getExecutable(bin + "/modelsim")
		val tclScript = "compile_" + name + ".tcl"
		val workingDir = project.getFolder("sim").getLocation().toString()
		new ProcessBuilder(modelsim, "-do", tclScript).directory(new File(workingDir))
	}

		override exportPR(Entity entity, Entity variant1, Entity variant2, Document xmlDoc) {
			return null
		}

		override setupPR(Entity entity) {
			return null
		}

		override createProject(Entity entity, Document xmlDoc) {
			return null
		}

}