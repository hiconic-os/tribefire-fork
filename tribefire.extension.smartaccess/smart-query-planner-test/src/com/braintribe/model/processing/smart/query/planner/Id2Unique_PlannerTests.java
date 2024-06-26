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

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.accessA.Id2UniqueEntityA;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.processing.smart.query.planner.base.AbstractSmartQueryPlannerTests;
import com.braintribe.model.processing.smart.query.planner.base.TestAccess;
import com.braintribe.model.query.Operator;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.queryplan.set.Projection;
import com.braintribe.model.smartqueryplan.set.DelegateQuerySet;

/**
 * 
 * Special set of tests for cases when id of a smart entity is mapped to a non-id property in delegate.
 */
public class Id2Unique_PlannerTests extends AbstractSmartQueryPlannerTests {

	@Before
	public void prepareUniqueToIdMapping() {
		TestAccess.idMapping = Arrays.<Object> asList("99", 99L);
	}

	@Test
	public void propertyEntityCondition() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.T, "p")
				.select("p", "nameA")
				.where()
					.property("p", "id2UniqueEntityA").eq().entity(id2UniqueEntity("99"))
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T)
			.whereProperty("operand")
				.hasType(DelegateQuerySet.T)
				.whereDelegateQuery()
					.whereSelection(1)
						.whereElementAt(0).isPropertyOperand("nameA")
					.whereCondition().isConjunction(1)
						.whereElementAt(0).isValueComparison(Operator.equal)
							.whereProperty("leftOperand").isSourceOnlyPropertyOperand().whereSource().isJoin("id2UniqueEntityA").close(2)
							.whereProperty("rightOperand").isReference_(Id2UniqueEntityA.T, 99L, accessIdA)
				.endQuery()
			;
		// @formatter:on
	}

	@Test
	public void collectionEntityCondition() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.T, "p")
				.select("p", "nameA")
				.where()
					.property("p", "id2UniqueEntitySetA").contains().entity(id2UniqueEntity("99"))
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T)
			.whereProperty("operand")
				.hasType(DelegateQuerySet.T)
				.whereDelegateQuery()
					.whereSelection(1)
						.whereElementAt(0).isPropertyOperand("nameA")
					.whereCondition().isConjunction(1)
						.whereElementAt(0).isValueComparison(Operator.contains)
							.whereProperty("leftOperand").isPropertyOperand("id2UniqueEntitySetA").close()
							.whereProperty("rightOperand").isReference_(Id2UniqueEntityA.T, 99L, accessIdA) 
				.endQuery()
			;
		// @formatter:on
	}

}
