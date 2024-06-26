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
package tribefire.extension.modelling.wire.space;

import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.modelling.model.deployment.ModellingManagementProcessor;
import tribefire.extension.modelling.model.deployment.ModellingProcessor;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

@Managed
public class ModellingModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;
	
	@Import
	private DeployablesSpace deployables;

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {

		bindings.bind(ModellingManagementProcessor.T)
		.component(tfPlatform.binders().accessRequestProcessor())
		.expertFactory(deployables::modellingManagementProcessor);
		
		bindings.bind(ModellingProcessor.T)
		.component(tfPlatform.binders().accessRequestProcessor())
		.expertSupplier(deployables::modellingProcessor);
	}

}
