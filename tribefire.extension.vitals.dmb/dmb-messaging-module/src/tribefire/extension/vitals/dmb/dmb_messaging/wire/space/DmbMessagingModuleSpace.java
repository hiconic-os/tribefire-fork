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
package tribefire.extension.vitals.dmb.dmb_messaging.wire.space;

import com.braintribe.model.cortex.deployment.CortexConfiguration;
import com.braintribe.model.deployment.Module;
import com.braintribe.model.messaging.dmb.GmDmbMqMessaging;
import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.transport.messaging.dbm.GmDmbMqConnectionProvider;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.dmb.messaging.model.deployment.DmbMessaging;
import tribefire.module.api.InitializerBindingBuilder;
import tribefire.module.wire.contract.ClusterBindersContract;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

/**
 * This module's javadoc is yet be written.
 */
@Managed
public class DmbMessagingModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private ClusterBindersContract clusterBinders;

	private static String messagingId = "wire://tribefire.extension.basic:dmb-messaging-module/DmbMessagingModuleSpace/dmbMessaging";

	@Override
	public void bindInitializers(InitializerBindingBuilder bindings) {
		bindings.bind("cortex", this::createDmbMessaging);
	}

	private void createDmbMessaging(PersistenceInitializationContext ctx) {
		ManagedGmSession session = ctx.getSession();

		// TODO create dmb-platform-messaging-initializer 
		Module module = session.getEntityByGlobalId("module://tribefire.extension.vitals.dmb:dmb-messaging-module");

		DmbMessaging dmbMessaging = session.create(DmbMessaging.T, messagingId);
		dmbMessaging.setExternalId("messaging.dmb");
		dmbMessaging.setModule(module);

		CortexConfiguration cortexConfig = session.findEntityByGlobalId(CortexConfiguration.CORTEX_CONFIGURATION_GLOBAL_ID);
		cortexConfig.setMessaging(dmbMessaging);
	}

	//
	// Deployables
	//

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		bindings.bind(DmbMessaging.T) //
				.component(clusterBinders.messaging()) //
				.expertFactory(this::messagingConnectionProvider);
	}

	private GmDmbMqConnectionProvider messagingConnectionProvider(ExpertContext<DmbMessaging> expertContext) {
		DmbMessaging deployable = expertContext.getDeployable();

		GmDmbMqConnectionProvider bean = new GmDmbMqConnectionProvider();
		bean.setConnectionConfiguration(toLegacyConfig(deployable));
		bean.setMessagingContext(tfPlatform.messaging().context());

		return bean;
	}

	private static GmDmbMqMessaging toLegacyConfig(DmbMessaging deployable) {
		GmDmbMqMessaging result = GmDmbMqMessaging.T.create();
		result.setBrokerHost(deployable.getBrokerHost());
		result.setConnectorPort(deployable.getConnectorPort());
		result.setJmxServiceUrl(deployable.getJmxServiceUrl());
		result.setUsername(deployable.getUsername());
		result.setPassword(deployable.getPassword());

		return result;
	}

}
