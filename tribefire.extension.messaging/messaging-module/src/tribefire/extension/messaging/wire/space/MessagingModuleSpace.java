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
package tribefire.extension.messaging.wire.space;

import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.messaging.model.deployment.service.HealthCheckProcessor;
import tribefire.extension.messaging.model.deployment.service.MessagingAspect;
import tribefire.extension.messaging.model.deployment.service.MessagingProcessor;
import tribefire.extension.messaging.model.deployment.service.MessagingWorker;
import tribefire.extension.messaging.model.deployment.service.test.TestGetObjectProcessor;
import tribefire.extension.messaging.model.deployment.service.test.TestReceiveMessagingProcessor;
import tribefire.extension.messaging.model.deployment.service.test.TestUpdateObjectProcessor;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;
import tribefire.module.wire.contract.WebPlatformBindersContract;

@Managed
public class MessagingModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private WebPlatformBindersContract commonComponents;

	@Import
	private DeployablesSpace deployables;

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		//@formatter:off
		//----------------------------
		// SERVICE
		//----------------------------
		bindings.bind(MessagingProcessor.T)
			.component(tfPlatform.binders().serviceProcessor())
			.expertFactory(deployables::messagingProcessor);
		//TODO HERE SHOULD BIND A POST-PROCESSOR FROM CONFIG ??? (NO Task is allocated as the question targets message consumption which is not in priority as of 22.08.2022)
		//----------------------------
		// ASPECT
		//----------------------------
		bindings.bind(MessagingAspect.T)
			.component(tfPlatform.binders().serviceProcessor())
			.expertFactory(deployables::messagingAspect)
			.component(tfPlatform.binders().serviceAroundProcessor())
			.expertFactory(deployables::messagingAspect);

		//----------------------------
		// WORKER
		//----------------------------

		bindings.bind(MessagingWorker.T)
			.component(commonComponents.worker())
			.expertFactory(deployables::messagingWorker);

		//----------------------------
		// TEST
		//----------------------------
		bindings.bind(TestReceiveMessagingProcessor.T)
			.component(tfPlatform.binders().serviceProcessor())
			.expertFactory(deployables::testReceiveMessagingProcessor);

		bindings.bind(TestUpdateObjectProcessor.T)
				.component(tfPlatform.binders().serviceProcessor())
				.expertFactory(deployables::testUpdateObjectProcessor);

		bindings.bind(TestGetObjectProcessor.T)
				.component(tfPlatform.binders().serviceProcessor())
				.expertFactory(deployables::testGetObjectProcessor);

		//----------------------------
		// HEALTH
		//----------------------------
		bindings.bind(HealthCheckProcessor.T)
			.component(tfPlatform.binders().checkProcessor())
			.expertFactory(deployables::healthCheckProcessor);
		//@formatter:on
	}

}
