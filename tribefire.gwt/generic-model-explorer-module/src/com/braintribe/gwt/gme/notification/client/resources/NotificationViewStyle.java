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
package com.braintribe.gwt.gme.notification.client.resources;

import com.google.gwt.resources.client.CssResource;

public interface NotificationViewStyle extends CssResource {

	@ClassName("notificationView")
	String parent();

	@ClassName("notificationView-group")
	String group();

	@ClassName("notificationView-header")
	String head();

	@ClassName("notificationView-ruler")
	String ruler();

	@ClassName("notificationView-source")
	String source();

	@ClassName("notificationView-caption")
	String caption();

	@ClassName("notificationView-unread")
	String unread();

	@ClassName("notificationView-messages")
	String body();

	@ClassName("notificationView-item")
	String item();

	@ClassName("notificationView-command")
	String command();

	@ClassName("notificationView-item-text")
	String itemText();

	@ClassName("notificationView-item-sel")
	String itemSel();

	@ClassName("notificationView-collapsed")
	String collapsed();

	@ClassName("notificationView-expanded")
	String expanded();

	@ClassName("notificationView-info")
	String levelInfo();

	@ClassName("notificationView-warning")
	String levelWarning();

	@ClassName("notificationView-error")
	String levelError();
	
	@ClassName("notificationView-success")
	String levelSuccess();

}
