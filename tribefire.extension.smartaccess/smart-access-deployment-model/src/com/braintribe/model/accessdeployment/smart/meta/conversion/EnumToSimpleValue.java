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
package com.braintribe.model.accessdeployment.smart.meta.conversion;

import java.util.Map;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.GmEnumConstant;

/**
 * @see SmartConversion
 */
public interface EnumToSimpleValue extends SmartConversion {

	EntityType<EnumToSimpleValue> T = EntityTypes.T(EnumToSimpleValue.class);

	/**
	 * explicit mappings. If no mapping for a constant is given the name of the enum constant is assumed to be the
	 * value.
	 */
	void setValueMappings(Map<GmEnumConstant, Object> valueMappings);
	Map<GmEnumConstant, Object> getValueMappings();

}
