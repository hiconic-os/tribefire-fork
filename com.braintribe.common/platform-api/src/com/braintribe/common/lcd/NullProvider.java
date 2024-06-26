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
package com.braintribe.common.lcd;

import java.util.function.Supplier;

/**
 * A {@link Supplier} which always returns <tt>null</tt>.
 */
public final class NullProvider<T> implements Supplier<T> {

	private static final NullProvider<?> INSTANCE = new NullProvider<>();

	public static <T> Supplier<T> instance() {
		return (Supplier<T>) INSTANCE;
	}

	@Override
	public T get() {
		return null;
	}
}
