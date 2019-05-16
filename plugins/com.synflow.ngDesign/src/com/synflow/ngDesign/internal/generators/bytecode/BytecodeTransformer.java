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
package com.synflow.ngDesign.internal.generators.bytecode;

import com.google.common.collect.ImmutableList;
import com.synflow.core.transformations.AbstractTransformer;
import com.synflow.core.transformations.ExpressionTransformation;
import com.synflow.core.transformations.SchedulerTransformation;
import com.synflow.core.transformations.Transformation;
import com.synflow.models.ir.transform.PhiRemoval;
import com.synflow.models.ir.transform.SSATransformation;
import com.synflow.models.ir.transform.SSAVariableRenamer;
import com.synflow.ngDesign.transformations.AddBufferedInputs;
import com.synflow.ngDesign.transformations.AddReadyAssignments;
import com.synflow.ngDesign.transformations.CodeCleaner;

/**
 * This class defines a transformer for bytecode generation.
 *
 * @author Matthieu Wipliez
 *
 */
public class BytecodeTransformer extends AbstractTransformer {

	@Override
	protected Iterable<Transformation> getTransformations() {
		// only cleans code of scheduler
		// because CodeCleaner moves assignments around, which does not work well in this case
		// because in Java variables are updated immediately (as opposed to end of cycle as in HDL)

		return ImmutableList.<Transformation> of( //
				// cleans up code in scheduler
				new SchedulerTransformation(new SSATransformation()), //
				new SchedulerTransformation(new CodeCleaner()), //
				new SchedulerTransformation(new PhiRemoval()), //
				new SchedulerTransformation(new SSAVariableRenamer()), //
				//
				// adapts IR type system to Java and add casts where necessary
				new ExpressionTransformation(new BytecodeTypeSystemAdapter()), //
				//
				// transforms references/assignments to ready ports
				new AddReadyAssignments(), //
				new AddBufferedInputs());
	}

}
