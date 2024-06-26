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

import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.modelling.management.api.ModellingManagementRequest;
import tribefire.extension.modelling.model.api.request.ModellingRequest;
import tribefire.extension.modelling.processing.management.ModellingManagementProcessor;
import tribefire.extension.modelling.processing.modelling.ModellingProcessor;
import tribefire.module.wire.contract.PlatformResourcesContract;
import tribefire.module.wire.contract.SystemUserRelatedContract;

@Managed
public class DeployablesSpace implements WireSpace {

	@Import
	SystemUserRelatedContract systemUser;
	
	@Import
	PlatformResourcesContract resources;
	
	@Managed
	public ModellingManagementProcessor<ModellingManagementRequest, Object> modellingManagementProcessor(
			ExpertContext<tribefire.extension.modelling.model.deployment.ModellingManagementProcessor> context) {
		ModellingManagementProcessor<ModellingManagementRequest, Object> bean = new ModellingManagementProcessor<>();
		
		bean.setSessionFactory(systemUser.sessionFactory());
		
		bean.setTempDir(resources.tmp("model-management").asFile());
		
		bean.setExplorerUrl(context.getDeployable().getExplorerUrl());
		
		// APE
		bean.setRepositoryConfigurationName(context.getDeployable().getRepositoryConfigurationName());
		
		return bean;
	}
	
	@Managed
	public ModellingProcessor<ModellingRequest, Object> modellingProcessor() {
		ModellingProcessor<ModellingRequest, Object> bean = new ModellingProcessor<>();
		
		bean.setCortexSessionProvider(() -> systemUser.sessionFactory().newSession("cortex"));
		
		return bean;
	}
	
}