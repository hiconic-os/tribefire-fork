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
package com.braintribe.devrock.zarathud.model.reasons.comparison;

import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * simply a missing type/interface/enum -> toplevel thingi
 */
@SelectiveInformation("base's ${entity} has no correspondence")
public interface MissingEntityReason extends AbstractComparisonReason {
	
	EntityType<MissingEntityReason> T = EntityTypes.T(MissingEntityReason.class);
	
	String entity = "entity";
	
	String getEntity();
	void setEntity(String value);

	

}
