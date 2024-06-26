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
package com.braintribe.model.accessory;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Resolves the data model of an access with given {@link #getExternalId() externalId}.
 * 
 * @author peter.gazdik
 */
public interface GetAccessModel extends GetComponentModel {

	EntityType<GetAccessModel> T = EntityTypes.T(GetAccessModel.class);

	static GetAccessModel create(String externalId, String perspective, boolean extended) {
		GetAccessModel result = GetAccessModel.T.create();
		result.setExternalId(externalId);
		result.setPerspective(perspective);
		result.setExtended(extended);

		return result;
	}

}
