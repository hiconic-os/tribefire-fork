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
package com.braintribe.model.generic.reflection;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.braintribe.common.attribute.TypeSafeAttribute;
import com.braintribe.common.attribute.TypeSafeAttributeEntry;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.EvalContextAspect;
import com.braintribe.model.generic.eval.EvalException;
import com.braintribe.model.generic.eval.JsEvalContext;
import com.braintribe.processing.async.api.AsyncCallback;
import com.braintribe.processing.async.api.JsPromise;
import com.braintribe.utils.promise.JsPromiseCallback;

/**
 * @author peter.gazdik
 */
@SuppressWarnings("unusable-by-js")
public class JsEvalContextImpl<R> implements JsEvalContext<R> {

	private final EvalContext<R> delegate;

	public JsEvalContextImpl(EvalContext<R> delegate) {
		this.delegate = delegate;
	}

	@Override
	public JsPromise<R> andGet() {
		return JsPromiseCallback.promisify(this::get);
	}

	@Override
	public JsPromise<Maybe<R>> andGetReasoned() {
		return JsPromiseCallback.promisify(this::getReasoned);
	}

	@Override
	public <A extends TypeSafeAttribute<V>, V> V findOrNull(Class<A> attribute) {
		return delegate.findOrNull(attribute);
	}

	@Override
	public <A extends TypeSafeAttribute<V>, V> V findOrDefault(Class<A> attribute, V defaultValue) {
		return delegate.findOrDefault(attribute, defaultValue);
	}

	@Override
	public <A extends TypeSafeAttribute<V>, V> V findOrSupply(Class<A> attribute, Supplier<V> defaultValueSupplier) {
		return delegate.findOrSupply(attribute, defaultValueSupplier);
	}

	@Override
	public R get() throws EvalException {
		return delegate.get();
	}

	@Override
	public void get(AsyncCallback<? super R> callback) {
		delegate.get(callback);
	}

	@Override
	public Maybe<R> getReasoned() {
		return delegate.getReasoned();
	}

	@Override
	public void getReasoned(AsyncCallback<? super Maybe<R>> callback) {
		delegate.getReasoned(callback);
	}

	@Override
	public <T, A extends EvalContextAspect<? super T>> EvalContext<R> with(Class<A> aspect, T value) {
		return delegate.with(aspect, value);
	}

	@Override
	public <A extends TypeSafeAttribute<V>, V> Optional<V> findAttribute(Class<A> attribute) {
		return delegate.findAttribute(attribute);
	}

	@Override
	public <A extends TypeSafeAttribute<? super V>, V> void setAttribute(Class<A> attribute, V value) {
		delegate.setAttribute(attribute, value);
	}

	@Override
	public <A extends TypeSafeAttribute<V>, V> V getAttribute(Class<A> attribute) {
		return delegate.getAttribute(attribute);
	}

	@Override
	public Stream<TypeSafeAttributeEntry> streamAttributes() {
		return delegate.streamAttributes();
	}

}
