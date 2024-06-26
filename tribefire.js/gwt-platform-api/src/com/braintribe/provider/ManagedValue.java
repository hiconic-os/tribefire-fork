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

import java.util.function.Supplier;

/**
 * In case you just want to give a value without any special {@link #release()} handling, use {@link Holder}.
 */
public interface ManagedValue<T> extends Supplier<T> {

	void release();

	static <V> ManagedValue<V> of(V value) {
		return new Holder<>(value);
	}

	static <V> ManagedValue<V> fromSuplier(Supplier<? extends V> delegate) {
		return new ManagedValue<V>() {
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

}
