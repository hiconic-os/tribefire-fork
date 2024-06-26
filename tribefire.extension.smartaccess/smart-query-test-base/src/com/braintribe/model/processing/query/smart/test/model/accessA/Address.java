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
package com.braintribe.model.processing.query.smart.test.model.accessA;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * This entity only exists in A and the name has no "A" suffix so we can then use different mappings.
 */

public interface Address extends StandardIdentifiableA {

	final EntityType<Address> T = EntityTypes.T(Address.class);

	// @formatter:off
	String getStreet();
	void setStreet(String street);

	Integer getNumber();
	void setNumber(Integer number);

	String getZipCode();
	void setZipCode(String zipCode);

	String getCountry();
	void setCountry(String country);
	// @formatter:on

}
