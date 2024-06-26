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
package com.braintribe.gm.service.access.test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.braintribe.gm.service.access.test.wire.AccessRequestProcessingTestWireModule;
import com.braintribe.gm.service.access.test.wire.contract.AccessRequestProcessingTestContract;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.utils.IOTools;
import com.braintribe.wire.api.Wire;
import com.braintribe.wire.api.context.WireContext;

public class TestAccessResource {

	protected static WireContext<AccessRequestProcessingTestContract> context;
	protected static Evaluator<ServiceRequest> evaluator;
	protected static AccessRequestProcessingTestContract testContract;

	@Before
	public void beforeClass() {
		context = Wire.context(AccessRequestProcessingTestWireModule.INSTANCE);
		testContract = context.contract();
		evaluator = testContract.evaluator();
	}

	@After
	public void afterClass() {
		context.shutdown();
	}

	@Test
	public void test() throws Exception {
		PersistenceGmSession session = testContract.sessionFactory().newSession(TestConstants.ACCESS_ID_RESOURCE_TEST);

		Resource r = session.resources().create().name("test.txt").store(os -> {
			os.write("hello".getBytes(StandardCharsets.UTF_8));
		});

		try (InputStream in = r.openStream()) {
			String check = IOTools.slurp(in, "UTF-8");
			System.out.println(check);
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		r.writeToStream(baos);
		System.out.println(baos.toString("UTF-8"));

		session.deleteEntity(r);

	}
}
