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
package com.braintribe.utils.junit.core.rules;

import org.junit.Rule;
import org.junit.Test;

public class ThrowableChainRuleTests {

	@Rule
	public ThrowableChainRule exceptionChainRule = new ThrowableChainRule(Exception.class, IllegalArgumentException.class);

	@Test
	public void defaultThrowableChain() throws Exception {
		throw new Exception(new IllegalArgumentException());
	}

	@Test
	@ThrowableChain({ Exception.class, RuntimeException.class })
	public void customChainSpecifiedByAnnotation() throws Exception {
		throw new Exception(new RuntimeException());
	}

	@Test
	@ThrowableChain({})
	public void noExceptionExpected() {
		// empty
	}

	@Test(expected = RuntimeException.class)
	@ThrowableChain({})
	public void justShowingHowItIntegratesWithJunit() throws Exception {
		/* This test passes, because this method exits with RuntimeException, but the JUnit runner expects that (expected=RuntimeException.class), so
		 * it "eats" the exception. Therefore, since our rule is configured to expect no exception at all, the test passes.
		 *
		 * If you remove the @ThrowableChain thing, the test will fail telling you no exception was thrown, even though it should have been. (Just try
		 * it.) */
		throw new RuntimeException();
	}

}
