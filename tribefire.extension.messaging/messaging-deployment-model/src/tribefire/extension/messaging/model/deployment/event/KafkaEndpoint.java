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
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Priority;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.messaging.model.MessagingConnectorType;

@SelectiveInformation("Kafka Endpoint: ${name} - ${connectionUrl}")
public interface KafkaEndpoint extends EventEndpoint {

	EntityType<KafkaEndpoint> T = EntityTypes.T(KafkaEndpoint.class);

	// ***************************************************************************************************
	// STATIC CONFIGURATION
	// ***************************************************************************************************

	@Override
	@Mandatory
	@Initializer("'localhost:29092'")
	@Priority(3.0d)
	String getConnectionUrl();

	// -----------------------
	// Advanced Settings
	// -----------------------

	@Override
	default MessagingConnectorType getConnectorType() {
		return MessagingConnectorType.KAFKA;
	}
}
