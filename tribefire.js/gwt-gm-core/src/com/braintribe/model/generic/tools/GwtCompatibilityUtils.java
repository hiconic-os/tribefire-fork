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
package com.braintribe.model.generic.tools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.braintribe.exception.Exceptions;

public class GwtCompatibilityUtils {

	public static <K, V> Map<K, V> newConcurrentMap() {
		return new ConcurrentHashMap<K, V>();
	}
	
	public static Class<?> getEnumClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw Exceptions.unchecked(e, "Enum class not found: " + className);
		}
	}

}
