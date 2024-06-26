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

import com.braintribe.common.attribute.TypeSafeAttribute;
import com.braintribe.common.attribute.TypeSafeAttributeEntry;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.processing.async.api.AsyncCallback;

@SuppressWarnings("unusable-by-js")
public class DelegatingEvalContext<R> implements EvalContext<R> {
	protected EvalContext<R> delegate;

	protected DelegatingEvalContext() {
		
	}
	
	public DelegatingEvalContext(EvalContext<R> delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public R get() throws EvalException {
		return getDelegate().get();
	}
	
	@Override
	public void get(AsyncCallback<? super R> callback) {
		getDelegate().get(callback);
	}
	
	@Override
	public Maybe<R> getReasoned() {
		return getDelegate().getReasoned();
	}
	
	@Override
	public void getReasoned(AsyncCallback<? super Maybe<R>> callback) {
		getDelegate().getReasoned(callback);
	}

	@Override
	public <T, A extends EvalContextAspect<? super T>> EvalContext<R> with(Class<A> aspect, T value) {
		return getDelegate().with(aspect, value);
	}

	@Override
	public <A extends TypeSafeAttribute<V>, V> Optional<V> findAttribute(Class<A> attribute) {
		return getDelegate().findAttribute(attribute);
	}

	@Override
	public <A extends TypeSafeAttribute<? super V>, V> void setAttribute(Class<A> attribute, V value) {
		getDelegate().setAttribute(attribute, value);
	}

	@Override
	public <A extends TypeSafeAttribute<V>, V> V getAttribute(Class<A> attribute) {
		return getDelegate().getAttribute(attribute);
	}

	@Override
	public Stream<TypeSafeAttributeEntry> streamAttributes() {
		return getDelegate().streamAttributes();
	}

	protected EvalContext<R> getDelegate() {
		return delegate;
	}
}
