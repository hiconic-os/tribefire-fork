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
package tribefire.extension.messaging.templates.wire.contract;

import com.braintribe.model.extensiondeployment.ServiceProcessor;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.messaging.model.deployment.service.MessagingAspect;
import tribefire.extension.messaging.model.deployment.service.MessagingProcessor;
import tribefire.extension.messaging.model.deployment.service.MessagingWorker;
import tribefire.extension.messaging.templates.api.MessagingTemplateContext;

public interface MessagingTemplatesContract extends WireSpace {

	/**
	 * Setup MESSAGING with a specified {@link MessagingTemplateContext}
	 */
	void setupMessaging(MessagingTemplateContext context);

	/**
	 * Set up a full Consumer/Producer set of services
	 * @param context contextTemplate
	 */

	void setupProducerSet(MessagingTemplateContext context);

	void setupConsumerSet(MessagingTemplateContext context);

	/* Consumer Related */

	MessagingWorker messagingWorker(MessagingTemplateContext context);

	<T extends ServiceProcessor> T postProcessor(MessagingTemplateContext context);

	/* Producer Related */

	MessagingAspect messagingAspect(MessagingTemplateContext context);

	MessagingProcessor sendMessageProcessor(MessagingTemplateContext context);

}
