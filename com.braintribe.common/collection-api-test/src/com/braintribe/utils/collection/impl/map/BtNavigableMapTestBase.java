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
package com.braintribe.utils.collection.impl.map;

import java.util.TreeMap;

import com.braintribe.testing.junit.assertions.assertj.core.api.Assertions;
import com.braintribe.utils.collection.impl.BtNavigableMap;
import com.braintribe.utils.collection.impl.ComparableComparator;

/**
 * @deprecated {@link BtNavigableMap} was only created for GWT compatibility. GWT now supports {@link TreeMap}, so use that!!!
 */
@Deprecated
public abstract class BtNavigableMapTestBase {

	protected BtNavigableMap<Long, Long> map;

	private static final int BIG_PRIME = 65537;

	protected void setUp() {
		map = new BtNavigableMap<Long, Long>(ComparableComparator.<Long> instance());
	}

	// #################################################
	// ## . . . . . . . MODIFYING MAP . . . . . . . . ##
	// #################################################

	protected BtNavigableMapTestBase put(Long key, Long value) {
		map.put(key, value);
		return this;
	}

	protected BtNavigableMapTestBase remove(Object value) {
		map.remove(value);
		return this;
	}

	protected BtNavigableMapTestBase put(long l) {
		standardPut(l);
		return this;
	}

	protected Long standardPut(Long l) {
		return map.put(l, getStandardValueForKey(l));
	}

	protected void standardRemove(Long l) {
		Long expected = getStandardValueForKey(l);
		Assertions.assertThat(map.remove(l)).as("Wrong value removed for key: " + l).isEqualTo(expected);
		Assertions.assertThat(map.remove(l)).as("Value should be deleted: " + l).isNull();
	}

	protected void assertSize(int expectedSize) {
		Assertions.assertThat(map).hasSize(expectedSize);
	}

	// for map/multiMap
	public static Long getStandardValueForKey(Long key) {
		return key == null ? null : 11 * key;
	}

	protected Long[] longsForRangeRnd(long start, long end, int seed) {
		return permutation(longsForRange(start, end), seed);
	}

	protected Long[] permutation(Long[] values, int seed) {
		int count = values.length;
		while (count-- > 0) {
			seed = Math.abs((BIG_PRIME * (seed + count)) % values.length);
			swap(values, count, seed);
		}

		return values;
	}

	private void swap(Long[] values, int a, int b) {
		Long tmp = values[a];
		values[a] = values[b];
		values[b] = tmp;
	}

	protected Long[] longsForRange(long start, long end) {
		int count = (int) (end - start + 1);
		Long[] result = new Long[count];

		for (int i = 0; i < count; i++) {
			result[i] = start + i;
		}

		return result;
	}

	protected Long[] longsSeries(long start, int count, int diff) {
		Long[] result = new Long[count];

		for (int i = 0; i < count; i++) {
			result[i] = start + i * diff;
		}

		return result;
	}

}
