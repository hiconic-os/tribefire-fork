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

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Holders hold a property. This property is set via the constructor, but can also be set via the {@link #accept(Object)} method.
 *
 * @author michel.docouto
 *
 * @param <E>
 *            The type
 */
public class Holder<E> implements Hub<E>, ManagedValue<E> {

	protected E value;

	public Holder() {
	}

	public Holder(E object) {
		this.value = object;
	}

	public static <V> Holder<V> of(V value) {
		return new Holder<>(value);
	}

	public static <V> Holder<V> fromSuplier(Supplier<? extends V> delegate) {
		return new Holder<V>() {
			@Override
			public V get() {
				return delegate.get();
			}

			@Override
			public void release() {
				/* NOOP */
			}
		};
	}

	@Override
	public void accept(E object) {
		this.value = object;
	}

	@Override
	public E get() {
		return value;
	}

	@Override
	public void release() {
		value = null;
	}

	/**
	 * Sets the value of this holder. This method is kept here because some Spring-based tests rely on this setter method.
	 *
	 * @deprecated use {@link Consumer#accept(Object)} interface
	 */
	@Deprecated
	public void setObject(E object) {
		this.value = object;
	}
}
