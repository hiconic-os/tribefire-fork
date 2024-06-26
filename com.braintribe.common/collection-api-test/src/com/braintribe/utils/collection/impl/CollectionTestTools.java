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

public class CollectionTestTools {
	private static final int BIG_PRIME = 65537;

	public static String orderToIteratorName(boolean ascending) {
		return ascending ? "AscendingIterator" : "DescendingIterator";
	}

	public static Long[] permutation(Long[] values, int seed) {
		int count = values.length;
		while (count-- > 0) {
			seed = Math.abs((BIG_PRIME * (seed + count)) % values.length);
			swap(values, count, seed);
		}

		return values;
	}

	private static void swap(Long[] values, int a, int b) {
		Long tmp = values[a];
		values[a] = values[b];
		values[b] = tmp;
	}

	// for map/multiMap
	public static Long getStandardValueForKey(Long key) {
		return key == null ? null : 11 * key;
	}

	public static Long getStandardKeyForValue(Long value) {
		return value == null ? null : value / 11;
	}

	public static Long[] longsForRangeRnd(long start, long end, int seed) {
		return permutation(longsForRange(start, end), seed);
	}

	public static Long[] longsForRange(long start, long end) {
		int count = (int) (end - start + 1);
		Long[] result = new Long[count];

		for (int i = 0; i < count; i++) {
			result[i] = start + i;
		}

		return result;
	}

	public static Long[] longsSeries(long start, int count, int diff) {
		Long[] result = new Long[count];

		for (int i = 0; i < count; i++) {
			result[i] = start + i * diff;
		}

		return result;
	}

}
