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
package jsinterop.utils;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsType;

/**
 * @author peter.gazdik
 */
@JsType(namespace = "$tf.util")
public class Lambdas {

	// @formatter:off
	
	// Function

	@JsFunction @FunctionalInterface
	public static interface JsUnaryFunction<T, R> { R call(T t);	}
	public static <T, R> Function<T, R> toJFunction(JsUnaryFunction<T, R> jsLambda) { return jsLambda::call; }

	// BiFunction

	@JsFunction	@FunctionalInterface
	public static interface JsBiFunction<T, U, R> { R call(T t, U u); }
	public static <T, U, R> BiFunction<T, U, R> toJBiFunction(JsBiFunction<T, U, R> jsLambda) { return jsLambda::call; }

	// BinaryOperator

	@JsFunction	@FunctionalInterface
	public static interface JsBinaryOperator<T> { T call(T t, T u); }
	public static <T> BinaryOperator<T> toJBinaryOperator(JsBinaryOperator<T> jsLambda) { return jsLambda::call; }

	// Supplier

	@JsFunction	@FunctionalInterface
	public static interface JsSupplier<T> { T call(); }
	public static <T> Supplier<T> toJSupplier(JsSupplier<T> jsLambda) { return jsLambda::call; }

	// Consumer
	
	@JsFunction	@FunctionalInterface
	public static interface JsConsumer<T> { void call(T t); }
	public static <T> Consumer<T> toJConsumer(JsConsumer<T> jsLambda) { return jsLambda::call; }
	
	// BiConsumer

	@JsFunction	@FunctionalInterface
	public static interface JsBiConsumer<T, U> { void call(T t, U u); }
	public static <T, U> BiConsumer<T, U> toJBiConsumer(JsBiConsumer<T, U> jsLambda) { return jsLambda::call; }

	// Predicate

	@JsFunction	@FunctionalInterface
	public static interface JsPredicate<T> { boolean call(T t); }
	public static <T> Predicate<T> toJPredicate(JsPredicate<T> jsLambda) { return jsLambda::call; }

	// BiPredicate

	@JsFunction	@FunctionalInterface
	public static interface JsBiPredicate<T, U> { boolean call(T t, U u); }
	public static <T, U> BiPredicate<T, U> toJBiPredicate(JsBiPredicate<T, U> jsLambda) { return jsLambda::call; }

	// @formatter:on
}
