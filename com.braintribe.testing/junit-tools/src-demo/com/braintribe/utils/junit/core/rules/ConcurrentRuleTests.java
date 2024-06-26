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

import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;

public class ConcurrentRuleTests {

	@Rule
	public ConcurrentRule concurrentRule = new ConcurrentRule(2);

	@Test
	public void demoWithDefalutValue() {
		System.out.println("Run by thread: " + Thread.currentThread().getName());
	}

	@Test
	@Concurrent(5)
	public void demoWithCustomValue() {
		System.out.println("Run by thread: " + Thread.currentThread().getName());
	}

	@Test(expected = NumberFormatException.class)
	public void demoWithExpectedException() {
		/* This just shows that the "expected" exception is handled by JUnit framework correctly, even if we use this (or any other) custom rule. */

		// Wouldn't it be fun if this was actually parsable in future versions of java?
		int value = Integer.parseInt("forty-two");
		// should be unreachable
		fail("What?! Integer (unexpectedly) could be parsed: " + value);
	}

}
