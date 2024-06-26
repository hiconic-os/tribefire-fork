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
package com.braintribe.gwt.notification.client;

import java.util.HashMap;
import java.util.Map;

import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.DisposableBean;

/**
 * This implementation of {@link NotificationListener} distributes the {@link Notification} instances
 * based on the {@link Notification#getTargetKey()} property. This is the way how {@link Notification}
 * instances can reach an end point in an application. 
 * @author Dirk
 *
 */
public class NotificationDistributor implements NotificationListener<Object>, DisposableBean {
	private Map<String, NotificationListener<?>> targetListeners;

	@SuppressWarnings("rawtypes")
	@Override
	public void onNotificationReceived(Notification<Object> notification) {
		if (targetListeners != null) {
			NotificationListener listener = targetListeners.get(notification.getTargetKey());
			if (listener != null)
				listener.onNotificationReceived(notification);
		}
	}
	
	/**
	 * Configures the {@link NotificationListener} instances that are associated to
	 * a targetKey that must match with {@link Notification#getTargetKey()} when
	 * a {@link Notification} is being distributed.
	 */
	@Configurable
	public void setTargetListeners(Map<String, NotificationListener<?>> targetListeners) {
		this.targetListeners = targetListeners;
	}
	
	/**
	 * Adds a single {@link NotificationListener} that is associated to
	 * a targetKey that must match with {@link Notification#getTargetKey()} when a 
	 * {@link Notification} is being distributed.
	 */
	@Configurable
	public void addTargetListener(String targetKey, NotificationListener<?> listener) {
		if (targetListeners == null) 
			targetListeners = new HashMap<>();
		
		targetListeners.put(targetKey, listener);
	}
	
	/**
	 * IOC cleanup method
	 */
	@Override
	public void disposeBean() throws Exception {
		if (targetListeners != null) {
			targetListeners.clear();
			targetListeners = null;
		}
	}
}
