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

import static com.braintribe.utils.lcd.CollectionTools2.isEmpty;
import static java.util.Collections.emptyList;

import java.util.List;
import java.util.function.Function;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.model.processing.meta.editor.ModelMetaDataEditor;
import com.braintribe.model.processing.meta.oracle.BasicModelOracle;
import com.braintribe.model.processing.meta.oracle.ModelOracle;
import com.braintribe.model.processing.smart.mapping.api.SmartMappingEditor;
import com.braintribe.utils.lcd.CommonTools;

/**
 * @author peter.gazdik
 */
public class BasicSmartMappingEditorBuilder implements SmartMappingEditorBuilder {

	private String globalIdPrefix;
	private GmMetaModel smartModel;
	private List<GmMetaModel> delegateModels = emptyList();
	private Function<EntityType<?>, GenericEntity> entityFactory = EntityType::create;
	private Function<String, GenericEntity> entityLookup = s -> null;

	@Override
	public BasicSmartMappingEditorBuilder globalIdPrefix(String globalIdPrefix) {
		this.globalIdPrefix = globalIdPrefix;
		return this;
	}

	@Override
	public BasicSmartMappingEditorBuilder smartModel(GmMetaModel smartModel) {
		this.smartModel = smartModel;
		return this;
	}

	@Override
	public BasicSmartMappingEditorBuilder delegateModels(List<GmMetaModel> delegateModels) {
		this.delegateModels = delegateModels;
		return this;
	}

	@Override
	public BasicSmartMappingEditorBuilder entityFactory(Function<EntityType<?>, GenericEntity> entityFactory) {
		this.entityFactory = entityFactory;
		return this;
	}
	
	@Override
	public BasicSmartMappingEditorBuilder entityLookup(Function<String, GenericEntity> entityLookup) {
		this.entityLookup = entityLookup;
		return this;
	}

	@Override
	public SmartMappingEditor build() {
		return new BasicSmartMappingEditor(this);
	}

	// ############################################
	// ## . . . . . . Internal stuff . . . . . . ##
	// ############################################

	/* package */ String globalIdPrefix() {
		return CommonTools.getValueOrDefault(globalIdPrefix, smartModel.getGlobalId());
	}

	/**
	 * Returns a model oracle which contains all the types from smart and all delegate levels, in order to find them
	 * when configuring mapping MD.
	 */
	/* package */ ModelOracle lookupOracle() {
		return new BasicModelOracle(lookupModel());
	}

	private GmMetaModel lookupModel() {
		if (isEmpty(delegateModels))
			return smartModel;

		GmMetaModel lookupModel = GmMetaModel.T.create();
		lookupModel.setName("smart.lookup:" + smartModel.getName());

		lookupModel.getDependencies().add(smartModel);
		lookupModel.getDependencies().addAll(delegateModels);

		return lookupModel;
	}

	/* package */ ModelMetaDataEditor smartModelMdEditor() {
		return BasicModelMetaDataEditor.create(smartModel).withEtityFactory(entityFactory).done();
	}

	/* package */ Function<EntityType<?>, GenericEntity> entityFactory() {
		return entityFactory;
	}

	/* package */ Function<String, GenericEntity> entityLookup() {
		return entityLookup;
	}
	
}
