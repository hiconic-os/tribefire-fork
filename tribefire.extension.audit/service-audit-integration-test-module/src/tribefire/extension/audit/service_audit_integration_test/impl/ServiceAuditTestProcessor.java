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
package tribefire.extension.audit.service_audit_integration_test.impl;

import java.util.Map;

import com.braintribe.cfg.Required;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.gm.model.reason.Reasons;
import com.braintribe.gm.model.reason.essential.InvalidArgument;
import com.braintribe.gm.model.reason.essential.NotFound;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.processing.service.impl.AbstractDispatchingServiceProcessor;
import com.braintribe.model.processing.service.impl.DispatchConfiguration;

import tribefire.extension.audit.model.test.api.GetPersonData;
import tribefire.extension.audit.model.test.api.TestRequest;
import tribefire.extension.audit.model.test.data.Person;

public class ServiceAuditTestProcessor extends AbstractDispatchingServiceProcessor<TestRequest, Object> {

	private Map<String, Person> data;

	@Override
	protected void configureDispatching(DispatchConfiguration<TestRequest, Object> dispatching) {
		dispatching.registerReasoned(GetPersonData.T, this::getPersonData);
	}
	
	@Required
	public void setData(Map<String, Person> data) {
		this.data = data;
	}
	
	private Maybe<Person> getPersonData(ServiceRequestContext context, GetPersonData request) {
		
		String personId = request.getPersonId();
		
		if (personId == null)
			return Reasons.build(InvalidArgument.T).text("GetPersonData.personId must not be null").toMaybe();
			
		
		Person person = data.get(personId);
		
		if (person != null)
			return Maybe.complete(person);
		
		return Reasons.build(NotFound.T).text("Person with id " + personId + " not found").toMaybe();
	}
}
