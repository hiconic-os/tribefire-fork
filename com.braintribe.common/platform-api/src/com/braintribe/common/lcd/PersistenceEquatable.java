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
package com.braintribe.common.lcd;

/**
 * Classes implementing this interface indicate that method {@link #persistenceEquals} should be used to compare instances of the class for
 * persistence equality.
 *
 * @author michael.lafite
 */
public interface PersistenceEquatable {

	/**
	 * Returns <code>true</code>, if the <code>other</code> instance is "persistence-equal" to this instance, otherwise <code>false</code>.
	 */
	boolean persistenceEquals(Object other);

}
