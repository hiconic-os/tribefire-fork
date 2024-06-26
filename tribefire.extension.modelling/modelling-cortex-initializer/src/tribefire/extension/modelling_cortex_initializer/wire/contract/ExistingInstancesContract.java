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
package tribefire.extension.modelling_cortex_initializer.wire.contract;

import com.braintribe.model.deployment.Module;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.cortex.initializer.support.impl.lookup.GlobalId;
import tribefire.cortex.initializer.support.impl.lookup.InstanceLookup;
import tribefire.extension.js.model.deployment.UxModule;

@InstanceLookup(lookupOnly=true)
public interface ExistingInstancesContract extends WireSpace {

	String GROUP_ID = "tribefire.extension.modelling";
	String GLOBAL_ID_PREFIX = "model:" + GROUP_ID + ":";
	String GROUP_MODULE_PREFIX = "module://" + GROUP_ID; 
	String GROUP_UX_MODULE_PREFIX = "js-ux-module://" + GROUP_ID; 
	
	@GlobalId(GROUP_MODULE_PREFIX + ":modelling-module")
	Module modellingModule();
	
	@GlobalId(GLOBAL_ID_PREFIX + "modelling-deployment-model")
	GmMetaModel modellingDeploymentModel();
	
	@GlobalId(GLOBAL_ID_PREFIX + "modelling-api-model")
	GmMetaModel modellingApiModel();
	
	@GlobalId(GLOBAL_ID_PREFIX + "modelling-management-model")
	GmMetaModel managementModel();
	
	@GlobalId(GLOBAL_ID_PREFIX + "modelling-management-api-model")
	GmMetaModel managementApiModel();
	
	@GlobalId(GLOBAL_ID_PREFIX + "modelling-project-model")
	GmMetaModel modellingProjectModel();
	
	@GlobalId(GROUP_UX_MODULE_PREFIX + ":modelling-ux-module")
	UxModule modellerUxModule();

}
