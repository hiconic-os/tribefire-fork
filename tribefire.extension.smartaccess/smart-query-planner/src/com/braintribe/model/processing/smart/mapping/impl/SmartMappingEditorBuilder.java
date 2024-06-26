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

import java.util.List;
import java.util.function.Function;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.smart.mapping.api.SmartMappingEditor;

/**
 * @author peter.gazdik
 */
public interface SmartMappingEditorBuilder {

	BasicSmartMappingEditorBuilder globalIdPrefix(String globalIdPrefix);

	SmartMappingEditorBuilder smartModel(GmMetaModel smartModel);

	/**
	 * We do not have to specify all delegates. The only important thing is that the smart model and all here stated
	 * delegates cover all the possible types we are referring to in our mappings.
	 */
	SmartMappingEditorBuilder delegateModels(List<GmMetaModel> delegateModels);

	SmartMappingEditorBuilder entityFactory(Function<EntityType<?>, GenericEntity> entityFactory);

	/**
	 * Lookup for entities based on their globalId.
	 */
	SmartMappingEditorBuilder entityLookup(Function<String, GenericEntity> entityLookup);

	SmartMappingEditor build();

}
