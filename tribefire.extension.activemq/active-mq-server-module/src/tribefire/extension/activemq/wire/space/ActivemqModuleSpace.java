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
package tribefire.extension.activemq.wire.space;

import com.braintribe.model.activemqdeployment.ActiveMqWorker;
import com.braintribe.model.activemqdeployment.HealthCheckProcessor;
import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.context.WireContextConfiguration;

import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;
import tribefire.module.wire.contract.WebPlatformBindersContract;

/**
 * This module's javadoc is yet to be written.
 */
@Managed
public class ActivemqModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;
	
	@Import
	private WebPlatformBindersContract commonComponents; 

	@Import
	private ActivemqDeployablesSpace deployables;
	
	@Override
	public void onLoaded(WireContextConfiguration configuration) {
		// TODO Auto-generated method stub
		TribefireModuleContract.super.onLoaded(configuration);
	}
	
	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		// Bind deployment experts for deployable denotation types.
		// Note that the basic component binders (for e.g. serviceProcessor or incrementalAccess) can be found via tfPlatform.deployment().binders().
		
		bindings.bind(ActiveMqWorker.T)
			.component(commonComponents.worker())
			.expertFactory(deployables::worker);

		bindings.bind(HealthCheckProcessor.T)
			.component(commonComponents.checkProcessor())
			.expertFactory(deployables::healthCheckProcessor);
		
	}

}
