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
package com.synflow.ngDesign.internal.generators;

import java.util.List;

import com.synflow.ngDesign.internal.generators.Namer;
import com.synflow.models.dpn.Endpoint;
import com.synflow.models.ir.Block;
import com.synflow.models.ir.BlockBasic;
import com.synflow.models.ir.Expression;
import com.synflow.models.ir.InstReturn;
import com.synflow.models.ir.Procedure;

/**
 * This class defines common extensions used by code generators.
 *
 * @author Matthieu Wipliez
 *
 */
public class GeneratorExtensions {

	/**
	 * Returns the expression that this scheduler procedure reduces to. The scheduler must be
	 * inlinable.
	 *
	 * @param scheduler
	 *            a scheduler
	 * @return an expression
	 */
	public static Expression getExpression(Procedure scheduler) {
		BlockBasic basic = (BlockBasic) scheduler.getBlocks().get(0);
		InstReturn instReturn = (InstReturn) basic.getInstructions().get(0);
		return instReturn.getValue();
	}

	/**
	 * Returns the number of hexadecimal digits required for the given number. Equivalent to
	 * <code>(int) Math.ceil((float) n / 4.0f)</code>.
	 *
	 * @param n
	 *            a (positive) number
	 * @return the number of hexadecimal digits required for <code>n</code>
	 */
	public static int getNumberOfHexadecimalDigits(int n) {
		return (n % 4 == 0) ? n / 4 : n / 4 + 1;
	}

	public static String getSignal(Namer namer, Endpoint endpoint) {
		if (endpoint.hasInstance()) {
			return endpoint.getInstance().getName() + "_" + endpoint.getPort().getName();
		} else {
			return namer.getName(endpoint.getPort());
		}
	}

	/**
	 * Returns <code>true</code> if the scheduler procedure can be inlined.
	 *
	 * @param scheduler
	 *            a scheduler procedure
	 */
	public static boolean isInlinable(Procedure scheduler) {
		if (scheduler.getLocals().isEmpty() && scheduler.getBlocks().size() == 1) {
			final Block block = scheduler.getBlocks().get(0);
			BlockBasic basic = (BlockBasic) block;
			return basic.getInstructions().size() == 1;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if a signal/wire is needed, either:
	 * <ul>
	 * <li>there is more than one connection (broadcast), or</li>
	 * <li>there is one outgoing connection to an instance.</li>
	 * </ul>
	 * In any other cases this method returns false:
	 * <ul>
	 * <li>when the list of endpoints is empty,</li>
	 * <li>when there is one endpoint that is an output port.</li>
	 * </ul>
	 */
	public static boolean isSignalNeeded(List<Endpoint> endpoints) {
		if (endpoints.isEmpty()) {
			return false;
		} else if (endpoints.size() > 1) {
			return true;
		} else {
			return endpoints.get(0).hasInstance();
		}
	}

}
