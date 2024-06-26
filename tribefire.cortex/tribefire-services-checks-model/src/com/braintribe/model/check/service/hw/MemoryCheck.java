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
package com.braintribe.model.check.service.hw;

import com.braintribe.model.accessapi.AccessRequest;
import com.braintribe.model.check.service.ParameterizedCheckRequest;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.AuthorizedRequest;

public interface MemoryCheck extends ParameterizedCheckRequest, AccessRequest, AuthorizedRequest {

	EntityType<MemoryCheck> T = EntityTypes.T(MemoryCheck.class);
	
	void setGlobalMemoryThresholds(MemoryThresholdValues globalMemoryThresholds);
	MemoryThresholdValues getGlobalMemoryThresholds();

	void setSwapMemoryThresholds(MemoryThresholdValues swapMemoryThresholds);
	MemoryThresholdValues getSwapMemoryThresholds();

	void setJavaMemoryThresholds(MemoryThresholdValues javaMemoryThresholds);
	MemoryThresholdValues getJavaMemoryThresholds();
	
}
