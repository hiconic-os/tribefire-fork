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

import java.util.Objects;

import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.service.api.ServiceRequest;

/**
 * <p>
 * Base class for {@link ServiceRequest}-driven externalizing components.
 * 
 */
public abstract class AbstractExternalizingComponent {

	private Evaluator<ServiceRequest> remoteEvaluator;
	private String externalId;

	/**
	 * <p>
	 * Initializes the externalizer.
	 * 
	 * @param remoteEvaluator
	 *            The remote {@link Evaluator} used to evaluate the {@link ServiceRequest} instances.
	 * @param externalId
	 *            The external processor's id.
	 */
	public AbstractExternalizingComponent(Evaluator<ServiceRequest> remoteEvaluator, String externalId) {
		super();
		Objects.requireNonNull(remoteEvaluator, "remoteEvaluator must not be null");
		Objects.requireNonNull(externalId, "externalId must not be null");
		this.remoteEvaluator = remoteEvaluator;
		this.externalId = externalId;
	}

	protected Evaluator<ServiceRequest> evaluator() {
		return remoteEvaluator;
	}

	protected String externalId() {
		return externalId;
	}

}
