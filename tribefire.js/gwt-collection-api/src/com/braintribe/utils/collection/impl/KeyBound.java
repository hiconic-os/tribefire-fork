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

import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.SortedMap;
import java.util.SortedSet;

/**
 * This is used as bounds for keys for {@link NavigableMap}and {@link NavigableSet} implementations, which are based on
 * {@link SortedMap} and {@link SortedSet} delegates. In map case, this serves the same purpose as
 * {@link NavigableEntry}, except this only considers they key of the map.
 */
public class KeyBound {

	public static final int BIGGER = 2;
	public static final int BIG = 1;
	public static final int EVEN = 0;
	public static final int SMALL = -1;

	public Object key;
	public int level;

	private KeyBound(Object key, int level) {
		this.key = key;
		this.level = level;
	}

	public static KeyBound upperBound(Object key, boolean inclusive) {
		return new KeyBound(key, inclusive ? BIGGER : SMALL);
	}

	public static KeyBound upBound(Object key, boolean inclusive) {
		return new KeyBound(key, inclusive ? BIG : SMALL);
	}

	public static KeyBound lowerBound(Object key, boolean inclusive) {
		return new KeyBound(key, inclusive ? SMALL : BIG);
	}

	public <T> T cast() {
		return (T) this;
	}

}
