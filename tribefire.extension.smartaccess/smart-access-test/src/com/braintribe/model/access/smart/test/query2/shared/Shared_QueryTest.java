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
package com.braintribe.model.access.smart.test.query2.shared;

import static com.braintribe.model.processing.query.smart.test2.shared.model.shared.SharedSource.uuid;

import org.junit.Test;

import com.braintribe.model.access.smart.test.query2._base.AbstractSmartQueryTests;
import com.braintribe.model.processing.query.smart.test2._common.SmartModelTestSetup;
import com.braintribe.model.processing.query.smart.test2.shared.SharedSmartSetup;
import com.braintribe.model.processing.query.smart.test2.shared.model.shared.SharedSource;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.testing.junit.assertions.assertj.core.api.Assertions;

/**
 * @author peter.gazdik
 */
public class Shared_QueryTest extends AbstractSmartQueryTests {

	@Override
	protected SmartModelTestSetup getSmartModelTestSetup() {
		return SharedSmartSetup.SHARED_SETUP;
	}

	@Test
	public void singleSimpleEntity() throws Exception {
		SharedSource ss1 = bA.make(SharedSource.T).set(uuid, "s1").done();
		SharedSource ss2 = bB.make(SharedSource.T).set(uuid, "s2").done();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("s")
				.from(SharedSource.T, "s")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		SharedSource sss1 = acquireSmart(ss1);
		SharedSource sss2 = acquireSmart(ss2);
		
		Assertions.assertThat(sss1).isNotSameAs(sss2);
		
		assertResultContains(sss1);
		assertResultContains(sss2);
		assertNoMoreResults();
	}

}
