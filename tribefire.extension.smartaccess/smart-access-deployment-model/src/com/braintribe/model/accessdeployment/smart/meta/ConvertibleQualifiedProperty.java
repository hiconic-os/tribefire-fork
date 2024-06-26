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
package com.braintribe.model.accessdeployment.smart.meta;

import com.braintribe.model.accessdeployment.smart.meta.conversion.SmartConversion;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.data.QualifiedProperty;

public interface ConvertibleQualifiedProperty extends QualifiedProperty {

	EntityType<ConvertibleQualifiedProperty> T = EntityTypes.T(ConvertibleQualifiedProperty.class);

	/**
	 * Sets a conversion to be applied on given property. (optional)
	 * 
	 * @see SmartConversion
	 */
	SmartConversion getConversion();
	void setConversion(SmartConversion conversion);

}
