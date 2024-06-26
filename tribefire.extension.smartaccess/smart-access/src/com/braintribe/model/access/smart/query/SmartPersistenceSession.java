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
package com.braintribe.model.access.smart.query;

import com.braintribe.model.access.IncrementalAccess;
import com.braintribe.model.access.smart.SmartAccess;
import com.braintribe.model.access.smart.SmartEagerLoader;
import com.braintribe.model.accessapi.ManipulationResponse;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.processing.session.api.notifying.interceptors.CollectionEnhancer;
import com.braintribe.model.processing.session.api.notifying.interceptors.LazyLoader;
import com.braintribe.model.processing.session.api.resource.ResourceAccess;
import com.braintribe.model.processing.session.impl.persistence.AbstractPersistenceGmSession;
import com.braintribe.model.processing.smart.query.planner.structure.ModelExpert;
import com.braintribe.processing.async.api.AsyncCallback;

/**
 * TODO move elsewhere
 * 
 * @author peter.gazdik
 */
public class SmartPersistenceSession extends AbstractPersistenceGmSession {

	private final SmartAccess smartAccess;
	private final ModelExpert modelExpert;

	public SmartPersistenceSession(SmartAccess smartAccess, ModelExpert modelExpert) {
		this.smartAccess = smartAccess;
		this.modelExpert = modelExpert;

		suspendHistory();

		SmartEagerLoader eagerLoader = new SmartEagerLoader(smartAccess, this, modelExpert);
		interceptors().with(LazyLoader.class).after(CollectionEnhancer.class).add(eagerLoader);
	}

	@Override
	public ManipulationResponse commit() throws GmSessionException {
		throw new UnsupportedOperationException("Cannot commmit, this is a read-only session.");
	}

	@Override
	public void commit(AsyncCallback<ManipulationResponse> callback) {
		throw new UnsupportedOperationException("Cannot commmit, this is a read-only session.");
	}

	@Override
	public ResourceAccess resources() {
		throw new UnsupportedOperationException("Method 'ReadOnlyPersistenceSession.resources' is not supported!");
	}

	@Override
	protected IncrementalAccess getIncrementalAccess() {
		return smartAccess;
	}

	public ModelExpert getModelExpert() {
		return modelExpert;
	}
}
