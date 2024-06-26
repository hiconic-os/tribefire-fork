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
package tribefire.extension.demo.processing;

import static com.braintribe.model.processing.query.building.EntityQueries.from;
import static com.braintribe.model.processing.query.building.EntityQueries.property;
import static com.braintribe.model.processing.query.building.Queries.eq;

import java.util.List;

import com.braintribe.model.processing.accessrequest.api.AccessRequestContext;
import com.braintribe.model.processing.accessrequest.api.AccessRequestProcessor;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.model.query.EntityQueryResult;

import tribefire.extension.demo.model.api.GetPersonsByName;
import tribefire.extension.demo.model.api.PaginatedPersons;
import tribefire.extension.demo.model.data.Person;

public class GetPersonsByNameProcessor implements AccessRequestProcessor<GetPersonsByName, PaginatedPersons> {

	@Override
	public PaginatedPersons process(AccessRequestContext<GetPersonsByName> context) {
		PersistenceGmSession session = context.getSession();
		GetPersonsByName request = context.getRequest();
		String name = request.getName();

		EntityQuery query = from(Person.T) //
				.where( //
						eq(property(Person.firstName), name) //
				);

		if (request.hasPagination())
			query = query.paging(request.getPageOffset(), request.getPageLimit());

		EntityQueryResult qr = session.query().entities(query).result();
		return toPaginatedPersons(qr);
	}

	private PaginatedPersons toPaginatedPersons(EntityQueryResult qr) {
		List<?> entities = qr.getEntities();

		PaginatedPersons result = PaginatedPersons.T.create();
		result.setPersons((List<Person>) entities);
		result.setHasMore(qr.getHasMore());

		return result;
	}

}
