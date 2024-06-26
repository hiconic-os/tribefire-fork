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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.utils.lcd.MapTools;

/**
 * Tests for {@link MapTools#createIntersection(Map...)}
 *
 *
 */
@SuppressWarnings("unchecked")
public class MapToolsIntersectionTests {

	List<String> commonKeys = Arrays.asList("a", "b", "c");

	// @Test
	// public void createMapIntersectionWithNoParameters() {
	// final Map<Object, Object> result = MapTools.createIntersection();
	// Assert.assertNull(result);
	// }
	//
	// @Test
	// public void createMapIntersectionWithSingleNull() {
	// final Map<Object, Object> result = MapTools.createIntersection((Map<Object, Object>[]) null);
	// Assert.assertNull(result);
	// }
	//
	// @Test
	// public void createMapIntersectionWithMultipleNull() {
	// final Map<Object, Object> result = MapTools.createIntersection(null, null, null);
	// Assert.assertNull(result);
	// }

	@Test
	public void createMapIntersectionWithSingleMap() {
		final Map<String, String> stringMap = createMapWithCommonKeys();
		final Map<String, String> result = MapTools.createIntersection(stringMap);
		assertMapContainsCommonKeysAndHasExpectedSize(result);
	}

	private void assertMapContainsCommonKeysAndHasExpectedSize(final Map<String, String> result) {
		for (final String key : this.commonKeys) {
			Assert.assertTrue("The resulting intersection should contain the key " + key, result.containsKey(key));
		}
		Assert.assertEquals(this.commonKeys.size(), result.size());
	}

	private Map<String, String> createMapWithCommonKeys() {
		final Map<String, String> stringMap = new HashMap<>();
		for (final String key : this.commonKeys) {
			stringMap.put(key, key + "value");
		}
		return stringMap;
	}

	@Test
	public void createMapIntersectionWithMultipleMaps() {
		final Map<String, String> firstMap = createMapWithCommonKeys();
		firstMap.put("x", "x");
		final Map<String, String> secondMap = createMapWithCommonKeys();
		final Map<String, String> thirdMap = createMapWithCommonKeys();
		thirdMap.put("n", "n");
		thirdMap.put("m", "m");
		thirdMap.put("h", "h");
		thirdMap.put("g", "g");
		thirdMap.put("x", "x");

		final Map<String, String> result = MapTools.createIntersection(firstMap, secondMap, thirdMap);
		assertMapContainsCommonKeysAndHasExpectedSize(result);
	}

}
