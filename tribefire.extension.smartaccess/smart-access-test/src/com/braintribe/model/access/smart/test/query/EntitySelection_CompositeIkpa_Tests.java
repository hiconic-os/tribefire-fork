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

import com.braintribe.model.processing.query.smart.test.model.accessA.CompositeIkpaEntityA;
import com.braintribe.model.processing.query.smart.test.model.accessA.PersonA;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.processing.smart.query.planner.EntitySelection_CompositeIkpa_PlannerTests;
import com.braintribe.model.query.SelectQuery;

/**
 * @see EntitySelection_CompositeIkpa_PlannerTests
 */
public class EntitySelection_CompositeIkpa_Tests extends AbstractSmartQueryTests {

	/** @see EntitySelection_CompositeIkpa_PlannerTests#selectCompositeIkpaEntity() */
	@Test
	public void selectCompositeIkpaEntity() {
		PersonA p1 = bA.personA("p1").create();
		PersonA p2 = bA.personA("p2").create();
		PersonA p3 = bA.personA("p3").create();

		CompositeIkpaEntityA c1 = bA.compositeIkpaEntityA().personData(p1).description("d1").create();
		CompositeIkpaEntityA c2 = bA.compositeIkpaEntityA().personData(p2).description("d2").create();
		CompositeIkpaEntityA c3 = bA.compositeIkpaEntityA().personData(p3).description("d3").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.select("p", "nameA")
				.select("p", "compositeIkpaEntity")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("p1", smartCompositeIkpa(c1));
		assertResultContains("p2", smartCompositeIkpa(c2));
		assertResultContains("p3", smartCompositeIkpa(c3));
		assertNoMoreResults();
	}

	/** @see EntitySelection_CompositeIkpa_PlannerTests#conditionOnCompositeIkpaEntity_ExternalDqj() */
	@Test
	public void conditionOnCompositeIkpaEntity() {
		PersonA p1 = bA.personA("p1").create();
		PersonA p2 = bA.personA("p2").create();
		PersonA p3 = bA.personA("p3").create();

		CompositeIkpaEntityA c;
		c = bA.compositeIkpaEntityA().personData(p1).description("d1").create();
		c = bA.compositeIkpaEntityA().personData(p2).description("d2").create();
		c = bA.compositeIkpaEntityA().personData(p3).description("d3").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.select("p", "nameA")
				.where()
					.property("p", "compositeIkpaEntity").eq().entity(smartCompositeIkpa(c))
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("p3");
		assertNoMoreResults();
	}

}
