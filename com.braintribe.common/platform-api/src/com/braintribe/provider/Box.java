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
package com.braintribe.provider;

/**
 * Simple version of a {@link Holder}. The main purpose is to wrap a local variable, so it can be accessed within a lambda even if it's not final
 * (just the box itself is final
 * <p>
 * Of course, only do that if you know what you're doing.
 *
 * @author peter.gazdik
 */
public class Box<T> {

	public T value;

	public static <T> Box<T> of(T value) {
		Box<T> result = new Box<T>();
		result.value = value;

		return result;
	}
}
