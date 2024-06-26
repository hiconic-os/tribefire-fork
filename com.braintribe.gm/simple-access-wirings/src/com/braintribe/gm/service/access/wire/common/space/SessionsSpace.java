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
package com.braintribe.gm.service.access.wire.common.space;

import com.braintribe.gm.service.access.SimpleAccessService;
import com.braintribe.gm.service.access.SimpleAccessServiceModeAccessoryFactory;
import com.braintribe.gm.service.access.SimpleResourceProcessor;
import com.braintribe.gm.service.access.api.AccessProcessingConfiguration;
import com.braintribe.gm.service.access.wire.common.contract.AccessProcessingConfigurationContract;
import com.braintribe.gm.service.access.wire.common.contract.CommonAccessProcessingContract;
import com.braintribe.gm.service.wire.common.space.CommonServiceProcessingSpace;
import com.braintribe.model.processing.resource.streaming.access.BasicResourceAccessFactory;
import com.braintribe.model.processing.securityservice.commons.provider.SessionAuthorizationFromUserSessionProvider;
import com.braintribe.model.processing.service.common.context.UserSessionAspect;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSessionFactory;
import com.braintribe.model.processing.session.impl.BasicPersistenceGmSessionFactory;
import com.braintribe.model.resourceapi.base.ResourceRequest;
import com.braintribe.utils.collection.impl.AttributeContexts;
import com.braintribe.utils.stream.api.StreamPipes;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.context.WireContextConfiguration;

@Managed
public class SessionsSpace implements CommonAccessProcessingContract {
	@Import
	private CommonAccessProcessingSpace commonAccessProcessing;
	@Import
	private CommonServiceProcessingSpace commonServiceProcessing;
	@Import
	private AccessProcessingConfigurationContract accessProcessingConfiguration;

	@Override
	public void onLoaded(WireContextConfiguration configuration) {
		CommonAccessProcessingContract.super.onLoaded(configuration);

		accessProcessingConfiguration.registerAccessConfigurer(this::configureServices);
	}

	private void configureServices(AccessProcessingConfiguration config) {
		config.registerAccessRequestProcessor(ResourceRequest.T, simpleResourceProcessor());
	}

	@Managed
	private SimpleResourceProcessor simpleResourceProcessor() {
		SimpleResourceProcessor bean = new SimpleResourceProcessor();
		return bean;
	}

	@Override
	@Managed
	public PersistenceGmSessionFactory sessionFactory() {
		BasicPersistenceGmSessionFactory bean = new BasicPersistenceGmSessionFactory();
		bean.setAccessService(accessService());
		bean.setRequestEvaluator(commonServiceProcessing.evaluator());
		bean.setSessionAuthorizationProvider(sessionAuthorizationProvider());
		bean.setModelAccessoryFactory(modelAccessoryFactory());
		bean.setResourceAccessFactory(resourceAccessFactory());
		return bean;
	}

	@Managed
	public BasicResourceAccessFactory resourceAccessFactory() {
		BasicResourceAccessFactory bean = new BasicResourceAccessFactory();
		bean.setUrlBuilderSupplier(null);
		bean.setStreamPipeFactory(StreamPipes.fileBackedFactory());
		return bean;
	}

	@Managed
	public SessionAuthorizationFromUserSessionProvider sessionAuthorizationProvider() {
		SessionAuthorizationFromUserSessionProvider bean = new SessionAuthorizationFromUserSessionProvider();
		bean.setUserSessionProvider(() -> AttributeContexts.peek().findAttribute(UserSessionAspect.class).orElse(null));
		return bean;
	}

	@Managed
	private SimpleAccessService accessService() {
		return commonAccessProcessing.accessRegistry().getSimpleAccessService();
	}

	@Managed
	private SimpleAccessServiceModeAccessoryFactory modelAccessoryFactory() {
		SimpleAccessServiceModeAccessoryFactory bean = new SimpleAccessServiceModeAccessoryFactory();
		bean.setAccessService(accessService());
		return bean;
	}

}
