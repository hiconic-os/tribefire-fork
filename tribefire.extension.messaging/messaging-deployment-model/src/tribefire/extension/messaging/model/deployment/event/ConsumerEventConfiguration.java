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

import java.util.List;

import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.annotation.meta.Priority;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import tribefire.extension.messaging.model.MessagingComponentDeploymentType;
import tribefire.extension.messaging.model.deployment.event.rule.ConsumerEventRule;

@SelectiveInformation("Consumer Event Configuration: ${id}")
public interface ConsumerEventConfiguration extends EventConfiguration {
    EntityType<ConsumerEventConfiguration> T = EntityTypes.T(ConsumerEventConfiguration.class);

    String eventRules = "eventRules";

    @Name("Event Rules")
    @Description("Rules applicable for this configuration")
    @Priority(2.8d)
    List<ConsumerEventRule> getEventRules();
    void setEventRules(List<ConsumerEventRule> eventRules);

    @Override
    default MessagingComponentDeploymentType getDeploymentType() {
        return MessagingComponentDeploymentType.CONSUMER;
    }
}
