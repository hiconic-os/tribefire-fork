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
package tribefire.extension.scripting.test.model.data;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface Person extends GenericEntity {

	EntityType<Person> T = EntityTypes.T(Person.class);

	String role = "role";

	String getRole();
	void setRole(String role);

	String state = "state";

	String getState();
	void setState(String state);

	String name = "name";

	String getName();
	void setName(String name);

	String familyName = "familyName";

	String getFamilyName();
	void setFamilyName(String familyName);

	String firstName = "firstName";

	String getFirstName();
	void setFirstName(String firstName);
}
