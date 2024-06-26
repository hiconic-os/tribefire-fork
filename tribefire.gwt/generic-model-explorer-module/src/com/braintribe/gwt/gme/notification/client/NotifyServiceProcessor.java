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
package com.braintribe.gwt.gme.notification.client;

import java.util.function.Supplier;

import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.notification.NotificationBarEventSource;
import com.braintribe.model.notification.Notify;
import com.braintribe.model.processing.notification.api.NotificationFactory;
import com.braintribe.model.processing.service.api.ServiceProcessor;
import com.braintribe.model.processing.service.api.ServiceRequestContext;

/**
 * Local {@link ServiceProcessor} implementation which simply broadcasts the {@link Notify} notifications.
 * @author michel.docouto
 *
 */
@SuppressWarnings("unusable-by-js")
public class NotifyServiceProcessor implements ServiceProcessor<Notify, Object> {
	
	private Supplier<? extends NotificationFactory> notificationFactorySupplier;
	
	/**
	 * Configures the {@link NotificationFactory} used for broadcasting a notification.
	 */
	@Required
	public void setNotificationFactorySupplier(Supplier<? extends NotificationFactory> notificationFactorySupplier) {
		this.notificationFactorySupplier = notificationFactorySupplier;
	}

	@Override
	@SuppressWarnings("unusable-by-js")
	public Object process(ServiceRequestContext requestContext, Notify notify) {
		NotificationFactory notificationFactory = notificationFactorySupplier.get();
		notificationFactory.broadcast(notify.getNotifications(), notificationFactory.createEventSource(NotificationBarEventSource.T));
		
		return null;
	}

}
