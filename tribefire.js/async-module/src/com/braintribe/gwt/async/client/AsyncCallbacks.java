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
package com.braintribe.gwt.async.client;

import java.util.function.Consumer;
import java.util.function.Function;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author peter.gazdik
 */
public interface AsyncCallbacks {

	/**
	 * Creates a new callback which accepts values of type X, by transforming them using given function to a value of type T and passing them to this
	 * callback.
	 */
	static <T, X> AsyncCallback<X> adapt(AsyncCallback<T> callback, Function<? super X, ? extends T> adapter) {
		return of(x -> {
			T adapted;
			try {
				adapted = adapter.apply(x);
			} catch (Exception e) {
				callback.onFailure(e);
				return;
			}
			callback.onSuccess(adapted);
		}, callback::onFailure);
	}

	static <T extends Object> AsyncCallback<T> of(Consumer<T> success, Consumer<Throwable> failure) {
		return new AsyncCallback<T>() {
			@Override
			public void onFailure(Throwable caught) {
				failure.accept(caught);
			}
			@Override
			public void onSuccess(T result) {
				success.accept(result);
			}
		};
	}

}
