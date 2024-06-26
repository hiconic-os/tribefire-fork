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
package com.braintribe.model.swagger.v2_0;

import java.util.List;
import java.util.Map;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * See https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#schemaObject See http://json-schema.org/
 */
public interface SwaggerSchema extends GenericEntity, WithFormat, WithType, WithRef {

	final EntityType<SwaggerSchema> T = EntityTypes.T(SwaggerSchema.class);

	String getTitle();
	void setTitle(String title);

	String getDescription();
	void setDescription(String description);

	/**
	 * For "object" type, the list of required properties.
	 */
	List<String> getRequired();
	void setRequired(List<String> required);

	/**
	 * For "object" type, the map of properties names -> types.
	 */
	Map<String, SwaggerSchema> getProperties();
	void setProperties(Map<String, SwaggerSchema> properties);

	/**
	 * For "array" type, the type if the element in the list/array.
	 */
	SwaggerSchema getItems();
	void setItems(SwaggerSchema items);

	/**
	 * For "enum" type, the list of possible values.
	 */
	List<String> getEnum();
	void setEnum(List<String> enumValues);

	SwaggerSchema getAdditionalProperties();
	void setAdditionalProperties(SwaggerSchema additionalProperties);
}
