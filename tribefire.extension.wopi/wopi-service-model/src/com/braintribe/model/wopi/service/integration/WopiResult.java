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
package com.braintribe.model.wopi.service.integration;

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.notification.HasNotifications;
import com.braintribe.model.service.api.result.Failure;

/**
 * Base for all WOPI service results; in case of a known error a {@link Failure} will be returned. In case of an error
 * this type will be returned
 * 
 *
 */
public interface WopiResult extends StandardIdentifiable, HasNotifications {

	EntityType<WopiResult> T = EntityTypes.T(WopiResult.class);

	String failure = "failure";

	@Name("Failure")
	@Description("Failure appearing in service execution")
	Failure getFailure();
	void setFailure(Failure failure);

}
