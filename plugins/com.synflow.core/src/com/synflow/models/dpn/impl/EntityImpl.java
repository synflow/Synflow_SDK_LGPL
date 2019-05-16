/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.synflow.models.dpn.impl;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.google.gson.JsonObject;
import com.synflow.models.dpn.DpnPackage;
import com.synflow.models.dpn.Entity;
import com.synflow.models.dpn.Port;
import com.synflow.models.ir.Procedure;
import com.synflow.models.ir.Var;
import com.synflow.models.ir.util.IrUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Entity</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.synflow.models.dpn.impl.EntityImpl#getFileName <em>File Name</em>}</li>
 * <li>{@link com.synflow.models.dpn.impl.EntityImpl#getInputs <em>Inputs</em>}</li>
 * <li>{@link com.synflow.models.dpn.impl.EntityImpl#getLineNumber <em>Line Number</em>}</li>
 * <li>{@link com.synflow.models.dpn.impl.EntityImpl#getName <em>Name</em>}</li>
 * <li>{@link com.synflow.models.dpn.impl.EntityImpl#getOutputs <em>Outputs</em>}</li>
 * <li>{@link com.synflow.models.dpn.impl.EntityImpl#getProperties <em>Properties</em>}</li>
 * <li>{@link com.synflow.models.dpn.impl.EntityImpl#getProcedures <em>Procedures</em>}</li>
 * <li>{@link com.synflow.models.dpn.impl.EntityImpl#getVariables <em>Variables</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EntityImpl extends EObjectImpl implements Entity {

	/**
	 * The default value of the '{@link #getFileName() <em>File Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFileName() <em>File Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected String fileName = FILE_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInputs() <em>Inputs</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> inputs;

	/**
	 * The default value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected int lineNumber = LINE_NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOutputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> outputs;

	/**
	 * The default value of the '{@link #getProperties() <em>Properties</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected static final JsonObject PROPERTIES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected JsonObject properties = PROPERTIES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProcedures() <em>Procedures</em>}' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProcedures()
	 * @generated
	 * @ordered
	 */
	protected EList<Procedure> procedures;

	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<Var> variables;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected EntityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DpnPackage.ENTITY__FILE_NAME:
			return getFileName();
		case DpnPackage.ENTITY__INPUTS:
			return getInputs();
		case DpnPackage.ENTITY__LINE_NUMBER:
			return getLineNumber();
		case DpnPackage.ENTITY__NAME:
			return getName();
		case DpnPackage.ENTITY__OUTPUTS:
			return getOutputs();
		case DpnPackage.ENTITY__PROPERTIES:
			return getProperties();
		case DpnPackage.ENTITY__PROCEDURES:
			return getProcedures();
		case DpnPackage.ENTITY__VARIABLES:
			return getVariables();
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
		case DpnPackage.ENTITY__INPUTS:
			return ((InternalEList<?>) getInputs()).basicRemove(otherEnd, msgs);
		case DpnPackage.ENTITY__OUTPUTS:
			return ((InternalEList<?>) getOutputs()).basicRemove(otherEnd, msgs);
		case DpnPackage.ENTITY__PROCEDURES:
			return ((InternalEList<?>) getProcedures()).basicRemove(otherEnd, msgs);
		case DpnPackage.ENTITY__VARIABLES:
			return ((InternalEList<?>) getVariables()).basicRemove(otherEnd, msgs);
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
		case DpnPackage.ENTITY__FILE_NAME:
			return FILE_NAME_EDEFAULT == null ? fileName != null
					: !FILE_NAME_EDEFAULT.equals(fileName);
		case DpnPackage.ENTITY__INPUTS:
			return inputs != null && !inputs.isEmpty();
		case DpnPackage.ENTITY__LINE_NUMBER:
			return lineNumber != LINE_NUMBER_EDEFAULT;
		case DpnPackage.ENTITY__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case DpnPackage.ENTITY__OUTPUTS:
			return outputs != null && !outputs.isEmpty();
		case DpnPackage.ENTITY__PROPERTIES:
			return PROPERTIES_EDEFAULT == null ? properties != null
					: !PROPERTIES_EDEFAULT.equals(properties);
		case DpnPackage.ENTITY__PROCEDURES:
			return procedures != null && !procedures.isEmpty();
		case DpnPackage.ENTITY__VARIABLES:
			return variables != null && !variables.isEmpty();
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
		case DpnPackage.ENTITY__FILE_NAME:
			setFileName((String) newValue);
			return;
		case DpnPackage.ENTITY__INPUTS:
			getInputs().clear();
			getInputs().addAll((Collection<? extends Port>) newValue);
			return;
		case DpnPackage.ENTITY__LINE_NUMBER:
			setLineNumber((Integer) newValue);
			return;
		case DpnPackage.ENTITY__NAME:
			setName((String) newValue);
			return;
		case DpnPackage.ENTITY__OUTPUTS:
			getOutputs().clear();
			getOutputs().addAll((Collection<? extends Port>) newValue);
			return;
		case DpnPackage.ENTITY__PROPERTIES:
			setProperties((JsonObject) newValue);
			return;
		case DpnPackage.ENTITY__PROCEDURES:
			getProcedures().clear();
			getProcedures().addAll((Collection<? extends Procedure>) newValue);
			return;
		case DpnPackage.ENTITY__VARIABLES:
			getVariables().clear();
			getVariables().addAll((Collection<? extends Var>) newValue);
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
		return DpnPackage.Literals.ENTITY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DpnPackage.ENTITY__FILE_NAME:
			setFileName(FILE_NAME_EDEFAULT);
			return;
		case DpnPackage.ENTITY__INPUTS:
			getInputs().clear();
			return;
		case DpnPackage.ENTITY__LINE_NUMBER:
			setLineNumber(LINE_NUMBER_EDEFAULT);
			return;
		case DpnPackage.ENTITY__NAME:
			setName(NAME_EDEFAULT);
			return;
		case DpnPackage.ENTITY__OUTPUTS:
			getOutputs().clear();
			return;
		case DpnPackage.ENTITY__PROPERTIES:
			setProperties(PROPERTIES_EDEFAULT);
			return;
		case DpnPackage.ENTITY__PROCEDURES:
			getProcedures().clear();
			return;
		case DpnPackage.ENTITY__VARIABLES:
			getVariables().clear();
			return;
		}
		super.eUnset(featureID);
	}

	@Override
	public IFile getFile() {
		String fileName = getFileName();
		if (fileName == null) {
			return null;
		}

		if (ResourcesPlugin.getPlugin() == null) {
			return null;
		}

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return root.getFile(new Path(fileName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Port> getInputs() {
		if (inputs == null) {
			inputs = new EObjectContainmentEList<Port>(Port.class, this, DpnPackage.ENTITY__INPUTS);
		}
		return inputs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Port> getOutputs() {
		if (outputs == null) {
			outputs = new EObjectContainmentEList<Port>(Port.class, this,
					DpnPackage.ENTITY__OUTPUTS);
		}
		return outputs;
	}

	@Override
	public String getPackage() {
		return IrUtil.getPackage(getName());
	}

	@Override
	public Procedure getProcedure(String name) {
		for (Procedure procedure : getProcedures()) {
			if (procedure.getName().equals(name)) {
				return procedure;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Procedure> getProcedures() {
		if (procedures == null) {
			procedures = new EObjectContainmentEList<Procedure>(Procedure.class, this,
					DpnPackage.ENTITY__PROCEDURES);
		}
		return procedures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public JsonObject getProperties() {
		return properties;
	}

	@Override
	public String getSimpleName() {
		return IrUtil.getSimpleName(getName());
	}

	@Override
	public Var getVariable(String name) {
		for (Var var : getVariables()) {
			if (var.getName().equals(name)) {
				return var;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Var> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<Var>(Var.class, this,
					DpnPackage.ENTITY__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setFileName(String newFileName) {
		String oldFileName = fileName;
		fileName = newFileName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DpnPackage.ENTITY__FILE_NAME,
					oldFileName, fileName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLineNumber(int newLineNumber) {
		int oldLineNumber = lineNumber;
		lineNumber = newLineNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DpnPackage.ENTITY__LINE_NUMBER,
					oldLineNumber, lineNumber));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DpnPackage.ENTITY__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setProperties(JsonObject newProperties) {
		JsonObject oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DpnPackage.ENTITY__PROPERTIES,
					oldProperties, properties));
	}

	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();
		return "entity " + getName();
	}

}
