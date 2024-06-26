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
package tribefire.extension.demo.initializer.wire.contract;

import com.braintribe.model.deployment.Module;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.cortex.initializer.support.impl.lookup.GlobalId;
import tribefire.cortex.initializer.support.impl.lookup.InstanceLookup;

/**
 * <p>
 * This {@link WireSpace Wire contract} provides lookups on already existing instances. <br>
 * It exposes instances like:
 * <ul>
 * <li>Models which are coming from ModelPriming assets</li>
 * <li>Resources coming from ResourcePriming assets</li>
 * </ul>
 * </p>
 */
@InstanceLookup(lookupOnly = true)
public interface ExistingInstancesContract extends WireSpace {

	String GROUP_ID = "tribefire.extension.demo";
	String GLOBAL_ID_PREFIX = "model:" + GROUP_ID + ":";

	@GlobalId("module://" + GROUP_ID + ":demo-module")
	Module demoModule();

	@GlobalId(GLOBAL_ID_PREFIX + "demo-model")
	GmMetaModel demoModel();

	@GlobalId(GLOBAL_ID_PREFIX + "demo-deployment-model")
	GmMetaModel demoDeploymentModel();

	@GlobalId(GLOBAL_ID_PREFIX + "demo-api-model")
	GmMetaModel demoServiceModel();

	@GlobalId(GLOBAL_ID_PREFIX + "demo-cortex-api-model")
	GmMetaModel demoCortexServiceModel();

	@GlobalId("model:com.braintrige.gm:security-service-api-model")
	GmMetaModel securityServiceApiModel();

	@GlobalId("property:tribefire.extension.demo.model.data.process.HolidayRequestProcess/approvalStatus")
	GmProperty statusApproval();

}
