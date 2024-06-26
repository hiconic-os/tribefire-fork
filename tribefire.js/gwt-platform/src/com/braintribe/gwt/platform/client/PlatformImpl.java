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
package com.braintribe.gwt.platform.client;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class PlatformImpl {

	protected Map<Class<?>, Supplier<?>> factories = new HashMap<Class<?>, Supplier<?>>();

	public <T> T create(Class<T> clazz) {
		Supplier<?> provider = factories.get(clazz);

		try {
			T result = (T) provider.get();
			if (result == null) {
				throw new IllegalStateException("Missing factory for :" + clazz);
			}

			return result;

		} catch (RuntimeException e) {
			throw new IllegalStateException("Error while creating instance for :" + clazz, e);
		}
	}

}
