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
package com.braintribe.utils;

import static com.braintribe.utils.lcd.CollectionTools2.asList;
import static com.braintribe.utils.lcd.CollectionTools2.asSet;
import static com.braintribe.utils.lcd.CollectionTools2.splitToLists;
import static com.braintribe.utils.lcd.CollectionTools2.splitToSets;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.Test;

public class CollectionTools2Tests {

	@Test
	public void split() {
		List<Integer> original = asList(0, 1, 2, 0, 1, 2, 0, 1, 2);

		List<Integer> expectedListPart = asList(0, 1, 2);
		Set<Integer> expectedSetPart = asSet(0, 1, 2);

		List<List<Integer>> lists = splitToLists(original, 3);
		List<Set<Integer>> sets = splitToSets(original, 3);

		for (List<Integer> listPart : lists) {
			assertThat(listPart).isEqualTo(expectedListPart);
		}

		for (Set<Integer> setPart : sets) {
			assertThat(setPart).isEqualTo(expectedSetPart);
		}
	}

}
