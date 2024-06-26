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
package tribefire.extension.simple.deployables.access;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;
import static com.braintribe.testing.junit.assertions.gm.assertj.core.api.GmAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.impl.persistence.BasicPersistenceGmSession;

import tribefire.extension.simple.deployables.access.SimpleInMemoryAccess;
import tribefire.extension.simple.model.data.Company;
import tribefire.extension.simple.model.data.Person;

/**
 * Provides tests for {@link SimpleInMemoryAccess}.<br>
 * The main purpose of this class is to demonstrate how to easily write unit tests for an access.
 *
 * @author michael.lafite
 */
public class SimpleInMemoryAccessTest {

	/**
	 * Creates a new {@link SimpleInMemoryAccess} initialized with example data. Afterwards verifies that the example
	 * data is correct (by doing some queries). Finally the test also performs some manipulations and then again
	 * verifies the result via queries.
	 */
	@Test
	public void test() throws GmSessionException {
		// create access and example data
		SimpleInMemoryAccess access = new SimpleInMemoryAccess();
		access.setInitializeWithExampleData(true);
		access.postConstruct();

		// use a session to work with the access
		// this is a) more convenient and b) the same code can be used in an integration test
		PersistenceGmSession session = new BasicPersistenceGmSession(access);

		// query for company
		Company company = session.query().entities(EntityQueryBuilder.from(Company.class).done()).unique();
		// check some properties
		assertThat(company).isNotNull().hasPropertyValue(Company.name, "Acme").hasPropertyValue(Company.averageRevenue, new BigDecimal("1234567890"));
		assertThat(company.getCeo().getFirstName()).isEqualTo("Jack");

		// query for person entities and check results via comparison of first names
		assertThat(session.query().entities(EntityQueryBuilder.from(Person.class).done()).list()).hasSize(4).extracting(Person.firstName)
				.containsExactlyInAnyOrder("Jack", "Jim", "Jane", "John");

		// create a new person and persist it
		Person person = session.create(Person.T);
		person.setFirstName("Joan");
		session.commit();

		// query again and verify that new person was persisted successfully
		PersistenceGmSession otherSession = new BasicPersistenceGmSession(access);
		assertThat(otherSession.query().entities(EntityQueryBuilder.from(Person.class).done()).list()).hasSize(5).extracting(Person.firstName)
				.containsExactlyInAnyOrder("Jack", "Jim", "Jane", "John", "Joan");
	}

}
