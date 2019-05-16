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
import com.synflow.models.dpn.Entity
import com.synflow.ngDesign.ExportHelper
import com.synflow.ngDesign.NgDesign
import java.io.File
import org.eclipse.core.runtime.Path
import org.w3c.dom.Document

import static com.synflow.ngDesign.preferences.IPreferenceConstants.PREF_DIAMOND_BIN

/**
 * This class defines a generator of Diamond project file.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 *
 */
class DiamondExporter implements IExporter {

	/**
	 * Exports to Diamond .ldf file.
	 */
	def private generateFiles(ExportHelper helper, Entity entity) {
		val name = entity.simpleName
		val path = new Path(FOLDER_PROJECTS).append(name).toString

		helper.write(path + "/" + name + ".pty", printXty(""))
		helper.write(path + "/" + name + ".ldf", printLdf(helper, name))
		helper.write(path + "/run_" + name + ".tcl", printTcl(name))

		helper.preservingWrite(path + "/Strategy1.sty", printXty("Strategy1"))
		helper.preservingWrite(path + "/" + name + ".lpf", printLpf)
		helper.preservingWrite(path + "/" + name + ".xcf", printXcf(name))
	}

	override buildProject(Entity entity, Document xmlDoc) {
		val project = entity.file.project
		val pathComputer = new ExportHelper(entity, FOLDER_PROJECTS + '/' + entity.simpleName, "other")
		generateFiles(pathComputer, entity)

		val bin = NgDesign.getPreference(PREF_DIAMOND_BIN, "");
		if (bin.isEmpty()) {
			return null
		}

		val pnmain = CoreUtil.getExecutable(bin + "/pnmainc")
		val tclScript = "run_" + entity.getSimpleName() + ".tcl"
		val location = project.getFolder("projects/" + entity.getSimpleName()).getLocation()
		val workingDir = location.toOSString()
		new ProcessBuilder(pnmain, tclScript).directory(new File(workingDir))
	}

	def private printLdf(ExportHelper helper, String name) {
		val pathList = helper.computePathList
		'''
			<?xml version="1.0" encoding="UTF-8"?>
			<BaliProject version="2.0" title="«name»" device="LFE3-35EA-8FN484C" default_implementation="«name»">
			    <Options/>
			    <Implementation title="«name»" dir="«name»" description="«name»" default_strategy="Strategy1">
			        <Options>
			            <Option name="include path" value="«helper.includePath.join(File.pathSeparator)»"/>
			        </Options>

			        <!-- Source files -->
			        «FOR path : pathList»
				«printLdfFile(helper.language, path)»
			        «ENDFOR»
			        <Source name="«name».lpf" type="Logic Preference" type_short="LPF">
			    <Options/>
			        </Source>
			        <Source name="«name».xcf" type="ispVM Download Project" type_short="ispVM">
			            <Options/>
			        </Source>
			    </Implementation>
			    <Strategy name="Strategy1" file="Strategy1.sty"/>
			</BaliProject>
		'''
	}

	def private printLdfFile(String language, String path) '''
		<Source name="«path»" type="«language»" type_short="«language»">
		    <Options/>
		</Source>
	'''

	def private printLpf() '''
		BLOCK RESETPATHS ;
		BLOCK ASYNCPATHS ;
		LOCATE COMP "clock" SITE "L5" ;
		LOCATE COMP "reset_n" SITE "A21" ;
		IOBUF PORT "clock" IO_TYPE=LVDS25 ;
		IOBUF PORT "reset_n" IO_TYPE=LVCMOS33 ;
	'''

	def private printTcl(String name) {
		'''
			prj_project open "«name».ldf"
			prj_run Export -impl «name» -task Bitgen
			pgr_project open "«name».xcf"
			pgr_program run
		'''
	}

	def private printXty(String label) '''
		<?xml version="1.0" encoding="UTF-8"?>
		<!DOCTYPE strategy>
		<Strategy version="1.0" predefined="0" description="" label="«label»"/>
	'''

	def private printXcf(String name) '''
		<?xml version='1.0' encoding='utf-8' ?>
		<!DOCTYPE		ispXCF	SYSTEM	"IspXCF.dtd" >
		<ispXCF version="1.4">
			<Comment></Comment>
			<Chain>
				<Comm>JTAG</Comm>
				<Device>
					<SelectedProg value="TRUE"/>
					<Pos>1</Pos>
					<Vendor>Lattice</Vendor>
					<Family>LatticeECP3</Family>
					<Name>LFE3-35EA</Name>
					<IDCode>0x01012043</IDCode>
					<Package>All</Package>
					<PON>LFE3-35EA</PON>
					<Bypass>
						<InstrLen>8</InstrLen>
						<InstrVal>11111111</InstrVal>
						<BScanLen>1</BScanLen>
						<BScanVal>0</BScanVal>
					</Bypass>
					<File>«name»/«name»_«name».bit</File>
					<FileTime>6/26/2012 22:50:15</FileTime>
					<Operation>Fast Program</Operation>
					<Option>
						<SVFVendor>JTAG STANDARD</SVFVendor>
						<IOState>HighZ</IOState>
						<PreloadLength>675</PreloadLength>
						<IOVectorData>0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF</IOVectorData>
						<OverideUES value="TRUE"/>
						<TCKFrequency>1.000000 MHz</TCKFrequency>
						<SVFProcessor>ispVM</SVFProcessor>
						<Usercode>0x00000000</Usercode>
						<AccessMode>JTAG</AccessMode>
					</Option>
				</Device>
			</Chain>
			<ProjectOptions>
				<Program>SEQUENTIAL</Program>
				<Process>ENTIRED CHAIN</Process>
				<OperationOverride>No Override</OperationOverride>
				<StartTAP>TLR</StartTAP>
				<EndTAP>TLR</EndTAP>
				<VerifyUsercode value="FALSE"/>
			</ProjectOptions>
			<CableOptions>
				<CableName>USB2</CableName>
				<PortAdd>FTUSB-0</PortAdd>
			</CableOptions>
		</ispXCF>
	'''

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
