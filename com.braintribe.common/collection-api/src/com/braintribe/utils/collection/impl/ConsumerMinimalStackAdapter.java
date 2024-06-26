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

import java.util.function.Consumer;

import com.braintribe.utils.collection.api.MinimalStack;

/**
 * @author Neidhart.Orlich
 *
 * @deprecated Please use any other implementation of MinimalStack. This class was created for backwards compatibility
 *             reasons only to support a specific pattern which is used throughout the system that was abusing consumers
 *             to mimic stack behavior.
 */
@Deprecated
public class ConsumerMinimalStackAdapter<T> implements MinimalStack<T> {
	private final Consumer<T> consumer;

	public ConsumerMinimalStackAdapter(Consumer<T> consumer) {
		super();
		this.consumer = consumer;
	}

	@Override
	public void push(T element) {
		consumer.accept(element);
	}

	@Override
	public T peek() {
		throw new RuntimeException(
				"ConsumerMinimalStackAdapter was tried to be used as actual stack - this is not possible as it was just introduced for backwards compatibility reasons.");
	}

	@Override
	public T pop() {
		consumer.accept(null);
		return null;
	}

	@Override
	public boolean isEmpty() {
		throw new RuntimeException(
				"ConsumerMinimalStackAdapter was tried to be used as actual stack - this is not possible as it was just introduced for backwards compatibility reasons.");

	}

}
