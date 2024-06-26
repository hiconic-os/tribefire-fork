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

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;

import com.braintribe.utils.collection.api.NavigableMultiMap;

/**
 * 
 */
public class MultiMapBackedSet<E> extends AbstractSet<E> implements NavigableSet<E> {
	/**
	 * The backing map.
	 */
	private transient NavigableMultiMap<E, ?> m;

	/**
	 * Constructs a map backed by the specified navigable map.
	 */
	MultiMapBackedSet(NavigableMultiMap<E, ?> m) {
		this.m = m;
	}

	@Override
	public Iterator<E> iterator() {
		return m.keySet().iterator();
	}

	@Override
	public Iterator<E> descendingIterator() {
		return m.descendingKeySet().iterator();
	}

	@Override
	public NavigableSet<E> descendingSet() {
		return new MultiMapBackedSet<E>(m.descendingMap());
	}

	@Override
	public int size() {
		return m.size();
	}

	@Override
	public boolean isEmpty() {
		return m.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return m.containsKey(o);
	}

	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("Remove is not supported for " + MultiMapBackedSet.class.getName());
	}

	@Override
	public void clear() {
		m.clear();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
		return new MultiMapBackedSet<E>(m.subMap(fromElement, fromInclusive, toElement, toInclusive));
	}

	@Override
	public NavigableSet<E> headSet(E toElement, boolean inclusive) {
		return new MultiMapBackedSet<E>(m.headMap(toElement, inclusive));
	}

	@Override
	public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
		return new MultiMapBackedSet<E>(m.tailMap(fromElement, inclusive));
	}

	@Override
	public NavigableSet<E> subSet(E fromElement, E toElement) {
		return subSet(fromElement, true, toElement, false);
	}

	@Override
	public NavigableSet<E> headSet(E toElement) {
		return headSet(toElement, false);
	}

	@Override
	public NavigableSet<E> tailSet(E fromElement) {
		return tailSet(fromElement, true);
	}

	@Override
	public Comparator<? super E> comparator() {
		return m.keyComparator();
	}

	@Override
	public E first() {
		return m.firstKey();
	}

	@Override
	public E last() {
		return m.lastKey();
	}

	// NavigableSet API methods

	@Override
	public E lower(E e) {
		return m.lowerKey(e);
	}

	@Override
	public E floor(E e) {
		return m.floorKey(e);
	}

	@Override
	public E ceiling(E e) {
		return m.ceilingKey(e);
	}

	@Override
	public E higher(E e) {
		return m.higherKey(e);
	}

	@Override
	public E pollFirst() {
		Map.Entry<E, ?> e = m.pollFirstEntry();
		return (e == null) ? null : e.getKey();
	}

	@Override
	public E pollLast() {
		Map.Entry<E, ?> e = m.pollLastEntry();
		return (e == null) ? null : e.getKey();
	}

}
