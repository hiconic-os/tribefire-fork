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

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * See https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#dataTypeFormat
 */
public interface WithFormat extends GenericEntity {

	final EntityType<WithFormat> T = EntityTypes.T(WithFormat.class);

	/**
	 * See https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#dataTypeFormat for details.
	 * 
	 * This should really be an enum, but we want it to be serialized as String so we have no choice.
	 * 
	 * Possible values: "int32", "int64", "float", "double", "byte", "binary", "date", "date-time" or "password"
	 */
	String getFormat();
	void setFormat(String format);

}
