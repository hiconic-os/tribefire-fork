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
package com.braintribe.model.processing.vde.evaluator.impl.service;

import static java.util.Objects.requireNonNull;

import com.braintribe.gm.model.svd.EvaluateRequest;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.value.EntityReference;
import com.braintribe.model.processing.vde.evaluator.api.ValueDescriptorEvaluator;
import com.braintribe.model.processing.vde.evaluator.api.VdeContext;
import com.braintribe.model.processing.vde.evaluator.api.VdeEvaluationMode;
import com.braintribe.model.processing.vde.evaluator.api.VdeResult;
import com.braintribe.model.processing.vde.evaluator.api.aspects.RequestEvaluatorAspect;
import com.braintribe.model.processing.vde.evaluator.api.aspects.SessionAspect;
import com.braintribe.model.processing.vde.evaluator.impl.VdeResultImpl;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.processing.async.api.AsyncCallback;

/**
 * {@link ValueDescriptorEvaluator} for {@link EntityReference}
 * 
 */
public class EvaluateRequestVde implements ValueDescriptorEvaluator<EvaluateRequest> {

	private static EvaluateRequestVde instance = null;

	protected EvaluateRequestVde() {
		// empty
	}

	public static EvaluateRequestVde getInstance() {
		if (instance == null) {
			instance = new EvaluateRequestVde();
		}
		return instance;
	}

	@Override
	public VdeResult evaluate(VdeContext context, EvaluateRequest vd) {
		ServiceRequest request = resolveRequest(vd);
		Evaluator<ServiceRequest> evaluator = resolveEvaluator(context);

		if (evaluator == null) {
			if (context.getEvaluationMode() == VdeEvaluationMode.Preliminary)
				return new VdeResultImpl("No evaluator provided in context");
			else
				throw new IllegalStateException("No evaluator provided in context");
		}

		try {
			Object result = evaluator.eval(request).get();

			return new VdeResultImpl(result, false);

		} catch (Exception e) {
			throw new RuntimeException("Error while evaluating request: " + request, e);
		}
	}

	@Override
	public void evaluateAsync(VdeContext context, EvaluateRequest vd, AsyncCallback<VdeResult> callback) {
		try {
			_evaluateAsync(context, vd, callback);
		} catch (Exception e) {
			callback.onFailure(e);
		}
	}

	private void _evaluateAsync(VdeContext context, EvaluateRequest vd, AsyncCallback<VdeResult> callback) {
		ServiceRequest request = resolveRequest(vd);
		Evaluator<ServiceRequest> evaluator = resolveEvaluator(context);

		if (evaluator == null) {
			if (context.getEvaluationMode() == VdeEvaluationMode.Preliminary) {
				callback.onSuccess(new VdeResultImpl("No evaluator provided in context"));
				return;
			}

			throw new IllegalStateException("No evaluator provided in context");
		}

		evaluator.eval(request).get(callback.adapt(VdeResultImpl::new));
	}

	private ServiceRequest resolveRequest(EvaluateRequest vd) {
		return requireNonNull(vd.getRequest(), "EvaluateRequest.request cannot be null.");
	}

	private Evaluator<ServiceRequest> resolveEvaluator(VdeContext context) {
		Evaluator<ServiceRequest> evaluator = context.get(RequestEvaluatorAspect.class);

		if (evaluator == null)
			evaluator = context.get(SessionAspect.class);

		return evaluator;
	}

}
