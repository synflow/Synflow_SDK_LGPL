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
import com.synflow.models.dpn.Entity
import com.synflow.ngDesign.ExportHelper
import java.io.File
import org.eclipse.core.runtime.Path
import org.w3c.dom.Document

/**
 * This class defines a generator of Quartus II project file.
 *
 * @author Matthieu Wipliez
 *
 */
class QuartusExporter implements IExporter {

	/**
	 * Exports to Altera .qpf and .qsf files.
	 */
	override createProject(Entity entity, Document xmlDoc) {
		val name = entity.simpleName
		val path = new Path(FOLDER_PROJECTS).append(name).toString
		val helper = new ExportHelper(entity, path, "other")

		helper.write(path + "/" + name + ".qpf", printQpf(helper, name))
		helper.write(path + "/" + name + ".qsf", printQsf(helper, name))

		null
	}

	def private printQpf(ExportHelper helper, String name) '''
		QUARTUS_VERSION = "11.1"
		DATE = "«helper.date»"

		# Revisions

		PROJECT_REVISION = "«name»"
	'''

	def private printQsf(ExportHelper helper, String name) {
		'''
			# Generated from «name»
			set_global_assignment -name FAMILY "Cyclone IV GX"
			set_global_assignment -name DEVICE AUTO
			set_global_assignment -name TOP_LEVEL_ENTITY «name»
			set_global_assignment -name ORIGINAL_QUARTUS_VERSION 12.0
			set_global_assignment -name PROJECT_CREATION_TIME_DATE "«helper.date»"
			set_global_assignment -name LAST_QUARTUS_VERSION 12.0

			# Source files
			«FOR path : helper.computePathList()»
				«printAssignment(helper.language, path)»
			«ENDFOR»

			set_global_assignment -name SEARCH_PATH "«helper.includePath.join(File.pathSeparator)»"
		'''
	}

	def private printAssignment(String language, String path) '''
		set_global_assignment -name «language.toUpperCase»_FILE «path»
	'''

	override exportPR(Entity entity, Entity variant1, Entity variant2, Document xmlDoc) {
		return null
	}

	override setupPR(Entity entity) {
		return null
	}

	override buildProject(Entity entity, Document xmlDoc) {
		return null
	}

}
