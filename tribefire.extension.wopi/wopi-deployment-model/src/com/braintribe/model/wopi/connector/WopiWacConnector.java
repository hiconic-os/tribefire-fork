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
package com.braintribe.model.wopi.connector;

import static tribefire.extension.wopi.model.WopiMetaDataConstants.CONNECTION_REQUEST_TIMEOUT_IN_MS_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.CONNECTION_REQUEST_TIMEOUT_IN_MS_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.CONNECTION_RETRIES_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.CONNECTION_RETRIES_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.CONNECT_TIMEOUT_IN_MS_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.CONNECT_TIMEOUT_IN_MS_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.CUSTOM_PUBLIC_SERVICES_URL_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.DELAY_ON_RETRY_IN_MS_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.DELAY_ON_RETRY_IN_MS_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.SOCKET_TIMEOUT_IN_MS_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.SOCKET_TIMEOUT_IN_MS_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.CUSTOM_PUBLIC_SERVICES_URL_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.WAC_DISCOVERY_ENDPOINT_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.WAC_DISCOVERY_ENDPOINT_NAME;

import com.braintribe.model.deployment.connector.Connector;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.DeployableComponent;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Min;
import com.braintribe.model.generic.annotation.meta.MinLength;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Connector to the "Office Online Server"
 * 
 *
 */
@DeployableComponent
public interface WopiWacConnector extends Connector {

	// There was also the idea to add a BasicAuthentication configuration. This does not work because the WOPI Server
	// removes silently the BasicAuthentication information. It was tested around end 2019; maybe something changed
	// afterwards

	final EntityType<WopiWacConnector> T = EntityTypes.T(WopiWacConnector.class);

	String wacDiscoveryEndpoint = "wacDiscoveryEndpoint";
	String customPublicServicesUrl = "customPublicServicesUrl";
	String connectionRequestTimeoutInMs = "connectionRequestTimeoutInMs";
	String connectTimeoutInMs = "connectTimeoutInMs";
	String socketTimeoutInMs = "socketTimeoutInMs";
	String connectionRetries = "connectionRetries";
	String delayOnRetryInMs = "delayOnRetryInMs";

	@Name(WAC_DISCOVERY_ENDPOINT_NAME)
	@Description(WAC_DISCOVERY_ENDPOINT_DESCRIPTION)
	@Mandatory
	@MinLength(5)
	String getWacDiscoveryEndpoint();
	void setWacDiscoveryEndpoint(String wacDiscoveryEndpoint);

	@Name(CUSTOM_PUBLIC_SERVICES_URL_NAME)
	@Description(CUSTOM_PUBLIC_SERVICES_URL_DESCRIPTION)
	String getCustomPublicServicesUrl();
	void setCustomPublicServicesUrl(String customPublicServicesUrl);

	@Name(CONNECTION_REQUEST_TIMEOUT_IN_MS_NAME)
	@Description(CONNECTION_REQUEST_TIMEOUT_IN_MS_DESCRIPTION)
	@Mandatory
	@Initializer("2000") // 2s
	@Min("1")
	int getConnectionRequestTimeoutInMs();
	void setConnectionRequestTimeoutInMs(int connectionRequestTimeoutInMs);

	@Name(CONNECT_TIMEOUT_IN_MS_NAME)
	@Description(CONNECT_TIMEOUT_IN_MS_DESCRIPTION)
	@Mandatory
	@Initializer("2000") // 2s
	@Min("1")
	int getConnectTimeoutInMs();
	void setConnectTimeoutInMs(int connectTimeoutInMs);

	@Name(SOCKET_TIMEOUT_IN_MS_NAME)
	@Description(SOCKET_TIMEOUT_IN_MS_DESCRIPTION)
	@Mandatory
	@Initializer("2000") // 2s
	@Min("1")
	int getSocketTimeoutInMs();
	void setSocketTimeoutInMs(int socketTimeoutInMs);

	@Name(CONNECTION_RETRIES_NAME)
	@Description(CONNECTION_RETRIES_DESCRIPTION)
	@Mandatory
	@Initializer("3")
	@Min("1")
	int getConnectionRetries();
	void setConnectionRetries(int connectionRetries);

	@Name(DELAY_ON_RETRY_IN_MS_NAME)
	@Description(DELAY_ON_RETRY_IN_MS_DESCRIPTION)
	@Mandatory
	@Initializer("1000") // 1s
	@Min("1")
	int getDelayOnRetryInMs();
	void setDelayOnRetryInMs(int delayOnRetryInMs);

}
