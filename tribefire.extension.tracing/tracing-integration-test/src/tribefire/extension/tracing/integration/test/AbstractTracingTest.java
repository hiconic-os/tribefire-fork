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
package tribefire.extension.tracing.integration.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;

import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSessionFactory;
import com.braintribe.testing.internal.tribefire.tests.AbstractTribefireQaTest;

import tribefire.extension.tracing.integration.test.util.CustomJUnitStopWatch;

public abstract class AbstractTracingTest extends AbstractTribefireQaTest {

	protected PersistenceGmSession cortexSession;
	private PersistenceGmSessionFactory sessionFactory;

	@Rule
	public CustomJUnitStopWatch stopwatch = new CustomJUnitStopWatch();

	// -----------------------------------------------------------------------
	// CLASS - SETUP / TEARDOWN
	// -----------------------------------------------------------------------

	@BeforeClass
	public static void beforeClass() throws Exception {
		// nothing so far
	}

	@AfterClass
	public static void afterClass() throws Exception {
		// nothing so far
	}

	// -----------------------------------------------------------------------
	// TEST - SETUP / TEARDOWN
	// -----------------------------------------------------------------------

	@Before
	public void before() throws Exception {
		sessionFactory = apiFactory().buildSessionFactory();
		cortexSession = sessionFactory.newSession("cortex");
	}

	@After
	public void after() throws Exception {
		// nothing so far
	}

	// -----------------------------------------------------------------------
	// HELPER METHODS
	// -----------------------------------------------------------------------

}
