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
package com.braintribe.wire.impl.contract;

import com.braintribe.wire.api.annotation.ContractAggregation;
import com.braintribe.wire.api.context.WireContext;
import com.braintribe.wire.api.space.ContractResolution;
import com.braintribe.wire.api.space.ContractSpaceResolver;
import com.braintribe.wire.api.space.WireSpace;

public class ContractAggregationSpaceResolver implements ContractSpaceResolver {

	@Override
	public ContractResolution resolveContractSpace(Class<? extends WireSpace> contractSpaceClass) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public ContractResolution resolveContractSpace(WireContext<?> wireContext,
			Class<? extends WireSpace> contractSpaceClass) {
		if (!contractSpaceClass.isAnnotationPresent(ContractAggregation.class))
			return null;
		
		return className -> ContractAggregationInvocationHandler.create(wireContext, contractSpaceClass);
	}

}
