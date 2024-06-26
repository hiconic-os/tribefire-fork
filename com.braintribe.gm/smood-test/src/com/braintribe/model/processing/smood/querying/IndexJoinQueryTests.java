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
package com.braintribe.model.processing.smood.querying;

import java.util.Date;

import org.junit.Test;

import com.braintribe.model.processing.query.test.IndexJoinTests;
import com.braintribe.model.processing.query.test.model.Address;
import com.braintribe.model.processing.query.test.model.Company;
import com.braintribe.model.processing.query.test.model.Person;
import com.braintribe.model.query.SelectQuery;

/**
 * 
 */
public class IndexJoinQueryTests extends AbstractSelectQueryTests {

	// ####################################
	// ## . . . . . Value Join . . . . . ##
	// ####################################

	/** @see IndexJoinTests#simpleValueJoin() */
	@Test
	public void simpleValueJoin() {
		Company c1 = b.company("C1").indexedName("C1").create();
		Company c2 = b.company("C2").indexedName("C2").create();

		Person p1 = b.owner("P1").companyName("C1").create();
		Person p2 = b.owner("P2").companyName("C2").create();

		// @formatter:off
		SelectQuery selectQuery = query()
				.from(Person.T, "p")
				.from(Company.class, "c")
				.where()
					.property("p", "companyName").eq().property("c", "indexedName")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(p1, c1);
		assertResultContains(p2, c2);
		assertNoMoreResults();
	}

	// ####################################
	// ## . . . . . Range Join . . . . . ##
	// ####################################

	/** @see IndexJoinTests#simpleRangeJoin() */
	@Test
	public void simpleRangeJoin() {
		final long YEAR = 365 * 24 * 3600 * 1000;

		Company c1 = b.company("C1").date(new Date(System.currentTimeMillis() - 15 * YEAR)).create();
		Company c2 = b.company("C2").date(new Date(System.currentTimeMillis() - 25 * YEAR)).create();

		Person p1 = b.person("P1").birthDate(new Date(System.currentTimeMillis() - 10 * YEAR)).create();
		Person p2 = b.person("P2").birthDate(new Date(System.currentTimeMillis() - 20 * YEAR)).create();

		// @formatter:off
		SelectQuery selectQuery = query()
				.from(Person.T, "p")
				.from(Company.class, "c")
				.where()
					.property("p", "birthDate").ge().property("c", "indexedDate")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(p1, c1);
		assertResultContains(p1, c2);
		assertResultContains(p2, c2);
		assertNoMoreResults();
	}

	// ####################################
	// ## . . Generated Value Join . . . ##
	// ####################################

	/** @see IndexJoinTests#mergeLookupJoin() */
	@Test
	public void mergeLookupJoin() {
		Company c1 = b.company("C1").create();
		Company c2 = b.company("C2").create();

		Person p1 = b.person("P1").companyName("C1").create();
		Person p2 = b.person("P2").companyName("C2").create();

		// @formatter:off
		SelectQuery selectQuery = query()
				.from(Person.T, "p")
				.from(Company.class, "c")
				.where()
					.property("p", "companyName").eq().property("c", "name")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(p1, c1);
		assertResultContains(p2, c2);
		assertNoMoreResults();
	}

	/** @see IndexJoinTests#mergeLookupJoinWithJoinOperand() */
	@Test
	public void mergeLookupJoinWithJoinOperand() {
		Address a1 = b.address("a1").create();
		Address a2 = b.address("a2").create();

		b.company("C1").address(a1).create();
		b.company("C2").address(a2).create();
		b.company("C3").create();

		// @formatter:off
		SelectQuery selectQuery = query()
			.select("c", "name")
		   	.from(Address.class, "a")
		   	.from(Company.class, "c")
		   		.join("c", "address", "cA") 
		   	.where()
		   		.entity("a").eq().entity("cA")
		   	.done();		
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("C1");
		assertResultContains("C2");
		assertNoMoreResults();
	}

	/** @see IndexJoinTests#mergeRangeJoin() */
	@Test
	public void mergeRangeJoin() {
		Company c1 = b.company("C1").create();
		Company c2 = b.company("C2").create();

		Person p1 = b.person("P1").companyName("C1").create();
		Person p2 = b.person("P2").companyName("C2").create();

		// @formatter:off
		SelectQuery selectQuery = query()
				.from(Person.T, "p")
				.from(Company.class, "c")
				.where()
					.property("p", "companyName").ge().property("c", "name")
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(p1, c1);
		assertResultContains(p2, c1);
		assertResultContains(p2, c2);
		assertNoMoreResults();
	}

	/** @see IndexJoinTests#mergeRangeJoin_Multi() */
	@Test
	@SuppressWarnings("unused")
	public void mergeRangeJoin_Multi() {
		Company c1c = b.company("C1").description("C").create();
		Company c1d = b.company("C1").description("D").create();

		Company c2c = b.company("C2").description("C").create();
		Company c2d = b.company("C2").description("D").create();

		Person p1 = b.person("P1").companyName("C1").create();
		Person p2 = b.person("P2").companyName("C2").create();

		// D > C2 > C1 > C
		// This means c1c passes the test when compared with p1, but c2c does not, because p1.companyNam (C1) < c1d.description (D)

		// @formatter:off
		SelectQuery selectQuery = query()
				.from(Person.T, "p")
				.from(Company.T, "c")
				.where()
					.conjunction()
						.property("p", "companyName").ge().property("c", "name")
						.property("p", "companyName").ge().property("c", "description")
					.close()
				.done();
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains(p1, c1c);
		assertResultContains(p2, c1c);
		assertResultContains(p2, c2c);
		assertNoMoreResults();
	}

	/** @see IndexJoinTests#mergeRangeJoinWithJoinOperand() */
	@Test
	public void mergeRangeJoinWithJoinOperand() {
		Address a1 = b.address("a1").create();
		Address a2 = b.address("a2").create();

		b.company("C1").address(a1).create();
		b.company("C2").address(a2).create();
		b.company("C3").create();

		// @formatter:off
		SelectQuery selectQuery = query()
			.select("a", "name")
			.select("c", "name")
		   	.from(Address.class, "a")
		   	.from(Company.class, "c")
		   		.join("c", "address", "cA") 
		   	.where()
				.property("a", "name").ge().property("cA", "name")
		   	.done();		
		// @formatter:on

		evaluate(selectQuery);

		assertResultContains("a1", "C1");
		assertResultContains("a2", "C1");
		assertResultContains("a2", "C2");
		assertNoMoreResults();
	}

}
