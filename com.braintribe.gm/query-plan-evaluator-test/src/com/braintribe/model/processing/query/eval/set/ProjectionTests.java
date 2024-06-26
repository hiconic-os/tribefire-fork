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

import static com.braintribe.model.processing.query.eval.set.base.TupleSetBuilder.localize;
import static com.braintribe.model.processing.query.eval.set.base.TupleSetBuilder.tupleComponent;
import static com.braintribe.model.processing.query.eval.set.base.TupleSetBuilder.valueProperty;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.processing.query.eval.set.base.AbstractEvalTupleSetTests;
import com.braintribe.model.processing.query.eval.set.base.ModelBuilder;
import com.braintribe.model.processing.query.test.model.Person;
import com.braintribe.model.queryplan.set.SourceSet;
import com.braintribe.model.queryplan.value.Value;

/**
 * Tests filtered set with the underlying source-set (for {@link Person} entities).
 */
public class ProjectionTests extends AbstractEvalTupleSetTests {

	private Person pA, pB;
	private SourceSet personSet;
	private Value personComponent;
	private Value personNameValue;
	private Value personCompanyNameValue;
	private Value personAgeValue;

	@Before
	public void buildData() {
		registerAtSmood(pA = ModelBuilder.person("personA", "companyA", 10));
		registerAtSmood(pB = ModelBuilder.person("personB", "companyB", 20));

		personSet = builder.sourceSet(Person.class);
		personComponent = tupleComponent(personSet);
		personNameValue = valueProperty(personSet, "name");
		personCompanyNameValue = valueProperty(personSet, "companyName");
		personAgeValue = valueProperty(personSet, "birthDate");
	}

	@Test
	public void theInstanceItself() throws Exception {
		evaluate(builder.projection(personSet, personComponent));

		assertContainsTuple(pA);
		assertContainsTuple(pB);
		assertNoMoreTuples();
	}

	@Test
	public void justOneProperty() throws Exception {
		evaluate(builder.projection(personSet, personNameValue));

		assertContainsTuple("personA");
		assertContainsTuple("personB");
		assertNoMoreTuples();
	}

	@Test
	public void moreProperties() throws Exception {
		evaluate(builder.projection(personSet, personNameValue, personComponent, personCompanyNameValue, personAgeValue));

		assertContainsTuple("personA", pA, "companyA", pA.getBirthDate());
		assertContainsTuple("personB", pB, "companyB", pB.getBirthDate());
		assertNoMoreTuples();
	}

	@Test
	public void localizedProperty() throws Exception {
		pA.setLocalizedString(localizedString("en", "yes", "pt", "sim"));
		pB.setLocalizedString(localizedString("en", "good", "pt", "bom"));

		Object operand = new Object(); // for evaluator tests this does not really matter
		evaluate(builder.projection(personSet, localize(operand, "pt", valueProperty(personSet, "localizedString"))));

		assertContainsTuple("sim");
		assertContainsTuple("bom");
		assertNoMoreTuples();
	}

}
