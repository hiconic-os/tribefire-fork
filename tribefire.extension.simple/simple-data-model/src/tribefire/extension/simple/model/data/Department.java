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
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * A department belongs to a {@link #getCompany() company}, has a certain {@link #getNumberOfEmployees() number of employees}, a {@link #manager} and
 * is hopefully {@link #getProfitable() profitable}.
 */
@SelectiveInformation("${name}")
public interface Department extends StandardIdentifiable {

	// Constant to conveniently access the entity type.
	EntityType<Department> T = EntityTypes.T(Department.class);

	/* Constants which provide convenient access to all property names, which is e.g. useful for queries. */
	String name = "name";
	String manager = "manager";
	String numberOfEmployees = "numberOfEmployees";
	String profitable = "profitable";
	String company = "company";

	/**
	 * The department name.
	 */
	String getName();
	void setName(String name);

	/**
	 * The manager of this department.
	 */
	Person getManager();
	void setManager(Person manager);

	/**
	 * The number of employees working in this department.
	 */
	int getNumberOfEmployees();
	void setNumberOfEmployees(int numberOfEmployees);

	/**
	 * A flag indicating whether this department is profitable.<br>
	 * The default value is <code>true</code>.
	 */
	@Initializer("true")
	boolean getProfitable();
	void setProfitable(boolean profitable);

	/**
	 * The back link to the {@link Company} this department belongs to.
	 */
	Company getCompany();
	void setCompany(Company company);
}
