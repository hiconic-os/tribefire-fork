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
package tribefire.extension.appconfiguration.app_configuration_initializer.wire.space;

import java.util.List;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.appconfiguration.app_configuration_initializer.wire.contract.AppConfigurationInitializerModelsContract;
import tribefire.extension.appconfiguration.app_configuration_initializer.wire.contract.ExistingInstancesContract;

@Managed
public class AppConfigurationInitializerModelsSpace extends AbstractInitializerSpace implements AppConfigurationInitializerModelsContract {

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Override
	@Managed
	public GmMetaModel appConfigurationWorkbenchModel() {
		GmMetaModel bean = create(GmMetaModel.T);
		bean.setName("app-configuration-access-workbench-model");
		bean.setVersion("1.0");

		List<GmMetaModel> dependencies = bean.getDependencies();
		dependencies.add(coreInstances.workbenchModel());
		dependencies.add(existingInstances.appConfigurationModel());
		dependencies.add(existingInstances.appConfigurationApiModel());
		dependencies.add(coreInstances.essentialMetaDataModel());

		return bean;
	}

}
