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
package com.braintribe.provider;

import java.util.ArrayDeque;
import java.util.Deque;

public class ThreadLocalStackedHolder<T> implements Hub<T> {

	private final ThreadLocal<Deque<T>> threadLocal = new ThreadLocal<Deque<T>>();

	private String noValueErrorMessage;

	public void setNoValueErrorMessage(String noValueErrorMessage) {
		this.noValueErrorMessage = noValueErrorMessage;
	}

	@Override
	public void accept(T object) throws RuntimeException {
		Deque<T> deque = threadLocal.get();

		if (object != null) {
			if (deque == null)
				threadLocal.set(deque = new ArrayDeque<>());

			deque.addFirst(object);

		} else if (deque != null) {
			deque.pollFirst();

			if (deque.isEmpty())
				threadLocal.remove();
		}

	}

	@Override
	public T get() throws RuntimeException {
		Deque<T> deque = threadLocal.get();

		T result = deque != null ? deque.peekFirst() : null;

		if (result == null && noValueErrorMessage != null)
			throw new IllegalStateException("No value in thread '" + Thread.currentThread().getName() + "'. Custom message: " + noValueErrorMessage);

		return result;
	}

}
