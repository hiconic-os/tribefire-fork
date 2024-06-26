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
package com.braintribe.model.plugin.persistence.auth.init.wire.contract;

import com.braintribe.gm.persistence.initializer.support.impl.lookup.GlobalId;
import com.braintribe.gm.persistence.initializer.support.impl.lookup.InstanceLookup;
import com.braintribe.model.deployment.Cartridge;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.auth.AuthConstants;
import com.braintribe.wire.api.space.WireSpace;

@InstanceLookup(lookupOnly=true)
public interface ExistingInstancesContract extends WireSpace {

	@GlobalId("model:" + AuthConstants.DEPLOYMENT_MODEL_QUALIFIEDNAME)
	GmMetaModel deploymentModel();

	@GlobalId("model:" + AuthConstants.SERVICE_MODEL_QUALIFIEDNAME)
	GmMetaModel serviceModel();
	
	@GlobalId("model:" + AuthConstants.DATA_MODEL_QUALIFIEDNAME)
	GmMetaModel dataModel();
		
	@GlobalId(AuthConstants.CARTRIDGE_GLOBAL_ID)
	Cartridge cartridge();
	
}

