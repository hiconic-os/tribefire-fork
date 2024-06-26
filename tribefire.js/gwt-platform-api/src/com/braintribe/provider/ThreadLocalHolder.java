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

/**
 * Implementation of the {@link Hub} interface that stores the value in a {@link ThreadLocal} variable.
 * 
 * @author roman.kurmanowytsch
 *
 * @param <T> The type of the object that should be stored in this Hub.
 */
public class ThreadLocalHolder <T> implements Hub<T>, ManagedValue<T> {

	private ThreadLocal<T> tl = new ThreadLocal<>();
	
	@Override
	public T get() {
		return tl.get();
	}

	@Override
	public void accept(T value) {
		if (value == null) {
			tl.remove();
		} else {
			tl.set(value);
		}
	}

	@Override
	public void release() {
		tl.remove();
	}

}
