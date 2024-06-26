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

import com.braintribe.model.processing.query.smart.eval.set.base.AbstractSmartEvalTupleSetTests;
import com.braintribe.model.processing.query.smart.test.model.accessA.PersonA;
import com.braintribe.model.processing.query.smart.test.model.accessB.PersonB;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.queryplan.value.Value;
import com.braintribe.model.smartqueryplan.ScalarMapping;
import com.braintribe.model.smartqueryplan.set.DelegateQuerySet;
import com.braintribe.model.smartqueryplan.set.OrderedConcatenation;

public class OrderedConcatenationTest extends AbstractSmartEvalTupleSetTests {

	private final List<PersonA> personAs = newList();
	private final List<PersonB> personBs = newList();

	/**
	 * Generating two sets of data (pairs: ${person.name}, ${person.companyName}):
	 * <ul>
	 * <li>p00, cA0</li>
	 * <li>p02, cA1</li>
	 * <li>p04, cA2</li>
	 * ...
	 * </ul>
	 * and
	 * 
	 * <ul>
	 * <li>p01, cB0</li>
	 * <li>p03, cB1</li>
	 * <li>p05, cB2</li>
	 * ...
	 * </ul>
	 */
	@Before
	public void buildData() {
		for (int i = 0; i < 50; i++) {
			personAs.add(bA.personA("p" + toString(2 * i)).companyNameA("cA" + i).create());
			personBs.add(bB.personB("p" + toString(2 * i + 1)).companyNameB("cB" + i).create());
		}
	}

	/**
	 * Now we create a two {@link DelegateQuerySet}s which return the values sorted by person.nameA (or nameB) and we
	 * merge them using {@link OrderedConcatenation}.
	 */
	@Test
	public void testMerging() {
		List<ScalarMapping> scalarMapping = standardScalarMappings();

		// @formatter:off
		SelectQuery queryA = query()
				.from(PersonA.class, "p")
				.select("p", "nameA")
				.select("p", "companyNameA")
				.orderBy().property("p", "nameA")
				.done();
		// @formatter:on
		DelegateQuerySet dqsA = builder.delegateQuerySet(setup.accessA, queryA, scalarMapping);

		// @formatter:off
		SelectQuery queryB = query()
				.from(PersonB.class, "p")
				.select("p", "nameB")
				.select("p", "companyNameB")
				.orderBy().property("p", "nameB")
				.done();
		// @formatter:on
		DelegateQuerySet dqsB = builder.delegateQuerySet(setup.accessB, queryB, scalarMapping);

		Value nameValue = tupleComponent(0);
		OrderedConcatenation oc = builder.orderedConcatenation(dqsA, dqsB, 2, builder.sortCriterium(nameValue, false));

		evaluate(oc);

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
	 * <li>p00, cA0</li>
	 * <li>p01, cB0</li>
	 * <li>p02, cA1</li>
	 * <li>p03, cB1</li>
	 * ...
	 * </ul>
	 */
	private void assertContainsAllPersonData() {
		for (int i = 0; i < 50; i++) {
			assertNextTuple("p" + toString(2 * i), "cA" + i);
			assertNextTuple("p" + toString(2 * i + 1), "cB" + i);
		}
		assertNoMoreTuples();
	}

	private String toString(int i) {
		return (i < 10 ? "0" : "") + i;
	}

}
