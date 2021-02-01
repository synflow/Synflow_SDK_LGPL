/*
 * This file is part of the ngDesign SDK.
 *
 * Copyright (c) 2019 - 2021 Synflow SAS.
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
package com.synflow.cx.validation;

/**
 * This interface defines all codes for issues.
 *
 * @author Matthieu Wipliez
 *
 */
public interface IssueCodes {

	String ERR_ARRAY_MULTI_NON_POWER_OF_TWO = "errArrayMultiDimNonPowerOfTwo";

	/**
	 * when available is used outside of if/for/while
	 */
	String ERR_AVAILABLE = "errAvailable";

	String ERR_CANNOT_ASSIGN_CONST = "errCannotAssignConstant";

	String ERR_CMP_ALWAYS_FALSE = "errCmpAlwaysFalse";

	String ERR_CMP_ALWAYS_TRUE = "errCmpAlwaysTrue";

	/**
	 * / and % operators are only allowed with constants that are power of two.
	 */
	String ERR_DIV_MOD_NOT_CONST_POW_Of_TWO = "errDivModByNonPowerOfTwoConstant";

	String ERR_DUPLICATE_DECLARATIONS = "errDuplicateDeclarations";

	String ERR_ENTRY_FUNCTION_BAD_TYPE = "errEntryFunctionBadType";

	String ERR_EXPECTED_CONST = "errExpectedConstant";

	String ERR_FUNCTION_CALL = "errFunctionCall";

	String ERR_ILLEGAL_FENCE = "errIllegalFence";

	String ERR_LOCAL_NOT_INITIALIZED = "errLocalNotInitialized";

	/**
	 * multiple reads from the same port are forbidden in expressions
	 */
	String ERR_MULTIPLE_READS = "errMultipleReads";

	String ERR_NO_SIDE_EFFECTS = "errNoSideEffects";

	String ERR_SIDE_EFFECTS_FUNCTION = "errNonVoidHasSideEffects";

	String ERR_TYPE_MISMATCH = "errTypeMismatch";

	String ERR_TYPE_ONE_BIT = "errTypeOneBit";

	String ERR_UNRESOLVED_FUNCTION = "errUnresolvedFunction";

	String ERR_VAR_DECL = "errVariableDeclaration";

	String SHOULD_REPLACE_NAME = "shouldReplaceName";

	String SYNTAX_ERROR_ARRAY_BRACE = "syntaxErrorArrayBrace";

	String SYNTAX_ERROR_SINGLE_QUOTE = "syntaxErrorSingleQuote";

}
