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
package tribefire.extension.wopi.wire.space;

import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.model.wopi.connector.WopiWacConnector;
import com.braintribe.model.wopi.service.CleanupWopiSessionWorker;
import com.braintribe.model.wopi.service.ExpireWopiSessionWorker;
import com.braintribe.model.wopi.service.WacHealthCheckProcessor;
import com.braintribe.model.wopi.service.WopiApp;
import com.braintribe.model.wopi.service.WopiIntegrationExample;
import com.braintribe.model.wopi.service.WopiServiceProcessor;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

/**
 * This module's javadoc is yet to be written.
 */
@Managed
public class WopiModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private DeployablesSpace deployables;

	//
	// Deployables
	//

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		//@formatter:off
		bindings.bind(WopiServiceProcessor.T)
			.component(tfPlatform.binders().accessRequestProcessor())
			.expertFactory(deployables::wopiServiceProcessor);
		
		bindings.bind(CleanupWopiSessionWorker.T)
			.component(tfPlatform.binders().worker())
			.expertFactory(deployables::cleanupWopiSessionWorker);
		
		bindings.bind(ExpireWopiSessionWorker.T)
			.component(tfPlatform.binders().worker())
			.expertFactory(deployables::expireWopiSessionWorker);		
		
		bindings.bind(WopiApp.T)
			.component(tfPlatform.binders().webTerminal())
			.expertFactory(deployables::wopiApp);
		
		bindings.bind(WopiIntegrationExample.T)
			.component(tfPlatform.binders().webTerminal())
			.expertFactory(deployables::wopiIntegrationExample);		
		
		bindings.bind(WopiWacConnector.T)
			.component(WopiWacConnector.T, com.braintribe.model.processing.wopi.WopiWacConnector.class)
			.expertFactory(deployables::wopiWacConnector);	
		
		bindings.bind(WacHealthCheckProcessor.T)
			.component(tfPlatform.binders().checkProcessor())
			.expertFactory(deployables::wacHealthCheckProcessor);		
		//@formatter:on
	}

}
