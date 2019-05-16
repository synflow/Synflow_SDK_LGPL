/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.dpn.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.synflow.models.dpn.Connection;
import com.synflow.models.dpn.DPN;
import com.synflow.models.dpn.DpnPackage;
import com.synflow.models.dpn.Endpoint;
import com.synflow.models.dpn.Instance;
import com.synflow.models.dpn.Port;
import com.synflow.models.graph.Edge;
import com.synflow.models.graph.Graph;
import com.synflow.models.graph.GraphFactory;
import com.synflow.models.graph.GraphPackage;
import com.synflow.models.graph.Vertex;

/**
 * This class defines a hierarchical DPN network.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * @generated
 */
public class DPNImpl extends EntityImpl implements DPN {

	/**
	 * This class defines an adapter that watches when vertex is set to initialize the
	 * incomingMap/outgoingMap and installs an adapter on the graph. We watch vertex because when
	 * deserializing, graph is constructed before vertex is set.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private static class DPNAdapter extends AdapterImpl {

		@Override
		public void notifyChanged(Notification notification) {
			int featureID = notification.getFeatureID(DPN.class);
			if (featureID == DpnPackage.DPN__VERTEX) {
				if (notification.getEventType() == Notification.SET) {
					DPNImpl dpn = (DPNImpl) getTarget();
					Graph graph = dpn.graph;

					// initialize maps and adds adapter
					dpn.incomingMap = new HashMap<>();
					dpn.outgoingMap = new HashMap<>();

					graph.eAdapters().add(new EdgeAdapter(dpn.incomingMap, dpn.outgoingMap));
				}
			}
		}

	}

	private static class EdgeAdapter extends AdapterImpl {

		private final Map<Vertex, Map<Port, List<Connection>>> incomingMap;

		private final Map<Vertex, Map<Port, List<Connection>>> outgoingMap;

		public EdgeAdapter(Map<Vertex, Map<Port, List<Connection>>> incomingMap,
				Map<Vertex, Map<Port, List<Connection>>> outgoingMap) {
			this.incomingMap = incomingMap;
			this.outgoingMap = outgoingMap;
		}

		private void add(Map<Vertex, Map<Port, List<Connection>>> dpnMap, Connection connection,
				Vertex vertex, Port port) {
			Map<Port, List<Connection>> map = getVertexMap(dpnMap, vertex);

			List<Connection> connections = map.get(port);
			if (connections == null) {
				connections = new ArrayList<>();
				map.put(port, connections);
			}
			connections.add(connection);
		}

		/**
		 * Adds the given connection to incomingMap and outgoingMap.
		 * 
		 * @param connection
		 */
		private void addConnection(Connection connection) {
			add(incomingMap, connection, connection.getTarget(), connection.getTargetPort());
			add(outgoingMap, connection, connection.getSource(), connection.getSourcePort());
		}

		/**
		 * Makes sure that the vertex is present in incomingMap and outgoingMap, so an unconnected
		 * vertex won't cause an NPE in getIncoming/getOutgoing.
		 * 
		 * @param vertex
		 */
		private void addVertex(Vertex vertex) {
			getVertexMap(incomingMap, vertex);
			getVertexMap(outgoingMap, vertex);
		}

		private Map<Port, List<Connection>> getVertexMap(
				Map<Vertex, Map<Port, List<Connection>>> dpnMap, Vertex vertex) {
			Map<Port, List<Connection>> map = dpnMap.get(vertex);
			if (map == null) {
				map = new HashMap<>();
				dpnMap.put(vertex, map);
			}
			return map;
		}

		@Override
		public void notifyChanged(Notification notification) {
			int eventType = notification.getEventType();
			int featureID = notification.getFeatureID(Graph.class);
			if (featureID == GraphPackage.GRAPH__EDGES) {
				if (eventType == Notification.ADD) {
					addConnection((Connection) notification.getNewValue());
				} else if (eventType == Notification.ADD_MANY) {
					List<?> edges = (List<?>) notification.getNewValue();
					for (Object edge : edges) {
						addConnection((Connection) edge);
					}
				} else if (eventType == Notification.REMOVE) {
					removeConnection((Connection) notification.getOldValue());
				} else if (eventType == Notification.REMOVE_MANY) {
					List<?> edges = (List<?>) notification.getOldValue();
					for (Object edge : edges) {
						removeConnection((Connection) edge);
					}
				}
			} else if (featureID == GraphPackage.GRAPH__VERTICES) {
				if (notification.getEventType() == Notification.ADD) {
					addVertex((Vertex) notification.getNewValue());
				} else if (notification.getEventType() == Notification.ADD_MANY) {
					List<?> vertices = (List<?>) notification.getNewValue();
					for (Object vertex : vertices) {
						addVertex((Vertex) vertex);
					}
				}
			}
		}

		private void remove(Map<Vertex, Map<Port, List<Connection>>> dpnMap, Connection connection,
				Vertex vertex, Port port) {
			Map<Port, List<Connection>> map = getVertexMap(dpnMap, vertex);

			List<Connection> connections = map.get(port);
			if (connections != null) {
				connections.remove(connection);
			}
		}

		private void removeConnection(Connection connection) {
			remove(incomingMap, connection, connection.getTarget(), connection.getTargetPort());
			remove(outgoingMap, connection, connection.getSource(), connection.getSourcePort());
		}

		@Override
		public void setTarget(Notifier newTarget) {
			super.setTarget(newTarget);

			// we do that so that maps are properly initialized when graph is deserialized
			Graph graph = (Graph) getTarget();
			for (Vertex vertex : graph.getVertices()) {
				addVertex(vertex);
			}

			for (Edge edge : graph.getEdges()) {
				addConnection((Connection) edge);
			}
		}

	}

	/**
	 * The cached value of the '{@link #getGraph() <em>Graph</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getGraph()
	 * @generated
	 * @ordered
	 */
	protected Graph graph;

	private Map<Vertex, Map<Port, List<Connection>>> incomingMap;

	/**
	 * The cached value of the '{@link #getInstances() <em>Instances</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInstances()
	 * @generated
	 * @ordered
	 */
	protected EList<Instance> instances;

	private Map<Vertex, Map<Port, List<Connection>>> outgoingMap;

	/**
	 * The cached value of the '{@link #getVertex() <em>Vertex</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVertex()
	 * @generated
	 * @ordered
	 */
	protected Vertex vertex;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	protected DPNImpl() {
		super();

		eAdapters().add(new DPNAdapter());
	}

	@Override
	public void add(Instance instance) {
		getGraph().add(instance);
		getInstances().add(instance);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Vertex basicGetVertex() {
		return vertex;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetGraph(Graph newGraph, NotificationChain msgs) {
		Graph oldGraph = graph;
		graph = newGraph;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					DpnPackage.DPN__GRAPH, oldGraph, newGraph);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DpnPackage.DPN__GRAPH:
			return getGraph();
		case DpnPackage.DPN__INSTANCES:
			return getInstances();
		case DpnPackage.DPN__VERTEX:
			if (resolve)
				return getVertex();
			return basicGetVertex();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
			NotificationChain msgs) {
		switch (featureID) {
		case DpnPackage.DPN__GRAPH:
			return basicSetGraph(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case DpnPackage.DPN__GRAPH:
			return graph != null;
		case DpnPackage.DPN__INSTANCES:
			return instances != null && !instances.isEmpty();
		case DpnPackage.DPN__VERTEX:
			return vertex != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case DpnPackage.DPN__GRAPH:
			setGraph((Graph) newValue);
			return;
		case DpnPackage.DPN__INSTANCES:
			getInstances().clear();
			getInstances().addAll((Collection<? extends Instance>) newValue);
			return;
		case DpnPackage.DPN__VERTEX:
			setVertex((Vertex) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DpnPackage.Literals.DPN;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DpnPackage.DPN__GRAPH:
			setGraph((Graph) null);
			return;
		case DpnPackage.DPN__INSTANCES:
			getInstances().clear();
			return;
		case DpnPackage.DPN__VERTEX:
			setVertex((Vertex) null);
			return;
		}
		super.eUnset(featureID);
	}

	@Override
	public Connection getConnection(Endpoint target) {
		List<Connection> connections = incomingMap.get(target.getVertex()).get(target.getPort());
		if (connections == null || connections.isEmpty()) {
			return null;
		}
		return connections.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Graph getGraph() {
		return graph;
	}

	@Override
	public Endpoint getIncoming(Endpoint endpoint) {
		Connection connection = getConnection(endpoint);
		return connection == null ? null : connection.getSourceEndpoint();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Connection> getIncoming(Instance instance) {
		EList<?> edges = instance.getIncoming();
		return (List<Connection>) edges;
	}

	@Override
	public Endpoint getIncoming(Port port) {
		return getIncoming(new Endpoint(this, port));
	}

	@Override
	public Instance getInstance(String name) {
		for (Instance instance : getInstances()) {
			if (instance.getName().equals(name)) {
				return instance;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Instance> getInstances() {
		if (instances == null) {
			instances = new EObjectResolvingEList<Instance>(Instance.class, this,
					DpnPackage.DPN__INSTANCES);
		}
		return instances;
	}

	@Override
	public int getNumIncoming(Instance instance, Port port) {
		List<Connection> connections = incomingMap.get(instance).get(port);
		return connections == null ? 0 : connections.size();
	}

	@Override
	public int getNumIncoming(Port port) {
		List<Connection> connections = incomingMap.get(getVertex()).get(port);
		return connections == null ? 0 : connections.size();
	}

	@Override
	public List<Endpoint> getOutgoing(Endpoint endpoint) {
		List<Connection> connections = getOutgoingList(endpoint.getVertex(), endpoint.getPort());
		List<Endpoint> endpoints = new ArrayList<>(connections.size());
		for (Connection connection : connections) {
			endpoints.add(connection.getTargetEndpoint());
		}
		return endpoints;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Connection> getOutgoing(Instance instance) {
		EList<?> edges = instance.getOutgoing();
		return (List<Connection>) edges;
	}

	@Override
	public List<Connection> getOutgoing(final Port port) {
		return getOutgoingList(getVertex(), port);
	}

	private List<Connection> getOutgoingList(Vertex vertex, Port port) {
		List<Connection> connections = outgoingMap.get(vertex).get(port);
		if (connections == null) {
			return Collections.emptyList();
		}
		return connections;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Vertex getVertex() {
		if (vertex != null && vertex.eIsProxy()) {
			InternalEObject oldVertex = (InternalEObject) vertex;
			vertex = (Vertex) eResolveProxy(oldVertex);
			if (vertex != oldVertex) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							DpnPackage.DPN__VERTEX, oldVertex, vertex));
			}
		}
		return vertex;
	}

	@Override
	public void init() {
		setGraph(GraphFactory.eINSTANCE.createGraph());
		Vertex vertex = GraphFactory.eINSTANCE.createVertex();
		setVertex(vertex);
		getGraph().add(vertex);
	}

	@Override
	public void remove(Instance instance) {
		getInstances().remove(instance);
		getGraph().remove(instance);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setGraph(Graph newGraph) {
		if (newGraph != graph) {
			NotificationChain msgs = null;
			if (graph != null)
				msgs = ((InternalEObject) graph).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - DpnPackage.DPN__GRAPH, null, msgs);
			if (newGraph != null)
				msgs = ((InternalEObject) newGraph).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - DpnPackage.DPN__GRAPH, null, msgs);
			msgs = basicSetGraph(newGraph, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DpnPackage.DPN__GRAPH, newGraph,
					newGraph));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setVertex(Vertex newVertex) {
		Vertex oldVertex = vertex;
		vertex = newVertex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DpnPackage.DPN__VERTEX, oldVertex,
					vertex));
	}

	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();
		return "network " + getName();
	}

}
