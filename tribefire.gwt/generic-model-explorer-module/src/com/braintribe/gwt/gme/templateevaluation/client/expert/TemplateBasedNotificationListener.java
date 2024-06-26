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
package com.braintribe.gwt.gme.templateevaluation.client.expert;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import com.braintribe.cfg.Required;
//import com.braintribe.gwt.gme.notification.client.CommandListener;
import com.braintribe.gwt.logging.client.ErrorDialog;
import com.braintribe.model.notification.Notification;
import com.braintribe.model.notification.NotificationEventSource;
import com.braintribe.model.notification.NotificationRegistry;
import com.braintribe.model.notification.NotificationRegistryEntry;
import com.braintribe.model.processing.notification.api.NotificationListener;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.processing.async.api.AsyncCallback;

public class TemplateBasedNotificationListener implements NotificationListener {

	private Supplier<? extends ManagedGmSession> gmManagedSessionSupplier;

	@Required
	public void setGmSession(Supplier<? extends ManagedGmSession> gmManagedSessionSupplier) {
		this.gmManagedSessionSupplier = gmManagedSessionSupplier;
	}

	@Override
	public void handleNotifications(List<Notification> notifications) {
		handleNotifications(notifications, null);
	}

	@Override
	public void handleNotifications(final List<Notification> notifications, final NotificationEventSource eventSource) {
		if (notifications == null)
			return;
		
		ManagedGmSession gmManagedSession = gmManagedSessionSupplier.get();
		gmManagedSession.query().entity(NotificationRegistry.T, NotificationRegistry.INSTANCE).require( //
				AsyncCallback.of(registry -> {
					NotificationRegistryEntry entry = gmManagedSession.create(NotificationRegistryEntry.T);
					entry.setId((long) entry.hashCode());
					entry.setReceivedAt(new Date());
					entry.setEventSource(eventSource);
					entry.setNotifications(notifications);
					registry.getEntries().add(entry);
				}, e -> ErrorDialog.show(e.getMessage(), e)));
	}

}
