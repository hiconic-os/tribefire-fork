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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import com.braintribe.utils.collection.api.IStack;
import com.braintribe.utils.collection.api.Stack;

/**
 * {@link ArrayList} based implementation of {@link IStack}. This has an advantage over e.g. {@link ArrayDeque} that it supports <tt>null</tt> as a
 * valid element.
 * 
 * @author peter.gazdik
 * 
 * @implSpec not thread-safe
 */
public class ArrayStack<E> implements IStack<E>, Stack<E>, Iterable<E> {

	private final List<E> list = new ArrayList<>();

	@Override
	public void push(E e) {
		list.add(e);
	}

	@Override
	public E peek() {
		return list.get(lastIndex());
	}

	@Override
	public E pop() {
		if (list.isEmpty())
			throw new NoSuchElementException();

		return list.remove(lastIndex());
	}

	private int lastIndex() {
		return list.size() - 1;
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		int size = list.size();
		ListIterator<E> li = list.listIterator(size);
		return Stream.generate(li::previous).limit(size).iterator();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + list;
	}

}
