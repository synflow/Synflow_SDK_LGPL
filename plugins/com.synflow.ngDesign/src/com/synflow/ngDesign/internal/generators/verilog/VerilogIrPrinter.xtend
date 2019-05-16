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
import com.synflow.core.IProperties
import com.synflow.models.dpn.DpnPackage
import com.synflow.models.dpn.Entity
import com.synflow.models.dpn.Goto
import com.synflow.models.ir.BlockBasic
import com.synflow.models.ir.BlockIf
import com.synflow.models.ir.BlockWhile
import com.synflow.models.ir.ExprVar
import com.synflow.models.ir.Expression
import com.synflow.models.ir.InstAssign
import com.synflow.models.ir.InstCall
import com.synflow.models.ir.InstLoad
import com.synflow.models.ir.InstReturn
import com.synflow.models.ir.InstStore
import com.synflow.models.ir.Instruction
import com.synflow.models.ir.Procedure
import com.synflow.models.ir.Type
import com.synflow.models.ir.TypeArray
import com.synflow.models.ir.TypeFloat
import com.synflow.models.ir.TypeInt
import com.synflow.models.ir.TypeString
import com.synflow.models.ir.Var
import com.synflow.models.ir.util.TypeUtil
import com.synflow.models.util.EcoreHelper
import com.synflow.ngDesign.internal.generators.Namer
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend2.lib.StringConcatenation

/**
 * This class prints Verilog code from IR.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 */
class VerilogIrPrinter extends VerilogExpressionPrinter {

	val IPathResolver pathResolver

	new(Namer namer, IPathResolver pathResolver) {
		super(namer);
		this.pathResolver = pathResolver
	}

	override caseBlockBasic(BlockBasic node)
		'''
		«doSwitch(node.instructions)»
		'''

	override caseBlockIf(BlockIf block)
		'''
		«printComments(block, block.lineNumber)»if («doSwitch(block.condition)») begin
		  «doSwitch(block.thenBlocks)»
		«IF block.elseRequired»
		end else begin
		  «doSwitch(block.elseBlocks)»
		«ENDIF»
		end
		«IF block.joinBlock !== null»
		«doSwitch(block.joinBlock)»
		«ENDIF»
		'''

	override caseBlockWhile(BlockWhile block)
		'''
		«printComments(block, block.lineNumber)»while («doSwitch(block.condition)») begin
		  «doSwitch(block.blocks)»
		  «doSwitch(block.joinBlock)»
		end
		'''

	override caseInstAssign(InstAssign assign) {
		'''
		«printComments(assign, assign.lineNumber)»«namer.getName(assign.target.variable)» = «doSwitch(assign.value)»;
		'''
	}

	override caseInstCall(InstCall call) {
		if (call.assert) {
			val expr = doSwitch(call.arguments.get(0))
			'''
			if (~(«expr»)) begin
			  $display("Assertion failed: «expr»");
			  $stop;
			end
			'''
		} else if (call.print) {
			'''
			$display(«printDisplayParams(call.arguments)»);
			'''
		} else {
			'''
			«IF call.target === null»
			«call.procedure.name»(«printCallParams(call.arguments)»);
			«ELSE»
			«namer.getName(call.target.variable)» = «call.procedure.name»(«printCallParams(call.arguments)»);
			«ENDIF»
			'''
		}
	}

	override caseInstReturn(InstReturn instReturn) {
		if (instReturn.value !== null) {
			val procedure = EcoreHelper.getContainerOfType(instReturn, Procedure)
			'''
			«procedure.name» = «doSwitch(instReturn.value)»;
			'''
		}
	}

	override caseInstLoad(InstLoad load) {
		var target = load.target.variable
		val source = load.source.variable
		if (target.type.array) {
			copyArray(target, load.indexes.get(0), source)
		} else {
			'''
			«printComments(load, load.lineNumber)»«namer.getName(target)» = «namer.getName(source)»«printIndexes(source.type, load.indexes)»;
			'''
		}
	}

	override caseInstruction(Instruction instr) {
		if (instr instanceof Goto) {
			'''FSM <= «instr.target.name»;'''
		}
	}

	override caseInstStore(InstStore store) {
		val target = store.target.variable
		val value = store.value
		if (value instanceof ExprVar) {
			val source = value.use.variable
			if (source.type.array) {
				return copyArray(target, store.indexes.get(0), source)
			}
		}

		val procedure = EcoreHelper.getContainerOfType(store, typeof(Procedure))
		val combinational = procedure.eContainingFeature == DpnPackage.Literals.ACTION__COMBINATIONAL
		val blocking = target.local || combinational

		'''
		«printComments(store, store.lineNumber)»«namer.getName(target)»«printIndexes(target.type, store.indexes)» «IF !blocking»<«ENDIF»= «doSwitch(value)»;
		'''
	}

	override caseTypeArray(TypeArray type) {
		var depth = 1
		for (dim : type.dimensions ) {
			depth = depth * dim
		}
		depth = depth - 1
		'''[0 : «depth»]'''
	}

	override caseTypeFloat(TypeFloat type) '''real'''

	override caseTypeInt(TypeInt type) {
		// if signed, prints 'signed [size - 1 : 0]', otherwise just '[size - 1 : 0]'
		'''«IF type.signed»signed «ENDIF»[«type.size - 1» : 0]'''
	}

	override caseTypeString(TypeString type) {
		throw new IllegalArgumentException("unsupported String type")
	}

	override caseVar(Var variable) {
		val name = namer.getName(variable)
		if (variable.type.array) {
			'''«doSwitch((variable.type as TypeArray).elementType)» «name» «doSwitch(variable.type)»'''
		} else {
			'''«doSwitch(variable.type)» «name»'''
		}
	}

	def private copyArray(Var target, Expression index, Var source) {
		var loopVar = (index as ExprVar).use.variable.name
		val bound = (source.type as TypeArray).dimensions.fold(1, [acc, elt|acc * elt])

		'''
		for («loopVar» = 0; «loopVar» < «bound»; «loopVar» = «loopVar» + 1) begin
		  «target.name»[«loopVar»] «IF target.isGlobal»<«ENDIF»= «source.name»[«loopVar»];
		end
		'''
	}

	def doSwitch(List<? extends EObject> objects)
		'''
		«FOR eObject : objects»
			«doSwitch(eObject)»
		«ENDFOR»
		'''

	def private getAbsoluteHexPath(Entity entity, String varName) {
		val path = pathResolver.getFullPath(entity)
		path.substring(0, path.lastIndexOf('.')) + "_" + varName + ".hex"
	}

	def private printCallParams(List<Expression> arguments) {
		if (arguments.empty) {
			"1'b1"
		} else {
			'''«FOR expr : arguments SEPARATOR ", "»«doSwitch(expr)»«ENDFOR»'''
		}
	}

	def printComments(EObject obj, int lineNumber) {
		val entity = EcoreHelper.getContainerOfType(obj, Entity)
		val comments = entity.properties.getAsJsonObject(IProperties.PROP_COMMENTS)
		if (comments !== null) {
			val lines = comments.getAsJsonArray(String.valueOf(lineNumber))
			if (lines !== null && lines.size != 0) {
				'''
				«FOR line : lines»
				// «line.asString»
				«ENDFOR»
				'''
			}
		}
	}

	def private printDisplayParams(List<Expression> arguments) {
		val format = new StringConcatenation
		val args = new ArrayList<CharSequence>
		format.append("\"")
		args.add(format)
		for (expr : arguments) {
			if (expr.exprString) {
				format.append(doSwitch(expr))
			} else {
				format.append("%0h")
				args.add(doSwitch(expr))
			}
		}

		format.append("\"")
		args.join(", ")
	}

	def printFunction(Procedure procedure) {
		'''
		function «doSwitch(procedure.returnType)» «procedure.name»(«IF procedure.parameters.empty»input _dummy«ELSE»«FOR param : procedure.parameters SEPARATOR ', '»input «doSwitch(param)»«ENDFOR»«ENDIF»);
		  «FOR local : procedure.locals»
		  reg «doSwitch(local)»;
		  «ENDFOR»
		  begin
		    «doSwitch(procedure.blocks)»
		  end
		endfunction

		'''
	}

	def private printIndexes(Type type, List<Expression> indexes) {
		val it = indexes.iterator
		if (it.hasNext) {
			val firstIndex = it.next
			if (it.hasNext) {
				'''[{«indexes.map[index|doSwitch(index)].join(', ')»}]'''
			} else {
				'''[«doSwitch(firstIndex)»]'''
			}
		}
	}

	/**
	 * prints the initial value of the given variable. If the variable has no
	 * initial value, the value that corresponds to the neutral element of
	 * the variable's type is returned.
	 *
	 * variable is never an array.
	 */
	def printInitialValue(Var variable) {
		val type = variable.type
		val value = variable.initialValue
		if (value === null) {
			'''«TypeUtil.getSize(type)»'b0'''
		} else {
			doSwitch(value)
		}
	}

	def printStateVar(Entity entity, Var variable) {
		val name = namer.getName(variable)

		'''
		«IF variable.assignable || variable.type.array»
			reg «doSwitch(variable)»;
			«IF variable.type.array»
				initial $readmemh("«getAbsoluteHexPath(entity, name)»", «name»);
			«ENDIF»
		«ELSE»
			localparam «doSwitch(variable)» = «printInitialValue(variable)»;
		«ENDIF»
		'''
	}

}
