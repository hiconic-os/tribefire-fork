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
package com.braintribe.gwt.gmview.client.js.api;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.braintribe.gm.model.persistence.reflection.api.GetModelEnvironment;
import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gmview.client.js.JsPersistenceSessionFactory;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.persistence.ModelEnvironmentDrivenGmSession;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.processing.async.api.AsyncCallback;
import com.braintribe.processing.async.api.JsPromise;

public class JsPersistenceSessionFactoryImpl implements JsPersistenceSessionFactory{
	
	private final Evaluator<ServiceRequest> remoteEvaluator;
	private final Map<String, Future<PersistenceGmSession>> sessions = new HashMap<>();
	private final Supplier<ModelEnvironmentDrivenGmSession> rawSessionFactory;
	
	public JsPersistenceSessionFactoryImpl(Evaluator<ServiceRequest> remoteEvaluator, Supplier<ModelEnvironmentDrivenGmSession> rawSessionFactory) {
		super();
		this.remoteEvaluator = remoteEvaluator;
		this.rawSessionFactory = rawSessionFactory;
	}

	@Override
	public JsPromise<PersistenceGmSession> openPersistenceSession(String accessId) {
		return acquirePersistenceSession(accessId).toJsPromise();
	}
	
	private Future<PersistenceGmSession> acquirePersistenceSession(String accessId) {
		return sessions.computeIfAbsent(accessId, this::buildSession);
	}
	
	private Future<PersistenceGmSession> buildSession(String accessId) {
		Future<PersistenceGmSession> promise = new Future<>();
		GetModelEnvironment getModelEnvironment = GetModelEnvironment.T.create();
		getModelEnvironment.setAccessId(accessId);
		
		getModelEnvironment.eval(remoteEvaluator).get(AsyncCallback.of( //
				modelEnvironment -> {
					GmMetaModel serviceModel = modelEnvironment.getServiceModel();
					GmMetaModel dataModel = modelEnvironment.getDataModel();

					GmMetaModel aggregatorModel = GmMetaModel.T.create();
					aggregatorModel.setName("virtual-aggregator");
					if (serviceModel != null)
						aggregatorModel.getDependencies().add(serviceModel);
					if (dataModel != null)
						aggregatorModel.getDependencies().add(dataModel);

					GMF.getTypeReflection().deploy(aggregatorModel, AsyncCallback.of(future -> {
						ModelEnvironmentDrivenGmSession session = rawSessionFactory.get();
						session.configureModelEnvironment(modelEnvironment);
						promise.onSuccess(session);
					}, promise::onFailure));
				}, promise::onFailure));
		
		return promise;
	}
	
}
