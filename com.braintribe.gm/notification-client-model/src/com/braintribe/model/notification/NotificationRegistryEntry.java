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
package com.braintribe.model.notification;

import java.util.Date;
import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Root model for the notification registry.
 * 
 */

public interface NotificationRegistryEntry extends GenericEntity {

	EntityType<NotificationRegistryEntry> T = EntityTypes.T(NotificationRegistryEntry.class);

	public static final String receivedAt = "receivedAt";
	public static final String wasReadAt = "wasReadAt";
	public static final String eventSource = "eventSource";
	public static final String notifications = "notifications";

	Date getReceivedAt();
	void setReceivedAt(Date receivedAt);

	Date getWasReadAt();
	void setWasReadAt(Date wasReadAt);

	NotificationEventSource getEventSource();
	void setEventSource(NotificationEventSource eventSource);

	List<Notification> getNotifications();
	void setNotifications(List<Notification> notifications);

}