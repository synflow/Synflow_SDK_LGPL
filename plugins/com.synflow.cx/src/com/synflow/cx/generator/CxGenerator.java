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
package com.synflow.cx.generator;

import static com.synflow.cx.CxConstants.NAME_LOOP;
import static com.synflow.cx.CxConstants.NAME_LOOP_DEPRECATED;
import static com.synflow.cx.CxConstants.NAME_SETUP;
import static com.synflow.cx.CxConstants.NAME_SETUP_DEPRECATED;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.AbstractFileSystemAccess;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;

import com.google.gson.JsonElement;
import com.google.inject.Inject;
import com.synflow.core.SynflowCore;
import com.synflow.core.transformations.ProcedureTransformation;
import com.synflow.core.transformations.SchedulerTransformation;
import com.synflow.core.util.CoreUtil;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Bundle;
import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.CxPackage;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Module;
import com.synflow.cx.cx.Network;
import com.synflow.cx.cx.Task;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.cx.internal.compiler.ActorTransformer;
import com.synflow.cx.internal.compiler.CommentTranslator;
import com.synflow.cx.internal.compiler.FunctionTransformer;
import com.synflow.cx.internal.scheduler.CycleScheduler;
import com.synflow.cx.internal.scheduler.IfScheduler;
import com.synflow.cx.internal.scheduler.experimental.ExperimentalScheduler;
import com.synflow.cx.internal.services.EdgeColoring;
import com.synflow.models.dpn.Actor;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Instance;
import com.synflow.models.dpn.Unit;
import com.synflow.models.ir.transform.StoreOnceTransformation;
import com.synflow.models.util.EcoreHelper;
import com.synflow.models.util.Executable;

/**
 * This class defines a generator for Cx resources.
 *
 * @author Matthieu Wipliez
 */
public class CxGenerator implements IGenerator {

	@Inject
	private IInstantiator instantiator;

	/**
	 * Using the given file system access, compiles the given module and serializes the IR version
	 * of its entities.
	 *
	 * @param fsa
	 *            file system access
	 * @param module
	 *            Cx module
	 */
	private void compile(final IFileSystemAccess fsa, Module module) {
		// translate comments for this module
		new CommentTranslator(instantiator).doSwitch(module);

		for (final CxEntity cxEntity : module.getEntities()) {
			instantiator.forEachMapping(cxEntity, new Executable<Entity>() {
				@Override
				public void exec(Entity entity) {
					switch (cxEntity.eClass().getClassifierID()) {
					case CxPackage.BUNDLE:
						transformBundle((Bundle) cxEntity, (Unit) entity);
						break;

					case CxPackage.NETWORK:
						// transforms and serializes all inner tasks of the network
						DPN dpn = (DPN) entity;
						Iterable<Actor> actors = transformNetwork((Network) cxEntity, dpn);
						for (Actor inner : actors) {
							serialize(fsa, inner);
						}

						// serializes built-in entities instantiated by this network
						for (Instance instance : dpn.getInstances()) {
							Entity subEntity = instance.getEntity();
							if (CoreUtil.isBuiltin(subEntity)) {
								serialize(fsa, subEntity);
							}
						}
						break;

					case CxPackage.TASK:
						transformTask((Task) cxEntity, (Actor) entity);
						break;
					}

					serialize(fsa, entity);
				}
			});
		}
	}

	@Override
	public void doGenerate(Resource resource, IFileSystemAccess fsa) {
		// do nothing if the resource does not contain anything
		if (resource.getContents().isEmpty()) {
			return;
		}

		IFile file = EcoreHelper.getFile(resource);
		if (file != null) {
			try {
				file.deleteMarkers(EdgeColoring.TYPE, false, IResource.DEPTH_ZERO);
			} catch (CoreException e) {
				SynflowCore.log(e);
			}
		}

		// compile module
		compile(fsa, (Module) resource.getContents().get(0));
	}

	/**
	 * Using the given file system access, serializes the given entity.
	 *
	 * @param fsa
	 *            Xtext file system access
	 * @param entity
	 *            IR entity
	 */
	private void serialize(IFileSystemAccess fsa, Entity entity) {
		// serializes to byte array (never throws exception)
		OutputStream os = new ByteArrayOutputStream();
		try {
			entity.eResource().save(os, null);
		} catch (IOException e) {
			// byte array output stream never throws exception
		}

		// serialize to relative file name (obtained by deresolving URI against base URI)
		URI base = ((AbstractFileSystemAccess) fsa).getURI("");
		if (!base.lastSegment().isEmpty()) {
			// last segment must be empty for URI to be deresolved properly
			base = base.appendSegment("");
		}

		URI uri = entity.eResource().getURI();
		String fileName = uri.deresolve(base).toString();
		if (uri.isPlatformResource()) {
			CoreUtil.ensureCaseConsistency(new Path(uri.toPlatformString(true)));
		}
		fsa.generateFile(fileName, os.toString());

		// color edges of FSM (if applicable)
		new EdgeColoring().visit(entity);
	}

	/**
	 * Transforms the given bundle into a unit.
	 *
	 * @param bundle
	 *            Cx bundle
	 * @param unit
	 *            IR unit
	 */
	private void transformBundle(Bundle bundle, Unit unit) {
		transformConstantFunctions(unit, CxUtil.getVariables(bundle));
		new ProcedureTransformation(new LoadStoreReplacer()).doSwitch(unit);
	}

	/**
	 * Transforms constant functions of the given entity to IR procedures.
	 *
	 * @param entity
	 *            an entity
	 * @param variables
	 *            a list of declarations
	 */
	private void transformConstantFunctions(Entity entity, Iterable<Variable> variables) {
		for (Variable variable : variables) {
			if (CxUtil.isFunctionConstant(variable)) {
				// visit constant functions
				new FunctionTransformer(instantiator, entity).doSwitch(variable);
			}
		}
	}

	/**
	 * Calls {@link #transformTask(Task, Actor)} for each inner task of the given network, and
	 * returns the actors that were transformed.
	 *
	 * @param network
	 *            a network
	 * @param dpn
	 *            the DPN corresponding to the network
	 * @return an iterable over actors that correspond to inner tasks. May be empty.
	 */
	private Iterable<Actor> transformNetwork(Network network, DPN dpn) {
		List<Actor> innerTasks = new ArrayList<>();
		for (Inst inst : network.getInstances()) {
			final Task task = inst.getTask();
			if (task != null) {
				Instance instance = instantiator.getMapping(dpn, inst);
				Actor actor = (Actor) instance.getEntity();
				transformTask(task, actor);
				innerTasks.add(actor);
			}
		}
		return innerTasks;
	}

	/**
	 * Transforms the given task to an actor. Runs schedulers, transforms actor, beautifies FSM,
	 * runs several transformations on the code.
	 *
	 * @param task
	 *            Cx task
	 * @param actor
	 *            IR actor
	 */
	private void transformTask(Task task, Actor actor) {
		transformConstantFunctions(actor, CxUtil.getVariables(task));

		// finds init and run functions
		Variable setup = null;
		Variable loop = null;
		for (Variable function : CxUtil.getFunctions(task)) {
			String name = function.getName();
			if (NAME_SETUP.equals(name) || NAME_SETUP_DEPRECATED.equals(name)) {
				setup = function;
			} else if (NAME_LOOP.equals(name) || NAME_LOOP_DEPRECATED.equals(name)) {
				loop = function;
			}
		}

		JsonElement schedulerProp = actor.getProperties().get("scheduler");
		if (schedulerProp != null && "experimental".equals(schedulerProp.getAsString())) {
			ExperimentalScheduler scheduler = new ExperimentalScheduler(instantiator, actor);
			scheduler.schedule(setup, loop);
		} else {
			// schedules cycles, if statements, and transforms actor
			CycleScheduler scheduler = new CycleScheduler(instantiator, actor);
			scheduler.schedule(setup, loop);
			new IfScheduler(instantiator, actor).visit();
		}
		new ActorTransformer(instantiator, actor).visit();

		// post-process FSM: rename states and actions
		new FsmBeautifier().visit(actor);

		// promotes local variables used over more than one cycle to state variables
		// and replaces load/stores of local variables by use/assigns
		new VariablePromoter(actor.getVariables()).visit(actor);
		new ProcedureTransformation(new LoadStoreReplacer()).doSwitch(actor);

		// handle combinational behavior in actor
		new CombinationalVisitor().visit(actor);

		// apply store once transformation to scheduler and removes side effects
		new SchedulerTransformation(new StoreOnceTransformation()).doSwitch(actor);
		new SchedulerTransformation(new SideEffectRemover()).doSwitch(actor);
	}

}
