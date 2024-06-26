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
package com.braintribe.model.processing.smart.query.planner.structure;

import com.braintribe.model.accessdeployment.smart.SmartAccess;
import com.braintribe.model.accessdeployment.smart.meta.EntityAssignment;
import com.braintribe.model.accessdeployment.smart.meta.IdentityEntityAssignment;
import com.braintribe.model.accessdeployment.smart.meta.PropertyAsIs;
import com.braintribe.model.accessdeployment.smart.meta.PropertyAssignment;

/**
 * 
 */
class DefaultMappings {

	private static final PropertyAsIs defaultPropertyAssignment = PropertyAsIs.T.create();

	public static EntityAssignment entity(SmartAccess smartDenotation) {
		if (smartDenotation == null)
			return null;

		EntityAssignment result = IdentityEntityAssignment.T.createPlain();
		result.setAccess(smartDenotation);

		return result;
	}

	public static PropertyAssignment property() {
		return defaultPropertyAssignment;
	}

}
