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
package com.braintribe.utils.promise;

import java.util.function.Consumer;

import com.braintribe.processing.async.api.AsyncCallback;
import com.braintribe.processing.async.api.JsPromise;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * helper class that adapts Javascript Promise with AsyncCallback from GWT and from Braintribe
 * 
 * @author Dirk Scheffler
 */
public class JsPromiseCallback<T> implements AsyncCallback<T>, com.google.gwt.user.client.rpc.AsyncCallback<T> {

	private com.braintribe.processing.async.api.JsPromise<T> promise;
	private JavaScriptObject resolve;
	private JavaScriptObject reject;

	public JsPromiseCallback() {
		initPromise();
	}

	private native void initPromise() /*-{
		var that = this;
		this.@JsPromiseCallback::promise = new Promise(function(resolve, reject) {
			that.@JsPromiseCallback::resolve = resolve;
			that.@JsPromiseCallback::reject = reject;
		});
	}-*/;

	@Override
	public native void onSuccess(T future) /*-{
		var f = this.@JsPromiseCallback::resolve;
		f(future);
	}-*/;

	@Override
	@SuppressWarnings("unusable-by-js")
	public native void onFailure(Throwable t) /*-{
		var f = this.@JsPromiseCallback::reject;
		f(t);
	}-*/;

	public JsPromise<T> asPromise() {
		return promise;
	}

	/**
	 * Convenience method to create a {@link JsPromise} for a method which accepts some kind of AsyncCallback.
	 * 
	 * Example: {@code return JsPromiseCallback.promisify(serviceRequest.eval(evaluator)::get)}
	 */
	public static <T> JsPromise<T> promisify(Consumer<? super JsPromiseCallback<T>> asyncCode) {
		JsPromiseCallback<T> callback = new JsPromiseCallback<>();
		asyncCode.accept(callback);
		return callback.asPromise();
	}

}
