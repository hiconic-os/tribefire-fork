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
package tribefire.extension.messaging.model.deployment.service;

import com.braintribe.model.extensiondeployment.BinaryPersistence;
import com.braintribe.model.extensiondeployment.ServiceProcessor;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Messaging Processor - holds messaging implementation
 *
 *
 */
public interface MessagingProcessor extends ServiceProcessor {

	String context = "context";
	String resourceTarget = "resourceTarget";

	@Name("Context")
	@Description("Context information")
	String getContext();
	void setContext(String context);

	BinaryPersistence getResourceTarget();
	void setResourceTarget(BinaryPersistence resourceTarget);

	EntityType<MessagingProcessor> T = EntityTypes.T(MessagingProcessor.class);

}
