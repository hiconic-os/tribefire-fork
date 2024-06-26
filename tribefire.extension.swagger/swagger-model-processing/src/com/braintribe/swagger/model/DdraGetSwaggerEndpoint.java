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
package com.braintribe.swagger.model;

import com.braintribe.model.ddra.endpoints.v2.RestV2Endpoint;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface DdraGetSwaggerEndpoint extends RestV2Endpoint {

	EntityType<DdraGetSwaggerEndpoint> T = EntityTypes.T(DdraGetSwaggerEndpoint.class);

	String getAccessId();
	void setAccessId(String accessId);

	@Initializer("false")
	boolean getEnablePartition();
	void setEnablePartition(boolean enablePartition);

	String getResource();
	void setResource(String resources);

	String getBasePath();
	void setBasePath(String basePath);

}
