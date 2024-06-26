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
package com.braintribe.utils.lcd.indexing;

import static com.braintribe.utils.lcd.CollectionTools2.acquireLinkedSet;
import static com.braintribe.utils.lcd.CollectionTools2.acquireList;
import static com.braintribe.utils.lcd.CollectionTools2.newLinkedMap;
import static com.braintribe.utils.lcd.CollectionTools2.newList;
import static com.braintribe.utils.lcd.CollectionTools2.nullSafe;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import com.braintribe.utils.lcd.CollectionTools2;

/**
 * @author peter.gazdik
 */
public class IndexerImpl<V> implements Indexer<V> {

	private final Iterable<? extends V> values;

	public IndexerImpl(Stream<? extends V> values) {
		this(((Stream<V>) values)::iterator);
	}

	public IndexerImpl(Iterable<? extends V> values) {
		this.values = nullSafe(values);
	}

	@Override
	public <K> BoundIndexer<K, V> by(Function<? super V, ? extends K> indexFunction) {
		return new BoundIndexerImpl<>(v -> Arrays.asList(indexFunction.apply(v)));
	}

	@Override
	public <K> BoundIndexer<K, V> byMany(Function<? super V, ? extends Iterable<? extends K>> indexFunction) {
		return new BoundIndexerImpl<>(indexFunction);
	}

	// ############################################
	// ## . . . . . . . . Bound . . . . . . . . .##
	// ############################################

	private class BoundIndexerImpl<K> implements BoundIndexer<K, V> {

		private final Function<? super V, ? extends Iterable<? extends K>> indexFunction;

		public BoundIndexerImpl(Function<? super V, ? extends Iterable<? extends K>> indexFunction) {
			this.indexFunction = indexFunction;
		}

		@Override
		public Map<K, V> unique() {
			return unique(newLinkedMap());
		}

		@Override
		public Map<K, V> unique(Map<K, V> result) {
			Objects.requireNonNull(result, "Map for index cannot be null!");

			V v2;
			for (V v : values) {
				for (K k : indexFunction.apply(v)) {
					if ((v2 = result.put(k, v)) != null) {
						throw new IllegalArgumentException("Duplicate mapping for key '" + k + "'. Values: '" + v2 + "', '" + v + "'");
					}
				}
			}

			return result;
		}

		@Override
		public MultiIndexer<K, V> multi() {
			return new MultiIndexerImpl();
		}

		// ############################################
		// ## . . . . . . . . Multi . . . . . . . . ##
		// ############################################

		private class MultiIndexerImpl implements MultiIndexer<K, V> {

			private boolean distinct;

			@Override
			public MultiIndexer<K, V> distinct() {
				this.distinct = true;
				return this;
			}

			@Override
			public Map<K, List<V>> please() {
				return to(newLinkedMap());
			}

			@Override
			public Map<K, List<V>> to(Map<K, List<V>> map) {
				return distinct ? distinct(map) : regular(map);
			}

			private Map<K, List<V>> distinct(Map<K, List<V>> result) {
				Objects.requireNonNull(result, "Map for index cannot be null!");

				Map<K, Set<V>> distinctResult = newLinkedMap();

				for (V v : values) {
					for (K k : indexFunction.apply(v)) {
						acquireLinkedSet(distinctResult, k).add(v);
					}
				}

				CollectionTools2.mapValues(distinctResult, set -> newList(set), result);

				return result;
			}

			private Map<K, List<V>> regular(Map<K, List<V>> result) {
				for (V v : values) {
					for (K k : indexFunction.apply(v)) {
						acquireList(result, k).add(v);
					}
				}

				return result;
			}

		}
	}
}
