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

import com.braintribe.gwt.gme.constellation.client.MasterDetailConstellation;
import com.braintribe.gwt.gme.notification.client.NotificationConstellation;
import com.braintribe.gwt.gme.notification.client.NotificationFactoryImpl;
import com.braintribe.gwt.gme.notification.client.NotificationView;
import com.braintribe.gwt.security.client.SessionScopedBeanProvider;
import com.braintribe.model.processing.core.expert.impl.ConfigurableGmExpertRegistry;
import com.braintribe.provider.PrototypeBeanProvider;

public class Notifications {
	
	protected static Supplier<NotificationConstellation> notificationsConstellationProvider = new SessionScopedBeanProvider<NotificationConstellation>() {
		@Override
		public NotificationConstellation create() throws Exception {
			NotificationConstellation bean = publish(new NotificationConstellation());
			bean.setMasterDetailConstellationProvider(notificationMasterDetailConstellationProvider);
			bean.setGmSession(Session.notificationManagedSession.get());
			bean.setDataSession(Session.persistenceSession.get());
			bean.setVerticalTabActionBar(Panels.constellationActionBarProvider.get());
			return bean;
		}
	};
	
	public static Supplier<NotificationFactoryImpl> defaultNotificationFactory = new SessionScopedBeanProvider<NotificationFactoryImpl>() {
		@Override
		public NotificationFactoryImpl create() throws Exception {
			NotificationFactoryImpl bean = publish(new NotificationFactoryImpl());
			bean.setSession(Session.notificationManagedSession.get());
			return bean;
		}
	};
	
	public static Supplier<NotificationFactoryImpl> processDesignerNotificationFactory = new SessionScopedBeanProvider<NotificationFactoryImpl>() {
		@Override
		public NotificationFactoryImpl create() throws Exception {
			NotificationFactoryImpl bean = publish(new NotificationFactoryImpl());
			bean.setSession(Session.notificationManagedSession.get());
			return bean;
		}
	};
	
	public static Supplier<NotificationFactoryImpl> notificationFactory = defaultNotificationFactory;
	
	private static Supplier<MasterDetailConstellation> notificationMasterDetailConstellationProvider = new PrototypeBeanProvider<MasterDetailConstellation>() {
		@Override
		public MasterDetailConstellation create() throws Exception {
			MasterDetailConstellation bean = new MasterDetailConstellation();
			bean.setDefaultMasterViewProvider(notificationViewProvider);
			bean.setShowDetailView(false);
			return bean;
		}
	};
	
	private static Supplier<NotificationView> notificationViewProvider = new PrototypeBeanProvider<NotificationView>() {
		@Override
		public NotificationView create() throws Exception {
			NotificationView bean = new NotificationView();
			bean.setCommandRegistry(expertRegistry);
			return bean;
		}
	};
	
	private static Supplier<ConfigurableGmExpertRegistry> complexExpertRegistry = new SessionScopedBeanProvider<ConfigurableGmExpertRegistry>() {
		@Override
		public ConfigurableGmExpertRegistry create() throws Exception {
			ConfigurableGmExpertRegistry bean = publish(new ConfigurableGmExpertRegistry());
			return bean;
		}
	};
	
	public static Supplier<ConfigurableGmExpertRegistry> expertRegistry = complexExpertRegistry;
	
}
