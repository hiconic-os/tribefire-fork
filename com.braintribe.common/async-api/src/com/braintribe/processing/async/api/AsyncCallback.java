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
/**
 * 
 */
package com.braintribe.processing.async.api;

import java.util.function.Consumer;
import java.util.function.Function;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

@JsType(namespace = "$tf.async")
@SuppressWarnings("unusable-by-js")
public interface AsyncCallback<T> {

	void onSuccess(T future);
	void onFailure(Throwable t);

	static <T> AsyncCallback<T> of(Consumer<? super T> consumer, Consumer<Throwable> errorHandler) {
		return new AsyncCallback<T>() {
			@Override
			public void onSuccess(T value) {
				consumer.accept(value);
			}

			@Override
			public void onFailure(Throwable t) {
				errorHandler.accept(t);
			}
		};
	}

	@JsMethod(name = "fromErrorHandler")
	static <T> AsyncCallback<T> of(Consumer<Throwable> errorHandler) {
		return of(r -> { /* NO OP */ }, errorHandler);
	}
	
	static <T> AsyncCallback<T> ofConsumer(Consumer<? super T> consumer) {
		return of (consumer, e -> { /* NO OP */});
	}

	/**
	 * Creates a new callback which accepts values of type X, by transforming them using given function to a value of type T and passing them to this
	 * callback.
	 */
	default <X> AsyncCallback<X> adapt(Function<? super X, ? extends T> adapter) {
		return of(x -> {
			T adapted;
			try {
				adapted = adapter.apply(x);
			} catch (Exception e) {
				onFailure(e);
				return;
			}
			onSuccess(adapted);
		}, this::onFailure);
	}

}
