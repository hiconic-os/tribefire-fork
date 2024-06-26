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
package com.braintribe.model.processing.query.eval.set;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.processing.query.eval.set.base.AbstractEvalTupleSetTests;
import com.braintribe.model.processing.query.eval.set.base.ModelBuilder;
import com.braintribe.model.processing.query.test.model.Person;
import com.braintribe.model.processing.smood.population.SmoodIndexTools;
import com.braintribe.model.queryplan.index.MetricIndex;
import com.braintribe.model.queryplan.index.RepositoryMetricIndex;

/**
 * Tests for {@link EvalIndexOrderedSet}.
 */
public class IndexOrderedSetTests extends AbstractEvalTupleSetTests {

	private static final int PERSON_COUNT = 4;
	private final Person[] persons = new Person[PERSON_COUNT];

	private static final boolean ASCENDING = false;
	private static final boolean DESCENDING = !ASCENDING;

	@Before
	public void buildData() {
		for (int i = 0; i < PERSON_COUNT; i++)
			registerAtSmood(persons[i] = ModelBuilder.person("person" + String.valueOf((char) (i + 65))));
	}

	@Test
	public void ascendingOrder() throws Exception {
		runTest(ASCENDING);
	}

	@Test
	public void descendingOrder() throws Exception {
		runTest(DESCENDING);
	}

	private void runTest(boolean reverseOrder) throws Exception {
		evaluate(builder.indexOrderedSet(Person.class, "indexedName", nameIndex(), reverseOrder));

		assertCorrectOrder(reverseOrder);
	}

	/* package */ static MetricIndex nameIndex() {
		RepositoryMetricIndex index = RepositoryMetricIndex.T.create();
		index.setIndexId(SmoodIndexTools.indexId(Person.class.getName(), "indexedName"));

		return index;
	}

	private void assertCorrectOrder(boolean reverseOrder) {
		for (int i = 0; i < PERSON_COUNT; i++) {
			int j = reverseOrder ? PERSON_COUNT - 1 - i : i;
			assertNextTuple(persons[j]);
		}
	}

}
