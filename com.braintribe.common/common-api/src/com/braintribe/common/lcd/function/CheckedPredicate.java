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
import java.util.function.Predicate;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;

/**
 * @author peter.gazdik
 */
@FunctionalInterface
@JsType(namespace = "$tf.util")
public interface CheckedPredicate<T, E extends Throwable> {

	boolean test(T t) throws E;

	default CheckedPredicate<T, E> negate() {
		return t -> !test(t);
	}

	default CheckedPredicate<T, E> and(CheckedPredicate<? super T, ? extends E> other) {
		Objects.requireNonNull(other);
		return t -> test(t) && other.test(t);
	}

	@JsIgnore
	default CheckedPredicate<T, E> and(Predicate<? super T> other) {
		Objects.requireNonNull(other);
		return t -> test(t) && other.test(t);
	}

	default CheckedPredicate<T, E> or(CheckedPredicate<? super T, ? extends E> other) {
		Objects.requireNonNull(other);
		return t -> test(t) || other.test(t);
	}

	@JsIgnore
	default CheckedPredicate<T, E> or(Predicate<? super T> other) {
		Objects.requireNonNull(other);
		return t -> test(t) || other.test(t);
	}

}
