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
package com.braintribe.gwt.gmsession.client;

import com.braintribe.model.access.IncrementalAccess;
import com.braintribe.model.access.common.EvaluatingAccessService;
import com.braintribe.model.access.impl.AccessServiceDelegatingAccess;
import com.braintribe.model.accessapi.ModelEnvironment;
import com.braintribe.model.processing.session.api.persistence.AccessDescriptor;
import com.braintribe.model.processing.session.api.persistence.ModelEnvironmentDrivenGmSession;
import com.braintribe.processing.async.api.AsyncCallback;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;

@JsType
@SuppressWarnings("unusable-by-js")
public class AccessServiceGwtPersistenceGmSession extends GwtPersistenceGmSession implements ModelEnvironmentDrivenGmSession {

	protected ModelEnvironment modelEnvironment;

	@Override
	public void configureModelEnvironment(ModelEnvironment me) {
		cleanup();
		configureAccessDescriptor(new AccessDescriptor(me.getDataAccessId(), me.getDataModel(), me.getDataAccessDenotationType()));
		this.modelEnvironment = me;
	}

	@Override
	public void cleanup() {
		super.cleanup();
		this.modelEnvironment = null;

	}

	@JsIgnore
	@Override
	public void configureModelEnvironment(ModelEnvironment modelEnvironment, AsyncCallback<Void> asyncCallback) {
		try {
			configureModelEnvironment(modelEnvironment);
			asyncCallback.onSuccess(null);
		} catch (Throwable t) {
			asyncCallback.onFailure(t);
		}
	}

	@Override
	public ModelEnvironment getModelEnvironment() {
		return modelEnvironment;
	}
	
	@Override
	protected IncrementalAccess getIncrementalAccess() {
		AccessServiceDelegatingAccess access = new AccessServiceDelegatingAccess();
		access.setAccessId(modelEnvironment.getDataAccessId());
		access.setAccessService(new EvaluatingAccessService(getRequestEvaluator()));
		return access;
	}

}
