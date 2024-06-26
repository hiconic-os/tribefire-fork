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
package tribefire.extension.cache.wire.space;

import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.cache.model.deployment.service.CacheAspect;
import tribefire.extension.cache.model.deployment.service.CacheAspectAdminServiceProcessor;
import tribefire.extension.cache.model.deployment.service.demo.CacheDemoProcessor;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

@Managed
public class CacheModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private DeployablesSpace deployables;

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		//@formatter:off
		//----------------------------
		// PROCESSOR
		//----------------------------
		
		bindings.bind(CacheDemoProcessor.T)
			.component(tfPlatform.binders().serviceProcessor())
			.expertFactory(deployables::cacheDemoProcessor);		

		bindings.bind(CacheAspectAdminServiceProcessor.T)
			.component(tfPlatform.binders().serviceProcessor())
			.expertFactory(deployables::cacheAspectLocalStatus);		


		//----------------------------
		// ASPECT
		//----------------------------
		
		bindings.bind(CacheAspect.T)
			.component(tfPlatform.binders().serviceAroundProcessor())
			.expertFactory(deployables::cacheAspect);
		
		
		//----------------------------
		// CUSTOM DEPLOYABLES
		//----------------------------
		
		//@formatter:on
	}
}
