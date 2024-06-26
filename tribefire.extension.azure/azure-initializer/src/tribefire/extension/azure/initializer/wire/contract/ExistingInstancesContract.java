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
package tribefire.extension.azure.initializer.wire.contract;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.cortex.initializer.support.impl.lookup.GlobalId;
import tribefire.cortex.initializer.support.impl.lookup.InstanceLookup;

@InstanceLookup(lookupOnly = true)
public interface ExistingInstancesContract extends WireSpace {

	String GROUP_ID = "tribefire.extension.azure";
	String GLOBAL_ID_PREFIX = "model:" + GROUP_ID + ":";

	@GlobalId("model:" + GROUP_ID + ":azure-deployment-model")
	GmMetaModel deploymentModel();

	@GlobalId("model:" + GROUP_ID + ":azure-api-model")
	GmMetaModel serviceModel();

	@GlobalId("model:" + GROUP_ID + ":azure-model")
	GmMetaModel dataModel();

	@GlobalId("module://" + GROUP_ID + ":azure-module")
	com.braintribe.model.deployment.Module module();
}
