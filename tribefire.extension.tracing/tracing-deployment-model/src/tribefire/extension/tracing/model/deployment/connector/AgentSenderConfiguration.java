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
package tribefire.extension.tracing.model.deployment.connector;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface AgentSenderConfiguration extends SenderConfiguration {

	final EntityType<AgentSenderConfiguration> T = EntityTypes.T(AgentSenderConfiguration.class);

	String host = "host";
	String port = "port";

	@Mandatory
	@Initializer("'localhost'")
	@Name("Host")
	@Description("Host name of the agent")
	String getHost();
	void setHost(String host);

	@Mandatory
	@Initializer("5775")
	@Name("Port")
	@Description("Port of the agent")
	int getPort();
	void setPort(int port);
}
