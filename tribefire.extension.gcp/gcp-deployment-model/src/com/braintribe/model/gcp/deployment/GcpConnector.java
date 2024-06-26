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
package com.braintribe.model.gcp.deployment;

import com.braintribe.model.deployment.connector.Connector;
import com.braintribe.model.descriptive.HasName;
import com.braintribe.model.generic.annotation.meta.MaxLength;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface GcpConnector extends Connector, HasName {

	final EntityType<GcpConnector> T = EntityTypes.T(GcpConnector.class);

	final static String jsonCredentials = "jsonCredentials";
	
	final static String privateKeyId = "privateKeyId";
	final static String privateKey = "privateKey";
	final static String clientId = "clientId";
	final static String clientEmail = "clientEmail";
	final static String tokenServerUri = "tokenServerUri";
	final static String projectId = "projectId";

	@MaxLength(4096)
	String getJsonCredentials();
	void setJsonCredentials(String jsonCredentials);

	@MaxLength(2048)
	String getPrivateKeyId();
	void setPrivateKeyId(String privateKeyId);

	@MaxLength(2048)
	String getPrivateKey();
	void setPrivateKey(String privateKey);

	String getClientId();
	void setClientId(String clientId);

	String getClientEmail();
	void setClientEmail(String clientEmail);

	String getTokenServerUri();
	void setTokenServerUri(String tokenServerUri);

	String getProjectId();
	void setProjectId(String projectId);

}
