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

import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.annotation.meta.Priority;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.GmEntityType;

@SelectiveInformation("Consumer Event Rule: ${name} - ${endpoints}")
public interface ConsumerEventRule extends EventRule {
	EntityType<ConsumerEventRule> T = EntityTypes.T(ConsumerEventRule.class);

	String postProcessorType = "postProcessorType";

	@Name("Post Processor Type")
	@Description("PostProcessor to be used for message consumption")
	@Priority(1.9d)
	@Mandatory
	GmEntityType getPostProcessorType();
	void setPostProcessorType(GmEntityType postProcessorType);

	@Name("Post Processing Request Type")
	@Description("Request type to be used to trigger post processor")
	@Priority(1.8d)
	@Mandatory
	GmEntityType getPostProcessorRequestType();
	void  setPostProcessorRequestType(GmEntityType postProcessorRequestType);
}
