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

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.annotation.meta.Priority;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.messaging.model.MessagingConnectorType;

@Abstract
public interface EventEndpoint extends GenericEntity {

	EntityType<EventEndpoint> T = EntityTypes.T(EventEndpoint.class);

	String name = "name";
	String connectionUrl = "connectionUrl";

	// ***************************************************************************************************
	// INTERNAL/TECHNICAL CONFIGURATION
	// ***************************************************************************************************

	@Name("Name")
	@Description("Name of used endpoint")
	@Mandatory
	@Priority(1.0d)
	String getName();
	void setName(String name);

	@Mandatory
	@Name("Connection URL")
	@Description("URL for messaging connection")
	@Priority(3.0d)
	String getConnectionUrl();
	void setConnectionUrl(String connectionUrl);

	default MessagingConnectorType getConnectorType() {
		return null;
	}

	// -----------------------
	// Advanced Settings
	// -----------------------

	// ***************************************************************************************************
	// DYNAMIC CONFIGURATION
	// ***************************************************************************************************

}
