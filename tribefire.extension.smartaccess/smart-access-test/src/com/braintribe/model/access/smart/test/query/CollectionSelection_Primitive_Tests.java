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

import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.processing.smart.query.planner.CollectionSelection_Primitive_PlannerTests;
import com.braintribe.model.query.JoinType;
import com.braintribe.model.query.SelectQuery;

/**
 * 
 */
public class CollectionSelection_Primitive_Tests extends AbstractSmartQueryTests {

	/** @see CollectionSelection_Primitive_PlannerTests#simpleSetQuery() */
	@Test
	public void simpleEmptySetQuery() {
		bA.personA("p").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.select("p", "nickNamesSetA")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(null);
		assertNoMoreResults();
	}

	/** @see CollectionSelection_Primitive_PlannerTests#simpleSetQuery() */
	@Test
	public void innerJoinEmptySetQuery() {
		bA.personA("a").create();
		bA.personA("b").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.join("p", "nickNamesSetA", "n", JoinType.inner)
				.select("n")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertNoMoreResults();
	}

	/** @see CollectionSelection_Primitive_PlannerTests#simpleSetQuery() */
	@Test
	public void simpleSetQuery() {
		bA.personA("p").nickNamesA("p", "pp", "ppp").create();
		bA.personA("q").nickNamesA("q", "qq", "qqq").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.select("p", "nickNamesSetA")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("p");
		assertResultContains("pp");
		assertResultContains("ppp");
		assertResultContains("q");
		assertResultContains("qq");
		assertResultContains("qqq");
		assertNoMoreResults();
	}

	@Test
	public void collectionIsLeftJoined() {
		bA.personA("p").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.select("p", "nameA")
				.select("p", "nickNamesSetA")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("p", (Object) null);
		assertNoMoreResults();
	}

	@Test
	public void collectionIsInnerJoinedExplicitly() {
		bA.personA("p").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.join("p", "nickNamesSetA", "n", JoinType.inner)
				.select("p", "nameA")
				.select("n")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertNoMoreResults();
	}

	/** @see CollectionSelection_Primitive_PlannerTests#simpleListWithIndexQuery() */
	@Test
	public void simpleListWithIndexQuery() {
		bA.personA("p").nickNamesA("p", "pp", "ppp").create();
		bA.personA("q").nickNamesA("q", "qq", "qqq").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.join("p", "nickNamesListA", "n")
					.select().listIndex("n")
					.select("n")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(0, "p");
		assertResultContains(1, "pp");
		assertResultContains(2, "ppp");
		assertResultContains(0, "q");
		assertResultContains(1, "qq");
		assertResultContains(2, "qqq");
		assertNoMoreResults();
	}

	/** @see CollectionSelection_Primitive_PlannerTests#simpleMapWithKeyQuery() */
	@Test
	public void simpleMapWithKeyQuery() {
		bA.personA("p").nickNamesA("p", "pp", "ppp").create();
		bA.personA("q").nickNamesA("q", "qq", "qqq").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.class, "p")
				.join("p", "nickNamesMapA", "n")
					.select().mapKey("n")
					.select("n")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		// see builder on how the nickNamesMapA is constructed
		assertResultContains(2, "p");
		assertResultContains(4, "pp");
		assertResultContains(6, "ppp");
		assertResultContains(2, "q");
		assertResultContains(4, "qq");
		assertResultContains(6, "qqq");
		assertNoMoreResults();
	}
}
