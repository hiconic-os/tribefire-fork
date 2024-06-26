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

import com.braintribe.gwt.ioc.gme.client.expert.ManipulationListenerAndForwarder;
import com.braintribe.gwt.processdesigner.client.ProcessDesigner;
import com.braintribe.gwt.security.client.SessionScopedBeanProvider;
import com.braintribe.provider.PrototypeBeanProvider;

public class ProcessDesignerIoc {
	
	public static Supplier<ProcessDesigner> standAloneProcessDesigner = new SessionScopedBeanProvider<ProcessDesigner>() {
		@Override
		public ProcessDesigner create() throws Exception {
			ProcessDesigner bean = publish(new ProcessDesigner());
			bean.configureGmSession(Session.persistenceSession.get());
			bean.setWorkbenchSession(Session.templateWorkbenchPersistenceSession.get());
			bean.setQuickAccessPanelProvider(Panels.spotlightPanelProvider);
			bean.setAccessIdChanger(Controllers.accessIdChanger.get());
			bean.setExternalSessionConsumer(manipulationListenerAndForwarder.get());
			return bean;
		}
	};
	
	public static Supplier<ProcessDesigner> processDesigner = new SessionScopedBeanProvider<ProcessDesigner>() {
		@Override
		public ProcessDesigner create() throws Exception {
			ProcessDesigner bean = publish(new ProcessDesigner());
			bean.configureGmSession(Session.persistenceSession.get());
			bean.setWorkbenchSession(Session.templateWorkbenchPersistenceSession.get());
			bean.setQuickAccessPanelProvider(Panels.spotlightPanelProvider);
			bean.setAccessIdChanger(Controllers.accessIdChanger.get());
			bean.setExternalSessionConsumer(manipulationListenerAndForwarder.get());
			return bean;
		}
	};
	
	private static Supplier<ManipulationListenerAndForwarder> manipulationListenerAndForwarder = new PrototypeBeanProvider<ManipulationListenerAndForwarder>() {
		@Override
		public ManipulationListenerAndForwarder create() throws Exception {
			ManipulationListenerAndForwarder bean = new ManipulationListenerAndForwarder();
			bean.setSession(Session.persistenceSession.get());
			return bean;
		}
	};
	
}