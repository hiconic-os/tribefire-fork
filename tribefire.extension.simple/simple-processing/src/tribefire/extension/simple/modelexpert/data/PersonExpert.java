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

import java.util.HashSet;
import java.util.Set;

import tribefire.extension.simple.model.data.Person;

/**
 * Provides methods for working with {@link Person} entities.<br>
 * The main purpose of this class is to provide an example of how to implement a so-called entity type expert, i.e. a class which provides utility
 * methods to work with entities of particular type.
 *
 * @author michael.lafite
 */
public interface PersonExpert {

	/**
	 * Returns the parents of the specified <code>person</code>, i.e. {@link Person#getMother() mother} and {@link Person#getFather()} (unless
	 * <code>null</code>).
	 *
	 * @throws IllegalArgumentException
	 *             if <code>person</code> is <code>null</code>.
	 */
	static Set<Person> getParents(Person person) {
		if (person == null) {
			throw new IllegalArgumentException("The passed person must not be null!");
		}
		Set<Person> parents = new HashSet<>();
		if (person.getMother() != null) {
			parents.add(person.getMother());
		}
		if (person.getFather() != null) {
			parents.add(person.getFather());
		}
		return parents;
	}

}
