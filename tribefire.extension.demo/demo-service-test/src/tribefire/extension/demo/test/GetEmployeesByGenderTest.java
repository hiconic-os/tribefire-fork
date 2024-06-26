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
package tribefire.extension.demo.test;

import static com.braintribe.utils.lcd.CollectionTools2.asSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.manipulation.DeleteMode;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;

import tribefire.extension.demo.model.api.GetEmployeesByGender;
import tribefire.extension.demo.model.data.Company;
import tribefire.extension.demo.model.data.Gender;
import tribefire.extension.demo.model.data.Person;

public class GetEmployeesByGenderTest extends DemoTestBase {
	
	private Company company;

	@Before
	public void prepare() {
		company = testAccessSession.create(Company.T);
		
		Person person1 = testAccessSession.create(Person.T);
		person1.setFirstName("John");
		person1.setLastName("Doe");
		person1.setGender(Gender.male);
		
		Person person2 = testAccessSession.create(Person.T);
		person2.setFirstName("Jane");
		person2.setLastName("Doe");
		person2.setGender(Gender.female);
		
		Person person3 = testAccessSession.create(Person.T);
		person3.setFirstName("Sue");
		person3.setLastName("St. Doe");
		person3.setGender(Gender.female);
		
		company.setEmployees(asSet(person1, person2, person3));
		
		testAccessSession.commit();
	}
	
	@After
	public void cleanup() {
		List<GenericEntity> genericEntities = testAccessSession.query().entities(EntityQueryBuilder.from(GenericEntity.T).done()).list();

		for (GenericEntity genericEntity : genericEntities) {
			testAccessSession.deleteEntity(genericEntity, DeleteMode.dropReferences);
		}
		testAccessSession.commit();
	}
	
	@Test
	public void testGettingEmployeesByGender() {
		GetEmployeesByGender request = GetEmployeesByGender.T.create();
		request.setDomainId(testAccessSession.getAccessId());
		request.setCompany(company);
		request.setGender(Gender.male);
		
		List<GenericEntity> response = request.eval(testAccessSession).get();
		
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(Person.T.getTypeSignature(), response.get(0).entityType().getTypeSignature());
		assertEquals(Gender.male, ((Person)response.get(0)).getGender());
		
		request.setGender(Gender.female);
		
		response = request.eval(testAccessSession).get();
		
		assertNotNull(response);
		assertEquals(2, response.size());
		assertEquals(Person.T.getTypeSignature(), response.get(0).entityType().getTypeSignature());
		assertEquals(Person.T.getTypeSignature(), response.get(1).entityType().getTypeSignature());
		assertEquals(Gender.female, ((Person)response.get(0)).getGender());
		assertEquals(Gender.female, ((Person)response.get(1)).getGender());
	}
	
}
