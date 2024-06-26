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
package com.braintribe.gwt.logging.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CycleBufferList<E> implements List<E> {
	private ArrayList<E> elements;
	private int offset;
	private int capacity;
	
	public CycleBufferList(int capacity) {
		elements = new ArrayList<E>(capacity);
		this.capacity = capacity;
	}
	
	@Override
	public boolean add(E element) {
		add(size(), element);
		return true;
	}

	@Override
	public void add(int element, E index) {
		//NOP
	}

	@Override
	public boolean addAll(Collection<? extends E> elements) {
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> elements) {
		return false;
	}

	@Override
	public void clear() {
		elements.clear();
		offset = 0;
	}

	@Override
	public boolean contains(Object element) {
		return elements.contains(element);
	}

	@Override
	public boolean containsAll(Collection<?> elements) {
		return this.elements.containsAll(elements);
	}

	@Override
	public E get(int index) {
		return elements.get((index + offset) % capacity);
	}

	@Override
	public int indexOf(Object element) {
		int index = elements.indexOf(element);
		index -= offset;
		if (index < 0) index += capacity;
		return index;
	}

	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return listIterator();
	}

	@Override
	public int lastIndexOf(Object element) {
		return 0;
	}

	@Override
	public ListIterator<E> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return null;
	}

	@Override
	public E remove(int index) {
		return null;
	}

	@Override
	public boolean remove(Object element) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> elements) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> elements) {
		return false;
	}

	@Override
	public E set(int index, E element) {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public List<E> subList(int start, int end) {
		return null;
	}

	@Override
	public Object[] toArray() {
		return null;
	}

	@Override
	public <T> T[] toArray(T[] array) {
		return null;
	}
	
}
