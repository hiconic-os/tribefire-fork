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
package tribefire.extension.messaging.initializer.wire.space;

import com.braintribe.model.extensiondeployment.check.CheckBundle;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.cortex.model.check.CheckCoverage;
import tribefire.cortex.model.check.CheckWeight;
import tribefire.extension.messaging.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.messaging.initializer.wire.contract.MessagingInitializerContract;
import tribefire.extension.messaging.model.deployment.service.HealthCheckProcessor;
import tribefire.extension.messaging.templates.wire.contract.MessagingTemplatesContract;

@Managed
public class MessagingInitializerSpace extends AbstractInitializerSpace implements MessagingInitializerContract {

	public static final String TENANT = "default"; // TODO think of naming per set context?
	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Import
	private MessagingTemplatesContract messagingTemplates;

	@Override
	public void setupDefaultConfiguration() {
		// This is meant for testing purposes!
	}

	// -----------------------------------------------------------------------
	// HEALTH
	// -----------------------------------------------------------------------

	@Override
	@Managed
	public CheckBundle functionalCheckBundle() {
		CheckBundle bean = create(CheckBundle.T);
		bean.setModule(existingInstances.module());
		bean.getChecks().add(healthCheckProcessor());
		bean.setName("MESSAGING Checks");
		bean.setWeight(CheckWeight.under1s);
		bean.setCoverage(CheckCoverage.connectivity);
		bean.setIsPlatformRelevant(false);

		return bean;
	}

	@Managed
	@Override
	public HealthCheckProcessor healthCheckProcessor() {
		HealthCheckProcessor bean = create(HealthCheckProcessor.T);
		bean.setModule(existingInstances.module());
		bean.setAutoDeploy(true);
		bean.setName("MESSAGING Health Check");
		bean.setExternalId("messaging.healthzProcessor");
		return bean;
	}
}
