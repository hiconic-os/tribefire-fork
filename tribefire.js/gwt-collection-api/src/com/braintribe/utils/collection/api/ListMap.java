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

import java.util.List;
import java.util.Map;

/**
 * As opposed to {@link MultiMap} implementations of this interface just offer a few utility methods for handling maps
 * with list values. Where a {@link MultiMap} can't have repeated values for the same key, a {@link ListMap} of course
 * can.
 * <p>
 * Be aware that <code>ListMap&lt;K, V></code> implements <code>Map&lt;K, List&lt;V>></code>
 * 
 * @author Neidhart.Orlich
 *
 * @param <K> Key type of the Map
 * @param <V> The value type of the Map will be <code>List&lt;V></code>
 */
public interface ListMap<K, V> extends Map<K, List<V>> {
	/**
	 * If the list that is accessed by the key contains exactly one element, it is returned. No list or an empty list returns null;
	 * 
	 * @throws IllegalStateException if the list contains more than one element. 
	 */
	V getSingleElement(K key);

	/**
	 * Returns <tt>true</tt> iff this map contains given key-value pair.
	 */
	boolean containsKeyValue(K key, V value);

	/**
	 * Removes the first occurrence a single element from the list which is accessed by the key. The result is the same as calling {@link List#remove(Object)} on the resulting list.
	 * 
	 * @return true when the element was contained by the list, false otherwise.
	 */
	boolean removeSingleElement(K key, V value);

	/**
	 * Adds a single element to the list which is accessed by the key. If the key is not yet mapped or is mapped to null, a new List is created first.
	 */
	void putSingleElement(K key, V value);
}
