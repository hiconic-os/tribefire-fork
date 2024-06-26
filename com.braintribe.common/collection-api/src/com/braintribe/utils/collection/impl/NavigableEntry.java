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

import java.util.Comparator;
import java.util.Map;

/**
 * 
 */
public class NavigableEntry<K, V> implements Map.Entry<K, V> {

	public static final byte BIGGER = 2;
	public static final byte BIG = 1;
	public static final byte EVEN = 0;
	public static final byte SMALL = -1;

	public K key;
	public V value;
	public byte keyLevel, valueLevel;

	public NavigableEntry(K key, V value) {
		this(key, EVEN, value, EVEN);
	}

	public NavigableEntry(K key, byte keyLevel, V value, byte valueLevel) {
		this.key = key;
		this.keyLevel = keyLevel;
		this.value = value;
		this.valueLevel = valueLevel;
	}

	@Override
	public final K getKey() {
		return key;
	}

	@Override
	public final V getValue() {
		return value;
	}

	@Override
	public final V setValue(V value) {
		V oldValue = this.value;
		this.value = value;
		return oldValue;
	}

	/**
	 * Compares this entry with one given as parameter. The comparators provided are expected to be able to handle
	 * <tt>null</tt> values.
	 */
	public final int compare(NavigableEntry<K, V> other, Comparator<K> keyCmp, Comparator<V> valCmp) {
		int cmp = keyCmp.compare(this.key, other.key);
		if (cmp != 0) {
			return cmp;
		}

		cmp = keyLevel - other.keyLevel;
		if (cmp != 0) {
			return cmp;
		}

		cmp = valCmp.compare(this.value, other.value);
		if (cmp != 0) {
			return cmp;
		}

		return this.valueLevel - other.valueLevel;
	}

	public NavigableEntry<K, V> higherBorder() {
		NavigableEntry<K, V> result = copy();

		if (result.keyLevel == BIG) {
			result.keyLevel = BIGGER;
			return result;
		}

		if (result.valueLevel == BIG) {
			result.valueLevel = BIGGER;
		}
		return result;
	}

	protected NavigableEntry<K, V> copy() {
		return new NavigableEntry<K, V>(key, keyLevel, value, valueLevel);
	}

    @Override
	public final String toString() { 
    	return getKey() + "=" + getValue();
    }
}

abstract class ComparableNavigableEntry<K, V> extends NavigableEntry<K, V> implements Comparable<ComparableNavigableEntry<K, V>> {

	public ComparableNavigableEntry(K key, byte keyLevel, V value, byte valueLevel) {
		super(key, keyLevel, value, valueLevel);
	}

	@Override
	public int compareTo(ComparableNavigableEntry<K, V> other) {
		int cmp = compareKeys(this.key, other.key);
		if (cmp != 0) {
			return cmp;
		}

		cmp = keyLevel - other.keyLevel;
		if (cmp != 0) {
			return cmp;
		}

		cmp = compareValues(this.value, other.value);
		if (cmp != 0) {
			return cmp;
		}

		return this.valueLevel - other.valueLevel;
	}

	protected abstract int compareKeys(K k1, K k2);

	protected abstract int compareValues(V v1, V v2);

}
