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
package com.braintribe.cc.lcd;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Extension of standard {@link Comparator} interface which also features a hash-code computing method.
 *
 * @see HashingComparatorWrapperCodec
 * @see CodingMap#create(HashingComparator)
 */
public interface HashingComparator<E> {

	boolean compare(E e1, E e2);

	int computeHash(E e);

	default boolean isHashImmutable() {
		return true;
	}

	default Set<E> newHashSet() {
		return CodingSet.create(this);
	}

	default Set<E> newHashSet(Supplier<Set<?>> backupFactory) {
		return CodingSet.create(this, backupFactory);
	}

	default Set<E> newLinkedHashSet() {
		return newHashSet(LinkedHashSet::new);
	}

	default <V> Map<E, V> newHashMap() {
		return CodingMap.create(this);
	}

	default <V> Map<E, V> newHashMap(Supplier<Map<?, ?>> backupFactory) {
		return CodingMap.create(this, backupFactory);
	}

	default <V> Map<E, V> newLinkedHashMap() {
		return newHashMap(LinkedHashMap::new);
	}

	default <V> Map<E, V> newConcurrentHashMap() {
		return newHashMap(ConcurrentHashMap::new);
	}

	default EqProxy<E> eqProxy(E subject) {
		return isHashImmutable() ? new HashComparatorImmutableEqProxy<>(this, subject) : //
				new HashComparatorEqProxy<>(this, subject);
	}
}
