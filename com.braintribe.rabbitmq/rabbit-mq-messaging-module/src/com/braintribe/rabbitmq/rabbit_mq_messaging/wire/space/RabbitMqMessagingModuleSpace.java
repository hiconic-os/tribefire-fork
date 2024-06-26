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
package com.braintribe.rabbitmq.rabbit_mq_messaging.wire.space;

import java.util.List;

import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.rabbitmq.model.deployment.RabbitMqMessaging;
import com.braintribe.transport.messaging.rabbitmq.RabbitMqConnectionProvider;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.module.wire.contract.ClusterBindersContract;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

@Managed
public class RabbitMqMessagingModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;
	
	@Import
	private ClusterBindersContract clusterBinders;

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		bindings.bind(RabbitMqMessaging.T) //
			.component(clusterBinders.messaging()) //
			.expertFactory(this::rabbitMqMessaging);
	}
	
	@Managed
	private RabbitMqConnectionProvider rabbitMqMessaging(ExpertContext<RabbitMqMessaging> expertContext) {
		RabbitMqMessaging deployable = expertContext.getDeployable();

		return new com.braintribe.transport.messaging.rabbitmq.RabbitMqMessaging().createConnectionProvider(toLegacyConfig(deployable), tfPlatform.messaging().context());
	}

	private com.braintribe.model.messaging.rabbitmq.RabbitMqMessaging toLegacyConfig(RabbitMqMessaging deployable) {
		com.braintribe.model.messaging.rabbitmq.RabbitMqMessaging result = com.braintribe.model.messaging.rabbitmq.RabbitMqMessaging.T.create();
		
		result.setHost(deployable.getHost());
		result.setVirtualHost(deployable.getVirtualHost());
		result.setPort(deployable.getPort());
		result.setUri(deployable.getUri());
		result.setUsername(deployable.getUsername());
		result.setPassword(deployable.getPassword());
		result.setAddresses(deployable.getAddresses());
		result.setAutomaticRecoveryEnabled(deployable.getAutomaticRecoveryEnabled());
		result.setTopologyRecoveryEnabled(deployable.getTopologyRecoveryEnabled());
		result.setConnectionTimeout(deployable.getConnectionTimeout());
		result.setNetworkRecoveryInterval(deployable.getNetworkRecoveryInterval());
		result.setRequestedHeartbeat(deployable.getRequestedHeartbeat());
		result.setName(deployable.getName());
		
		return result;
	}

}
