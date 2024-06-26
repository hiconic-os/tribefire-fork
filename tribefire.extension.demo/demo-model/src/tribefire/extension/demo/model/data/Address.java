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
package tribefire.extension.demo.model.data;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * An Address is a holder for address informations identifying the location of a {@link Person} or {@link Company}.
 */

@SelectiveInformation("${street} ${streetNumber} ${postalCode} ${city} ${country}")
public interface Address extends GenericEntity {

	EntityType<Address> T = EntityTypes.T(Address.class);

	/* Constants for each property name. */
	public static final String street = "street";
	public static final String streetNumber = "streetNumber";
	public static final String postalCode = "postalCode";
	public static final String city = "city";
	public static final String country = "country";

	/**
	 * The street as string.
	 */
	@Mandatory
	String getStreet();
	void setStreet(String street);

	/**
	 * The street number as integer.
	 */
	@Mandatory
	Integer getStreetNumber();
	void setStreetNumber(Integer streetNumber);

	/**
	 * The postal code as string.
	 */
	@Mandatory
	String getPostalCode();
	void setPostalCode(String postalCode);

	/**
	 * The city as string.
	 */
	@Mandatory
	String getCity();
	void setCity(String city);

	/**
	 * The country as string.
	 */
	@Mandatory
	String getCountry();
	void setCountry(String country);
}
