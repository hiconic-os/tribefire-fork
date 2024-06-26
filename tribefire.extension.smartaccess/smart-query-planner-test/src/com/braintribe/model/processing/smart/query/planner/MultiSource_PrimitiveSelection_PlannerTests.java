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

import com.braintribe.model.processing.query.smart.test.model.accessA.CompanyA;
import com.braintribe.model.processing.query.smart.test.model.accessA.PersonA;
import com.braintribe.model.processing.query.smart.test.model.smart.Company;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.processing.smart.query.planner.base.AbstractSmartQueryPlannerTests;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.queryplan.set.CartesianProduct;
import com.braintribe.model.queryplan.set.Projection;
import com.braintribe.model.smartqueryplan.set.DelegateQuerySet;

/**
 * 
 */
public class MultiSource_PrimitiveSelection_PlannerTests extends AbstractSmartQueryPlannerTests {

	@Test
	public void simpleQuery() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.T, "p")
				.from(Company.T, "c")
				.select("p", "nameA")
				.select("c", "nameA")
				.done()
		;
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T)
			.whereProperty("operand")
				.hasType(CartesianProduct.T)
				.whereProperty("operands").isListWithSize(2)
					.whereElementAt(0)
						.hasType(DelegateQuerySet.T)
						.whereProperty("delegateAccess").isDelegateAccess("accessA").close()
						.whereProperty("scalarMappings").isListWithSize(1).close()
						.whereDelegateQuery()
							.whereSelection(1)
								.whereElementAt(0).isPropertyOperand("nameA")
							.close(2)
							.whereProperty("froms").isListWithSize(1)
								.whereElementAt(0).isFrom(CompanyA.T)
						.endQuery()
					.close()
					.whereElementAt(1)
						.hasType(DelegateQuerySet.T)
						.whereProperty("delegateAccess").isDelegateAccess("accessA").close()
						.whereProperty("scalarMappings").isListWithSize(1).close()
						.whereDelegateQuery()
							.whereProperty("selections")
								.whereElementAt(0).isPropertyOperand("nameA")
							.close(2)
							.whereProperty("froms").isListWithSize(1)
								.whereElementAt(0).isFrom(PersonA.T)
						.endQuery()
					.close()
				.close()
			.close()
			.whereProperty("values").isListWithSize(2)
				.whereElementAt(0).isTupleComponent_(1)
				.whereElementAt(1).isTupleComponent_(0)
		;
		// @formatter:on
	}

	@Test
	public void simpleDelegateQueryWithCartesianProduct() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartPersonA.T, "p")
				.from(Company.T, "c")
				.select("p", "nameA")
				.select("c", "nameA")
				.where()
					.property("p", "companyNameA").eq().property("c", "nameA")
				.done()
				;
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Projection.T)
			.whereProperty("operand")
				.hasType(DelegateQuerySet.T)
				.whereProperty("delegateAccess").isDelegateAccess("accessA").close()
				.whereProperty("scalarMappings").isListWithSize(2).close()
				.whereDelegateQuery()
					.whereProperty("selections")
						.whereElementAt(0).isPropertyOperand("nameA").close()
						.whereElementAt(1).isPropertyOperand("nameA").close()
					.close()
					.whereProperty("froms").isListWithSize(2)
						.whereElementAt(0).isFrom(CompanyA.T).close()
						.whereElementAt(1).isFrom(PersonA.T)
				.endQuery()
			.close()
			.whereProperty("values").isListWithSize(2)
				.whereElementAt(0).isTupleComponent_(1)
				.whereElementAt(1).isTupleComponent_(0)
		;
		// @formatter:on
	}

}
