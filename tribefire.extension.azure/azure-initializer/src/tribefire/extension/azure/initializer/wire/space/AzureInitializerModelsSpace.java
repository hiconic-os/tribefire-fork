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
package tribefire.extension.azure.initializer.wire.space;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.azure.initializer.wire.contract.AzureInitializerModelsContract;
import tribefire.extension.azure.initializer.wire.contract.ExistingInstancesContract;

@Managed
public class AzureInitializerModelsSpace extends AbstractInitializerSpace implements AzureInitializerModelsContract {

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Managed
	@Override
	public GmMetaModel configuredDataModel() {
		GmMetaModel model = create(GmMetaModel.T);
		model.setName("tribefire.extension.jdbc.support:configured-azure-model");
		model.getDependencies().add(existingInstances.dataModel());

		return model;
	}

	@Managed
	@Override
	public GmMetaModel configuredServiceModel() {
		GmMetaModel model = create(GmMetaModel.T);
		model.setName("tribefire.extension.jdbc.support:configured-azure-api-model");
		model.getDependencies().add(existingInstances.serviceModel());

		return model;
	}

	@Managed
	@Override
	public GmMetaModel configuredDeploymentModel() {
		GmMetaModel model = create(GmMetaModel.T);
		model.setName("tribefire.extension.jdbc.support:configured-azure-deployment-model");
		model.getDependencies().add(existingInstances.deploymentModel());

		return model;
	}
}
