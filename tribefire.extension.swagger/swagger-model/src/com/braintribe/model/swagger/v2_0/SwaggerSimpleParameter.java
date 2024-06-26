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

import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * This is the parameter
 * 
 * See https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#parameterObject
 */
public interface SwaggerSimpleParameter extends SwaggerParameter, WithFormat, WithType {

	final EntityType<SwaggerSimpleParameter> T = EntityTypes.T(SwaggerSimpleParameter.class);

	/**
	 * Possible values: "string", "number", "integer", "boolean", "array" or "file"
	 */
	@Override
	@Mandatory
	String getType();

	boolean getAllowEmptyValue();
	void setAllowEmptyValue(boolean allowEmptyValue);

	String getCollectionFormat();
	void setCollectionFormat(String collectionFormat);

	SwaggerItems getItems();
	void setItems(SwaggerItems items);

	String getDefault();
	void setDefault(String defaultValue);
}
