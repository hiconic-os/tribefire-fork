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
package com.braintribe.testing.junit.rules;

import org.junit.Rule;
import org.junit.Test;

public class LoopRuleTests {

	@Rule
	public LoopRule loopRule = new LoopRule(2);

	@Test
	public void demoWithDefaultValue() {
		System.out.println("This should be printed twice!");
	}

	@Test
	@Loop(5)
	public void demoWithCustomValue() {
		System.out.println("This value should be printed 5 times!");
	}
}
