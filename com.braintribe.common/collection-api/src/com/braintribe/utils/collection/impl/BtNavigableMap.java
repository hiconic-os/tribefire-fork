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
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @deprecated This was only created for GWT compatibility. GWT now supports {@link TreeMap}, so use that!!!
 */
@Deprecated
public class BtNavigableMap<K, V> extends AbstractNavigableMap<K, V> {

	public BtNavigableMap() {
		this(ComparableComparator.<K> unboundedInstance());
	}

	public BtNavigableMap(Comparator<? super K> keyComparator) {
		super(new NullHandlingComparator<K>(keyComparator));
	}

	@Override
	public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
		return new BtNavigableSubMap(false, fromKey, fromInclusive, false, toKey, toInclusive, map);
	}

	@Override
	public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
		return new BtNavigableSubMap(true, null, true, false, toKey, inclusive, map);
	}

	@Override
	public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
		return new BtNavigableSubMap(false, fromKey, inclusive, true, null, inclusive, map);
	}

	class BtNavigableSubMap extends AbstractNavigableMap<K, V> {

		private final SortedMap<K, V> unboundedMap;
		private final boolean fromStart, toEnd;
		private final boolean fromInc, toInc;
		private final K from, to;
		private final KeyBound fromB, toB;

		public BtNavigableSubMap(boolean fromStart, K fromKey, boolean fromInc, boolean toEnd, K toKey, boolean toInc,
				SortedMap<K, V> unboundedMap) {

			super(BtNavigableMap.this.keyComparator, BtNavigableMap.this.boundComparator);
			this.unboundedMap = unboundedMap;

			this.fromStart = fromStart;
			this.toEnd = toEnd;
			this.from = fromKey;
			this.to = toKey;
			this.fromInc = fromInc;
			this.toInc = toInc;

			/* These will actually not have type K, but it will be Bounds. I need K cause I use this as map boundaries.
			 * The comparator used is able to handle the bounds in the right way. */
			K _from, _to;

			if (fromStart) {
				_from = null;
				_to = KeyBound.upperBound(toKey, toInc).cast();
				this.map = unboundedMap.headMap(_to);

			} else if (toEnd) {
				_from = KeyBound.lowerBound(fromKey, fromInc).cast();
				_to = null;
				this.map = unboundedMap.tailMap(_from);

			} else {
				_from = KeyBound.lowerBound(fromKey, fromInc).cast();
				_to = KeyBound.upperBound(toKey, toInc).cast();
				this.map = unboundedMap.subMap(_from, _to);
			}

			fromB = (KeyBound) _from;
			toB = (KeyBound) _to;
		}

		@Override
		public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
			validateLow(fromKey, fromInclusive);
			validateHigh(toKey, toInclusive);

			return new BtNavigableSubMap(false, fromKey, fromInclusive, false, toKey, toInclusive, unboundedMap);
		}

		@Override
		public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
			validateLow(toKey, inclusive);
			validateHigh(toKey, inclusive);

			return new BtNavigableSubMap(fromStart, from, fromInc, false, toKey, inclusive, unboundedMap);
		}

		@Override
		public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
			validateLow(fromKey, inclusive);
			validateHigh(fromKey, inclusive);

			return new BtNavigableSubMap(false, fromKey, inclusive, toEnd, to, toInc, unboundedMap);
		}

		@Override
		public Map.Entry<K, V> lowerEntry(K key) {
			if (tooHigh(key, true)) {
				return lastEntry();
			}

			return tooLow(key, true) ? null : lastEntry(headMap(key));
		}

		@Override
		public K lowerKey(K key) {
			if (tooHigh(key, true)) {
				return lastKey(map);
			}

			return tooLow(key, true) ? null : lastKey(map.headMap(key));
		}

		@Override
		public Map.Entry<K, V> floorEntry(K key) {
			if (tooHigh(key, true)) {
				return lastEntry();
			}

			return tooLow(key, true) ? null : lastEntry(headMap(key, true));
		}

		@Override
		public K floorKey(K key) {
			if (tooHigh(key, true)) {
				return lastKey(map);
			}

			return tooLow(key, true) ? null : lastKey(headMap(key, true));
		}

		@Override
		public Map.Entry<K, V> ceilingEntry(K key) {
			if (tooLow(key, false)) {
				return firstEntry();
			}

			return tooHigh(key, true) ? null : firstEntry(tailMap(key));
		}

		@Override
		public K ceilingKey(K key) {
			if (tooLow(key, false)) {
				return firstKey(map);
			}

			return tooHigh(key, true) ? null : firstKey(tailMap(key));
		}

		@Override
		public Map.Entry<K, V> higherEntry(K key) {
			if (tooLow(key, false)) {
				return firstEntry();
			}

			return tooHigh(key, true) ? null : firstEntry(tailMap(key, false));
		}

		@Override
		public K higherKey(K key) {
			if (tooLow(key, false)) {
				return firstKey(map);
			}

			return tooHigh(key, true) ? null : firstKey(tailMap(key, false));
		}

		private void validateLow(K fromKey, boolean fromInclusive) {
			if (tooLow(fromKey, fromInclusive)) {
				throw new RuntimeException("'from' too low");
			}
		}

		private boolean tooLow(K fromKey, boolean fromInclusive) {
			if (fromStart) {
				return false;
			}

			int cmp = boundComparator.compareBounds(fromB, KeyBound.lowerBound(fromKey, fromInclusive));
			return cmp > 0;
		}

		private void validateHigh(K toKey, boolean toInclusive) {
			if (tooHigh(toKey, toInclusive)) {
				throw new RuntimeException("'to' too low");
			}
		}

		private boolean tooHigh(K toKey, boolean toInclusive) {
			if (toEnd) {
				return false;
			}

			int cmp = boundComparator.compareBounds(toB, KeyBound.upBound(toKey, toInclusive));
			return cmp <= 0;
		}
	}

}
