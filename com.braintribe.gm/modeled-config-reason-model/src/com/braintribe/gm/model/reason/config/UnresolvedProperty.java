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
package com.braintribe.gm.model.reason.config;

import com.braintribe.gm.model.reason.Reason;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@SelectiveInformation("unresolved property ${propertyName}")
public interface UnresolvedProperty extends Reason {

	EntityType<UnresolvedProperty> T = EntityTypes.T(UnresolvedProperty.class);

	String propertyName = "propertyName";

	String getPropertyName();
	void setPropertyName(String value);

	static UnresolvedProperty create(String propertyName) {
		UnresolvedProperty result = T.create();
		result.setText("unresolved property " + propertyName);
		result.setPropertyName(propertyName);
		return result;
	}
}
