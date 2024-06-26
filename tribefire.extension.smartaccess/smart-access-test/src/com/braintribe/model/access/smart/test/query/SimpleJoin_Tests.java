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
import org.junit.experimental.categories.Category;

import com.braintribe.model.processing.query.smart.test.model.accessA.CompanyA;
import com.braintribe.model.processing.query.smart.test.model.accessA.PersonA;
import com.braintribe.model.processing.query.smart.test.model.accessB.PersonB;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.processing.smart.query.planner.SimpleJoin_PlannerTests;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.testing.category.KnownIssue;

/**
 * 
 */
public class SimpleJoin_Tests extends AbstractSmartQueryTests {

	/** @see SimpleJoin_PlannerTests#simpleJoinSelectPrimitiveProps() */
	@Test
	public void simpleJoinSelectPrimitiveProps() {
		bA.personA("p1").companyA(bA.company("c1").create()).create();
		bA.personA("p2").companyA(bA.company("c2").create()).create();
		bA.personA("p3").companyA(bA.company("c3").create()).create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.join("p", "companyA", "c")
				.select("p", "nameA")
				.select("c", "nameA")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("p1", "c1");
		assertResultContains("p2", "c2");
		assertResultContains("p3", "c3");
		assertNoMoreResults();
	}

	/** @see SimpleJoin_PlannerTests#simpleJoinSelectEntities() */
	@Test
	public void simpleJoinSelectEntities() {
		CompanyA c1 = bA.company("c1").create();
		CompanyA c2 = bA.company("c2").create();
		CompanyA c3 = bA.company("c3").create();

		PersonA p1 = bA.personA("p1").companyA(c1).create();
		PersonA p2 = bA.personA("p2").companyA(c2).create();
		PersonA p3 = bA.personA("p3").companyA(c3).create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.join("p", "companyA", "c")
				.select("p")
				.select("c")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(smartPerson(p1), smartCompany(c1));
		assertResultContains(smartPerson(p2), smartCompany(c2));
		assertResultContains(smartPerson(p3), smartCompany(c3));
		assertNoMoreResults();
	}

	/** @see SimpleJoin_PlannerTests#simpleKeyPropertyJoinSelectPrimitiveProps() */
	@Test
	public void simpleKeyPropertyJoinSelectPrimitiveProps() {
		bB.personB("pB1").create();
		bB.personB("pB2").create();

		bA.personA("pA1").parentB("pB1").create();
		bA.personA("pA2").parentB("pB2").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "pa")
					.join("pa", "smartParentB", "pb")
				.select("pa", "nameA")
				.select("pb", "nameB")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("pA1", "pB1");
		assertResultContains("pA2", "pB2");
		assertNoMoreResults();
	}

	/** @see SimpleJoin_PlannerTests#simpleKeyPropertyJoinSelectEntities() */
	@Test
	public void simpleKeyPropertyJoinSelectEntities() {
		PersonB pb1 = bB.personB("pB1").create();
		PersonB pb2 = bB.personB("pB2").create();

		PersonA pa1 = bA.personA("pA1").parentB("pB1").create();
		PersonA pa2 = bA.personA("pA2").parentB("pB2").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "pa")
				.join("pa", "smartParentB", "pb")
				.select("pa")
				.select("pb")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(smartPerson(pa1), smartPerson(pb1));
		assertResultContains(smartPerson(pa2), smartPerson(pb2));
		assertNoMoreResults();
	}

	/** @see SimpleJoin_PlannerTests#simpleJoinWithConditionOnJoined_SameAccess() */
	@Test
	public void simpleJoinWithConditionOnJoined_SameAccess() {
		CompanyA c1 = bA.company("c1").create();
		CompanyA c2 = bA.company("c2").create();
		CompanyA c3 = bA.company("c3").create();

		PersonA pa;
		pa = bA.personA("p1").companyA(c1).create();
		pa = bA.personA("p2").companyA(c2).create();
		pa = bA.personA("p3").companyA(c3).create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.join("p", "companyA", "c")
				.select("p")
				.select("c")
				.where()
					.property("c", "nameA").like("*3")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(smartPerson(pa), smartCompany(c3));
		assertNoMoreResults();
	}

	/** @see SimpleJoin_PlannerTests#simpleJoinWithConditionOnJoind_DifferentAccess() */
	@Test
	public void simpleJoinWithConditionOnJoind_DifferentAccess() {
		PersonB pb;
		pb = bB.personB("pB1").create();
		pb = bB.personB("pB2").create();

		PersonA pa;
		pa = bA.personA("pA1").parentB("pB1").create();
		pa = bA.personA("pA2").parentB("pB2").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "a")
				.join("a", "smartParentB", "b")
				.select("a")
				.select("b")
				.where()
					.property("b", "nameB").like("*2")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(smartPerson(pa), smartPerson(pb));
		assertNoMoreResults();
	}

	/** @see SimpleJoin_PlannerTests#EXPECTED_TO_FAIL_simpleJoinWithConditionOnJoind_DifferentAccess_IsNull() */
	@Test
	@Category(KnownIssue.class)
	public void EXPECTED_TO_FAIL_simpleJoinWithConditionOnJoind_DifferentAccess_IsNull() {
		bB.personB("pB1").create();

		bA.personA("p_with_B").parentB("pB1").create();
		bA.personA("p_without_B").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("pa")
				.from(SmartPersonA.class, "pa")
				.where()
					.property("pa", "smartParentB").eq(null)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("p_without_B");
		assertNoMoreResults();
	}

	/** @see SimpleJoin_PlannerTests#simpleJoinWithConditionOnJoind_DifferentAccess_IsNotNull() */
	@Test
	public void simpleJoinWithConditionOnJoind_DifferentAccess_IsNotNull() {
		bB.personB("pB1").create();

		bA.personA("p_with_B").parentB("pB1").create();
		bA.personA("p_without_B").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("pa", "nameA")
				.from(SmartPersonA.class, "pa")
				.where()
					.negation()
						.property("pa", "smartParentB").eq(null)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("p_with_B");
		assertNoMoreResults();
	}

}
