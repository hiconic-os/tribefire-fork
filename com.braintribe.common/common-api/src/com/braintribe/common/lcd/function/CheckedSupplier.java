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
package com.braintribe.common.lcd.function;

import jsinterop.annotations.JsType;

/**
 * @author peter.gazdik
 */
@FunctionalInterface
@JsType(namespace = "$tf.util")
public interface CheckedSupplier<T, E extends Throwable> {

	T get() throws E;

	default T uncheckedGet() {
		try {
			return get();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Simple method to wrap supplier code into unchecked one. Example: {@code
	 * 	value1 = CheckSupplier.uncheckedGet(supplier::exceptionThrowingSupplierMethod);
	 * 	value2 = CheckSupplier.uncheckedGet(() -> supplier.exceptionThrowingSupplierMethod(arg1,...));
	 * }
	 * 
	 */
	static <T> T uncheckedGet(CheckedSupplier<T, ?> supplier) {
		return supplier.uncheckedGet();
	}

}
