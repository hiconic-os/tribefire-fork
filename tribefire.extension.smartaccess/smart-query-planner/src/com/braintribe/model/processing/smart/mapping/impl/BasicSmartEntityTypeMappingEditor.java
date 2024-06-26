// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
package com.braintribe.model.processing.smart.mapping.impl;

import java.util.function.Consumer;

import com.braintribe.model.accessdeployment.smart.meta.AsIs;
import com.braintribe.model.accessdeployment.smart.meta.QualifiedEntityAssignment;
import com.braintribe.model.accessdeployment.smart.meta.QualifiedPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.SmartUnmapped;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.meta.editor.EntityTypeMetaDataEditor;
import com.braintribe.model.processing.meta.oracle.EntityTypeOracle;
import com.braintribe.model.processing.smart.mapping.api.SmartEntityTypeMappingEditor;

/**
 * @author peter.gazdik
 */
public class BasicSmartEntityTypeMappingEditor implements SmartEntityTypeMappingEditor {

	private static final String QEA = "qea";
	private static final String QPA = "qpa";

	private final BasicSmartMappingEditor mappingEditor;
	private final EntityTypeMetaDataEditor entityMdEditor;

	private String currentAccessId;
	private String currentGlobalIdPart;

	private EntityTypeOracle currentDelegateTypeOracle;

	public BasicSmartEntityTypeMappingEditor(BasicSmartMappingEditor mappingEditor, EntityTypeMetaDataEditor entityMdEditor) {
		this.mappingEditor = mappingEditor;
		this.entityMdEditor = entityMdEditor;
	}

	@Override
	public SmartEntityTypeMappingEditor forDelegate(String accessId) {
		this.currentAccessId = accessId;
		return this;
	}

	@Override
	public SmartEntityTypeMappingEditor withGlobalIdPart(String globalIdPart) {
		currentGlobalIdPart = globalIdPart;
		return this;
	}

	@Override
	public SmartEntityTypeMappingEditor addMetaData(Consumer<EntityTypeMetaDataEditor> mdConfigurer) {
		mdConfigurer.accept(entityMdEditor);
		return this;
	}

	@Override
	public SmartEntityTypeMappingEditor allAsIs() {
		return entityAsIs().propertiesAsIs();
	}

	@Override
	public SmartEntityTypeMappingEditor entityAsIs() {
		currentDelegateTypeOracle = findEntityTypeOracle(entityMdEditor.getEntityType().getTypeSignature());

		AsIs asIs = mappingEditor.acquireAsIs(currentAccessId);
		entityMdEditor.addMetaData(asIs);
		return this;
	}

	@Override
	public SmartEntityTypeMappingEditor propertiesAsIs() {
		AsIs asIs = mappingEditor.acquireAsIs(currentAccessId);
		entityMdEditor.addPropertyMetaData(asIs);
		return this;
	}

	@Override
	public SmartEntityTypeMappingEditor allUnmapped() {
		return entityUnmapped().propertiesUnmapped();
	}

	@Override
	public SmartEntityTypeMappingEditor entityUnmapped() {
		SmartUnmapped unmapped = mappingEditor.acquireUnmapped(currentAccessId);
		entityMdEditor.addMetaData(unmapped);
		return this;
	}

	@Override
	public SmartEntityTypeMappingEditor propertiesUnmapped() {
		SmartUnmapped unmapped = mappingEditor.acquireUnmapped(currentAccessId);
		entityMdEditor.addPropertyMetaData(unmapped);
		return this;
	}

	@Override
	public SmartEntityTypeMappingEditor entityTo(EntityType<?> delegateType) {
		return entityTo(delegateType.getTypeSignature());
	}

	@Override
	public SmartEntityTypeMappingEditor entityTo(String typeSignature) {
		currentDelegateTypeOracle = findEntityTypeOracle(typeSignature);

		QualifiedEntityAssignment qea = mappingEditor.newMapping(QualifiedEntityAssignment.T, globalId(QEA), currentAccessId);
		qea.setEntityType(currentDelegateTypeOracle.asGmEntityType());

		entityMdEditor.addMetaData(qea);

		return this;
	}

	@Override
	public SmartEntityTypeMappingEditor propertyTo(String smartProperty, String delegateProperty) {
		QualifiedPropertyAssignment qpa = mappingEditor.newMapping(QualifiedPropertyAssignment.T, globalId(QPA), currentAccessId);

		qpa.setEntityType(currentDelegateTypeOracle.asGmEntityType());
		qpa.setProperty(currentDelegateTypeOracle.getProperty(delegateProperty).asGmProperty());

		entityMdEditor.addPropertyMetaData(smartProperty, qpa);

		return this;
	}

	@Override
	public SmartEntityTypeMappingEditor propertyUnmapped(String smartProperty) {
		SmartUnmapped unmapped = mappingEditor.acquireUnmapped(currentAccessId);
		entityMdEditor.addPropertyMetaData(smartProperty, unmapped);

		return this;
	}

	@Override
	public SmartEntityTypeMappingEditor propertiesUnmapped(String... smartProperties) {
		SmartUnmapped unmapped = mappingEditor.acquireUnmapped(currentAccessId);
		for (String smartProperty : smartProperties)
			entityMdEditor.addPropertyMetaData(smartProperty, unmapped);

		return this;
	}

	private String globalId(String component) {
		if (currentAccessId == null)
			return mappingEditor.globalId(currentGlobalIdPart, component, "global");
		else
			return mappingEditor.globalId(currentAccessId, currentGlobalIdPart, component);
	}

	private EntityTypeOracle findEntityTypeOracle(String typeSignature) {
		return mappingEditor.lookupOracle.findEntityTypeOracle(typeSignature);
	}

}
