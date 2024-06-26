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

import com.braintribe.model.processing.query.smart.test.model.accessA.CarA;
import com.braintribe.model.processing.query.smart.test.model.accessA.FlyingCarA;
import com.braintribe.model.processing.query.smart.test.model.smart.Car;
import com.braintribe.model.processing.query.smart.test.model.smart.FlyingCar;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.processing.smart.query.planner.base.AbstractSmartQueryPlannerTests;
import com.braintribe.model.query.Operator;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.query.functions.EntitySignature;
import com.braintribe.model.queryplan.set.Projection;
import com.braintribe.model.queryplan.value.ConstantValue;
import com.braintribe.model.queryplan.value.QueryFunctionValue;
import com.braintribe.model.smartqueryplan.set.DelegateQueryJoin;

public class QueryFunctions_PlannerTests extends AbstractSmartQueryPlannerTests {

	// #########################################
	// ## . . . . . EntitySignature . . . . . ##
	// #########################################

	@Test
	public void signature_Final() {
		// @formatter:off
		SelectQuery selectQuery = query()
				.from(SmartPersonA.T, "p")
				.select().entitySignature().entity("p")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
		.hasType(Projection.T)
			.whereProperty("operand")
				.isDelegateQuerySet("accessA")
				.whereDelegateQuery()
					.whereSelection(1)
						.whereElementAt(0).hasType(EntitySignature.T)
				.endQuery()
				.whereProperty("scalarMappings").isListWithSize(1).close()
			.close()
			.whereProperty("values").isListWithSize(1)
				.whereElementAt(0).hasType(ConstantValue.T)
					.whereProperty("value").is_(SmartPersonA.class.getName())
		;
		// @formatter:on
	}

	@Test
	public void signature_Hierarchy() {
		// @formatter:off
		SelectQuery selectQuery = query()
				.from(Car.T, "c")
				.select().entitySignature().entity("c")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
		.hasType(Projection.T)
			.whereProperty("operand")
				.isDelegateQuerySet("accessA")
				.whereDelegateQuery()
					.whereSelection(1)
						.whereElementAt(0).hasType(EntitySignature.T)
				.endQuery()
				.whereProperty("scalarMappings").isListWithSize(1).close()
			.close()
			.whereProperty("values").isListWithSize(1)
				.whereElementAt(0).hasType(QueryFunctionValue.T)
					.whereProperty("queryFunction").hasType(EntitySignature.T).close()
					.whereProperty("operandMappings").isMapWithSize(1)
		;
		// @formatter:on
	}

	@Test
	public void conditionOnSourceType() {
		// @formatter:off
		SelectQuery selectQuery = query()
				.from(Car.T, "c")
				.select().entity("c")
				.where()
					.entitySignature("c").eq(FlyingCar.class.getName())
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan(8)
			.hasType(Projection.T).whereProperty("operand")
				.hasType(DelegateQueryJoin.T)
				.whereProperty("materializedSet")
					.isDelegateQuerySet("accessA")
					.whereDelegateQuery()
						.whereFroms(1).whereElementAt(0).isFrom(CarA.T)
						.whereCondition().isConjunction(1)
								.whereElementAt(0).isValueComparison(Operator.equal)
									.whereProperty("leftOperand").hasType(EntitySignature.T).close()
									.whereProperty("rightOperand").is_(FlyingCarA.class.getName())
					.endQuery()
				.close()
				.whereProperty("querySet")
					.isDelegateQuerySet("accessA")
					.whereDelegateQuery()
						.whereFroms(1).whereElementAt(0).isFrom(FlyingCarA.T)
					.endQuery()
				.close()
			.close()
			.whereProperty("values").isListWithSize(1)
		;
		// @formatter:on
	}

	// @SuppressWarnings("unused")
	// @Test(expected = Exception.class)
	// public void selectingLocalizedValue() {
//		// @formatter:off
//		SelectQuery selectQuery = query()
//				.from(SmartItem.T, "i")
//				.select("i").select().localize("pt").property("i", "localizedNameB")
//				.done();
//		// @formatter:on
	//
	// evaluate(selectQuery);
	//
	// }

	// TODO DELEGATE AsString function
	@Test
	public void propertyAsString() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.T, "p")
				.select().asString().property("p", "id")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
		.hasType(Projection.T)
			.whereProperty("operand")
				.isDelegateQuerySet("accessA")
				.whereDelegateQuery()
					.whereSelection(1)
						.whereElementAt(0).isPropertyOperand("id")
				.endQuery()
				.whereProperty("scalarMappings").isListWithSize(1).close()
			.close()
			.whereProperty("values").isListWithSize(1)
				.whereElementAt(0)
		;
		// @formatter:on
	}

}
