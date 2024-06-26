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

import static tribefire.extension.wopi.model.WopiMetaDataConstants.CORRELATIONID_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.CORRELATIONID_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.INCLUDE_CURRENT_RESOURCE_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.INCLUDE_CURRENT_RESOURCE_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.INCLUDE_POST_OPEN_RESOURCE_VERSIONS_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.INCLUDE_POST_OPEN_RESOURCE_VERSIONS_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.INCLUDE_RESOURCE_VERSIONS_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.INCLUDE_RESOURCE_VERSIONS_NAME;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;

/**
 * Get the Current Resource of a WOPI Session
 * 
 *
 */
public interface GetWopiResource extends WopiRequest {

	EntityType<GetWopiResource> T = EntityTypes.T(GetWopiResource.class);

	@Override
	EvalContext<? extends GetWopiResourceResult> eval(Evaluator<ServiceRequest> evaluator);

	String correlationId = "correlationId";

	@Name(CORRELATIONID_NAME)
	@Description(CORRELATIONID_DESCRIPTION)
	@Mandatory
	String getCorrelationId();
	void setCorrelationId(String correlationId);

	@Name(INCLUDE_CURRENT_RESOURCE_NAME)
	@Description(INCLUDE_CURRENT_RESOURCE_DESCRIPTION)
	@Mandatory
	@Initializer("true")
	boolean getIncludeCurrentResource();
	void setIncludeCurrentResource(boolean includeCurrentResource);

	@Name(INCLUDE_RESOURCE_VERSIONS_NAME)
	@Description(INCLUDE_RESOURCE_VERSIONS_DESCRIPTION)
	@Mandatory
	@Initializer("false")
	boolean getIncludeResourceVersions();
	void setIncludeResourceVersions(boolean includeResourceVersions);

	@Name(INCLUDE_POST_OPEN_RESOURCE_VERSIONS_NAME)
	@Description(INCLUDE_POST_OPEN_RESOURCE_VERSIONS_DESCRIPTION)
	@Mandatory
	@Initializer("false")
	boolean getIncludePostOpenResourceVersions();
	void setIncludePostOpenResourceVersions(boolean includePostOpenResourceVersions);
}
