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

import java.util.Map;
import java.util.function.Supplier;

import com.braintribe.wire.api.context.WireContext;
import com.braintribe.wire.api.space.ContractResolution;
import com.braintribe.wire.api.space.ContractSpaceResolver;
import com.braintribe.wire.api.space.WireSpace;

public class MapBasedContractSpaceResolver implements ContractSpaceResolver {

	private Map<Class<? extends WireSpace>, Supplier<ContractResolution>> mappings;
	private boolean lenient;
	
	public MapBasedContractSpaceResolver(Map<Class<? extends WireSpace>, Supplier<ContractResolution>> mappings, boolean lenient) {
		this.mappings = mappings;
		this.lenient = lenient;
	}

	@Override
	public ContractResolution resolveContractSpace(WireContext<?> wireContext, Class<? extends WireSpace> contractSpaceClass) {
		return resolveContractSpace(contractSpaceClass);
	}
	
	@Override
	public ContractResolution resolveContractSpace(Class<? extends WireSpace> contractSpaceClass) {
		Supplier<ContractResolution> mappedClassSupplier = mappings.get(contractSpaceClass);
		
		if (mappedClassSupplier == null) {
			if (lenient)
				return null;
			
			throw new IllegalStateException("Contract BeanSpace " +  contractSpaceClass + " is not mapped");
		}
		
		return mappedClassSupplier.get();
	}
	
}
