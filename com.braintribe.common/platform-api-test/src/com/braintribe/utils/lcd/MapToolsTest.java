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
package com.braintribe.utils.lcd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Provides tests for {@link MapTools}.
 *
 * @author michael.lafite
 */
public class MapToolsTest {

	@Test
	public void testToArray() {
		List<Object> keysAndValues = Arrays.asList("a", 1, "b", 2, "c", 3, "f", 4, "e", 5, "d", 6);

		// LinkedHashMap has insertion order
		Map<String, Integer> map = new LinkedHashMap<>();
		MapTools.putAll(map, keysAndValues);

		assertThat(Arrays.asList(MapTools.toArray(map, Object.class))).isEqualTo(keysAndValues);
	}

}
