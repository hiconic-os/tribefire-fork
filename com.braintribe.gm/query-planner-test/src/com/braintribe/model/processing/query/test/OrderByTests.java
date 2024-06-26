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
package com.braintribe.model.processing.query.test;

import org.junit.Test;

import com.braintribe.model.processing.query.test.model.Owner;
import com.braintribe.model.processing.query.test.model.Person;
import com.braintribe.model.query.OrderingDirection;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.queryplan.set.OrderedSet;
import com.braintribe.model.queryplan.set.PaginatedSet;
import com.braintribe.model.queryplan.set.Projection;
import com.braintribe.model.queryplan.set.SortCriterion;
import com.braintribe.model.queryplan.set.join.JoinKind;

/**
 * 
 */
public class OrderByTests extends AbstractQueryPlannerTests {

	@Test
	public void simpleDefaultSort() {
		// @formatter:off
		SelectQuery selectQuery = query()
				.from(Person.T, "p")
				.orderBy().property("p", "name")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T).whereOperand()
				.hasType(OrderedSet.T)
					.whereOperand().isSourceSet_(Person.T)
					.whereProperty("sortCriteria").isListWithSize(1)
						.whereElementAt(0)
							.hasType(SortCriterion.T)
							.whereProperty("descending").isFalse_()
							.whereValue().isValueProperty_("name")
		;
		// @formatter:on
	}

	@Test
	public void simpleAscendingSort() {
		// @formatter:off
		SelectQuery selectQuery = query()
				.from(Person.T, "p")
				.orderBy(OrderingDirection.ascending).property("p", "name")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T).whereOperand()
				.hasType(OrderedSet.T)
					.whereOperand().isSourceSet_(Person.T)
					.whereProperty("sortCriteria")
						.whereElementAt(0)
							.hasType(SortCriterion.T)
							.whereProperty("descending").isFalse_()
							.whereValue().isValueProperty_("name")
		;
		// @formatter:on
	}

	@Test
	public void simpleDescendingSort() {
		// @formatter:off
		SelectQuery selectQuery = query()
				.from(Person.T, "p")
				.orderBy(OrderingDirection.descending).property("p", "name")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T).whereOperand()
				.hasType(OrderedSet.T)
					.whereOperand().isSourceSet_(Person.T)
					.whereProperty("sortCriteria")
						.whereElementAt(0)
							.hasType(SortCriterion.T)
							.whereProperty("descending").isTrue_()
							.whereValue().isValueProperty_("name")
		;
		// @formatter:on
	}

	@Test
	public void compoundProperty() {
		// @formatter:off
		SelectQuery selectQuery = query()
				.from(Owner.class, "p")
				.orderBy().property("p", "company.name")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T).whereOperand()
				.hasType(OrderedSet.T)
				.whereOperand().isSourceSet_(Owner.T)
				.whereProperty("sortCriteria")
					.whereElementAt(0)
						.hasType(SortCriterion.T)
						.whereValue().isValueProperty_("company.name")
		;
		// @formatter:on
	}

	@Test
	public void multipleOrderBys() {
		// @formatter:off
		SelectQuery selectQuery = query()
				.from(Owner.T, "p")
				.orderByCascade()
					.dir(OrderingDirection.descending).property("p", "company.name")
					.dir(OrderingDirection.ascending).value(45) // this must be ignored 
					.dir(OrderingDirection.ascending).property("p", "name")
				.close()
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T).whereOperand()
				.hasType(OrderedSet.T)
					.whereOperand().isSourceSet_(Owner.T)
					.whereProperty("sortCriteria")
						.whereElementAt(0)
							.hasType(SortCriterion.T)
							.whereProperty("descending").isTrue_()
							.whereValue().isValueProperty_("company.name")
						.close()
						.whereElementAt(1)
							.hasType(SortCriterion.T)
							.whereProperty("descending").isFalse_()
							.whereValue().isValueProperty_("name")
		;
		// @formatter:on
	}

	@Test
	public void orderByWithPagination() {
		// @formatter:off
		SelectQuery selectQuery = query()
				.from(Person.T, "p")
				.orderBy().property("p", "name")
				.paging(2, 1)
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(PaginatedSet.T).whereOperand()
				.hasType(Projection.T)
					.whereOperand().hasType_(OrderedSet.T)
				.close()
			.whereProperty("limit").is_(2)
			.whereProperty("offset").is_(1)
		;
		// @formatter:on
	}

	@Test
	public void orderByNonSelected() {
		// @formatter:off
		SelectQuery selectQuery = query()
				.select("p", "name")
				.from(Person.T, "p")
					.join("p", "company", "c")
				.orderBy().property("c", "id")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T).whereOperand()
				.hasType(OrderedSet.T)
				.whereOperand()
					.isEntityJoin(JoinKind.inner)
						.whereOperand().isSourceSet_(Person.T)
					.close()
				.whereProperty("sortCriteria")
					.whereElementAt(0)
						.hasType(SortCriterion.T)
						.whereProperty("descending").isFalse_()
						.whereValue().isValueProperty_("id")
		;
		// @formatter:on
	}
}
