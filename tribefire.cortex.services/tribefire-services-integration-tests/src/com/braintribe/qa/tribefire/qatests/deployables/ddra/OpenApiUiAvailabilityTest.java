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
package com.braintribe.qa.tribefire.qatests.deployables.ddra;

import org.junit.Test;

import com.braintribe.testing.internal.tribefire.tests.ServletAvailabilityTest;

public class OpenApiUiAvailabilityTest extends ServletAvailabilityTest {
	@Test
	public void test() throws Exception {
		testEndpoint("services", "cortex");
		testEndpoint("entities", "cortex");
		testEndpoint("properties", "cortex");
	}

	public void testEndpoint(String endpoint, String accessId) throws Exception {
		assertServletAvailability("/openapi/ui/" + endpoint + "/" + accessId + "?sessionId=" + sessionId);
	}
}
