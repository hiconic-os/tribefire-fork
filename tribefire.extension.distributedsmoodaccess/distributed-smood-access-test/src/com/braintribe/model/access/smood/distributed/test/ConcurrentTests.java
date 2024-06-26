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
package com.braintribe.model.access.smood.distributed.test;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.model.access.smood.distributed.test.concurrent.tester.AbstractSmoodDbAccessTest;
import com.braintribe.testing.category.Slow;
import com.braintribe.testing.category.SpecialEnvironment;

@Category(SpecialEnvironment.class)
public class ConcurrentTests extends TestBase {

	@Test
	@Category(Slow.class)
	public void executeConcurrentTests() {
		
		List<AbstractSmoodDbAccessTest> testers = configuration.concurrentTesters();
		if (testers != null) {
			for (AbstractSmoodDbAccessTest tester : testers) {
				try {
					tester.executeTest();
				} catch(Exception e) {
					throw new AssertionError("Tester "+tester+" failed.", e);
				}
			}
		}
		
	}
}
