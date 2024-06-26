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

import static com.braintribe.model.generic.GenericEntity.partition;
import static com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup.accessIdA;
import static com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup.accessIdB;
import static com.braintribe.utils.lcd.CollectionTools2.asSet;

import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.shared.SharedFileDescriptor;
import com.braintribe.model.processing.query.smart.test.model.shared.SharedSource;
import com.braintribe.model.processing.smart.query.planner.base.AbstractSmartQueryPlannerTests;
import com.braintribe.model.processing.smart.query.planner.splitter.SmartQuerySplitter;
import com.braintribe.model.query.OrderingDirection;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.queryplan.set.Concatenation;

/**
 * Testing SharedEntities queries in cases where partition is specified, and thus not all relevant delegates should be
 * queried.
 */
public class SharedEntity_PartitionCondition_PlannerTests extends AbstractSmartQueryPlannerTests {

	/** By specifying the partition in the query we know delegateB doesn't have to be checked. */
	@Test
	public void selectSharedEntity_ConditionOnPartition_Equals() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("s")
				.from(SharedSource.T, "s")
				.where()
					.property("s", partition).eq(accessIdA)
				.done();
		// @formatter:on

		runTest(selectQuery);

		assertQueryPlan(1).isDelegateQueryAsIs("accessA");
	}

	/**
	 * Previous use-case with ordering:
	 * 
	 * Before, when doing a query with ordering on a Shared source, we collected pairs from each delegate (SharedSource,
	 * SharedSource.id), so that we could then apply ordering when combining the partial results together. In this case,
	 * the other partial results are all empty plans, thus we end up with a single plan that selects the id for no
	 * obvious reason, and a projection on top of it that picks the first element only.
	 * 
	 * Later we have improved this by adding delegate resolution into the {@link SmartQuerySplitter} based on the query
	 * condition. Now it knows right away it only needs to use one delegate.
	 */
	@Test
	public void selectSharedEntity_ConditionOnPartition_Equals_WithOrdering() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("s")
				.from(SharedSource.T, "s")
				.where()
					.property("s", partition).eq(accessIdA)
				.orderBy(OrderingDirection.ascending)
					.property("s", "id")
				.done();
		// @formatter:on

		runTest(selectQuery);

		assertQueryPlan(1).isDelegateQueryAsIs("accessA");
	}

	/** By specifying the partition in the query we know delegateB doesn't have to be checked. */
	@Test
	public void selectSharedEntity_ConditionOnPartition_In() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("s")
				.from(SharedSource.T, "s")
				.where()
					.property("s", partition).in(asSet(accessIdA))
				.done();
		// @formatter:on

		runTest(selectQuery);

		assertQueryPlan(1).isDelegateQueryAsIs("accessA");
	}

	@Test
	public void selectSharedEntity_ConditionOnPartition_In_WithCondition() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("s")
				.from(SharedSource.T, "s")
				.where()
					.property("s", partition).in(asSet(accessIdA))
				.orderBy(OrderingDirection.ascending)
					.property("s", "id")
				.done();
		// @formatter:on

		runTest(selectQuery);

		assertQueryPlan(1).isDelegateQueryAsIs("accessA");
	}

	/** By specifying the reference we also specify the partition, hence delegateB doesn't have to be checked. */
	@Test
	public void selectSharedEntity_ConditionOnEntity_Equals() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("s")
				.from(SharedSource.T, "s")
				.where()
					.entity("s").eq().entityReference(reference(SharedSource.class, accessIdA, 99L))
				.done();
		// @formatter:on

		runTest(selectQuery);

		assertQueryPlan(1).isDelegateQueryAsIs("accessA");
	}

	/** By specifying the reference we also specify the partition, hence delegateB doesn't have to be checked. */
	@Test
	public void selectSharedEntity_ConditionOnEntity_Equals_WithOrdering() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("s")
				.from(SharedSource.T, "s")
				.where()
					.entity("s").eq().entityReference(reference(SharedSource.class, accessIdA, 99L))
				.orderBy(OrderingDirection.ascending)
					.property("s", "id")
				.done();
		// @formatter:on

		runTest(selectQuery);

		assertQueryPlan(1).isDelegateQueryAsIs("accessA");
	}

	/** By specifying the reference we also specify the partition, hence delegateB doesn't have to be checked. */
	@Test
	public void selectSharedEntity_ConditionOnEntityProperty_Equals() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("fd")
				.from(SharedFileDescriptor.T, "fd")
				.where()
					.property("fd", "file").eq().entityReference(reference(SharedFileDescriptor.class, accessIdA, 99L))
				.done();
		// @formatter:on

		runTest(selectQuery);

		assertQueryPlan(1).isDelegateQueryAsIs("accessA");
	}

	/** By specifying the reference we also specify the partition, hence delegateB doesn't have to be checked. */
	@Test
	public void selectSharedEntity_ConditionOnEntity_In() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("s")
				.from(SharedSource.T, "s")
				.where()
					.entity("s").in(asSet(reference(SharedSource.class, accessIdA, 99L)))
				.done();
		// @formatter:on

		runTest(selectQuery);

		assertQueryPlan(1).isDelegateQueryAsIs("accessA");
	}

	@Test
	public void selectSharedEntity_ConditionOnEntity_In_WithOrdering() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("s")
				.from(SharedSource.T, "s")
				.where()
					.entity("s").in(asSet(reference(SharedSource.class, accessIdA, 99L)))
				.orderBy(OrderingDirection.ascending)
					.property("s", "id")
				.done();
		// @formatter:on
		
		runTest(selectQuery);
		
		assertQueryPlan(1).isDelegateQueryAsIs("accessA");
	}
	
	/** By specifying the reference we also specify the partition, hence delegateB doesn't have to be checked. */
	@Test
	public void selectSharedEntity_ConditionOnEntity_InMixtureOfPartitions() {
		// @formatter:off
		SelectQuery selectQuery = query()		
				.select("s")
				.from(SharedSource.T, "s")
				.where()
					.entity("s").in(asSet(
							reference(SharedSource.class, accessIdA, 99L),
							reference(SharedSource.class, accessIdB, 420L)
						))
				.done();
		// @formatter:on

		runTest(selectQuery);

		// @formatter:off
		assertQueryPlan()
			.hasType(Concatenation.T)
			.whereProperty("firstOperand")
				.isDelegateQueryAsIs("accessA").close()
			.whereProperty("secondOperand")
				.isDelegateQueryAsIs("accessB").close()
		;
		// @formatter:on
	}
}
