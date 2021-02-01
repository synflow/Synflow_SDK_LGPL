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
package com.synflow.ngDesign.internal.generators.verilog;

import com.google.common.collect.ImmutableList;
import com.synflow.core.transformations.AbstractTransformer;
import com.synflow.core.transformations.BodyTransformation;
import com.synflow.core.transformations.ExpressionTransformation;
import com.synflow.core.transformations.ProcedureTransformation;
import com.synflow.core.transformations.Transformation;
import com.synflow.models.dpn.Entity;
import com.synflow.models.ir.transform.PhiRemoval;
import com.synflow.models.ir.transform.SSATransformation;
import com.synflow.models.ir.transform.SSAVariableRenamer;
import com.synflow.models.ir.transform.StoreOnceTransformation;
import com.synflow.models.util.Void;
import com.synflow.ngDesign.internal.generators.verilog.transformations.LoopIndexMaker;
import com.synflow.ngDesign.internal.generators.verilog.transformations.ResizeExtractor;
import com.synflow.ngDesign.internal.generators.verilog.transformations.VerilogTypeSystemAdapter;
import com.synflow.ngDesign.transformations.CodeCleaner;
import com.synflow.ngDesign.transformations.AddReadyAssignments;
import com.synflow.ngDesign.transformations.AddBufferedInputs;

/**
 * This class defines a transformer for Verilog.
 *
 * @author Matthieu Wipliez
 *
 */
public class VerilogTransformer extends AbstractTransformer {

	@Override
	public Void caseEntity(Entity entity) {
		computeImportList(entity);
		return super.caseEntity(entity);
	}

	@Override
	protected Iterable<Transformation> getTransformations() {
		return ImmutableList.<Transformation> of( //
				// makes sure there is at most one store per variable per cycle
				new BodyTransformation(new StoreOnceTransformation()),
				//
				// cleans up code
				new ProcedureTransformation(new SSATransformation()), //
				new ProcedureTransformation(new CodeCleaner()), //
				new ProcedureTransformation(new PhiRemoval()), //
				new ProcedureTransformation(new SSAVariableRenamer()), //
				//
				// adapts IR type system to Verilog and add casts where necessary
				new ExpressionTransformation(new VerilogTypeSystemAdapter()), //
				new ExpressionTransformation(new ResizeExtractor()), //
				//
				// adds index for "for" loops to copy arrays
				new ProcedureTransformation(new LoopIndexMaker()),
				//
				// transforms references/assignments to ready ports
				new AddReadyAssignments(),
				new AddBufferedInputs());
	}

}
