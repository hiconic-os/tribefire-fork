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
package com.braintribe.model.access.smart.test.query;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.accessA.PersonA;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.query.SelectQuery;

/**
 * Note the test-cases are copied from SmoodTests (from a class with exact same name -
 * ConstantConditionSelectQueryTests).
 */
public class ConstantConditionSelect_Tests extends AbstractSmartQueryTests {
	private static final String MATCH_ALL = "";

	private SmartPersonA p;

	@Before
	public void initPerson() {
		PersonA pA = bA.personA("john smith").create();
		bB.personB("john smith").create();

		p = smartPerson(pA);
	}

	@Test
	public void fulltextMatchingAll_Negated() throws Exception {
		// @formatter:off
		SelectQuery selectQuery = query()
				.from(SmartPersonA.class, "p").where().negation().fullText("p", MATCH_ALL)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertNoMoreResults();
	}

	@Test
	public void matchingAll_1_eq_1() throws Exception {
		// @formatter:off
		SelectQuery selectQuery = query()
				.from(SmartPersonA.class, "p").where()
					.value(1).eq().value(1)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(p);
	}

	@Test
	public void matchingNothing_1_eq_0() throws Exception {
		// @formatter:off
		SelectQuery selectQuery = query()
				.from(SmartPersonA.class, "p")
				.where()
					.value(1).eq().value(0)
				.orderBy().property("p", "companyNameA")
				.limit(5)
				.paging(10, 2)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertNoMoreResults();
	}

}
