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
package com.braintribe.cartridge.common.processing.externalizer;

import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.service.api.DispatchableRequest;
import com.braintribe.model.service.api.ServiceRequest;

/**
 * <p>
 * Base class for {@link DispatchableRequest}-driven externalizing components.
 * 
 */
public abstract class AbstractExternalizingDispatchableComponent<R extends DispatchableRequest> extends AbstractExternalizingComponent {

	private ExternalizingEvaluator<R> evaluator;

	/**
	 * <p>
	 * Initializes the externalizer.
	 * 
	 * @param remoteEvaluator
	 *            The remote {@link Evaluator} used to evaluate the {@link ServiceRequest} instances.
	 * @param externalId
	 *            The external processor's id.
	 */
	public AbstractExternalizingDispatchableComponent(Evaluator<ServiceRequest> remoteEvaluator, String externalId) {
		super(remoteEvaluator, externalId);
		this.evaluator = new ExternalizingEvaluator<R>(remoteEvaluator, externalId);
	}

	@Override
	protected Evaluator<ServiceRequest> evaluator() {
		Evaluator<?> dispatchingEvaluator = evaluator;
		return (Evaluator<ServiceRequest>) dispatchingEvaluator;
	}

	private static class ExternalizingEvaluator<D extends DispatchableRequest> implements Evaluator<D> {

		private Evaluator<ServiceRequest> remoteEvaluator;
		private String externalId;

		public ExternalizingEvaluator(Evaluator<ServiceRequest> remoteEvaluator, String externalId) {
			super();
			this.remoteEvaluator = remoteEvaluator;
			this.externalId = externalId;
		}

		@Override
		public <T> EvalContext<T> eval(D request) {
			String serviceId = request.getServiceId();
			if (serviceId == null) {
				request.setServiceId(externalId);
			}
			return remoteEvaluator.eval(request);
		}

	}

}
