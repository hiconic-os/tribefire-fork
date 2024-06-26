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

import static com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup.accessIdA;

import org.junit.Test;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.query.smart.test.model.accessA.special.ReaderA;
import com.braintribe.model.processing.query.smart.test.model.smart.special.SmartManualA;
import com.braintribe.model.processing.query.smart.test.model.smart.special.SmartReaderA;
import com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup;
import com.braintribe.model.processing.smart.query.planner.base.AbstractSmartQueryPlannerTests;
import com.braintribe.model.query.Operator;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.queryplan.set.Projection;
import com.braintribe.model.queryplan.value.QueryFunctionValue;

/**
 * Tests for the "weak" property type. This means we have some unmapped type A as type of our property, but we only want to allow it's sub
 * types (SubA) as values. We therefore use SubA in the mappings, and end up with query plans which only consider SubA, and not the entire A
 * hierarchy.
 */
public class UnmappedPropertyType_Weak_PlannerTests extends AbstractSmartQueryPlannerTests {

	// ########################################################
	// ## . . . . . IKPA defined on unmapped types . . . . . ##
	// ########################################################

	@Test
	public void selectUnmappedTypeProperty_Weak() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("r", "weakFavoriteManual")
				.from(SmartReaderA.T, "r")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T).whereProperty("operand")
				.isDelegateQueryJoin_Left()
				.whereProperty("materializedSet")
					.isDelegateQuerySet("accessA")
					.whereDelegateQuery()
						.whereSelection(1)
							.whereElementAt(0).isPropertyOperand("favoriteManualTitle").whereSource().isFrom(ReaderA.T).close(2)
					.endQuery()
				.close()
				.whereProperty("querySet")
					.isDelegateQuerySet("accessS")
					.whereDelegateQuery()
						.whereFroms(1).whereElementAt(0).isFrom(SmartManualA.T)
						.whereSelection(8)
							.whereElementAt(i=0).isPropertyOperand("author").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand(GenericEntity.globalId).whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("id").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("isbn").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("numberOfPages").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand(GenericEntity.partition).whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("smartManualString").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("title").whereSource().isFrom(SmartManualA.T).close(2)
					.endQuery()
				.close()
			.close()
			.whereProperty("values").isListWithSize(1)
				.whereElementAt(0).hasType(QueryFunctionValue.T).whereProperty("queryFunction").isAssembleEntity(SmartManualA.T).close(2)
		;
		// @formatter:on
	}

	@Test
	public void selectUnmappedTypeProperty_Weak_Set() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("r", "weakFavoriteManuals")
				.from(SmartReaderA.T, "r")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T).whereProperty("operand")
				.isDelegateQueryJoin_Left()
				.whereProperty("materializedSet")
					.isDelegateQuerySet("accessA")
					.whereDelegateQuery()
						.whereSelection(1)
							.whereElementAt(0).isSourceOnlyPropertyOperand().whereSource().isJoin("favoriteManualTitles").whereSource().isFrom(ReaderA.T)
					.endQuery()
				.close()
				.whereProperty("querySet")
					.isDelegateQuerySet("accessS")
					.whereDelegateQuery()
						.whereFroms(1).whereElementAt(0).isFrom(SmartManualA.T)
						.whereSelection(8)
							.whereElementAt(i=0).isPropertyOperand("author").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand(GenericEntity.globalId).whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("id").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("isbn").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("numberOfPages").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand(GenericEntity.partition).whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("smartManualString").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("title").whereSource().isFrom(SmartManualA.T).close(2)
					.endQuery()
				.close()
			.close()
			.whereProperty("values").isListWithSize(1)
				.whereElementAt(0).hasType(QueryFunctionValue.T).whereProperty("queryFunction").isAssembleEntity(SmartManualA.T).close(2)
		;
		// @formatter:on
	}

	@Test
	public void selectUnmappedTypeProperty_WeakInverse_Set() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("r", "weakInverseFavoriteManuals")
				.from(SmartReaderA.T, "r")
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T).whereProperty("operand")
				.isDelegateQueryJoin_Left()
				.whereProperty("materializedSet")
					.isDelegateQuerySet("accessA")
					.whereDelegateQuery()
						.whereSelection(1)
							.whereElementAt(0).isPropertyOperand("name").whereSource().isFrom(ReaderA.T)
					.endQuery()
				.close()
				.whereProperty("querySet")
					.isDelegateQuerySet("accessS")
					.whereDelegateQuery()
						.whereFroms(1).whereElementAt(0).isFrom(SmartManualA.T)
						.whereSelection(8)
							.whereElementAt(i=0).isPropertyOperand("author").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand(GenericEntity.globalId).whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("id").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("isbn").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("numberOfPages").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand(GenericEntity.partition).whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("smartManualString").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("title").whereSource().isFrom(SmartManualA.T).close(2)
					.endQuery()
				.close()
			.close()
			.whereProperty("values").isListWithSize(1)
				.whereElementAt(0).hasType(QueryFunctionValue.T).whereProperty("queryFunction").isAssembleEntity(SmartManualA.T).close(2)
		;
		// @formatter:on
	}

	@Test
	public void selectUnmappedTypePropertyWithEntityCondition_Weak() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("r", "weakFavoriteManual")
				.from(SmartReaderA.T, "r")
				.where()
					.property("r", "weakFavoriteManual").eq().entityReference(reference(SmartManualA.class, SmartMappingSetup.accessIdA, 99L))
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T).whereProperty("operand")
				.isDelegateQueryJoin_Inner()
				.whereProperty("materializedSet")
					.isDelegateQuerySet("accessS")
					.whereDelegateQuery()
						.whereFroms(1).whereElementAt(0).isFrom(SmartManualA.T)
						.whereSelection(8)
							.whereElementAt(i=0).isPropertyOperand("author").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand(GenericEntity.globalId).whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("id").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("isbn").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("numberOfPages").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand(GenericEntity.partition).whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("smartManualString").whereSource().isFrom(SmartManualA.T).close(2)
							.whereElementAt(++i).isPropertyOperand("title").whereSource().isFrom(SmartManualA.T).close(2)
						.whereCondition().isConjunction(1)
							.whereElementAt(0).isValueComparison(Operator.equal)
								.whereProperty("leftOperand").close()
								.whereProperty("rightOperand").isReference_(SmartManualA.T, 99L, accessIdA)
					.endQuery()
				.close()
				.whereProperty("querySet")
				.isDelegateQuerySet("accessA")
				.whereDelegateQuery()
					.whereSelection(1)
						.whereElementAt(0).isPropertyOperand("favoriteManualTitle").whereSource().isFrom(ReaderA.T).close(2)
				.endQuery()
			.close()
			.close()
			.whereProperty("values").isListWithSize(1)
				.whereElementAt(0).hasType(QueryFunctionValue.T).whereProperty("queryFunction").isAssembleEntity(SmartManualA.T).close(2)
		;
		// @formatter:on
	}

}
