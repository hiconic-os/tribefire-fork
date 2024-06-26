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

import java.util.Deque;
import java.util.Stack;
import java.util.function.Supplier;

/**
 * Because the {@link Stack} class from the standard library implements several other interfaces and actually isn't even
 * an interface itself, sometimes we need a more minimal interface reduced to the minimal requirements of a stack which
 * is provided here. 
 * 
 * @author Neidhart.Orlich
 *
 * @param <T>
 *            Stack element type
 */
public interface MinimalStack<T> extends Supplier<T> {

	void push(T element);

	T peek();
	
	@Override
	default T get() { return peek(); }

	T pop();
	
	boolean isEmpty();
	
	static <E> MinimalStack<E> of(Deque<E> stack){
		return new MinimalStack<E>() {

			@Override
			public void push(E element) {
				stack.push(element);
			}

			@Override
			public E peek() {
				return stack.peek();
			}

			@Override
			public E pop() {
				return stack.pop();
			}

			@Override
			public boolean isEmpty() {
				return stack.isEmpty();
			}
		};
	}
	
	static <E> MinimalStack<E> of(Stack<E> stack){
		return new MinimalStack<E>() {

			@Override
			public void push(E element) {
				stack.push(element);
			}

			@Override
			public E peek() {
				return stack.peek();
			}

			@Override
			public E pop() {
				return stack.pop();
			}

			@Override
			public boolean isEmpty() {
				return stack.isEmpty();
			}
		};
	}
	
	
}
