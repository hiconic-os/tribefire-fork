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
package com.braintribe.model.processing.smart.mapping.api;

import java.util.function.Consumer;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.meta.editor.EntityTypeMetaDataEditor;

/**
 * @author peter.gazdik
 */
public interface SmartEntityTypeMappingEditor {

	SmartEntityTypeMappingEditor forDelegate(String accessId);

	SmartEntityTypeMappingEditor withGlobalIdPart(String globalIdPart);

	SmartEntityTypeMappingEditor addMetaData(Consumer<EntityTypeMetaDataEditor> mdConfigurer);

	/** Maps this entity and all it's properties AsIs */
	SmartEntityTypeMappingEditor allAsIs();

	SmartEntityTypeMappingEditor entityAsIs();

	SmartEntityTypeMappingEditor propertiesAsIs();

	SmartEntityTypeMappingEditor allUnmapped();

	SmartEntityTypeMappingEditor entityUnmapped();

	SmartEntityTypeMappingEditor propertiesUnmapped();

	SmartEntityTypeMappingEditor entityTo(EntityType<?> delegateType);

	SmartEntityTypeMappingEditor entityTo(String typeSignature);

	SmartEntityTypeMappingEditor propertyTo(String smartProperty, String delegateProperty);

	SmartEntityTypeMappingEditor propertyUnmapped(String smartProperty);

	SmartEntityTypeMappingEditor propertiesUnmapped(String... smartProperties);

}
