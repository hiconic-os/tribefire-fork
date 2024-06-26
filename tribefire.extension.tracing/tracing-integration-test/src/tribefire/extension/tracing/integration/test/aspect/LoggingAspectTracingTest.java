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
package tribefire.extension.tracing.integration.test.aspect;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.model.logging.LogLevel;
import com.braintribe.testing.category.VerySlow;

import tribefire.extension.tracing.model.deployment.connector.LoggingTracingConnector;
import tribefire.extension.tracing.model.deployment.connector.TracingConnector;

/**
 * Tests with {@link LoggingTracingConnector}
 * 
 *
 */
@Category(VerySlow.class)
public class LoggingAspectTracingTest extends AbstractAspectTracingTest {

	// -----------------------------------------------------------------------
	// ABSTRACT
	// -----------------------------------------------------------------------

	@Override
	protected TracingConnector connector() {
		if (connector == null) {
			LoggingTracingConnector loggingConnector = cortexSession.create(LoggingTracingConnector.T);
			loggingConnector.setLogLevel(LogLevel.INFO);
			loggingConnector.setLogAttributes(true);

			connector = loggingConnector;
			connector.setName(TEST_CONNECTOR_EXTERNAL_ID);
			connector.setExternalId(TEST_CONNECTOR_EXTERNAL_ID);
		}
		return connector;
	}

	// -----------------------------------------------------------------------
	// TESTS
	// -----------------------------------------------------------------------

	@Test
	public void testAspect_simple() {
		demoTracing();
	}
}
