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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.braintribe.testing.model.test.demo.person.Person;

import tribefire.extension.demo.model.api.EntityMarshallingResponse;
import tribefire.extension.demo.model.api.MarshallEntityToJson;
import tribefire.extension.demo.model.api.MarshallEntityToXml;
import tribefire.extension.demo.model.api.MarshallEntityToYaml;

public class EntityMarshallingTest extends DemoTestBase {
	
	@Test
	public void testJsonMarshalling() {
		MarshallEntityToJson request = MarshallEntityToJson.T.create();
		Person person = Person.T.create();
		person.setFirstName("John");
		person.setLastName("Doe");
		request.setEntity(person);
		
		EntityMarshallingResponse response = request.eval(evaluator).get();
		
		assertNotNull(response.getMarshalledEntity());
		assertFalse(response.getMarshalledEntity().trim().isEmpty());
	}
	
	@Test
	public void testXmlMarshalling() {
		MarshallEntityToXml request = MarshallEntityToXml.T.create();
		Person person = Person.T.create();
		person.setFirstName("John");
		person.setLastName("Doe");
		request.setEntity(person);
		
		EntityMarshallingResponse response = request.eval(evaluator).get();
		
		assertNotNull(response.getMarshalledEntity());
		assertFalse(response.getMarshalledEntity().trim().isEmpty());
	}
	
	@Test
	public void testYamlMarshalling() {
		MarshallEntityToYaml request = MarshallEntityToYaml.T.create();
		Person person = Person.T.create();
		person.setFirstName("John");
		person.setLastName("Doe");
		request.setEntity(person);
		
		EntityMarshallingResponse response = request.eval(evaluator).get();
		
		assertNotNull(response.getMarshalledEntity());
		assertFalse(response.getMarshalledEntity().trim().isEmpty());
	}

}
