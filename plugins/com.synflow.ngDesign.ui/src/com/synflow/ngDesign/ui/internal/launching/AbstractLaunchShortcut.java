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
package com.synflow.ngDesign.ui.internal.launching;

import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.ENTITY;
import static com.synflow.ngDesign.ICxLaunchConfigurationConstants.PROJECT;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.google.common.collect.Iterables;
import com.synflow.core.SynflowCore;
import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.Instantiable;
import com.synflow.cx.cx.Module;
import com.synflow.models.ir.util.IrUtil;
import com.synflow.models.util.EcoreHelper;

/**
 * This class defines an abstract launch shortcut usable on Cx entities.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class AbstractLaunchShortcut implements ILaunchShortcut2 {

	public static String getEntityName(Instantiable instantiable) {
		Module module = (Module) instantiable.eContainer();
		return module.getPackage() + "." + instantiable.getName();
	}

	/**
	 * Returns a configuration from the given collection of configurations that should be launched,
	 * or <code>null</code> to cancel. Default implementation opens a selection dialog that allows
	 * the user to choose one of the specified launch configurations. Returns the chosen
	 * configuration, or <code>null</code> if the user cancels.
	 *
	 * @param configList
	 *            list of configurations to choose from
	 * @return configuration to launch or <code>null</code> to cancel
	 */
	protected ILaunchConfiguration chooseConfiguration(List<ILaunchConfiguration> configList) {
		IDebugModelPresentation labelProvider = DebugUITools.newDebugModelPresentation();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(null, labelProvider);
		dialog.setElements(configList.toArray());
		dialog.setTitle("Select Cx entity");
		dialog.setMessage("&Select existing configuration:");
		dialog.setMultipleSelection(false);
		int result = dialog.open();
		labelProvider.dispose();
		if (result == Window.OK) {
			return (ILaunchConfiguration) dialog.getFirstResult();
		}
		return null;
	}

	private Instantiable chooseType(Instantiable[] instantiables, String title) {
		EntitySelectionDialog dialog = new EntitySelectionDialog(null, instantiables, title);
		if (dialog.open() == Window.OK) {
			return (Instantiable) dialog.getResult()[0];
		}
		return null;
	}

	private ILaunchConfiguration createConfiguration(IProject project, Instantiable instantiable) {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(getType());

		try {
			// generate configuration name
			String entityName = getEntityName(instantiable);
			String name = getLaunchConfigurationName(IrUtil.getSimpleName(entityName));
			name = manager.generateLaunchConfigurationName(name);

			// create configuration
			ILaunchConfigurationWorkingCopy wc = type.newInstance(null, name);
			wc.setAttribute(PROJECT, project.getName());
			wc.setAttribute(ENTITY, entityName);

			setDefaults(wc);

			return wc.doSave();
		} catch (CoreException e) {
			SynflowCore.log(e);
			return null;
		}
	}

	private Instantiable[] findInstantiables(IFile file) {
		Module module = EcoreHelper.getEObject(file, Module.class);
		List<CxEntity> entities = module.getEntities();
		Iterable<Instantiable> instantiables = Iterables.filter(entities, Instantiable.class);
		return Iterables.toArray(instantiables, Instantiable.class);
	}

	private List<ILaunchConfiguration> getCandidates(IProject project, Instantiable instantiable) {
		// configurations that match the given resource
		List<ILaunchConfiguration> configs = new ArrayList<ILaunchConfiguration>();

		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(getType());
		try {
			// candidates
			ILaunchConfiguration[] candidates = manager.getLaunchConfigurations(type);
			String qualifiedName = getEntityName(instantiable);
			for (ILaunchConfiguration config : candidates) {
				String projectName = config.getAttribute(PROJECT, "");
				String entityName = config.getAttribute(ENTITY, "");
				if (projectName.equals(project.getName()) && entityName.equals(qualifiedName)) {
					configs.add(config);
				}
			}

		} catch (CoreException e) {
			SynflowCore.log(e);
		}
		return configs;
	}

	@Override
	public IResource getLaunchableResource(IEditorPart editorpart) {
		IEditorInput input = editorpart.getEditorInput();
		if (input instanceof IFileEditorInput) {
			return ((IFileEditorInput) input).getFile();
		}

		return null;
	}

	@Override
	public IResource getLaunchableResource(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IFile) {
				return (IFile) obj;
			}
		}

		return null;
	}

	/**
	 * Finds configurations for the given project and instantiable, pick one or create one if none
	 * exists, and calls launch.
	 *
	 * @param project
	 * @param instantiable
	 * @param mode
	 */
	private ILaunchConfiguration getLaunchConfiguration(IProject project, Instantiable instantiable,
			String mode) {
		List<ILaunchConfiguration> configs = getCandidates(project, instantiable);
		int count = configs.size();
		if (count == 0) {
			// no candidates, create one
			return createConfiguration(project, instantiable);
		} else if (count == 1) {
			// exactly one candidate, it's the one
			return configs.get(0);
		} else {
			// otherwise must pick one config
			return chooseConfiguration(configs);
		}
	}

	/**
	 * Returns the name to use for a new launch configuration based on the given entity's simple
	 * name.
	 *
	 * @param name
	 *            simple name of an entity
	 * @return a name to use for a new launch configuration
	 */
	protected abstract String getLaunchConfigurationName(String name);

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		return null;
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		return null;
	}

	/**
	 * Returns the type to use for a new launch configuration.
	 *
	 * @return
	 */
	protected abstract String getType();

	@Override
	public void launch(IEditorPart editor, String mode) {
		IResource resource = getLaunchableResource(editor);
		if (resource instanceof IFile) {
			IFile file = (IFile) resource;
			searchAndLaunch(file, mode);
		}
	}

	/**
	 * Launches the given configuration.
	 *
	 * @param config
	 * @param mode
	 * @throws CoreException
	 */
	protected void launch(ILaunchConfiguration config, String mode) throws CoreException {
		config.launch(mode, null);
	}

	@Override
	public void launch(ISelection selection, String mode) {
		IResource resource = getLaunchableResource(selection);
		if (resource instanceof IFile) {
			IFile file = (IFile) resource;
			searchAndLaunch(file, mode);
		}
	}

	private void searchAndLaunch(IFile file, String mode) {
		Instantiable[] instantiables = findInstantiables(file);
		Instantiable instantiable = null;
		if (instantiables.length == 0) {
			MessageDialog.openError(null, "Launch Error", "No launchable entities were found");
		} else if (instantiables.length > 1) {
			instantiable = chooseType(instantiables, "Select Cx network");
		} else {
			instantiable = instantiables[0];
		}

		if (instantiable != null) {
			IProject project = file.getProject();
			ILaunchConfiguration config = getLaunchConfiguration(project, instantiable, mode);
			if (config != null) {
				try {
					launch(config, mode);
				} catch (CoreException e) {
					ErrorDialog.openError(null, null, null, e.getStatus());
				}
			}
		}
	}

	protected abstract void setDefaults(ILaunchConfigurationWorkingCopy wc);

}
