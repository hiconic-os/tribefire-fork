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

import java.util.Objects;
import java.util.function.Consumer;

import jsinterop.annotations.JsType;

/**
 * @author peter.gazdik
 */
@FunctionalInterface
@JsType(namespace = "$tf.util")
public interface CheckedConsumer<T, E extends Throwable> {

	void accept(T t) throws E;

	/** Similar to {@link Consumer#andThen(Consumer)} */
	default CheckedConsumer<T, E> andThen(CheckedConsumer<? super T, ? extends E> after) {
		Objects.requireNonNull(after);
		return (T t) -> {
			accept(t);
			after.accept(t);
		};
	}

}
