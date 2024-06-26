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

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * An <code>Address</code> describes a location. It consists of information such as the {@link #getStreet() street},
 * {@link #getCity() city} and {@link #getCountry() country}.
 */
@SelectiveInformation("${street} ${streetNumber}, ${postalCode} ${city} (${country})")
public interface Address extends StandardIdentifiable {

	// Constant to conveniently access the entity type.
	EntityType<Address> T = EntityTypes.T(Address.class);

	/* Constants which provide convenient access to all property names, which is e.g. useful for queries. */
	String street = "street";
	String streetNumber = "streetNumber";
	String postalCode = "postalCode";
	String city = "city";
	String country = "country";

	/**
	 * The street name.
	 */
	String getStreet();
	void setStreet(String street);

	/**
	 * The street number. (For the sake of simplicity this is just a number, although there could be street numbers like
	 * <code>21b</code>.)
	 */
	Integer getStreetNumber();
	void setStreetNumber(Integer streetNumber);

	/**
	 * The postal code.
	 */
	String getPostalCode();
	void setPostalCode(String postalCode);

	/**
	 * The city (name).
	 */
	String getCity();
	void setCity(String city);

	/**
	 * The country (name).
	 */
	String getCountry();
	void setCountry(String country);

}
