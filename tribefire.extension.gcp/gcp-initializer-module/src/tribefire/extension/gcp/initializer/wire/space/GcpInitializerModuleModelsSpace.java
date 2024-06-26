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
package tribefire.extension.gcp.initializer.wire.space;

import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.gcp.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.gcp.initializer.wire.contract.GcpInitializerModuleModelsContract;

/**
 * @see GcpInitializerModuleModelsContract
 */
@Managed
public class GcpInitializerModuleModelsSpace extends AbstractInitializerSpace implements GcpInitializerModuleModelsContract {

	@Import
	private ExistingInstancesContract existingInstances;
	
	@Managed
	@Override
	public GmMetaModel configuredDataModel() {
		GmMetaModel model = create(GmMetaModel.T);
		model.setName("tribefire.extension.gcp:configured-gcp-model");
		model.getDependencies().add(existingInstances.dataModel());
		
		return model;
	}

	@Managed
	@Override
	public GmMetaModel configuredServiceModel() {
		GmMetaModel model = create(GmMetaModel.T);
		model.setName("tribefire.extension.gcp:configured-gcp-service-model");
		model.getDependencies().add(existingInstances.serviceModel());
		
		return model;
	}
	
	@Managed
	@Override
	public GmMetaModel configuredDeploymentModel() {
		GmMetaModel model = create(GmMetaModel.T);
		model.setName("tribefire.extension.gcp:configured-gcp-deployment-model");
		model.getDependencies().add(existingInstances.deploymentModel());
		
		return model;
	}
}
