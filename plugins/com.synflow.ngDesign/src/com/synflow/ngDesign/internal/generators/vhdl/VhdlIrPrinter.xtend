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

import com.synflow.models.dpn.Port
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
import com.synflow.models.ir.IrFactory
import com.synflow.models.ir.Procedure
import com.synflow.models.ir.Type
import com.synflow.models.ir.TypeArray
import com.synflow.models.ir.TypeBool
import com.synflow.models.ir.TypeFloat
import com.synflow.models.ir.TypeInt
import com.synflow.models.ir.TypeString
import com.synflow.models.ir.Var
import com.synflow.models.ir.util.TypeUtil
import com.synflow.models.util.EcoreHelper
import com.synflow.ngDesign.internal.generators.Namer
import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import java.util.List
import org.eclipse.emf.ecore.EObject

import static com.synflow.ngDesign.internal.generators.vhdl.VhdlGeneratorUtil.computeVarMap

/**
 * This class defines the VHDL printer for the IR.
 *
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 */
class VhdlIrPrinter extends VhdlExpressionPrinter {

	protected static TypeBool typeBool = IrFactory.eINSTANCE.createTypeBool

	public boolean printTypeSize = true

	new(Namer namer) {
		super(namer)
	}

	override caseBlockBasic(BlockBasic node)
		'''
		«doSwitch(node.instructions)»
		'''

	override caseBlockIf(BlockIf ifNode)
		'''
		if («doSwitch(ifNode.condition)») then
		  «doSwitch(ifNode.thenBlocks)»
		«IF ifNode.elseRequired»
		else
		  «doSwitch(ifNode.elseBlocks)»
		«ENDIF»
		end if;
		«IF ifNode.joinBlock !== null»
		«doSwitch(ifNode.joinBlock)»
		«ENDIF»
		'''

	override caseBlockWhile(BlockWhile whileNode)
		'''
		while («doSwitch(whileNode.condition)») loop
		  «doSwitch(whileNode.blocks)»
		  «doSwitch(whileNode.joinBlock)»
		end loop;
		'''

	override caseInstAssign(InstAssign assign) {
		val target = assign.target.variable
		'''
		«getName(target)» := «doSwitch(assign.value)»;
		'''
	}

	override caseInstCall(InstCall call) {
		if (call.assert) {
			val expr = call.arguments.get(0)
			val exprStr = doSwitch(expr)
			'''assert «exprStr» report "«exprStr.toString.replace("\"", "'")»" severity failure;'''
		} else if (call.print) {
			'''
			print(«printWriteParams(call.arguments)»);
			'''
		} else {
			val called =
				if (call.procedure.parameters.empty) {
					call.procedure.name
				} else {
					'''«call.procedure.name»(«printCallParams(call.procedure.parameters, call.arguments)»)'''
				}

			if (call.target === null) {
				'''«called»;'''
			} else {
				val target = call.target.variable

				'''
				«IF target.type.bool»
				«getName(target)» := «called»;
				«ELSE»
				«getName(target)» := resize(«called», «(target.type as TypeInt).size»);
				«ENDIF»
				'''
			}
		}
	}

	override caseInstLoad(InstLoad load) {
		val source = load.source.variable
		var target = load.target.variable
		if (target.type.array) {
			copyArray(target, source)
		} else {
			'''
			«getName(target)» := «getName(source)»«printIndexes(load.indexes)»;
			'''
		}
	}

	override caseInstReturn(InstReturn instReturn) {
		if (instReturn.value !== null) {
			'''
			return «doSwitch(instReturn.value)»;
			'''
		}
	}

	override caseInstStore(InstStore store) {
		val target = store.target.variable
		val type = target.type

		if (target instanceof Port && type.int && !store.value.exprInt) {
			'''
			«getName(target)» <= std_logic_vector(«doSwitch(store.value)»);
			'''
		} else {
			val value = store.value
			if (value instanceof ExprVar) {
				val source = value.use.variable
				if (source.type.array) {
					return copyArray(target, source)
				}
			}

			'''
			«getName(target)»«printIndexes(store.indexes)» «IF target.local» := «ELSE» <= «ENDIF»«doSwitch(store.value)»;
			'''
		}
	}

	override caseTypeArray(TypeArray type) {
		var depth = 1
		for (dim : type.dimensions) {
			depth = depth * dim
		}
		depth = depth - 1
		'''array (0 to «depth») of «doSwitch(type.elementType)»'''
	}

	override caseTypeBool(TypeBool type) '''std_logic'''

	override caseTypeFloat(TypeFloat type) '''real'''

	override caseTypeInt(TypeInt type) {
		'''«IF type.signed»signed«ELSE»unsigned«ENDIF»«IF printTypeSize»(«type.size - 1» downto 0)«ENDIF»'''
	}

	override caseTypeString(TypeString type) {
		throw new IllegalArgumentException("unsupported String type")
	}

	override caseVar(Var variable) {
		val name = getName(variable)
		'''«IF variable.type.array»«name» : «variable.name»_type«ELSE»«name» : «doSwitch(variable.type)»«ENDIF»'''
	}

	def computeVarMap(List<Procedure> procedures) {
		varMap = new HashMap
		computeVarMap(varMap, procedures)
	}

	def private copyArray(Var target, Var source) {
		val procedure = EcoreHelper.getContainerOfType(if (target.global) source else target, typeof(Procedure))
		var loopVar = if (procedure.getLocal('loop_idx') === null) 'loop_idx' else '\\i^\\'
		val bound = (source.type as TypeArray).dimensions.fold(1, [acc, elt|acc * elt])

		'''
		for «loopVar» in 0 to «bound - 1» loop
		  «target.name»(«loopVar») «IF target.global»<«ELSE»:«ENDIF»= «source.name»(«loopVar»);
		end loop;
		'''
	}

	def declareTypeList(Collection<Var> variables)
		'''
		«FOR variable : variables»
		«IF variable.type.array»
		type «variable.name»_type is «doSwitch(variable.type)»;
		«ENDIF»
		«ENDFOR»
		'''

	def doSwitch(List<? extends EObject> objects)
		'''
		«FOR eObject : objects»
			«doSwitch(eObject)»
		«ENDFOR»
		'''

	def private getInitValue(Type type) {
		if (type.bool) {
			"'0'"
		} else {
			printQuotedValue(TypeUtil.getSize(type), 0bi)
		}
	}

	def private printCallParams(List<Var> parameters, List<Expression> arguments) {
		'''«FOR expr : arguments SEPARATOR ", "»«doSwitch(expr)»«ENDFOR»'''
	}

	def printFunction(Procedure procedure)
		'''
		«printFunctionSignature(procedure)» is
		  «declareTypeList(procedure.locals)»
		  «FOR local : procedure.locals»
		  	variable «doSwitch(local)»;
		  «ENDFOR»
		begin
		  «doSwitch(procedure.blocks)»
		end «procedure.name»;

		'''

	def printFunctionSignature(Procedure procedure) {
		printTypeSize = false
		val seq = '''impure function «procedure.name»«IF !procedure.parameters.empty»(«FOR param : procedure.parameters SEPARATOR "; "»«doSwitch(param)»«ENDFOR»)«ENDIF» return «doSwitch(procedure.returnType)»'''
		printTypeSize = true
		seq
	}

	def private printIndexes(List<Expression> indexes) {
		if (!indexes.empty) {
			'''(to_integer(«indexes.map[index|doSwitch(index)].join(' & ')»))'''
		}
	}

	/**
	 * prints the initial value of the given variable. If the variable has no
	 * initial value, the value that corresponds to the neutral element of
	 * the variable's type is returned.
	 */
	def printInitialValue(Var variable) {
		val type = variable.type
		val value = variable.initialValue

		if (type instanceof TypeArray) {
			if (value === null) {
				'''(others => «getInitValue(type.elementType)»)'''
			} else {
				'''(«doSwitch(value)»)'''
			}
		} else {
			if (value === null) {
				getInitValue(type)
			} else {
				doSwitch(value)
			}
		}
	}

	/**
	 * Prints the given state variable.
	 */
	def printStateVar(Var variable)
		'''
		«IF variable.assignable»
		signal «doSwitch(variable)»;
		«ELSE»
		constant «doSwitch(variable)» := «printInitialValue(variable)»;
		«ENDIF»
		'''

	def private printWriteParams(List<Expression> arguments) {
		val res = new ArrayList<CharSequence>
		for (expr : arguments) {
			val toStringExpr =
				if (expr.exprString) {
					doSwitch(expr)
				} else if (TypeUtil.getType(expr).bool) {
					'''to_string_93(to_bit(«doSwitch(expr)»))'''
				} else {
					'''to_hstring_93(to_bitvector(std_logic_vector(«doSwitch(expr)»)))'''
				}
			res.add(toStringExpr)
		}

		res.join(" & ")
	}

}
