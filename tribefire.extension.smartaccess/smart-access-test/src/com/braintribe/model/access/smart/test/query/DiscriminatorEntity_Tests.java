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

import com.braintribe.model.processing.query.smart.test.model.accessA.discriminator.DiscriminatorEntityA;
import com.braintribe.model.processing.query.smart.test.model.smart.discriminator.SmartDiscriminatorBase;
import com.braintribe.model.processing.query.smart.test.model.smart.discriminator.SmartDiscriminatorType1;
import com.braintribe.model.processing.query.smart.test.model.smart.discriminator.SmartDiscriminatorType2;
import com.braintribe.model.processing.smart.query.planner.DiscriminatorEntity_PlannerTests;
import com.braintribe.model.query.SelectQuery;

/**
 * 
 */
public class DiscriminatorEntity_Tests extends AbstractSmartQueryTests {

	private static final String DISC_OTHER = "other";

	/** @see DiscriminatorEntity_PlannerTests#selectPropert_HierarchyBase() */
	@Test
	public void selectPropert_HierarchyBase() {
		bA.discriminatorEntityA("t1", SmartDiscriminatorType1.DISC_TYPE1).create();
		bA.discriminatorEntityA("t2", SmartDiscriminatorType2.DISC_TYPE2).create();
		bA.discriminatorEntityA("other", DISC_OTHER).create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartDiscriminatorBase.class, "e")
				.select("e", "name")
				.orderBy().property("e", "id")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("t1");
		assertResultContains("t2");
		assertNoMoreResults();
	}

	/** @see DiscriminatorEntity_PlannerTests#selectPropert_HierarchyLeaf() */
	@Test
	public void selectPropert_HierarchyLeaf() {
		bA.discriminatorEntityA("t1", SmartDiscriminatorType1.DISC_TYPE1).type1Name("t1Name").create();
		bA.discriminatorEntityA("t2", SmartDiscriminatorType2.DISC_TYPE2).create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartDiscriminatorType1.class, "e")
				.select("e", "type1Name")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("t1Name");
		assertNoMoreResults();
	}

	/** @see DiscriminatorEntity_PlannerTests#selectTheDiscriminatorProperty() */
	@Test
	public void selectTheDiscriminatorProperty() {
		bA.discriminatorEntityA("t1", SmartDiscriminatorType1.DISC_TYPE1).type1Name("t1Name").create();
		bA.discriminatorEntityA("t2", SmartDiscriminatorType2.DISC_TYPE2).create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartDiscriminatorType2.class, "e")
				.select("e", "discriminator")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(SmartDiscriminatorType2.DISC_TYPE2);
		assertNoMoreResults();
	}

	/** @see DiscriminatorEntity_PlannerTests#selectEntity_HierarchyLeaf() */
	@Test
	public void selectEntity_HierarchyLeaf() {
		DiscriminatorEntityA e;
		e = bA.discriminatorEntityA("t2", SmartDiscriminatorType2.DISC_TYPE2).create();
		e = bA.discriminatorEntityA("t1", SmartDiscriminatorType1.DISC_TYPE1).type1Name("t1Name").create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartDiscriminatorType1.class, "e")
				.select("e")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(smartDiscriminatorType1(e));
		assertNoMoreResults();
	}

	/** @see DiscriminatorEntity_PlannerTests#selectEntity_HierarchyBase() */
	@Test
	public void selectEntity_HierarchyBase() {
		DiscriminatorEntityA e1 = bA.discriminatorEntityA("t1", SmartDiscriminatorType1.DISC_TYPE1).type1Name("t1Name").create();
		DiscriminatorEntityA e2 = bA.discriminatorEntityA("t2", SmartDiscriminatorType2.DISC_TYPE2).create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartDiscriminatorBase.class, "e")
				.select("e")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(smartDiscriminatorType1(e1));
		assertResultContains(smartDiscriminatorType2(e2));
		assertNoMoreResults();
	}

	/** @see DiscriminatorEntity_PlannerTests#selectTypeSignature() */
	@Test
	public void selectTypeSignature() {
		bA.discriminatorEntityA("t1", SmartDiscriminatorType1.DISC_TYPE1).create();
		bA.discriminatorEntityA("t2", SmartDiscriminatorType2.DISC_TYPE2).create();

		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartDiscriminatorBase.class, "e")
				.select().entitySignature().entity("e")
				.done();
		// @formatter:on

		evaluate(selectQuery);
		
		assertResultContains(SmartDiscriminatorType1.class.getName());
		assertResultContains(SmartDiscriminatorType2.class.getName());
		assertNoMoreResults();
	}

	/** @see DiscriminatorEntity_PlannerTests#selectEntity_ConditionOnType() */
	@Test
	public void selectEntity_ConditionOnType() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.from(SmartDiscriminatorBase.class, "e")
				.select("e")
				.where()
					.entitySignature("e").eq(SmartDiscriminatorType1.class.getName())
				.done();
		// @formatter:on

		evaluate(selectQuery);

		// TODO FINISH
	}

}
