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

import java.util.List;

import com.braintribe.gwt.gme.notification.client.NotificationAction;
import com.braintribe.gwt.gme.notification.client.NotificationViewModel;
import com.braintribe.model.notification.NotificationEventSource;
import com.braintribe.model.processing.notification.api.NotificationEventSourceExpert;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.XTemplates.FormatterFactories;
import com.sencha.gxt.core.client.XTemplates.FormatterFactory;
import com.sencha.gxt.core.client.XTemplates.FormatterFactoryMethod;

//@formatter:off
@FormatterFactories(@FormatterFactory(factory = NotificationFormatterFactory.class, methods = {
	@FormatterFactoryMethod(name = "expanded", method = "getExpanded"),
	@FormatterFactoryMethod(name = "source", method = "getSource"),
	@FormatterFactoryMethod(name = "unread", method = "getUnread"),
	@FormatterFactoryMethod(name = "level", method = "getLevel"),
	@FormatterFactoryMethod(name = "command", method = "getCommand"),
	@FormatterFactoryMethod(name = "message", method = "getMessage")
}))
//@formatter:on
public interface NotificationTemplates extends XTemplates {

	public static final NotificationTemplates INSTANCE = GWT.create(NotificationTemplates.class);

	@XTemplate(source = "NotificationBar.xhtml")
	SafeHtml renderBar(NotificationBarStyle style, String textStyleClass, SafeHtml message, List<NotificationAction> actions);
	
	@XTemplate(source = "ConfirmationBar.xhtml")
	SafeHtml renderConfirmationBar(NotificationBarStyle style, SafeStyles confirmationIconStyle, SafeHtml message, List<NotificationAction> actions);
	
	@XTemplate(source = "MessageBar.xhtml")
	SafeHtml renderMessageBar(NotificationBarStyle style, SafeStyles confirmationIconStyle, SafeHtml message, List<NotificationAction> actions);

	@XTemplate(source = "NotificationIcon.xhtml")
	SafeHtml renderIcon(NotificationIconStyle style, SafeStyles icon, String value);

	@XTemplate(source = "NotificationView.xhtml")
	SafeHtml renderView(NotificationViewStyle style, NotificationViewModel model, NotificationEventSourceExpert<NotificationEventSource> expert);

	@XTemplate(source = "NotificationEmpty.xhtml")
	SafeHtml renderEmpty(LocalizedText messages);

}
