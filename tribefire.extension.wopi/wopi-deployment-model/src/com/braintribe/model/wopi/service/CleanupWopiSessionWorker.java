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
package com.braintribe.model.wopi.service;

import static tribefire.extension.wopi.model.WopiMetaDataConstants.ACCESS_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.ACCESS_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.CLEANUP_INTERVAL_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.CLEANUP_INTERVAL_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.CONTEXT_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.CONTEXT_NAME;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.extensiondeployment.Worker;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Max;
import com.braintribe.model.generic.annotation.meta.Min;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Cleanup closed WopiSession
 * 
 *
 */
public interface CleanupWopiSessionWorker extends Worker {

	final EntityType<CleanupWopiSessionWorker> T = EntityTypes.T(CleanupWopiSessionWorker.class);

	String access = "access";
	String intervalInMs = "intervalInMs";
	String context = "context";

	@Name(ACCESS_NAME)
	@Description(ACCESS_DESCRIPTION)
	@Mandatory
	IncrementalAccess getAccess();
	void setAccess(IncrementalAccess access);

	@Name(CLEANUP_INTERVAL_NAME)
	@Description(CLEANUP_INTERVAL_DESCRIPTION)
	@Mandatory
	@Initializer("3600000l") // 1hour
	@Min("1l")
	@Max("172800000l") // 2days
	long getIntervalInMs();
	void setIntervalInMs(long intervalInMs);

	@Name(CONTEXT_NAME)
	@Description(CONTEXT_DESCRIPTION)
	@Mandatory
	String getContext();
	void setContext(String context);

}
