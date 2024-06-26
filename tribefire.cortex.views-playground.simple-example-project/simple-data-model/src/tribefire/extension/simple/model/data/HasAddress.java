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
package tribefire.extension.simple.model.data;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Super type for entities that have an {@link Address}.<br>
 * Note that this type is {@link Abstract}, which means it cannot be instantiated.
 */
@Abstract
public interface HasAddress extends GenericEntity {

	// Constant to conveniently access the entity type.
	EntityType<HasAddress> T = EntityTypes.T(HasAddress.class);

	/* Constants which provide convenient access to all property names, which is e.g. useful for queries. */
	String address = "address";

	/**
	 * A reference to the {@link Address} instance.
	 */
	Address getAddress();
	void setAddress(Address address);

}
