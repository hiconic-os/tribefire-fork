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
package com.braintribe.utils.collection.impl;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.braintribe.testing.junit.assertions.assertj.core.api.Assertions;
import com.braintribe.utils.collection.api.NavigableMultiMap;

/**
 * 
 */
public class NavigableMultiMap_BasicFunctionality_Tests extends AbstractNavigableMultiMapTests {

	@Test
	public void testGettingFromEmptyMap() {
		testGettingFromEmptyMapWith(multiMap);
		testGettingFromEmptyMapWith(multiMap.subMap(-500L, true, 5000L, true));
	}

	private void testGettingFromEmptyMapWith(NavigableMultiMap<Long, Long> multiMap) {
		assertNull(multiMap.get(1L));
		Assertions.assertThat(multiMap.getAll(1L)).isNotNull().isEmpty();

		assertNotContains(multiMap.getLowest(1l));
		assertNotContains(multiMap.getHighest(1l));
	}

	@Test
	public void testSimpleAddingAndRemoving() {
		testSimpleAddingAndRemovingWith(multiMap);
		testSimpleAddingAndRemovingWith(multiMap.subMap(-500L, true, 5000L, true));
	}

	private void testSimpleAddingAndRemovingWith(NavigableMultiMap<Long, Long> multiMap) {
		assertEmpty(multiMap);

		multiMap.put(1L, 10L);
		multiMap.put(1L, 13L);
		multiMap.put(1L, 12L);
		multiMap.put(1L, 11L);
		assertContainsKeyValues(multiMap, 1L, 10L, 11L, 12L, 13L);
		assertContainsKeyValue(1L, 10L);

		multiMap.remove(1L, 11L);
		assertContainsKeyValues(multiMap, 1L, 10L, 12L, 13L);

		multiMap.put(1L, null);
		assertContainsKeyValue(1L, null);

		multiMap.clear();
		assertEmpty(multiMap);
	}

}
