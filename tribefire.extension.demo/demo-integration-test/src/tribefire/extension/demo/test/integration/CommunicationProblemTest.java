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
package tribefire.extension.demo.test.integration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.common.attribute.AttributeContext;
import com.braintribe.common.attribute.AttributeContextBuilder;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.model.accessapi.GetPartitions;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSessionFactory;
import com.braintribe.model.processing.webrpc.client.ErroneusMultipartDataCapture;
import com.braintribe.model.processing.webrpc.client.RemoteCapture;
import com.braintribe.model.processing.webrpc.client.RemoteCaptureAspect;
import com.braintribe.model.securityservice.GetCurrentUser;
import com.braintribe.model.securityservice.OpenUserSession;
import com.braintribe.model.securityservice.ValidateUserSession;
import com.braintribe.model.user.User;
import com.braintribe.product.rat.imp.ImpApi;
import com.braintribe.product.rat.imp.impl.utils.QueryHelper;
import com.braintribe.testing.internal.tribefire.tests.AbstractTribefireQaTest;
import com.braintribe.utils.IOTools;
import com.braintribe.utils.collection.impl.AttributeContexts;

import tribefire.extension.demo.test.integration.utils.DemoConstants;

/**
 * Generically loads all entity types directly declared by the demo-model and checks if create, read & update functions for the DemoAccess
 *
 * for more details check log output or log.info lines in testCRUD method
 *
 * @author Neidhart
 *
 */
public class CommunicationProblemTest extends AbstractTribefireQaTest implements DemoConstants {

	private QueryHelper queryHelper;

	private PersistenceGmSession session;
	private PersistenceGmSessionFactory factory;

	@Before
	public void initLocal() {
		
		//System.setProperty("qa.force.url", "https://development-tribefire-demo-cortex.t1-staging.tribefire-aws.cloud/services");
		//System.setProperty("qa.force.url", "https://demo-13152156-pe-m-06-ci-tests.t1-staging.tribefire-aws.cloud/services");
		logger.info("Preparing DevQa Test for tribefire demo cartridge");

		String baseURL = apiFactory().getBaseURL();
		System.out.println(baseURL);
		ImpApi imp = apiFactory().build();
		factory = apiFactory().buildSessionFactory();

		session = factory.newSession("cortex");
		queryHelper = new QueryHelper(session);
	}

	@Test
	public void getCurrentUserAThousandTimes() {
		AttributeContextBuilder derive = AttributeContexts.peek().derive();
		
		boolean capture = true;
		
		if (capture) {
			derive.set(RemoteCaptureAspect.class, RemoteCapture.response);
			derive.set(ErroneusMultipartDataCapture.class, ip -> {
				String filename = "capture/mp-capture-getCurrentUserAThousandTimes-" + UUID.randomUUID().toString() + ".txt";
				File file = new File(filename);
				file.getParentFile().mkdirs();
				try (OutputStream out = new FileOutputStream(file); InputStream in = ip.openInputStream()) {
					IOTools.transferBytes(in, out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
		
		AttributeContext context = derive.build();

		AttributeContexts.push(context);

		try {
			doTest();
		} finally {
			AttributeContexts.pop();
		}
	}
	
	private void doTest() {
		for (int i = 0; i < 10000; i++) {
			GetCurrentUser getCurrentUser = GetCurrentUser.T.create();
			getCurrentUser.eval(session).get();
			GetPartitions getPartitions = GetPartitions.T.create();
			getPartitions.setServiceId("cortex");
			getPartitions.eval(session).get();
			if (i % 100 == 0) System.out.println(i);
		}
	}
}
