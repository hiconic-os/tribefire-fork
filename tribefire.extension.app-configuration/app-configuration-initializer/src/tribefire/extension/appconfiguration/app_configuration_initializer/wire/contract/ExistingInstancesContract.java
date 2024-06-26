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
package tribefire.extension.appconfiguration.app_configuration_initializer.wire.contract;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.selector.UseCaseSelector;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.cortex.initializer.support.impl.lookup.GlobalId;
import tribefire.cortex.initializer.support.impl.lookup.InstanceLookup;
import tribefire.extension.appconfiguration.app_configuration_initializer.AppConfigurationConstants;
import tribefire.extension.js.model.deployment.UxModule;

@InstanceLookup(lookupOnly = true)
public interface ExistingInstancesContract extends WireSpace {

	@GlobalId(AppConfigurationConstants.APP_CONFIGURATION_DEPLOYMENT_MODEL_GLOBAL_ID)
	GmMetaModel appConfigurationDeploymentModel();

	@GlobalId(AppConfigurationConstants.APP_CONFIGURATION_API_MODEL_GLOBAL_ID)
	GmMetaModel appConfigurationApiModel();

	@GlobalId(AppConfigurationConstants.APP_CONFIGURATION_MODEL_GLOBAL_ID)
	GmMetaModel appConfigurationModel();

	@GlobalId(AppConfigurationConstants.WORKBENCH_ACCESS_GLOBAL_ID)
	IncrementalAccess workbenchAccess();

	@GlobalId(AppConfigurationConstants.APP_CONFIGURATION_UX_MODULE)
	UxModule uxModule();

	@GlobalId("selector:useCase/gme.gmeGlobalUseCase")
	UseCaseSelector gmeSelector();

	@GlobalId("selector:useCase/swagger")
	UseCaseSelector swaggerSelector();

	@GlobalId("useCase:openapi")
	UseCaseSelector openApiSelector();

}
