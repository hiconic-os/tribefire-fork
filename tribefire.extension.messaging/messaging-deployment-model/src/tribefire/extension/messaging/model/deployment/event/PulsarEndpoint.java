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
package tribefire.extension.messaging.model.deployment.event;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.annotation.meta.Priority;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.messaging.model.MessagingConnectorType;

@SelectiveInformation("Pulsar Endpoint: ${name} - ${connectionUrl}")
public interface PulsarEndpoint extends EventEndpoint {

	EntityType<PulsarEndpoint> T = EntityTypes.T(PulsarEndpoint.class);

	// ***************************************************************************************************
	// STATIC CONFIGURATION
	// ***************************************************************************************************
	String adminUrl = "adminUrl";

	@Override
	@Mandatory
	@Initializer("'pulsar://localhost:6650'")
	@Priority(3.0d)
	String getConnectionUrl();

	@Mandatory
	@Name("Admin URL")
	@Description("URL for Admin connection for HealthCheck support")
	@Initializer("'http://localhost:8081'")
	@Priority(4.0d)
	String getAdminUrl();
	void setAdminUrl(String adminUrl);

	// -----------------------
	// Advanced Settings
	// -----------------------

	@Override
	default MessagingConnectorType getConnectorType() {
		return MessagingConnectorType.PULSAR;
	}
}
