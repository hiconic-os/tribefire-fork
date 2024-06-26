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

import java.util.Map;
import java.util.function.Supplier;

import com.braintribe.gwt.gme.constellation.client.expert.CurrentContentViewProvider;
import com.braintribe.gwt.gme.constellation.client.expert.EntityTypeInstantiationActionsProvider;
import com.braintribe.gwt.gme.constellation.client.expert.ModelEnvironmentProvider;
import com.braintribe.gwt.gme.constellation.client.expert.PackagingProvider;
import com.braintribe.gwt.gme.workbench.client.FolderIconProvider;
import com.braintribe.gwt.gmview.action.client.ObjectAssignmentActionDialog;
import com.braintribe.gwt.gmview.client.IconProvider;
import com.braintribe.gwt.gmview.client.parse.ParserWithPossibleValues;
import com.braintribe.gwt.gmview.util.client.MetaDataIconProvider;
import com.braintribe.gwt.gmview.util.client.MetaDataIconProvider.Mode;
import com.braintribe.gwt.gmview.util.client.TypeIconProvider;
import com.braintribe.gwt.ioc.gme.client.expert.bootstrapping.ModelEnvironmentDataProvider;
import com.braintribe.gwt.ioc.gme.client.expert.bootstrapping.WorkbenchDataProvider;
import com.braintribe.gwt.security.client.RolesProvider;
import com.braintribe.gwt.security.client.SessionIdProvider;
import com.braintribe.gwt.security.client.SessionScopedBeanProvider;
import com.braintribe.gwt.security.client.UserFullNameProvider;
import com.braintribe.gwt.security.client.UserNameProvider;
import com.braintribe.gwt.utils.client.FastMap;
import com.braintribe.model.folder.Folder;
import com.braintribe.provider.PrototypeBeanProvider;
import com.braintribe.provider.SingletonBeanProvider;

public class Providers {
	
	public static Supplier<PackagingProvider> packagingProvider = new SingletonBeanProvider<PackagingProvider>() {
		@Override
		public PackagingProvider create() throws Exception {
			PackagingProvider bean = publish(new PackagingProvider());
			bean.setClientUrl(Runtime.tribefireExplorerUrl.get());
			return bean;
		}
	};
	
	protected static Supplier<SessionIdProvider> sessionIdProvider = new SingletonBeanProvider<SessionIdProvider>() {
		@Override
		public SessionIdProvider create() throws Exception {
			SessionIdProvider bean = publish(new SessionIdProvider());
			bean.setSecurityService(Services.securityService.get());
			return bean;
		}
	};
	
	protected static Supplier<RolesProvider> rolesProvider = new SessionScopedBeanProvider<RolesProvider>() {
		@Override
		public RolesProvider create() throws Exception {
			RolesProvider bean = publish(new RolesProvider());
			bean.setSecurityService(Services.securityService.get());
			return bean;
		}
	};
	
	protected static Supplier<WorkbenchDataProvider> workbenchDataProvider = new SessionScopedBeanProvider<WorkbenchDataProvider>() {
		@Override
		public WorkbenchDataProvider create() throws Exception {
			WorkbenchDataProvider bean = publish(new WorkbenchDataProvider());
			bean.setGmSession(Session.persistenceSession.get());
			return bean;
		}
	};
	
	protected static Supplier<ModelEnvironmentProvider> modelEnvironmentProvider = new SingletonBeanProvider<ModelEnvironmentProvider>() {
		@Override
		public ModelEnvironmentProvider create() throws Exception {
			ModelEnvironmentProvider bean = publish(new ModelEnvironmentProvider());
			bean.setModelEnvironmentFutureProvider(modelEnvironmentFutureProvider.get());
			return bean;
		}
	};
	
	protected static Supplier<UserNameProvider> userNameProvider = new SessionScopedBeanProvider<UserNameProvider>() {
		@Override
		public UserNameProvider create() throws Exception {
			UserNameProvider bean = new UserNameProvider();
			bean.setSecurityService(Services.securityService.get());
			return bean;
		}
	};
	
	protected static Supplier<ObjectAssignmentActionDialog> newInstanceProvider = new SessionScopedBeanProvider<ObjectAssignmentActionDialog>() {
		@Override
		public ObjectAssignmentActionDialog create() throws Exception {
			ObjectAssignmentActionDialog bean = publish(new ObjectAssignmentActionDialog());
			bean.setSpotlightPanel(Panels.spotlightPanelNewInstanceProvider.get());
			return bean;
		}
	};
	
	protected static Supplier<UserFullNameProvider> userFullNameProvider = new SessionScopedBeanProvider<UserFullNameProvider>() {
		@Override
		public UserFullNameProvider create() throws Exception {
			UserFullNameProvider bean = new UserFullNameProvider();
			bean.setSecurityService(Services.securityService.get());
			return bean;
		}
	};
	
	protected static Supplier<EntityTypeInstantiationActionsProvider> entityTypeInstantiationActionsProvider = new SessionScopedBeanProvider<EntityTypeInstantiationActionsProvider>() {
		@Override
		public EntityTypeInstantiationActionsProvider create() throws Exception {
			EntityTypeInstantiationActionsProvider bean = publish(new EntityTypeInstantiationActionsProvider());
			bean.setGmSession(Session.persistenceSession.get());
			bean.setInstantiationActionsSupplier(Panels.instantiationActionsProvider.get());
			return bean;
		}
	};
	
	protected static Supplier<ParserWithPossibleValues> parserWithPossibleValues = new SingletonBeanProvider<ParserWithPossibleValues>() {
		@Override
		public ParserWithPossibleValues create() throws Exception {
			ParserWithPossibleValues bean = new ParserWithPossibleValues();
			bean.setLocaleProvider(Startup.localeProvider.get());
			return bean;
		}
	};
	
	protected static Supplier<TypeIconProvider> typeIconProvider = new PrototypeBeanProvider<TypeIconProvider>() {
		@Override
		public TypeIconProvider create() throws Exception {
			TypeIconProvider bean = new TypeIconProvider();
			bean.setDefaultProvider(largestMetaDataIconProvider.get());
			bean.setTypeProvidersMap(typeProvidersMap.get());
			return bean;
		}
	};
	
	protected static Supplier<CurrentContentViewProvider> currentContentViewProvider = new SessionScopedBeanProvider<CurrentContentViewProvider>() {
		@Override
		public CurrentContentViewProvider create() throws Exception {
			return new CurrentContentViewProvider() {
				@Override
				public com.braintribe.gwt.gmview.client.GmContentView get() throws RuntimeException {
					return ProcessDesignerIoc.standAloneProcessDesigner.get();
				}
			};
		}
	};
	
	private static Supplier<ModelEnvironmentDataProvider> modelEnvironmentFutureProvider = new SessionScopedBeanProvider<ModelEnvironmentDataProvider>() {
		@Override
		public ModelEnvironmentDataProvider create() throws Exception {
			ModelEnvironmentDataProvider bean = publish(new ModelEnvironmentDataProvider());
			bean.setBootstrappingRequest(Requests.bootstrappingRequest.get());
			return bean;
		}
	};
	
	private static Supplier<Map<String, IconProvider>> typeProvidersMap = new PrototypeBeanProvider<Map<String, IconProvider>>() {
		@Override
		public Map<String, IconProvider> create() throws Exception {
			Map<String, IconProvider> bean = new FastMap<IconProvider>();
			bean.put(Folder.T.getTypeSignature(), folderIconProvider.get());
			return bean;
		}
	};
	
	private static Supplier<MetaDataIconProvider> largestMetaDataIconProvider = new PrototypeBeanProvider<MetaDataIconProvider>() {
		@Override
		public MetaDataIconProvider create() throws Exception {
			MetaDataIconProvider bean = new MetaDataIconProvider();
			bean.configureGmSession(Session.persistenceSession.get());
			bean.setMode(Mode.largest);
			return bean;
		}
	};
	
	private static Supplier<FolderIconProvider> folderIconProvider = new PrototypeBeanProvider<FolderIconProvider>() {
		@Override
		public FolderIconProvider create() throws Exception {
			FolderIconProvider bean = new FolderIconProvider();
			return bean;
		}
	};

}
