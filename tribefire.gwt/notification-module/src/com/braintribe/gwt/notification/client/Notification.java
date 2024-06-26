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

/**
 * This class transports notification within the client. It can have arbitrary data
 * as payload.
 * 
 * @author Dirk
 */
public class Notification<T> {
	private String targetKey;
	private String type;
	private T data;
	
	public Notification(String targetKey, String type, T data) {
		this.targetKey = targetKey;
		this.data = data;
		this.type = type;
	}
	
	/**
	 * Indicates the type of the data.
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * The key that can used to route this notification to an endpoint with an application.
	 * @see NotificationDistributor 
	 */
	public String getTargetKey() {
		return targetKey;
	}
	
	/**
	 * The payload of the notification. That's the acutal message that is beeing transferred
	 * with this notification.
	 */
	public T getData() {
		return data;
	}
}
