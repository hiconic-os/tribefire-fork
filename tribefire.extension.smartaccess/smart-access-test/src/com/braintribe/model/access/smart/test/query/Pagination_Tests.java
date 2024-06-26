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

import static com.braintribe.model.access.smart.test.query.PropertyConversion_Tests.YEAR_IN_MILLIS;
import static com.braintribe.model.access.smart.test.query.PropertyConversion_Tests.convert;

import java.util.Date;

import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.accessB.PersonB;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonB;
import com.braintribe.model.processing.smart.query.planner.Pagination_PlannerTests;
import com.braintribe.model.query.SelectQuery;

/**
 * 
 */
public class Pagination_Tests extends AbstractSmartQueryTests {

	// #######################################
	// ## . . . . . Plain Sorting . . . . . ##
	// #######################################

	/** @see Pagination_PlannerTests#bulksDueToPagination() */
	@Test
	public void bulksDueToPagination() {
		bA.personA("p").companyNameA("cA").create();
		bA.personA("p").companyNameA("cA").create();
		bA.personA("p").companyNameA("cB").create();
		bA.personA("p").companyNameA("cB").create();

		bA.company("cA").create();
		bA.company("cB").create();

		// @formatter:off
		SelectQuery selectQuery = query()
				.from(SmartPersonA.class, "p")
					.join("p", "keyCompanyA", "c")
				.select().property("p", "nameA")
				.limit(2)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("p");
		assertResultContains("p");
		assertNoMoreResults();
	}

	/** @see Pagination_PlannerTests#bulksDueToPagination_WithSimpleCollection() */
	@Test
	public void bulksDueToPagination_WithSimpleCollection() {
		bA.personA("p").nickNamesA("n", "n").companyNameA("cA").create();
		bA.personA("p").nickNamesA("n", "n").companyNameA("cB").create();

		bA.company("cA").create();
		bA.company("cB").create();

		// @formatter:off
		SelectQuery selectQuery = query()
				.from(SmartPersonA.class, "p")
					.join("p", "nickNamesSetA", "n")
					.join("p", "keyCompanyA", "c")
				.select().property("p", "nameA")
				.select().entity("n")
				.where()
					// forcing Person to be first (materialized side of DQJ)
					.property("p", "nameA").like("p*")
				.limit(10)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("p", "n");
		assertResultContains("p", "n");
		assertNoMoreResults();
	}

	/** @see Pagination_PlannerTests#bulksDueToPagination_WhenConditions() */
	@Test
	public void bulksDueToPagination_WhenConditions() {
		bA.personA("p").companyNameA("cA").create();
		bA.personA("p").companyNameA("cA").create();
		bA.personA("p").companyNameA("cB").create();
		bA.personA("p").companyNameA("cB").create();

		bA.company("cA").create();
		bA.company("cB").create();

		// @formatter:off
		SelectQuery selectQuery = query()
				.from(SmartPersonA.class, "p")
					.join("p", "keyCompanyA", "c")
				.select().property("p", "nameA")
				.where()
					.conjunction()
						.property("p", "nameA").like("p*")
						.property("c", "nameA").like("c*")
					.close()
				.limit(2)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("p");
		assertResultContains("p");
		assertNoMoreResults();
	}

	/** @see Pagination_PlannerTests#bulksDueToPagination_WhenOrdered() */
	@Test
	public void bulksDueToPagination_WhenOrdered() {
		bA.personA("p1").companyNameA("cA").create();
		bA.personA("p2").companyNameA("cA").create();
		bA.personA("p3").companyNameA("cB").create();
		bA.personA("p4").companyNameA("cB").create();

		bA.company("cA").create();
		bA.company("cB").create();

		// @formatter:off
		SelectQuery selectQuery = query()
				.from(SmartPersonA.class, "p")
					.join("p", "keyCompanyA", "c")
				.select().property("p", "nameA")
				.orderBy().property("p", "nameA")
				.limit(2)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("p1");
		assertResultContains("p2");
		assertNoMoreResults();
	}

	/** @see Pagination_PlannerTests#noBulksDueToTheNeedOfBeingSorted() */
	@Test
	public void noBulksDueToTheNeedOfBeingSorted() {
		bA.personA("p1").companyNameA("cA").create();
		bA.personA("p2").companyNameA("cA").create();
		bA.personA("p3").companyNameA("cB").create();
		bA.personA("p4").companyNameA("cB").create();

		bA.company("cA").create();
		bA.company("cB").create();

		// @formatter:off
		SelectQuery selectQuery = query()
				.from(SmartPersonA.class, "p")
					.join("p", "keyCompanyA", "c")
				.select().property("p", "nameA")
				.where()
					.property("c", "nameA").like("c*")
				.orderBy().property("p", "nameA")
				.paging(2, 1)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("p2");
		assertResultContains("p3");
		assertNoMoreResults();
	}

	/** @see Pagination_PlannerTests#bulksDueToNonDelegatableCondition() */
	@SuppressWarnings("unused")
	@Test
	public void bulksDueToNonDelegatableCondition() {
		Date d0 = new Date(0L * YEAR_IN_MILLIS);
		Date d1 = new Date(10 * YEAR_IN_MILLIS);
		Date d2 = new Date(20 * YEAR_IN_MILLIS);
		Date d3 = new Date(30 * YEAR_IN_MILLIS);

		PersonB p1 = bB.personB("person1").birthDate(convert(d1)).create();
		PersonB p2 = bB.personB("person2").birthDate(convert(d2)).create();
		PersonB p3 = bB.personB("person3").birthDate(convert(d3)).create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonB.class, "p")
				.select("p")
				.where()
					.property("p", "convertedBirthDate").ge(d0)
				.orderBy().property("p", "id")
				.paging(2, 1)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(smartPerson(p2));
		assertResultContains(smartPerson(p3));
		assertNoMoreResults();
	}

}
