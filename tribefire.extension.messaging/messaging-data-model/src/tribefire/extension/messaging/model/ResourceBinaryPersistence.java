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
package tribefire.extension.messaging.model;

import com.braintribe.model.generic.base.EnumBase;
import com.braintribe.model.generic.reflection.EnumType;
import com.braintribe.model.generic.reflection.EnumTypes;

/**
 * Defines the strategy to persist the binaries of a resource
 *
 */
public enum ResourceBinaryPersistence implements EnumBase {
	/**
	 * No resource
	 */
	NONE,
	/**
	 * Only transient resources
	 */
	TRANSIENT,
	/**
	 *  Transient and access based resources
	 */
	ALL;

	public static final EnumType T = EnumTypes.T(ResourceBinaryPersistence.class);

	@Override
	public EnumType type() {
		return T;
	}

	public boolean shouldPersist(boolean isTransient) {
		boolean shouldPersist;
		switch (this) {
			case TRANSIENT -> shouldPersist = isTransient;
			case ALL ->	shouldPersist = true;
			default -> shouldPersist = false;
		}
		return shouldPersist;
	}
}
