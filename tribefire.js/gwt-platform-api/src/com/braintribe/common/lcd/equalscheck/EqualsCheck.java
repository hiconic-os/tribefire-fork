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
 * Interface used to implement custom <code>equals</code> checks without overriding {@link Object#equals(Object)}.
 *
 * @author michael.lafite
 *
 * @param <T>
 *            the type of the objects to check for equality
 */
public interface EqualsCheck<T> {

	/**
	 * Checks whether the two objects are equal (from the standpoint of this check). Objects may be <code>null</code>.
	 */
	boolean equals(T object1, Object object2);

}
