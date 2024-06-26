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
package tribefire.extension.messaging.service.test;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.service.api.ServiceProcessor;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSessionFactory;
import com.braintribe.model.query.EntityQuery;

import tribefire.extension.messaging.model.test.TestObject;
import tribefire.extension.messaging.service.test.model.TestGetObjectRequest;
import tribefire.extension.messaging.service.test.model.TestGetObjectResult;

public class TestGetObjectProcessor implements ServiceProcessor<TestGetObjectRequest, TestGetObjectResult> {
	private PersistenceGmSessionFactory factory;

	@Override
	public TestGetObjectResult process(ServiceRequestContext requestContext, TestGetObjectRequest request) {
		// This is a stub processor to fulfill the requirements for integration testing
		PersistenceGmSession session = factory.newSession("cortex");
		EntityQuery query = EntityQueryBuilder.from(TestObject.T).where().property(TestObject.id).ilike(request.getRelatedObjId()).done();
		TestObject result = session.query().entities(query).unique();
		return TestGetObjectResult.build(result);
	}

	@Required
	@Configurable
	public void setFactory(PersistenceGmSessionFactory factory) {
		this.factory = factory;
	}
}
