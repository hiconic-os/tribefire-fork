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
package com.braintribe.gwt.ioc.gme.client;

import java.util.function.Supplier;

import com.braintribe.gwt.gme.constellation.client.TransientGmSession;
import com.braintribe.gwt.gme.servicerequestpanel.client.ServiceRequestConstellationScopedBeanProvider;
import com.braintribe.gwt.gmresource.session.GwtSessionResourceSupport;
import com.braintribe.gwt.gmsession.client.AccessServiceGwtPersistenceGmSession;
import com.braintribe.gwt.security.client.SessionScopedBeanProvider;
import com.braintribe.model.notification.NotificationRegistry;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.model.processing.session.impl.managed.BasicManagedGmSession;
import com.braintribe.provider.PrototypeBeanProvider;

public class Session {
	
	protected static Supplier<AccessServiceGwtPersistenceGmSession> persistenceSession = new SessionScopedBeanProvider<AccessServiceGwtPersistenceGmSession>() {
		@Override
		public AccessServiceGwtPersistenceGmSession create() throws Exception {
			AccessServiceGwtPersistenceGmSession bean = publish(abstractSession.get());
			return bean;
		}
	};
	
	protected static Supplier<TransientGmSession> transientManagedSession = new SessionScopedBeanProvider<TransientGmSession>() {
		@Override
		public TransientGmSession create() throws Exception {
			return publish(prototypeTransientManagedSession.get());
		}
	};
	
	protected static Supplier<TransientGmSession> serviceRequestScopedTransientGmSession = new ServiceRequestConstellationScopedBeanProvider<TransientGmSession>() {
		@Override
		public TransientGmSession create() throws Exception {
			TransientGmSession bean = new TransientGmSession();
			bean.setDynamicAspectProviders(MetaData.dynamicAspectValueProviders.get());
			return bean;
		}
	};
	
	protected static Supplier<AccessServiceGwtPersistenceGmSession> workbenchPersistenceSession = new SessionScopedBeanProvider<AccessServiceGwtPersistenceGmSession>() {
		@Override
		public AccessServiceGwtPersistenceGmSession create() throws Exception {
			AccessServiceGwtPersistenceGmSession bean = publish(abstractSession.get());
			return bean;
		}
	};
	
	protected static Supplier<TransientGmSession> prototypeTransientManagedSession = new PrototypeBeanProvider<TransientGmSession>() {
		@Override
		public TransientGmSession create() throws Exception {
			TransientGmSession bean = new TransientGmSession();
			bean.setDynamicAspectProviders(MetaData.dynamicAspectValueProviders.get());
			return bean;
		}
	};
	
	protected static Supplier<AccessServiceGwtPersistenceGmSession> templateWorkbenchPersistenceSession = new SessionScopedBeanProvider<AccessServiceGwtPersistenceGmSession>() {
		@Override
		public AccessServiceGwtPersistenceGmSession create() throws Exception {
			AccessServiceGwtPersistenceGmSession bean = publish(abstractSession.get());
			return bean;
		}
	};
	
	protected static Supplier<ManagedGmSession> notificationManagedSession = new SessionScopedBeanProvider<ManagedGmSession>() {
		@Override
		public ManagedGmSession create() throws Exception {
			ManagedGmSession bean = publish(new BasicManagedGmSession());

			NotificationRegistry registry = bean.create(NotificationRegistry.T);
			registry.setId(NotificationRegistry.INSTANCE);

			return bean;
		}
	};
	
	private static Supplier<AccessServiceGwtPersistenceGmSession> abstractSession = new PrototypeBeanProvider<AccessServiceGwtPersistenceGmSession>() {
		{
			setAbstract(true);
		}
		@Override
		public AccessServiceGwtPersistenceGmSession create() throws Exception {
			AccessServiceGwtPersistenceGmSession bean = new AccessServiceGwtPersistenceGmSession();
			bean.setResourcesAccessFactory(resourceAccess.get());
			bean.setModelAccessoryResourcesAccessFactory(accessoryResourceAccess.get());
			bean.setDynamicAspectProviders(MetaData.dynamicAspectValueProviders.get());
			bean.setRequestEvaluator(GmRpc.serviceRequestEvaluator.get());
			bean.setUseCases(Runtime.metadataResolverUseCases.get());
			bean.setUserNameSupplier(Providers.userNameProvider.get());
			bean.setUserRolesSupplier(Providers.rolesProvider.get());
			bean.setSessionIdSupplier(Providers.sessionIdProvider.get());
			return bean;
		}
	};
	
	private static Supplier<GwtSessionResourceSupport> resourceAccess = new SessionScopedBeanProvider<GwtSessionResourceSupport>() {
		@Override
		public GwtSessionResourceSupport create() throws Exception {
			GwtSessionResourceSupport bean = publish(restBasedAbstractResourceAccess.get());
			return bean;
		}
	};
	
	private static Supplier<GwtSessionResourceSupport> restBasedAbstractResourceAccess = new PrototypeBeanProvider<GwtSessionResourceSupport>() {
		@Override
		public GwtSessionResourceSupport create() throws Exception {
			GwtSessionResourceSupport bean = new GwtSessionResourceSupport();
			bean.setSessionIdProvider(Providers.sessionIdProvider.get());
			bean.setStreamBaseUrl(Runtime.tribefireServicesUrl.get() + "api/v1/");
			return bean;
		}
	};
	
	private static Supplier<GwtSessionResourceSupport> accessoryResourceAccess = new SessionScopedBeanProvider<GwtSessionResourceSupport>() {
		@Override
		public GwtSessionResourceSupport create() throws Exception {
			GwtSessionResourceSupport bean = publish(restBasedAbstractResourceAccess.get());
			bean.setAccessoryAxis(true);
			return bean;
		}
	};
}
