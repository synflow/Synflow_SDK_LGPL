/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.ir;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each
 * non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see com.synflow.models.ir.IrPackage
 * @generated
 */
public interface IrFactory extends EFactory {

	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	IrFactory eINSTANCE = com.synflow.models.ir.impl.IrFactoryImpl.init();

	Expression and(Expression e1, Expression e2);

	/**
	 * Returns a new object of class '<em>Expr Cast</em>'. <!-- begin-user-doc --> Creates a cast.
	 * Type's size is copied.<!-- end-user-doc -->
	 * 
	 * @param target
	 * @param source
	 * @param expr
	 * 
	 * @return a new object of class '<em>Expr Cast</em>'.
	 */
	Expression cast(Type target, Type source, Expression expr);

	/**
	 * Returns a new object of class '<em>Expr Cast</em>'. <!-- begin-user-doc --> Creates a cast.
	 * Type's size is copied.<!-- end-user-doc -->
	 * 
	 * @param target
	 * @param source
	 * @param expr
	 * @param forceResize
	 * 
	 * @return a new object of class '<em>Expr Cast</em>'.
	 */
	Expression cast(Type target, Type source, Expression expr, boolean forceResize);

	/**
	 * Creates resize(unsigned(expr), size)
	 * 
	 * @param size
	 * @param expr
	 * @return
	 */
	Expression castToUnsigned(int size, Expression expr);

	/**
	 * Returns a new object of class '<em>Expr Cast</em>'. <!-- begin-user-doc --> Creates a cast.
	 * Type's size is copied.<!-- end-user-doc -->
	 * 
	 * @param typeName
	 * @param expr
	 * 
	 * @return a new object of class '<em>Expr Cast</em>'.
	 */
	ExprTypeConv convert(String typeName, Expression expr);

	/**
	 * Returns a new object of class '<em>Block Basic</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Block Basic</em>'.
	 * @generated
	 */
	BlockBasic createBlockBasic();

	/**
	 * Returns a new object of class '<em>Block If</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Block If</em>'.
	 * @generated
	 */
	BlockIf createBlockIf();

	/**
	 * Returns a new object of class '<em>Block While</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Block While</em>'.
	 * @generated
	 */
	BlockWhile createBlockWhile();

	/**
	 * Returns a new object of class '<em>Def</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Def</em>'.
	 * @generated
	 */
	Def createDef();

	/**
	 * Creates a new definition of the given variable.
	 * 
	 * @param variable
	 *            a variable
	 * @return a new definition of the given variable
	 */
	Def createDef(Var variable);

	/**
	 * Returns a new object of class '<em>Expr Binary</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr Binary</em>'.
	 * @generated
	 */
	ExprBinary createExprBinary();

	/**
	 * Creates a new ExprBinary.
	 * 
	 * @param e1
	 * @param op
	 * @param e2
	 * @return
	 */
	ExprBinary createExprBinary(Expression e1, OpBinary op, Expression e2);

	/**
	 * Returns a new object of class '<em>Expr Bool</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Expr Bool</em>'.
	 * @generated
	 */
	ExprBool createExprBool();

	ExprBool createExprBool(boolean value);

	/**
	 * Returns a new object of class '<em>Expr Float</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr Float</em>'.
	 * @generated
	 */
	ExprFloat createExprFloat();

	ExprFloat createExprFloat(BigDecimal value);

	ExprFloat createExprFloat(float value);

	/**
	 * Returns a new object of class '<em>Expr Int</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Expr Int</em>'.
	 * @generated
	 */
	ExprInt createExprInt();

	ExprInt createExprInt(BigInteger value);

	ExprInt createExprInt(int value);

	/**
	 * Returns a new object of class '<em>Expr List</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Expr List</em>'.
	 * @generated
	 */
	ExprList createExprList();

	ExprList createExprList(List<Expression> exprs);

	/**
	 * Returns a new object of class '<em>Expr Resize</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr Resize</em>'.
	 * @generated
	 */
	ExprResize createExprResize();

	/**
	 * Returns a new object of class '<em>Expr Ternary</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr Ternary</em>'.
	 * @generated
	 */
	ExprTernary createExprTernary();

	/**
	 * Returns a new object of class '<em>Expr Ternary</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr Ternary</em>'.
	 */
	ExprTernary createExprTernary(Expression cond, Expression e1, Expression e2);

	/**
	 * Returns a new object of class '<em>Expr String</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr String</em>'.
	 * @generated
	 */
	ExprString createExprString();

	ExprString createExprString(String value);

	/**
	 * Returns a new object of class '<em>Expr Type Conv</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr Type Conv</em>'.
	 * @generated
	 */
	ExprTypeConv createExprTypeConv();

	/**
	 * Returns a new object of class '<em>Expr Unary</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr Unary</em>'.
	 * @generated
	 */
	ExprUnary createExprUnary();

	/**
	 * Creates a new ExprUnary.
	 * 
	 * @param op
	 * @param expression
	 * @return
	 */
	ExprUnary createExprUnary(OpUnary op, Expression expression);

	/**
	 * Returns a new object of class '<em>Expr Var</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Expr Var</em>'.
	 * @generated
	 */
	ExprVar createExprVar();

	ExprVar createExprVar(Var variable);

	/**
	 * Returns a new object of class '<em>Inst Assign</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Inst Assign</em>'.
	 * @generated
	 */
	InstAssign createInstAssign();

	/**
	 * Creates an InstAssign with the given target and value.
	 * 
	 * @param target
	 *            target variable
	 * @param value
	 *            value
	 * @return an InstAssign with the given location, target and value.
	 */
	InstAssign createInstAssign(Var target, Expression value);

	/**
	 * Returns a new object of class '<em>Inst Call</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Inst Call</em>'.
	 * @generated
	 */
	InstCall createInstCall();

	/**
	 * Creates an InstCall with the given location, target, procedure, parameters.
	 * 
	 * @param lineNumber
	 * @param target
	 * @param procedure
	 * @param parameters
	 * @return a call
	 */
	InstCall createInstCall(int lineNumber, Var target, Procedure procedure,
			List<Expression> parameters);

	/**
	 * Returns a new object of class '<em>Inst Load</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Inst Load</em>'.
	 * @generated
	 */
	InstLoad createInstLoad();

	/**
	 * Creates an InstLoad with the given target and source.
	 * 
	 * @param target
	 * @param source
	 * @return a load
	 */
	InstLoad createInstLoad(Def target, Var source);

	InstLoad createInstLoad(int lineNumber, Var target, Var source);

	/**
	 * Creates an InstLoad with the given location, target, source, indexes.
	 * 
	 * @param lineNumber
	 * @param target
	 * @param source
	 * @param indexes
	 * @return a load
	 */
	InstLoad createInstLoad(int lineNumber, Var target, Var source, List<Expression> indexes);

	/**
	 * Returns a new object of class '<em>Inst Phi</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Inst Phi</em>'.
	 * @generated
	 */
	InstPhi createInstPhi();

	/**
	 * Creates an InstPhi with the given location, target, and values.
	 * 
	 * @param lineNumber
	 * @param target
	 * @param values
	 * @return a phi
	 */
	InstPhi createInstPhi(int lineNumber, Def target, List<Expression> values);

	/**
	 * Creates an InstPhi with the given target and values.
	 * 
	 * @param target
	 * @param values
	 * @return a phi
	 */
	InstPhi createInstPhi(Var target, List<Expression> values);

	/**
	 * Returns a new object of class '<em>Inst Return</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Inst Return</em>'.
	 * @generated
	 */
	InstReturn createInstReturn();

	/**
	 * Creates an InstReturn with the given location and value.
	 * 
	 * @param value
	 * @return a return
	 */
	InstReturn createInstReturn(Expression value);

	/**
	 * Returns a new object of class '<em>Inst Store</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Inst Store</em>'.
	 * @generated
	 */
	InstStore createInstStore();

	/**
	 * Creates an InstStore with the given location, target, indexes, value.
	 * 
	 * @param lineNumber
	 * @param target
	 * @param source
	 * @param indexes
	 * @return a store
	 */
	InstStore createInstStore(int lineNumber, Var target, Collection<Expression> indexes,
			Expression value);

	InstStore createInstStore(int lineNumber, Var target, Expression value);

	InstStore createInstStore(Var target, Expression value);

	/**
	 * Returns a new object of class '<em>Procedure</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Procedure</em>'.
	 * @generated
	 */
	Procedure createProcedure();

	/**
	 * Creates a new procedure, not external, with empty parameters, locals, and blocks. Return type
	 * is copied if needed.
	 * 
	 * @param name
	 *            The procedure name.
	 * @param lineNumber
	 *            the number of the line at which the procedure is defined
	 * @param returnType
	 *            The procedure return type.
	 */
	Procedure createProcedure(String name, int lineNumber, Type returnType);

	/**
	 * Returns a new object of class '<em>Type Array</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Type Array</em>'.
	 * @generated
	 */
	TypeArray createTypeArray();

	/**
	 * Creates a new array type with the given size and element type. Type is copied if needed.
	 * 
	 * @param type
	 *            the type of elements
	 * @param dimensions
	 *            dimensions
	 */
	TypeArray createTypeArray(Type type, int... dimensions);

	/**
	 * Returns a new object of class '<em>Type Bool</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Type Bool</em>'.
	 * @generated
	 */
	TypeBool createTypeBool();

	/**
	 * Returns a new object of class '<em>Type Float</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Type Float</em>'.
	 * @generated
	 */
	TypeFloat createTypeFloat();

	/**
	 * Creates a new float type with the given size (the size could only be 16, 32 or 64).
	 * 
	 * @param size
	 *            the size of this float type
	 */
	TypeFloat createTypeFloat(int size);

	/**
	 * Returns a new object of class '<em>Type Int</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Type Int</em>'.
	 * @generated
	 */
	TypeInt createTypeInt();

	/**
	 * Creates a new int type with the given size.
	 * 
	 * @param size
	 *            the size of this int type
	 */
	TypeInt createTypeInt(int size, boolean signed);

	/**
	 * Creates a TypeInt or TypeUint depending on the sign of value. 0 is considered positive.
	 * 
	 * @param value
	 *            a value
	 * @return a TypeInt or TypeUint depending on the sign of value
	 */
	TypeInt createTypeIntOrUint(BigInteger value);

	/**
	 * Returns a new object of class '<em>Type String</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Type String</em>'.
	 * @generated
	 */
	TypeString createTypeString();

	Type createTypeString(int size);

	/**
	 * Returns a new object of class '<em>Type Void</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Type Void</em>'.
	 * @generated
	 */
	TypeVoid createTypeVoid();

	/**
	 * Returns a new object of class '<em>Use</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Use</em>'.
	 * @generated
	 */
	Use createUse();

	/**
	 * Creates a new use of the given variable.
	 * 
	 * @param variable
	 *            a variable
	 * @return a new use of the given variable
	 */
	Use createUse(Var variable);

	/**
	 * Returns a new object of class '<em>Var</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Var</em>'.
	 * @generated
	 */
	Var createVar();

	/**
	 * Creates a new variable with the given location, type, and name. The variable may be
	 * assignable or not. Type is copied if needed.
	 * 
	 * @param lineNumber
	 *            the number of the line at which the variable is defined
	 * @param type
	 *            the variable type
	 * @param name
	 *            the variable name
	 * @param assignable
	 *            whether this variable is assignable
	 */
	Var createVar(int lineNumber, Type type, String name, boolean assignable);

	/**
	 * Creates a new variable with the given location, type, name, initial value. The variable may
	 * be assignable or not. Type is copied if needed.
	 * 
	 * @param lineNumber
	 *            the number of the line at which the variable is defined
	 * @param type
	 *            the variable type
	 * @param name
	 *            the variable name
	 * @param assignable
	 *            whether this variable is assignable
	 * @param initialValue
	 *            initial value of this variable
	 */
	Var createVar(int lineNumber, Type type, String name, boolean assignable,
			Expression initialValue);

	/**
	 * Creates a new variable with the given location, type, name, index. The variable may be
	 * assignable or not. Type is copied if needed.
	 * 
	 * @param lineNumber
	 *            the number of the line at which the variable is defined
	 * @param type
	 *            the variable type
	 * @param name
	 *            the variable name
	 * @param assignable
	 *            whether this variable is assignable
	 * @param index
	 *            index of this variable
	 */
	Var createVar(int lineNumber, Type type, String name, boolean assignable, int index);

	/**
	 * Creates a new local variable with the given type, name, index. The variable may be assignable
	 * or not. Type is copied if needed.
	 * 
	 * @param type
	 *            the variable type
	 * @param name
	 *            the variable name
	 * @param assignable
	 *            whether this variable is assignable
	 */
	Var createVar(Type type, String name, boolean assignable);

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	IrPackage getIrPackage();

	/**
	 * Creates a new local variable that can be used to hold intermediate results. The variable is
	 * added to procedure's locals. Type is copied if needed.
	 * 
	 * @param type
	 *            type of the variable
	 * @param name
	 *            hint for the variable name
	 * @return a new local variable
	 */
	Var newTempLocalVariable(Procedure procedure, Type type, String hint);

	Expression not(Var var);

	Expression or(Expression e1, Expression e2);

} // IrFactory
