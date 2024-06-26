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
package tribefire.extension.messaging.model.deployment.event.rule;

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.annotation.meta.Priority;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import tribefire.extension.messaging.model.deployment.event.EventEndpointConfiguration;

@Abstract
@SelectiveInformation("Event Rule: ${name} - ${ruleEnabled}")
public interface EventRule extends GenericEntity {

	EntityType<EventRule> T = EntityTypes.T(EventRule.class);

	// ***************************************************************************************************
	// INTERNAL/TECHNICAL CONFIGURATION
	// ***************************************************************************************************
	String name = "name";
	String ruleEnabled = "ruleEnabled";

	@Name("Rule Enabled")
	@Description("Enables/disables this rule for processing")
	@Priority(2.8d)
	@Mandatory
	@Initializer("false")
	boolean getRuleEnabled();
	void setRuleEnabled(boolean ruleEnabled);

	@Name("Name")
	@Description("The name of the rule.")
	@Priority(2.9d)
	@Mandatory
	String getName();
	void setName(String name);

	// ***************************************************************************************************
	// DYNAMIC CONFIGURATION
	// ***************************************************************************************************
	String endpointConfiguration = "endpointConfiguration";

	@Name("Endpoint delivery configuration")
	@Description("The configuration for message delivery (multiple topics per endpoint)")
	@Priority(1.8d)
	List<EventEndpointConfiguration> getEndpointConfiguration();
	void setEndpointConfiguration(List<EventEndpointConfiguration> endpointConfiguration);
}
