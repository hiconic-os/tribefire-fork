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
package com.braintribe.model.openapi.v3_0;

import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Common base for {@link OpenapiParameter} and {@link OpenapiHeader}
 */
@Abstract
public interface OpenapiParameterBase extends JsonReferencable {

	EntityType<OpenapiParameterBase> T = EntityTypes.T(OpenapiParameterBase.class);

	String getDescription();
	void setDescription(String description);

	boolean getRequired();
	void setRequired(boolean required);

	boolean getDeprecated();
	void setDeprecated(boolean deprecated);

	OpenapiSchema getSchema();
	void setSchema(OpenapiSchema schema);

	//
	// boolean getAllowEmptyValue();
	// void setAllowEmptyValue(boolean allowEmptyValue);
}
