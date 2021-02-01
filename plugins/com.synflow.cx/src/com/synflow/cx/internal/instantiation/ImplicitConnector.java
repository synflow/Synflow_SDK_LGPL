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
package com.synflow.cx.internal.instantiation;

import static com.synflow.cx.CxUtil.isPort;
import static com.synflow.models.util.SwitchUtil.DONE;
import static com.synflow.models.util.SwitchUtil.visit;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.ExpressionVariable;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Network;
import com.synflow.cx.cx.StatementWrite;
import com.synflow.cx.cx.Task;
import com.synflow.cx.cx.VarRef;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.cx.services.VoidCxSwitch;
import com.synflow.models.dpn.Connection;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.Direction;
import com.synflow.models.dpn.DpnFactory;
import com.synflow.models.dpn.Endpoint;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Instance;
import com.synflow.models.dpn.Port;
import com.synflow.models.util.Void;

/**
 * This class visits and replaces references to implicit ports by actual ports.
 *
 * @author Matthieu Wipliez
 *
 */
public class ImplicitConnector extends VoidCxSwitch {

	private DPN dpn;

	@Inject
	private ConnectorHelper helper;

	private Instance instance;

	@Inject
	private IInstantiator instantiator;

	/**
	 * A map whose keys are Instance or DPN, and whose values are ports that can be written to
	 * (input ports for instance, output ports for DPN).
	 */
	private Multimap<EObject, Port> portMap;

	@Override
	public Void caseExpressionVariable(ExpressionVariable expr) {
		VarRef ref = expr.getSource();
		if (isPort(ref.getVariable())) {
			visitPort(ref);
		}

		return super.caseExpressionVariable(expr);
	}

	@Override
	public Void caseInst(Inst inst) {
		instance = instantiator.getMapping(dpn, inst);
		visit(this, inst.getTask());
		instance = null;
		return DONE;
	}

	@Override
	public Void caseStatementWrite(StatementWrite stmt) {
		// visit value first
		super.caseStatementWrite(stmt);

		visitPort(stmt.getPort());
		return DONE;
	}

	@Override
	public Void caseTask(Task task) {
		// must implement caseTask because it is not in VoidCxSwitch
		return visit(this, CxUtil.getVariables(task));
	}

	/**
	 * Connects the given network.
	 *
	 * @param portMap
	 * @param network
	 * @param dpn
	 */
	public void connect(Multimap<EObject, Port> portMap, Network network, DPN dpn) {
		this.portMap = portMap;
		this.dpn = dpn;

		visit(this, network.getInstances());

		this.dpn = null;
		this.portMap = null;
	}

	/**
	 * Creates a new port from the given parameters, and adds a connection to the DPN associated
	 * with the network containing the instance.
	 *
	 * @param link
	 *            link
	 * @param instance
	 *            an instance
	 * @param otherPort
	 *            IR port
	 * @param ref
	 *            reference to the port
	 * @return a new IR port
	 */
	private Port getConnectedPort(String link, Instance instance, Endpoint otherEndPoint) {
		boolean isDpnPort = !otherEndPoint.hasInstance();

		// create connection
		DPN dpn = instance.getDPN();
		Port otherPort = otherEndPoint.getPort();
		boolean isOtherInput = otherPort.getDirection() == Direction.INPUT;

		// compute otherEndPoint and portName
		String portName;
		if (isDpnPort) {
			// this port is defined in this instance or containing netwok
			portName = dpn.getSimpleName() + "_" + otherPort.getName();
		} else {
			// this port is defined by another instance
			portName = otherEndPoint.getInstance().getName() + "_" + otherPort.getName();

			// if this is an input port, remove it from the port map
			if (isOtherInput) {
				portMap.remove(otherEndPoint.getInstance(), otherPort);
			}
		}

		// create port with the right direction (same if in DPN, reversed if in other instance)
		boolean isThisInput = isDpnPort ? isOtherInput : !isOtherInput;
		Entity entity = instance.getEntity();
		Port thisPort = DpnFactory.eINSTANCE.createPort(otherPort.getType(), portName,
				otherPort.getInterface(), isThisInput ? entity.getInputs() : entity.getOutputs());

		// add connection to graph
		Connection conn;
		Endpoint thisEndPoint = new Endpoint(instance, thisPort);
		if (thisPort.getDirection() == Direction.INPUT) {
			conn = DpnFactory.eINSTANCE.createConnection(otherEndPoint, thisEndPoint);
		} else {
			conn = DpnFactory.eINSTANCE.createConnection(thisEndPoint, otherEndPoint);
		}
		dpn.getGraph().add(conn);

		return thisPort;
	}

	private void visitPort(VarRef ref) {
		Variable cxPort = ref.getVariable();
		Port port = instantiator.getMapping(instance.getEntity(), cxPort);
		if (port == null) {
			// reference is to another instance's port
			INode node = NodeModelUtils.getNode(ref);
			final String link = NodeModelUtils.getTokenText(node);

			Endpoint otherEndpoint = helper.getEndpoint(dpn, ref);
			if (otherEndpoint == null) {
				// may happen if link refers to a non-existent port
				return;
			}

			port = instantiator.getMapping(instance.getEntity(), otherEndpoint);
			if (port == null) {
				// we add a port to this entity and connect it to the other instance
				port = getConnectedPort(link, instance, otherEndpoint);
				instantiator.putMapping(instance.getEntity(), otherEndpoint, port);
			}
		}

		instantiator.putMapping(instance.getEntity(), ref, port);
	}

}
