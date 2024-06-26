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
import java.util.TreeSet;

/**
 * 
 */
public class ComparatorBasedNavigableMultiMap<K, V> extends NavigableMultiMapBase<K, V> {

	Comparator<K> keyComparator;
	EntryComparator<K, V> entryComparator;

	static final class EntryComparator<K, V> implements Comparator<NavigableEntry<K, V>> {
		private final Comparator<K> keyComparator;
		private final Comparator<V> valueComparator;

		public EntryComparator(Comparator<K> keyComparator, Comparator<V> valueComparator) {
			super();
			this.keyComparator = keyComparator;
			this.valueComparator = valueComparator;
		}

		@Override
		public int compare(NavigableEntry<K, V> o1, NavigableEntry<K, V> o2) {
			return o1.compare(o2, keyComparator, valueComparator);
		}

	}

	public ComparatorBasedNavigableMultiMap(Comparator<? super K> kcmp, Comparator<? super V> vcmp) {
		initialize(kcmp, vcmp);
		this.set = new TreeSet<NavigableEntry<K, V>>(entryComparator);
	}

	private void initialize(Comparator<? super K> kcmp, Comparator<? super V> vcmp) {
		this.keyComparator = new NullHandlingComparator<K>(kcmp);

		Comparator<V> valueComparator = new NullHandlingComparator<V>(vcmp);

		this.entryComparator = new EntryComparator<K, V>(keyComparator, valueComparator);
		this.ascending = true;
	}

	@Override
	protected int compareKeys(K key1, K key2) {
		return keyComparator.compare(key1, key2);
	}

	@Override
	public Comparator<? super K> keyComparator() {
		return keyComparator;
	}

	@Override
	protected int compareEntries(NavigableEntry<K, V> o1, NavigableEntry<K, V> o2) {
		return entryComparator.compare(o1, o2);
	}

}
