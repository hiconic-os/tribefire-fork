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
package com.braintribe.model.processing.service.api;

import java.util.ArrayList;
import java.util.List;

import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.EvalContextAspect;
import com.braintribe.model.generic.eval.EvalException;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.service.api.CompositeRequest;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.model.service.api.result.CompositeResponse;
import com.braintribe.model.service.api.result.ResponseEnvelope;
import com.braintribe.model.service.api.result.ServiceResult;
import com.braintribe.processing.async.api.AsyncCallback;

public class CompositeRequestEvaluator implements Evaluator<ServiceRequest> {
	private List<AsyncCallback<?>> callbacks = new ArrayList<>();
	private CompositeRequest request;
	
	/**
	 * @deprecated {@link CompositeRequest} always requires authentication.
	 */
	@SuppressWarnings("unused")
	@Deprecated
	public CompositeRequestEvaluator(boolean authorized) {
		this();
	}
	
	public CompositeRequestEvaluator() {
		request = CompositeRequest.T.create();
		request.setParallelize(false);
	}
	
	@Override
	public <T> EvalContext<T> eval(ServiceRequest evaluable) {
		request.getRequests().add(evaluable);
		final int position = callbacks.size();
		callbacks.add(null);

		return new EvalContext<T>() {

			@Override
			public T get() throws EvalException {
				throw new UnsupportedOperationException("not supported in composite building");
			}

			@Override
			public void get(AsyncCallback<? super T> callback) {
				callbacks.set(position, callback);
			}

			@Override
			public <U, A extends EvalContextAspect<? super U>> EvalContext<T> with(Class<A> aspect, U value) {
				return this;
			}
			
		};
	}
	
	protected void processResults(CompositeResponse compositeResponse) {
		int index = 0;
		for (AsyncCallback<?> individualCallback: callbacks) {
			if (individualCallback != null) {
				@SuppressWarnings("unchecked")
				AsyncCallback<Object> castedCallback = (AsyncCallback<Object>) individualCallback;
				ServiceResult serviceResult = compositeResponse.getResults().get(index);
				ResponseEnvelope responseEnvelope = (serviceResult != null) ? serviceResult.asResponse() : null;
				if (responseEnvelope != null) {
					castedCallback.onSuccess(responseEnvelope.getResult());
				}
			}
			
			index++;
		}
	}
	
	public EvalContext<? extends CompositeResponse> eval(Evaluator<ServiceRequest> evaluator) {
		return new CompositeEvalContext<>(request.eval(evaluator));
	}
	
	private class CompositeEvalContext<T extends CompositeResponse> implements EvalContext<T> {
		final EvalContext<T> delegate;
		
		public CompositeEvalContext(EvalContext<T> delegate) {
			super();
			this.delegate = delegate;
		}

		@Override
		public T get() throws EvalException {
			T compositeResponse = delegate.get();
			processResults(compositeResponse);
			return compositeResponse;
		}

		@Override
		public void get(final AsyncCallback<? super T> callback) {
			delegate.get(new AsyncCallback<T>() {
				@Override
				public void onSuccess(T compositeResponse) {
					processResults(compositeResponse);
					callback.onSuccess(compositeResponse);
				}

				@Override
				public void onFailure(Throwable t) {
					callback.onFailure(t);
				}
			});
		}
		
		@Override
		public Maybe<T> getReasoned() {
			Maybe<T> maybe = delegate.getReasoned();
			
			if (maybe.isSatisfied()) {
				CompositeResponse compositeResponse = maybe.get();
				processResults(compositeResponse);
			}
			
			return maybe;
		}
		
		@Override
		public void getReasoned(AsyncCallback<? super Maybe<T>> callback) {
			delegate.getReasoned(AsyncCallback.of(
				m -> {
					if (m.isSatisfied()) {
						CompositeResponse compositeResponse = m.get();
						processResults(compositeResponse);
					}
					callback.onSuccess(m);
				},
				t-> {
					callback.onFailure(t);
				})
			);
		}

		@Override
		public <X, A extends EvalContextAspect<? super X>> EvalContext<T> with(Class<A> aspect, X value) {
			delegate.with(aspect, value);
			return null;
		}
	}
}
