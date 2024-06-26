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

import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.accessB.ItemB;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.processing.smart.query.planner.EntitySelection_Dqj_PlannerTests;
import com.braintribe.model.query.SelectQuery;

/**
 * @author peter.gazdik
 */
public class EntitySelection_Dqj_Tests extends AbstractSmartQueryTests {

	/** @see EntitySelection_Dqj_PlannerTests#simpleInverseKeyPropertyJoin() */
	@Test
	public void simpleInverseKeyPropertyJoin() {
		bA.personA("pA1").create();
		bA.personA("pA2").create();

		ItemB i1 = bB.item("i1").singleOwnerName("pA1").create();
		ItemB i2 = bB.item("i2").singleOwnerName("pA1").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.select("p", "inverseKeyItem")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(smartItem(i1));
		assertResultContains(smartItem(i2));
	}

}
