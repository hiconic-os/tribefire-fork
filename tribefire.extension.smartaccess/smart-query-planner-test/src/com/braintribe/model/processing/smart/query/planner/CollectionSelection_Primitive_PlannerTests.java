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
package com.braintribe.model.processing.smart.query.planner;

import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.smart.SmartItem;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.processing.smart.query.planner.base.AbstractSmartQueryPlannerTests;
import com.braintribe.model.query.JoinType;
import com.braintribe.model.query.PropertyOperand;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.queryplan.set.Projection;
import com.braintribe.model.queryplan.value.TupleComponent;

/**
 * 
 */
public class CollectionSelection_Primitive_PlannerTests extends AbstractSmartQueryPlannerTests {

	@Test
	public void simpleSetQuery() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.T, "p")
				.select("p", "nickNamesSetA")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
		.hasType(Projection.T)
			.whereProperty("operand")
				.isDelegateQuerySet("accessA")
				.whereProperty("scalarMappings").isListWithSize(1).close()
				.whereDelegateQuery()
					.whereSelection(1)
						.whereElementAt(0).isSourceOnlyPropertyOperand().whereSource().isJoin("nickNamesSetA", JoinType.left)
				.endQuery()
			.close()
			.whereProperty("values").isListWithSize(1)
				.whereElementAt(0).hasType(TupleComponent.T)
		;
		// @formatter:on
	}

	@Test
	public void simpleListWithIndexQuery() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.T, "p")
					.join("p", "nickNamesListA", "n")
				.select().listIndex("n")
				.select("n")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
		.hasType(Projection.T)
			.whereProperty("operand")
				.isDelegateQuerySet("accessA")
				.whereProperty("scalarMappings").isListWithSize(2).close()
			.close()
			.whereProperty("values").isListWithSize(2)
				.whereElementAt(0).isTupleComponent_(1)
				.whereElementAt(1).isTupleComponent_(0)
		;
		// @formatter:on
	}

	@Test
	public void simpleMapWithKeyQuery() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.T, "p")
					.join("p", "nickNamesMapA", "n")
				.select().mapKey("n")
				.select("n")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
		.hasType(Projection.T)
			.whereProperty("operand")
				.isDelegateQuerySet("accessA")
				.whereProperty("scalarMappings").isListWithSize(2).close()
			.close()
			.whereProperty("values").isListWithSize(2)
				.whereElementAt(0).isTupleComponent_(1)
				.whereElementAt(1).isTupleComponent_(0)
		;
		// @formatter:on
	}

	@Test
	public void queryWithDelegatableSetCondition() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.T, "p")
				.select("p", "nameA")
				.where()
					.value("pp").in().property("p", "nickNamesSetA")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
		.hasType(Projection.T)
			.whereProperty("operand")
				.isDelegateQuerySet("accessA")
				.whereProperty("scalarMappings").isListWithSize(1).close()
				.whereDelegateQuery()
					.whereSelection(1)
						.whereElementAt(0).hasType(PropertyOperand.T).whereProperty("propertyName").is_("nameA")
				.endQuery()
			.close()
			.whereProperty("values").isListWithSize(1)
				.whereElementAt(0).hasType(TupleComponent.T)
		;
		// @formatter:on
	}

	@Test
	public void setQueryWithDelegatableSetCondition() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.T, "p")
				.select("p", "nameA")
				.select("p", "nickNamesSetA")
				.where()
					.value("pp").in().property("p", "nickNamesSetA")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
		.hasType(Projection.T)
			.whereProperty("operand")
				.isDelegateQuerySet("accessA")
				.whereProperty("scalarMappings").isListWithSize(2).close()
			.close()
			.whereProperty("values").isListWithSize(2)
				.whereElementAt(0).hasType(TupleComponent.T)
		;
		// @formatter:on
	}

	@Test
	public void enumSet() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartItem.T, "i")
				.select("i", "nameB")
				.select("i", "itemTypes")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
		.hasType(Projection.T)
			.whereProperty("operand")
				.isDelegateQuerySet("accessB")
				.whereDelegateQuery()
					.whereSelection(2)
						.whereElementAt(0).isPropertyOperand("nameB").close()
						.whereElementAt(1).castedToString().isSourceOnlyPropertyOperand().whereSource().isJoin("itemTypes")
				.endQuery()
				.whereProperty("scalarMappings").isListWithSize(2).close()
			.close()
		;
		// @formatter:on
	}

	@Test
	public void enumMapKey() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartItem.T, "i")
					.join("i", "itemTypeWeights", "w")
				.select("i", "nameB")
				.select().mapKey("w")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
		.hasType(Projection.T)
			.whereProperty("operand")
				.isDelegateQuerySet("accessB")
				.whereDelegateQuery()
					/* TODO fix - retrieving map value even though we only need key -> this will not be 3 selections, but just 2 */
					.whereSelection(3)
						.whereElementAt(0).isPropertyOperand("nameB").close()
						.whereElementAt(2).castedToString().isMapKeyOnJoin("itemTypeWeights")
				.endQuery()
				.whereProperty("scalarMappings").isListWithSize(3).close()
			.close()
		;
		// @formatter:on
	}
}
