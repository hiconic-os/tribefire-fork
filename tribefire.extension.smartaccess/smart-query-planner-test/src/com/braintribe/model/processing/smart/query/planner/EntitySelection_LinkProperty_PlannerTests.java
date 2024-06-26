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

import static com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup.accessIdB;

import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.accessA.PersonA;
import com.braintribe.model.processing.query.smart.test.model.accessB.ItemB;
import com.braintribe.model.processing.query.smart.test.model.accessB.PersonItemLink;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartItem;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.processing.smart.query.planner.base.AbstractSmartQueryPlannerTests;
import com.braintribe.model.query.Operator;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.queryplan.set.Projection;
import com.braintribe.model.queryplan.value.QueryFunctionValue;
import com.braintribe.model.smartqueryplan.set.DelegateQueryJoin;

/**
 * 
 */
public class EntitySelection_LinkProperty_PlannerTests extends AbstractSmartQueryPlannerTests {

	@Test
	public void simpleEntityQuery() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.T, "p")
				.select("p", "linkItem")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T)
			.whereProperty("operand")
				.isDelegateQueryJoin_Left()
				.whereProperty("materializedSet")
					.isDelegateQueryJoin_Left()
					.whereProperty("materializedSet")
						.isDelegateQuerySet("accessA")
						.whereDelegateQuery()
							.whereSelection(1)
								.whereElementAt(0).isPropertyOperand("nameA").whereSource().isFrom(PersonA.T)
						.endQuery()
					.close()
					.whereProperty("querySet")
						.isDelegateQuerySet("accessB")
						.whereDelegateQuery()
							.whereSelection(2)
								.whereElementAt(0).isPropertyOperand("itemName").whereSource().isFrom(PersonItemLink.T).close(2)
								.whereElementAt(1).isPropertyOperand("personName").whereSource().isFrom(PersonItemLink.T).close(2)
						.endQuery()
					.close()
				.close()
				.whereProperty("querySet")
					.isDelegateQuerySet("accessB")
					.whereDelegateQuery()
						.whereSelection(5)
							.whereElementAt(1).isPropertyOperand("id").whereSource().isFrom(ItemB.T)
					.endQuery()
				.close()
			.close()
			.whereProperty("values").isListWithSize(1)
				.whereElementAt(0).hasType(QueryFunctionValue.T).whereProperty("queryFunction").isAssembleEntity(SmartItem.T)
		;
		// @formatter:on
	}

	@Test
	public void queryWithDelegatableEntityCondition() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.T, "p")
				.select("p", "nameA")
				.where()
					.property("p", "linkItem").eq().entity(item(99L))
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T)
			.whereProperty("operand")
				.hasType(DelegateQueryJoin.T)
				.whereProperty("materializedSet")
					.hasType(DelegateQueryJoin.T)
					.whereProperty("materializedSet")
						.isDelegateQuerySet("accessB")
						.whereDelegateQuery()
							.whereSelection(1)
								.whereElementAt(0).isPropertyOperand("nameB").whereSource().isFrom(ItemB.T)
							.whereCondition().isConjunction(1)
								.whereElementAt(0).isValueComparison(Operator.equal)
									.whereProperty("leftOperand").isSourceOnlyPropertyOperand().close()
									.whereProperty("rightOperand").isReference_(ItemB.T, 99L, accessIdB)
						.endQuery()
					.close()
					.whereProperty("querySet")
						.isDelegateQuerySet("accessB")
						.whereDelegateQuery()
							.whereSelection(2)
								.whereElementAt(0).isPropertyOperand("itemName").whereSource().isFrom(PersonItemLink.T).close(2)
								.whereElementAt(1).isPropertyOperand("personName").whereSource().isFrom(PersonItemLink.T).close(2)
						.endQuery()
					.close()
				.close()
				.whereProperty("querySet")
					.isDelegateQuerySet("accessA")
					.whereDelegateQuery()
						.whereSelection(1)
							.whereElementAt(0).isPropertyOperand("nameA").whereSource().isFrom(PersonA.T)
					.endQuery()
				.close()
			.close()
			.whereProperty("values").isListWithSize(1)
				.whereElementAt(0).isTupleComponent_(1)
		;
		// @formatter:on
	}

}
