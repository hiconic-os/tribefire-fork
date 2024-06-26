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
package com.braintribe.model.processing.rpc.commons.impl.filter;

import java.util.function.Predicate;

public class TypeAssignabilityFilter implements Predicate<Class<?>> {
	
	private Class<?> type;

	public void setType(Class<?> type) {
		this.type = type;
	}

	@Override
	public boolean test(Class<?> incomingType) {

		if (incomingType == null || type == null) {
			return false;
		}

		return type.isAssignableFrom(incomingType);

	}

}
