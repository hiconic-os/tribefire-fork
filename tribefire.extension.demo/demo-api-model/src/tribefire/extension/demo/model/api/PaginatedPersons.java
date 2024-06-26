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
package tribefire.extension.demo.model.api;

import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.pagination.Paginated;

import tribefire.extension.demo.model.data.Person;

/**
 * {@link Paginated} result for a list of {@link Person}s.
 * 
 * @author peter.gazdik
 */
public interface PaginatedPersons extends Paginated {

	EntityType<PaginatedPersons> T = EntityTypes.T(PaginatedPersons.class);

	List<Person> getPersons();
	void setPersons(List<Person> persons);

}
