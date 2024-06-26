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
package com.braintribe.model.generic.eval;

import java.util.Optional;
import java.util.stream.Stream;

import com.braintribe.common.attribute.AttributeAccessor;
import com.braintribe.common.attribute.MutableTypeSafeAttributes;
import com.braintribe.common.attribute.ReflectedTypeSafeAttributes;
import com.braintribe.common.attribute.TypeSafeAttribute;
import com.braintribe.common.attribute.TypeSafeAttributeEntry;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.gm.model.reason.UnsatisfiedMaybeTunneling;
import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.processing.async.api.AsyncCallback;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

/**
 * 
 * @param <R>
 *            evaluated type
 */
@JsType(namespace = GmCoreApiInteropNamespaces.eval)
@SuppressWarnings("unusable-by-js")
public interface EvalContext<R> extends MutableTypeSafeAttributes, ReflectedTypeSafeAttributes {

	/** Similar to {@link #getReasoned()}, but returns the result directly. We recommend using the reasoned method. */
	@JsMethod(name = "getSynchronous")
	R get() throws EvalException;

	/** Similar to {@link #getReasoned(AsyncCallback)}, but passes the result directly. We recommend using the reasoned method. */
	void get(AsyncCallback<? super R> callback);

	/**
	 * Invokes the underlying request and ignores the result.
	 * <p>
	 * Possible errors while evaluation, whether in the form of an exception, or an unsatisfied {@link Maybe} are logged with
	 * {@link ErrorLoggingCallback}.
	 * 
	 * @see #getReasoned(AsyncCallback)
	 */
	default void executeAsync() {
		getReasoned(ErrorLoggingCallback.instance());
	}

	/**
	 * Executes the evaluation and returns a {@link Maybe} in case of success. If an {@link UnsatisfiedMaybeTunneling} occurs, it is being caught and
	 * its {@link Maybe} is being returned.
	 */
	@JsMethod(name = "getReasonedSynchronous")
	default Maybe<R> getReasoned() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Asynchronously executes the evaluation and notifies either a {@link Maybe} on {@link AsyncCallback#onSuccess(Object)} or a Throwable on
	 * {@link AsyncCallback#onFailure(Throwable)}.
	 * <p>
	 * To simply invoke an asynchronous evaluation, without the need to handle the result, use {@link #executeAsync()}.
	 * 
	 * @see #executeAsync()
	 */
	default void getReasoned(@SuppressWarnings("unused") AsyncCallback<? super Maybe<R>> callback) {
		throw new UnsupportedOperationException();
	}

	@JsIgnore
	default <T, A extends EvalContextAspect<? super T>> EvalContext<R> with(Class<A> aspect, T value) {
		setAttribute(aspect, value);
		return this;
	}

	default <T> EvalContext<R> with(AttributeAccessor<T> accessor, T value) {
		accessor.set(this, value);
		return this;
	}

	@Override
	@SuppressWarnings("unusable-by-js")
	default <A extends TypeSafeAttribute<V>, V> Optional<V> findAttribute(Class<A> attribute) {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("unusable-by-js")
	default <A extends TypeSafeAttribute<? super V>, V> void setAttribute(Class<A> attribute, V value) {
		throw new UnsupportedOperationException();
	}

	@Override
	default <A extends TypeSafeAttribute<V>, V> V getAttribute(Class<A> attribute) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	@SuppressWarnings("unusable-by-js")
	default Stream<TypeSafeAttributeEntry> streamAttributes() {
		throw new UnsupportedOperationException();
	}
}
