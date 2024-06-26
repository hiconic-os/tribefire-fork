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
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Bidirectional;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * This type holds informations about a particular department of a
 * {@link Company}
 */

@SelectiveInformation("${name}")
public interface Department extends GenericEntity, HasComments {

	
	final EntityType<Department> T = EntityTypes.T(Department.class);
	
	/*
	 * Constants for each property name.
	 */
	public static final String name = "name";
	public static final String manager = "manager";
	public static final String numberOfEmployees = "numberOfEmployees";
	public static final String profitable = "profitable";
	public static final String company = "company";

	/**
	 * The name of this department as string.
	 */
	@Mandatory
	String getName();
	void setName(String name);

	/**
	 * A reference to the manager (natural {@link Person}).
	 */
	Person getManager();
	void setManager(Person manager);

	/**
	 * The number of employees working in this department as integer.
	 */
	@Initializer("0")
	int getNumberOfEmployees();
	void setNumberOfEmployees(int numberOfEmployees);

	/**
	 * A boolean flag indicating whether this department is profitable.<br />
	 * The field is initialized with true when an instance is created.
	 */
	@Initializer("true")
	boolean getProfitable();
	void setProfitable(boolean profitable);

	/**
	 * The back link to the company this department belongs to.
	 */
	@Bidirectional(type=Company.class, property=Company.departments)
	Company getCompany();
	void setCompany(Company company);

}
