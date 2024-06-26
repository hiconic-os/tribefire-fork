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
package com.braintribe.tribefire.scheduling.test;

import org.junit.After;
import org.junit.Before;

import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSessionFactory;
import com.braintribe.product.rat.imp.ImpApi;
import com.braintribe.product.rat.imp.ImpApiFactory;
import com.braintribe.testing.internal.tribefire.tests.AbstractTribefireQaTest;

import tribefire.extension.scheduling.SchedulingConstants;

public abstract class AbstractSchedulingTest extends AbstractTribefireQaTest {

	public static final String SERVICES_URL = "https://localhost:9443/tribefire-services/";
	public static final String CORTEX_USER = "cortex";
	public static final String CORTEX_PASSWORD = "cortex"; // NOSONAR: it is a test
	public static final String CORTEX_ID = "cortex";

	protected PersistenceGmSessionFactory sessionFactory;

	protected static PersistenceGmSession cortexSession;
	protected PersistenceGmSession schedulingSession;

	protected static ImpApi globalImp;

	// -----------------------------------------------------------------------
	// SETUP & TEARDOWN
	// -----------------------------------------------------------------------

	@Before
	public void before() throws Exception {
		if (globalImp == null) {
			globalImp = ImpApiFactory.with().credentials(CORTEX_USER, CORTEX_PASSWORD).build();
			// globalImp = ImpApiFactory.with().credentials(CORTEX_USER, CORTEX_PASSWORD).url(SERVICES_URL).build();

			cortexSession = globalImp.switchToAccess(CORTEX_ID).session();

		}
		if (sessionFactory == null) {
			sessionFactory = accessId -> globalImp.switchToAccess(accessId).session();
		}

	}

	@After
	public void after() throws Exception {
		// nothing
	}

	// -----------------------------------------------------------------------
	// HELPER METHODS
	// -----------------------------------------------------------------------

	protected PersistenceGmSession getSession() {
		return sessionFactory.newSession(SchedulingConstants.ACCESS_ID);
	}
}
