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

import com.braintribe.gwt.gmview.action.client.DefaultGmContentViewActionManager;
import com.braintribe.gwt.gmview.actionbar.client.ModelEnvironmentFolderLoader;
import com.braintribe.gwt.ioc.gme.client.expert.AccessIdChanger;
import com.braintribe.gwt.security.client.SessionScopedBeanProvider;
import com.braintribe.model.workbench.KnownWorkenchPerspective;
import com.braintribe.provider.PrototypeBeanProvider;
import com.braintribe.provider.SingletonBeanProvider;

public class Controllers {
	
	protected static Supplier<ModelEnvironmentFolderLoader> actionBarFolderLoader = new SessionScopedBeanProvider<ModelEnvironmentFolderLoader>() {
		@Override
		public ModelEnvironmentFolderLoader create() throws Exception {
			ModelEnvironmentFolderLoader bean = publish(new ModelEnvironmentFolderLoader());
			bean.setKnownWorkbenchPerspective(KnownWorkenchPerspective.actionBar);
			bean.setWorkbenchPerspectiveFutureProvider(Providers.workbenchDataProvider.get());
			return bean;
		}
	};
	
	//for some reason, this must not be deleted...
	@SuppressWarnings("unused")
	private static Supplier<DefaultGmContentViewActionManager> pdActionManager = new SingletonBeanProvider<DefaultGmContentViewActionManager>() {
		@Override
		public DefaultGmContentViewActionManager create() throws Exception {
			DefaultGmContentViewActionManager bean = publish(abstractActionManager.get());
			bean.setPersistenceSession(Session.persistenceSession.get());
			bean.setWorkbenchSession(Session.workbenchPersistenceSession.get());
			bean.setExternalActionProviders(Actions.externalActionProviders.get());
			bean.setPrepareDeleteEntityAction(false);
			bean.setPrepareInstantiateEntityAction(false);
			bean.addActionPeformanceListener(Constellations.explorerConstellationProvider.get());
			bean.setInstantiationActionHandler(Constellations.explorerConstellationProvider.get());
			bean.setInstantiationActionsProvider(Providers.entityTypeInstantiationActionsProvider.get());
			return bean;
		}
	};
	
	protected static Supplier<AccessIdChanger> accessIdChanger = new SessionScopedBeanProvider<AccessIdChanger>() {
		@Override
		public AccessIdChanger create() throws Exception {
			AccessIdChanger bean = publish(new AccessIdChanger());
			bean.setGmSession(Session.persistenceSession.get());
			bean.setModelEnvironmentChanger(Providers.modelEnvironmentProvider.get());
			return bean;
		}
	};
	
	private static Supplier<DefaultGmContentViewActionManager> abstractActionManager = new PrototypeBeanProvider<DefaultGmContentViewActionManager>() {
		{
			setAbstract(true);
		}

		@Override
		public DefaultGmContentViewActionManager create() throws Exception {
			DefaultGmContentViewActionManager bean = new DefaultGmContentViewActionManager();
			bean.setInstanceSelectionFutureProvider(Panels.gimaSelectionConstellationSupplier);
			bean.setActionMenuBuilder(UiElements.defaultActionMenuBuilder);
			bean.setViewSituationResolver(ViewSituationResolution.viewSituationResolver);
			bean.setExternalContentViewContexts(Actions.contentViewContexts.get());
			bean.setFolderLoader(actionBarFolderLoader.get());
			return bean;
		}
	};

}
