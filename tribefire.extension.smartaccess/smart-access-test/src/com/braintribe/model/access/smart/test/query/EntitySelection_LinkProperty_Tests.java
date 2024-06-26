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

import com.braintribe.model.processing.query.smart.test.model.accessA.PersonA;
import com.braintribe.model.processing.query.smart.test.model.accessB.ItemB;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartItem;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.processing.smart.query.planner.EntitySelection_LinkProperty_PlannerTests;
import com.braintribe.model.query.SelectQuery;

/**
 * @see EntitySelection_LinkProperty_PlannerTests
 */
public class EntitySelection_LinkProperty_Tests extends AbstractSmartQueryTests {

	private ItemB i1, i2, i3;
	private PersonA p1, p2, p3;
	private SmartItem si1, si2, si3;

	/** @see EntitySelection_LinkProperty_PlannerTests#simpleEntityQuery() */
	@Test
	public void simpleEntityQuery() {
		prepareData();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.select("p", "linkItem")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(si1);
		assertResultContains(si2);
		assertResultContains(si3);
		assertResultContains(null);
		assertNoMoreResults();
	}

	/** @see EntitySelection_LinkProperty_PlannerTests#queryWithDelegatableEntityCondition() */
	@Test
	public void queryWithDelegatableEntityCondition() {
		prepareData();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.select("p", "nameA")
				.where()
					.property("p", "linkItem").eq().entity(si2)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(p2.getNameA());
		assertNoMoreResults();
	}

	/**
	 * Each of the three persons contains items with numerical value greater or equal than their own.
	 */
	private void prepareData() {
		i1 = bB.item("i1").create();
		i2 = bB.item("i2").create();
		i3 = bB.item("i3").create();

		p1 = bA.personA("p1").create();
		p2 = bA.personA("p2").create();
		p3 = bA.personA("p3").create();

		si1 = smartItem(i1);
		si2 = smartItem(i2);
		si3 = smartItem(i3);

		bB.personItemLink(p1, i1);
		bB.personItemLink(p2, i2);
		bB.personItemLink(p3, i3);

		// #######################################
		// ## . . . . Testing left join . . . . ##
		// #######################################

		/* This is the entity for which we want the resolved link item to be null */
		bA.personA("noValuePerson").create();

		/* This tests the problem that if we create dqj condition with null value (from ItemB i where i.nameB = null), this entity will be
		 * returned. But in such case we want to omit the condition and simply let it be null due to being a left join. */
		bB.item(null).create();
	}

}
