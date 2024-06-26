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
import static tribefire.extension.wopi.model.WopiMetaDataConstants.WOPI_APP_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.WOPI_APP_NAME;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.extensiondeployment.WebTerminal;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Max;
import com.braintribe.model.generic.annotation.meta.Min;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Integration example of WOPI
 * 
 *
 */
public interface WopiIntegrationExample extends WebTerminal {

	final EntityType<WopiIntegrationExample> T = EntityTypes.T(WopiIntegrationExample.class);

	String access = "access";
	String healthConnectTimeoutInMs = "healthConnectTimeoutInMs";
	String healthReadTimeoutInMs = "healthReadTimeoutInMs";
	String wopiApp = "wopiApp";

	@Name(ACCESS_NAME)
	@Description(ACCESS_DESCRIPTION)
	@Mandatory
	IncrementalAccess getAccess();
	void setAccess(IncrementalAccess access);

	@Mandatory
	@Initializer("5000")
	@Min("1l")
	@Max("60000l")
	int getHealthConnectTimeoutInMs();
	void setHealthConnectTimeoutInMs(int healthConnectTimeoutInMs);

	@Mandatory
	@Initializer("5000")
	@Min("1l")
	@Max("60000l")
	int getHealthReadTimeoutInMs();
	void setHealthReadTimeoutInMs(int healthReadTimeoutInMs);

	@Name(WOPI_APP_NAME)
	@Description(WOPI_APP_DESCRIPTION)
	@Mandatory
	WopiApp getWopiApp();
	void setWopiApp(WopiApp wopiApp);

}
