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
package com.braintribe.utils.collection.api;

import java.util.Collection;
import java.util.Map;

/**
 * 
 */
public interface MultiMap<K, V> extends Map<K, V> {

	/** Puts key and value to this multi-map. Returns the original <tt>value</tt>, if it was already in the multi-map, or <tt>null</tt> otherwise. */
	@Override
	V put(K key, V value);

	/**
	 * Returns <tt>true</tt> iff the map did not contain this entry before this call. This makes more sense for multi-map than
	 * {@link #put(Object, Object)}, which we only implement to stay conform with the {@link Map} interface.
	 */
	boolean put2(K key, V value);

	/** Returns <tt>true</tt> iff this multi-map contains given key-value pair. */
	boolean containsKeyValue(Object key, Object value);

	/** Removes (if present) given key-value pair from the multi-map. Returns <tt>true</tt>, iff such pair was contained in the multi-map. */
	@Override
	boolean remove(Object key, Object value);

	/** Removes all the values associated with given <tt>key</tt>. Return true iff at least one entry was removed. */
	boolean removeAll(Object key);

	/** Removes all the values associated with given <tt>key</tt> and returns a collection of the removed values. */
	Collection<V> removeAll2(Object key);

	/**
	 * This method (inherited from {@link Map}) is not supported and throws an {@link UnsupportedOperationException}. For removing use either
	 * {@link #remove(Object, Object)}, {@link #removeAll(Object)}  or {@link #removeAll2(Object)}.
	 */
	@Override
	V remove(Object key);

	/** Returns some value for given <tt>key</tt>. Some implementations may have rules as to which value is selected as result. */
	@Override
	V get(Object key);

	/** Returns a snapshot of all values for given key. This means that changes in the map are NOT reflected in the returned collection. */
	Collection<V> getAll(K key);

	/** Adds all the values for given key. */
	void putAll(K key, Collection<? extends V> values);

}
