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
package tribefire.extension.simple.modelexpert.data;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThatExecuting;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import tribefire.extension.simple.model.data.Person;

/**
 * Provides tests for {@link PersonExpert}.<br>
 * The main purpose of this class is to demonstrate how to easily write unit tests for an expert.
 *
 * @author michael.lafite
 */
public class PersonExpertTest {

	/**
	 * Tests method {@link PersonExpert#getParents(Person)}.
	 */
	@Test
	public void testGetParents() {
		assertThatExecuting(() -> PersonExpert.getParents(null)).fails().with(IllegalArgumentException.class);

		Person child = Person.T.create();
		assertThat(PersonExpert.getParents(child)).isEmpty();

		Person mother = Person.T.create();
		child.setMother(mother);
		assertThat(PersonExpert.getParents(child)).containsExactlyInAnyOrder(mother);

		Person father = Person.T.create();
		child.setFather(father);
		assertThat(PersonExpert.getParents(child)).containsExactlyInAnyOrder(mother, father);

		child.setMother(null);
		assertThat(PersonExpert.getParents(child)).containsExactlyInAnyOrder(father);
	}
}
