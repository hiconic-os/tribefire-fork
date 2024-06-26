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
package com.braintribe.common.lcd.equalscheck;

/**
 * Default {@link EqualsCheck} which just delegates to {@link Object#equals(Object)} .
 *
 * @author michael.lafite
 *
 * @param <T>
 *            the type of the objects to check for equality
 */
public class DefaultEqualsCheck<T> implements EqualsCheck<T> {

	public DefaultEqualsCheck() {
		// nothing to do
	}

	/**
	 * Checks if the two objects are equal using {@link Object#equals(Object)}. Note that this method also returns <code>true</code>, if both objects
	 * are <code>null</code>.
	 */
	@Override
	public boolean equals(final T object1, final Object object2) {
		if (object1 == null) {
			return (object2 == null);
		}

		return object1.equals(object2);
	}

}
