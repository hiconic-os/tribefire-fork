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

import com.braintribe.model.processing.query.smart.test.model.accessA.constant.ConstantPropEntityA;
import com.braintribe.model.processing.query.smart.test.model.accessA.constant.ConstantPropEntityA2;
import com.braintribe.model.processing.query.smart.test.model.accessA.constant.ConstantPropEntitySubA;
import com.braintribe.model.processing.query.smart.test.model.smart.constant.SmartConstantPropEntity;
import com.braintribe.model.processing.query.smart.test.model.smart.constant.SmartConstantPropEntityA;
import com.braintribe.model.processing.query.smart.test.model.smart.constant.SmartConstantPropEntityA2;
import com.braintribe.model.processing.query.smart.test.model.smart.constant.SmartConstantPropEntitySubA;
import com.braintribe.model.processing.smart.query.planner.ConstantProperty_PlannerTests;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.utils.junit.assertions.BtAssertions;

/**
 * 
 */
public class ConstantProperty_Tests extends AbstractSmartQueryTests {

	/** @see ConstantProperty_PlannerTests#selectTheConstant() */
	@Test
	public void selectTheConstant() {
		ConstantPropEntitySubA cpe = bA.constantPropEntitySubA("constantine").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartConstantPropEntitySubA.class, "c")
				.select("c", "name")
				.select("c", "constantValue")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(cpe.getName(), SmartConstantPropEntitySubA.CONSTANT_VALUE_SUB);
		assertNoMoreResults();
	}

	/** @see ConstantProperty_PlannerTests#selectConstantPropertyEntity() */
	@Test
	public void selectConstantPropertyEntity() {
		ConstantPropEntitySubA cpe = bA.constantPropEntitySubA("constantine").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartConstantPropEntitySubA.class, "c")
				.select("c")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		SmartConstantPropEntitySubA scpe = smartConstantPropEntitySubA(cpe);

		assertResultContains(scpe);
		assertNoMoreResults();

		BtAssertions.assertThat(scpe.<Object> getId()).isEqualTo(cpe.getId());
		BtAssertions.assertThat(scpe.getName()).isEqualTo(cpe.getName());
		BtAssertions.assertThat(scpe.getConstantValue()).isEqualTo(SmartConstantPropEntitySubA.CONSTANT_VALUE_SUB);
	}

	/** @see ConstantProperty_PlannerTests#conditionOnTheConstant_WhenTrue() */
	@Test
	public void conditionOnTheConstant_WhenTrue() {
		ConstantPropEntitySubA cpe = bA.constantPropEntitySubA("constantine").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartConstantPropEntitySubA.class, "c")
				.select("c", "name")
				.where()
					.property("c", "constantValue").eq(SmartConstantPropEntitySubA.CONSTANT_VALUE_SUB)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(cpe.getName());
		assertNoMoreResults();
	}

	/** @see ConstantProperty_PlannerTests#conditionOnTheConstant_WhenFalse() */
	@Test
	public void conditionOnTheConstant_WhenFalse() {
		bA.constantPropEntitySubA("constantine").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartConstantPropEntitySubA.class, "c")
				.select("c", "name")
				.where()
					.property("c", "constantValue").eq("Bullshit")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertNoMoreResults();
	}

	/** @see ConstantProperty_PlannerTests#selectTheConstant_Polymorphic() */
	@Test
	public void selectTheConstant_Polymorphic() {
		ConstantPropEntityA cpe = bA.constantPropEntityA("constantine").create();
		ConstantPropEntitySubA cpes = bA.constantPropEntitySubA("sub-constantine").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartConstantPropEntityA.class, "c")
				.select("c", "name")
				.select("c", "constantValue")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(cpe.getName(), SmartConstantPropEntityA.CONSTANT_VALUE);
		assertResultContains(cpes.getName(), SmartConstantPropEntitySubA.CONSTANT_VALUE_SUB);
		assertNoMoreResults();
	}

	/** @see ConstantProperty_PlannerTests#conditionOnTheConstant_Polymorphic_WhenPartiallyTrue() */
	@Test
	public void conditionOnTheConstant_Polymorphic_WhenPartiallyTrue() {
		ConstantPropEntityA cpe;
		cpe = bA.constantPropEntityA("constantine").create();
		cpe = bA.constantPropEntitySubA("sub-constantine").create(); // only this' name will be retrieved

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartConstantPropEntityA.class, "c")
				.select("c", "name")
				.where()
					.property("c", "constantValue").eq(SmartConstantPropEntitySubA.CONSTANT_VALUE_SUB)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(cpe.getName());
		assertNoMoreResults();
	}

	/** @see ConstantProperty_PlannerTests#conditionOnTheConstant_WithPagination() */
	@Test
	public void conditionOnTheConstant_WithPagination() {
		bA.constantPropEntityA("constantine").create();
		bA.constantPropEntitySubA("sub-constantine").create(); // only this' name will be retrieved

		ConstantPropEntityA2 cpe2 = bA.constantPropEntityA2("constantine").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartConstantPropEntity.class, "c")
				.select("c")
				.where()
					.property("c", "constantValue").eq(SmartConstantPropEntityA2.CONSTANT_VALUE)
				.paging(10, 0)
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(smartConstantPropEntityA2(cpe2));
		assertNoMoreResults();
	}

}
