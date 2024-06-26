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
package com.braintribe.model.processing.query.smart.eval.set;

import static com.braintribe.model.processing.query.eval.set.base.TupleSetBuilder.tupleComponent;
import static com.braintribe.utils.lcd.CollectionTools2.newList;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.processing.query.eval.set.OrderedSetRefinementTests;
import com.braintribe.model.processing.query.smart.eval.set.base.AbstractSmartEvalTupleSetTests;
import com.braintribe.model.processing.query.smart.test.model.accessA.PersonA;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.queryplan.set.OrderedSetRefinement;
import com.braintribe.model.queryplan.value.Value;
import com.braintribe.model.smartqueryplan.ScalarMapping;
import com.braintribe.model.smartqueryplan.set.DelegateQuerySet;

/**
 * Originally, the {@link OrderedSetRefinement} was implemented in smart access for a specific use-case (under the name ExtendedOrderedSet). Now it
 * was moved to the basic query evaluator, but I keep this tests for extra safety.
 * 
 * @see OrderedSetRefinementTests
 */
public class SmartOrderedSetRefinementTest extends AbstractSmartEvalTupleSetTests {

	private final List<PersonA> persons = newList();

	/**
	 * Generating data like (pairs: ${person.name}, ${person.companyName}):
	 * <ul>
	 * <li>p00, c0
	 * <li>p01, c1
	 * <li>...
	 * <li>p09, c9
	 * <li>p10, c0
	 * <li>p11, c1
	 * <li>...
	 * </ul>
	 */
	@Before
	public void buildData() {
		for (int i = 0; i < 100; i++)
			persons.add(bA.personA("p" + toString(i)).companyNameA("c" + (i % 10)).create());
	}

	/**
	 * Now we create a {@link DelegateQuerySet} which returns the values sorted by person.companyName and we wrap it into a
	 * {@link OrderedSetRefinement}, which applies secondary ordering - by person.name.
	 */
	@Test
	public void testOrdering() {
		// @formatter:off
		SelectQuery query = query()
				.from(PersonA.class, "p")
				.select("p", "nameA")
				.select("p", "companyNameA")
				.orderBy().property("p", "companyNameA")
				.done();
		// @formatter:on

		DelegateQuerySet dqs = builder.delegateQuerySet(setup.accessA, query, standardScalarMappings());

		Value nameValue = tupleComponent(0);
		Value companyNameValue = tupleComponent(1);

		OrderedSetRefinement osr = builder.orderedSetRefinement(dqs, nameValue, false, Arrays.asList(companyNameValue));

		evaluate(osr);

		assertContainsAllPersonData();
	}

	private List<ScalarMapping> standardScalarMappings() {
		ScalarMapping sm1 = builder.scalarMapping(0);
		ScalarMapping sm2 = builder.scalarMapping(1);

		return Arrays.asList(sm1, sm2);
	}

	/**
	 * So the expected result is:
	 * <ul>
	 * <li>p00, c0
	 * <li>p10, c0
	 * <li>...
	 * <li>p90, c0
	 * <li>p01, c1
	 * <li>p11, c1
	 * <li>...
	 * <li>p91, c1
	 * <li>p02, c2
	 * <li>...
	 * </ul>
	 */
	private void assertContainsAllPersonData() {
		for (int c = 0; c < 10; c++) {
			for (int i = 0; i < 10; i++) {
				int p = 10 * i + c;
				assertNextTuple("p" + toString(p), "c" + c);
			}
		}
		assertNoMoreTuples();
	}

	private String toString(int i) {
		return (i < 10 ? "0" : "") + i;
	}

}
