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

import static tribefire.extension.wopi.model.WopiMetaDataConstants.CURRENT_RESOURCE_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.CURRENT_RESOURCE_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.POST_OPEN_RESOURCE_VERSIONS_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.POST_OPEN_RESOURCE_VERSIONS_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.RESOURCE_VERSIONS_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.RESOURCE_VERSIONS_NAME;

import java.util.List;

import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;

/**
 * 
 * 
 *
 */
public interface GetWopiResourceResult extends WopiResult {

	EntityType<GetWopiResourceResult> T = EntityTypes.T(GetWopiResourceResult.class);

	String currentResource = "currentResource";
	String resourceVersions = "resourceVersions";
	String postOpenResourceVersions = "postOpenResourceVersions";

	@Name(CURRENT_RESOURCE_NAME)
	@Description(CURRENT_RESOURCE_DESCRIPTION)
	Resource getCurrentResource();
	void setCurrentResource(Resource currentResource);

	@Name(RESOURCE_VERSIONS_NAME)
	@Description(RESOURCE_VERSIONS_DESCRIPTION)
	List<Resource> getResourceVersions();
	void setResourceVersions(List<Resource> resourceVersions);

	@Name(POST_OPEN_RESOURCE_VERSIONS_NAME)
	@Description(POST_OPEN_RESOURCE_VERSIONS_DESCRIPTION)
	List<Resource> getPostOpenResourceVersions();
	void setPostOpenResourceVersions(List<Resource> postOpenResourceVersions);

}
