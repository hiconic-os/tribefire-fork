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

import java.math.BigDecimal;
import java.util.Set;

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * A <code>Company</code> has a {@link #getName() name} and holds references to its {@link #getCeo() CEO},
 * {@link #getEmployees() employees} and {@link #getDepartments() departments}. It also has a (main)
 * {@link #getAddress() address}, inherited from a {@link HasAddress super type}.
 */
@SelectiveInformation("${name}")
public interface Company extends StandardIdentifiable, HasAddress {

	// Constant to conveniently access the entity type.
	EntityType<Company> T = EntityTypes.T(Company.class);

	/* Constants which provide convenient access to all property names, which is e.g. useful for queries. */
	String name = "name";
	String ceo = "ceo";
	String departments = "departments";
	String employees = "employees";
	String averageRevenue = "averageRevenue";

	/**
	 * The company name.
	 */
	String getName();
	void setName(String name);

	/**
	 * The CEO of this company.
	 */
	Person getCeo();
	void setCeo(Person ceo);

	/**
	 * The average revenue of the company.
	 */
	BigDecimal getAverageRevenue();
	void setAverageRevenue(BigDecimal averageRevenue);

	/**
	 * The set of {@link Department}s of this company.
	 */
	Set<Department> getDepartments();
	void setDepartments(Set<Department> departments);

	/**
	 * The set of {@link Person}s employed at this company.
	 */
	Set<Person> getEmployees();
	void setEmployees(Set<Person> employees);

}
